package mist475.mcpatcherforge.mixins.early.client.renderer.tileentity;

import net.minecraft.client.renderer.tileentity.TileEntitySignRenderer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import com.prupe.mcpatcher.cc.ColorizeWorld;

@Mixin(TileEntitySignRenderer.class)
public abstract class MixinTileEntitySignRenderer {

    @ModifyVariable(
        method = "renderTileEntityAt(Lnet/minecraft/tileentity/TileEntitySign;DDDF)V",
        at = @At("STORE"),
        ordinal = 0)
    private byte modifyRenderTileEntityAt(byte value) {
        return (byte) ColorizeWorld.colorizeSignText();
    }
}
