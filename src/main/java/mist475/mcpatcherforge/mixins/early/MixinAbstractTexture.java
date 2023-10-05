package mist475.mcpatcherforge.mixins.early;

import net.minecraft.client.renderer.texture.AbstractTexture;

import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import mist475.mcpatcherforge.mixins.interfaces.AbstractTextureExpansion;

@Mixin(AbstractTexture.class)
public abstract class MixinAbstractTexture implements AbstractTextureExpansion {

    @Shadow
    protected int glTextureId = -1;

    public void unloadGLTexture() {
        if (this.glTextureId >= 0) {
            GL11.glDeleteTextures(this.glTextureId);
            this.glTextureId = -1;
        }
    }

    public void finalize() {
        this.unloadGLTexture();
    }

}
