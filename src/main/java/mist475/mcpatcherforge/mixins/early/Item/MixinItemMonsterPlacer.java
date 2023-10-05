package mist475.mcpatcherforge.mixins.early.Item;

import net.minecraft.entity.EntityList;
import net.minecraft.item.ItemMonsterPlacer;
import net.minecraft.item.ItemStack;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import com.prupe.mcpatcher.cc.ColorizeItem;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@Mixin(ItemMonsterPlacer.class)
public abstract class MixinItemMonsterPlacer {

    /**
     * @author Mist475 (adapted from Paul Rupe)
     * @reason Bound to be incompatible anyway
     */
    @SideOnly(Side.CLIENT)
    @Overwrite
    public int getColorFromItemStack(ItemStack add, int spots) {
        final EntityList.EntityEggInfo entityegginfo = EntityList.entityEggs.get(add.getItemDamage());
        if (entityegginfo == null) {
            return ColorizeItem.colorizeSpawnerEgg(16777215, add.getItemDamage(), spots);
        }
        if (spots == 0) {
            return ColorizeItem.colorizeSpawnerEgg(entityegginfo.primaryColor, add.getItemDamage(), spots);
        }
        return ColorizeItem.colorizeSpawnerEgg(entityegginfo.secondaryColor, add.getItemDamage(), spots);
    }
}
