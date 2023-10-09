package mist475.mcpatcherforge.mixins.early.client.renderer.RenderBlocks;

import static net.minecraftforge.common.util.ForgeDirection.EAST;
import static net.minecraftforge.common.util.ForgeDirection.NORTH;
import static net.minecraftforge.common.util.ForgeDirection.SOUTH;
import static net.minecraftforge.common.util.ForgeDirection.WEST;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAnvil;
import net.minecraft.block.BlockBrewingStand;
import net.minecraft.block.BlockCauldron;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.BlockFlowerPot;
import net.minecraft.block.BlockGrass;
import net.minecraft.block.BlockHopper;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockPane;
import net.minecraft.block.BlockRailBase;
import net.minecraft.block.BlockRedstoneDiode;
import net.minecraft.block.BlockRedstoneWire;
import net.minecraft.block.BlockStainedGlassPane;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.llamalad7.mixinextras.injector.WrapWithCondition;
import com.prupe.mcpatcher.cc.ColorizeBlock;
import com.prupe.mcpatcher.cc.Colorizer;
import com.prupe.mcpatcher.ctm.CTMUtils;
import com.prupe.mcpatcher.ctm.GlassPaneRenderer;
import com.prupe.mcpatcher.mal.block.RenderBlocksUtils;
import com.prupe.mcpatcher.renderpass.RenderPass;

@Mixin(RenderBlocks.class)
public abstract class MixinRenderBlocks {

    @Shadow
    public IBlockAccess blockAccess;
    @Shadow
    public IIcon overrideBlockTexture;
    @Shadow
    public boolean renderAllFaces;
    @Shadow
    public static boolean fancyGrass;
    @Shadow
    public double renderMinX;
    @Shadow
    public double renderMaxX;
    @Shadow
    public double renderMinY;
    @Shadow
    public double renderMaxY;
    @Shadow
    public double renderMinZ;
    @Shadow
    public double renderMaxZ;
    @Shadow
    public boolean enableAO;
    @Shadow
    public float aoLightValueScratchXYZNNN;
    @Shadow
    public float aoLightValueScratchXYNN;
    @Shadow
    public float aoLightValueScratchXYZNNP;
    @Shadow
    public float aoLightValueScratchYZNN;
    @Shadow
    public float aoLightValueScratchYZNP;
    @Shadow
    public float aoLightValueScratchXYZPNN;
    @Shadow
    public float aoLightValueScratchXYPN;
    @Shadow
    public float aoLightValueScratchXYZPNP;
    @Shadow
    public float aoLightValueScratchXYZNPN;
    @Shadow
    public float aoLightValueScratchXYNP;
    @Shadow
    public float aoLightValueScratchXYZNPP;
    @Shadow
    public float aoLightValueScratchYZPN;
    @Shadow
    public float aoLightValueScratchXYZPPN;
    @Shadow
    public float aoLightValueScratchXYPP;
    @Shadow
    public float aoLightValueScratchYZPP;
    @Shadow
    public float aoLightValueScratchXYZPPP;
    @Shadow
    public float aoLightValueScratchXZNN;
    @Shadow
    public float aoLightValueScratchXZPN;
    @Shadow
    public float aoLightValueScratchXZNP;
    @Shadow
    public float aoLightValueScratchXZPP;
    @Shadow
    public int aoBrightnessXYZNNN;
    @Shadow
    public int aoBrightnessXYNN;
    @Shadow
    public int aoBrightnessXYZNNP;
    @Shadow
    public int aoBrightnessYZNN;
    @Shadow
    public int aoBrightnessYZNP;
    @Shadow
    public int aoBrightnessXYZPNN;
    @Shadow
    public int aoBrightnessXYPN;
    @Shadow
    public int aoBrightnessXYZPNP;
    @Shadow
    public int aoBrightnessXYZNPN;
    @Shadow
    public int aoBrightnessXYNP;
    @Shadow
    public int aoBrightnessXYZNPP;
    @Shadow
    public int aoBrightnessYZPN;
    @Shadow
    public int aoBrightnessXYZPPN;
    @Shadow
    public int aoBrightnessXYPP;
    @Shadow
    public int aoBrightnessYZPP;
    @Shadow
    public int aoBrightnessXYZPPP;
    @Shadow
    public int aoBrightnessXZNN;
    @Shadow
    public int aoBrightnessXZPN;
    @Shadow
    public int aoBrightnessXZNP;
    @Shadow
    public int aoBrightnessXZPP;
    @Shadow
    public int brightnessTopLeft;
    @Shadow
    public int brightnessBottomLeft;
    @Shadow
    public int brightnessBottomRight;
    @Shadow
    public int brightnessTopRight;

    @Shadow
    public float colorRedTopLeft;
    @Shadow
    public float colorRedBottomLeft;
    @Shadow
    public float colorRedBottomRight;
    @Shadow
    public float colorRedTopRight;
    @Shadow
    public float colorGreenTopLeft;
    @Shadow
    public float colorGreenBottomLeft;
    @Shadow
    public float colorGreenBottomRight;
    @Shadow
    public float colorGreenTopRight;
    @Shadow
    public float colorBlueTopLeft;
    @Shadow
    public float colorBlueBottomLeft;
    @Shadow
    public float colorBlueBottomRight;
    @Shadow
    public float colorBlueTopRight;

    @Shadow
    public abstract IIcon getBlockIcon(Block block, IBlockAccess access, int x, int y, int z, int side);

    @Shadow
    public abstract IIcon getBlockIconFromSideAndMetadata(Block block, int side, int meta);

    @Shadow
    public abstract IIcon getBlockIconFromSide(Block block, int side);

    @Shadow
    public abstract boolean hasOverrideBlockTexture();

    @Shadow
    public abstract int getAoBrightness(int p_147778_1_, int p_147778_2_, int p_147778_3_, int p_147778_4_);

    @Shadow
    public abstract int mixAoBrightness(int p_147727_1_, int p_147727_2_, int p_147727_3_, int p_147727_4_,
        double p_147727_5_, double p_147727_7_, double p_147727_9_, double p_147727_11_);

    @Shadow
    public abstract void renderFaceYNeg(Block block, double x, double y, double z, IIcon texture);

    @Shadow
    public abstract void renderFaceYPos(Block block, double x, double y, double z, IIcon texture);

    @Shadow
    public abstract void renderFaceZNeg(Block block, double x, double y, double z, IIcon texture);

    @Shadow
    abstract public void renderFaceZPos(Block block, double x, double y, double z, IIcon texture);

    @Shadow
    public abstract void renderFaceXNeg(Block block, double x, double y, double z, IIcon texture);

    @Shadow
    public abstract void renderFaceXPos(Block block, double x, double y, double z, IIcon texture);

    @Shadow
    public abstract IIcon getIconSafe(IIcon texture);

    @Redirect(
        method = "renderBlockMinecartTrack(Lnet/minecraft/block/BlockRailBase;III)Z",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/renderer/RenderBlocks;getBlockIconFromSideAndMetadata(Lnet/minecraft/block/Block;II)Lnet/minecraft/util/IIcon;"))
    private IIcon modifyRenderBlockMinecartTrack(RenderBlocks instance, Block block, int side, int meta,
        BlockRailBase specializedBlock, int x, int y, int z) {
        return (this.blockAccess == null) ? this.getBlockIconFromSideAndMetadata(block, side, meta)
            : this.getBlockIcon(block, this.blockAccess, x, y, z, side);
    }

    @Redirect(
        method = { "renderBlockVine(Lnet/minecraft/block/Block;III)Z",
            "renderBlockLilyPad(Lnet/minecraft/block/Block;III)Z", "renderBlockLadder(Lnet/minecraft/block/Block;III)Z",
            "renderBlockTripWireSource(Lnet/minecraft/block/Block;III)Z",
            "renderBlockLever(Lnet/minecraft/block/Block;III)Z", "renderBlockTripWire(Lnet/minecraft/block/Block;III)Z",
             },
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/renderer/RenderBlocks;getBlockIconFromSide(Lnet/minecraft/block/Block;I)Lnet/minecraft/util/IIcon;"))
    private IIcon redirectGetBlockIconFromSide(RenderBlocks instance, Block block, int side, Block specializedBlock,
        int x, int y, int z) {
        return (this.blockAccess == null) ? this.getBlockIconFromSide(block, side)
            : this.getBlockIcon(block, this.blockAccess, x, y, z, side);
    }

    @Redirect(
        method = "renderBlockBed(Lnet/minecraft/block/Block;III)Z",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/block/Block;shouldSideBeRendered(Lnet/minecraft/world/IBlockAccess;IIII)Z"))
    private boolean modifyRenderBlockBed(Block block, IBlockAccess worldIn, int x, int y, int z, int side) {
        return RenderPass.shouldSideBeRendered(block, worldIn, x, y, z, side);
    }

    @Redirect(
        method = "renderBlockBrewingStand(Lnet/minecraft/block/BlockBrewingStand;III)Z",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/renderer/RenderBlocks;getBlockIconFromSideAndMetadata(Lnet/minecraft/block/Block;II)Lnet/minecraft/util/IIcon;"))
    private IIcon modifyRenderBlockBrewingStand(RenderBlocks instance, Block block, int side, int meta,
        BlockBrewingStand specializedBlock, int x, int y, int z) {
        return (this.blockAccess == null) ? this.getBlockIconFromSideAndMetadata(block, side, meta)
            : this.getBlockIcon(block, this.blockAccess, x, y, z, side);
    }

    @Inject(
        method = "renderBlockCauldron(Lnet/minecraft/block/BlockCauldron;III)Z",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/block/BlockCauldron;getBlockTextureFromSide(I)Lnet/minecraft/util/IIcon;",
            shift = At.Shift.AFTER))
    private void modifyRenderBlockCauldron1(BlockCauldron block, int x, int y, int z,
        CallbackInfoReturnable<Boolean> cir) {
        ColorizeBlock.computeWaterColor();
        Tessellator.instance.setColorOpaque_F(Colorizer.setColor[0], Colorizer.setColor[1], Colorizer.setColor[2]);
    }

    @Inject(
        method = "renderBlockCauldron(Lnet/minecraft/block/BlockCauldron;III)Z",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/block/BlockLiquid;getLiquidIcon(Ljava/lang/String;)Lnet/minecraft/util/IIcon;"))
    private void modifyRenderBlockCauldron2(BlockCauldron block, int x, int y, int z,
        CallbackInfoReturnable<Boolean> cir) {
        ColorizeBlock.computeWaterColor();
        Tessellator.instance.setColorOpaque_F(Colorizer.setColor[0], Colorizer.setColor[1], Colorizer.setColor[2]);
    }

    @Redirect(
        method = "renderBlockFlowerpot(Lnet/minecraft/block/BlockFlowerPot;III)Z",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/renderer/RenderBlocks;getBlockIconFromSide(Lnet/minecraft/block/Block;I)Lnet/minecraft/util/IIcon;"))
    private IIcon modifyRenderBlockFlowerpot(RenderBlocks instance, Block block, int side,
        BlockFlowerPot specializedBlock, int x, int y, int z) {
        return (this.blockAccess == null) ? this.getBlockIconFromSide(block, side)
            : this.getBlockIcon(block, this.blockAccess, x, y, z, side);
    }

    @Redirect(
        method = "renderBlockAnvilRotate(Lnet/minecraft/block/BlockAnvil;IIIIFFFFZZI)F",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/renderer/RenderBlocks;getBlockIconFromSideAndMetadata(Lnet/minecraft/block/Block;II)Lnet/minecraft/util/IIcon;"))
    private IIcon modifyRenderBlockAnvilRotate(RenderBlocks instance, Block block, int side, int meta,
        BlockAnvil specializedBlock, int x, int y, int z) {
        return (this.blockAccess == null) ? this.getBlockIconFromSideAndMetadata(block, side, meta)
            : this.getBlockIcon(block, this.blockAccess, x, y, z, side);
    }

    @Redirect(
        method = "renderBlockRedstoneDiodeMetadata(Lnet/minecraft/block/BlockRedstoneDiode;IIII)V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/renderer/RenderBlocks;getBlockIconFromSideAndMetadata(Lnet/minecraft/block/Block;II)Lnet/minecraft/util/IIcon;"))
    private IIcon modifyRenderRedstoneDiodeMetadata(RenderBlocks instance, Block block, int side, int meta,
        BlockRedstoneDiode specializedBlock, int x, int y, int z) {
        return (this.blockAccess == null) ? this.getBlockIconFromSideAndMetadata(block, side, meta)
            : this.getBlockIcon(block, this.blockAccess, x, y, z, side);
    }

    /**
     * @author Mist475 (adapted from Paul Rupe)
     * @reason I forgor
     */
    @SuppressWarnings({ "DataFlowIssue", "DuplicatedCode" })
    @Overwrite
    public boolean renderBlockRedstoneWire(Block block, int x, int y, int z) {
        Tessellator tessellator = Tessellator.instance;
        int l = this.blockAccess.getBlockMetadata(x, y, z);
        IIcon iicon = BlockRedstoneWire.getRedstoneWireIcon("cross");
        IIcon iicon1 = BlockRedstoneWire.getRedstoneWireIcon("line");
        IIcon iicon2 = BlockRedstoneWire.getRedstoneWireIcon("cross_overlay");
        IIcon iicon3 = BlockRedstoneWire.getRedstoneWireIcon("line_overlay");
        tessellator.setBrightness(block.getMixedBrightnessForBlock(this.blockAccess, x, y, z));

        // patch start
        float f1;
        float f2;
        float f3;
        if (ColorizeBlock.computeRedstoneWireColor(l)) {
            f1 = Colorizer.setColor[0];
            f2 = Colorizer.setColor[1];
            f3 = Colorizer.setColor[2];
        } else {
            float n7 = l / 15.0f;
            f1 = n7 * 0.6f + 0.4f;
            if (l == 0) {
                f1 = 0.3f;
            }
            f2 = n7 * n7 * 0.7f - 0.5f;
            f3 = n7 * n7 * 0.6f - 0.7f;
        }
        // patch end
        if (f2 < 0.0F) {
            f2 = 0.0F;
        }

        if (f3 < 0.0F) {
            f3 = 0.0F;
        }

        tessellator.setColorOpaque_F(f1, f2, f3);
        boolean flag = BlockRedstoneWire.isPowerProviderOrWire(this.blockAccess, x - 1, y, z, 1)
            || !this.blockAccess.getBlock(x - 1, y, z)
                .isBlockNormalCube() && BlockRedstoneWire.isPowerProviderOrWire(this.blockAccess, x - 1, y - 1, z, -1);
        boolean flag1 = BlockRedstoneWire.isPowerProviderOrWire(this.blockAccess, x + 1, y, z, 3)
            || !this.blockAccess.getBlock(x + 1, y, z)
                .isBlockNormalCube() && BlockRedstoneWire.isPowerProviderOrWire(this.blockAccess, x + 1, y - 1, z, -1);
        boolean flag2 = BlockRedstoneWire.isPowerProviderOrWire(this.blockAccess, x, y, z - 1, 2)
            || !this.blockAccess.getBlock(x, y, z - 1)
                .isBlockNormalCube() && BlockRedstoneWire.isPowerProviderOrWire(this.blockAccess, x, y - 1, z - 1, -1);
        boolean flag3 = BlockRedstoneWire.isPowerProviderOrWire(this.blockAccess, x, y, z + 1, 0)
            || !this.blockAccess.getBlock(x, y, z + 1)
                .isBlockNormalCube() && BlockRedstoneWire.isPowerProviderOrWire(this.blockAccess, x, y - 1, z + 1, -1);

        if (!this.blockAccess.getBlock(x, y + 1, z)
            .isBlockNormalCube()) {
            if (this.blockAccess.getBlock(x - 1, y, z)
                .isBlockNormalCube()
                && BlockRedstoneWire.isPowerProviderOrWire(this.blockAccess, x - 1, y + 1, z, -1)) {
                flag = true;
            }

            if (this.blockAccess.getBlock(x + 1, y, z)
                .isBlockNormalCube()
                && BlockRedstoneWire.isPowerProviderOrWire(this.blockAccess, x + 1, y + 1, z, -1)) {
                flag1 = true;
            }

            if (this.blockAccess.getBlock(x, y, z - 1)
                .isBlockNormalCube()
                && BlockRedstoneWire.isPowerProviderOrWire(this.blockAccess, x, y + 1, z - 1, -1)) {
                flag2 = true;
            }

            if (this.blockAccess.getBlock(x, y, z + 1)
                .isBlockNormalCube()
                && BlockRedstoneWire.isPowerProviderOrWire(this.blockAccess, x, y + 1, z + 1, -1)) {
                flag3 = true;
            }
        }

        float f4 = (float) (x);
        float f5 = (float) (x + 1);
        float f6 = (float) (z);
        float f7 = (float) (z + 1);
        int i1 = 0;

        if ((flag || flag1) && !flag2 && !flag3) {
            i1 = 1;
        }

        if ((flag2 || flag3) && !flag1 && !flag) {
            i1 = 2;
        }

        if (i1 == 0) {
            int j1 = 0;
            int k1 = 0;
            int l1 = 16;
            int i2 = 16;

            if (!flag) {
                f4 += 0.3125F;
            }

            if (!flag) {
                j1 += 5;
            }

            if (!flag1) {
                f5 -= 0.3125F;
            }

            if (!flag1) {
                l1 -= 5;
            }

            if (!flag2) {
                f6 += 0.3125F;
            }

            if (!flag2) {
                k1 += 5;
            }

            if (!flag3) {
                f7 -= 0.3125F;
            }

            if (!flag3) {
                i2 -= 5;
            }

            tessellator.addVertexWithUV(
                f5,
                (double) y + 0.015625D,
                f7,
                iicon.getInterpolatedU(l1),
                iicon.getInterpolatedV(i2));
            tessellator.addVertexWithUV(
                f5,
                (double) y + 0.015625D,
                f6,
                iicon.getInterpolatedU(l1),
                iicon.getInterpolatedV(k1));
            tessellator.addVertexWithUV(
                f4,
                (double) y + 0.015625D,
                f6,
                iicon.getInterpolatedU(j1),
                iicon.getInterpolatedV(k1));
            tessellator.addVertexWithUV(
                f4,
                (double) y + 0.015625D,
                f7,
                iicon.getInterpolatedU(j1),
                iicon.getInterpolatedV(i2));
            tessellator.setColorOpaque_F(1.0F, 1.0F, 1.0F);
            tessellator.addVertexWithUV(
                f5,
                (double) y + 0.015625D,
                f7,
                iicon2.getInterpolatedU(l1),
                iicon2.getInterpolatedV(i2));
            tessellator.addVertexWithUV(
                f5,
                (double) y + 0.015625D,
                f6,
                iicon2.getInterpolatedU(l1),
                iicon2.getInterpolatedV(k1));
            tessellator.addVertexWithUV(
                f4,
                (double) y + 0.015625D,
                f6,
                iicon2.getInterpolatedU(j1),
                iicon2.getInterpolatedV(k1));
            tessellator.addVertexWithUV(
                f4,
                (double) y + 0.015625D,
                f7,
                iicon2.getInterpolatedU(j1),
                iicon2.getInterpolatedV(i2));
        } else if (i1 == 1) {
            tessellator.addVertexWithUV(f5, (double) y + 0.015625D, f7, iicon1.getMaxU(), iicon1.getMaxV());
            tessellator.addVertexWithUV(f5, (double) y + 0.015625D, f6, iicon1.getMaxU(), iicon1.getMinV());
            tessellator.addVertexWithUV(f4, (double) y + 0.015625D, f6, iicon1.getMinU(), iicon1.getMinV());
            tessellator.addVertexWithUV(f4, (double) y + 0.015625D, f7, iicon1.getMinU(), iicon1.getMaxV());
            tessellator.setColorOpaque_F(1.0F, 1.0F, 1.0F);
            tessellator.addVertexWithUV(f5, (double) y + 0.015625D, f7, iicon3.getMaxU(), iicon3.getMaxV());
            tessellator.addVertexWithUV(f5, (double) y + 0.015625D, f6, iicon3.getMaxU(), iicon3.getMinV());
            tessellator.addVertexWithUV(f4, (double) y + 0.015625D, f6, iicon3.getMinU(), iicon3.getMinV());
            tessellator.addVertexWithUV(f4, (double) y + 0.015625D, f7, iicon3.getMinU(), iicon3.getMaxV());
        } else {
            tessellator.addVertexWithUV(f5, (double) y + 0.015625D, f7, iicon1.getMaxU(), iicon1.getMaxV());
            tessellator.addVertexWithUV(f5, (double) y + 0.015625D, f6, iicon1.getMinU(), iicon1.getMaxV());
            tessellator.addVertexWithUV(f4, (double) y + 0.015625D, f6, iicon1.getMinU(), iicon1.getMinV());
            tessellator.addVertexWithUV(f4, (double) y + 0.015625D, f7, iicon1.getMaxU(), iicon1.getMinV());
            tessellator.setColorOpaque_F(1.0F, 1.0F, 1.0F);
            tessellator.addVertexWithUV(f5, (double) y + 0.015625D, f7, iicon3.getMaxU(), iicon3.getMaxV());
            tessellator.addVertexWithUV(f5, (double) y + 0.015625D, f6, iicon3.getMinU(), iicon3.getMaxV());
            tessellator.addVertexWithUV(f4, (double) y + 0.015625D, f6, iicon3.getMinU(), iicon3.getMinV());
            tessellator.addVertexWithUV(f4, (double) y + 0.015625D, f7, iicon3.getMaxU(), iicon3.getMinV());
        }

        if (!this.blockAccess.getBlock(x, y + 1, z)
            .isBlockNormalCube()) {

            if (this.blockAccess.getBlock(x - 1, y, z)
                .isBlockNormalCube() && this.blockAccess.getBlock(x - 1, y + 1, z) == Blocks.redstone_wire) {
                tessellator.setColorOpaque_F(f1, f2, f3);
                tessellator.addVertexWithUV(
                    (double) x + 0.015625D,
                    (float) (y + 1) + 0.021875F,
                    z + 1,
                    iicon1.getMaxU(),
                    iicon1.getMinV());
                tessellator.addVertexWithUV((double) x + 0.015625D, y, z + 1, iicon1.getMinU(), iicon1.getMinV());
                tessellator.addVertexWithUV((double) x + 0.015625D, y, z, iicon1.getMinU(), iicon1.getMaxV());
                tessellator.addVertexWithUV(
                    (double) x + 0.015625D,
                    (float) (y + 1) + 0.021875F,
                    z,
                    iicon1.getMaxU(),
                    iicon1.getMaxV());
                tessellator.setColorOpaque_F(1.0F, 1.0F, 1.0F);
                tessellator.addVertexWithUV(
                    (double) x + 0.015625D,
                    (float) (y + 1) + 0.021875F,
                    z + 1,
                    iicon3.getMaxU(),
                    iicon3.getMinV());
                tessellator.addVertexWithUV((double) x + 0.015625D, y, z + 1, iicon3.getMinU(), iicon3.getMinV());
                tessellator.addVertexWithUV((double) x + 0.015625D, y, z, iicon3.getMinU(), iicon3.getMaxV());
                tessellator.addVertexWithUV(
                    (double) x + 0.015625D,
                    (float) (y + 1) + 0.021875F,
                    z,
                    iicon3.getMaxU(),
                    iicon3.getMaxV());
            }

            if (this.blockAccess.getBlock(x + 1, y, z)
                .isBlockNormalCube() && this.blockAccess.getBlock(x + 1, y + 1, z) == Blocks.redstone_wire) {
                tessellator.setColorOpaque_F(f1, f2, f3);
                tessellator.addVertexWithUV((double) (x + 1) - 0.015625D, y, z + 1, iicon1.getMinU(), iicon1.getMaxV());
                tessellator.addVertexWithUV(
                    (double) (x + 1) - 0.015625D,
                    (float) (y + 1) + 0.021875F,
                    z + 1,
                    iicon1.getMaxU(),
                    iicon1.getMaxV());
                tessellator.addVertexWithUV(
                    (double) (x + 1) - 0.015625D,
                    (float) (y + 1) + 0.021875F,
                    z,
                    iicon1.getMaxU(),
                    iicon1.getMinV());
                tessellator.addVertexWithUV((double) (x + 1) - 0.015625D, y, z, iicon1.getMinU(), iicon1.getMinV());
                tessellator.setColorOpaque_F(1.0F, 1.0F, 1.0F);
                tessellator.addVertexWithUV((double) (x + 1) - 0.015625D, y, z + 1, iicon3.getMinU(), iicon3.getMaxV());
                tessellator.addVertexWithUV(
                    (double) (x + 1) - 0.015625D,
                    (float) (y + 1) + 0.021875F,
                    z + 1,
                    iicon3.getMaxU(),
                    iicon3.getMaxV());
                tessellator.addVertexWithUV(
                    (double) (x + 1) - 0.015625D,
                    (float) (y + 1) + 0.021875F,
                    z,
                    iicon3.getMaxU(),
                    iicon3.getMinV());
                tessellator.addVertexWithUV((double) (x + 1) - 0.015625D, y, z, iicon3.getMinU(), iicon3.getMinV());
            }

            if (this.blockAccess.getBlock(x, y, z - 1)
                .isBlockNormalCube() && this.blockAccess.getBlock(x, y + 1, z - 1) == Blocks.redstone_wire) {
                tessellator.setColorOpaque_F(f1, f2, f3);
                tessellator.addVertexWithUV(x + 1, y, (double) z + 0.015625D, iicon1.getMinU(), iicon1.getMaxV());
                tessellator.addVertexWithUV(
                    x + 1,
                    (float) (y + 1) + 0.021875F,
                    (double) z + 0.015625D,
                    iicon1.getMaxU(),
                    iicon1.getMaxV());
                tessellator.addVertexWithUV(
                    x,
                    (float) (y + 1) + 0.021875F,
                    (double) z + 0.015625D,
                    iicon1.getMaxU(),
                    iicon1.getMinV());
                tessellator.addVertexWithUV(x, y, (double) z + 0.015625D, iicon1.getMinU(), iicon1.getMinV());
                tessellator.setColorOpaque_F(1.0F, 1.0F, 1.0F);
                tessellator.addVertexWithUV(x + 1, y, (double) z + 0.015625D, iicon3.getMinU(), iicon3.getMaxV());
                tessellator.addVertexWithUV(
                    x + 1,
                    (float) (y + 1) + 0.021875F,
                    (double) z + 0.015625D,
                    iicon3.getMaxU(),
                    iicon3.getMaxV());
                tessellator.addVertexWithUV(
                    x,
                    (float) (y + 1) + 0.021875F,
                    (double) z + 0.015625D,
                    iicon3.getMaxU(),
                    iicon3.getMinV());
                tessellator.addVertexWithUV(x, y, (double) z + 0.015625D, iicon3.getMinU(), iicon3.getMinV());
            }

            if (this.blockAccess.getBlock(x, y, z + 1)
                .isBlockNormalCube() && this.blockAccess.getBlock(x, y + 1, z + 1) == Blocks.redstone_wire) {
                tessellator.setColorOpaque_F(f1, f2, f3);
                tessellator.addVertexWithUV(
                    x + 1,
                    (float) (y + 1) + 0.021875F,
                    (double) (z + 1) - 0.015625D,
                    iicon1.getMaxU(),
                    iicon1.getMinV());
                tessellator.addVertexWithUV(x + 1, y, (double) (z + 1) - 0.015625D, iicon1.getMinU(), iicon1.getMinV());
                tessellator.addVertexWithUV(x, y, (double) (z + 1) - 0.015625D, iicon1.getMinU(), iicon1.getMaxV());
                tessellator.addVertexWithUV(
                    x,
                    (float) (y + 1) + 0.021875F,
                    (double) (z + 1) - 0.015625D,
                    iicon1.getMaxU(),
                    iicon1.getMaxV());
                tessellator.setColorOpaque_F(1.0F, 1.0F, 1.0F);
                tessellator.addVertexWithUV(
                    x + 1,
                    (float) (y + 1) + 0.021875F,
                    (double) (z + 1) - 0.015625D,
                    iicon3.getMaxU(),
                    iicon3.getMinV());
                tessellator.addVertexWithUV(x + 1, y, (double) (z + 1) - 0.015625D, iicon3.getMinU(), iicon3.getMinV());
                tessellator.addVertexWithUV(x, y, (double) (z + 1) - 0.015625D, iicon3.getMinU(), iicon3.getMaxV());
                tessellator.addVertexWithUV(
                    x,
                    (float) (y + 1) + 0.021875F,
                    (double) (z + 1) - 0.015625D,
                    iicon3.getMaxU(),
                    iicon3.getMaxV());
            }
        }

        return true;
    }

    @Redirect(
        method = "renderBlockLadder(Lnet/minecraft/block/Block;III)Z",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/renderer/RenderBlocks;getBlockIconFromSide(Lnet/minecraft/block/Block;I)Lnet/minecraft/util/IIcon;"))
    private IIcon modifyRenderBlockLadder(RenderBlocks instance, Block block, int side, Block specializedBlock, int x,
        int y, int z) {
        return (this.blockAccess == null) ? this.getBlockIconFromSide(block, side)
            : this.getBlockIcon(block, this.blockAccess, x, y, z, side);
    }

    @Redirect(
        method = "renderBlockVine(Lnet/minecraft/block/Block;III)Z",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/renderer/RenderBlocks;getBlockIconFromSide(Lnet/minecraft/block/Block;I)Lnet/minecraft/util/IIcon;"))
    private IIcon modifyRenderBlockVine(RenderBlocks instance, Block block, int side, Block specializedBlock, int x,
        int y, int z) {
        return (this.blockAccess == null) ? this.getBlockIconFromSide(block, side)
            : this.getBlockIcon(block, this.blockAccess, x, y, z, side);
    }

    /**
     * @author Mist475 (adapted from Paul Rupe)
     * @reason Significant deviation from Vanilla
     */
    @SuppressWarnings("DuplicatedCode")
    @Overwrite
    public boolean renderBlockStainedGlassPane(Block block, int x, int y, int z) {
        Tessellator tessellator = Tessellator.instance;
        tessellator.setBrightness(block.getMixedBrightnessForBlock(this.blockAccess, x, y, z));
        int i1 = block.colorMultiplier(this.blockAccess, x, y, z);
        float f = (float) (i1 >> 16 & 255) / 255.0F;
        float f1 = (float) (i1 >> 8 & 255) / 255.0F;
        float f2 = (float) (i1 & 255) / 255.0F;

        if (EntityRenderer.anaglyphEnable) {
            float f3 = (f * 30.0F + f1 * 59.0F + f2 * 11.0F) / 100.0F;
            float f4 = (f * 30.0F + f1 * 70.0F) / 100.0F;
            float f5 = (f * 30.0F + f2 * 70.0F) / 100.0F;
            f = f3;
            f1 = f4;
            f2 = f5;
        }

        tessellator.setColorOpaque_F(f, f1, f2);
        boolean flag5 = block instanceof BlockStainedGlassPane;
        IIcon iicon;
        IIcon iicon1;

        if (this.hasOverrideBlockTexture()) {
            iicon = this.overrideBlockTexture;
            iicon1 = this.overrideBlockTexture;
        } else {
            int j1 = this.blockAccess.getBlockMetadata(x, y, z);
            // Change 1
            iicon = ((this.blockAccess == null) ? this.getBlockIconFromSideAndMetadata(block, 0, j1)
                : this.getBlockIcon(block, this.blockAccess, x, y, z, 0));
            iicon1 = flag5 ? ((BlockStainedGlassPane) block).func_150104_b(j1) : ((BlockPane) block).func_150097_e();
        }
        // Change 2
        double n7 = iicon.getMinU();
        double n8 = iicon.getInterpolatedU(7.0);
        double n9 = iicon.getInterpolatedU(9.0);
        double n10 = iicon.getMaxU();
        double n11 = iicon.getMinV();
        double n12 = iicon.getMaxV();

        double n13 = iicon1.getInterpolatedU(7.0);
        double n14 = iicon1.getInterpolatedU(9.0);
        double n15 = iicon1.getMinV();
        double n16 = iicon1.getMaxV();
        double n17 = iicon1.getInterpolatedV(7.0);
        double n18 = iicon1.getInterpolatedV(9.0);

        double n20 = x + 1;
        double n22 = z + 1;
        double n23 = x + 0.5 - 0.0625;
        double n24 = x + 0.5 + 0.0625;
        double n25 = z + 0.5 - 0.0625;
        double n26 = z + 0.5 + 0.0625;

        boolean connectNorth = ((BlockPane) block).canPaneConnectTo(this.blockAccess, x, y, z - 1, NORTH);
        boolean connectSouth = ((BlockPane) block).canPaneConnectTo(this.blockAccess, x, y, z + 1, SOUTH);
        boolean connectWest = ((BlockPane) block).canPaneConnectTo(this.blockAccess, x - 1, y, z, WEST);
        boolean connectEast = ((BlockPane) block).canPaneConnectTo(this.blockAccess, x + 1, y, z, EAST);

        // Change 3
        GlassPaneRenderer.renderThick(
            (RenderBlocks) (Object) this,
            block,
            iicon,
            x,
            y,
            z,
            connectNorth,
            connectSouth,
            connectWest,
            connectEast);
        boolean flag4 = !connectNorth && !connectSouth && !connectWest && !connectEast;
        // Change 4 (full on replacement)
        if (connectWest || flag4) {
            if (connectWest && connectEast) {
                if (!connectNorth) {
                    if (!GlassPaneRenderer.skipPaneRendering) {
                        tessellator.addVertexWithUV(n20, y + 0.999, n25, n10, n11);
                        tessellator.addVertexWithUV(n20, y + 0.001, n25, n10, n12);
                        tessellator.addVertexWithUV(x, y + 0.001, n25, n7, n12);
                        tessellator.addVertexWithUV(x, y + 0.999, n25, n7, n11);
                    }
                } else if (!GlassPaneRenderer.skipPaneRendering) {
                    tessellator.addVertexWithUV(n23, y + 0.999, n25, n8, n11);
                    tessellator.addVertexWithUV(n23, y + 0.001, n25, n8, n12);
                    tessellator.addVertexWithUV(x, y + 0.001, n25, n7, n12);
                    tessellator.addVertexWithUV(x, y + 0.999, n25, n7, n11);
                    tessellator.addVertexWithUV(n20, y + 0.999, n25, n10, n11);
                    tessellator.addVertexWithUV(n20, y + 0.001, n25, n10, n12);
                    tessellator.addVertexWithUV(n24, y + 0.001, n25, n9, n12);
                    tessellator.addVertexWithUV(n24, y + 0.999, n25, n9, n11);
                }
                if (!connectSouth) {
                    if (!GlassPaneRenderer.skipPaneRendering) {
                        tessellator.addVertexWithUV(x, y + 0.999, n26, n7, n11);
                        tessellator.addVertexWithUV(x, y + 0.001, n26, n7, n12);
                        tessellator.addVertexWithUV(n20, y + 0.001, n26, n10, n12);
                        tessellator.addVertexWithUV(n20, y + 0.999, n26, n10, n11);
                    }
                } else if (!GlassPaneRenderer.skipPaneRendering) {
                    tessellator.addVertexWithUV(x, y + 0.999, n26, n7, n11);
                    tessellator.addVertexWithUV(x, y + 0.001, n26, n7, n12);
                    tessellator.addVertexWithUV(n23, y + 0.001, n26, n8, n12);
                    tessellator.addVertexWithUV(n23, y + 0.999, n26, n8, n11);
                    tessellator.addVertexWithUV(n24, y + 0.999, n26, n9, n11);
                    tessellator.addVertexWithUV(n24, y + 0.001, n26, n9, n12);
                    tessellator.addVertexWithUV(n20, y + 0.001, n26, n10, n12);
                    tessellator.addVertexWithUV(n20, y + 0.999, n26, n10, n11);
                }
                if (!GlassPaneRenderer.skipTopEdgeRendering) {
                    tessellator.addVertexWithUV(x, y + 0.999, n26, n14, n15);
                    tessellator.addVertexWithUV(n20, y + 0.999, n26, n14, n16);
                    tessellator.addVertexWithUV(n20, y + 0.999, n25, n13, n16);
                    tessellator.addVertexWithUV(x, y + 0.999, n25, n13, n15);
                }
                if (!GlassPaneRenderer.skipBottomEdgeRendering) {
                    tessellator.addVertexWithUV(n20, y + 0.001, n26, n13, n16);
                    tessellator.addVertexWithUV(x, y + 0.001, n26, n13, n15);
                    tessellator.addVertexWithUV(x, y + 0.001, n25, n14, n15);
                    tessellator.addVertexWithUV(n20, y + 0.001, n25, n14, n16);
                }
            } else {
                if (!connectNorth && !flag4) {
                    if (!GlassPaneRenderer.skipPaneRendering) {
                        tessellator.addVertexWithUV(n24, y + 0.999, n25, n9, n11);
                        tessellator.addVertexWithUV(n24, y + 0.001, n25, n9, n12);
                        tessellator.addVertexWithUV(x, y + 0.001, n25, n7, n12);
                        tessellator.addVertexWithUV(x, y + 0.999, n25, n7, n11);
                    }
                } else if (!GlassPaneRenderer.skipPaneRendering) {
                    tessellator.addVertexWithUV(n23, y + 0.999, n25, n8, n11);
                    tessellator.addVertexWithUV(n23, y + 0.001, n25, n8, n12);
                    tessellator.addVertexWithUV(x, y + 0.001, n25, n7, n12);
                    tessellator.addVertexWithUV(x, y + 0.999, n25, n7, n11);
                }
                if (!connectSouth && !flag4) {
                    if (!GlassPaneRenderer.skipPaneRendering) {
                        tessellator.addVertexWithUV(x, y + 0.999, n26, n7, n11);
                        tessellator.addVertexWithUV(x, y + 0.001, n26, n7, n12);
                        tessellator.addVertexWithUV(n24, y + 0.001, n26, n9, n12);
                        tessellator.addVertexWithUV(n24, y + 0.999, n26, n9, n11);
                    }
                } else if (!GlassPaneRenderer.skipPaneRendering) {
                    tessellator.addVertexWithUV(x, y + 0.999, n26, n7, n11);
                    tessellator.addVertexWithUV(x, y + 0.001, n26, n7, n12);
                    tessellator.addVertexWithUV(n23, y + 0.001, n26, n8, n12);
                    tessellator.addVertexWithUV(n23, y + 0.999, n26, n8, n11);
                }
                if (!GlassPaneRenderer.skipTopEdgeRendering) {
                    tessellator.addVertexWithUV(x, y + 0.999, n26, n14, n15);
                    tessellator.addVertexWithUV(n23, y + 0.999, n26, n14, n17);
                    tessellator.addVertexWithUV(n23, y + 0.999, n25, n13, n17);
                    tessellator.addVertexWithUV(x, y + 0.999, n25, n13, n15);
                }
                if (!GlassPaneRenderer.skipBottomEdgeRendering) {
                    tessellator.addVertexWithUV(n23, y + 0.001, n26, n13, n17);
                    tessellator.addVertexWithUV(x, y + 0.001, n26, n13, n15);
                    tessellator.addVertexWithUV(x, y + 0.001, n25, n14, n15);
                    tessellator.addVertexWithUV(n23, y + 0.001, n25, n14, n17);
                }
            }
        } else if (!connectNorth && !connectSouth && !GlassPaneRenderer.skipPaneRendering) {
            tessellator.addVertexWithUV(n23, y + 0.999, n25, n8, n11);
            tessellator.addVertexWithUV(n23, y + 0.001, n25, n8, n12);
            tessellator.addVertexWithUV(n23, y + 0.001, n26, n9, n12);
            tessellator.addVertexWithUV(n23, y + 0.999, n26, n9, n11);
        }
        if ((connectEast || flag4) && !connectWest) {
            if (!connectSouth && !flag4) {
                if (!GlassPaneRenderer.skipPaneRendering) {
                    tessellator.addVertexWithUV(n23, y + 0.999, n26, n8, n11);
                    tessellator.addVertexWithUV(n23, y + 0.001, n26, n8, n12);
                    tessellator.addVertexWithUV(n20, y + 0.001, n26, n10, n12);
                    tessellator.addVertexWithUV(n20, y + 0.999, n26, n10, n11);
                }
            } else if (!GlassPaneRenderer.skipPaneRendering) {
                tessellator.addVertexWithUV(n24, y + 0.999, n26, n9, n11);
                tessellator.addVertexWithUV(n24, y + 0.001, n26, n9, n12);
                tessellator.addVertexWithUV(n20, y + 0.001, n26, n10, n12);
                tessellator.addVertexWithUV(n20, y + 0.999, n26, n10, n11);
            }
            if (!connectNorth && !flag4) {
                if (!GlassPaneRenderer.skipPaneRendering) {
                    tessellator.addVertexWithUV(n20, y + 0.999, n25, n10, n11);
                    tessellator.addVertexWithUV(n20, y + 0.001, n25, n10, n12);
                    tessellator.addVertexWithUV(n23, y + 0.001, n25, n8, n12);
                    tessellator.addVertexWithUV(n23, y + 0.999, n25, n8, n11);
                }
            } else if (!GlassPaneRenderer.skipPaneRendering) {
                tessellator.addVertexWithUV(n20, y + 0.999, n25, n10, n11);
                tessellator.addVertexWithUV(n20, y + 0.001, n25, n10, n12);
                tessellator.addVertexWithUV(n24, y + 0.001, n25, n9, n12);
                tessellator.addVertexWithUV(n24, y + 0.999, n25, n9, n11);
            }
            if (!GlassPaneRenderer.skipTopEdgeRendering) {
                tessellator.addVertexWithUV(n24, y + 0.999, n26, n14, n18);
                tessellator.addVertexWithUV(n20, y + 0.999, n26, n14, n15);
                tessellator.addVertexWithUV(n20, y + 0.999, n25, n13, n15);
                tessellator.addVertexWithUV(n24, y + 0.999, n25, n13, n18);
            }
            if (!GlassPaneRenderer.skipBottomEdgeRendering) {
                tessellator.addVertexWithUV(n20, y + 0.001, n26, n13, n16);
                tessellator.addVertexWithUV(n24, y + 0.001, n26, n13, n18);
                tessellator.addVertexWithUV(n24, y + 0.001, n25, n14, n18);
                tessellator.addVertexWithUV(n20, y + 0.001, n25, n14, n16);
            }
        } else if (!connectEast && !connectNorth && !connectSouth && !GlassPaneRenderer.skipPaneRendering) {
            tessellator.addVertexWithUV(n24, y + 0.999, n26, n8, n11);
            tessellator.addVertexWithUV(n24, y + 0.001, n26, n8, n12);
            tessellator.addVertexWithUV(n24, y + 0.001, n25, n9, n12);
            tessellator.addVertexWithUV(n24, y + 0.999, n25, n9, n11);
        }
        if (connectNorth || flag4) {
            if (connectNorth && connectSouth) {
                if (!connectWest) {
                    if (!GlassPaneRenderer.skipPaneRendering) {
                        tessellator.addVertexWithUV(n23, y + 0.999, z, n7, n11);
                        tessellator.addVertexWithUV(n23, y + 0.001, z, n7, n12);
                        tessellator.addVertexWithUV(n23, y + 0.001, n22, n10, n12);
                        tessellator.addVertexWithUV(n23, y + 0.999, n22, n10, n11);
                    }
                } else if (!GlassPaneRenderer.skipPaneRendering) {
                    tessellator.addVertexWithUV(n23, y + 0.999, z, n7, n11);
                    tessellator.addVertexWithUV(n23, y + 0.001, z, n7, n12);
                    tessellator.addVertexWithUV(n23, y + 0.001, n25, n8, n12);
                    tessellator.addVertexWithUV(n23, y + 0.999, n25, n8, n11);
                    tessellator.addVertexWithUV(n23, y + 0.999, n26, n9, n11);
                    tessellator.addVertexWithUV(n23, y + 0.001, n26, n9, n12);
                    tessellator.addVertexWithUV(n23, y + 0.001, n22, n10, n12);
                    tessellator.addVertexWithUV(n23, y + 0.999, n22, n10, n11);
                }
                if (!connectEast) {
                    if (!GlassPaneRenderer.skipPaneRendering) {
                        tessellator.addVertexWithUV(n24, y + 0.999, n22, n10, n11);
                        tessellator.addVertexWithUV(n24, y + 0.001, n22, n10, n12);
                        tessellator.addVertexWithUV(n24, y + 0.001, z, n7, n12);
                        tessellator.addVertexWithUV(n24, y + 0.999, z, n7, n11);
                    }
                } else if (!GlassPaneRenderer.skipPaneRendering) {
                    tessellator.addVertexWithUV(n24, y + 0.999, n25, n8, n11);
                    tessellator.addVertexWithUV(n24, y + 0.001, n25, n8, n12);
                    tessellator.addVertexWithUV(n24, y + 0.001, z, n7, n12);
                    tessellator.addVertexWithUV(n24, y + 0.999, z, n7, n11);
                    tessellator.addVertexWithUV(n24, y + 0.999, n22, n10, n11);
                    tessellator.addVertexWithUV(n24, y + 0.001, n22, n10, n12);
                    tessellator.addVertexWithUV(n24, y + 0.001, n26, n9, n12);
                    tessellator.addVertexWithUV(n24, y + 0.999, n26, n9, n11);
                }
                if (!GlassPaneRenderer.skipTopEdgeRendering) {
                    tessellator.addVertexWithUV(n24, y + 0.999, z, n14, n15);
                    tessellator.addVertexWithUV(n23, y + 0.999, z, n13, n15);
                    tessellator.addVertexWithUV(n23, y + 0.999, n22, n13, n16);
                    tessellator.addVertexWithUV(n24, y + 0.999, n22, n14, n16);
                }
                if (!GlassPaneRenderer.skipBottomEdgeRendering) {
                    tessellator.addVertexWithUV(n23, y + 0.001, z, n13, n15);
                    tessellator.addVertexWithUV(n24, y + 0.001, z, n14, n15);
                    tessellator.addVertexWithUV(n24, y + 0.001, n22, n14, n16);
                    tessellator.addVertexWithUV(n23, y + 0.001, n22, n13, n16);
                }
            } else {
                if (!connectWest && !flag4) {
                    if (!GlassPaneRenderer.skipPaneRendering) {
                        tessellator.addVertexWithUV(n23, y + 0.999, z, n7, n11);
                        tessellator.addVertexWithUV(n23, y + 0.001, z, n7, n12);
                        tessellator.addVertexWithUV(n23, y + 0.001, n26, n9, n12);
                        tessellator.addVertexWithUV(n23, y + 0.999, n26, n9, n11);
                    }
                } else if (!GlassPaneRenderer.skipPaneRendering) {
                    tessellator.addVertexWithUV(n23, y + 0.999, z, n7, n11);
                    tessellator.addVertexWithUV(n23, y + 0.001, z, n7, n12);
                    tessellator.addVertexWithUV(n23, y + 0.001, n25, n8, n12);
                    tessellator.addVertexWithUV(n23, y + 0.999, n25, n8, n11);
                }
                if (!connectEast && !flag4) {
                    if (!GlassPaneRenderer.skipPaneRendering) {
                        tessellator.addVertexWithUV(n24, y + 0.999, n26, n9, n11);
                        tessellator.addVertexWithUV(n24, y + 0.001, n26, n9, n12);
                        tessellator.addVertexWithUV(n24, y + 0.001, z, n7, n12);
                        tessellator.addVertexWithUV(n24, y + 0.999, z, n7, n11);
                    }
                } else if (!GlassPaneRenderer.skipPaneRendering) {
                    tessellator.addVertexWithUV(n24, y + 0.999, n25, n8, n11);
                    tessellator.addVertexWithUV(n24, y + 0.001, n25, n8, n12);
                    tessellator.addVertexWithUV(n24, y + 0.001, z, n7, n12);
                    tessellator.addVertexWithUV(n24, y + 0.999, z, n7, n11);
                }
                if (!GlassPaneRenderer.skipTopEdgeRendering) {
                    tessellator.addVertexWithUV(n24, y + 0.999, z, n14, n15);
                    tessellator.addVertexWithUV(n23, y + 0.999, z, n13, n15);
                    tessellator.addVertexWithUV(n23, y + 0.999, n25, n13, n17);
                    tessellator.addVertexWithUV(n24, y + 0.999, n25, n14, n17);
                }
                if (!GlassPaneRenderer.skipBottomEdgeRendering) {
                    tessellator.addVertexWithUV(n23, y + 0.001, z, n13, n15);
                    tessellator.addVertexWithUV(n24, y + 0.001, z, n14, n15);
                    tessellator.addVertexWithUV(n24, y + 0.001, n25, n14, n17);
                    tessellator.addVertexWithUV(n23, y + 0.001, n25, n13, n17);
                }
            }
        } else if (!connectEast && !connectWest && !GlassPaneRenderer.skipPaneRendering) {
            tessellator.addVertexWithUV(n24, y + 0.999, n25, n9, n11);
            tessellator.addVertexWithUV(n24, y + 0.001, n25, n9, n12);
            tessellator.addVertexWithUV(n23, y + 0.001, n25, n8, n12);
            tessellator.addVertexWithUV(n23, y + 0.999, n25, n8, n11);
        }
        if ((connectSouth || flag4) && !connectNorth) {
            if (!connectWest && !flag4) {
                if (!GlassPaneRenderer.skipPaneRendering) {
                    tessellator.addVertexWithUV(n23, y + 0.999, n25, n8, n11);
                    tessellator.addVertexWithUV(n23, y + 0.001, n25, n8, n12);
                    tessellator.addVertexWithUV(n23, y + 0.001, n22, n10, n12);
                    tessellator.addVertexWithUV(n23, y + 0.999, n22, n10, n11);
                }
            } else if (!GlassPaneRenderer.skipPaneRendering) {
                tessellator.addVertexWithUV(n23, y + 0.999, n26, n9, n11);
                tessellator.addVertexWithUV(n23, y + 0.001, n26, n9, n12);
                tessellator.addVertexWithUV(n23, y + 0.001, n22, n10, n12);
                tessellator.addVertexWithUV(n23, y + 0.999, n22, n10, n11);
            }
            if (!connectEast && !flag4) {
                if (!GlassPaneRenderer.skipPaneRendering) {
                    tessellator.addVertexWithUV(n24, y + 0.999, n22, n10, n11);
                    tessellator.addVertexWithUV(n24, y + 0.001, n22, n10, n12);
                    tessellator.addVertexWithUV(n24, y + 0.001, n25, n8, n12);
                    tessellator.addVertexWithUV(n24, y + 0.999, n25, n8, n11);
                }
            } else if (!GlassPaneRenderer.skipPaneRendering) {
                tessellator.addVertexWithUV(n24, y + 0.999, n22, n10, n11);
                tessellator.addVertexWithUV(n24, y + 0.001, n22, n10, n12);
                tessellator.addVertexWithUV(n24, y + 0.001, n26, n9, n12);
                tessellator.addVertexWithUV(n24, y + 0.999, n26, n9, n11);
            }
            if (!GlassPaneRenderer.skipTopEdgeRendering) {
                tessellator.addVertexWithUV(n24, y + 0.999, n26, n14, n18);
                tessellator.addVertexWithUV(n23, y + 0.999, n26, n13, n18);
                tessellator.addVertexWithUV(n23, y + 0.999, n22, n13, n16);
                tessellator.addVertexWithUV(n24, y + 0.999, n22, n14, n16);
            }
            if (!GlassPaneRenderer.skipBottomEdgeRendering) {
                tessellator.addVertexWithUV(n23, y + 0.001, n26, n13, n18);
                tessellator.addVertexWithUV(n24, y + 0.001, n26, n14, n18);
                tessellator.addVertexWithUV(n24, y + 0.001, n22, n14, n16);
                tessellator.addVertexWithUV(n23, y + 0.001, n22, n13, n16);
            }
        } else if (!connectSouth && !connectEast && !connectWest && !GlassPaneRenderer.skipPaneRendering) {
            tessellator.addVertexWithUV(n23, y + 0.999, n26, n8, n11);
            tessellator.addVertexWithUV(n23, y + 0.001, n26, n8, n12);
            tessellator.addVertexWithUV(n24, y + 0.001, n26, n9, n12);
            tessellator.addVertexWithUV(n24, y + 0.999, n26, n9, n11);
        }
        if (!GlassPaneRenderer.skipTopEdgeRendering) {
            tessellator.addVertexWithUV(n24, y + 0.999, n25, n14, n17);
            tessellator.addVertexWithUV(n23, y + 0.999, n25, n13, n17);
            tessellator.addVertexWithUV(n23, y + 0.999, n26, n13, n18);
            tessellator.addVertexWithUV(n24, y + 0.999, n26, n14, n18);
        }
        if (!GlassPaneRenderer.skipBottomEdgeRendering) {
            tessellator.addVertexWithUV(n23, y + 0.001, n25, n13, n17);
            tessellator.addVertexWithUV(n24, y + 0.001, n25, n14, n17);
            tessellator.addVertexWithUV(n24, y + 0.001, n26, n14, n18);
            tessellator.addVertexWithUV(n23, y + 0.001, n26, n13, n18);
        }
        if (flag4) {
            if (!GlassPaneRenderer.skipPaneRendering) {
                tessellator.addVertexWithUV(x, y + 0.999, n25, n8, n11);
                tessellator.addVertexWithUV(x, y + 0.001, n25, n8, n12);
                tessellator.addVertexWithUV(x, y + 0.001, n26, n9, n12);
                tessellator.addVertexWithUV(x, y + 0.999, n26, n9, n11);
                tessellator.addVertexWithUV(n20, y + 0.999, n26, n8, n11);
                tessellator.addVertexWithUV(n20, y + 0.001, n26, n8, n12);
                tessellator.addVertexWithUV(n20, y + 0.001, n25, n9, n12);
                tessellator.addVertexWithUV(n20, y + 0.999, n25, n9, n11);
            }
            if (!GlassPaneRenderer.skipPaneRendering) {
                tessellator.addVertexWithUV(n24, y + 0.999, z, n9, n11);
                tessellator.addVertexWithUV(n24, y + 0.001, z, n9, n12);
                tessellator.addVertexWithUV(n23, y + 0.001, z, n8, n12);
                tessellator.addVertexWithUV(n23, y + 0.999, z, n8, n11);
                tessellator.addVertexWithUV(n23, y + 0.999, n22, n8, n11);
                tessellator.addVertexWithUV(n23, y + 0.001, n22, n8, n12);
                tessellator.addVertexWithUV(n24, y + 0.001, n22, n9, n12);
                tessellator.addVertexWithUV(n24, y + 0.999, n22, n9, n11);
            }
        }

        return true;
    }

    /**
     * @author Mist475 (adapted from Paul Rupe)
     * @reason Significant deviation from Vanilla
     */
    @SuppressWarnings("DuplicatedCode")
    @Overwrite
    public boolean renderBlockPane(BlockPane block, int x, int y, int z) {
        int r = this.blockAccess.getHeight();
        Tessellator tessellator = Tessellator.instance;
        tessellator.setBrightness(block.getMixedBrightnessForBlock(this.blockAccess, x, y, z));
        int d = block.colorMultiplier(this.blockAccess, x, y, z);
        float n = (d >> 16 & 0xFF) / 255.0f;
        float n2 = (d >> 8 & 0xFF) / 255.0f;
        float n3 = (d & 0xFF) / 255.0f;
        if (EntityRenderer.anaglyphEnable) {
            float n4 = (n * 30.0f + n2 * 59.0f + n3 * 11.0f) / 100.0f;
            float n5 = (n * 30.0f + n2 * 70.0f) / 100.0f;
            float n6 = (n * 30.0f + n3 * 70.0f) / 100.0f;
            n = n4;
            n2 = n5;
            n3 = n6;
        }
        tessellator.setColorOpaque_F(n, n2, n3);
        IIcon d2;
        IIcon rf;
        if (this.hasOverrideBlockTexture()) {
            d2 = this.overrideBlockTexture;
            rf = this.overrideBlockTexture;
        } else {
            int e = this.blockAccess.getBlockMetadata(x, y, z);
            d2 = ((this.blockAccess == null) ? this.getBlockIconFromSideAndMetadata(block, 0, e)
                : this.getBlockIcon(block, this.blockAccess, x, y, z, 0));
            rf = block.func_150097_e();
        }
        double n7 = d2.getMinU();
        double n8 = d2.getInterpolatedU(8.0);
        double n9 = d2.getMaxU();
        double n10 = d2.getMinV();
        double n11 = d2.getMaxV();
        double n12 = rf.getInterpolatedU(7.0);
        double n13 = rf.getInterpolatedU(9.0);
        double n14 = rf.getMinV();
        double n15 = rf.getInterpolatedV(8.0);
        double n16 = rf.getMaxV();
        double n18 = x + 0.5;
        double n19 = x + 1;
        double n21 = z + 0.5;
        double n22 = z + 1;
        double n23 = x + 0.5 - 0.0625;
        double n24 = x + 0.5 + 0.0625;
        double n25 = z + 0.5 - 0.0625;
        double n26 = z + 0.5 + 0.0625;

        // Slightly different due to forge
        boolean a2 = block.canPaneConnectTo(this.blockAccess, x, y, z - 1, NORTH);
        boolean a3 = block.canPaneConnectTo(this.blockAccess, x, y, z + 1, SOUTH);
        boolean a4 = block.canPaneConnectTo(this.blockAccess, x - 1, y, z, WEST);
        boolean a5 = block.canPaneConnectTo(this.blockAccess, x + 1, y, z, EAST);
        boolean a6 = block.shouldSideBeRendered(this.blockAccess, x, y + 1, z, 1);
        boolean a7 = block.shouldSideBeRendered(this.blockAccess, x, y - 1, z, 0);

        GlassPaneRenderer.renderThin((RenderBlocks) (Object) this, block, d2, x, y, z, a2, a3, a4, a5);
        if ((a4 && a5) || (!a4 && !a5 && !a2 && !a3)) {
            if (!GlassPaneRenderer.skipPaneRendering) {
                tessellator.addVertexWithUV(x, y + 1, n21, n7, n10);
                tessellator.addVertexWithUV(x, y, n21, n7, n11);
                tessellator.addVertexWithUV(n19, y, n21, n9, n11);
                tessellator.addVertexWithUV(n19, y + 1, n21, n9, n10);
                tessellator.addVertexWithUV(n19, y + 1, n21, n7, n10);
                tessellator.addVertexWithUV(n19, y, n21, n7, n11);
                tessellator.addVertexWithUV(x, y, n21, n9, n11);
                tessellator.addVertexWithUV(x, y + 1, n21, n9, n10);
            }
            if (a6) {
                if (!GlassPaneRenderer.skipTopEdgeRendering) {
                    tessellator.addVertexWithUV(x, y + 1 + 0.01, n26, n13, n16);
                    tessellator.addVertexWithUV(n19, y + 1 + 0.01, n26, n13, n14);
                    tessellator.addVertexWithUV(n19, y + 1 + 0.01, n25, n12, n14);
                    tessellator.addVertexWithUV(x, y + 1 + 0.01, n25, n12, n16);
                    tessellator.addVertexWithUV(n19, y + 1 + 0.01, n26, n13, n16);
                    tessellator.addVertexWithUV(x, y + 1 + 0.01, n26, n13, n14);
                    tessellator.addVertexWithUV(x, y + 1 + 0.01, n25, n12, n14);
                    tessellator.addVertexWithUV(n19, y + 1 + 0.01, n25, n12, n16);
                }
            } else {
                if (y < r - 1 && this.blockAccess.isAirBlock(x - 1, y + 1, z)
                    && !GlassPaneRenderer.skipTopEdgeRendering) {
                    tessellator.addVertexWithUV(x, y + 1 + 0.01, n26, n13, n15);
                    tessellator.addVertexWithUV(n18, y + 1 + 0.01, n26, n13, n16);
                    tessellator.addVertexWithUV(n18, y + 1 + 0.01, n25, n12, n16);
                    tessellator.addVertexWithUV(x, y + 1 + 0.01, n25, n12, n15);
                    tessellator.addVertexWithUV(n18, y + 1 + 0.01, n26, n13, n15);
                    tessellator.addVertexWithUV(x, y + 1 + 0.01, n26, n13, n16);
                    tessellator.addVertexWithUV(x, y + 1 + 0.01, n25, n12, n16);
                    tessellator.addVertexWithUV(n18, y + 1 + 0.01, n25, n12, n15);
                }
                if (y < r - 1 && this.blockAccess.isAirBlock(x + 1, y + 1, z)
                    && !GlassPaneRenderer.skipTopEdgeRendering) {
                    tessellator.addVertexWithUV(n18, y + 1 + 0.01, n26, n13, n14);
                    tessellator.addVertexWithUV(n19, y + 1 + 0.01, n26, n13, n15);
                    tessellator.addVertexWithUV(n19, y + 1 + 0.01, n25, n12, n15);
                    tessellator.addVertexWithUV(n18, y + 1 + 0.01, n25, n12, n14);
                    tessellator.addVertexWithUV(n19, y + 1 + 0.01, n26, n13, n14);
                    tessellator.addVertexWithUV(n18, y + 1 + 0.01, n26, n13, n15);
                    tessellator.addVertexWithUV(n18, y + 1 + 0.01, n25, n12, n15);
                    tessellator.addVertexWithUV(n19, y + 1 + 0.01, n25, n12, n14);
                }
            }
            if (a7) {
                if (!GlassPaneRenderer.skipBottomEdgeRendering) {
                    tessellator.addVertexWithUV(x, y - 0.01, n26, n13, n16);
                    tessellator.addVertexWithUV(n19, y - 0.01, n26, n13, n14);
                    tessellator.addVertexWithUV(n19, y - 0.01, n25, n12, n14);
                    tessellator.addVertexWithUV(x, y - 0.01, n25, n12, n16);
                    tessellator.addVertexWithUV(n19, y - 0.01, n26, n13, n16);
                    tessellator.addVertexWithUV(x, y - 0.01, n26, n13, n14);
                    tessellator.addVertexWithUV(x, y - 0.01, n25, n12, n14);
                    tessellator.addVertexWithUV(n19, y - 0.01, n25, n12, n16);
                }
            } else {
                if (y > 1 && this.blockAccess.isAirBlock(x - 1, y - 1, z)
                    && !GlassPaneRenderer.skipBottomEdgeRendering) {
                    tessellator.addVertexWithUV(x, y - 0.01, n26, n13, n15);
                    tessellator.addVertexWithUV(n18, y - 0.01, n26, n13, n16);
                    tessellator.addVertexWithUV(n18, y - 0.01, n25, n12, n16);
                    tessellator.addVertexWithUV(x, y - 0.01, n25, n12, n15);
                    tessellator.addVertexWithUV(n18, y - 0.01, n26, n13, n15);
                    tessellator.addVertexWithUV(x, y - 0.01, n26, n13, n16);
                    tessellator.addVertexWithUV(x, y - 0.01, n25, n12, n16);
                    tessellator.addVertexWithUV(n18, y - 0.01, n25, n12, n15);
                }
                if (y > 1 && this.blockAccess.isAirBlock(x + 1, y - 1, z)) {
                    if (!GlassPaneRenderer.skipBottomEdgeRendering) {
                        tessellator.addVertexWithUV(n18, y - 0.01, n26, n13, n14);
                        tessellator.addVertexWithUV(n19, y - 0.01, n26, n13, n15);
                        tessellator.addVertexWithUV(n19, y - 0.01, n25, n12, n15);
                        tessellator.addVertexWithUV(n18, y - 0.01, n25, n12, n14);
                        tessellator.addVertexWithUV(n19, y - 0.01, n26, n13, n14);
                        tessellator.addVertexWithUV(n18, y - 0.01, n26, n13, n15);
                        tessellator.addVertexWithUV(n18, y - 0.01, n25, n12, n15);
                        tessellator.addVertexWithUV(n19, y - 0.01, n25, n12, n14);
                    }
                }
            }
        } else if (a4) {
            if (!GlassPaneRenderer.skipPaneRendering) {
                tessellator.addVertexWithUV(x, y + 1, n21, n7, n10);
                tessellator.addVertexWithUV(x, y, n21, n7, n11);
                tessellator.addVertexWithUV(n18, y, n21, n8, n11);
                tessellator.addVertexWithUV(n18, y + 1, n21, n8, n10);
                tessellator.addVertexWithUV(n18, y + 1, n21, n7, n10);
                tessellator.addVertexWithUV(n18, y, n21, n7, n11);
                tessellator.addVertexWithUV(x, y, n21, n8, n11);
                tessellator.addVertexWithUV(x, y + 1, n21, n8, n10);
            }
            if (!a3 && !a2) {
                tessellator.addVertexWithUV(n18, y + 1, n26, n12, n14);
                tessellator.addVertexWithUV(n18, y, n26, n12, n16);
                tessellator.addVertexWithUV(n18, y, n25, n13, n16);
                tessellator.addVertexWithUV(n18, y + 1, n25, n13, n14);
                tessellator.addVertexWithUV(n18, y + 1, n25, n12, n14);
                tessellator.addVertexWithUV(n18, y, n25, n12, n16);
                tessellator.addVertexWithUV(n18, y, n26, n13, n16);
                tessellator.addVertexWithUV(n18, y + 1, n26, n13, n14);
            }
            if ((a6 || (y < r - 1 && this.blockAccess.isAirBlock(x - 1, y + 1, z)))
                && !GlassPaneRenderer.skipTopEdgeRendering) {
                tessellator.addVertexWithUV(x, y + 1 + 0.01, n26, n13, n15);
                tessellator.addVertexWithUV(n18, y + 1 + 0.01, n26, n13, n16);
                tessellator.addVertexWithUV(n18, y + 1 + 0.01, n25, n12, n16);
                tessellator.addVertexWithUV(x, y + 1 + 0.01, n25, n12, n15);
                tessellator.addVertexWithUV(n18, y + 1 + 0.01, n26, n13, n15);
                tessellator.addVertexWithUV(x, y + 1 + 0.01, n26, n13, n16);
                tessellator.addVertexWithUV(x, y + 1 + 0.01, n25, n12, n16);
                tessellator.addVertexWithUV(n18, y + 1 + 0.01, n25, n12, n15);
            }
            if (a7 || (y > 1 && this.blockAccess.isAirBlock(x - 1, y - 1, z))) {
                if (!GlassPaneRenderer.skipBottomEdgeRendering) {
                    tessellator.addVertexWithUV(x, y - 0.01, n26, n13, n15);
                    tessellator.addVertexWithUV(n18, y - 0.01, n26, n13, n16);
                    tessellator.addVertexWithUV(n18, y - 0.01, n25, n12, n16);
                    tessellator.addVertexWithUV(x, y - 0.01, n25, n12, n15);
                    tessellator.addVertexWithUV(n18, y - 0.01, n26, n13, n15);
                    tessellator.addVertexWithUV(x, y - 0.01, n26, n13, n16);
                    tessellator.addVertexWithUV(x, y - 0.01, n25, n12, n16);
                    tessellator.addVertexWithUV(n18, y - 0.01, n25, n12, n15);
                }
            }
        } else if (a5) {
            if (!GlassPaneRenderer.skipPaneRendering) {
                tessellator.addVertexWithUV(n18, y + 1, n21, n8, n10);
                tessellator.addVertexWithUV(n18, y, n21, n8, n11);
                tessellator.addVertexWithUV(n19, y, n21, n9, n11);
                tessellator.addVertexWithUV(n19, y + 1, n21, n9, n10);
                tessellator.addVertexWithUV(n19, y + 1, n21, n8, n10);
                tessellator.addVertexWithUV(n19, y, n21, n8, n11);
                tessellator.addVertexWithUV(n18, y, n21, n9, n11);
                tessellator.addVertexWithUV(n18, y + 1, n21, n9, n10);
            }
            if (!a3 && !a2) {
                tessellator.addVertexWithUV(n18, y + 1, n25, n12, n14);
                tessellator.addVertexWithUV(n18, y, n25, n12, n16);
                tessellator.addVertexWithUV(n18, y, n26, n13, n16);
                tessellator.addVertexWithUV(n18, y + 1, n26, n13, n14);
                tessellator.addVertexWithUV(n18, y + 1, n26, n12, n14);
                tessellator.addVertexWithUV(n18, y, n26, n12, n16);
                tessellator.addVertexWithUV(n18, y, n25, n13, n16);
                tessellator.addVertexWithUV(n18, y + 1, n25, n13, n14);
            }
            if ((a6 || (y < r - 1 && this.blockAccess.isAirBlock(x + 1, y + 1, z)))
                && !GlassPaneRenderer.skipTopEdgeRendering) {
                tessellator.addVertexWithUV(n18, y + 1 + 0.01, n26, n13, n14);
                tessellator.addVertexWithUV(n19, y + 1 + 0.01, n26, n13, n15);
                tessellator.addVertexWithUV(n19, y + 1 + 0.01, n25, n12, n15);
                tessellator.addVertexWithUV(n18, y + 1 + 0.01, n25, n12, n14);
                tessellator.addVertexWithUV(n19, y + 1 + 0.01, n26, n13, n14);
                tessellator.addVertexWithUV(n18, y + 1 + 0.01, n26, n13, n15);
                tessellator.addVertexWithUV(n18, y + 1 + 0.01, n25, n12, n15);
                tessellator.addVertexWithUV(n19, y + 1 + 0.01, n25, n12, n14);
            }
            if ((a7 || (y > 1 && this.blockAccess.isAirBlock(x + 1, y - 1, z)))
                && !GlassPaneRenderer.skipBottomEdgeRendering) {
                tessellator.addVertexWithUV(n18, y - 0.01, n26, n13, n14);
                tessellator.addVertexWithUV(n19, y - 0.01, n26, n13, n15);
                tessellator.addVertexWithUV(n19, y - 0.01, n25, n12, n15);
                tessellator.addVertexWithUV(n18, y - 0.01, n25, n12, n14);
                tessellator.addVertexWithUV(n19, y - 0.01, n26, n13, n14);
                tessellator.addVertexWithUV(n18, y - 0.01, n26, n13, n15);
                tessellator.addVertexWithUV(n18, y - 0.01, n25, n12, n15);
                tessellator.addVertexWithUV(n19, y - 0.01, n25, n12, n14);
            }
        }
        if ((a2 && a3) || (!a4 && !a5 && !a2 && !a3)) {
            if (!GlassPaneRenderer.skipPaneRendering) {
                tessellator.addVertexWithUV(n18, y + 1, n22, n7, n10);
                tessellator.addVertexWithUV(n18, y, n22, n7, n11);
                tessellator.addVertexWithUV(n18, y, z, n9, n11);
                tessellator.addVertexWithUV(n18, y + 1, z, n9, n10);
                tessellator.addVertexWithUV(n18, y + 1, z, n7, n10);
                tessellator.addVertexWithUV(n18, y, z, n7, n11);
                tessellator.addVertexWithUV(n18, y, n22, n9, n11);
                tessellator.addVertexWithUV(n18, y + 1, n22, n9, n10);
            }
            if (a6) {
                if (!GlassPaneRenderer.skipTopEdgeRendering) {
                    tessellator.addVertexWithUV(n24, y + 1 + 0.005, n22, n13, n16);
                    tessellator.addVertexWithUV(n24, y + 1 + 0.005, z, n13, n14);
                    tessellator.addVertexWithUV(n23, y + 1 + 0.005, z, n12, n14);
                    tessellator.addVertexWithUV(n23, y + 1 + 0.005, n22, n12, n16);
                    tessellator.addVertexWithUV(n24, y + 1 + 0.005, z, n13, n16);
                    tessellator.addVertexWithUV(n24, y + 1 + 0.005, n22, n13, n14);
                    tessellator.addVertexWithUV(n23, y + 1 + 0.005, n22, n12, n14);
                    tessellator.addVertexWithUV(n23, y + 1 + 0.005, z, n12, n16);
                }
            } else {
                if (y < r - 1 && this.blockAccess.isAirBlock(x, y + 1, z - 1)
                    && !GlassPaneRenderer.skipTopEdgeRendering) {
                    tessellator.addVertexWithUV(n23, y + 1 + 0.005, z, n13, n14);
                    tessellator.addVertexWithUV(n23, y + 1 + 0.005, n21, n13, n15);
                    tessellator.addVertexWithUV(n24, y + 1 + 0.005, n21, n12, n15);
                    tessellator.addVertexWithUV(n24, y + 1 + 0.005, z, n12, n14);
                    tessellator.addVertexWithUV(n23, y + 1 + 0.005, n21, n13, n14);
                    tessellator.addVertexWithUV(n23, y + 1 + 0.005, z, n13, n15);
                    tessellator.addVertexWithUV(n24, y + 1 + 0.005, z, n12, n15);
                    tessellator.addVertexWithUV(n24, y + 1 + 0.005, n21, n12, n14);
                }
                if (y < r - 1 && this.blockAccess.isAirBlock(x, y + 1, z + 1)
                    && !GlassPaneRenderer.skipTopEdgeRendering) {
                    tessellator.addVertexWithUV(n23, y + 1 + 0.005, n21, n12, n15);
                    tessellator.addVertexWithUV(n23, y + 1 + 0.005, n22, n12, n16);
                    tessellator.addVertexWithUV(n24, y + 1 + 0.005, n22, n13, n16);
                    tessellator.addVertexWithUV(n24, y + 1 + 0.005, n21, n13, n15);
                    tessellator.addVertexWithUV(n23, y + 1 + 0.005, n22, n12, n15);
                    tessellator.addVertexWithUV(n23, y + 1 + 0.005, n21, n12, n16);
                    tessellator.addVertexWithUV(n24, y + 1 + 0.005, n21, n13, n16);
                    tessellator.addVertexWithUV(n24, y + 1 + 0.005, n22, n13, n15);
                }
            }
            if (a7) {
                if (!GlassPaneRenderer.skipBottomEdgeRendering) {
                    tessellator.addVertexWithUV(n24, y - 0.005, n22, n13, n16);
                    tessellator.addVertexWithUV(n24, y - 0.005, z, n13, n14);
                    tessellator.addVertexWithUV(n23, y - 0.005, z, n12, n14);
                    tessellator.addVertexWithUV(n23, y - 0.005, n22, n12, n16);
                    tessellator.addVertexWithUV(n24, y - 0.005, z, n13, n16);
                    tessellator.addVertexWithUV(n24, y - 0.005, n22, n13, n14);
                    tessellator.addVertexWithUV(n23, y - 0.005, n22, n12, n14);
                    tessellator.addVertexWithUV(n23, y - 0.005, z, n12, n16);
                }
            } else {
                if (y > 1 && this.blockAccess.isAirBlock(x, y - 1, z - 1)
                    && !GlassPaneRenderer.skipBottomEdgeRendering) {
                    tessellator.addVertexWithUV(n23, y - 0.005, z, n13, n14);
                    tessellator.addVertexWithUV(n23, y - 0.005, n21, n13, n15);
                    tessellator.addVertexWithUV(n24, y - 0.005, n21, n12, n15);
                    tessellator.addVertexWithUV(n24, y - 0.005, z, n12, n14);
                    tessellator.addVertexWithUV(n23, y - 0.005, n21, n13, n14);
                    tessellator.addVertexWithUV(n23, y - 0.005, z, n13, n15);
                    tessellator.addVertexWithUV(n24, y - 0.005, z, n12, n15);
                    tessellator.addVertexWithUV(n24, y - 0.005, n21, n12, n14);
                }
                if (y > 1 && this.blockAccess.isAirBlock(x, y - 1, z + 1)) {
                    if (!GlassPaneRenderer.skipBottomEdgeRendering) {
                        tessellator.addVertexWithUV(n23, y - 0.005, n21, n12, n15);
                        tessellator.addVertexWithUV(n23, y - 0.005, n22, n12, n16);
                        tessellator.addVertexWithUV(n24, y - 0.005, n22, n13, n16);
                        tessellator.addVertexWithUV(n24, y - 0.005, n21, n13, n15);
                        tessellator.addVertexWithUV(n23, y - 0.005, n22, n12, n15);
                        tessellator.addVertexWithUV(n23, y - 0.005, n21, n12, n16);
                        tessellator.addVertexWithUV(n24, y - 0.005, n21, n13, n16);
                        tessellator.addVertexWithUV(n24, y - 0.005, n22, n13, n15);
                    }
                }
            }
        } else if (a2) {
            if (!GlassPaneRenderer.skipPaneRendering) {
                tessellator.addVertexWithUV(n18, y + 1, z, n7, n10);
                tessellator.addVertexWithUV(n18, y, z, n7, n11);
                tessellator.addVertexWithUV(n18, y, n21, n8, n11);
                tessellator.addVertexWithUV(n18, y + 1, n21, n8, n10);
                tessellator.addVertexWithUV(n18, y + 1, n21, n7, n10);
                tessellator.addVertexWithUV(n18, y, n21, n7, n11);
                tessellator.addVertexWithUV(n18, y, z, n8, n11);
                tessellator.addVertexWithUV(n18, y + 1, z, n8, n10);
            }
            if (!a5 && !a4) {
                tessellator.addVertexWithUV(n23, y + 1, n21, n12, n14);
                tessellator.addVertexWithUV(n23, y, n21, n12, n16);
                tessellator.addVertexWithUV(n24, y, n21, n13, n16);
                tessellator.addVertexWithUV(n24, y + 1, n21, n13, n14);
                tessellator.addVertexWithUV(n24, y + 1, n21, n12, n14);
                tessellator.addVertexWithUV(n24, y, n21, n12, n16);
                tessellator.addVertexWithUV(n23, y, n21, n13, n16);
                tessellator.addVertexWithUV(n23, y + 1, n21, n13, n14);
            }
            if ((a6 || (y < r - 1 && this.blockAccess.isAirBlock(x, y + 1, z - 1)))
                && !GlassPaneRenderer.skipTopEdgeRendering) {
                tessellator.addVertexWithUV(n23, y + 1 + 0.005, z, n13, n14);
                tessellator.addVertexWithUV(n23, y + 1 + 0.005, n21, n13, n15);
                tessellator.addVertexWithUV(n24, y + 1 + 0.005, n21, n12, n15);
                tessellator.addVertexWithUV(n24, y + 1 + 0.005, z, n12, n14);
                tessellator.addVertexWithUV(n23, y + 1 + 0.005, n21, n13, n14);
                tessellator.addVertexWithUV(n23, y + 1 + 0.005, z, n13, n15);
                tessellator.addVertexWithUV(n24, y + 1 + 0.005, z, n12, n15);
                tessellator.addVertexWithUV(n24, y + 1 + 0.005, n21, n12, n14);
            }
            if (a7 || (y > 1 && this.blockAccess.isAirBlock(x, y - 1, z - 1))) {
                if (!GlassPaneRenderer.skipBottomEdgeRendering) {
                    tessellator.addVertexWithUV(n23, y - 0.005, z, n13, n14);
                    tessellator.addVertexWithUV(n23, y - 0.005, n21, n13, n15);
                    tessellator.addVertexWithUV(n24, y - 0.005, n21, n12, n15);
                    tessellator.addVertexWithUV(n24, y - 0.005, z, n12, n14);
                    tessellator.addVertexWithUV(n23, y - 0.005, n21, n13, n14);
                    tessellator.addVertexWithUV(n23, y - 0.005, z, n13, n15);
                    tessellator.addVertexWithUV(n24, y - 0.005, z, n12, n15);
                    tessellator.addVertexWithUV(n24, y - 0.005, n21, n12, n14);
                }
            }
        } else if (a3) {
            if (!GlassPaneRenderer.skipPaneRendering) {
                tessellator.addVertexWithUV(n18, y + 1, n21, n8, n10);
                tessellator.addVertexWithUV(n18, y, n21, n8, n11);
                tessellator.addVertexWithUV(n18, y, n22, n9, n11);
                tessellator.addVertexWithUV(n18, y + 1, n22, n9, n10);
                tessellator.addVertexWithUV(n18, y + 1, n22, n8, n10);
                tessellator.addVertexWithUV(n18, y, n22, n8, n11);
                tessellator.addVertexWithUV(n18, y, n21, n9, n11);
                tessellator.addVertexWithUV(n18, y + 1, n21, n9, n10);
            }
            if (!a5 && !a4) {
                tessellator.addVertexWithUV(n24, y + 1, n21, n12, n14);
                tessellator.addVertexWithUV(n24, y, n21, n12, n16);
                tessellator.addVertexWithUV(n23, y, n21, n13, n16);
                tessellator.addVertexWithUV(n23, y + 1, n21, n13, n14);
                tessellator.addVertexWithUV(n23, y + 1, n21, n12, n14);
                tessellator.addVertexWithUV(n23, y, n21, n12, n16);
                tessellator.addVertexWithUV(n24, y, n21, n13, n16);
                tessellator.addVertexWithUV(n24, y + 1, n21, n13, n14);
            }
            if ((a6 || (y < r - 1 && this.blockAccess.isAirBlock(x, y + 1, z + 1)))
                && !GlassPaneRenderer.skipTopEdgeRendering) {
                tessellator.addVertexWithUV(n23, y + 1 + 0.005, n21, n12, n15);
                tessellator.addVertexWithUV(n23, y + 1 + 0.005, n22, n12, n16);
                tessellator.addVertexWithUV(n24, y + 1 + 0.005, n22, n13, n16);
                tessellator.addVertexWithUV(n24, y + 1 + 0.005, n21, n13, n15);
                tessellator.addVertexWithUV(n23, y + 1 + 0.005, n22, n12, n15);
                tessellator.addVertexWithUV(n23, y + 1 + 0.005, n21, n12, n16);
                tessellator.addVertexWithUV(n24, y + 1 + 0.005, n21, n13, n16);
                tessellator.addVertexWithUV(n24, y + 1 + 0.005, n22, n13, n15);
            }
            if ((a7 || (y > 1 && this.blockAccess.isAirBlock(x, y - 1, z + 1)))
                && !GlassPaneRenderer.skipBottomEdgeRendering) {
                tessellator.addVertexWithUV(n23, y - 0.005, n21, n12, n15);
                tessellator.addVertexWithUV(n23, y - 0.005, n22, n12, n16);
                tessellator.addVertexWithUV(n24, y - 0.005, n22, n13, n16);
                tessellator.addVertexWithUV(n24, y - 0.005, n21, n13, n15);
                tessellator.addVertexWithUV(n23, y - 0.005, n22, n12, n15);
                tessellator.addVertexWithUV(n23, y - 0.005, n21, n12, n16);
                tessellator.addVertexWithUV(n24, y - 0.005, n21, n13, n16);
                tessellator.addVertexWithUV(n24, y - 0.005, n22, n13, n15);
            }
        }
        return true;
    }

    @Redirect(
        method = "renderCrossedSquares(Lnet/minecraft/block/Block;III)Z",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/renderer/RenderBlocks;getBlockIconFromSideAndMetadata(Lnet/minecraft/block/Block;II)Lnet/minecraft/util/IIcon;"))
    private IIcon redirectGetBlockIconFromSideAndMetadata(RenderBlocks instance, Block block, int side, int meta,
        Block specializedBlock, int x, int y, int z) {
        return (this.blockAccess == null) ? this.getBlockIconFromSideAndMetadata(block, side, meta)
            : this.getBlockIcon(block, this.blockAccess, x, y, z, side);
    }

    @Redirect(
        method = "renderBlockDoublePlant(Lnet/minecraft/block/BlockDoublePlant;III)Z",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/block/BlockDoublePlant;func_149888_a(ZI)Lnet/minecraft/util/IIcon;"))
    private IIcon modifyRenderBlockDoublePlant(BlockDoublePlant block, boolean top, int meta,
        BlockDoublePlant specializedBlock, int x, int y, int z) {
        return CTMUtils.getBlockIcon(
            block.func_149888_a(top, meta),
            (RenderBlocks) (Object) this,
            block,
            this.blockAccess,
            x,
            y,
            z,
            -1);
    }

    @Redirect(
        method = "renderBlockLilyPad(Lnet/minecraft/block/Block;III)Z",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/renderer/RenderBlocks;getBlockIconFromSide(Lnet/minecraft/block/Block;I)Lnet/minecraft/util/IIcon;"))
    private IIcon modifyRenderBlockLilyPad(RenderBlocks instance, Block block, int side, Block specializedBlock, int x,
        int y, int z) {
        return (this.blockAccess == null) ? this.getBlockIconFromSide(block, side)
            : this.getBlockIcon(block, this.blockAccess, x, y, z, side);
    }

    /**
     * @author Mist475 (adapted from Paul Rupe)
     * @reason Will be changes soon :tm:
     */
    @SuppressWarnings("DuplicatedCode")
    @Overwrite
    public boolean renderBlockLiquid(final Block block, final int p_147721_2_, final int p_147721_3_,
        final int p_147721_4_) {
        final Tessellator tessellator = Tessellator.instance;
        final int firstInt = block.colorMultiplier(this.blockAccess, p_147721_2_, p_147721_3_, p_147721_4_);
        final float firstFloat = (firstInt >> 16 & 0xFF) / 255.0f;
        final float secondFloat = (firstInt >> 8 & 0xFF) / 255.0f;
        final float thirdFloat = (firstInt & 0xFF) / 255.0f;
        final boolean shouldSideBeRendered = RenderPass
            .shouldSideBeRendered(block, this.blockAccess, p_147721_2_, p_147721_3_ + 1, p_147721_4_, 1);
        final boolean shouldSideBeRendered2 = RenderPass
            .shouldSideBeRendered(block, this.blockAccess, p_147721_2_, p_147721_3_ - 1, p_147721_4_, 0);
        final boolean[] firstBoolArray = {
            RenderPass.shouldSideBeRendered(block, this.blockAccess, p_147721_2_, p_147721_3_, p_147721_4_ - 1, 2),
            RenderPass.shouldSideBeRendered(block, this.blockAccess, p_147721_2_, p_147721_3_, p_147721_4_ + 1, 3),
            RenderPass.shouldSideBeRendered(block, this.blockAccess, p_147721_2_ - 1, p_147721_3_, p_147721_4_, 4),
            RenderPass.shouldSideBeRendered(block, this.blockAccess, p_147721_2_ + 1, p_147721_3_, p_147721_4_, 5) };
        if (!shouldSideBeRendered && !shouldSideBeRendered2
            && !firstBoolArray[0]
            && !firstBoolArray[1]
            && !firstBoolArray[2]
            && !firstBoolArray[3]) {
            return false;
        }
        boolean b = false;
        final float fourthFloat = 0.5f;
        final float fifthFloat = 1.0f;
        final float sixthFloat = 0.8f;
        final float seventhFloat = 0.6f;
        final double firstDouble = 0.0;
        final double secondDouble = 1.0;
        final Material o = block.getMaterial();
        final int secondInt = this.blockAccess.getBlockMetadata(p_147721_2_, p_147721_3_, p_147721_4_);
        double thirdDouble = this.getLiquidHeight(p_147721_2_, p_147721_3_, p_147721_4_, o);
        double fourthDouble = this.getLiquidHeight(p_147721_2_, p_147721_3_, p_147721_4_ + 1, o);
        double fifthDouble = this.getLiquidHeight(p_147721_2_ + 1, p_147721_3_, p_147721_4_ + 1, o);
        double sixthDouble = this.getLiquidHeight(p_147721_2_ + 1, p_147721_3_, p_147721_4_, o);
        final double seventhDouble = 0.0010000000474974513;
        if (this.renderAllFaces || shouldSideBeRendered) {
            b = true;
            IIcon icon1 = (this.blockAccess == null) ? this.getBlockIconFromSideAndMetadata(block, 1, secondInt)
                : this.getBlockIcon(block, this.blockAccess, p_147721_2_, p_147721_3_, p_147721_4_, 1);
            final float flowDirection = (float) BlockLiquid
                .getFlowDirection(this.blockAccess, p_147721_2_, p_147721_3_, p_147721_4_, o);
            if (flowDirection > -999.0f) {
                icon1 = ((this.blockAccess == null) ? this.getBlockIconFromSideAndMetadata(block, 2, secondInt)
                    : this.getBlockIcon(block, this.blockAccess, p_147721_2_, p_147721_3_, p_147721_4_, 2));
            }
            thirdDouble -= seventhDouble;
            fourthDouble -= seventhDouble;
            fifthDouble -= seventhDouble;
            sixthDouble -= seventhDouble;
            double double8;
            double double9;
            double double10;
            double double11;
            double double12;
            double double13;
            double double14;
            double double15;
            if (flowDirection < -999.0f) {
                double8 = icon1.getInterpolatedU(0.0);
                double9 = icon1.getInterpolatedV(0.0);
                double10 = double8;
                double11 = icon1.getInterpolatedV(16.0);
                double12 = icon1.getInterpolatedU(16.0);
                double13 = double11;
                double14 = double12;
                double15 = double9;
            } else {
                final float float8 = MathHelper.sin(flowDirection) * 0.25f;
                final float float9 = MathHelper.cos(flowDirection) * 0.25f;
                double8 = icon1.getInterpolatedU(8.0f + (-float9 - float8) * 16.0f);
                double9 = icon1.getInterpolatedV(8.0f + (-float9 + float8) * 16.0f);
                double10 = icon1.getInterpolatedU(8.0f + (-float9 + float8) * 16.0f);
                double11 = icon1.getInterpolatedV(8.0f + (float9 + float8) * 16.0f);
                double12 = icon1.getInterpolatedU(8.0f + (float9 + float8) * 16.0f);
                double13 = icon1.getInterpolatedV(8.0f + (float9 - float8) * 16.0f);
                double14 = icon1.getInterpolatedU(8.0f + (float9 - float8) * 16.0f);
                double15 = icon1.getInterpolatedV(8.0f + (-float9 - float8) * 16.0f);
            }
            tessellator.setBrightness(
                block.getMixedBrightnessForBlock(this.blockAccess, p_147721_2_, p_147721_3_, p_147721_4_));
            if (!(ColorizeBlock.isSmooth = ColorizeBlock.setupBlockSmoothing(
                (RenderBlocks) (Object) this,
                block,
                this.blockAccess,
                p_147721_2_,
                p_147721_3_,
                p_147721_4_,
                1 + 6))) {
                tessellator
                    .setColorOpaque_F(fifthFloat * firstFloat, fifthFloat * secondFloat, fifthFloat * thirdFloat);
            }
            if (ColorizeBlock.isSmooth) {
                tessellator.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
                tessellator
                    .addVertexWithUV(p_147721_2_ + 0, p_147721_3_ + thirdDouble, p_147721_4_ + 0, double8, double9);
                tessellator
                    .setColorOpaque_F(this.colorRedBottomLeft, this.colorGreenBottomLeft, this.colorBlueBottomLeft);
                tessellator
                    .addVertexWithUV(p_147721_2_ + 0, p_147721_3_ + fourthDouble, p_147721_4_ + 1, double10, double11);
                tessellator
                    .setColorOpaque_F(this.colorRedBottomRight, this.colorGreenBottomRight, this.colorBlueBottomRight);
                tessellator
                    .addVertexWithUV(p_147721_2_ + 1, p_147721_3_ + fifthDouble, p_147721_4_ + 1, double12, double13);
                tessellator.setColorOpaque_F(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight);
                tessellator
                    .addVertexWithUV(p_147721_2_ + 1, p_147721_3_ + sixthDouble, p_147721_4_ + 0, double14, double15);
                tessellator.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
                tessellator
                    .addVertexWithUV(p_147721_2_ + 0, p_147721_3_ + thirdDouble, p_147721_4_ + 0, double8, double9);
                tessellator.setColorOpaque_F(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight);
                tessellator
                    .addVertexWithUV(p_147721_2_ + 1, p_147721_3_ + sixthDouble, p_147721_4_ + 0, double14, double15);
                tessellator
                    .setColorOpaque_F(this.colorRedBottomRight, this.colorGreenBottomRight, this.colorBlueBottomRight);
                tessellator
                    .addVertexWithUV(p_147721_2_ + 1, p_147721_3_ + fifthDouble, p_147721_4_ + 1, double12, double13);
                tessellator
                    .setColorOpaque_F(this.colorRedBottomLeft, this.colorGreenBottomLeft, this.colorBlueBottomLeft);
                tessellator
                    .addVertexWithUV(p_147721_2_ + 0, p_147721_3_ + fourthDouble, p_147721_4_ + 1, double10, double11);
            } else {
                tessellator
                    .addVertexWithUV(p_147721_2_ + 0, p_147721_3_ + thirdDouble, p_147721_4_ + 0, double8, double9);
                tessellator
                    .addVertexWithUV(p_147721_2_ + 0, p_147721_3_ + fourthDouble, p_147721_4_ + 1, double10, double11);
                tessellator
                    .addVertexWithUV(p_147721_2_ + 1, p_147721_3_ + fifthDouble, p_147721_4_ + 1, double12, double13);
                tessellator
                    .addVertexWithUV(p_147721_2_ + 1, p_147721_3_ + sixthDouble, p_147721_4_ + 0, double14, double15);
                tessellator
                    .addVertexWithUV(p_147721_2_ + 0, p_147721_3_ + thirdDouble, p_147721_4_ + 0, double8, double9);
                tessellator
                    .addVertexWithUV(p_147721_2_ + 1, p_147721_3_ + sixthDouble, p_147721_4_ + 0, double14, double15);
                tessellator
                    .addVertexWithUV(p_147721_2_ + 1, p_147721_3_ + fifthDouble, p_147721_4_ + 1, double12, double13);
                tessellator
                    .addVertexWithUV(p_147721_2_ + 0, p_147721_3_ + fourthDouble, p_147721_4_ + 1, double10, double11);
            }
        }
        if (this.renderAllFaces || shouldSideBeRendered2) {
            tessellator.setBrightness(
                block.getMixedBrightnessForBlock(this.blockAccess, p_147721_2_, p_147721_3_ - 1, p_147721_4_));
            if (!(ColorizeBlock.isSmooth = ColorizeBlock.setupBlockSmoothing(
                (RenderBlocks) (Object) this,
                block,
                this.blockAccess,
                p_147721_2_,
                p_147721_3_,
                p_147721_4_,
                0 + 6))) {
                tessellator
                    .setColorOpaque_F(fourthFloat * firstFloat, fourthFloat * secondFloat, fourthFloat * thirdFloat);
            }
            if (ColorizeBlock.isSmooth) {
                this.enableAO = true;
            }
            final IIcon icon2 = (this.blockAccess == null) ? this.getBlockIconFromSide(block, 0)
                : this.getBlockIcon(block, this.blockAccess, p_147721_2_, p_147721_3_, p_147721_4_, 0);
            this.enableAO = false;
            this.renderFaceYNeg(block, p_147721_2_, p_147721_3_ + seventhDouble, p_147721_4_, icon2);
            b = true;
        }
        for (int counter1 = 0; counter1 < 4; ++counter1) {
            int int3 = p_147721_2_;
            int int4 = p_147721_4_;
            if (counter1 == 0) {
                --int4;
            }
            if (counter1 == 1) {
                ++int4;
            }
            if (counter1 == 2) {
                --int3;
            }
            if (counter1 == 3) {
                ++int3;
            }
            final IIcon icon3 = (this.blockAccess == null)
                ? this.getBlockIconFromSideAndMetadata(block, counter1 + 2, secondInt)
                : this.getBlockIcon(block, this.blockAccess, p_147721_2_, p_147721_3_, p_147721_4_, counter1 + 2);
            if (this.renderAllFaces || firstBoolArray[counter1]) {
                double double16;
                double double17;
                double double18;
                double double19;
                double double20;
                double double21;
                if (counter1 == 0) {
                    double16 = thirdDouble;
                    double17 = sixthDouble;
                    double18 = p_147721_2_;
                    double19 = p_147721_2_ + 1;
                    double20 = p_147721_4_ + seventhDouble;
                    double21 = p_147721_4_ + seventhDouble;
                } else if (counter1 == 1) {
                    double16 = fifthDouble;
                    double17 = fourthDouble;
                    double18 = p_147721_2_ + 1;
                    double19 = p_147721_2_;
                    double20 = p_147721_4_ + 1 - seventhDouble;
                    double21 = p_147721_4_ + 1 - seventhDouble;
                } else if (counter1 == 2) {
                    double16 = fourthDouble;
                    double17 = thirdDouble;
                    double18 = p_147721_2_ + seventhDouble;
                    double19 = p_147721_2_ + seventhDouble;
                    double20 = p_147721_4_ + 1;
                    double21 = p_147721_4_;
                } else {
                    double16 = sixthDouble;
                    double17 = fifthDouble;
                    double18 = p_147721_2_ + 1 - seventhDouble;
                    double19 = p_147721_2_ + 1 - seventhDouble;
                    double20 = p_147721_4_;
                    double21 = p_147721_4_ + 1;
                }
                b = true;
                final float float10 = icon3.getInterpolatedU(0.0);
                final float float11 = icon3.getInterpolatedU(8.0);
                final float float12 = icon3.getInterpolatedV((1.0 - double16) * 16.0 * 0.5);
                final float float13 = icon3.getInterpolatedV((1.0 - double17) * 16.0 * 0.5);
                final float float14 = icon3.getInterpolatedV(8.0);
                tessellator.setBrightness(block.getMixedBrightnessForBlock(this.blockAccess, int3, p_147721_3_, int4));
                final float float15 = 1.0f * ((counter1 < 2) ? sixthFloat : seventhFloat);
                if (!(ColorizeBlock.isSmooth = ColorizeBlock.setupBlockSmoothing(
                    (RenderBlocks) (Object) this,
                    block,
                    this.blockAccess,
                    p_147721_2_,
                    p_147721_3_,
                    p_147721_4_,
                    counter1 + 2 + 6))) {
                    tessellator.setColorOpaque_F(
                        fifthFloat * float15 * firstFloat,
                        fifthFloat * float15 * secondFloat,
                        fifthFloat * float15 * thirdFloat);
                }
                if (ColorizeBlock.isSmooth) {
                    tessellator.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
                    tessellator.addVertexWithUV(double18, p_147721_3_ + double16, double20, float10, float12);
                    tessellator
                        .setColorOpaque_F(this.colorRedBottomLeft, this.colorGreenBottomLeft, this.colorBlueBottomLeft);
                    tessellator.addVertexWithUV(double19, p_147721_3_ + double17, double21, float11, float13);
                    tessellator.setColorOpaque_F(
                        this.colorRedBottomRight,
                        this.colorGreenBottomRight,
                        this.colorBlueBottomRight);
                    tessellator.addVertexWithUV(double19, p_147721_3_ + 0, double21, float11, float14);
                    tessellator
                        .setColorOpaque_F(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight);
                    tessellator.addVertexWithUV(double18, p_147721_3_ + 0, double20, float10, float14);
                    tessellator
                        .setColorOpaque_F(this.colorRedTopRight, this.colorGreenTopRight, this.colorBlueTopRight);
                    tessellator.addVertexWithUV(double18, p_147721_3_ + 0, double20, float10, float14);
                    tessellator.setColorOpaque_F(
                        this.colorRedBottomRight,
                        this.colorGreenBottomRight,
                        this.colorBlueBottomRight);
                    tessellator.addVertexWithUV(double19, p_147721_3_ + 0, double21, float11, float14);
                    tessellator
                        .setColorOpaque_F(this.colorRedBottomLeft, this.colorGreenBottomLeft, this.colorBlueBottomLeft);
                    tessellator.addVertexWithUV(double19, p_147721_3_ + double17, double21, float11, float13);
                    tessellator.setColorOpaque_F(this.colorRedTopLeft, this.colorGreenTopLeft, this.colorBlueTopLeft);
                    tessellator.addVertexWithUV(double18, p_147721_3_ + double16, double20, float10, float12);
                } else {
                    tessellator.addVertexWithUV(double18, p_147721_3_ + double16, double20, float10, float12);
                    tessellator.addVertexWithUV(double19, p_147721_3_ + double17, double21, float11, float13);
                    tessellator.addVertexWithUV(double19, p_147721_3_ + 0, double21, float11, float14);
                    tessellator.addVertexWithUV(double18, p_147721_3_ + 0, double20, float10, float14);
                    tessellator.addVertexWithUV(double18, p_147721_3_ + 0, double20, float10, float14);
                    tessellator.addVertexWithUV(double19, p_147721_3_ + 0, double21, float11, float14);
                    tessellator.addVertexWithUV(double19, p_147721_3_ + double17, double21, float11, float13);
                    tessellator.addVertexWithUV(double18, p_147721_3_ + double16, double20, float10, float12);
                }
            }
        }
        this.renderMinY = firstDouble;
        this.renderMaxY = secondDouble;
        return b;
    }
    // If I was able to access ordinal number the duplication wouldn't be necessary
    @WrapWithCondition(
        method = "renderBlockSandFalling(Lnet/minecraft/block/Block;Lnet/minecraft/world/World;IIII)V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/renderer/Tessellator;setColorOpaque_F(FFF)V",
            ordinal = 0))
    private boolean modifyRenderBlockSandFalling0(Tessellator tessellator, float x, float y, float z, Block block,
        World world) {
        return !ColorizeBlock
            .setupBlockSmoothing((RenderBlocks) (Object) this, block, this.blockAccess, (int) x, (int) y, (int) z, 0);
    }

    @WrapWithCondition(
        method = "renderBlockSandFalling(Lnet/minecraft/block/Block;Lnet/minecraft/world/World;IIII)V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/renderer/Tessellator;setColorOpaque_F(FFF)V",
            ordinal = 1))
    private boolean modifyRenderBlockSandFalling1(Tessellator tessellator, float x, float y, float z, Block block,
        World world) {
        return !ColorizeBlock
            .setupBlockSmoothing((RenderBlocks) (Object) this, block, this.blockAccess, (int) x, (int) y, (int) z, 1);
    }

    @WrapWithCondition(
        method = "renderBlockSandFalling(Lnet/minecraft/block/Block;Lnet/minecraft/world/World;IIII)V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/renderer/Tessellator;setColorOpaque_F(FFF)V",
            ordinal = 2))
    private boolean modifyRenderBlockSandFalling2(Tessellator tessellator, float x, float y, float z, Block block,
        World world) {
        return !ColorizeBlock
            .setupBlockSmoothing((RenderBlocks) (Object) this, block, this.blockAccess, (int) x, (int) y, (int) z, 2);
    }

    @WrapWithCondition(
        method = "renderBlockSandFalling(Lnet/minecraft/block/Block;Lnet/minecraft/world/World;IIII)V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/renderer/Tessellator;setColorOpaque_F(FFF)V",
            ordinal = 3))
    private boolean modifyRenderBlockSandFalling3(Tessellator tessellator, float x, float y, float z, Block block,
        World world) {
        return !ColorizeBlock
            .setupBlockSmoothing((RenderBlocks) (Object) this, block, this.blockAccess, (int) x, (int) y, (int) z, 3);
    }

    @WrapWithCondition(
        method = "renderBlockSandFalling(Lnet/minecraft/block/Block;Lnet/minecraft/world/World;IIII)V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/renderer/Tessellator;setColorOpaque_F(FFF)V",
            ordinal = 4))
    private boolean modifyRenderBlockSandFalling4(Tessellator tessellator, float x, float y, float z, Block block,
        World world) {
        return !ColorizeBlock
            .setupBlockSmoothing((RenderBlocks) (Object) this, block, this.blockAccess, (int) x, (int) y, (int) z, 4);
    }

    @WrapWithCondition(
        method = "renderBlockSandFalling(Lnet/minecraft/block/Block;Lnet/minecraft/world/World;IIII)V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/renderer/Tessellator;setColorOpaque_F(FFF)V",
            ordinal = 5))
    private boolean modifyRenderBlockSandFalling5(Tessellator tessellator, float x, float y, float z, Block block,
        World world) {
        return !ColorizeBlock
            .setupBlockSmoothing((RenderBlocks) (Object) this, block, this.blockAccess, (int) x, (int) y, (int) z, 5);
    }

    @Inject(
        method = "renderStandardBlock(Lnet/minecraft/block/Block;III)Z",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;isAmbientOcclusionEnabled()Z"))
    private void modifyRenderStandardBlock(Block block, int x, int y, int z, CallbackInfoReturnable<Boolean> cir) {

        // TODO: capture local variables to prevent double math
        int l = block.colorMultiplier(this.blockAccess, x, y, z);
        float f = (float) (l >> 16 & 255) / 255.0F;
        float f1 = (float) (l >> 8 & 255) / 255.0F;
        float f2 = (float) (l & 255) / 255.0F;

        if (EntityRenderer.anaglyphEnable) {
            float f3 = (f * 30.0F + f1 * 59.0F + f2 * 11.0F) / 100.0F;
            float f4 = (f * 30.0F + f1 * 70.0F) / 100.0F;
            float f5 = (f * 30.0F + f2 * 70.0F) / 100.0F;
            f = f3;
            f1 = f4;
            f2 = f5;
        }
        RenderBlocksUtils
            .setupColorMultiplier(block, this.blockAccess, x, y, z, this.hasOverrideBlockTexture(), f, f1, f2);
    }

    /**
     * @author Mist475 (adapted from Paul Rupe)
     * @reason I forgor.
     *         TODO: look at again for compatability
     */
    @Overwrite
    public boolean renderStandardBlockWithAmbientOcclusion(Block block, int x, int y, int z, float red, float green,
        float blue) {
        this.enableAO = true;
        boolean flag = false;

        int l = block.getMixedBrightnessForBlock(this.blockAccess, x, y, z);
        Tessellator tessellator = Tessellator.instance;
        tessellator.setBrightness(983055);

        boolean flag2;
        boolean flag3;
        boolean flag4;
        boolean flag5;
        int i1;
        float f7;

        // face 1
        if (this.renderAllFaces || RenderPass.shouldSideBeRendered(block, this.blockAccess, x, y, z, 0)) {
            if (this.renderMinY <= 0.0D) {
                --y;
            }

            this.aoBrightnessXYNN = block.getMixedBrightnessForBlock(this.blockAccess, x - 1, y, z);
            this.aoBrightnessYZNN = block.getMixedBrightnessForBlock(this.blockAccess, x, y, z - 1);
            this.aoBrightnessYZNP = block.getMixedBrightnessForBlock(this.blockAccess, x, y, z + 1);
            this.aoBrightnessXYPN = block.getMixedBrightnessForBlock(this.blockAccess, x + 1, y, z);
            this.aoLightValueScratchXYNN = this.blockAccess.getBlock(x - 1, y, z)
                .getAmbientOcclusionLightValue();
            this.aoLightValueScratchYZNN = this.blockAccess.getBlock(x, y, z - 1)
                .getAmbientOcclusionLightValue();
            this.aoLightValueScratchYZNP = this.blockAccess.getBlock(x, y, z + 1)
                .getAmbientOcclusionLightValue();
            this.aoLightValueScratchXYPN = this.blockAccess.getBlock(x + 1, y, z)
                .getAmbientOcclusionLightValue();
            flag2 = this.blockAccess.getBlock(x + 1, y - 1, z)
                .getCanBlockGrass();
            flag3 = this.blockAccess.getBlock(x - 1, y - 1, z)
                .getCanBlockGrass();
            flag4 = this.blockAccess.getBlock(x, y - 1, z + 1)
                .getCanBlockGrass();
            flag5 = this.blockAccess.getBlock(x, y - 1, z - 1)
                .getCanBlockGrass();

            if (!flag5 && !flag3) {
                this.aoLightValueScratchXYZNNN = this.aoLightValueScratchXYNN;
                this.aoBrightnessXYZNNN = this.aoBrightnessXYNN;
            } else {
                this.aoLightValueScratchXYZNNN = this.blockAccess.getBlock(x - 1, y, z - 1)
                    .getAmbientOcclusionLightValue();
                this.aoBrightnessXYZNNN = block.getMixedBrightnessForBlock(this.blockAccess, x - 1, y, z - 1);
            }

            if (!flag4 && !flag3) {
                this.aoLightValueScratchXYZNNP = this.aoLightValueScratchXYNN;
                this.aoBrightnessXYZNNP = this.aoBrightnessXYNN;
            } else {
                this.aoLightValueScratchXYZNNP = this.blockAccess.getBlock(x - 1, y, z + 1)
                    .getAmbientOcclusionLightValue();
                this.aoBrightnessXYZNNP = block.getMixedBrightnessForBlock(this.blockAccess, x - 1, y, z + 1);
            }

            if (!flag5 && !flag2) {
                this.aoLightValueScratchXYZPNN = this.aoLightValueScratchXYPN;
                this.aoBrightnessXYZPNN = this.aoBrightnessXYPN;
            } else {
                this.aoLightValueScratchXYZPNN = this.blockAccess.getBlock(x + 1, y, z - 1)
                    .getAmbientOcclusionLightValue();
                this.aoBrightnessXYZPNN = block.getMixedBrightnessForBlock(this.blockAccess, x + 1, y, z - 1);
            }

            if (!flag4 && !flag2) {
                this.aoLightValueScratchXYZPNP = this.aoLightValueScratchXYPN;
                this.aoBrightnessXYZPNP = this.aoBrightnessXYPN;
            } else {
                this.aoLightValueScratchXYZPNP = this.blockAccess.getBlock(x + 1, y, z + 1)
                    .getAmbientOcclusionLightValue();
                this.aoBrightnessXYZPNP = block.getMixedBrightnessForBlock(this.blockAccess, x + 1, y, z + 1);
            }

            if (this.renderMinY <= 0.0D) {
                ++y;
            }

            i1 = l;

            if (this.renderMinY <= 0.0D || !this.blockAccess.getBlock(x, y - 1, z)
                .isOpaqueCube()) {
                i1 = block.getMixedBrightnessForBlock(this.blockAccess, x, y - 1, z);
            }

            f7 = this.blockAccess.getBlock(x, y - 1, z)
                .getAmbientOcclusionLightValue();

            // topLeft f3
            float topLeft = (this.aoLightValueScratchXYZNNP + this.aoLightValueScratchXYNN
                + this.aoLightValueScratchYZNP
                + f7) / 4.0F;
            // topRight f6
            float topRight = (this.aoLightValueScratchYZNP + f7
                + this.aoLightValueScratchXYZPNP
                + this.aoLightValueScratchXYPN) / 4.0F;
            // bottomRight f5
            float bottomRight = (f7 + this.aoLightValueScratchYZNN
                + this.aoLightValueScratchXYPN
                + this.aoLightValueScratchXYZPNN) / 4.0F;
            // bottomLeft f4
            float bottomLeft = (this.aoLightValueScratchXYNN + this.aoLightValueScratchXYZNNN
                + f7
                + this.aoLightValueScratchYZNN) / 4.0F;

            this.brightnessTopLeft = this
                .getAoBrightness(this.aoBrightnessXYZNNP, this.aoBrightnessXYNN, this.aoBrightnessYZNP, i1);
            this.brightnessTopRight = this
                .getAoBrightness(this.aoBrightnessYZNP, this.aoBrightnessXYZPNP, this.aoBrightnessXYPN, i1);
            this.brightnessBottomRight = this
                .getAoBrightness(this.aoBrightnessYZNN, this.aoBrightnessXYPN, this.aoBrightnessXYZPNN, i1);
            this.brightnessBottomLeft = this
                .getAoBrightness(this.aoBrightnessXYNN, this.aoBrightnessXYZNNN, this.aoBrightnessYZNN, i1);

            if (!ColorizeBlock.setupBlockSmoothing(
                (RenderBlocks) (Object) this,
                block,
                this.blockAccess,
                x,
                y,
                z,
                0,
                topLeft,
                bottomLeft,
                bottomRight,
                topRight)) {
                if (RenderBlocksUtils.useColorMultiplier(0)) {
                    this.colorRedTopRight = this.colorRedBottomRight = this.colorRedBottomLeft = this.colorRedTopLeft = red
                        * RenderPass.getAOBaseMultiplier(0.5f);
                    this.colorGreenTopRight = this.colorGreenBottomRight = this.colorGreenBottomLeft = this.colorGreenTopLeft = green
                        * RenderPass.getAOBaseMultiplier(0.5f);
                    this.colorBlueTopRight = this.colorBlueBottomRight = this.colorBlueBottomLeft = this.colorBlueTopLeft = blue
                        * RenderPass.getAOBaseMultiplier(0.5f);
                } else {
                    this.colorRedTopRight = colorRedBottomRight = colorRedBottomLeft = colorRedTopLeft = RenderPass
                        .getAOBaseMultiplier(0.5f);
                    this.colorGreenTopRight = colorGreenBottomRight = colorGreenBottomLeft = colorGreenTopLeft = RenderPass
                        .getAOBaseMultiplier(0.5f);
                    this.colorBlueTopRight = colorBlueBottomRight = colorBlueBottomLeft = colorBlueTopLeft = RenderPass
                        .getAOBaseMultiplier(0.5f);
                }
                this.colorRedTopLeft *= topLeft;
                this.colorGreenTopLeft *= topLeft;
                this.colorBlueTopLeft *= topLeft;
                this.colorRedBottomLeft *= bottomLeft;
                this.colorGreenBottomLeft *= bottomLeft;
                this.colorBlueBottomLeft *= bottomLeft;
                this.colorRedBottomRight *= bottomRight;
                this.colorGreenBottomRight *= bottomRight;
                this.colorBlueBottomRight *= bottomRight;
                this.colorRedTopRight *= topRight;
                this.colorGreenTopRight *= topRight;
                this.colorBlueTopRight *= topRight;
            }

            this.renderFaceYNeg(block, x, y, z, this.getBlockIcon(block, this.blockAccess, x, y, z, 0));
            flag = true;
        }

        // face 2
        if (this.renderAllFaces || RenderPass.shouldSideBeRendered(block, this.blockAccess, x, y + 1, z, 1)) {
            if (this.renderMaxY >= 1.0D) {
                ++y;
            }

            this.aoBrightnessXYNP = block.getMixedBrightnessForBlock(this.blockAccess, x - 1, y, z);
            this.aoBrightnessXYPP = block.getMixedBrightnessForBlock(this.blockAccess, x + 1, y, z);
            this.aoBrightnessYZPN = block.getMixedBrightnessForBlock(this.blockAccess, x, y, z - 1);
            this.aoBrightnessYZPP = block.getMixedBrightnessForBlock(this.blockAccess, x, y, z + 1);
            this.aoLightValueScratchXYNP = this.blockAccess.getBlock(x - 1, y, z)
                .getAmbientOcclusionLightValue();
            this.aoLightValueScratchXYPP = this.blockAccess.getBlock(x + 1, y, z)
                .getAmbientOcclusionLightValue();
            this.aoLightValueScratchYZPN = this.blockAccess.getBlock(x, y, z - 1)
                .getAmbientOcclusionLightValue();
            this.aoLightValueScratchYZPP = this.blockAccess.getBlock(x, y, z + 1)
                .getAmbientOcclusionLightValue();
            flag2 = this.blockAccess.getBlock(x + 1, y + 1, z)
                .getCanBlockGrass();
            flag3 = this.blockAccess.getBlock(x - 1, y + 1, z)
                .getCanBlockGrass();
            flag4 = this.blockAccess.getBlock(x, y + 1, z + 1)
                .getCanBlockGrass();
            flag5 = this.blockAccess.getBlock(x, y + 1, z - 1)
                .getCanBlockGrass();

            if (!flag5 && !flag3) {
                this.aoLightValueScratchXYZNPN = this.aoLightValueScratchXYNP;
                this.aoBrightnessXYZNPN = this.aoBrightnessXYNP;
            } else {
                this.aoLightValueScratchXYZNPN = this.blockAccess.getBlock(x - 1, y, z - 1)
                    .getAmbientOcclusionLightValue();
                this.aoBrightnessXYZNPN = block.getMixedBrightnessForBlock(this.blockAccess, x - 1, y, z - 1);
            }

            if (!flag5 && !flag2) {
                this.aoLightValueScratchXYZPPN = this.aoLightValueScratchXYPP;
                this.aoBrightnessXYZPPN = this.aoBrightnessXYPP;
            } else {
                this.aoLightValueScratchXYZPPN = this.blockAccess.getBlock(x + 1, y, z - 1)
                    .getAmbientOcclusionLightValue();
                this.aoBrightnessXYZPPN = block.getMixedBrightnessForBlock(this.blockAccess, x + 1, y, z - 1);
            }

            if (!flag4 && !flag3) {
                this.aoLightValueScratchXYZNPP = this.aoLightValueScratchXYNP;
                this.aoBrightnessXYZNPP = this.aoBrightnessXYNP;
            } else {
                this.aoLightValueScratchXYZNPP = this.blockAccess.getBlock(x - 1, y, z + 1)
                    .getAmbientOcclusionLightValue();
                this.aoBrightnessXYZNPP = block.getMixedBrightnessForBlock(this.blockAccess, x - 1, y, z + 1);
            }

            if (!flag4 && !flag2) {
                this.aoLightValueScratchXYZPPP = this.aoLightValueScratchXYPP;
                this.aoBrightnessXYZPPP = this.aoBrightnessXYPP;
            } else {
                this.aoLightValueScratchXYZPPP = this.blockAccess.getBlock(x + 1, y, z + 1)
                    .getAmbientOcclusionLightValue();
                this.aoBrightnessXYZPPP = block.getMixedBrightnessForBlock(this.blockAccess, x + 1, y, z + 1);
            }

            if (this.renderMaxY >= 1.0D) {
                --y;
            }

            i1 = l;

            if (this.renderMaxY >= 1.0D || !this.blockAccess.getBlock(x, y + 1, z)
                .isOpaqueCube()) {
                i1 = block.getMixedBrightnessForBlock(this.blockAccess, x, y + 1, z);
            }

            f7 = this.blockAccess.getBlock(x, y + 1, z)
                .getAmbientOcclusionLightValue();

            float f6 = (this.aoLightValueScratchXYZNPP + this.aoLightValueScratchXYNP
                + this.aoLightValueScratchYZPP
                + f7) / 4.0F;
            float f3 = (this.aoLightValueScratchYZPP + f7
                + this.aoLightValueScratchXYZPPP
                + this.aoLightValueScratchXYPP) / 4.0F;
            float f4 = (f7 + this.aoLightValueScratchYZPN
                + this.aoLightValueScratchXYPP
                + this.aoLightValueScratchXYZPPN) / 4.0F;
            float f5 = (this.aoLightValueScratchXYNP + this.aoLightValueScratchXYZNPN
                + f7
                + this.aoLightValueScratchYZPN) / 4.0F;

            this.brightnessTopRight = this
                .getAoBrightness(this.aoBrightnessXYZNPP, this.aoBrightnessXYNP, this.aoBrightnessYZPP, i1);
            this.brightnessTopLeft = this
                .getAoBrightness(this.aoBrightnessYZPP, this.aoBrightnessXYZPPP, this.aoBrightnessXYPP, i1);
            this.brightnessBottomLeft = this
                .getAoBrightness(this.aoBrightnessYZPN, this.aoBrightnessXYPP, this.aoBrightnessXYZPPN, i1);
            this.brightnessBottomRight = this
                .getAoBrightness(this.aoBrightnessXYNP, this.aoBrightnessXYZNPN, this.aoBrightnessYZPN, i1);
            // This block is different from the rest
            if (!ColorizeBlock.setupBlockSmoothing(
                (RenderBlocks) (Object) this,
                block,
                this.blockAccess,
                x,
                y,
                z,
                1,
                f3,
                f4,
                f5,
                f6)) {
                this.colorRedTopLeft = this.colorRedBottomLeft = this.colorRedBottomRight = this.colorRedTopRight = red;
                this.colorGreenTopLeft = this.colorGreenBottomLeft = this.colorGreenBottomRight = this.colorGreenTopRight = green;
                this.colorBlueTopLeft = this.colorBlueBottomLeft = this.colorBlueBottomRight = this.colorBlueTopRight = blue;
                this.colorRedTopLeft *= f3;
                this.colorGreenTopLeft *= f3;
                this.colorBlueTopLeft *= f3;
                this.colorRedBottomLeft *= f4;
                this.colorGreenBottomLeft *= f4;
                this.colorBlueBottomLeft *= f4;
                this.colorRedBottomRight *= f5;
                this.colorGreenBottomRight *= f5;
                this.colorBlueBottomRight *= f5;
                this.colorRedTopRight *= f6;
                this.colorGreenTopRight *= f6;
                this.colorBlueTopRight *= f6;
            }
            this.renderFaceYPos(block, x, y, z, this.getBlockIcon(block, this.blockAccess, x, y, z, 1));
            flag = true;
        }

        // face 3
        IIcon iicon;

        if (this.renderAllFaces || RenderPass.shouldSideBeRendered(block, this.blockAccess, x, y, z - 1, 2)) {
            if (this.renderMinZ <= 0.0D) {
                --z;
            }

            this.aoLightValueScratchXZNN = this.blockAccess.getBlock(x - 1, y, z)
                .getAmbientOcclusionLightValue();
            this.aoLightValueScratchYZNN = this.blockAccess.getBlock(x, y - 1, z)
                .getAmbientOcclusionLightValue();
            this.aoLightValueScratchYZPN = this.blockAccess.getBlock(x, y + 1, z)
                .getAmbientOcclusionLightValue();
            this.aoLightValueScratchXZPN = this.blockAccess.getBlock(x + 1, y, z)
                .getAmbientOcclusionLightValue();
            this.aoBrightnessXZNN = block.getMixedBrightnessForBlock(this.blockAccess, x - 1, y, z);
            this.aoBrightnessYZNN = block.getMixedBrightnessForBlock(this.blockAccess, x, y - 1, z);
            this.aoBrightnessYZPN = block.getMixedBrightnessForBlock(this.blockAccess, x, y + 1, z);
            this.aoBrightnessXZPN = block.getMixedBrightnessForBlock(this.blockAccess, x + 1, y, z);
            flag2 = this.blockAccess.getBlock(x + 1, y, z - 1)
                .getCanBlockGrass();
            flag3 = this.blockAccess.getBlock(x - 1, y, z - 1)
                .getCanBlockGrass();
            flag4 = this.blockAccess.getBlock(x, y + 1, z - 1)
                .getCanBlockGrass();
            flag5 = this.blockAccess.getBlock(x, y - 1, z - 1)
                .getCanBlockGrass();

            if (!flag3 && !flag5) {
                this.aoLightValueScratchXYZNNN = this.aoLightValueScratchXZNN;
                this.aoBrightnessXYZNNN = this.aoBrightnessXZNN;
            } else {
                this.aoLightValueScratchXYZNNN = this.blockAccess.getBlock(x - 1, y - 1, z)
                    .getAmbientOcclusionLightValue();
                this.aoBrightnessXYZNNN = block.getMixedBrightnessForBlock(this.blockAccess, x - 1, y - 1, z);
            }

            if (!flag3 && !flag4) {
                this.aoLightValueScratchXYZNPN = this.aoLightValueScratchXZNN;
                this.aoBrightnessXYZNPN = this.aoBrightnessXZNN;
            } else {
                this.aoLightValueScratchXYZNPN = this.blockAccess.getBlock(x - 1, y + 1, z)
                    .getAmbientOcclusionLightValue();
                this.aoBrightnessXYZNPN = block.getMixedBrightnessForBlock(this.blockAccess, x - 1, y + 1, z);
            }

            if (!flag2 && !flag5) {
                this.aoLightValueScratchXYZPNN = this.aoLightValueScratchXZPN;
                this.aoBrightnessXYZPNN = this.aoBrightnessXZPN;
            } else {
                this.aoLightValueScratchXYZPNN = this.blockAccess.getBlock(x + 1, y - 1, z)
                    .getAmbientOcclusionLightValue();
                this.aoBrightnessXYZPNN = block.getMixedBrightnessForBlock(this.blockAccess, x + 1, y - 1, z);
            }

            if (!flag2 && !flag4) {
                this.aoLightValueScratchXYZPPN = this.aoLightValueScratchXZPN;
                this.aoBrightnessXYZPPN = this.aoBrightnessXZPN;
            } else {
                this.aoLightValueScratchXYZPPN = this.blockAccess.getBlock(x + 1, y + 1, z)
                    .getAmbientOcclusionLightValue();
                this.aoBrightnessXYZPPN = block.getMixedBrightnessForBlock(this.blockAccess, x + 1, y + 1, z);
            }

            if (this.renderMinZ <= 0.0D) {
                ++z;
            }

            i1 = l;

            if (this.renderMinZ <= 0.0D || !this.blockAccess.getBlock(x, y, z - 1)
                .isOpaqueCube()) {
                i1 = block.getMixedBrightnessForBlock(this.blockAccess, x, y, z - 1);
            }

            f7 = this.blockAccess.getBlock(x, y, z - 1)
                .getAmbientOcclusionLightValue();

            float f3 = (this.aoLightValueScratchXZNN + this.aoLightValueScratchXYZNPN
                + f7
                + this.aoLightValueScratchYZPN) / 4.0F;
            float f4 = (f7 + this.aoLightValueScratchYZPN
                + this.aoLightValueScratchXZPN
                + this.aoLightValueScratchXYZPPN) / 4.0F;
            float f5 = (this.aoLightValueScratchYZNN + f7
                + this.aoLightValueScratchXYZPNN
                + this.aoLightValueScratchXZPN) / 4.0F;
            float f6 = (this.aoLightValueScratchXYZNNN + this.aoLightValueScratchXZNN
                + this.aoLightValueScratchYZNN
                + f7) / 4.0F;

            this.brightnessTopLeft = this
                .getAoBrightness(this.aoBrightnessXZNN, this.aoBrightnessXYZNPN, this.aoBrightnessYZPN, i1);
            this.brightnessBottomLeft = this
                .getAoBrightness(this.aoBrightnessYZPN, this.aoBrightnessXZPN, this.aoBrightnessXYZPPN, i1);
            this.brightnessBottomRight = this
                .getAoBrightness(this.aoBrightnessYZNN, this.aoBrightnessXYZPNN, this.aoBrightnessXZPN, i1);
            this.brightnessTopRight = this
                .getAoBrightness(this.aoBrightnessXYZNNN, this.aoBrightnessXZNN, this.aoBrightnessYZNN, i1);
            if (!ColorizeBlock.setupBlockSmoothing(
                (RenderBlocks) (Object) this,
                block,
                this.blockAccess,
                x,
                y,
                z,
                2,
                f3,
                f4,
                f5,
                f6)) {
                if (RenderBlocksUtils.useColorMultiplier(2)) {
                    this.colorRedTopLeft = this.colorRedBottomLeft = this.colorRedBottomRight = this.colorRedTopRight = red
                        * RenderPass.getAOBaseMultiplier(0.8f);
                    this.colorGreenTopLeft = this.colorGreenBottomLeft = this.colorGreenBottomRight = this.colorGreenTopRight = green
                        * RenderPass.getAOBaseMultiplier(0.8f);
                    this.colorBlueTopLeft = this.colorBlueBottomLeft = this.colorBlueBottomRight = this.colorBlueTopRight = blue
                        * RenderPass.getAOBaseMultiplier(0.8f);
                } else {
                    this.colorRedTopLeft = this.colorRedBottomLeft = this.colorRedBottomRight = this.colorRedTopRight = RenderPass
                        .getAOBaseMultiplier(0.8f);
                    this.colorGreenTopLeft = this.colorGreenBottomLeft = this.colorGreenBottomRight = this.colorGreenTopRight = RenderPass
                        .getAOBaseMultiplier(0.8f);
                    this.colorBlueTopLeft = this.colorBlueBottomLeft = this.colorBlueBottomRight = this.colorBlueTopRight = RenderPass
                        .getAOBaseMultiplier(0.8f);
                }

                this.colorRedTopLeft *= f3;
                this.colorGreenTopLeft *= f3;
                this.colorBlueTopLeft *= f3;
                this.colorRedBottomLeft *= f4;
                this.colorGreenBottomLeft *= f4;
                this.colorBlueBottomLeft *= f4;
                this.colorRedBottomRight *= f5;
                this.colorGreenBottomRight *= f5;
                this.colorBlueBottomRight *= f5;
                this.colorRedTopRight *= f6;
                this.colorGreenTopRight *= f6;
                this.colorBlueTopRight *= f6;
            }
            iicon = this.getBlockIcon(block, this.blockAccess, x, y, z, 2);
            this.renderFaceZNeg(block, x, y, z, iicon);

            if (fancyGrass && iicon.getIconName()
                .equals("grass_side") && !this.hasOverrideBlockTexture()) {
                this.colorRedTopLeft *= red;
                this.colorRedBottomLeft *= red;
                this.colorRedBottomRight *= red;
                this.colorRedTopRight *= red;
                this.colorGreenTopLeft *= green;
                this.colorGreenBottomLeft *= green;
                this.colorGreenBottomRight *= green;
                this.colorGreenTopRight *= green;
                this.colorBlueTopLeft *= blue;
                this.colorBlueBottomLeft *= blue;
                this.colorBlueBottomRight *= blue;
                this.colorBlueTopRight *= blue;
                this.renderFaceZNeg(
                    block,
                    x,
                    y,
                    z,
                    CTMUtils.getBlockIcon(
                        BlockGrass.getIconSideOverlay(),
                        (RenderBlocks) (Object) this,
                        block,
                        this.blockAccess,
                        x,
                        y,
                        z,
                        2));
            }

            flag = true;
        }

        // face 4
        if (this.renderAllFaces || RenderPass.shouldSideBeRendered(block, this.blockAccess, x, y, z + 1, 3)) {
            if (this.renderMaxZ >= 1.0D) {
                ++z;
            }

            this.aoLightValueScratchXZNP = this.blockAccess.getBlock(x - 1, y, z)
                .getAmbientOcclusionLightValue();
            this.aoLightValueScratchXZPP = this.blockAccess.getBlock(x + 1, y, z)
                .getAmbientOcclusionLightValue();
            this.aoLightValueScratchYZNP = this.blockAccess.getBlock(x, y - 1, z)
                .getAmbientOcclusionLightValue();
            this.aoLightValueScratchYZPP = this.blockAccess.getBlock(x, y + 1, z)
                .getAmbientOcclusionLightValue();
            this.aoBrightnessXZNP = block.getMixedBrightnessForBlock(this.blockAccess, x - 1, y, z);
            this.aoBrightnessXZPP = block.getMixedBrightnessForBlock(this.blockAccess, x + 1, y, z);
            this.aoBrightnessYZNP = block.getMixedBrightnessForBlock(this.blockAccess, x, y - 1, z);
            this.aoBrightnessYZPP = block.getMixedBrightnessForBlock(this.blockAccess, x, y + 1, z);
            flag2 = this.blockAccess.getBlock(x + 1, y, z + 1)
                .getCanBlockGrass();
            flag3 = this.blockAccess.getBlock(x - 1, y, z + 1)
                .getCanBlockGrass();
            flag4 = this.blockAccess.getBlock(x, y + 1, z + 1)
                .getCanBlockGrass();
            flag5 = this.blockAccess.getBlock(x, y - 1, z + 1)
                .getCanBlockGrass();

            if (!flag3 && !flag5) {
                this.aoLightValueScratchXYZNNP = this.aoLightValueScratchXZNP;
                this.aoBrightnessXYZNNP = this.aoBrightnessXZNP;
            } else {
                this.aoLightValueScratchXYZNNP = this.blockAccess.getBlock(x - 1, y - 1, z)
                    .getAmbientOcclusionLightValue();
                this.aoBrightnessXYZNNP = block.getMixedBrightnessForBlock(this.blockAccess, x - 1, y - 1, z);
            }

            if (!flag3 && !flag4) {
                this.aoLightValueScratchXYZNPP = this.aoLightValueScratchXZNP;
                this.aoBrightnessXYZNPP = this.aoBrightnessXZNP;
            } else {
                this.aoLightValueScratchXYZNPP = this.blockAccess.getBlock(x - 1, y + 1, z)
                    .getAmbientOcclusionLightValue();
                this.aoBrightnessXYZNPP = block.getMixedBrightnessForBlock(this.blockAccess, x - 1, y + 1, z);
            }

            if (!flag2 && !flag5) {
                this.aoLightValueScratchXYZPNP = this.aoLightValueScratchXZPP;
                this.aoBrightnessXYZPNP = this.aoBrightnessXZPP;
            } else {
                this.aoLightValueScratchXYZPNP = this.blockAccess.getBlock(x + 1, y - 1, z)
                    .getAmbientOcclusionLightValue();
                this.aoBrightnessXYZPNP = block.getMixedBrightnessForBlock(this.blockAccess, x + 1, y - 1, z);
            }

            if (!flag2 && !flag4) {
                this.aoLightValueScratchXYZPPP = this.aoLightValueScratchXZPP;
                this.aoBrightnessXYZPPP = this.aoBrightnessXZPP;
            } else {
                this.aoLightValueScratchXYZPPP = this.blockAccess.getBlock(x + 1, y + 1, z)
                    .getAmbientOcclusionLightValue();
                this.aoBrightnessXYZPPP = block.getMixedBrightnessForBlock(this.blockAccess, x + 1, y + 1, z);
            }

            if (this.renderMaxZ >= 1.0D) {
                --z;
            }

            i1 = l;

            if (this.renderMaxZ >= 1.0D || !this.blockAccess.getBlock(x, y, z + 1)
                .isOpaqueCube()) {
                i1 = block.getMixedBrightnessForBlock(this.blockAccess, x, y, z + 1);
            }

            f7 = this.blockAccess.getBlock(x, y, z + 1)
                .getAmbientOcclusionLightValue();

            float f3 = (this.aoLightValueScratchXZNP + this.aoLightValueScratchXYZNPP
                + f7
                + this.aoLightValueScratchYZPP) / 4.0F;
            float f6 = (f7 + this.aoLightValueScratchYZPP
                + this.aoLightValueScratchXZPP
                + this.aoLightValueScratchXYZPPP) / 4.0F;
            float f5 = (this.aoLightValueScratchYZNP + f7
                + this.aoLightValueScratchXYZPNP
                + this.aoLightValueScratchXZPP) / 4.0F;
            float f4 = (this.aoLightValueScratchXYZNNP + this.aoLightValueScratchXZNP
                + this.aoLightValueScratchYZNP
                + f7) / 4.0F;

            this.brightnessTopLeft = this
                .getAoBrightness(this.aoBrightnessXZNP, this.aoBrightnessXYZNPP, this.aoBrightnessYZPP, i1);
            this.brightnessTopRight = this
                .getAoBrightness(this.aoBrightnessYZPP, this.aoBrightnessXZPP, this.aoBrightnessXYZPPP, i1);
            this.brightnessBottomRight = this
                .getAoBrightness(this.aoBrightnessYZNP, this.aoBrightnessXYZPNP, this.aoBrightnessXZPP, i1);
            this.brightnessBottomLeft = this
                .getAoBrightness(this.aoBrightnessXYZNNP, this.aoBrightnessXZNP, this.aoBrightnessYZNP, i1);

            if (!ColorizeBlock.setupBlockSmoothing(
                (RenderBlocks) (Object) this,
                block,
                this.blockAccess,
                x,
                y,
                z,
                3,
                f3,
                f4,
                f5,
                f6)) {
                if (RenderBlocksUtils.useColorMultiplier(3)) {
                    this.colorRedTopLeft = this.colorRedBottomLeft = this.colorRedBottomRight = this.colorRedTopRight = red
                        * RenderPass.getAOBaseMultiplier(0.8f);
                    this.colorGreenTopLeft = this.colorGreenBottomLeft = this.colorGreenBottomRight = this.colorGreenTopRight = green
                        * RenderPass.getAOBaseMultiplier(0.8f);
                    this.colorBlueTopLeft = this.colorBlueBottomLeft = this.colorBlueBottomRight = this.colorBlueTopRight = blue
                        * RenderPass.getAOBaseMultiplier(0.8f);
                } else {
                    this.colorRedTopLeft = this.colorRedBottomLeft = this.colorRedBottomRight = this.colorRedTopRight = RenderPass
                        .getAOBaseMultiplier(0.8f);
                    this.colorGreenTopLeft = this.colorGreenBottomLeft = this.colorGreenBottomRight = this.colorGreenTopRight = RenderPass
                        .getAOBaseMultiplier(0.8f);
                    this.colorBlueTopLeft = this.colorBlueBottomLeft = this.colorBlueBottomRight = this.colorBlueTopRight = RenderPass
                        .getAOBaseMultiplier(0.8f);
                }

                this.colorRedTopLeft *= f3;
                this.colorGreenTopLeft *= f3;
                this.colorBlueTopLeft *= f3;
                this.colorRedBottomLeft *= f4;
                this.colorGreenBottomLeft *= f4;
                this.colorBlueBottomLeft *= f4;
                this.colorRedBottomRight *= f5;
                this.colorGreenBottomRight *= f5;
                this.colorBlueBottomRight *= f5;
                this.colorRedTopRight *= f6;
                this.colorGreenTopRight *= f6;
                this.colorBlueTopRight *= f6;
            }
            iicon = this.getBlockIcon(block, this.blockAccess, x, y, z, 3);
            this.renderFaceZPos(block, x, y, z, this.getBlockIcon(block, this.blockAccess, x, y, z, 3));

            if (fancyGrass && iicon.getIconName()
                .equals("grass_side") && !this.hasOverrideBlockTexture()) {
                this.colorRedTopLeft *= red;
                this.colorRedBottomLeft *= red;
                this.colorRedBottomRight *= red;
                this.colorRedTopRight *= red;
                this.colorGreenTopLeft *= green;
                this.colorGreenBottomLeft *= green;
                this.colorGreenBottomRight *= green;
                this.colorGreenTopRight *= green;
                this.colorBlueTopLeft *= blue;
                this.colorBlueBottomLeft *= blue;
                this.colorBlueBottomRight *= blue;
                this.colorBlueTopRight *= blue;
                this.renderFaceZPos(
                    block,
                    x,
                    y,
                    z,
                    CTMUtils.getBlockIcon(
                        BlockGrass.getIconSideOverlay(),
                        (RenderBlocks) (Object) this,
                        block,
                        this.blockAccess,
                        x,
                        y,
                        z,
                        3));
            }

            flag = true;
        }

        // face 5
        if (this.renderAllFaces || RenderPass.shouldSideBeRendered(block, this.blockAccess, x - 1, y, z, 4)) {
            if (this.renderMinX <= 0.0D) {
                --x;
            }

            this.aoLightValueScratchXYNN = this.blockAccess.getBlock(x, y - 1, z)
                .getAmbientOcclusionLightValue();
            this.aoLightValueScratchXZNN = this.blockAccess.getBlock(x, y, z - 1)
                .getAmbientOcclusionLightValue();
            this.aoLightValueScratchXZNP = this.blockAccess.getBlock(x, y, z + 1)
                .getAmbientOcclusionLightValue();
            this.aoLightValueScratchXYNP = this.blockAccess.getBlock(x, y + 1, z)
                .getAmbientOcclusionLightValue();
            this.aoBrightnessXYNN = block.getMixedBrightnessForBlock(this.blockAccess, x, y - 1, z);
            this.aoBrightnessXZNN = block.getMixedBrightnessForBlock(this.blockAccess, x, y, z - 1);
            this.aoBrightnessXZNP = block.getMixedBrightnessForBlock(this.blockAccess, x, y, z + 1);
            this.aoBrightnessXYNP = block.getMixedBrightnessForBlock(this.blockAccess, x, y + 1, z);
            flag2 = this.blockAccess.getBlock(x - 1, y + 1, z)
                .getCanBlockGrass();
            flag3 = this.blockAccess.getBlock(x - 1, y - 1, z)
                .getCanBlockGrass();
            flag4 = this.blockAccess.getBlock(x - 1, y, z - 1)
                .getCanBlockGrass();
            flag5 = this.blockAccess.getBlock(x - 1, y, z + 1)
                .getCanBlockGrass();

            if (!flag4 && !flag3) {
                this.aoLightValueScratchXYZNNN = this.aoLightValueScratchXZNN;
                this.aoBrightnessXYZNNN = this.aoBrightnessXZNN;
            } else {
                this.aoLightValueScratchXYZNNN = this.blockAccess.getBlock(x, y - 1, z - 1)
                    .getAmbientOcclusionLightValue();
                this.aoBrightnessXYZNNN = block.getMixedBrightnessForBlock(this.blockAccess, x, y - 1, z - 1);
            }

            if (!flag5 && !flag3) {
                this.aoLightValueScratchXYZNNP = this.aoLightValueScratchXZNP;
                this.aoBrightnessXYZNNP = this.aoBrightnessXZNP;
            } else {
                this.aoLightValueScratchXYZNNP = this.blockAccess.getBlock(x, y - 1, z + 1)
                    .getAmbientOcclusionLightValue();
                this.aoBrightnessXYZNNP = block.getMixedBrightnessForBlock(this.blockAccess, x, y - 1, z + 1);
            }

            if (!flag4 && !flag2) {
                this.aoLightValueScratchXYZNPN = this.aoLightValueScratchXZNN;
                this.aoBrightnessXYZNPN = this.aoBrightnessXZNN;
            } else {
                this.aoLightValueScratchXYZNPN = this.blockAccess.getBlock(x, y + 1, z - 1)
                    .getAmbientOcclusionLightValue();
                this.aoBrightnessXYZNPN = block.getMixedBrightnessForBlock(this.blockAccess, x, y + 1, z - 1);
            }

            if (!flag5 && !flag2) {
                this.aoLightValueScratchXYZNPP = this.aoLightValueScratchXZNP;
                this.aoBrightnessXYZNPP = this.aoBrightnessXZNP;
            } else {
                this.aoLightValueScratchXYZNPP = this.blockAccess.getBlock(x, y + 1, z + 1)
                    .getAmbientOcclusionLightValue();
                this.aoBrightnessXYZNPP = block.getMixedBrightnessForBlock(this.blockAccess, x, y + 1, z + 1);
            }

            if (this.renderMinX <= 0.0D) {
                ++x;
            }

            i1 = l;

            if (this.renderMinX <= 0.0D || !this.blockAccess.getBlock(x - 1, y, z)
                .isOpaqueCube()) {
                i1 = block.getMixedBrightnessForBlock(this.blockAccess, x - 1, y, z);
            }

            f7 = this.blockAccess.getBlock(x - 1, y, z)
                .getAmbientOcclusionLightValue();

            float f6 = (this.aoLightValueScratchXYNN + this.aoLightValueScratchXYZNNP
                + f7
                + this.aoLightValueScratchXZNP) / 4.0F;
            float f3 = (f7 + this.aoLightValueScratchXZNP
                + this.aoLightValueScratchXYNP
                + this.aoLightValueScratchXYZNPP) / 4.0F;
            float f4 = (this.aoLightValueScratchXZNN + f7
                + this.aoLightValueScratchXYZNPN
                + this.aoLightValueScratchXYNP) / 4.0F;
            float f5 = (this.aoLightValueScratchXYZNNN + this.aoLightValueScratchXYNN
                + this.aoLightValueScratchXZNN
                + f7) / 4.0F;

            this.brightnessTopRight = this
                .getAoBrightness(this.aoBrightnessXYNN, this.aoBrightnessXYZNNP, this.aoBrightnessXZNP, i1);
            this.brightnessTopLeft = this
                .getAoBrightness(this.aoBrightnessXZNP, this.aoBrightnessXYNP, this.aoBrightnessXYZNPP, i1);
            this.brightnessBottomLeft = this
                .getAoBrightness(this.aoBrightnessXZNN, this.aoBrightnessXYZNPN, this.aoBrightnessXYNP, i1);
            this.brightnessBottomRight = this
                .getAoBrightness(this.aoBrightnessXYZNNN, this.aoBrightnessXYNN, this.aoBrightnessXZNN, i1);
            if (!ColorizeBlock.setupBlockSmoothing(
                (RenderBlocks) (Object) this,
                block,
                this.blockAccess,
                x,
                y,
                z,
                4,
                f3,
                f4,
                f5,
                f6)) {
                if (RenderBlocksUtils.useColorMultiplier(4)) {
                    this.colorRedTopLeft = this.colorRedBottomLeft = this.colorRedBottomRight = this.colorRedTopRight = red
                        * RenderPass.getAOBaseMultiplier(0.6F);
                    this.colorGreenTopLeft = this.colorGreenBottomLeft = this.colorGreenBottomRight = this.colorGreenTopRight = green
                        * RenderPass.getAOBaseMultiplier(0.6F);
                    this.colorBlueTopLeft = this.colorBlueBottomLeft = this.colorBlueBottomRight = this.colorBlueTopRight = blue
                        * RenderPass.getAOBaseMultiplier(0.6F);
                } else {
                    this.colorRedTopLeft = this.colorRedBottomLeft = this.colorRedBottomRight = this.colorRedTopRight = RenderPass
                        .getAOBaseMultiplier(0.6F);
                    this.colorGreenTopLeft = this.colorGreenBottomLeft = this.colorGreenBottomRight = this.colorGreenTopRight = RenderPass
                        .getAOBaseMultiplier(0.6F);
                    this.colorBlueTopLeft = this.colorBlueBottomLeft = this.colorBlueBottomRight = this.colorBlueTopRight = RenderPass
                        .getAOBaseMultiplier(0.6F);
                }

                this.colorRedTopLeft *= f3;
                this.colorGreenTopLeft *= f3;
                this.colorBlueTopLeft *= f3;
                this.colorRedBottomLeft *= f4;
                this.colorGreenBottomLeft *= f4;
                this.colorBlueBottomLeft *= f4;
                this.colorRedBottomRight *= f5;
                this.colorGreenBottomRight *= f5;
                this.colorBlueBottomRight *= f5;
                this.colorRedTopRight *= f6;
                this.colorGreenTopRight *= f6;
                this.colorBlueTopRight *= f6;
            }
            iicon = this.getBlockIcon(block, this.blockAccess, x, y, z, 4);
            this.renderFaceXNeg(block, x, y, z, iicon);

            if (fancyGrass && iicon.getIconName()
                .equals("grass_side") && !this.hasOverrideBlockTexture()) {
                this.colorRedTopLeft *= red;
                this.colorRedBottomLeft *= red;
                this.colorRedBottomRight *= red;
                this.colorRedTopRight *= red;
                this.colorGreenTopLeft *= green;
                this.colorGreenBottomLeft *= green;
                this.colorGreenBottomRight *= green;
                this.colorGreenTopRight *= green;
                this.colorBlueTopLeft *= blue;
                this.colorBlueBottomLeft *= blue;
                this.colorBlueBottomRight *= blue;
                this.colorBlueTopRight *= blue;
                this.renderFaceXNeg(
                    block,
                    x,
                    y,
                    z,
                    CTMUtils.getBlockIcon(
                        BlockGrass.getIconSideOverlay(),
                        (RenderBlocks) (Object) this,
                        block,
                        this.blockAccess,
                        x,
                        y,
                        z,
                        4));
            }

            flag = true;
        }
        // face 6
        if (this.renderAllFaces || RenderPass.shouldSideBeRendered(block, this.blockAccess, x + 1, y, z, 5)) {
            if (this.renderMaxX >= 1.0D) {
                ++x;
            }

            this.aoLightValueScratchXYPN = this.blockAccess.getBlock(x, y - 1, z)
                .getAmbientOcclusionLightValue();
            this.aoLightValueScratchXZPN = this.blockAccess.getBlock(x, y, z - 1)
                .getAmbientOcclusionLightValue();
            this.aoLightValueScratchXZPP = this.blockAccess.getBlock(x, y, z + 1)
                .getAmbientOcclusionLightValue();
            this.aoLightValueScratchXYPP = this.blockAccess.getBlock(x, y + 1, z)
                .getAmbientOcclusionLightValue();
            this.aoBrightnessXYPN = block.getMixedBrightnessForBlock(this.blockAccess, x, y - 1, z);
            this.aoBrightnessXZPN = block.getMixedBrightnessForBlock(this.blockAccess, x, y, z - 1);
            this.aoBrightnessXZPP = block.getMixedBrightnessForBlock(this.blockAccess, x, y, z + 1);
            this.aoBrightnessXYPP = block.getMixedBrightnessForBlock(this.blockAccess, x, y + 1, z);
            flag2 = this.blockAccess.getBlock(x + 1, y + 1, z)
                .getCanBlockGrass();
            flag3 = this.blockAccess.getBlock(x + 1, y - 1, z)
                .getCanBlockGrass();
            flag4 = this.blockAccess.getBlock(x + 1, y, z + 1)
                .getCanBlockGrass();
            flag5 = this.blockAccess.getBlock(x + 1, y, z - 1)
                .getCanBlockGrass();

            if (!flag3 && !flag5) {
                this.aoLightValueScratchXYZPNN = this.aoLightValueScratchXZPN;
                this.aoBrightnessXYZPNN = this.aoBrightnessXZPN;
            } else {
                this.aoLightValueScratchXYZPNN = this.blockAccess.getBlock(x, y - 1, z - 1)
                    .getAmbientOcclusionLightValue();
                this.aoBrightnessXYZPNN = block.getMixedBrightnessForBlock(this.blockAccess, x, y - 1, z - 1);
            }

            if (!flag3 && !flag4) {
                this.aoLightValueScratchXYZPNP = this.aoLightValueScratchXZPP;
                this.aoBrightnessXYZPNP = this.aoBrightnessXZPP;
            } else {
                this.aoLightValueScratchXYZPNP = this.blockAccess.getBlock(x, y - 1, z + 1)
                    .getAmbientOcclusionLightValue();
                this.aoBrightnessXYZPNP = block.getMixedBrightnessForBlock(this.blockAccess, x, y - 1, z + 1);
            }

            if (!flag2 && !flag5) {
                this.aoLightValueScratchXYZPPN = this.aoLightValueScratchXZPN;
                this.aoBrightnessXYZPPN = this.aoBrightnessXZPN;
            } else {
                this.aoLightValueScratchXYZPPN = this.blockAccess.getBlock(x, y + 1, z - 1)
                    .getAmbientOcclusionLightValue();
                this.aoBrightnessXYZPPN = block.getMixedBrightnessForBlock(this.blockAccess, x, y + 1, z - 1);
            }

            if (!flag2 && !flag4) {
                this.aoLightValueScratchXYZPPP = this.aoLightValueScratchXZPP;
                this.aoBrightnessXYZPPP = this.aoBrightnessXZPP;
            } else {
                this.aoLightValueScratchXYZPPP = this.blockAccess.getBlock(x, y + 1, z + 1)
                    .getAmbientOcclusionLightValue();
                this.aoBrightnessXYZPPP = block.getMixedBrightnessForBlock(this.blockAccess, x, y + 1, z + 1);
            }

            if (this.renderMaxX >= 1.0D) {
                --x;
            }

            i1 = l;

            if (this.renderMaxX >= 1.0D || !this.blockAccess.getBlock(x + 1, y, z)
                .isOpaqueCube()) {
                i1 = block.getMixedBrightnessForBlock(this.blockAccess, x + 1, y, z);
            }

            f7 = this.blockAccess.getBlock(x + 1, y, z)
                .getAmbientOcclusionLightValue();

            float f3 = (this.aoLightValueScratchXYPN + this.aoLightValueScratchXYZPNP
                + f7
                + this.aoLightValueScratchXZPP) / 4.0F;
            float f4 = (this.aoLightValueScratchXYZPNN + this.aoLightValueScratchXYPN
                + this.aoLightValueScratchXZPN
                + f7) / 4.0F;
            float f5 = (this.aoLightValueScratchXZPN + f7
                + this.aoLightValueScratchXYZPPN
                + this.aoLightValueScratchXYPP) / 4.0F;
            float f6 = (f7 + this.aoLightValueScratchXZPP
                + this.aoLightValueScratchXYPP
                + this.aoLightValueScratchXYZPPP) / 4.0F;

            this.brightnessTopLeft = this
                .getAoBrightness(this.aoBrightnessXYPN, this.aoBrightnessXYZPNP, this.aoBrightnessXZPP, i1);
            this.brightnessTopRight = this
                .getAoBrightness(this.aoBrightnessXZPP, this.aoBrightnessXYPP, this.aoBrightnessXYZPPP, i1);
            this.brightnessBottomRight = this
                .getAoBrightness(this.aoBrightnessXZPN, this.aoBrightnessXYZPPN, this.aoBrightnessXYPP, i1);
            this.brightnessBottomLeft = this
                .getAoBrightness(this.aoBrightnessXYZPNN, this.aoBrightnessXYPN, this.aoBrightnessXZPN, i1);
            if (!ColorizeBlock.setupBlockSmoothing(
                (RenderBlocks) (Object) this,
                block,
                this.blockAccess,
                x,
                y,
                z,
                5,
                f3,
                f4,
                f5,
                f6)) {
                if (RenderBlocksUtils.useColorMultiplier(5)) {
                    this.colorRedTopLeft = this.colorRedBottomLeft = this.colorRedBottomRight = this.colorRedTopRight = red
                        * RenderPass.getAOBaseMultiplier(0.6F);
                    this.colorGreenTopLeft = this.colorGreenBottomLeft = this.colorGreenBottomRight = this.colorGreenTopRight = green
                        * RenderPass.getAOBaseMultiplier(0.6F);
                    this.colorBlueTopLeft = this.colorBlueBottomLeft = this.colorBlueBottomRight = this.colorBlueTopRight = blue
                        * RenderPass.getAOBaseMultiplier(0.6F);
                } else {
                    this.colorRedTopLeft = this.colorRedBottomLeft = this.colorRedBottomRight = this.colorRedTopRight = RenderPass
                        .getAOBaseMultiplier(0.6F);
                    this.colorGreenTopLeft = this.colorGreenBottomLeft = this.colorGreenBottomRight = this.colorGreenTopRight = RenderPass
                        .getAOBaseMultiplier(0.6F);
                    this.colorBlueTopLeft = this.colorBlueBottomLeft = this.colorBlueBottomRight = this.colorBlueTopRight = RenderPass
                        .getAOBaseMultiplier(0.6F);
                }

                this.colorRedTopLeft *= f3;
                this.colorGreenTopLeft *= f3;
                this.colorBlueTopLeft *= f3;
                this.colorRedBottomLeft *= f4;
                this.colorGreenBottomLeft *= f4;
                this.colorBlueBottomLeft *= f4;
                this.colorRedBottomRight *= f5;
                this.colorGreenBottomRight *= f5;
                this.colorBlueBottomRight *= f5;
                this.colorRedTopRight *= f6;
                this.colorGreenTopRight *= f6;
                this.colorBlueTopRight *= f6;
            }
            iicon = this.getBlockIcon(block, this.blockAccess, x, y, z, 5);
            this.renderFaceXPos(block, x, y, z, iicon);

            if (fancyGrass && iicon.getIconName()
                .equals("grass_side") && !this.hasOverrideBlockTexture()) {
                this.colorRedTopLeft *= red;
                this.colorRedBottomLeft *= red;
                this.colorRedBottomRight *= red;
                this.colorRedTopRight *= red;
                this.colorGreenTopLeft *= green;
                this.colorGreenBottomLeft *= green;
                this.colorGreenBottomRight *= green;
                this.colorGreenTopRight *= green;
                this.colorBlueTopLeft *= blue;
                this.colorBlueBottomLeft *= blue;
                this.colorBlueBottomRight *= blue;
                this.colorBlueTopRight *= blue;
                this.renderFaceXPos(
                    block,
                    x,
                    y,
                    z,
                    CTMUtils.getBlockIcon(
                        BlockGrass.getIconSideOverlay(),
                        (RenderBlocks) (Object) this,
                        block,
                        this.blockAccess,
                        x,
                        y,
                        z,
                        5));
            }

            flag = true;
        }

        this.enableAO = false;
        return flag;
    }

    /**
     * @author Mist475 (adapted from Paul Rupe)
     * @reason Some code wrapped in if statements
     *         TODO: look at again for compatability
     */
    @Overwrite
    public boolean renderStandardBlockWithAmbientOcclusionPartial(Block block, int x, int y, int z, float red,
        float green, float blue) {
        this.enableAO = true;
        boolean flag = false;
        float f3;
        float f4;
        float f5;
        float f6;

        int l = block.getMixedBrightnessForBlock(this.blockAccess, x, y, z);
        Tessellator tessellator = Tessellator.instance;
        tessellator.setBrightness(983055);

        boolean flag2;
        boolean flag3;
        boolean flag4;
        boolean flag5;
        int i1;
        float f7;
        // block 0
        if (this.renderAllFaces || RenderPass.shouldSideBeRendered(block, this.blockAccess, x, y - 1, z, 0)) {
            if (this.renderMinY <= 0.0D) {
                --y;
            }

            this.aoBrightnessXYNN = block.getMixedBrightnessForBlock(this.blockAccess, x - 1, y, z);
            this.aoBrightnessYZNN = block.getMixedBrightnessForBlock(this.blockAccess, x, y, z - 1);
            this.aoBrightnessYZNP = block.getMixedBrightnessForBlock(this.blockAccess, x, y, z + 1);
            this.aoBrightnessXYPN = block.getMixedBrightnessForBlock(this.blockAccess, x + 1, y, z);
            this.aoLightValueScratchXYNN = this.blockAccess.getBlock(x - 1, y, z)
                .getAmbientOcclusionLightValue();
            this.aoLightValueScratchYZNN = this.blockAccess.getBlock(x, y, z - 1)
                .getAmbientOcclusionLightValue();
            this.aoLightValueScratchYZNP = this.blockAccess.getBlock(x, y, z + 1)
                .getAmbientOcclusionLightValue();
            this.aoLightValueScratchXYPN = this.blockAccess.getBlock(x + 1, y, z)
                .getAmbientOcclusionLightValue();
            flag2 = this.blockAccess.getBlock(x + 1, y - 1, z)
                .getCanBlockGrass();
            flag3 = this.blockAccess.getBlock(x - 1, y - 1, z)
                .getCanBlockGrass();
            flag4 = this.blockAccess.getBlock(x, y - 1, z + 1)
                .getCanBlockGrass();
            flag5 = this.blockAccess.getBlock(x, y - 1, z - 1)
                .getCanBlockGrass();

            if (!flag5 && !flag3) {
                this.aoLightValueScratchXYZNNN = this.aoLightValueScratchXYNN;
                this.aoBrightnessXYZNNN = this.aoBrightnessXYNN;
            } else {
                this.aoLightValueScratchXYZNNN = this.blockAccess.getBlock(x - 1, y, z - 1)
                    .getAmbientOcclusionLightValue();
                this.aoBrightnessXYZNNN = block.getMixedBrightnessForBlock(this.blockAccess, x - 1, y, z - 1);
            }

            if (!flag4 && !flag3) {
                this.aoLightValueScratchXYZNNP = this.aoLightValueScratchXYNN;
                this.aoBrightnessXYZNNP = this.aoBrightnessXYNN;
            } else {
                this.aoLightValueScratchXYZNNP = this.blockAccess.getBlock(x - 1, y, z + 1)
                    .getAmbientOcclusionLightValue();
                this.aoBrightnessXYZNNP = block.getMixedBrightnessForBlock(this.blockAccess, x - 1, y, z + 1);
            }

            if (!flag5 && !flag2) {
                this.aoLightValueScratchXYZPNN = this.aoLightValueScratchXYPN;
                this.aoBrightnessXYZPNN = this.aoBrightnessXYPN;
            } else {
                this.aoLightValueScratchXYZPNN = this.blockAccess.getBlock(x + 1, y, z - 1)
                    .getAmbientOcclusionLightValue();
                this.aoBrightnessXYZPNN = block.getMixedBrightnessForBlock(this.blockAccess, x + 1, y, z - 1);
            }

            if (!flag4 && !flag2) {
                this.aoLightValueScratchXYZPNP = this.aoLightValueScratchXYPN;
                this.aoBrightnessXYZPNP = this.aoBrightnessXYPN;
            } else {
                this.aoLightValueScratchXYZPNP = this.blockAccess.getBlock(x + 1, y, z + 1)
                    .getAmbientOcclusionLightValue();
                this.aoBrightnessXYZPNP = block.getMixedBrightnessForBlock(this.blockAccess, x + 1, y, z + 1);
            }

            if (this.renderMinY <= 0.0D) {
                ++y;
            }

            i1 = l;

            if (this.renderMinY <= 0.0D || !this.blockAccess.getBlock(x, y - 1, z)
                .isOpaqueCube()) {
                i1 = block.getMixedBrightnessForBlock(this.blockAccess, x, y - 1, z);
            }

            f7 = this.blockAccess.getBlock(x, y - 1, z)
                .getAmbientOcclusionLightValue();
            f3 = (this.aoLightValueScratchXYZNNP + this.aoLightValueScratchXYNN + this.aoLightValueScratchYZNP + f7)
                / 4.0F;
            f6 = (this.aoLightValueScratchYZNP + f7 + this.aoLightValueScratchXYZPNP + this.aoLightValueScratchXYPN)
                / 4.0F;
            f5 = (f7 + this.aoLightValueScratchYZNN + this.aoLightValueScratchXYPN + this.aoLightValueScratchXYZPNN)
                / 4.0F;
            f4 = (this.aoLightValueScratchXYNN + this.aoLightValueScratchXYZNNN + f7 + this.aoLightValueScratchYZNN)
                / 4.0F;
            this.brightnessTopLeft = this
                .getAoBrightness(this.aoBrightnessXYZNNP, this.aoBrightnessXYNN, this.aoBrightnessYZNP, i1);
            this.brightnessTopRight = this
                .getAoBrightness(this.aoBrightnessYZNP, this.aoBrightnessXYZPNP, this.aoBrightnessXYPN, i1);
            this.brightnessBottomRight = this
                .getAoBrightness(this.aoBrightnessYZNN, this.aoBrightnessXYPN, this.aoBrightnessXYZPNN, i1);
            this.brightnessBottomLeft = this
                .getAoBrightness(this.aoBrightnessXYNN, this.aoBrightnessXYZNNN, this.aoBrightnessYZNN, i1);
            if (!ColorizeBlock.setupBlockSmoothing(
                (RenderBlocks) (Object) this,
                block,
                this.blockAccess,
                x,
                y,
                z,
                0,
                f3,
                f4,
                f5,
                f6)) {
                if (RenderBlocksUtils.useColorMultiplier(0)) {
                    this.colorRedTopLeft = this.colorRedBottomLeft = this.colorRedBottomRight = this.colorRedTopRight = red
                        * 0.5F;
                    this.colorGreenTopLeft = this.colorGreenBottomLeft = this.colorGreenBottomRight = this.colorGreenTopRight = green
                        * 0.5F;
                    this.colorBlueTopLeft = this.colorBlueBottomLeft = this.colorBlueBottomRight = this.colorBlueTopRight = blue
                        * 0.5F;
                } else {
                    this.colorRedTopLeft = this.colorRedBottomLeft = this.colorRedBottomRight = this.colorRedTopRight = 0.5F;
                    this.colorGreenTopLeft = this.colorGreenBottomLeft = this.colorGreenBottomRight = this.colorGreenTopRight = 0.5F;
                    this.colorBlueTopLeft = this.colorBlueBottomLeft = this.colorBlueBottomRight = this.colorBlueTopRight = 0.5F;
                }

                this.colorRedTopLeft *= f3;
                this.colorGreenTopLeft *= f3;
                this.colorBlueTopLeft *= f3;
                this.colorRedBottomLeft *= f4;
                this.colorGreenBottomLeft *= f4;
                this.colorBlueBottomLeft *= f4;
                this.colorRedBottomRight *= f5;
                this.colorGreenBottomRight *= f5;
                this.colorBlueBottomRight *= f5;
                this.colorRedTopRight *= f6;
                this.colorGreenTopRight *= f6;
                this.colorBlueTopRight *= f6;
            }
            this.renderFaceYNeg(block, x, y, z, this.getBlockIcon(block, this.blockAccess, x, y, z, 0));
            flag = true;
        }
        // block 1
        if (this.renderAllFaces || RenderPass.shouldSideBeRendered(block, this.blockAccess, x, y + 1, z, 1)) {
            if (this.renderMaxY >= 1.0D) {
                ++y;
            }

            this.aoBrightnessXYNP = block.getMixedBrightnessForBlock(this.blockAccess, x - 1, y, z);
            this.aoBrightnessXYPP = block.getMixedBrightnessForBlock(this.blockAccess, x + 1, y, z);
            this.aoBrightnessYZPN = block.getMixedBrightnessForBlock(this.blockAccess, x, y, z - 1);
            this.aoBrightnessYZPP = block.getMixedBrightnessForBlock(this.blockAccess, x, y, z + 1);
            this.aoLightValueScratchXYNP = this.blockAccess.getBlock(x - 1, y, z)
                .getAmbientOcclusionLightValue();
            this.aoLightValueScratchXYPP = this.blockAccess.getBlock(x + 1, y, z)
                .getAmbientOcclusionLightValue();
            this.aoLightValueScratchYZPN = this.blockAccess.getBlock(x, y, z - 1)
                .getAmbientOcclusionLightValue();
            this.aoLightValueScratchYZPP = this.blockAccess.getBlock(x, y, z + 1)
                .getAmbientOcclusionLightValue();
            flag2 = this.blockAccess.getBlock(x + 1, y + 1, z)
                .getCanBlockGrass();
            flag3 = this.blockAccess.getBlock(x - 1, y + 1, z)
                .getCanBlockGrass();
            flag4 = this.blockAccess.getBlock(x, y + 1, z + 1)
                .getCanBlockGrass();
            flag5 = this.blockAccess.getBlock(x, y + 1, z - 1)
                .getCanBlockGrass();

            if (!flag5 && !flag3) {
                this.aoLightValueScratchXYZNPN = this.aoLightValueScratchXYNP;
                this.aoBrightnessXYZNPN = this.aoBrightnessXYNP;
            } else {
                this.aoLightValueScratchXYZNPN = this.blockAccess.getBlock(x - 1, y, z - 1)
                    .getAmbientOcclusionLightValue();
                this.aoBrightnessXYZNPN = block.getMixedBrightnessForBlock(this.blockAccess, x - 1, y, z - 1);
            }

            if (!flag5 && !flag2) {
                this.aoLightValueScratchXYZPPN = this.aoLightValueScratchXYPP;
                this.aoBrightnessXYZPPN = this.aoBrightnessXYPP;
            } else {
                this.aoLightValueScratchXYZPPN = this.blockAccess.getBlock(x + 1, y, z - 1)
                    .getAmbientOcclusionLightValue();
                this.aoBrightnessXYZPPN = block.getMixedBrightnessForBlock(this.blockAccess, x + 1, y, z - 1);
            }

            if (!flag4 && !flag3) {
                this.aoLightValueScratchXYZNPP = this.aoLightValueScratchXYNP;
                this.aoBrightnessXYZNPP = this.aoBrightnessXYNP;
            } else {
                this.aoLightValueScratchXYZNPP = this.blockAccess.getBlock(x - 1, y, z + 1)
                    .getAmbientOcclusionLightValue();
                this.aoBrightnessXYZNPP = block.getMixedBrightnessForBlock(this.blockAccess, x - 1, y, z + 1);
            }

            if (!flag4 && !flag2) {
                this.aoLightValueScratchXYZPPP = this.aoLightValueScratchXYPP;
                this.aoBrightnessXYZPPP = this.aoBrightnessXYPP;
            } else {
                this.aoLightValueScratchXYZPPP = this.blockAccess.getBlock(x + 1, y, z + 1)
                    .getAmbientOcclusionLightValue();
                this.aoBrightnessXYZPPP = block.getMixedBrightnessForBlock(this.blockAccess, x + 1, y, z + 1);
            }

            if (this.renderMaxY >= 1.0D) {
                --y;
            }

            i1 = l;

            if (this.renderMaxY >= 1.0D || !this.blockAccess.getBlock(x, y + 1, z)
                .isOpaqueCube()) {
                i1 = block.getMixedBrightnessForBlock(this.blockAccess, x, y + 1, z);
            }

            f7 = this.blockAccess.getBlock(x, y + 1, z)
                .getAmbientOcclusionLightValue();
            f6 = (this.aoLightValueScratchXYZNPP + this.aoLightValueScratchXYNP + this.aoLightValueScratchYZPP + f7)
                / 4.0F;
            f3 = (this.aoLightValueScratchYZPP + f7 + this.aoLightValueScratchXYZPPP + this.aoLightValueScratchXYPP)
                / 4.0F;
            f4 = (f7 + this.aoLightValueScratchYZPN + this.aoLightValueScratchXYPP + this.aoLightValueScratchXYZPPN)
                / 4.0F;
            f5 = (this.aoLightValueScratchXYNP + this.aoLightValueScratchXYZNPN + f7 + this.aoLightValueScratchYZPN)
                / 4.0F;
            this.brightnessTopRight = this
                .getAoBrightness(this.aoBrightnessXYZNPP, this.aoBrightnessXYNP, this.aoBrightnessYZPP, i1);
            this.brightnessTopLeft = this
                .getAoBrightness(this.aoBrightnessYZPP, this.aoBrightnessXYZPPP, this.aoBrightnessXYPP, i1);
            this.brightnessBottomLeft = this
                .getAoBrightness(this.aoBrightnessYZPN, this.aoBrightnessXYPP, this.aoBrightnessXYZPPN, i1);
            this.brightnessBottomRight = this
                .getAoBrightness(this.aoBrightnessXYNP, this.aoBrightnessXYZNPN, this.aoBrightnessYZPN, i1);
            if (!ColorizeBlock.setupBlockSmoothing(
                (RenderBlocks) (Object) this,
                block,
                this.blockAccess,
                x,
                y,
                z,
                1,
                f3,
                f4,
                f5,
                f6)) {
                this.colorRedTopLeft = this.colorRedBottomLeft = this.colorRedBottomRight = this.colorRedTopRight = red;
                this.colorGreenTopLeft = this.colorGreenBottomLeft = this.colorGreenBottomRight = this.colorGreenTopRight = green;
                this.colorBlueTopLeft = this.colorBlueBottomLeft = this.colorBlueBottomRight = this.colorBlueTopRight = blue;
                this.colorRedTopLeft *= f3;
                this.colorGreenTopLeft *= f3;
                this.colorBlueTopLeft *= f3;
                this.colorRedBottomLeft *= f4;
                this.colorGreenBottomLeft *= f4;
                this.colorBlueBottomLeft *= f4;
                this.colorRedBottomRight *= f5;
                this.colorGreenBottomRight *= f5;
                this.colorBlueBottomRight *= f5;
                this.colorRedTopRight *= f6;
                this.colorGreenTopRight *= f6;
                this.colorBlueTopRight *= f6;
            }
            this.renderFaceYPos(block, x, y, z, this.getBlockIcon(block, this.blockAccess, x, y, z, 1));
            flag = true;
        }

        float f8;
        float f9;
        float f10;
        float f11;
        int j1;
        int k1;
        int l1;
        int i2;
        IIcon iicon;
        // face 2
        if (this.renderAllFaces || RenderPass.shouldSideBeRendered(block, this.blockAccess, x, y, z - 1, 2)) {
            if (this.renderMinZ <= 0.0D) {
                --z;
            }

            this.aoLightValueScratchXZNN = this.blockAccess.getBlock(x - 1, y, z)
                .getAmbientOcclusionLightValue();
            this.aoLightValueScratchYZNN = this.blockAccess.getBlock(x, y - 1, z)
                .getAmbientOcclusionLightValue();
            this.aoLightValueScratchYZPN = this.blockAccess.getBlock(x, y + 1, z)
                .getAmbientOcclusionLightValue();
            this.aoLightValueScratchXZPN = this.blockAccess.getBlock(x + 1, y, z)
                .getAmbientOcclusionLightValue();
            this.aoBrightnessXZNN = block.getMixedBrightnessForBlock(this.blockAccess, x - 1, y, z);
            this.aoBrightnessYZNN = block.getMixedBrightnessForBlock(this.blockAccess, x, y - 1, z);
            this.aoBrightnessYZPN = block.getMixedBrightnessForBlock(this.blockAccess, x, y + 1, z);
            this.aoBrightnessXZPN = block.getMixedBrightnessForBlock(this.blockAccess, x + 1, y, z);
            flag2 = this.blockAccess.getBlock(x + 1, y, z - 1)
                .getCanBlockGrass();
            flag3 = this.blockAccess.getBlock(x - 1, y, z - 1)
                .getCanBlockGrass();
            flag4 = this.blockAccess.getBlock(x, y + 1, z - 1)
                .getCanBlockGrass();
            flag5 = this.blockAccess.getBlock(x, y - 1, z - 1)
                .getCanBlockGrass();

            if (!flag3 && !flag5) {
                this.aoLightValueScratchXYZNNN = this.aoLightValueScratchXZNN;
                this.aoBrightnessXYZNNN = this.aoBrightnessXZNN;
            } else {
                this.aoLightValueScratchXYZNNN = this.blockAccess.getBlock(x - 1, y - 1, z)
                    .getAmbientOcclusionLightValue();
                this.aoBrightnessXYZNNN = block.getMixedBrightnessForBlock(this.blockAccess, x - 1, y - 1, z);
            }

            if (!flag3 && !flag4) {
                this.aoLightValueScratchXYZNPN = this.aoLightValueScratchXZNN;
                this.aoBrightnessXYZNPN = this.aoBrightnessXZNN;
            } else {
                this.aoLightValueScratchXYZNPN = this.blockAccess.getBlock(x - 1, y + 1, z)
                    .getAmbientOcclusionLightValue();
                this.aoBrightnessXYZNPN = block.getMixedBrightnessForBlock(this.blockAccess, x - 1, y + 1, z);
            }

            if (!flag2 && !flag5) {
                this.aoLightValueScratchXYZPNN = this.aoLightValueScratchXZPN;
                this.aoBrightnessXYZPNN = this.aoBrightnessXZPN;
            } else {
                this.aoLightValueScratchXYZPNN = this.blockAccess.getBlock(x + 1, y - 1, z)
                    .getAmbientOcclusionLightValue();
                this.aoBrightnessXYZPNN = block.getMixedBrightnessForBlock(this.blockAccess, x + 1, y - 1, z);
            }

            if (!flag2 && !flag4) {
                this.aoLightValueScratchXYZPPN = this.aoLightValueScratchXZPN;
                this.aoBrightnessXYZPPN = this.aoBrightnessXZPN;
            } else {
                this.aoLightValueScratchXYZPPN = this.blockAccess.getBlock(x + 1, y + 1, z)
                    .getAmbientOcclusionLightValue();
                this.aoBrightnessXYZPPN = block.getMixedBrightnessForBlock(this.blockAccess, x + 1, y + 1, z);
            }

            if (this.renderMinZ <= 0.0D) {
                ++z;
            }

            i1 = l;

            if (this.renderMinZ <= 0.0D || !this.blockAccess.getBlock(x, y, z - 1)
                .isOpaqueCube()) {
                i1 = block.getMixedBrightnessForBlock(this.blockAccess, x, y, z - 1);
            }

            f7 = this.blockAccess.getBlock(x, y, z - 1)
                .getAmbientOcclusionLightValue();
            f8 = (this.aoLightValueScratchXZNN + this.aoLightValueScratchXYZNPN + f7 + this.aoLightValueScratchYZPN)
                / 4.0F;
            f9 = (f7 + this.aoLightValueScratchYZPN + this.aoLightValueScratchXZPN + this.aoLightValueScratchXYZPPN)
                / 4.0F;
            f10 = (this.aoLightValueScratchYZNN + f7 + this.aoLightValueScratchXYZPNN + this.aoLightValueScratchXZPN)
                / 4.0F;
            f11 = (this.aoLightValueScratchXYZNNN + this.aoLightValueScratchXZNN + this.aoLightValueScratchYZNN + f7)
                / 4.0F;
            f3 = (float) ((double) f8 * this.renderMaxY * (1.0D - this.renderMinX)
                + (double) f9 * this.renderMaxY * this.renderMinX
                + (double) f10 * (1.0D - this.renderMaxY) * this.renderMinX
                + (double) f11 * (1.0D - this.renderMaxY) * (1.0D - this.renderMinX));
            f4 = (float) ((double) f8 * this.renderMaxY * (1.0D - this.renderMaxX)
                + (double) f9 * this.renderMaxY * this.renderMaxX
                + (double) f10 * (1.0D - this.renderMaxY) * this.renderMaxX
                + (double) f11 * (1.0D - this.renderMaxY) * (1.0D - this.renderMaxX));
            f5 = (float) ((double) f8 * this.renderMinY * (1.0D - this.renderMaxX)
                + (double) f9 * this.renderMinY * this.renderMaxX
                + (double) f10 * (1.0D - this.renderMinY) * this.renderMaxX
                + (double) f11 * (1.0D - this.renderMinY) * (1.0D - this.renderMaxX));
            f6 = (float) ((double) f8 * this.renderMinY * (1.0D - this.renderMinX)
                + (double) f9 * this.renderMinY * this.renderMinX
                + (double) f10 * (1.0D - this.renderMinY) * this.renderMinX
                + (double) f11 * (1.0D - this.renderMinY) * (1.0D - this.renderMinX));
            j1 = this.getAoBrightness(this.aoBrightnessXZNN, this.aoBrightnessXYZNPN, this.aoBrightnessYZPN, i1);
            k1 = this.getAoBrightness(this.aoBrightnessYZPN, this.aoBrightnessXZPN, this.aoBrightnessXYZPPN, i1);
            l1 = this.getAoBrightness(this.aoBrightnessYZNN, this.aoBrightnessXYZPNN, this.aoBrightnessXZPN, i1);
            i2 = this.getAoBrightness(this.aoBrightnessXYZNNN, this.aoBrightnessXZNN, this.aoBrightnessYZNN, i1);
            this.brightnessTopLeft = this.mixAoBrightness(
                j1,
                k1,
                l1,
                i2,
                this.renderMaxY * (1.0D - this.renderMinX),
                this.renderMaxY * this.renderMinX,
                (1.0D - this.renderMaxY) * this.renderMinX,
                (1.0D - this.renderMaxY) * (1.0D - this.renderMinX));
            this.brightnessBottomLeft = this.mixAoBrightness(
                j1,
                k1,
                l1,
                i2,
                this.renderMaxY * (1.0D - this.renderMaxX),
                this.renderMaxY * this.renderMaxX,
                (1.0D - this.renderMaxY) * this.renderMaxX,
                (1.0D - this.renderMaxY) * (1.0D - this.renderMaxX));
            this.brightnessBottomRight = this.mixAoBrightness(
                j1,
                k1,
                l1,
                i2,
                this.renderMinY * (1.0D - this.renderMaxX),
                this.renderMinY * this.renderMaxX,
                (1.0D - this.renderMinY) * this.renderMaxX,
                (1.0D - this.renderMinY) * (1.0D - this.renderMaxX));
            this.brightnessTopRight = this.mixAoBrightness(
                j1,
                k1,
                l1,
                i2,
                this.renderMinY * (1.0D - this.renderMinX),
                this.renderMinY * this.renderMinX,
                (1.0D - this.renderMinY) * this.renderMinX,
                (1.0D - this.renderMinY) * (1.0D - this.renderMinX));
            if (!ColorizeBlock.setupBlockSmoothing(
                (RenderBlocks) (Object) this,
                block,
                this.blockAccess,
                x,
                y,
                z,
                2,
                f3,
                f4,
                f5,
                f6)) {
                if (RenderBlocksUtils.useColorMultiplier(2)) {
                    this.colorRedTopLeft = this.colorRedBottomLeft = this.colorRedBottomRight = this.colorRedTopRight = red
                        * 0.8F;
                    this.colorGreenTopLeft = this.colorGreenBottomLeft = this.colorGreenBottomRight = this.colorGreenTopRight = green
                        * 0.8F;
                    this.colorBlueTopLeft = this.colorBlueBottomLeft = this.colorBlueBottomRight = this.colorBlueTopRight = blue
                        * 0.8F;
                } else {
                    this.colorRedTopLeft = this.colorRedBottomLeft = this.colorRedBottomRight = this.colorRedTopRight = 0.8F;
                    this.colorGreenTopLeft = this.colorGreenBottomLeft = this.colorGreenBottomRight = this.colorGreenTopRight = 0.8F;
                    this.colorBlueTopLeft = this.colorBlueBottomLeft = this.colorBlueBottomRight = this.colorBlueTopRight = 0.8F;
                }

                this.colorRedTopLeft *= f3;
                this.colorGreenTopLeft *= f3;
                this.colorBlueTopLeft *= f3;
                this.colorRedBottomLeft *= f4;
                this.colorGreenBottomLeft *= f4;
                this.colorBlueBottomLeft *= f4;
                this.colorRedBottomRight *= f5;
                this.colorGreenBottomRight *= f5;
                this.colorBlueBottomRight *= f5;
                this.colorRedTopRight *= f6;
                this.colorGreenTopRight *= f6;
                this.colorBlueTopRight *= f6;
            }
            iicon = this.getBlockIcon(block, this.blockAccess, x, y, z, 2);
            this.renderFaceZNeg(block, x, y, z, iicon);

            if (fancyGrass && iicon.getIconName()
                .equals("grass_side") && !this.hasOverrideBlockTexture()) {
                this.colorRedTopLeft *= red;
                this.colorRedBottomLeft *= red;
                this.colorRedBottomRight *= red;
                this.colorRedTopRight *= red;
                this.colorGreenTopLeft *= green;
                this.colorGreenBottomLeft *= green;
                this.colorGreenBottomRight *= green;
                this.colorGreenTopRight *= green;
                this.colorBlueTopLeft *= blue;
                this.colorBlueBottomLeft *= blue;
                this.colorBlueBottomRight *= blue;
                this.colorBlueTopRight *= blue;
                this.renderFaceZNeg(
                    block,
                    x,
                    y,
                    z,
                    CTMUtils.getBlockIcon(
                        BlockGrass.getIconSideOverlay(),
                        (RenderBlocks) (Object) this,
                        block,
                        this.blockAccess,
                        x,
                        y,
                        z,
                        2));
            }

            flag = true;
        }
        // face 3
        if (this.renderAllFaces || RenderPass.shouldSideBeRendered(block, this.blockAccess, x, y, z + 1, 3)) {
            if (this.renderMaxZ >= 1.0D) {
                ++z;
            }

            this.aoLightValueScratchXZNP = this.blockAccess.getBlock(x - 1, y, z)
                .getAmbientOcclusionLightValue();
            this.aoLightValueScratchXZPP = this.blockAccess.getBlock(x + 1, y, z)
                .getAmbientOcclusionLightValue();
            this.aoLightValueScratchYZNP = this.blockAccess.getBlock(x, y - 1, z)
                .getAmbientOcclusionLightValue();
            this.aoLightValueScratchYZPP = this.blockAccess.getBlock(x, y + 1, z)
                .getAmbientOcclusionLightValue();
            this.aoBrightnessXZNP = block.getMixedBrightnessForBlock(this.blockAccess, x - 1, y, z);
            this.aoBrightnessXZPP = block.getMixedBrightnessForBlock(this.blockAccess, x + 1, y, z);
            this.aoBrightnessYZNP = block.getMixedBrightnessForBlock(this.blockAccess, x, y - 1, z);
            this.aoBrightnessYZPP = block.getMixedBrightnessForBlock(this.blockAccess, x, y + 1, z);
            flag2 = this.blockAccess.getBlock(x + 1, y, z + 1)
                .getCanBlockGrass();
            flag3 = this.blockAccess.getBlock(x - 1, y, z + 1)
                .getCanBlockGrass();
            flag4 = this.blockAccess.getBlock(x, y + 1, z + 1)
                .getCanBlockGrass();
            flag5 = this.blockAccess.getBlock(x, y - 1, z + 1)
                .getCanBlockGrass();

            if (!flag3 && !flag5) {
                this.aoLightValueScratchXYZNNP = this.aoLightValueScratchXZNP;
                this.aoBrightnessXYZNNP = this.aoBrightnessXZNP;
            } else {
                this.aoLightValueScratchXYZNNP = this.blockAccess.getBlock(x - 1, y - 1, z)
                    .getAmbientOcclusionLightValue();
                this.aoBrightnessXYZNNP = block.getMixedBrightnessForBlock(this.blockAccess, x - 1, y - 1, z);
            }

            if (!flag3 && !flag4) {
                this.aoLightValueScratchXYZNPP = this.aoLightValueScratchXZNP;
                this.aoBrightnessXYZNPP = this.aoBrightnessXZNP;
            } else {
                this.aoLightValueScratchXYZNPP = this.blockAccess.getBlock(x - 1, y + 1, z)
                    .getAmbientOcclusionLightValue();
                this.aoBrightnessXYZNPP = block.getMixedBrightnessForBlock(this.blockAccess, x - 1, y + 1, z);
            }

            if (!flag2 && !flag5) {
                this.aoLightValueScratchXYZPNP = this.aoLightValueScratchXZPP;
                this.aoBrightnessXYZPNP = this.aoBrightnessXZPP;
            } else {
                this.aoLightValueScratchXYZPNP = this.blockAccess.getBlock(x + 1, y - 1, z)
                    .getAmbientOcclusionLightValue();
                this.aoBrightnessXYZPNP = block.getMixedBrightnessForBlock(this.blockAccess, x + 1, y - 1, z);
            }

            if (!flag2 && !flag4) {
                this.aoLightValueScratchXYZPPP = this.aoLightValueScratchXZPP;
                this.aoBrightnessXYZPPP = this.aoBrightnessXZPP;
            } else {
                this.aoLightValueScratchXYZPPP = this.blockAccess.getBlock(x + 1, y + 1, z)
                    .getAmbientOcclusionLightValue();
                this.aoBrightnessXYZPPP = block.getMixedBrightnessForBlock(this.blockAccess, x + 1, y + 1, z);
            }

            if (this.renderMaxZ >= 1.0D) {
                --z;
            }

            i1 = l;

            if (this.renderMaxZ >= 1.0D || !this.blockAccess.getBlock(x, y, z + 1)
                .isOpaqueCube()) {
                i1 = block.getMixedBrightnessForBlock(this.blockAccess, x, y, z + 1);
            }

            f7 = this.blockAccess.getBlock(x, y, z + 1)
                .getAmbientOcclusionLightValue();
            f8 = (this.aoLightValueScratchXZNP + this.aoLightValueScratchXYZNPP + f7 + this.aoLightValueScratchYZPP)
                / 4.0F;
            f9 = (f7 + this.aoLightValueScratchYZPP + this.aoLightValueScratchXZPP + this.aoLightValueScratchXYZPPP)
                / 4.0F;
            f10 = (this.aoLightValueScratchYZNP + f7 + this.aoLightValueScratchXYZPNP + this.aoLightValueScratchXZPP)
                / 4.0F;
            f11 = (this.aoLightValueScratchXYZNNP + this.aoLightValueScratchXZNP + this.aoLightValueScratchYZNP + f7)
                / 4.0F;
            f3 = (float) ((double) f8 * this.renderMaxY * (1.0D - this.renderMinX)
                + (double) f9 * this.renderMaxY * this.renderMinX
                + (double) f10 * (1.0D - this.renderMaxY) * this.renderMinX
                + (double) f11 * (1.0D - this.renderMaxY) * (1.0D - this.renderMinX));
            f4 = (float) ((double) f8 * this.renderMinY * (1.0D - this.renderMinX)
                + (double) f9 * this.renderMinY * this.renderMinX
                + (double) f10 * (1.0D - this.renderMinY) * this.renderMinX
                + (double) f11 * (1.0D - this.renderMinY) * (1.0D - this.renderMinX));
            f5 = (float) ((double) f8 * this.renderMinY * (1.0D - this.renderMaxX)
                + (double) f9 * this.renderMinY * this.renderMaxX
                + (double) f10 * (1.0D - this.renderMinY) * this.renderMaxX
                + (double) f11 * (1.0D - this.renderMinY) * (1.0D - this.renderMaxX));
            f6 = (float) ((double) f8 * this.renderMaxY * (1.0D - this.renderMaxX)
                + (double) f9 * this.renderMaxY * this.renderMaxX
                + (double) f10 * (1.0D - this.renderMaxY) * this.renderMaxX
                + (double) f11 * (1.0D - this.renderMaxY) * (1.0D - this.renderMaxX));
            j1 = this.getAoBrightness(this.aoBrightnessXZNP, this.aoBrightnessXYZNPP, this.aoBrightnessYZPP, i1);
            k1 = this.getAoBrightness(this.aoBrightnessYZPP, this.aoBrightnessXZPP, this.aoBrightnessXYZPPP, i1);
            l1 = this.getAoBrightness(this.aoBrightnessYZNP, this.aoBrightnessXYZPNP, this.aoBrightnessXZPP, i1);
            i2 = this.getAoBrightness(this.aoBrightnessXYZNNP, this.aoBrightnessXZNP, this.aoBrightnessYZNP, i1);
            this.brightnessTopLeft = this.mixAoBrightness(
                j1,
                i2,
                l1,
                k1,
                this.renderMaxY * (1.0D - this.renderMinX),
                (1.0D - this.renderMaxY) * (1.0D - this.renderMinX),
                (1.0D - this.renderMaxY) * this.renderMinX,
                this.renderMaxY * this.renderMinX);
            this.brightnessBottomLeft = this.mixAoBrightness(
                j1,
                i2,
                l1,
                k1,
                this.renderMinY * (1.0D - this.renderMinX),
                (1.0D - this.renderMinY) * (1.0D - this.renderMinX),
                (1.0D - this.renderMinY) * this.renderMinX,
                this.renderMinY * this.renderMinX);
            this.brightnessBottomRight = this.mixAoBrightness(
                j1,
                i2,
                l1,
                k1,
                this.renderMinY * (1.0D - this.renderMaxX),
                (1.0D - this.renderMinY) * (1.0D - this.renderMaxX),
                (1.0D - this.renderMinY) * this.renderMaxX,
                this.renderMinY * this.renderMaxX);
            this.brightnessTopRight = this.mixAoBrightness(
                j1,
                i2,
                l1,
                k1,
                this.renderMaxY * (1.0D - this.renderMaxX),
                (1.0D - this.renderMaxY) * (1.0D - this.renderMaxX),
                (1.0D - this.renderMaxY) * this.renderMaxX,
                this.renderMaxY * this.renderMaxX);
            if (!ColorizeBlock.setupBlockSmoothing(
                (RenderBlocks) (Object) this,
                block,
                this.blockAccess,
                x,
                y,
                z,
                3,
                f3,
                f4,
                f5,
                f6)) {
                if (RenderBlocksUtils.useColorMultiplier(3)) {
                    this.colorRedTopLeft = this.colorRedBottomLeft = this.colorRedBottomRight = this.colorRedTopRight = red
                        * 0.8F;
                    this.colorGreenTopLeft = this.colorGreenBottomLeft = this.colorGreenBottomRight = this.colorGreenTopRight = green
                        * 0.8F;
                    this.colorBlueTopLeft = this.colorBlueBottomLeft = this.colorBlueBottomRight = this.colorBlueTopRight = blue
                        * 0.8F;
                } else {
                    this.colorRedTopLeft = this.colorRedBottomLeft = this.colorRedBottomRight = this.colorRedTopRight = 0.8F;
                    this.colorGreenTopLeft = this.colorGreenBottomLeft = this.colorGreenBottomRight = this.colorGreenTopRight = 0.8F;
                    this.colorBlueTopLeft = this.colorBlueBottomLeft = this.colorBlueBottomRight = this.colorBlueTopRight = 0.8F;
                }

                this.colorRedTopLeft *= f3;
                this.colorGreenTopLeft *= f3;
                this.colorBlueTopLeft *= f3;
                this.colorRedBottomLeft *= f4;
                this.colorGreenBottomLeft *= f4;
                this.colorBlueBottomLeft *= f4;
                this.colorRedBottomRight *= f5;
                this.colorGreenBottomRight *= f5;
                this.colorBlueBottomRight *= f5;
                this.colorRedTopRight *= f6;
                this.colorGreenTopRight *= f6;
                this.colorBlueTopRight *= f6;
            }
            iicon = this.getBlockIcon(block, this.blockAccess, x, y, z, 3);
            this.renderFaceZPos(block, x, y, z, iicon);

            if (fancyGrass && iicon.getIconName()
                .equals("grass_side") && !this.hasOverrideBlockTexture()) {
                this.colorRedTopLeft *= red;
                this.colorRedBottomLeft *= red;
                this.colorRedBottomRight *= red;
                this.colorRedTopRight *= red;
                this.colorGreenTopLeft *= green;
                this.colorGreenBottomLeft *= green;
                this.colorGreenBottomRight *= green;
                this.colorGreenTopRight *= green;
                this.colorBlueTopLeft *= blue;
                this.colorBlueBottomLeft *= blue;
                this.colorBlueBottomRight *= blue;
                this.colorBlueTopRight *= blue;
                this.renderFaceZPos(
                    block,
                    x,
                    y,
                    z,
                    CTMUtils.getBlockIcon(
                        BlockGrass.getIconSideOverlay(),
                        (RenderBlocks) (Object) this,
                        block,
                        this.blockAccess,
                        x,
                        y,
                        z,
                        3));
            }

            flag = true;
        }
        // face 4
        if (this.renderAllFaces || RenderPass.shouldSideBeRendered(block, this.blockAccess, x - 1, y, z, 4)) {
            if (this.renderMinX <= 0.0D) {
                --x;
            }

            this.aoLightValueScratchXYNN = this.blockAccess.getBlock(x, y - 1, z)
                .getAmbientOcclusionLightValue();
            this.aoLightValueScratchXZNN = this.blockAccess.getBlock(x, y, z - 1)
                .getAmbientOcclusionLightValue();
            this.aoLightValueScratchXZNP = this.blockAccess.getBlock(x, y, z + 1)
                .getAmbientOcclusionLightValue();
            this.aoLightValueScratchXYNP = this.blockAccess.getBlock(x, y + 1, z)
                .getAmbientOcclusionLightValue();
            this.aoBrightnessXYNN = block.getMixedBrightnessForBlock(this.blockAccess, x, y - 1, z);
            this.aoBrightnessXZNN = block.getMixedBrightnessForBlock(this.blockAccess, x, y, z - 1);
            this.aoBrightnessXZNP = block.getMixedBrightnessForBlock(this.blockAccess, x, y, z + 1);
            this.aoBrightnessXYNP = block.getMixedBrightnessForBlock(this.blockAccess, x, y + 1, z);
            flag2 = this.blockAccess.getBlock(x - 1, y + 1, z)
                .getCanBlockGrass();
            flag3 = this.blockAccess.getBlock(x - 1, y - 1, z)
                .getCanBlockGrass();
            flag4 = this.blockAccess.getBlock(x - 1, y, z - 1)
                .getCanBlockGrass();
            flag5 = this.blockAccess.getBlock(x - 1, y, z + 1)
                .getCanBlockGrass();

            if (!flag4 && !flag3) {
                this.aoLightValueScratchXYZNNN = this.aoLightValueScratchXZNN;
                this.aoBrightnessXYZNNN = this.aoBrightnessXZNN;
            } else {
                this.aoLightValueScratchXYZNNN = this.blockAccess.getBlock(x, y - 1, z - 1)
                    .getAmbientOcclusionLightValue();
                this.aoBrightnessXYZNNN = block.getMixedBrightnessForBlock(this.blockAccess, x, y - 1, z - 1);
            }

            if (!flag5 && !flag3) {
                this.aoLightValueScratchXYZNNP = this.aoLightValueScratchXZNP;
                this.aoBrightnessXYZNNP = this.aoBrightnessXZNP;
            } else {
                this.aoLightValueScratchXYZNNP = this.blockAccess.getBlock(x, y - 1, z + 1)
                    .getAmbientOcclusionLightValue();
                this.aoBrightnessXYZNNP = block.getMixedBrightnessForBlock(this.blockAccess, x, y - 1, z + 1);
            }

            if (!flag4 && !flag2) {
                this.aoLightValueScratchXYZNPN = this.aoLightValueScratchXZNN;
                this.aoBrightnessXYZNPN = this.aoBrightnessXZNN;
            } else {
                this.aoLightValueScratchXYZNPN = this.blockAccess.getBlock(x, y + 1, z - 1)
                    .getAmbientOcclusionLightValue();
                this.aoBrightnessXYZNPN = block.getMixedBrightnessForBlock(this.blockAccess, x, y + 1, z - 1);
            }

            if (!flag5 && !flag2) {
                this.aoLightValueScratchXYZNPP = this.aoLightValueScratchXZNP;
                this.aoBrightnessXYZNPP = this.aoBrightnessXZNP;
            } else {
                this.aoLightValueScratchXYZNPP = this.blockAccess.getBlock(x, y + 1, z + 1)
                    .getAmbientOcclusionLightValue();
                this.aoBrightnessXYZNPP = block.getMixedBrightnessForBlock(this.blockAccess, x, y + 1, z + 1);
            }

            if (this.renderMinX <= 0.0D) {
                ++x;
            }

            i1 = l;

            if (this.renderMinX <= 0.0D || !this.blockAccess.getBlock(x - 1, y, z)
                .isOpaqueCube()) {
                i1 = block.getMixedBrightnessForBlock(this.blockAccess, x - 1, y, z);
            }

            f7 = this.blockAccess.getBlock(x - 1, y, z)
                .getAmbientOcclusionLightValue();
            f8 = (this.aoLightValueScratchXYNN + this.aoLightValueScratchXYZNNP + f7 + this.aoLightValueScratchXZNP)
                / 4.0F;
            f9 = (f7 + this.aoLightValueScratchXZNP + this.aoLightValueScratchXYNP + this.aoLightValueScratchXYZNPP)
                / 4.0F;
            f10 = (this.aoLightValueScratchXZNN + f7 + this.aoLightValueScratchXYZNPN + this.aoLightValueScratchXYNP)
                / 4.0F;
            f11 = (this.aoLightValueScratchXYZNNN + this.aoLightValueScratchXYNN + this.aoLightValueScratchXZNN + f7)
                / 4.0F;
            f3 = (float) ((double) f9 * this.renderMaxY * this.renderMaxZ
                + (double) f10 * this.renderMaxY * (1.0D - this.renderMaxZ)
                + (double) f11 * (1.0D - this.renderMaxY) * (1.0D - this.renderMaxZ)
                + (double) f8 * (1.0D - this.renderMaxY) * this.renderMaxZ);
            f4 = (float) ((double) f9 * this.renderMaxY * this.renderMinZ
                + (double) f10 * this.renderMaxY * (1.0D - this.renderMinZ)
                + (double) f11 * (1.0D - this.renderMaxY) * (1.0D - this.renderMinZ)
                + (double) f8 * (1.0D - this.renderMaxY) * this.renderMinZ);
            f5 = (float) ((double) f9 * this.renderMinY * this.renderMinZ
                + (double) f10 * this.renderMinY * (1.0D - this.renderMinZ)
                + (double) f11 * (1.0D - this.renderMinY) * (1.0D - this.renderMinZ)
                + (double) f8 * (1.0D - this.renderMinY) * this.renderMinZ);
            f6 = (float) ((double) f9 * this.renderMinY * this.renderMaxZ
                + (double) f10 * this.renderMinY * (1.0D - this.renderMaxZ)
                + (double) f11 * (1.0D - this.renderMinY) * (1.0D - this.renderMaxZ)
                + (double) f8 * (1.0D - this.renderMinY) * this.renderMaxZ);
            j1 = this.getAoBrightness(this.aoBrightnessXYNN, this.aoBrightnessXYZNNP, this.aoBrightnessXZNP, i1);
            k1 = this.getAoBrightness(this.aoBrightnessXZNP, this.aoBrightnessXYNP, this.aoBrightnessXYZNPP, i1);
            l1 = this.getAoBrightness(this.aoBrightnessXZNN, this.aoBrightnessXYZNPN, this.aoBrightnessXYNP, i1);
            i2 = this.getAoBrightness(this.aoBrightnessXYZNNN, this.aoBrightnessXYNN, this.aoBrightnessXZNN, i1);
            this.brightnessTopLeft = this.mixAoBrightness(
                k1,
                l1,
                i2,
                j1,
                this.renderMaxY * this.renderMaxZ,
                this.renderMaxY * (1.0D - this.renderMaxZ),
                (1.0D - this.renderMaxY) * (1.0D - this.renderMaxZ),
                (1.0D - this.renderMaxY) * this.renderMaxZ);
            this.brightnessBottomLeft = this.mixAoBrightness(
                k1,
                l1,
                i2,
                j1,
                this.renderMaxY * this.renderMinZ,
                this.renderMaxY * (1.0D - this.renderMinZ),
                (1.0D - this.renderMaxY) * (1.0D - this.renderMinZ),
                (1.0D - this.renderMaxY) * this.renderMinZ);
            this.brightnessBottomRight = this.mixAoBrightness(
                k1,
                l1,
                i2,
                j1,
                this.renderMinY * this.renderMinZ,
                this.renderMinY * (1.0D - this.renderMinZ),
                (1.0D - this.renderMinY) * (1.0D - this.renderMinZ),
                (1.0D - this.renderMinY) * this.renderMinZ);
            this.brightnessTopRight = this.mixAoBrightness(
                k1,
                l1,
                i2,
                j1,
                this.renderMinY * this.renderMaxZ,
                this.renderMinY * (1.0D - this.renderMaxZ),
                (1.0D - this.renderMinY) * (1.0D - this.renderMaxZ),
                (1.0D - this.renderMinY) * this.renderMaxZ);
            if (!ColorizeBlock.setupBlockSmoothing(
                (RenderBlocks) (Object) this,
                block,
                this.blockAccess,
                x,
                y,
                z,
                4,
                f3,
                f4,
                f5,
                f6)) {
                if (RenderBlocksUtils.useColorMultiplier(4)) {
                    this.colorRedTopLeft = this.colorRedBottomLeft = this.colorRedBottomRight = this.colorRedTopRight = red
                        * 0.6F;
                    this.colorGreenTopLeft = this.colorGreenBottomLeft = this.colorGreenBottomRight = this.colorGreenTopRight = green
                        * 0.6F;
                    this.colorBlueTopLeft = this.colorBlueBottomLeft = this.colorBlueBottomRight = this.colorBlueTopRight = blue
                        * 0.6F;
                } else {
                    this.colorRedTopLeft = this.colorRedBottomLeft = this.colorRedBottomRight = this.colorRedTopRight = 0.6F;
                    this.colorGreenTopLeft = this.colorGreenBottomLeft = this.colorGreenBottomRight = this.colorGreenTopRight = 0.6F;
                    this.colorBlueTopLeft = this.colorBlueBottomLeft = this.colorBlueBottomRight = this.colorBlueTopRight = 0.6F;
                }

                this.colorRedTopLeft *= f3;
                this.colorGreenTopLeft *= f3;
                this.colorBlueTopLeft *= f3;
                this.colorRedBottomLeft *= f4;
                this.colorGreenBottomLeft *= f4;
                this.colorBlueBottomLeft *= f4;
                this.colorRedBottomRight *= f5;
                this.colorGreenBottomRight *= f5;
                this.colorBlueBottomRight *= f5;
                this.colorRedTopRight *= f6;
                this.colorGreenTopRight *= f6;
                this.colorBlueTopRight *= f6;
            }
            iicon = this.getBlockIcon(block, this.blockAccess, x, y, z, 4);
            this.renderFaceXNeg(block, x, y, z, iicon);

            if (fancyGrass && iicon.getIconName()
                .equals("grass_side") && !this.hasOverrideBlockTexture()) {
                this.colorRedTopLeft *= red;
                this.colorRedBottomLeft *= red;
                this.colorRedBottomRight *= red;
                this.colorRedTopRight *= red;
                this.colorGreenTopLeft *= green;
                this.colorGreenBottomLeft *= green;
                this.colorGreenBottomRight *= green;
                this.colorGreenTopRight *= green;
                this.colorBlueTopLeft *= blue;
                this.colorBlueBottomLeft *= blue;
                this.colorBlueBottomRight *= blue;
                this.colorBlueTopRight *= blue;
                this.renderFaceXNeg(
                    block,
                    x,
                    y,
                    z,
                    CTMUtils.getBlockIcon(
                        BlockGrass.getIconSideOverlay(),
                        (RenderBlocks) (Object) this,
                        block,
                        this.blockAccess,
                        x,
                        y,
                        z,
                        4));
            }

            flag = true;
        }
        // face 5
        if (this.renderAllFaces || RenderPass.shouldSideBeRendered(block, this.blockAccess, x + 1, y, z, 5)) {
            if (this.renderMaxX >= 1.0D) {
                ++x;
            }

            this.aoLightValueScratchXYPN = this.blockAccess.getBlock(x, y - 1, z)
                .getAmbientOcclusionLightValue();
            this.aoLightValueScratchXZPN = this.blockAccess.getBlock(x, y, z - 1)
                .getAmbientOcclusionLightValue();
            this.aoLightValueScratchXZPP = this.blockAccess.getBlock(x, y, z + 1)
                .getAmbientOcclusionLightValue();
            this.aoLightValueScratchXYPP = this.blockAccess.getBlock(x, y + 1, z)
                .getAmbientOcclusionLightValue();
            this.aoBrightnessXYPN = block.getMixedBrightnessForBlock(this.blockAccess, x, y - 1, z);
            this.aoBrightnessXZPN = block.getMixedBrightnessForBlock(this.blockAccess, x, y, z - 1);
            this.aoBrightnessXZPP = block.getMixedBrightnessForBlock(this.blockAccess, x, y, z + 1);
            this.aoBrightnessXYPP = block.getMixedBrightnessForBlock(this.blockAccess, x, y + 1, z);
            flag2 = this.blockAccess.getBlock(x + 1, y + 1, z)
                .getCanBlockGrass();
            flag3 = this.blockAccess.getBlock(x + 1, y - 1, z)
                .getCanBlockGrass();
            flag4 = this.blockAccess.getBlock(x + 1, y, z + 1)
                .getCanBlockGrass();
            flag5 = this.blockAccess.getBlock(x + 1, y, z - 1)
                .getCanBlockGrass();

            if (!flag3 && !flag5) {
                this.aoLightValueScratchXYZPNN = this.aoLightValueScratchXZPN;
                this.aoBrightnessXYZPNN = this.aoBrightnessXZPN;
            } else {
                this.aoLightValueScratchXYZPNN = this.blockAccess.getBlock(x, y - 1, z - 1)
                    .getAmbientOcclusionLightValue();
                this.aoBrightnessXYZPNN = block.getMixedBrightnessForBlock(this.blockAccess, x, y - 1, z - 1);
            }

            if (!flag3 && !flag4) {
                this.aoLightValueScratchXYZPNP = this.aoLightValueScratchXZPP;
                this.aoBrightnessXYZPNP = this.aoBrightnessXZPP;
            } else {
                this.aoLightValueScratchXYZPNP = this.blockAccess.getBlock(x, y - 1, z + 1)
                    .getAmbientOcclusionLightValue();
                this.aoBrightnessXYZPNP = block.getMixedBrightnessForBlock(this.blockAccess, x, y - 1, z + 1);
            }

            if (!flag2 && !flag5) {
                this.aoLightValueScratchXYZPPN = this.aoLightValueScratchXZPN;
                this.aoBrightnessXYZPPN = this.aoBrightnessXZPN;
            } else {
                this.aoLightValueScratchXYZPPN = this.blockAccess.getBlock(x, y + 1, z - 1)
                    .getAmbientOcclusionLightValue();
                this.aoBrightnessXYZPPN = block.getMixedBrightnessForBlock(this.blockAccess, x, y + 1, z - 1);
            }

            if (!flag2 && !flag4) {
                this.aoLightValueScratchXYZPPP = this.aoLightValueScratchXZPP;
                this.aoBrightnessXYZPPP = this.aoBrightnessXZPP;
            } else {
                this.aoLightValueScratchXYZPPP = this.blockAccess.getBlock(x, y + 1, z + 1)
                    .getAmbientOcclusionLightValue();
                this.aoBrightnessXYZPPP = block.getMixedBrightnessForBlock(this.blockAccess, x, y + 1, z + 1);
            }

            if (this.renderMaxX >= 1.0D) {
                --x;
            }

            i1 = l;

            if (this.renderMaxX >= 1.0D || !this.blockAccess.getBlock(x + 1, y, z)
                .isOpaqueCube()) {
                i1 = block.getMixedBrightnessForBlock(this.blockAccess, x + 1, y, z);
            }

            f7 = this.blockAccess.getBlock(x + 1, y, z)
                .getAmbientOcclusionLightValue();
            f8 = (this.aoLightValueScratchXYPN + this.aoLightValueScratchXYZPNP + f7 + this.aoLightValueScratchXZPP)
                / 4.0F;
            f9 = (this.aoLightValueScratchXYZPNN + this.aoLightValueScratchXYPN + this.aoLightValueScratchXZPN + f7)
                / 4.0F;
            f10 = (this.aoLightValueScratchXZPN + f7 + this.aoLightValueScratchXYZPPN + this.aoLightValueScratchXYPP)
                / 4.0F;
            f11 = (f7 + this.aoLightValueScratchXZPP + this.aoLightValueScratchXYPP + this.aoLightValueScratchXYZPPP)
                / 4.0F;
            f3 = (float) ((double) f8 * (1.0D - this.renderMinY) * this.renderMaxZ
                + (double) f9 * (1.0D - this.renderMinY) * (1.0D - this.renderMaxZ)
                + (double) f10 * this.renderMinY * (1.0D - this.renderMaxZ)
                + (double) f11 * this.renderMinY * this.renderMaxZ);
            f4 = (float) ((double) f8 * (1.0D - this.renderMinY) * this.renderMinZ
                + (double) f9 * (1.0D - this.renderMinY) * (1.0D - this.renderMinZ)
                + (double) f10 * this.renderMinY * (1.0D - this.renderMinZ)
                + (double) f11 * this.renderMinY * this.renderMinZ);
            f5 = (float) ((double) f8 * (1.0D - this.renderMaxY) * this.renderMinZ
                + (double) f9 * (1.0D - this.renderMaxY) * (1.0D - this.renderMinZ)
                + (double) f10 * this.renderMaxY * (1.0D - this.renderMinZ)
                + (double) f11 * this.renderMaxY * this.renderMinZ);
            f6 = (float) ((double) f8 * (1.0D - this.renderMaxY) * this.renderMaxZ
                + (double) f9 * (1.0D - this.renderMaxY) * (1.0D - this.renderMaxZ)
                + (double) f10 * this.renderMaxY * (1.0D - this.renderMaxZ)
                + (double) f11 * this.renderMaxY * this.renderMaxZ);
            j1 = this.getAoBrightness(this.aoBrightnessXYPN, this.aoBrightnessXYZPNP, this.aoBrightnessXZPP, i1);
            k1 = this.getAoBrightness(this.aoBrightnessXZPP, this.aoBrightnessXYPP, this.aoBrightnessXYZPPP, i1);
            l1 = this.getAoBrightness(this.aoBrightnessXZPN, this.aoBrightnessXYZPPN, this.aoBrightnessXYPP, i1);
            i2 = this.getAoBrightness(this.aoBrightnessXYZPNN, this.aoBrightnessXYPN, this.aoBrightnessXZPN, i1);
            this.brightnessTopLeft = this.mixAoBrightness(
                j1,
                i2,
                l1,
                k1,
                (1.0D - this.renderMinY) * this.renderMaxZ,
                (1.0D - this.renderMinY) * (1.0D - this.renderMaxZ),
                this.renderMinY * (1.0D - this.renderMaxZ),
                this.renderMinY * this.renderMaxZ);
            this.brightnessBottomLeft = this.mixAoBrightness(
                j1,
                i2,
                l1,
                k1,
                (1.0D - this.renderMinY) * this.renderMinZ,
                (1.0D - this.renderMinY) * (1.0D - this.renderMinZ),
                this.renderMinY * (1.0D - this.renderMinZ),
                this.renderMinY * this.renderMinZ);
            this.brightnessBottomRight = this.mixAoBrightness(
                j1,
                i2,
                l1,
                k1,
                (1.0D - this.renderMaxY) * this.renderMinZ,
                (1.0D - this.renderMaxY) * (1.0D - this.renderMinZ),
                this.renderMaxY * (1.0D - this.renderMinZ),
                this.renderMaxY * this.renderMinZ);
            this.brightnessTopRight = this.mixAoBrightness(
                j1,
                i2,
                l1,
                k1,
                (1.0D - this.renderMaxY) * this.renderMaxZ,
                (1.0D - this.renderMaxY) * (1.0D - this.renderMaxZ),
                this.renderMaxY * (1.0D - this.renderMaxZ),
                this.renderMaxY * this.renderMaxZ);
            if (!ColorizeBlock.setupBlockSmoothing(
                (RenderBlocks) (Object) this,
                block,
                this.blockAccess,
                x,
                y,
                z,
                5,
                f3,
                f4,
                f5,
                f6)) {
                if (RenderBlocksUtils.useColorMultiplier(5)) {
                    this.colorRedTopLeft = this.colorRedBottomLeft = this.colorRedBottomRight = this.colorRedTopRight = red
                        * 0.6F;
                    this.colorGreenTopLeft = this.colorGreenBottomLeft = this.colorGreenBottomRight = this.colorGreenTopRight = green
                        * 0.6F;
                    this.colorBlueTopLeft = this.colorBlueBottomLeft = this.colorBlueBottomRight = this.colorBlueTopRight = blue
                        * 0.6F;
                } else {
                    this.colorRedTopLeft = this.colorRedBottomLeft = this.colorRedBottomRight = this.colorRedTopRight = 0.6F;
                    this.colorGreenTopLeft = this.colorGreenBottomLeft = this.colorGreenBottomRight = this.colorGreenTopRight = 0.6F;
                    this.colorBlueTopLeft = this.colorBlueBottomLeft = this.colorBlueBottomRight = this.colorBlueTopRight = 0.6F;
                }

                this.colorRedTopLeft *= f3;
                this.colorGreenTopLeft *= f3;
                this.colorBlueTopLeft *= f3;
                this.colorRedBottomLeft *= f4;
                this.colorGreenBottomLeft *= f4;
                this.colorBlueBottomLeft *= f4;
                this.colorRedBottomRight *= f5;
                this.colorGreenBottomRight *= f5;
                this.colorBlueBottomRight *= f5;
                this.colorRedTopRight *= f6;
                this.colorGreenTopRight *= f6;
                this.colorBlueTopRight *= f6;
            }
            iicon = this.getBlockIcon(block, this.blockAccess, x, y, z, 5);
            this.renderFaceXPos(block, x, y, z, iicon);

            if (fancyGrass && iicon.getIconName()
                .equals("grass_side") && !this.hasOverrideBlockTexture()) {
                this.colorRedTopLeft *= red;
                this.colorRedBottomLeft *= red;
                this.colorRedBottomRight *= red;
                this.colorRedTopRight *= red;
                this.colorGreenTopLeft *= green;
                this.colorGreenBottomLeft *= green;
                this.colorGreenBottomRight *= green;
                this.colorGreenTopRight *= green;
                this.colorBlueTopLeft *= blue;
                this.colorBlueBottomLeft *= blue;
                this.colorBlueBottomRight *= blue;
                this.colorBlueTopRight *= blue;
                this.renderFaceXPos(
                    block,
                    x,
                    y,
                    z,
                    CTMUtils.getBlockIcon(
                        BlockGrass.getIconSideOverlay(),
                        (RenderBlocks) (Object) this,
                        block,
                        this.blockAccess,
                        x,
                        y,
                        z,
                        5));
            }

            flag = true;
        }

        this.enableAO = false;
        return flag;
    }

    /**
     * @author Mist475 (adapted from Paul Rupe)
     * @reason Significant deviation from Vanilla
     *         TODO: look at again for compatability
     */
    @Overwrite
    public boolean renderStandardBlockWithColorMultiplier(Block block, int x, int y, int z, float red, float green,
        float blue) {
        this.enableAO = false;
        Tessellator tessellator = Tessellator.instance;
        boolean flag = false;

        final float n7 = 0.8f;
        final float n8 = 0.6f;
        float n9 = n7;
        float n10 = n8;
        float n11 = n7;
        float n12 = n8;
        float n13 = n7;
        float n14 = n8;

        if (block != Blocks.grass) {

            n9 *= red;
            n10 *= red;
            n11 *= green;
            n12 *= green;
            n13 *= blue;
            n14 *= blue;
        }

        int l = block.getMixedBrightnessForBlock(this.blockAccess, x, y, z);
        // face 0
        if (this.renderAllFaces || RenderPass.shouldSideBeRendered(block, this.blockAccess, x, y - 1, z, 0)) {
            tessellator.setBrightness(
                this.renderMinY > 0.0D ? l : block.getMixedBrightnessForBlock(this.blockAccess, x, y - 1, z));
            tessellator.setColorOpaque_F(
                RenderBlocksUtils.getColorMultiplierRed(0),
                RenderBlocksUtils.getColorMultiplierGreen(0),
                RenderBlocksUtils.getColorMultiplierBlue(0));
            this.renderFaceYNeg(block, x, y, z, this.getBlockIcon(block, this.blockAccess, x, y, z, 0));
            flag = true;
        }
        // face 1
        if (this.renderAllFaces || RenderPass.shouldSideBeRendered(block, this.blockAccess, x, y + 1, z, 1)) {
            tessellator.setBrightness(
                this.renderMaxY < 1.0D ? l : block.getMixedBrightnessForBlock(this.blockAccess, x, y + 1, z));
            tessellator.setColorOpaque_F(
                RenderBlocksUtils.getColorMultiplierRed(1),
                RenderBlocksUtils.getColorMultiplierGreen(1),
                RenderBlocksUtils.getColorMultiplierBlue(1));
            this.renderFaceYPos(block, x, y, z, this.getBlockIcon(block, this.blockAccess, x, y, z, 1));
            flag = true;
        }

        IIcon iicon;
        // face 2
        if (this.renderAllFaces || RenderPass.shouldSideBeRendered(block, this.blockAccess, x, y, z - 1, 2)) {
            tessellator.setBrightness(
                this.renderMinZ > 0.0D ? l : block.getMixedBrightnessForBlock(this.blockAccess, x, y, z - 1));
            tessellator.setColorOpaque_F(
                RenderBlocksUtils.getColorMultiplierRed(2),
                RenderBlocksUtils.getColorMultiplierGreen(2),
                RenderBlocksUtils.getColorMultiplierBlue(2));
            iicon = this.getBlockIcon(block, this.blockAccess, x, y, z, 2);
            this.renderFaceZNeg(block, x, y, z, iicon);

            if (fancyGrass && iicon.getIconName()
                .equals("grass_side") && !this.hasOverrideBlockTexture()) {
                tessellator.setColorOpaque_F(n9 * red, n11 * green, n13 * blue);
                this.renderFaceZNeg(
                    block,
                    x,
                    y,
                    z,
                    CTMUtils.getBlockIcon(
                        BlockGrass.getIconSideOverlay(),
                        (RenderBlocks) (Object) this,
                        block,
                        this.blockAccess,
                        x,
                        y,
                        z,
                        2));
            }

            flag = true;
        }
        // face 3
        if (this.renderAllFaces || RenderPass.shouldSideBeRendered(block, this.blockAccess, x, y, z + 1, 3)) {
            tessellator.setBrightness(
                this.renderMaxZ < 1.0D ? l : block.getMixedBrightnessForBlock(this.blockAccess, x, y, z + 1));
            tessellator.setColorOpaque_F(
                RenderBlocksUtils.getColorMultiplierRed(3),
                RenderBlocksUtils.getColorMultiplierGreen(3),
                RenderBlocksUtils.getColorMultiplierBlue(3));
            iicon = this.getBlockIcon(block, this.blockAccess, x, y, z, 3);
            this.renderFaceZPos(block, x, y, z, iicon);

            if (fancyGrass && iicon.getIconName()
                .equals("grass_side") && !this.hasOverrideBlockTexture()) {
                tessellator.setColorOpaque_F(n9 * red, n11 * green, n13 * blue);
                this.renderFaceZPos(
                    block,
                    x,
                    y,
                    z,
                    CTMUtils.getBlockIcon(
                        BlockGrass.getIconSideOverlay(),
                        (RenderBlocks) (Object) this,
                        block,
                        this.blockAccess,
                        x,
                        y,
                        z,
                        3));
            }

            flag = true;
        }
        // face 4
        if (this.renderAllFaces || RenderPass.shouldSideBeRendered(block, this.blockAccess, x - 1, y, z, 4)) {
            tessellator.setBrightness(
                this.renderMinX > 0.0D ? l : block.getMixedBrightnessForBlock(this.blockAccess, x - 1, y, z));
            tessellator.setColorOpaque_F(
                RenderBlocksUtils.getColorMultiplierRed(4),
                RenderBlocksUtils.getColorMultiplierGreen(4),
                RenderBlocksUtils.getColorMultiplierBlue(4));
            iicon = this.getBlockIcon(block, this.blockAccess, x, y, z, 4);
            this.renderFaceXNeg(block, x, y, z, iicon);

            if (fancyGrass && iicon.getIconName()
                .equals("grass_side") && !this.hasOverrideBlockTexture()) {
                tessellator.setColorOpaque_F(n10 * red, n12 * green, n14 * blue);
                this.renderFaceXNeg(
                    block,
                    x,
                    y,
                    z,
                    CTMUtils.getBlockIcon(
                        BlockGrass.getIconSideOverlay(),
                        (RenderBlocks) (Object) this,
                        block,
                        this.blockAccess,
                        x,
                        y,
                        z,
                        4));
            }

            flag = true;
        }
        // face 5
        if (this.renderAllFaces || RenderPass.shouldSideBeRendered(block, this.blockAccess, x + 1, y, z, 5)) {
            tessellator.setBrightness(
                this.renderMaxX < 1.0D ? l : block.getMixedBrightnessForBlock(this.blockAccess, x + 1, y, z));
            tessellator.setColorOpaque_F(
                RenderBlocksUtils.getColorMultiplierRed(5),
                RenderBlocksUtils.getColorMultiplierGreen(5),
                RenderBlocksUtils.getColorMultiplierBlue(5));
            iicon = this.getBlockIcon(block, this.blockAccess, x, y, z, 5);
            this.renderFaceXPos(block, x, y, z, iicon);

            if (fancyGrass && iicon.getIconName()
                .equals("grass_side") && !this.hasOverrideBlockTexture()) {
                tessellator.setColorOpaque_F(n10 * red, n12 * green, n14 * blue);
                this.renderFaceXPos(
                    block,
                    x,
                    y,
                    z,
                    CTMUtils.getBlockIcon(
                        BlockGrass.getIconSideOverlay(),
                        (RenderBlocks) (Object) this,
                        block,
                        this.blockAccess,
                        x,
                        y,
                        z,
                        5));
            }

            flag = true;
        }

        return flag;
    }

    @Redirect(
        method = "renderBlockCactusImpl(Lnet/minecraft/block/Block;IIIFFF)Z",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/block/Block;shouldSideBeRendered(Lnet/minecraft/world/IBlockAccess;IIII)Z"))
    private boolean modifyRenderBlockCactusImpl(Block block, IBlockAccess blockAccess, int x, int y, int z, int side) {
        return RenderPass.shouldSideBeRendered(block, blockAccess, x, y, z, side);
    }

    @Redirect(
        method = "renderBlockHopperMetadata(Lnet/minecraft/block/BlockHopper;IIIIZ)Z",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/renderer/RenderBlocks;getBlockIconFromSideAndMetadata(Lnet/minecraft/block/Block;II)Lnet/minecraft/util/IIcon;"))
    private IIcon modifyRenderBlockHopperMetadata(RenderBlocks instance, Block block, int side, int meta,
        BlockHopper specializedBlock, int x, int y, int z) {
        return (this.blockAccess == null) ? this.getBlockIconFromSideAndMetadata(block, side, meta)
            : this.getBlockIcon(block, this.blockAccess, x, y, z, side);
    }

    @Redirect(
        method = "getBlockIcon(Lnet/minecraft/block/Block;Lnet/minecraft/world/IBlockAccess;IIII)Lnet/minecraft/util/IIcon;",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/renderer/RenderBlocks;getIconSafe(Lnet/minecraft/util/IIcon;)Lnet/minecraft/util/IIcon;"))
    private IIcon modifyGetBlockIcon(RenderBlocks instance, IIcon texture, Block block, IBlockAccess blockAccess, int x,
        int y, int z, int side) {
        return CTMUtils.getBlockIcon(
            this.getIconSafe(block.getIcon(blockAccess, x, y, z, side)),
            (RenderBlocks) (Object) this,
            block,
            blockAccess,
            x,
            y,
            z,
            side);
    }

    @Redirect(
        method = "getBlockIconFromSideAndMetadata(Lnet/minecraft/block/Block;II)Lnet/minecraft/util/IIcon;",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/renderer/RenderBlocks;getIconSafe(Lnet/minecraft/util/IIcon;)Lnet/minecraft/util/IIcon;"))
    private IIcon modifyGetBlockIconFromSideAndMetadata(RenderBlocks instance, IIcon texture, Block block, int side,
        int meta) {
        return CTMUtils
            .getBlockIcon(this.getIconSafe(block.getIcon(side, meta)), (RenderBlocks) (Object) this, block, side, meta);
    }

    @Redirect(
        method = "getBlockIconFromSide(Lnet/minecraft/block/Block;I)Lnet/minecraft/util/IIcon;",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/renderer/RenderBlocks;getIconSafe(Lnet/minecraft/util/IIcon;)Lnet/minecraft/util/IIcon;"))
    private IIcon modifyGetBlockIconFromSide(RenderBlocks instance, IIcon texture, Block block, int side) {
        return CTMUtils.getBlockIcon(
            this.getIconSafe(this.getIconSafe(block.getBlockTextureFromSide(side))),
            (RenderBlocks) (Object) this,
            block,
            side);
    }
}
