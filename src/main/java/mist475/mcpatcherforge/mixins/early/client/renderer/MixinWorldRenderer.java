package mist475.mcpatcherforge.mixins.early.client.renderer;

import java.util.HashSet;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.shader.TesselatorVertexState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ChunkCache;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.client.ForgeHooksClient;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.prupe.mcpatcher.renderpass.RenderPass;

@Mixin(WorldRenderer.class)
public abstract class MixinWorldRenderer {

    @Shadow
    private TesselatorVertexState vertexState;
    @Shadow
    public World worldObj;
    @Shadow
    public static int chunksUpdated;
    @Shadow
    public int posX;
    @Shadow
    public int posY;
    @Shadow
    public int posZ;

    @Shadow
    public boolean[] skipRenderPass;
    @Shadow
    public boolean needsUpdate;
    @Shadow
    public boolean isChunkLit;
    @Shadow
    private boolean isInitialized;
    @Shadow
    public List<TileEntity> tileEntityRenderers;
    @Shadow
    private List<TileEntity> tileEntities;
    @Shadow
    private int bytesDrawn;

    @Shadow
    protected abstract void preRenderBlocks(int p_147890_1_);

    @Shadow
    protected abstract void postRenderBlocks(int p_147891_1_, EntityLivingBase p_147891_2_);

    @Shadow
    public abstract void setPosition(int p_78913_1_, int p_78913_2_, int p_78913_3_);

    @Inject(method = "<init>(Lnet/minecraft/world/World;Ljava/util/List;IIII)V", at = @At("RETURN"))
    private void modifyWorldRendererConstructor1(World world, List<TileEntity> tileEntities, int x, int y, int z,
        int glRenderList, CallbackInfo ci) {
        skipRenderPass = new boolean[4];
        this.setPosition(x, y, z);
        this.needsUpdate = false;
    }

    /**
     * Cancel first call as skipRenderPass isn't ready yet
     */
    @Redirect(
        method = "<init>(Lnet/minecraft/world/World;Ljava/util/List;IIII)V",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/WorldRenderer;setPosition(III)V"))
    private void modifyWorldRendererConstructor2(WorldRenderer renderer, int x, int y, int z) {}

    @ModifyArg(
        method = "setPosition(III)V",
        at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/GL11;glNewList(II)V", remap = false),
        index = 0)
    private int modifySetPosition(int list) {
        return list + 2;
    }

    /**
     * @author Mist475 (adapted from Paul Rupe)
     * @reason decomp was unclear on some changes
     *         TODO: take a look again at some point to see if I can use something
     *         more graceful instead
     */
    @SuppressWarnings("DuplicatedCode")
    @Overwrite
    public void updateRenderer(EntityLivingBase p_147892_1_) {
        if (!this.needsUpdate) {
            RenderPass.finish();
            return;
        }
        this.needsUpdate = false;
        final int x = this.posX;
        final int y = this.posY;
        final int z = this.posZ;
        final int n = this.posX + 16;
        final int n2 = this.posY + 16;
        final int n3 = this.posZ + 16;
        for (int i = 0; i < 4; ++i) {
            this.skipRenderPass[i] = true;
        }
        Chunk.isLit = false;
        final HashSet<TileEntity> set = new HashSet<>(this.tileEntityRenderers);
        this.tileEntityRenderers.clear();
        final EntityLivingBase j = Minecraft.getMinecraft().renderViewEntity;
        final int c2 = MathHelper.floor_double(j.posX);
        final int c3 = MathHelper.floor_double(j.posY);
        final int c4 = MathHelper.floor_double(j.posZ);
        final int n4 = 1;
        final ChunkCache ahr = new ChunkCache(this.worldObj, x - n4, y - n4, z - n4, n + n4, n2 + n4, n3 + n4, n4);
        if (!ahr.extendedLevelsInChunkCache()) {
            ++chunksUpdated;
            final RenderBlocks blm = new RenderBlocks(ahr);
            ForgeHooksClient.setWorldRendererRB(blm);
            this.bytesDrawn = 0;
            this.vertexState = null;
            for (int k = 0; k < 4; ++k) {
                boolean checkRenderPasses = false;
                boolean b = false;
                int n5 = 0;
                RenderPass.start(k);
                for (int l = y; l < n2; ++l) {
                    for (int n6 = z; n6 < n3; ++n6) {
                        for (int n7 = x; n7 < n; ++n7) {
                            final Block a = ahr.getBlock(n7, l, n6);
                            if (a.getMaterial() != Material.air) {
                                if (n5 == 0) {
                                    n5 = 1;
                                    this.preRenderBlocks(k);
                                }
                                if (k == 0 && a.hasTileEntity(ahr.getBlockMetadata(n7, l, n6))) {
                                    final TileEntity o = ahr.getTileEntity(n7, l, n6);
                                    if (TileEntityRendererDispatcher.instance.hasSpecialRenderer(o)) {
                                        this.tileEntityRenderers.add(o);
                                    }
                                }
                                final int w = a.getRenderBlockPass();
                                checkRenderPasses = RenderPass.checkRenderPasses(a, checkRenderPasses);
                                if (RenderPass.canRenderInThisPass(w == k)) {
                                    b |= blm.renderBlockByRenderType(a, n7, l, n6);
                                    if (a.getRenderType() == 0 && n7 == c2 && l == c3 && n6 == c4) {
                                        blm.setRenderFromInside(true);
                                        blm.setRenderAllFaces(true);
                                        blm.renderBlockByRenderType(a, n7, l, n6);
                                        blm.setRenderFromInside(false);
                                        blm.setRenderAllFaces(false);
                                    }
                                }
                            }
                        }
                    }
                }
                if (b) {
                    this.skipRenderPass[k] = false;
                }
                if (n5 != 0) {
                    this.postRenderBlocks(k, p_147892_1_);
                }
                if (!checkRenderPasses) {
                    break;
                }
                ForgeHooksClient.setWorldRendererRB(null);
            }
        }
        final HashSet<TileEntity> set2 = new HashSet<>(this.tileEntityRenderers);
        set2.removeAll(set);
        this.tileEntities.addAll(set2);
        this.tileEntityRenderers.forEach(set::remove);
        this.tileEntities.removeAll(set);
        this.isChunkLit = Chunk.isLit;
        this.isInitialized = true;
        RenderPass.finish();
    }

    @ModifyConstant(method = "setDontDraw()V", constant = @Constant(intValue = 2))
    private int modifySetDontDraw(int length) {
        // Initial draw to setPosition runs before skipRenderPass length is changed, if 4 is used it will throw an out
        // of bounds
        return this.skipRenderPass.length;
    }

    @ModifyArg(
        method = "callOcclusionQueryList()V",
        at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/GL11;glCallList(I)V", remap = false),
        index = 0)
    private int modifyCallOcclusionQueryList(int list) {
        return list + 2;
    }

    /**
     * @author Mist475 (adapted from Paul Rupe)
     * @reason entire check is different except null check
     */
    @Overwrite
    public boolean skipAllRenderPasses() {
        return this.isInitialized && RenderPass.skipAllRenderPasses(this.skipRenderPass);
    }
}
