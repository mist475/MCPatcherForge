package mist475.mcpatcherforge.mixins.early.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderWolf;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.prupe.mcpatcher.cc.ColorizeEntity;
import com.prupe.mcpatcher.mob.MobRandomizer;

@Mixin(RenderWolf.class)
public abstract class MixinRenderWolf extends RenderLiving {

    public MixinRenderWolf(ModelBase modelBase, float shadowSize) {
        super(modelBase, shadowSize);
    }

    @Redirect(
        method = "shouldRenderPass(Lnet/minecraft/entity/passive/EntityWolf;IF)I",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/renderer/entity/RenderWolf;bindTexture(Lnet/minecraft/util/ResourceLocation;)V",
            ordinal = 1))
    private void modifyShouldRenderPass1(RenderWolf instance, ResourceLocation resourceLocation, EntityWolf entity) {
        this.bindTexture(MobRandomizer.randomTexture(entity, resourceLocation));
    }

    @Redirect(
        method = "shouldRenderPass(Lnet/minecraft/entity/passive/EntityWolf;IF)I",
        at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/GL11;glColor3f(FFF)V", ordinal = 1, remap = false))
    private void modifyShouldRenderPass2(float red, float green, float blue, EntityWolf entity) {
        int collarColor = entity.getCollarColor();
        GL11.glColor3f(
            ColorizeEntity.getWolfCollarColor(EntitySheep.fleeceColorTable[collarColor], collarColor)[0],
            ColorizeEntity.getWolfCollarColor(EntitySheep.fleeceColorTable[collarColor], collarColor)[1],
            ColorizeEntity.getWolfCollarColor(EntitySheep.fleeceColorTable[collarColor], collarColor)[2]);
    }
}
