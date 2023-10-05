package mist475.mcpatcherforge.mixins.early;

import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.WorldProvider;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import com.prupe.mcpatcher.cc.ColorizeWorld;
import com.prupe.mcpatcher.cc.Colorizer;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@Mixin(WorldProvider.class)
public abstract class MixinWorldProvider {

    /**
     * @author Mist475 (adapted from Paul Rupe)
     * @reason code block wrapped in if
     */
    @SideOnly(Side.CLIENT)
    @Overwrite
    public Vec3 getFogColor(float p_76562_1_, float p_76562_2_) {
        float f2 = MathHelper.cos(p_76562_1_ * (float) Math.PI * 2.0F) * 2.0F + 0.5F;

        if (f2 < 0.0F) {
            f2 = 0.0F;
        }

        if (f2 > 1.0F) {
            f2 = 1.0F;
        }

        float f3 = 0.7529412F;
        float f4 = 0.84705883F;
        float f5 = 1.0F;
        // patch start
        if (ColorizeWorld.computeFogColor((WorldProvider) (Object) this, p_76562_1_)) {
            f3 = Colorizer.setColor[0];
            f4 = Colorizer.setColor[1];
            f5 = Colorizer.setColor[2];
        }
        return Vec3.createVectorHelper(f3 * (f2 * 0.94f + 0.06f), f4 * (f2 * 0.94f + 0.06f), f5 * (f2 * 0.91f + 0.09f));
        // patch end
    }
}
