package mist475.mcpatcherforge.mixins.early.client.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.llamalad7.mixinextras.injector.WrapWithCondition;
import com.prupe.mcpatcher.cc.ColorizeWorld;
import com.prupe.mcpatcher.cc.Colorizer;
import com.prupe.mcpatcher.cc.Lightmap;
import com.prupe.mcpatcher.renderpass.RenderPass;

@Mixin(EntityRenderer.class)
public abstract class MixinEntityRenderer {

    @Shadow
    private Minecraft mc;

    @Shadow
    @Final
    private DynamicTexture lightmapTexture;

    @Shadow
    @Final
    private int[] lightmapColors;

    @Shadow
    private boolean lightmapUpdateNeeded;

    @Shadow
    float fogColorRed;

    @Shadow
    float fogColorGreen;

    @Shadow
    float fogColorBlue;

    @Shadow
    protected abstract void renderRainSnow(float p_78474_1_);

    @Inject(method = "updateLightmap(F)V", at = @At("HEAD"), cancellable = true)
    private void modifyUpdateLightMap(float partialTick, CallbackInfo ci) {
        if (Lightmap
            .computeLightmap((EntityRenderer) (Object) this, this.mc.theWorld, this.lightmapColors, partialTick)) {
            this.lightmapTexture.updateDynamicTexture();
            this.lightmapUpdateNeeded = false;
            ci.cancel();
        }
    }

    @WrapWithCondition(
        method = "renderWorld(FJ)V",
        at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/GL11;glShadeModel(I)V", remap = false, ordinal = 0))
    private boolean modifyRenderWorld1(int i) {
        return RenderPass.setAmbientOcclusion(this.mc.gameSettings.ambientOcclusion != 0);
    }

    @WrapWithCondition(
        method = "renderWorld(FJ)V",
        at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/GL11;glShadeModel(I)V", remap = false, ordinal = 2))
    private boolean modifyRenderWorld2(int i) {
        return RenderPass.setAmbientOcclusion(this.mc.gameSettings.ambientOcclusion != 0);
    }

    @Redirect(
        method = "renderWorld(FJ)V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/renderer/RenderGlobal;sortAndRender(Lnet/minecraft/entity/EntityLivingBase;ID)I",
            ordinal = 0))
    private int modifyRenderWorld3(RenderGlobal instance, EntityLivingBase entitylivingbase, int k, double i1) {
        int returnValue = instance.sortAndRender(entitylivingbase, k, i1);
        instance.sortAndRender(entitylivingbase, 4, i1);
        return returnValue;
    }

    @Inject(
        method = "renderWorld(FJ)V",
        at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/GL11;glDepthMask(Z)V", ordinal = 3, remap = false))
    private void modifyRenderWorld4(float p_78471_1_, long p_78471_2_, CallbackInfo ci) {
        this.mc.renderGlobal.sortAndRender(this.mc.renderViewEntity, 5, p_78471_1_);
        this.renderRainSnow(p_78471_1_);
    }

    @Inject(
        method = "updateFogColor(F)V",
        at = @At(
            value = "FIELD",
            target = "Lnet/minecraft/client/renderer/EntityRenderer;fogColorBlue:F",
            ordinal = 11,
            shift = At.Shift.AFTER))
    private void modifyUpdateFogColor1(float p_78466_1_, CallbackInfo ci) {
        float n11 = (float) EnchantmentHelper.getRespiration(this.mc.renderViewEntity) * 0.2F;
        if (ColorizeWorld.computeUnderwaterColor()) {
            this.fogColorRed = Colorizer.setColor[0] + n11;
            this.fogColorGreen = Colorizer.setColor[1] + n11;
            this.fogColorBlue = Colorizer.setColor[2] + n11;
        }
    }

    @Inject(
        method = "updateFogColor(F)V",
        at = @At(
            value = "FIELD",
            target = "Lnet/minecraft/client/renderer/EntityRenderer;fogColorBlue:F",
            ordinal = 12,
            shift = At.Shift.AFTER))
    private void modifyUpdateFogColor2(float p_78466_1_, CallbackInfo ci) {
        if (ColorizeWorld.computeUnderlavaColor()) {
            this.fogColorRed = Colorizer.setColor[0];
            this.fogColorGreen = Colorizer.setColor[1];
            this.fogColorBlue = Colorizer.setColor[2];
        }
    }
}
