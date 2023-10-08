package mist475.mcpatcherforge.mixins.early.client.renderer;

import java.nio.IntBuffer;
import java.util.Arrays;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.EntitySorter;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IWorldAccess;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.llamalad7.mixinextras.injector.WrapWithCondition;
import com.prupe.mcpatcher.cc.ColorizeWorld;
import com.prupe.mcpatcher.renderpass.RenderPass;
import com.prupe.mcpatcher.renderpass.RenderPassMap;
import com.prupe.mcpatcher.sky.SkyRenderer;

@Mixin(RenderGlobal.class)
public abstract class MixinRenderGlobal implements IWorldAccess {

    @Shadow
    public List<TileEntity> tileEntities;
    @Shadow
    private WorldClient theWorld;
    @Shadow
    private List<WorldRenderer> worldRenderersToUpdate;
    @Shadow
    private WorldRenderer[] sortedWorldRenderers;
    @Shadow
    private WorldRenderer[] worldRenderers;
    @Shadow
    private int renderChunksWide;
    @Shadow
    private int renderChunksTall;
    @Shadow
    private int renderChunksDeep;
    @Shadow
    private int glRenderListBase;
    @Shadow
    private Minecraft mc;
    @Shadow
    private IntBuffer glOcclusionQueryBase;
    @Shadow
    private boolean occlusionEnabled;
    @Shadow
    private int minBlockX;
    @Shadow
    private int minBlockY;
    @Shadow
    private int minBlockZ;
    @Shadow
    private int maxBlockX;
    @Shadow
    private int maxBlockY;
    @Shadow
    private int maxBlockZ;
    @Shadow
    private int renderDistanceChunks;
    @Shadow
    private int renderEntitiesStartupCounter;

    @Shadow
    protected abstract void markRenderersForNewPosition(int p_72722_1_, int p_72722_2_, int p_72722_3_);

    @Redirect(
        method = "<init>(Lnet/minecraft/client/Minecraft;)V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/renderer/GLAllocation;generateDisplayLists(I)I",
            ordinal = 0))
    private int modifyRenderGlobal(int n) {
        return n / 3 * 5;
    }

    /**
     * @author Mist475 (adapted from Paul Rupe)
     * @reason Only changes the increase of one variable, couldn't manage to target it though :(
     */
    @Overwrite
    public void loadRenderers() {
        if (this.theWorld != null) {
            Blocks.leaves.setGraphicsLevel(this.mc.gameSettings.fancyGraphics);
            Blocks.leaves2.setGraphicsLevel(this.mc.gameSettings.fancyGraphics);
            this.renderDistanceChunks = this.mc.gameSettings.renderDistanceChunks;
            int i;

            if (this.worldRenderers != null) {
                for (i = 0; i < this.worldRenderers.length; ++i) {
                    this.worldRenderers[i].stopRendering();
                }
            }

            i = this.renderDistanceChunks * 2 + 1;
            this.renderChunksWide = i;
            this.renderChunksTall = 16;
            this.renderChunksDeep = i;
            this.worldRenderers = new WorldRenderer[this.renderChunksWide * this.renderChunksTall
                * this.renderChunksDeep];
            this.sortedWorldRenderers = new WorldRenderer[this.renderChunksWide * this.renderChunksTall
                * this.renderChunksDeep];
            int j = 0;
            int k = 0;
            this.minBlockX = 0;
            this.minBlockY = 0;
            this.minBlockZ = 0;
            this.maxBlockX = this.renderChunksWide;
            this.maxBlockY = this.renderChunksTall;
            this.maxBlockZ = this.renderChunksDeep;
            int l;

            for (l = 0; l < this.worldRenderersToUpdate.size(); ++l) {
                this.worldRenderersToUpdate.get(l).needsUpdate = false;
            }

            this.worldRenderersToUpdate.clear();
            this.tileEntities.clear();
            this.onStaticEntitiesChanged();

            for (l = 0; l < this.renderChunksWide; ++l) {
                for (int i1 = 0; i1 < this.renderChunksTall; ++i1) {
                    for (int j1 = 0; j1 < this.renderChunksDeep; ++j1) {
                        this.worldRenderers[(j1 * this.renderChunksTall + i1) * this.renderChunksWide
                            + l] = new WorldRenderer(
                                this.theWorld,
                                this.tileEntities,
                                l * 16,
                                i1 * 16,
                                j1 * 16,
                                this.glRenderListBase + j);

                        if (this.occlusionEnabled) {
                            this.worldRenderers[(j1 * this.renderChunksTall + i1) * this.renderChunksWide
                                + l].glOcclusionQuery = this.glOcclusionQueryBase.get(k);
                        }

                        this.worldRenderers[(j1 * this.renderChunksTall + i1) * this.renderChunksWide
                            + l].isWaitingOnOcclusionQuery = false;
                        this.worldRenderers[(j1 * this.renderChunksTall + i1) * this.renderChunksWide
                            + l].isVisible = true;
                        this.worldRenderers[(j1 * this.renderChunksTall + i1) * this.renderChunksWide
                            + l].isInFrustum = true;
                        this.worldRenderers[(j1 * this.renderChunksTall + i1) * this.renderChunksWide
                            + l].chunkIndex = k++;
                        this.worldRenderers[(j1 * this.renderChunksTall + i1) * this.renderChunksWide + l].markDirty();
                        this.sortedWorldRenderers[(j1 * this.renderChunksTall + i1) * this.renderChunksWide
                            + l] = this.worldRenderers[(j1 * this.renderChunksTall + i1) * this.renderChunksWide + l];
                        this.worldRenderersToUpdate
                            .add(this.worldRenderers[(j1 * this.renderChunksTall + i1) * this.renderChunksWide + l]);
                        j += 5; // only change, someone help please!
                    }
                }
            }

            if (this.theWorld != null) {
                EntityLivingBase entitylivingbase = this.mc.renderViewEntity;

                if (entitylivingbase != null) {
                    this.markRenderersForNewPosition(
                        MathHelper.floor_double(entitylivingbase.posX),
                        MathHelper.floor_double(entitylivingbase.posY),
                        MathHelper.floor_double(entitylivingbase.posZ));
                    Arrays.sort(this.sortedWorldRenderers, new EntitySorter(entitylivingbase));
                }
            }

            this.renderEntitiesStartupCounter = 2;
        }
    }

    // Order important here!

    @Inject(
        method = "sortAndRender(Lnet/minecraft/entity/EntityLivingBase;ID)I",
        at = @At(value = "HEAD"),
        cancellable = true)
    private void modifySortAndRender1(EntityLivingBase p_72719_1_, int map18To17, double p_72719_3_,
        CallbackInfoReturnable<Integer> cir) {
        if (!RenderPass.preRenderPass(RenderPassMap.map17To18(map18To17))) {
            cir.setReturnValue(RenderPass.postRenderPass(0));
        }
    }

    @ModifyVariable(
        method = "sortAndRender(Lnet/minecraft/entity/EntityLivingBase;ID)I",
        at = @At(value = "HEAD"),
        ordinal = 0,
        argsOnly = true)
    private int modifySortAndRender2(int map18To17) {
        return RenderPassMap.map18To17(map18To17);
    }

    @Inject(
        method = "sortAndRender(Lnet/minecraft/entity/EntityLivingBase;ID)I",
        at = @At(value = "RETURN"),
        cancellable = true)
    private void modifySortAndRender3(EntityLivingBase p_72719_1_, int p_72719_2_, double p_72719_3_,
        CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(RenderPass.postRenderPass(cir.getReturnValue()));
    }

    @Redirect(
        method = "renderAllRenderLists(ID)V",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/EntityRenderer;enableLightmap(D)V"))
    private void modifyRenderAllRenderLists(EntityRenderer instance, double partialTick) {
        RenderPass.enableDisableLightmap(instance, partialTick);
    }

    @Inject(method = "renderSky(F)V", at = @At("HEAD"))
    private void modifyRenderSky1(float partialTick, CallbackInfo ci) {
        SkyRenderer.setup(this.theWorld, partialTick, this.theWorld.getCelestialAngle(partialTick));
    }

    @ModifyArg(
        method = "renderSky(F)V",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/Tessellator;setColorOpaque_I(I)V"))
    private int modifyRenderSky2(int endSkyColor) {
        return ColorizeWorld.endSkyColor;
    }

    @Inject(
        method = "renderSky(F)V",
        at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/GL11;glRotatef(FFFF)V", remap = false, ordinal = 9))
    private void modifyRenderSky3(float partialTick, CallbackInfo ci) {
        SkyRenderer.renderAll();
    }

    // Ordinal 0 shouldn't be redirected unfortunately
    @ModifyArg(
        method = "renderSky(F)V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/renderer/texture/TextureManager;bindTexture(Lnet/minecraft/util/ResourceLocation;)V",
            ordinal = 1))
    private ResourceLocation modifyRenderSky4(ResourceLocation location) {
        return SkyRenderer.setupCelestialObject(location);
    }

    @ModifyArg(
        method = "renderSky(F)V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/renderer/texture/TextureManager;bindTexture(Lnet/minecraft/util/ResourceLocation;)V",
            ordinal = 2))
    private ResourceLocation modifyRenderSky5(ResourceLocation location) {
        return SkyRenderer.setupCelestialObject(location);
    }

    @WrapWithCondition(
        method = "renderSky(F)V",
        at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/GL11;glColor4f(FFFF)V", remap = false, ordinal = 1))
    private boolean modifyRenderSky6(float f1, float f2, float f3, float f4) {
        return !SkyRenderer.active;
    }

    @WrapWithCondition(
        method = "renderSky(F)V",
        at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/GL11;glCallList(I)V", remap = false, ordinal = 1))
    private boolean modifyRenderSky7(int i) {
        return !SkyRenderer.active;
    }

    @ModifyArg(
        method = "renderSky(F)V",
        at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/GL11;glTranslatef(FFF)V", remap = false, ordinal = 2),
        index = 1)
    private float modifyRenderSky8(float input) {
        // -((d0 - 16.0D)) turned into -((d0 - SkyRenderer.horizonHeight))
        return (float) (input - 16f + SkyRenderer.horizonHeight);
    }

    @Redirect(
        method = "renderClouds(F)V",
        at = @At(value = "FIELD", target = "Lnet/minecraft/client/settings/GameSettings;fancyGraphics:Z"))
    private boolean modifyRenderClouds(GameSettings instance) {
        return ColorizeWorld.drawFancyClouds(instance.fancyGraphics);
    }
}
