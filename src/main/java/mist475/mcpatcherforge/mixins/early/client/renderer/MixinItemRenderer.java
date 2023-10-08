package mist475.mcpatcherforge.mixins.early.client.renderer;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.client.IItemRenderer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.prupe.mcpatcher.cc.ColorizeBlock;
import com.prupe.mcpatcher.cit.CITUtils;

@Mixin(ItemRenderer.class)
public abstract class MixinItemRenderer {

    @Redirect(
        method = "renderItem(Lnet/minecraft/entity/EntityLivingBase;Lnet/minecraft/item/ItemStack;ILnet/minecraftforge/client/IItemRenderer$ItemRenderType;)V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/entity/EntityLivingBase;getItemIcon(Lnet/minecraft/item/ItemStack;I)Lnet/minecraft/util/IIcon;"))
    private IIcon modifyRenderItem1(EntityLivingBase instance, ItemStack item, int p_70620_2_, EntityLivingBase entity,
        ItemStack item2, int p_78443_3_) {
        return CITUtils.getIcon(entity.getItemIcon(item2, p_78443_3_), item2, p_78443_3_);
    }

    @Inject(
        method = "renderItem(Lnet/minecraft/entity/EntityLivingBase;Lnet/minecraft/item/ItemStack;ILnet/minecraftforge/client/IItemRenderer$ItemRenderType;)V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/renderer/ItemRenderer;renderItemIn2D(Lnet/minecraft/client/renderer/Tessellator;FFFFIIF)V",
            ordinal = 0))
    private void modifyRenderItem2(EntityLivingBase p_78443_1_, ItemStack p_78443_2_, int p_78443_3_,
        IItemRenderer.ItemRenderType type, CallbackInfo ci) {
        ColorizeBlock.colorizeWaterBlockGL(Block.getBlockFromItem(p_78443_2_.getItem()));
    }

    @Redirect(
        method = "renderItem(Lnet/minecraft/entity/EntityLivingBase;Lnet/minecraft/item/ItemStack;ILnet/minecraftforge/client/IItemRenderer$ItemRenderType;)V",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;hasEffect(I)Z"),
        remap = false)
    private boolean modifyRenderItem3(ItemStack item, int pass, EntityLivingBase p_78443_1_, ItemStack p_78443_2_,
        int p_78443_3_) {
        return !CITUtils.renderEnchantmentHeld(item, p_78443_3_) && item.hasEffect(pass);
    }
}
