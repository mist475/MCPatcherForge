package com.prupe.mcpatcher.mal.resource;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GLContext;

public class GLAPI {

    private static final GLAPI instance = new GLAPI();
    private static final boolean useGlBlendFuncSeparate = GLContext.getCapabilities().OpenGL14;

    public static void glBindTexture(int texture) {
        if (texture >= 0) {
            instance.glBindTexture_Impl(texture);
        }
    }

    public static void glBlendFunc(int src, int dst) {
        glBlendFuncSeparate(src, dst, GL11.GL_ONE, GL11.GL_ZERO);
    }

    public static void glBlendFuncSeparate(int src, int dst, int srcAlpha, int dstAlpha) {
        instance.glBlendFunc_Impl(src, dst, srcAlpha, dstAlpha);
    }

    public static void glAlphaFunc(int func, float ref) {
        instance.glAlphaFunc_Impl(func, ref);
    }

    public static void glDepthFunc(int func) {
        instance.glDepthFunc_Impl(func);
    }

    public static void glDepthMask(boolean enable) {
        instance.glDepthMask_Impl(enable);
    }

    public static void glColor4f(float r, float g, float b, float a) {
        instance.glColor4f_Impl(r, g, b, a);
    }

    public static void glClearColor(float r, float g, float b, float a) {
        instance.glClearColor_Impl(r, g, b, a);
    }

    public static int getBoundTexture() {
        return GL11.glGetInteger(GL11.GL_TEXTURE_BINDING_2D);
    }

    public static void deleteTexture(int texture) {
        if (texture >= 0) {
            GL11.glDeleteTextures(texture);
        }
    }

    protected void glBindTexture_Impl(int texture) {
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture);
    }

    protected void glBlendFunc_Impl(int src, int dst, int srcAlpha, int dstAlpha) {
        if (useGlBlendFuncSeparate) {
            GL14.glBlendFuncSeparate(src, dst, srcAlpha, dstAlpha);
        } else {
            GL11.glBlendFunc(src, dst);
        }
    }

    protected void glAlphaFunc_Impl(int func, float ref) {
        GL11.glAlphaFunc(func, ref);
    }

    protected void glDepthFunc_Impl(int func) {
        GL11.glDepthFunc(func);
    }

    protected void glDepthMask_Impl(boolean enable) {
        GL11.glDepthMask(enable);
    }

    protected void glColor4f_Impl(float r, float g, float b, float a) {
        GL11.glColor4f(r, g, b, a);
    }

    protected void glClearColor_Impl(float r, float g, float b, float a) {
        GL11.glClearColor(r, g, b, a);
    }
}
