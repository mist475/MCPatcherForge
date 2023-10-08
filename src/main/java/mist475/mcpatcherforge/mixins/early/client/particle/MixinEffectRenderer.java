package mist475.mcpatcherforge.mixins.early.client.particle;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.util.ReportedException;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.prupe.mcpatcher.sky.FireworksHelper;

@SuppressWarnings({ "rawtypes" })
@Mixin(EffectRenderer.class)
public abstract class MixinEffectRenderer {

    @Shadow
    @Final
    private static ResourceLocation particleTextures;

    @Shadow
    private List[] fxLayers;

    @Shadow
    private TextureManager renderer;

    @Inject(
        method = "<init>(Lnet/minecraft/world/World;Lnet/minecraft/client/renderer/texture/TextureManager;)V",
        at = @At("RETURN"))
    private void modifyConstructor(World p_i1220_1_, TextureManager p_i1220_2_, CallbackInfo ci) {
        this.fxLayers = new List[5];
        for (int i = 0; i < this.fxLayers.length; ++i) {
            this.fxLayers[i] = new ArrayList();
        }
    }

    @Redirect(
        method = "addEffect(Lnet/minecraft/client/particle/EntityFX;)V",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/client/particle/EntityFX;getFXLayer()I"))
    private int modifyAddEffect(EntityFX instance) {
        return FireworksHelper.getFXLayer(instance);
    }

    @ModifyConstant(method = "updateEffects()V", constant = @Constant(intValue = 4))
    private int modifyListSize1(int constant) {
        return 5;
    }

    @ModifyConstant(method = "clearEffects(Lnet/minecraft/world/World;)V", constant = @Constant(intValue = 4))
    private int modifyListSize2(int constant) {
        return 5;
    }

    /**
     * @author Mist475 (adapted from Paul Rupe)
     * @reason was too tired to do this neatly
     */
    @Overwrite
    public void renderParticles(Entity p_78874_1_, float p_78874_2_) {
        float f1 = ActiveRenderInfo.rotationX;
        float f2 = ActiveRenderInfo.rotationZ;
        float f3 = ActiveRenderInfo.rotationYZ;
        float f4 = ActiveRenderInfo.rotationXY;
        float f5 = ActiveRenderInfo.rotationXZ;
        EntityFX.interpPosX = p_78874_1_.lastTickPosX
            + (p_78874_1_.posX - p_78874_1_.lastTickPosX) * (double) p_78874_2_;
        EntityFX.interpPosY = p_78874_1_.lastTickPosY
            + (p_78874_1_.posY - p_78874_1_.lastTickPosY) * (double) p_78874_2_;
        EntityFX.interpPosZ = p_78874_1_.lastTickPosZ
            + (p_78874_1_.posZ - p_78874_1_.lastTickPosZ) * (double) p_78874_2_;

        for (int k = 0; k < 5; ++k) // patch 1: 3-> 5
        {
            final int i = k;
            // patch 2: Wrap isEmpty with skipThisLayer
            if (!FireworksHelper.skipThisLayer(this.fxLayers[i].isEmpty(), i)) {
                switch (i) {
                    case 0:
                    default:
                        this.renderer.bindTexture(particleTextures);
                        break;
                    case 1:
                        this.renderer.bindTexture(TextureMap.locationBlocksTexture);
                        break;
                    case 2:
                        this.renderer.bindTexture(TextureMap.locationItemsTexture);
                }

                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                GL11.glDepthMask(false);
                GL11.glEnable(GL11.GL_BLEND);
                FireworksHelper.setParticleBlendMethod(i, 0, true); // Patch 3 Blend -> this
                GL11.glAlphaFunc(GL11.GL_GREATER, 0.003921569F);
                Tessellator tessellator = Tessellator.instance;
                tessellator.startDrawingQuads();

                for (int j = 0; j < this.fxLayers[i].size(); ++j) {
                    final EntityFX entityfx = (EntityFX) this.fxLayers[i].get(j);
                    if (entityfx == null) continue;
                    tessellator.setBrightness(entityfx.getBrightnessForRender(p_78874_2_));

                    try {
                        entityfx.renderParticle(tessellator, p_78874_2_, f1, f5, f2, f3, f4);
                    } catch (Throwable throwable) {
                        CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Rendering Particle");
                        CrashReportCategory crashreportcategory = crashreport.makeCategory("Particle being rendered");
                        crashreportcategory.addCrashSectionCallable("Particle", entityfx::toString);
                        crashreportcategory.addCrashSectionCallable("Particle Type", () -> {
                            if (i == 0) {
                                return "MISC_TEXTURE";
                            } else if (i == 1) {
                                return "TERRAIN_TEXTURE";
                            } else if (i == 2) {
                                return "ITEM_TEXTURE";
                            } else if (i == 3) {
                                return "ENTITY_PARTICLE_TEXTURE";
                            } else {
                                return "Unknown - " + i;
                            }
                        });
                        throw new ReportedException(crashreport);
                    }
                }

                tessellator.draw();
                GL11.glDisable(GL11.GL_BLEND);
                GL11.glDepthMask(true);
                GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
            }
        }
    }
}
