package mist475.mcpatcherforge.mixins.early.client.particle;

import net.minecraft.client.particle.EntityAuraFX;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.world.World;

import org.spongepowered.asm.mixin.Mixin;

import com.prupe.mcpatcher.cc.ColorizeEntity;
import com.prupe.mcpatcher.cc.Colorizer;

import mist475.mcpatcherforge.mixins.interfaces.EntityAuraFXExpansion;

@Mixin(EntityAuraFX.class)
public abstract class MixinEntityAuraFX extends EntityFX implements EntityAuraFXExpansion {

    protected MixinEntityAuraFX(World p_i1218_1_, double p_i1218_2_, double p_i1218_4_, double p_i1218_6_) {
        super(p_i1218_1_, p_i1218_2_, p_i1218_4_, p_i1218_6_);
    }

    @SuppressWarnings("DataFlowIssue")
    @Override
    public EntityAuraFX colorize() {
        if (ColorizeEntity.computeMyceliumParticleColor()) {
            this.particleRed = Colorizer.setColor[0];
            this.particleGreen = Colorizer.setColor[1];
            this.particleBlue = Colorizer.setColor[2];
        }
        return (EntityAuraFX) (Object) this;
    }
}
