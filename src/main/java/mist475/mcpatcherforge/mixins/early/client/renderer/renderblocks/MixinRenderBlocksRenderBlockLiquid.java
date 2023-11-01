package mist475.mcpatcherforge.mixins.early.client.renderer.renderblocks;

import net.minecraft.block.Block;
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

import com.prupe.mcpatcher.cc.ColorizeBlock;

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
    private int mcpatcherforge$neededSideRenderBlockLiquid;

    @Unique
    private float mcpatcherforge$neededFloat1;

    @Unique
    private float mcpatcherforge$neededFloat2;

    @Unique
    private float mcpatcherforge$neededFloat3;

    @Unique
    private void mcpatcherforge$setColorAndVertex(Tessellator tessellator, float red, float green, float blue, double x,
        double y, double z, double u, double v) {
        if (ColorizeBlock.isSmooth) {
            tessellator.setColorOpaque_F(red, green, blue);
        }
        tessellator.addVertexWithUV(x, y, z, u, v);
    }

    // Redirect calls to this.getBlockIcon when possible

    @Redirect(
        method = "renderBlockLiquid(Lnet/minecraft/block/Block;III)Z",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/renderer/RenderBlocks;getBlockIconFromSideAndMetadata(Lnet/minecraft/block/Block;II)Lnet/minecraft/util/IIcon;",
            ordinal = 0))
    private IIcon mcpatcherforge$obtainFloatsAndRedirectToGetBlockIcon(RenderBlocks instance, Block block, int side,
        int meta, Block specializedBlock, int x, int y, int z) {
        int l = block.colorMultiplier(this.blockAccess, x, y, z);
        this.mcpatcherforge$neededFloat1 = (float) (l >> 16 & 255) / 255.0F;
        this.mcpatcherforge$neededFloat2 = (float) (l >> 8 & 255) / 255.0F;
        this.mcpatcherforge$neededFloat3 = (float) (l & 255) / 255.0F;
        return (this.blockAccess == null) ? this.getBlockIconFromSideAndMetadata(block, side, meta)
            : this.getBlockIcon(block, this.blockAccess, x, y, z, side);
    }

    @Redirect(
        method = "renderBlockLiquid(Lnet/minecraft/block/Block;III)Z",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/renderer/RenderBlocks;getBlockIconFromSideAndMetadata(Lnet/minecraft/block/Block;II)Lnet/minecraft/util/IIcon;",
            ordinal = 1))
    private IIcon mcpatcherforge$redirectToGetBlockIcon(RenderBlocks instance, Block block, int side, int meta,
        Block specializedBlock, int x, int y, int z) {
        return (this.blockAccess == null) ? this.getBlockIconFromSideAndMetadata(block, side, meta)
            : this.getBlockIcon(block, this.blockAccess, x, y, z, side);
    }

    // Capture needed value
    @Redirect(
        method = "renderBlockLiquid(Lnet/minecraft/block/Block;III)Z",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/renderer/RenderBlocks;getBlockIconFromSideAndMetadata(Lnet/minecraft/block/Block;II)Lnet/minecraft/util/IIcon;",
            ordinal = 2))
    private IIcon mcpatcherforge$saveSideAndRedirectToGetBlockIcon(RenderBlocks instance, Block block, int side,
        int meta, Block specializedBlock, int x, int y, int z) {
        this.mcpatcherforge$neededSideRenderBlockLiquid = side;
        return (this.blockAccess == null) ? this.getBlockIconFromSideAndMetadata(block, side, meta)
            : this.getBlockIcon(block, this.blockAccess, x, y, z, side);
    }

    @Redirect(
        method = "renderBlockLiquid(Lnet/minecraft/block/Block;III)Z",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/renderer/RenderBlocks;getBlockIconFromSide(Lnet/minecraft/block/Block;I)Lnet/minecraft/util/IIcon;"))
    private IIcon mcpatcherforge$redirectToGetBlockIcon(RenderBlocks instance, Block block, int side,
        Block specializedBlock, int x, int y, int z) {
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
    private void mcpatcherforge$handleSmoothing(Tessellator tessellator, float red, float green, float blue,
        Block block, int x, int y, int z) {
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
    private void mcpatcherforge$redirectColor1(Tessellator tessellator, double x, double y, double z, double u,
        double v) {
        mcpatcherforge$setColorAndVertex(
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
    private void mcpatcherforge$redirectColor2(Tessellator tessellator, double x, double y, double z, double u,
        double v) {
        mcpatcherforge$setColorAndVertex(
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
    private void mcpatcherforge$redirectColor3(Tessellator tessellator, double x, double y, double z, double u,
        double v) {
        mcpatcherforge$setColorAndVertex(
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
    private void mcpatcherforge$redirectColor4(Tessellator tessellator, double x, double y, double z, double u,
        double v) {
        mcpatcherforge$setColorAndVertex(
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
    private void mcpatcherforge$redirectColor5(Tessellator tessellator, double x, double y, double z, double u,
        double v) {
        mcpatcherforge$setColorAndVertex(
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
    private void mcpatcherforge$redirectColor6(Tessellator tessellator, double x, double y, double z, double u,
        double v) {
        mcpatcherforge$setColorAndVertex(
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
    private void mcpatcherforge$redirectColor7(Tessellator tessellator, double x, double y, double z, double u,
        double v) {
        mcpatcherforge$setColorAndVertex(
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
    private void mcpatcherforge$redirectColor8(Tessellator tessellator, double x, double y, double z, double u,
        double v) {
        mcpatcherforge$setColorAndVertex(
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
    private void mcpatcherforge$redirectColor9(Tessellator tessellator, float red, float green, float blue, Block block,
        int x, int y, int z) {
        if (!(ColorizeBlock.isSmooth = ColorizeBlock
            .setupBlockSmoothing((RenderBlocks) (Object) this, block, this.blockAccess, x, y, z, 6))) {
            tessellator.setColorOpaque_F(
                red * this.mcpatcherforge$neededFloat1,
                green * this.mcpatcherforge$neededFloat2,
                blue * this.mcpatcherforge$neededFloat3);
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
    private void mcpatcherforge$setEnableAO(Block block, int x, int y, int z, CallbackInfoReturnable<Boolean> cir) {
        this.enableAO = false;
    }

    @Redirect(
        method = "renderBlockLiquid(Lnet/minecraft/block/Block;III)Z",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/renderer/Tessellator;setColorOpaque_F(FFF)V",
            ordinal = 2))
    private void mcpatcherforge$redirectColor10(Tessellator tessellator, float red, float green, float blue,
        Block block, int x, int y, int z) {
        if (!(ColorizeBlock.isSmooth = ColorizeBlock.setupBlockSmoothing(
            (RenderBlocks) (Object) this,
            block,
            this.blockAccess,
            x,
            y,
            z,
            this.mcpatcherforge$neededSideRenderBlockLiquid + 6))) {
            tessellator.setColorOpaque_F(red, green, blue);
        }
    }

    @Redirect(
        method = "renderBlockLiquid(Lnet/minecraft/block/Block;III)Z",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/renderer/Tessellator;addVertexWithUV(DDDDD)V",
            ordinal = 8))
    private void mcpatcherforge$redirectColor11(Tessellator tessellator, double x, double y, double z, double u,
        double v) {
        mcpatcherforge$setColorAndVertex(
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
    private void mcpatcherforge$redirectColor12(Tessellator tessellator, double x, double y, double z, double u,
        double v) {
        mcpatcherforge$setColorAndVertex(
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
    private void mcpatcherforge$redirectColor13(Tessellator tessellator, double x, double y, double z, double u,
        double v) {
        mcpatcherforge$setColorAndVertex(
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
    private void mcpatcherforge$redirectColor14(Tessellator tessellator, double x, double y, double z, double u,
        double v) {
        mcpatcherforge$setColorAndVertex(
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
    private void mcpatcherforge$redirectColor15(Tessellator tessellator, double x, double y, double z, double u,
        double v) {
        mcpatcherforge$setColorAndVertex(
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
    private void mcpatcherforge$redirectColor16(Tessellator tessellator, double x, double y, double z, double u,
        double v) {
        mcpatcherforge$setColorAndVertex(
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
    private void mcpatcherforge$redirectColor17(Tessellator tessellator, double x, double y, double z, double u,
        double v) {
        mcpatcherforge$setColorAndVertex(
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
    private void mcpatcherforge$redirectColor18(Tessellator tessellator, double x, double y, double z, double u,
        double v) {
        mcpatcherforge$setColorAndVertex(
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
