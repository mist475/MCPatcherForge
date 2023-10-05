package mist475.mcpatcherforge.mixins.early.Entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.prupe.mcpatcher.cit.CITUtils;

@Mixin(RenderBiped.class)
public abstract class MixinRenderBiped extends RenderLiving {

    public MixinRenderBiped(ModelBase p_i1262_1_, float p_i1262_2_) {
        super(p_i1262_1_, p_i1262_2_);
    }

    @Redirect(
        method = "shouldRenderPass(Lnet/minecraft/entity/EntityLiving;IF)I",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/renderer/entity/RenderBiped;bindTexture(Lnet/minecraft/util/ResourceLocation;)V"))
    private void modifyShouldRenderPass(RenderBiped instance, ResourceLocation resourceLocation,
        EntityLiving entityLiving, int p_77032_2_, float p_77032_3_) {
        this.bindTexture(
            CITUtils.getArmorTexture(resourceLocation, entityLiving, entityLiving.func_130225_q(3 - p_77032_2_)));
    }

    @Redirect(
        method = "func_82408_c(Lnet/minecraft/entity/EntityLiving;IF)V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/renderer/entity/RenderBiped;bindTexture(Lnet/minecraft/util/ResourceLocation;)V"))

    private void modifyFunc_82408_c(RenderBiped instance, ResourceLocation resourceLocation, EntityLiving entityLiving,
        int p_82408_2_, float p_82408_3_) {
        this.bindTexture(
            CITUtils.getArmorTexture(resourceLocation, entityLiving, entityLiving.func_130225_q(3 - p_82408_2_)));
    }
}
