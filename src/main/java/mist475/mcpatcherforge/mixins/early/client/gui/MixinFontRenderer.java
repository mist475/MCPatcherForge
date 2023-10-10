package mist475.mcpatcherforge.mixins.early.client.gui;

import java.awt.image.BufferedImage;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import com.prupe.mcpatcher.cc.ColorizeWorld;
import com.prupe.mcpatcher.hd.FontUtils;

import mist475.mcpatcherforge.mixins.interfaces.FontRendererExpansion;

@Mixin(FontRenderer.class)
public abstract class MixinFontRenderer implements FontRendererExpansion {

    @Shadow
    @Final
    private static ResourceLocation[] unicodePageLocations;

    @Shadow
    protected int[] charWidth;
    @Mutable
    @Shadow
    @Final
    protected ResourceLocation locationFontTexture;

    @Shadow
    protected float posX;
    @Shadow
    protected float posY;
    @Shadow
    private boolean unicodeFlag;

    @Shadow
    protected abstract float renderUnicodeChar(char p_78277_1_, boolean p_78277_2_);

    @Shadow(remap = false)
    protected abstract void bindTexture(ResourceLocation location);

    @Shadow(remap = false)
    protected abstract void setColor(float r, float g, float b, float a);

    @Unique
    private float[] mcpatcher_forge$charWidthf;
    @Unique
    private ResourceLocation mcpatcher_forge$defaultFont;
    @Unique
    private ResourceLocation mcpatcher_forge$hdFont;
    @Unique
    private boolean mcpatcher_forge$isHD;
    @Unique
    private float mcpatcher_forge$fontAdj;

    /**
     * for custom text colors the index is required when drawing
     * unfortunately capturing locals doesn't work with modifyVariable
     */
    @Unique
    private int mcpatcher_forge$renderStringAtPosIndex;

    public float[] getCharWidthf() {
        return mcpatcher_forge$charWidthf;
    }

    public void setCharWidthf(float[] widthf) {
        mcpatcher_forge$charWidthf = widthf;
    }

    public ResourceLocation getDefaultFont() {
        return mcpatcher_forge$defaultFont;
    }

    public void setDefaultFont(ResourceLocation font) {
        mcpatcher_forge$defaultFont = font;
    }

    public ResourceLocation getHDFont() {
        return mcpatcher_forge$hdFont;
    }

    public void setHDFont(ResourceLocation font) {
        mcpatcher_forge$hdFont = font;
    }

    public boolean getIsHD() {
        return mcpatcher_forge$isHD;
    }

    public void setIsHD(boolean isHD) {
        this.mcpatcher_forge$isHD = isHD;
    }

    public float getFontAdj() {
        return mcpatcher_forge$fontAdj;
    }

    public void setFontAdj(float fontAdj) {
        this.mcpatcher_forge$fontAdj = fontAdj;
    }

    @Redirect(
        method = "readFontTexture()V",
        at = @At(
            value = "FIELD",
            target = "Lnet/minecraft/client/gui/FontRenderer;locationFontTexture:Lnet/minecraft/util/ResourceLocation;"))
    private ResourceLocation modifyReadFontTexture1(FontRenderer instance) {
        this.locationFontTexture = FontUtils.getFontName((FontRenderer) (Object) this, this.locationFontTexture, 0.0f);
        return this.locationFontTexture;
    }

    @SuppressWarnings("InvalidInjectorMethodSignature") // IDEA plugin struggles with local capture
    @Inject(method = "readFontTexture()V", at = @At(value = "RETURN"), locals = LocalCapture.CAPTURE_FAILHARD)
    private void modifyReadFontTexture2(CallbackInfo ci, BufferedImage bufferedimage, int i, int j, int[] aint) {
        setCharWidthf(
            FontUtils
                .computeCharWidthsf((FontRenderer) (Object) this, locationFontTexture, bufferedimage, aint, charWidth));
    }

    /**
     * @author Mist475 (adapted from Paul Rupe)
     * @reason I forgor
     *         TODO: look at again
     */
    @SuppressWarnings({ "UnnecessaryUnicodeEscape", "UnnecessaryStringEscape" })
    @Overwrite
    private float renderCharAtPos(int p_78278_1_, char p_78278_2_, boolean p_78278_3_) {
        return p_78278_2_ == ' ' ? getCharWidthf()[32]
            : ("\u00c0\u00c1\u00c2\u00c8\u00ca\u00cb\u00cd\u00d3\u00d4\u00d5\u00da\u00df\u00e3\u00f5\u011f\u0130\u0131\u0152\u0153\u015e\u015f\u0174\u0175\u017e\u0207\u0000\u0000\u0000\u0000\u0000\u0000\u0000 !\"#$%&\'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\u0000\u00c7\u00fc\u00e9\u00e2\u00e4\u00e0\u00e5\u00e7\u00ea\u00eb\u00e8\u00ef\u00ee\u00ec\u00c4\u00c5\u00c9\u00e6\u00c6\u00f4\u00f6\u00f2\u00fb\u00f9\u00ff\u00d6\u00dc\u00f8\u00a3\u00d8\u00d7\u0192\u00e1\u00ed\u00f3\u00fa\u00f1\u00d1\u00aa\u00ba\u00bf\u00ae\u00ac\u00bd\u00bc\u00a1\u00ab\u00bb\u2591\u2592\u2593\u2502\u2524\u2561\u2562\u2556\u2555\u2563\u2551\u2557\u255d\u255c\u255b\u2510\u2514\u2534\u252c\u251c\u2500\u253c\u255e\u255f\u255a\u2554\u2569\u2566\u2560\u2550\u256c\u2567\u2568\u2564\u2565\u2559\u2558\u2552\u2553\u256b\u256a\u2518\u250c\u2588\u2584\u258c\u2590\u2580\u03b1\u03b2\u0393\u03c0\u03a3\u03c3\u03bc\u03c4\u03a6\u0398\u03a9\u03b4\u221e\u2205\u2208\u2229\u2261\u00b1\u2265\u2264\u2320\u2321\u00f7\u2248\u00b0\u2219\u00b7\u221a\u207f\u00b2\u25a0\u0000"
                .indexOf(p_78278_2_) != -1 && !unicodeFlag ? renderDefaultChar(p_78278_1_, p_78278_3_)
                    : renderUnicodeChar(p_78278_2_, p_78278_3_));
    }

    /**
     * @author Mist475 (adapted from Paul Rupe)
     * @reason Many small changes vs overwrite
     */
    @Overwrite
    protected float renderDefaultChar(int p_78266_1_, boolean p_78266_2_) {
        float f = (float) (p_78266_1_ % 16 * 8);
        float f1 = (float) (p_78266_1_ / 16 * 8);
        float f2 = p_78266_2_ ? 1.0F : 0.0F;
        bindTexture(this.locationFontTexture);
        float f3 = (float) this.charWidth[p_78266_1_] - 0.01F;
        GL11.glBegin(GL11.GL_TRIANGLE_STRIP);
        GL11.glTexCoord2f(f / 128.0F, f1 / 128.0F);
        GL11.glVertex3f(this.posX + f2, this.posY, 0.0F);
        GL11.glTexCoord2f(f / 128.0F, (f1 + 7.99F) / 128.0F);
        GL11.glVertex3f(this.posX - f2, this.posY + 7.99F, 0.0F);
        GL11.glTexCoord2f((f + f3 - mcpatcher_forge$fontAdj) / 128.0F, f1 / 128.0F);
        GL11.glVertex3f(this.posX + f3 - mcpatcher_forge$fontAdj + f2, this.posY, 0.0F);
        GL11.glTexCoord2f((f + f3 - mcpatcher_forge$fontAdj) / 128.0F, (f1 + 7.99F) / 128.0F);
        GL11.glVertex3f(this.posX + f3 - mcpatcher_forge$fontAdj - f2, this.posY + 7.99F, 0.0F);
        GL11.glEnd();
        return FontUtils.getCharWidthf((FontRenderer) (Object) this, this.charWidth, p_78266_1_);
    }

    @Inject(
        method = "getUnicodePageLocation(I)Lnet/minecraft/util/ResourceLocation;",
        at = @At("RETURN"),
        cancellable = true)
    private void modifyGetUnicodePageLocation(int p_111271_1_, CallbackInfoReturnable<ResourceLocation> cir) {
        cir.setReturnValue(FontUtils.getUnicodePage(unicodePageLocations[p_111271_1_]));
    }

    @Inject(
        method = "renderStringAtPos(Ljava/lang/String;Z)V",
        locals = LocalCapture.CAPTURE_FAILHARD,
        at = @At(value = "FIELD", target = "Lnet/minecraft/client/gui/FontRenderer;colorCode:[I"))
    private void modifyRenderStringAtPos1(String string, boolean bool, CallbackInfo ci, int i, char c0, int j) {
        this.mcpatcher_forge$renderStringAtPosIndex = j;
    }

    // IDEA plugin really struggles with this for some reason
    @SuppressWarnings("InvalidInjectorMethodSignature")
    @ModifyVariable(
        method = "renderStringAtPos(Ljava/lang/String;Z)V",
        at = @At(value = "STORE", ordinal = 0),
        ordinal = 2)
    private int modifyRenderStringAtPos2(int color) {
        return ColorizeWorld.colorizeText(color, this.mcpatcher_forge$renderStringAtPosIndex);
    }

    @ModifyVariable(method = "renderString(Ljava/lang/String;IIIZ)I", at = @At("HEAD"), ordinal = 2, argsOnly = true)
    private int modifyRenderString(int colorizeText) {
        return ColorizeWorld.colorizeText(colorizeText);
    }

    @Inject(method = "getStringWidth(Ljava/lang/String;)I", at = @At("HEAD"), cancellable = true)
    private void modifyGetStringWidth(String p_78256_1_, CallbackInfoReturnable<Integer> cir) {
        if (getIsHD()) {
            cir.setReturnValue((int) FontUtils.getStringWidthf((FontRenderer) (Object) this, p_78256_1_));
        }
    }
}
