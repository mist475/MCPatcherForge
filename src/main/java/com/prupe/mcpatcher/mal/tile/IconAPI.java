package com.prupe.mcpatcher.mal.tile;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.IIcon;

public class IconAPI {

    private static final IconAPI instance = new IconAPI();

    public static boolean needRegisterTileAnimations() {
        return instance.needRegisterTileAnimations_Impl();
    }

    public static int getIconX0(TextureAtlasSprite icon) {
        return instance.getIconX0_Impl(icon);
    }

    public static int getIconY0(TextureAtlasSprite icon) {
        return instance.getIconY0_Impl(icon);
    }

    public static int getIconWidth(IIcon icon) {
        return instance.getIconWidth_Impl((TextureAtlasSprite) icon);
    }

    public static int getIconHeight(IIcon icon) {
        return instance.getIconHeight_Impl((TextureAtlasSprite) icon);
    }

    public static String getIconName(IIcon icon) {
        return instance.getIconName_Impl(icon);
    }

    protected boolean needRegisterTileAnimations_Impl() {
        return true;
    }

    protected int getIconX0_Impl(TextureAtlasSprite icon) {
        return icon.getOriginX();
    }

    protected int getIconY0_Impl(TextureAtlasSprite icon) {
        return icon.getOriginY();
    }

    protected int getIconWidth_Impl(TextureAtlasSprite icon) {
        return icon.getIconWidth();
    }

    protected int getIconHeight_Impl(TextureAtlasSprite icon) {
        return icon.getIconHeight();
    }

    protected String getIconName_Impl(IIcon icon) {
        return icon.getIconName();
    }
}
