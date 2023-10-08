package mist475.mcpatcherforge.mixins.early.client.renderer.RenderBlocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import com.prupe.mcpatcher.cc.ColorizeBlock;
import com.prupe.mcpatcher.renderpass.RenderPass;

@Mixin(RenderBlocks.class)
public abstract class MixinRenderBlocksRenderBlockLiquid {

    @Shadow
    public IBlockAccess blockAccess;
    @Shadow
    public boolean enableAO;

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

    @Unique
    private void mcpatcherforge_actual_public$colorAndVertex(Tessellator tessellator, float red, float green,
        float blue, double x, double y, double z, double u, double v) {
        if (ColorizeBlock.isSmooth) {
            tessellator.setColorOpaque_F(red, green, blue);
        }
        tessellator.addVertexWithUV(x, y, z, u, v);
    }

    @Redirect(
        method = "renderBlockLiquid(Lnet/minecraft/block/Block;III)Z",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/block/Block;shouldSideBeRendered(Lnet/minecraft/world/IBlockAccess;IIII)Z"))
    private boolean modifyRenderBlockLiquid1(Block block, IBlockAccess worldIn, int x, int y, int z, int side) {
        return RenderPass.shouldSideBeRendered(block, worldIn, x, y, z, side);
    }

    // Redirect calls to this.getBlockIcon when possible

    @Redirect(
        method = "renderBlockLiquid(Lnet/minecraft/block/Block;III)Z",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/renderer/RenderBlocks;getBlockIconFromSideAndMetadata(Lnet/minecraft/block/Block;II)Lnet/minecraft/util/IIcon;"))
    private IIcon modifyRenderBlockLiquid2(RenderBlocks instance, Block block, int side, int meta,
        Block specializedBlock, int x, int y, int z) {
        return (this.blockAccess == null) ? this.getBlockIconFromSideAndMetadata(block, side, meta)
            : this.getBlockIcon(block, this.blockAccess, x, y, z, side);
    }

    @Redirect(
        method = "renderBlockLiquid(Lnet/minecraft/block/Block;III)Z",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/renderer/RenderBlocks;getBlockIconFromSide(Lnet/minecraft/block/Block;I)Lnet/minecraft/util/IIcon;"))
    private IIcon modifyRenderBlockLiquid3(RenderBlocks instance, Block block, int side, Block specializedBlock, int x,
        int y, int z) {
        return (this.blockAccess == null) ? this.getBlockIconFromSide(block, side)
            : this.getBlockIcon(block, this.blockAccess, x, y, z, side);
    }

    // Handle smoothing

    @Redirect(
        method = "renderBlockLiquid(Lnet/minecraft/block/Block;III)Z",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/renderer/Tessellator;setColorOpaque_F(FFF)V",
            ordinal = 0))
    private void modifyRenderBlockLiquid4(Tessellator tessellator, float red, float green, float blue, Block block,
        int x, int y, int z) {
        if (!(ColorizeBlock.isSmooth = ColorizeBlock
            .setupBlockSmoothing((RenderBlocks) (Object) this, block, this.blockAccess, x, y, z, 1 + 6))) {
            tessellator.setColorOpaque_F(red, green, blue);
        }
    }

    // Violate DRY

    @Redirect(
        method = "renderBlockLiquid(Lnet/minecraft/block/Block;III)Z",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/renderer/Tessellator;addVertexWithUV(DDDDD)V",
            ordinal = 0))
    private void modifyRenderBlockLiquid5(Tessellator tessellator, double x, double y, double z, double u, double v) {
        mcpatcherforge_actual_public$colorAndVertex(
            tessellator,
            this.colorRedTopLeft,
            this.colorGreenTopLeft,
            this.colorBlueTopLeft,
            x,
            y,
            z,
            u,
            v);
    }

    @Redirect(
        method = "renderBlockLiquid(Lnet/minecraft/block/Block;III)Z",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/renderer/Tessellator;addVertexWithUV(DDDDD)V",
            ordinal = 1))
    private void modifyRenderBlockLiquid6(Tessellator tessellator, double x, double y, double z, double u, double v) {
        mcpatcherforge_actual_public$colorAndVertex(
            tessellator,
            this.colorRedBottomLeft,
            this.colorGreenBottomLeft,
            this.colorBlueBottomLeft,
            x,
            y,
            z,
            u,
            v);
    }

    @Redirect(
        method = "renderBlockLiquid(Lnet/minecraft/block/Block;III)Z",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/renderer/Tessellator;addVertexWithUV(DDDDD)V",
            ordinal = 2))
    private void modifyRenderBlockLiquid7(Tessellator tessellator, double x, double y, double z, double u, double v) {
        mcpatcherforge_actual_public$colorAndVertex(
            tessellator,
            this.colorRedBottomRight,
            this.colorGreenBottomRight,
            this.colorBlueBottomRight,
            x,
            y,
            z,
            u,
            v);
    }

    @Redirect(
        method = "renderBlockLiquid(Lnet/minecraft/block/Block;III)Z",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/renderer/Tessellator;addVertexWithUV(DDDDD)V",
            ordinal = 3))
    private void modifyRenderBlockLiquid8(Tessellator tessellator, double x, double y, double z, double u, double v) {
        mcpatcherforge_actual_public$colorAndVertex(
            tessellator,
            this.colorRedTopRight,
            this.colorGreenTopRight,
            this.colorBlueTopRight,
            x,
            y,
            z,
            u,
            v);
    }

    @Redirect(
        method = "renderBlockLiquid(Lnet/minecraft/block/Block;III)Z",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/renderer/Tessellator;addVertexWithUV(DDDDD)V",
            ordinal = 4))
    private void modifyRenderBlockLiquid9(Tessellator tessellator, double x, double y, double z, double u, double v) {
        mcpatcherforge_actual_public$colorAndVertex(
            tessellator,
            this.colorRedTopLeft,
            this.colorGreenTopLeft,
            this.colorBlueTopLeft,
            x,
            y,
            z,
            u,
            v);
    }

    @Redirect(
        method = "renderBlockLiquid(Lnet/minecraft/block/Block;III)Z",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/renderer/Tessellator;addVertexWithUV(DDDDD)V",
            ordinal = 5))
    private void modifyRenderBlockLiquid10(Tessellator tessellator, double x, double y, double z, double u, double v) {
        mcpatcherforge_actual_public$colorAndVertex(
            tessellator,
            this.colorRedTopRight,
            this.colorGreenTopRight,
            this.colorBlueTopRight,
            x,
            y,
            z,
            u,
            v);
    }

    @Redirect(
        method = "renderBlockLiquid(Lnet/minecraft/block/Block;III)Z",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/renderer/Tessellator;addVertexWithUV(DDDDD)V",
            ordinal = 6))
    private void modifyRenderBlockLiquid11(Tessellator tessellator, double x, double y, double z, double u, double v) {
        mcpatcherforge_actual_public$colorAndVertex(
            tessellator,
            this.colorRedBottomRight,
            this.colorGreenBottomRight,
            this.colorBlueBottomRight,
            x,
            y,
            z,
            u,
            v);
    }

    @Redirect(
        method = "renderBlockLiquid(Lnet/minecraft/block/Block;III)Z",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/renderer/Tessellator;addVertexWithUV(DDDDD)V",
            ordinal = 7))
    private void modifyRenderBlockLiquid12(Tessellator tessellator, double x, double y, double z, double u, double v) {
        mcpatcherforge_actual_public$colorAndVertex(
            tessellator,
            this.colorRedBottomLeft,
            this.colorGreenBottomLeft,
            this.colorBlueBottomLeft,
            x,
            y,
            z,
            u,
            v);
    }

    @Redirect(
        method = "renderBlockLiquid(Lnet/minecraft/block/Block;III)Z",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/renderer/Tessellator;setColorOpaque_F(FFF)V",
            ordinal = 1))
    private void modifyRenderBlockLiquid13(Tessellator tessellator, float red, float green, float blue, Block block,
        int x, int y, int z) {
        if (!(ColorizeBlock.isSmooth = ColorizeBlock
            .setupBlockSmoothing((RenderBlocks) (Object) this, block, this.blockAccess, x, y, z, 6))) {
            tessellator.setColorOpaque_F(red, green, blue);
        }
        if (ColorizeBlock.isSmooth) {
            this.enableAO = true;
        }
    }

    @Inject(
        method = "renderBlockLiquid(Lnet/minecraft/block/Block;III)Z",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/renderer/RenderBlocks;renderFaceYNeg(Lnet/minecraft/block/Block;DDDLnet/minecraft/util/IIcon;)V"))
    private void modifyRenderBlockLiquid14(Block block, int x, int y, int z, CallbackInfoReturnable<Boolean> cir) {
        this.enableAO = false;
    }

    // inject new code block, nullify initial call

    // I LOVE LOCAL CAPTURE
    @Inject(
        method = "renderBlockLiquid(Lnet/minecraft/block/Block;III)Z",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/renderer/Tessellator;setColorOpaque_F(FFF)V",
            ordinal = 2),
        locals = LocalCapture.CAPTURE_FAILHARD)
    private void modifyRenderBlockLiquid15(Block block, int x, int y, int z, CallbackInfoReturnable<Boolean> cir,
        Tessellator tessellator, int l, float f, float f1, float f2, boolean flag, boolean flag1, boolean[] aboolean,
        boolean flag2, float f3, float f4, float f5, float f6, double d0, double d1, Material material, int i1,
        double d2, double d3, double d4, double d5, double d6, float f9, float f10, float f11, int k1, int l1, int j1,
        IIcon iicon1, double d9, double d11, double d13, double d15, double d17, double d19, float f8, float f12,
        float f13) {
        if (!(ColorizeBlock.isSmooth = ColorizeBlock
            .setupBlockSmoothing((RenderBlocks) (Object) this, block, this.blockAccess, x, y, z, k1 + 2 + 6))) {
            tessellator.setColorOpaque_F(f4 * f13 * f, f4 * f13 * f1, f4 * f13 * f2);
        }

    }

    @Redirect(
        method = "renderBlockLiquid(Lnet/minecraft/block/Block;III)Z",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/renderer/Tessellator;setColorOpaque_F(FFF)V",
            ordinal = 2))
    private void modifyRenderBlockLiquid16(Tessellator tessellator, float red, float green, float blue, Block block,
        int x, int y, int z) {

    }

    @Redirect(
        method = "renderBlockLiquid(Lnet/minecraft/block/Block;III)Z",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/renderer/Tessellator;addVertexWithUV(DDDDD)V",
            ordinal = 8))
    private void modifyRenderBlockLiquid17(Tessellator tessellator, double x, double y, double z, double u, double v) {
        mcpatcherforge_actual_public$colorAndVertex(
            tessellator,
            this.colorRedTopLeft,
            this.colorGreenTopLeft,
            this.colorBlueTopLeft,
            x,
            y,
            z,
            u,
            v);
    }

    @Redirect(
        method = "renderBlockLiquid(Lnet/minecraft/block/Block;III)Z",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/renderer/Tessellator;addVertexWithUV(DDDDD)V",
            ordinal = 9))
    private void modifyRenderBlockLiquid18(Tessellator tessellator, double x, double y, double z, double u, double v) {
        mcpatcherforge_actual_public$colorAndVertex(
            tessellator,
            this.colorRedBottomLeft,
            this.colorGreenBottomLeft,
            this.colorBlueBottomLeft,
            x,
            y,
            z,
            u,
            v);
    }

    @Redirect(
        method = "renderBlockLiquid(Lnet/minecraft/block/Block;III)Z",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/renderer/Tessellator;addVertexWithUV(DDDDD)V",
            ordinal = 10))
    private void modifyRenderBlockLiquid19(Tessellator tessellator, double x, double y, double z, double u, double v) {
        mcpatcherforge_actual_public$colorAndVertex(
            tessellator,
            this.colorRedBottomRight,
            this.colorGreenBottomRight,
            this.colorBlueBottomRight,
            x,
            y,
            z,
            u,
            v);
    }

    @Redirect(
        method = "renderBlockLiquid(Lnet/minecraft/block/Block;III)Z",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/renderer/Tessellator;addVertexWithUV(DDDDD)V",
            ordinal = 11))
    private void modifyRenderBlockLiquid20(Tessellator tessellator, double x, double y, double z, double u, double v) {
        mcpatcherforge_actual_public$colorAndVertex(
            tessellator,
            this.colorRedTopRight,
            this.colorGreenTopRight,
            this.colorBlueTopRight,
            x,
            y,
            z,
            u,
            v);
    }

    @Redirect(
        method = "renderBlockLiquid(Lnet/minecraft/block/Block;III)Z",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/renderer/Tessellator;addVertexWithUV(DDDDD)V",
            ordinal = 12))
    private void modifyRenderBlockLiquid21(Tessellator tessellator, double x, double y, double z, double u, double v) {
        mcpatcherforge_actual_public$colorAndVertex(
            tessellator,
            this.colorRedTopRight,
            this.colorGreenTopRight,
            this.colorBlueTopRight,
            x,
            y,
            z,
            u,
            v);
    }

    @Redirect(
        method = "renderBlockLiquid(Lnet/minecraft/block/Block;III)Z",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/renderer/Tessellator;addVertexWithUV(DDDDD)V",
            ordinal = 13))
    private void modifyRenderBlockLiquid22(Tessellator tessellator, double x, double y, double z, double u, double v) {
        mcpatcherforge_actual_public$colorAndVertex(
            tessellator,
            this.colorRedBottomRight,
            this.colorGreenBottomRight,
            this.colorBlueBottomRight,
            x,
            y,
            z,
            u,
            v);
    }

    @Redirect(
        method = "renderBlockLiquid(Lnet/minecraft/block/Block;III)Z",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/renderer/Tessellator;addVertexWithUV(DDDDD)V",
            ordinal = 14))
    private void modifyRenderBlockLiquid23(Tessellator tessellator, double x, double y, double z, double u, double v) {
        mcpatcherforge_actual_public$colorAndVertex(
            tessellator,
            this.colorRedBottomLeft,
            this.colorGreenBottomLeft,
            this.colorBlueBottomLeft,
            x,
            y,
            z,
            u,
            v);
    }

    @Redirect(
        method = "renderBlockLiquid(Lnet/minecraft/block/Block;III)Z",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/renderer/Tessellator;addVertexWithUV(DDDDD)V",
            ordinal = 15))
    private void modifyRenderBlockLiquid24(Tessellator tessellator, double x, double y, double z, double u, double v) {
        mcpatcherforge_actual_public$colorAndVertex(
            tessellator,
            this.colorRedTopLeft,
            this.colorGreenTopLeft,
            this.colorBlueTopLeft,
            x,
            y,
            z,
            u,
            v);
    }
}
