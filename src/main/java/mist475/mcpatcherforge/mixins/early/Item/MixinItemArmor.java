package mist475.mcpatcherforge.mixins.early.Item;

import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import com.prupe.mcpatcher.cc.ColorizeEntity;

@Mixin(ItemArmor.class)
public abstract class MixinItemArmor {

    @Final
    @Shadow
    private ItemArmor.ArmorMaterial material;

    /**
     * @author Mist475 (adapted from Paul Rupe)
     * @reason was tired
     *         TODO: turn into redirects
     */
    @Overwrite
    public int getColor(ItemStack p_82814_1_) {
        if (this.material != ItemArmor.ArmorMaterial.CLOTH) {
            return -1;
        }
        NBTTagCompound nbttagcompound = p_82814_1_.getTagCompound();

        if (nbttagcompound == null) {
            // patch 1
            return ColorizeEntity.undyedLeatherColor;
        }
        final NBTTagCompound displayCompound = nbttagcompound.getCompoundTag("display");
        if (displayCompound == null) {
            // patch 2
            return ColorizeEntity.undyedLeatherColor;
        }
        if (displayCompound.hasKey("color", 3)) {
            return displayCompound.getInteger("color");
        }
        // patch 3
        return ColorizeEntity.undyedLeatherColor;
    }
}
