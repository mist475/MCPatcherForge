package mist475.mcpatcherforge.mixins.early.client.renderer.entity;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.prupe.mcpatcher.cit.CITUtils;

@Mixin(RenderItem.class)
public abstract class MixinRenderItem extends Render {

    @Final
    @Shadow
    private static ResourceLocation RES_ITEM_GLINT;

    @Shadow
    public float zLevel;

    @Shadow
    protected abstract void renderGlint(int p_77018_1_, int p_77018_2_, int p_77018_3_, int p_77018_4_, int p_77018_5_);

    @Redirect(
        method = "doRender(Lnet/minecraft/entity/item/EntityItem;DDDFF)V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/item/Item;getIcon(Lnet/minecraft/item/ItemStack;I)Lnet/minecraft/util/IIcon;",
            remap = false))
    private IIcon modifyDoRender(Item item, ItemStack itemStack, int pass) {
        return CITUtils.getIcon(item.getIcon(itemStack, pass), itemStack, pass);
    }

    @Redirect(
        method = "renderDroppedItem(Lnet/minecraft/entity/item/EntityItem;Lnet/minecraft/util/IIcon;IFFFFI)V",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;hasEffect(I)Z", remap = false),
        remap = false)
    private boolean modifyRenderDroppedItem(ItemStack instance, int pass) {
        return !CITUtils.renderEnchantmentDropped(instance) && instance.hasEffect(pass);
    }

    @Inject(
        method = "renderItemIntoGUI(Lnet/minecraft/client/gui/FontRenderer;Lnet/minecraft/client/renderer/texture/TextureManager;Lnet/minecraft/item/ItemStack;IIZ)V",
        at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/GL11;glColorMask(ZZZZ)V", remap = false, ordinal = 0),
        remap = false)
    private void modifyRenderItemIntoGUI1(FontRenderer p_77015_1_, TextureManager p_77015_2_, ItemStack p_77015_3_,
        int p_77015_4_, int p_77015_5_, boolean renderEffect, CallbackInfo ci) {
        GL11.glDepthMask(false);
    }

    @Inject(
        method = "renderItemIntoGUI(Lnet/minecraft/client/gui/FontRenderer;Lnet/minecraft/client/renderer/texture/TextureManager;Lnet/minecraft/item/ItemStack;IIZ)V",
        at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/GL11;glEnable(I)V", remap = false, ordinal = 4),
        remap = false)
    private void modifyRenderItemIntoGUI2(FontRenderer p_77015_1_, TextureManager p_77015_2_, ItemStack p_77015_3_,
        int p_77015_4_, int p_77015_5_, boolean renderEffect, CallbackInfo ci) {
        GL11.glDepthMask(true);
    }

    @Redirect(
        method = "renderItemIntoGUI(Lnet/minecraft/client/gui/FontRenderer;Lnet/minecraft/client/renderer/texture/TextureManager;Lnet/minecraft/item/ItemStack;IIZ)V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/item/Item;getIcon(Lnet/minecraft/item/ItemStack;I)Lnet/minecraft/util/IIcon;",
            remap = false),
        remap = false)
    private IIcon modifyRenderItemIntoGUI3(Item item, ItemStack itemStack, int pass) {
        return CITUtils.getIcon(item.getIcon(itemStack, pass), itemStack, pass);
    }

    @Inject(
        method = "renderItemAndEffectIntoGUI(Lnet/minecraft/client/gui/FontRenderer;Lnet/minecraft/client/renderer/texture/TextureManager;Lnet/minecraft/item/ItemStack;II)V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/renderer/entity/RenderItem;renderItemIntoGUI(Lnet/minecraft/client/gui/FontRenderer;Lnet/minecraft/client/renderer/texture/TextureManager;Lnet/minecraft/item/ItemStack;IIZ)V",
            remap = false))
    private void modifyRenderItemAndEffectIntoGUI1(FontRenderer p_82406_1_, TextureManager p_82406_2_,
        ItemStack p_82406_3_, int p_82406_4_, int p_82406_5_, CallbackInfo ci) {
        // Moved to before call, will not trigger with forge event
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glAlphaFunc(GL11.GL_GREATER, 0.01f);
    }

    /**
     * Forge added a false && to the targeted if statement, this adds the entire statement back
     * TODO: target forges event render class instead & check compatability
     */
    @SuppressWarnings("DuplicatedCode")
    @Inject(
        method = "renderItemAndEffectIntoGUI(Lnet/minecraft/client/gui/FontRenderer;Lnet/minecraft/client/renderer/texture/TextureManager;Lnet/minecraft/item/ItemStack;II)V",
        at = @At(value = "FIELD", target = "Lnet/minecraft/client/renderer/entity/RenderItem;zLevel:F", ordinal = 2))
    private void modifyRenderItemAndEffectIntoGUI2(FontRenderer p_82406_1_, TextureManager p_82406_2_,
        ItemStack p_82406_3_, int p_82406_4_, int p_82406_5_, CallbackInfo ci) {
        if (!CITUtils.renderEnchantmentGUI(p_82406_3_, p_82406_4_, p_82406_5_, this.zLevel)
            && p_82406_3_.hasEffect(0)) {
            GL11.glDepthFunc(GL11.GL_EQUAL);
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glDepthMask(false);
            p_82406_2_.bindTexture(RES_ITEM_GLINT);
            GL11.glEnable(GL11.GL_ALPHA_TEST);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glColor4f(0.5F, 0.25F, 0.8F, 1.0F);
            this.renderGlint(p_82406_4_ * 431278612 + p_82406_5_ * 32178161, p_82406_4_ - 2, p_82406_5_ - 2, 20, 20);
            OpenGlHelper.glBlendFunc(770, 771, 1, 0);
            GL11.glDepthMask(true);
            GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glDepthFunc(GL11.GL_LEQUAL);
        }
    }
}