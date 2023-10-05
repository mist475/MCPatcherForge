package mist475.mcpatcherforge.mixins.early.Entity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
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

import mist475.mcpatcherforge.mixins.interfaces.EntityRendererExpansion;

@Mixin(EntityRenderer.class)
public abstract class MixinEntityRenderer implements EntityRendererExpansion {

    @Shadow
    private Minecraft mc;

    @Shadow
    @Final
    private DynamicTexture lightmapTexture;
    @Shadow
    @Final
    private int[] lightmapColors;
    @Shadow
    private float bossColorModifier;
    @Shadow
    private float bossColorModifierPrev;

    @Shadow
    private boolean lightmapUpdateNeeded;
    @Shadow
    public float torchFlickerX;

    @Shadow
    float fogColorRed;
    @Shadow
    float fogColorGreen;
    @Shadow
    float fogColorBlue;

    @Shadow
    protected abstract float getNightVisionBrightness(EntityPlayer player, float p_82830_2_);

    @Shadow
    protected abstract void renderRainSnow(float p_78474_1_);

    /**
     * @author Mist475 (adapted from Paul Rupe)
     * @reason Was tired when I wrote this, will be an inject once I've checked the code again
     */
    @Overwrite
    private void updateLightmap(float partialTick) {
        final WorldClient f = this.mc.theWorld;
        // patch 1 start
        if (Lightmap.computeLightmap((EntityRenderer) (Object) this, f, this.lightmapColors, partialTick)) {
            this.lightmapTexture.updateDynamicTexture();
            this.lightmapUpdateNeeded = false;
            return;
        }
        // patch 1 end
        if (f == null) {
            return;
        }
        for (int i = 0; i < 256; ++i) {
            float n = f.provider.lightBrightnessTable[i / 16] * (f.getSunBrightness(1.0f) * 0.95f + 0.05f);
            final float n2 = f.provider.lightBrightnessTable[i % 16] * (this.torchFlickerX * 0.1f + 1.5f);
            if (f.lastLightningBolt > 0) {
                n = f.provider.lightBrightnessTable[i / 16];
            }
            final float n3 = n * (f.getSunBrightness(1.0f) * 0.65f + 0.35f);
            final float n4 = n * (f.getSunBrightness(1.0f) * 0.65f + 0.35f);
            final float n5 = n;
            final float n7 = n2 * ((n2 * 0.6f + 0.4f) * 0.6f + 0.4f);
            final float n8 = n2 * (n2 * n2 * 0.6f + 0.4f);
            final float n9 = n3 + n2;
            final float n10 = n4 + n7;
            final float n11 = n5 + n8;
            float n12 = n9 * 0.96f + 0.03f;
            float n13 = n10 * 0.96f + 0.03f;
            float n14 = n11 * 0.96f + 0.03f;
            if (this.bossColorModifier > 0.0f) {
                final float n15 = this.bossColorModifierPrev
                    + (this.bossColorModifier - this.bossColorModifierPrev) * partialTick;
                n12 = n12 * (1.0f - n15) + n12 * 0.7f * n15;
                n13 = n13 * (1.0f - n15) + n13 * 0.6f * n15;
                n14 = n14 * (1.0f - n15) + n14 * 0.6f * n15;
            }
            if (f.provider.dimensionId == 1) {
                n12 = 0.22f + n2 * 0.75f;
                n13 = 0.28f + n7 * 0.75f;
                n14 = 0.25f + n8 * 0.75f;
            }
            if (this.mc.thePlayer.isPotionActive(Potion.nightVision)) {
                final float a = this.getNightVisionBrightness(this.mc.thePlayer, partialTick);
                float n16 = 1.0f / n12;
                if (n16 > 1.0f / n13) {
                    n16 = 1.0f / n13;
                }
                if (n16 > 1.0f / n14) {
                    n16 = 1.0f / n14;
                }
                n12 = n12 * (1.0f - a) + n12 * n16 * a;
                n13 = n13 * (1.0f - a) + n13 * n16 * a;
                n14 = n14 * (1.0f - a) + n14 * n16 * a;
            }
            if (n12 > 1.0f) {
                n12 = 1.0f;
            }
            if (n13 > 1.0f) {
                n13 = 1.0f;
            }
            if (n14 > 1.0f) {
                n14 = 1.0f;
            }
            final float ag = this.mc.gameSettings.gammaSetting;
            final float n17 = 1.0f - n12;
            final float n18 = 1.0f - n13;
            final float n19 = 1.0f - n14;
            final float n20 = 1.0f - n17 * n17 * n17 * n17;
            final float n21 = 1.0f - n18 * n18 * n18 * n18;
            final float n22 = 1.0f - n19 * n19 * n19 * n19;
            final float n23 = n12 * (1.0f - ag) + n20 * ag;
            final float n24 = n13 * (1.0f - ag) + n21 * ag;
            final float n25 = n14 * (1.0f - ag) + n22 * ag;
            float n26 = n23 * 0.96f + 0.03f;
            float n27 = n24 * 0.96f + 0.03f;
            float n28 = n25 * 0.96f + 0.03f;
            if (n26 > 1.0f) {
                n26 = 1.0f;
            }
            if (n27 > 1.0f) {
                n27 = 1.0f;
            }
            if (n28 > 1.0f) {
                n28 = 1.0f;
            }
            if (n26 < 0.0f) {
                n26 = 0.0f;
            }
            if (n27 < 0.0f) {
                n27 = 0.0f;
            }
            if (n28 < 0.0f) {
                n28 = 0.0f;
            }
            this.lightmapColors[i] = (255 << 24 | (int) (n26 * 255.0f) << 16
                | (int) (n27 * 255.0f) << 8
                | (int) (n28 * 255.0f));
        }
        this.lightmapTexture.updateDynamicTexture();
        this.lightmapUpdateNeeded = false;
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

    // TODO: refactor so this can be removed as it's not really necessary
    public float getNightVisionStrength(final float n) {
        if (this.mc.thePlayer.isPotionActive(Potion.nightVision)) {
            return this.getNightVisionBrightness(this.mc.thePlayer, n);
        }
        return 0.0f;
    }
}
