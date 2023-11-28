package mist475.mcpatcherforge.mixins.early.client.renderer;

import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IWorldAccess;

import org.spongepowered.asm.mixin.Mixin;
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
    private WorldClient theWorld;

    @Redirect(
        method = "<init>(Lnet/minecraft/client/Minecraft;)V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/renderer/GLAllocation;generateDisplayLists(I)I",
            ordinal = 0))
    private int modifyRenderGlobal(int n) {
        return GLAllocation.generateDisplayLists(n / 3 * 5);
    }

    @ModifyVariable(
        method = "loadRenderers()V",
        at = @At(
            value = "INVOKE",
            target = "Ljava/util/List;add(Ljava/lang/Object;)Z",
            remap = false,
            shift = At.Shift.AFTER),
        ordinal = 1)
    private int modifyLoadRenderers(int input) {
        return input + 2;
    }

    // Order important here!

    @Inject(
        method = "sortAndRender(Lnet/minecraft/entity/EntityLivingBase;ID)I",
        at = @At(value = "HEAD"),
        cancellable = true)
    private void modifySortAndRender1(EntityLivingBase entity, int map18To17, double partialTickTime,
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
    private void modifySortAndRender3(EntityLivingBase entity, int renderPass, double partialTickTime,
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
