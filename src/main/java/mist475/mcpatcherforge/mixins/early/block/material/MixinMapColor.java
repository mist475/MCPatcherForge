package mist475.mcpatcherforge.mixins.early.block.material;

import net.minecraft.block.material.MapColor;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import mist475.mcpatcherforge.mixins.interfaces.MapColorExpansion;

@Mixin(MapColor.class)
public abstract class MixinMapColor implements MapColorExpansion {

    @Unique
    private int mcpatcher_forge$origColorValue;

    @Inject(method = "<init>(II)V", at = @At("RETURN"))
    private void modifyConstructor(int p_i2117_1_, int p_i2117_2_, CallbackInfo ci) {
        this.setOriginalColorValue(p_i2117_2_);
    }

    public int getOriginalColorValue() {
        return mcpatcher_forge$origColorValue;
    }

    public void setOriginalColorValue(int value) {
        mcpatcher_forge$origColorValue = value;
    }
}
