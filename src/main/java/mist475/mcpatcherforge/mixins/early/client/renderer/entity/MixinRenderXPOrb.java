package mist475.mcpatcherforge.mixins.early.client.renderer.entity;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderXPOrb;
import net.minecraft.entity.item.EntityXPOrb;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.prupe.mcpatcher.cc.ColorizeEntity;

@Mixin(RenderXPOrb.class)
public abstract class MixinRenderXPOrb {

    @Redirect(
        method = "doRender(Lnet/minecraft/entity/item/EntityXPOrb;DDDFF)V",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/Tessellator;setColorRGBA_I(II)V"))
    private void modifyDoRender(Tessellator instance, int p_78384_1_, int p_78384_2_, EntityXPOrb p_76986_1_,
        double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_) {
        instance.setColorRGBA_I(
            ColorizeEntity.colorizeXPOrb(p_78384_1_, ((float) p_76986_1_.xpColor + p_76986_9_) / 2.0F),
            p_78384_2_);
    }
}
