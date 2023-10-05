package mist475.mcpatcherforge.mixins.early.Particle;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.particle.EntitySuspendFX;
import net.minecraft.world.World;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.prupe.mcpatcher.cc.ColorizeEntity;
import com.prupe.mcpatcher.cc.Colorizer;

@Mixin(EntitySuspendFX.class)
public abstract class MixinEntitySuspendFX extends EntityFX {

    protected MixinEntitySuspendFX(World p_i1218_1_, double p_i1218_2_, double p_i1218_4_, double p_i1218_6_) {
        super(p_i1218_1_, p_i1218_2_, p_i1218_4_, p_i1218_6_);
    }

    @Inject(method = "<init>(Lnet/minecraft/world/World;DDDDDD)V", at = @At("RETURN"))
    private void modifyConstructor(World p_i1231_1_, double p_i1231_2_, double p_i1231_4_, double p_i1231_6_,
        double p_i1231_8_, double p_i1231_10_, double p_i1231_12_, CallbackInfo ci) {
        ColorizeEntity.computeSuspendColor(6710962, (int) p_i1231_2_, (int) p_i1231_4_, (int) p_i1231_6_);
        this.particleRed = Colorizer.setColor[0];
        this.particleGreen = Colorizer.setColor[1];
        this.particleBlue = Colorizer.setColor[2];
    }
}
