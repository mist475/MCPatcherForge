package mist475.mcpatcherforge.mixins.early.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderSheep;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.util.ResourceLocation;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.prupe.mcpatcher.mob.MobRandomizer;

@Mixin(RenderSheep.class)
public abstract class MixinRenderSheep extends RenderLiving {

    public MixinRenderSheep(ModelBase p_i1262_1_, float p_i1262_2_) {
        super(p_i1262_1_, p_i1262_2_);
    }

    @Redirect(
        method = "shouldRenderPass(Lnet/minecraft/entity/passive/EntitySheep;IF)I",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/renderer/entity/RenderSheep;bindTexture(Lnet/minecraft/util/ResourceLocation;)V"))
    private void modifyShouldRenderPass(RenderSheep instance, ResourceLocation resourceLocation, EntitySheep entity) {
        this.bindTexture(MobRandomizer.randomTexture(entity, resourceLocation));
    }
}