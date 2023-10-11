package mist475.mcpatcherforge.mixins.early.client.renderer.texture;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureCompass;
import net.minecraft.world.World;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.prupe.mcpatcher.hd.FancyDial;

@Mixin(TextureCompass.class)
public abstract class MixinTextureCompass extends TextureAtlasSprite {

    @Unique
    private boolean mcpatcher_forge$fancyDialUpdateHasRan = false;

    public MixinTextureCompass(String p_i1282_1_) {
        super(p_i1282_1_);
    }

    @Inject(method = "<init>(Ljava/lang/String;)V", at = @At("RETURN"))
    private void modifyConstructor(String p_i1285_1_, CallbackInfo ci) {
        FancyDial.setup((TextureCompass) (Object) this);
    }

    @Inject(
        method = "updateCompass(Lnet/minecraft/world/World;DDDZZ)V",
        at = @At(value = "JUMP", ordinal = 12, shift = At.Shift.BEFORE),
        cancellable = true)
    private void modifyUpdateCompass1(World p_94241_1_, double p_94241_2_, double p_94241_4_, double p_94241_6_,
        boolean p_94241_8_, boolean itemFrameRenderer, CallbackInfo ci) {
        if (!this.mcpatcher_forge$fancyDialUpdateHasRan) {
            if (FancyDial.update(this, itemFrameRenderer)) {
                ci.cancel();
            }
            this.mcpatcher_forge$fancyDialUpdateHasRan = true;
        }
    }

    @Inject(method = "updateCompass(Lnet/minecraft/world/World;DDDZZ)V", at = @At("RETURN"))
    private void modifyUpdateCompass2(World p_94241_1_, double p_94241_2_, double p_94241_4_, double p_94241_6_,
        boolean p_94241_8_, boolean p_94241_9_, CallbackInfo ci) {
        this.mcpatcher_forge$fancyDialUpdateHasRan = false;
    }
}
