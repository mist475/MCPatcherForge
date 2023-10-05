package mist475.mcpatcherforge.mixins.early.Particle;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.particle.EntityPortalFX;
import net.minecraft.world.World;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.prupe.mcpatcher.cc.ColorizeEntity;

@Mixin(EntityPortalFX.class)
public abstract class MixinEntityPortalFX extends EntityFX {

    @Shadow
    private float portalParticleScale;

    protected MixinEntityPortalFX(World p_i1218_1_, double p_i1218_2_, double p_i1218_4_, double p_i1218_6_) {
        super(p_i1218_1_, p_i1218_2_, p_i1218_4_, p_i1218_6_);
    }

    @Inject(method = "<init>(Lnet/minecraft/world/World;DDDDDD)V", at = @At("RETURN"))
    private void modifyConstructor(World p_i1222_1_, double p_i1222_2_, double p_i1222_4_, double p_i1222_6_,
        double p_i1222_8_, double p_i1222_10_, double p_i1222_12_, CallbackInfo ci) {
        // green & red get multiplied in constructor, blue doesn't
        this.particleGreen = this.portalParticleScale / 0.3f;
        this.particleGreen *= ColorizeEntity.portalColor[1];
        this.particleRed = this.portalParticleScale / 0.9f;
        this.particleRed *= ColorizeEntity.portalColor[0];
        this.particleBlue = ColorizeEntity.portalColor[2];
    }
}
