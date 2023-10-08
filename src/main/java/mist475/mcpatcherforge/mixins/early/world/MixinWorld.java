package mist475.mcpatcherforge.mixins.early.world;

import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.client.ForgeHooksClient;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import com.prupe.mcpatcher.cc.ColorizeWorld;
import com.prupe.mcpatcher.cc.Colorizer;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@Mixin(World.class)
public abstract class MixinWorld {

    @Shadow
    public int lastLightningBolt;

    @Shadow
    public abstract float getCelestialAngle(float p_72826_1_);

    @Shadow
    public abstract float getRainStrength(float p_72867_1_);

    @Shadow
    public abstract float getWeightedThunderStrength(float p_72819_1_);

    /**
     * @author Mist475 (adapted from Paul Rupe)
     * @reason Forge changes this, so it looks different than my decomp
     *         TODO: look at again
     */
    @SideOnly(Side.CLIENT)
    @Overwrite(remap = false)
    public Vec3 getSkyColorBody(Entity entity, float p_72833_2_) {
        float f1 = this.getCelestialAngle(p_72833_2_);
        float f2 = MathHelper.cos(f1 * (float) Math.PI * 2.0F) * 2.0F + 0.5F;

        if (f2 < 0.0F) {
            f2 = 0.0F;
        }

        if (f2 > 1.0F) {
            f2 = 1.0F;
        }

        int i = MathHelper.floor_double(entity.posX);
        int j = MathHelper.floor_double(entity.posY);
        int k = MathHelper.floor_double(entity.posZ);
        // TODO: figure out if this works or if it needs to be removed or reversed
        // forge patch
        int l = ForgeHooksClient.getSkyBlendColour((World) (Object) this, i, j, k);
        // patch start
        ColorizeWorld.setupForFog(entity);
        float f4;
        float f5;
        float f6;
        if (ColorizeWorld.computeSkyColor((World) (Object) this, p_72833_2_)) {
            f4 = Colorizer.setColor[0];
            f5 = Colorizer.setColor[1];
            f6 = Colorizer.setColor[2];
        } else {
            f4 = (l >> 16 & 0xFF) / 255.0f;
            f5 = (l >> 8 & 0xFF) / 255.0f;
            f6 = (l & 0xFF) / 255.0f;
        }
        // patch end?
        f4 *= f2;
        f5 *= f2;
        f6 *= f2;
        float f7 = this.getRainStrength(p_72833_2_);
        float f8;
        float f9;

        if (f7 > 0.0F) {
            f8 = (f4 * 0.3F + f5 * 0.59F + f6 * 0.11F) * 0.6F;
            f9 = 1.0F - f7 * 0.75F;
            f4 = f4 * f9 + f8 * (1.0F - f9);
            f5 = f5 * f9 + f8 * (1.0F - f9);
            f6 = f6 * f9 + f8 * (1.0F - f9);
        }

        f8 = this.getWeightedThunderStrength(p_72833_2_);

        if (f8 > 0.0F) {
            f9 = (f4 * 0.3F + f5 * 0.59F + f6 * 0.11F) * 0.2F;
            float f10 = 1.0F - f8 * 0.75F;
            f4 = f4 * f10 + f9 * (1.0F - f10);
            f5 = f5 * f10 + f9 * (1.0F - f10);
            f6 = f6 * f10 + f9 * (1.0F - f10);
        }

        if (this.lastLightningBolt > 0) {
            f9 = (float) this.lastLightningBolt - p_72833_2_;

            if (f9 > 1.0F) {
                f9 = 1.0F;
            }

            f9 *= 0.45F;
            f4 = f4 * (1.0F - f9) + 0.8F * f9;
            f5 = f5 * (1.0F - f9) + 0.8F * f9;
            f6 = f6 * (1.0F - f9) + 1.0F * f9;
        }

        return Vec3.createVectorHelper(f4, f5, f6);
    }
}
