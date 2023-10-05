package mist475.mcpatcherforge.mixins.early.Particle;

import net.minecraft.client.particle.EntityBubbleFX;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.world.World;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.prupe.mcpatcher.cc.ColorizeBlock;
import com.prupe.mcpatcher.cc.Colorizer;

@Mixin(EntityBubbleFX.class)
public abstract class MixinEntityBubbleFX extends EntityFX {

    protected MixinEntityBubbleFX(World p_i1218_1_, double p_i1218_2_, double p_i1218_4_, double p_i1218_6_) {
        super(p_i1218_1_, p_i1218_2_, p_i1218_4_, p_i1218_6_);
    }

    @Inject(method = "<init>(Lnet/minecraft/world/World;DDDDDD)V", at = @At("RETURN"))
    private void modifyConstructor(World p_i1198_1_, double p_i1198_2_, double p_i1198_4_, double p_i1198_6_,
        double p_i1198_8_, double p_i1198_10_, double p_i1198_12_, CallbackInfo ci) {
        if (ColorizeBlock.computeWaterColor(false, (int) this.posX, (int) this.posY, (int) this.posZ)) {
            this.particleRed = Colorizer.setColor[0];
            this.particleGreen = Colorizer.setColor[1];
            this.particleBlue = Colorizer.setColor[2];
        } else {
            this.particleRed = 1.0f;
            this.particleGreen = 1.0f;
            this.particleBlue = 1.0f;
        }
    }
}
