package mist475.mcpatcherforge.mixins.early.Item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import com.prupe.mcpatcher.cit.CITUtils;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@Mixin(Item.class)
public abstract class MixinItem {

    @Shadow
    public abstract IIcon getIconFromDamage(int p_77617_1_);

    /**
     * @author Mist475 (adapted from Paul Rupe)
     * @reason was too tired, will be Redirect
     */
    @SideOnly(Side.CLIENT)
    @Overwrite
    public IIcon getIconIndex(ItemStack p_77650_1_) {
        return CITUtils.getIcon(this.getIconFromDamage(p_77650_1_.getItemDamage()), p_77650_1_, 0);
    }
}
