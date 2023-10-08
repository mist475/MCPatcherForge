package mist475.mcpatcherforge.mixins.early.client.particle;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.particle.EntityReddustFX;
import net.minecraft.world.World;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.prupe.mcpatcher.cc.ColorizeBlock;
import com.prupe.mcpatcher.cc.Colorizer;

@Mixin(EntityReddustFX.class)
public abstract class MixinEntityRedDustFX extends EntityFX {

    @Shadow
    float reddustParticleScale;

    protected MixinEntityRedDustFX(World p_i1218_1_, double p_i1218_2_, double p_i1218_4_, double p_i1218_6_) {
        super(p_i1218_1_, p_i1218_2_, p_i1218_4_, p_i1218_6_);
    }

    @Inject(method = "<init>(Lnet/minecraft/world/World;DDDFFFF)V", at = @At("RETURN"))
    private void modifyConstructor(World p_i1224_1_, double p_i1224_2_, double p_i1224_4_, double p_i1224_6_,
        float p_i1224_8_, float p_i1224_9_, float p_i1224_10_, float p_i1224_11_, CallbackInfo ci) {
        // == 1.0f is needed as this runs after the rest of the constructor
        if (p_i1224_9_ == 0.0F || p_i1224_9_ == 1.0f) {
            p_i1224_9_ = 1.0F;
            // Injected block
            if (ColorizeBlock.computeRedstoneWireColor(15)) {
                p_i1224_9_ = Colorizer.setColor[0];
                p_i1224_10_ = Colorizer.setColor[1];
                p_i1224_11_ = Colorizer.setColor[2];
            }
        }

        float f4 = (float) Math.random() * 0.4F + 0.6F;
        this.particleRed = ((float) (Math.random() * 0.20000000298023224D) + 0.8F) * p_i1224_9_ * f4;
        this.particleGreen = ((float) (Math.random() * 0.20000000298023224D) + 0.8F) * p_i1224_10_ * f4;
        this.particleBlue = ((float) (Math.random() * 0.20000000298023224D) + 0.8F) * p_i1224_11_ * f4;
        this.particleScale *= 0.75F;
        this.particleScale *= p_i1224_8_;
        this.reddustParticleScale = this.particleScale;
        this.particleMaxAge = (int) (8.0D / (Math.random() * 0.8D + 0.2D));
        this.particleMaxAge = (int) ((float) this.particleMaxAge * p_i1224_8_);
        this.noClip = false;
    }
}
