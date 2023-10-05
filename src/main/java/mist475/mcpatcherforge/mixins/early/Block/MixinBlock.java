package mist475.mcpatcherforge.mixins.early.Block;

import net.minecraft.block.Block;
import net.minecraft.world.IBlockAccess;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.prupe.mcpatcher.cc.ColorizeBlock;

@Mixin(Block.class)
public abstract class MixinBlock {

    @Inject(method = "getBlockColor()I", at = @At("HEAD"), cancellable = true)
    public void modifyGetBlockColor(CallbackInfoReturnable<Integer> cir) {
        if (ColorizeBlock.colorizeBlock((Block) (Object) this)) {
            cir.setReturnValue(ColorizeBlock.blockColor);
        }
    }

    @Inject(method = "getRenderColor(I)I", at = @At("HEAD"), cancellable = true)
    public void modifyGetRenderColor(int meta, CallbackInfoReturnable<Integer> cir) {
        if (ColorizeBlock.colorizeBlock((Block) (Object) this, meta)) {
            cir.setReturnValue(ColorizeBlock.blockColor);
        }
    }

    @Inject(method = "colorMultiplier(Lnet/minecraft/world/IBlockAccess;III)I", at = @At("HEAD"), cancellable = true)
    public void modifyColorMultiplier(IBlockAccess worldIn, int x, int y, int z, CallbackInfoReturnable<Integer> cir) {
        if (ColorizeBlock.colorizeBlock((Block) (Object) this, worldIn, x, y, z)) {
            cir.setReturnValue(ColorizeBlock.blockColor);
        }
    }
}
