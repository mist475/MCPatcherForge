package mist475.mcpatcherforge.mixins.early.world;

import net.minecraft.util.Vec3;
import net.minecraft.world.WorldProviderEnd;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import com.prupe.mcpatcher.cc.ColorizeWorld;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@Mixin(WorldProviderEnd.class)
public abstract class MixinWorldProviderEnd {

    /**
     * @author Mist475 (adpated from Paul Rupe)
     * @reason customized value
     */
    @SideOnly(Side.CLIENT)
    @Overwrite
    public Vec3 getFogColor(float p_76562_1_, float p_76562_2_) {
        return Vec3.createVectorHelper(
            ColorizeWorld.endFogColor[0],
            ColorizeWorld.endFogColor[1],
            ColorizeWorld.endFogColor[2]);
    }
}
