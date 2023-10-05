package mist475.mcpatcherforge.mixins.early.Block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRedstoneWire;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import com.prupe.mcpatcher.cc.ColorizeBlock;
import com.prupe.mcpatcher.cc.Colorizer;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@Mixin(BlockRedstoneWire.class)
public abstract class MixinBlockRedstoneWire {

    /**
     * @author Mist475 (adapted from Paul Rupe)
     * @reason incompatible anyway
     */
    @SideOnly(Side.CLIENT)
    @Overwrite
    public int colorMultiplier(IBlockAccess worldIn, int x, int y, int z) {
        if (ColorizeBlock.colorizeBlock((Block) (Object) this, worldIn, x, y, z)) {
            return ColorizeBlock.blockColor;
        }
        return ColorizeBlock.colorizeRedstoneWire(worldIn, x, y, z, 8388608);
    }

    /**
     * @author Paul Rupe
     * @reason if-else modified
     */
    @SideOnly(Side.CLIENT)
    @Overwrite
    public void randomDisplayTick(World worldIn, int x, int y, int z, Random random) {
        int l = worldIn.getBlockMetadata(x, y, z);

        if (l > 0) {
            double d0 = (double) x + 0.5D + ((double) random.nextFloat() - 0.5D) * 0.2D;
            double d1 = (float) y + 0.0625F;
            double d2 = (double) z + 0.5D + ((double) random.nextFloat() - 0.5D) * 0.2D;

            float n7;
            float n8;
            float n9;
            // Patch start
            if (ColorizeBlock.computeRedstoneWireColor(l)) {
                n7 = Colorizer.setColor[0];
                n8 = Colorizer.setColor[1];
                n9 = Colorizer.setColor[2];
            } else {
                // Patch end (else was if)
                final float n10 = l / 15.0f;
                n7 = n10 * 0.6f + 0.4f;
                n8 = n10 * n10 * 0.7f - 0.5f;
                n9 = n10 * n10 * 0.6f - 0.7f;
            }
            if (n8 < 0.0f) {
                n8 = 0.0f;
            }
            if (n9 < 0.0f) {
                n9 = 0.0f;
            }

            worldIn.spawnParticle("reddust", d0, d1, d2, n7, n8, n9);
        }
    }
}
