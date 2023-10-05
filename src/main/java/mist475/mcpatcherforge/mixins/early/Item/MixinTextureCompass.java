package mist475.mcpatcherforge.mixins.early.Item;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureCompass;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.prupe.mcpatcher.hd.FancyDial;

@Mixin(TextureCompass.class)
public abstract class MixinTextureCompass extends TextureAtlasSprite {

    @Shadow
    public double currentAngle;
    @Shadow
    public double angleDelta;

    public MixinTextureCompass(String p_i1282_1_) {
        super(p_i1282_1_);
    }

    @Inject(method = "<init>(Ljava/lang/String;)V", at = @At("RETURN"))
    private void modifyConstructor(String p_i1285_1_, CallbackInfo ci) {
        FancyDial.setup((TextureCompass) (Object) this);
    }

    /**
     * @author Mist475 (adapted from Paul Rupe)
     * @reason custom injector needed which is beyond my mixins skill atm
     */
    @SuppressWarnings("DuplicatedCode")
    @Overwrite
    public void updateCompass(World p_94241_1_, double p_94241_2_, double p_94241_4_, double p_94241_6_,
        boolean p_94241_8_, boolean itemFrameRenderer) {
        if (!this.framesTextureData.isEmpty()) {
            double d3 = 0.0D;

            if (p_94241_1_ != null && !p_94241_8_) {
                ChunkCoordinates chunkcoordinates = p_94241_1_.getSpawnPoint();
                double d4 = (double) chunkcoordinates.posX - p_94241_2_;
                double d5 = (double) chunkcoordinates.posZ - p_94241_4_;
                p_94241_6_ %= 360.0D;
                d3 = -((p_94241_6_ - 90.0D) * Math.PI / 180.0D - Math.atan2(d5, d4));

                if (!p_94241_1_.provider.isSurfaceWorld()) {
                    d3 = Math.random() * Math.PI * 2.0D;
                }
            }

            if (itemFrameRenderer) {
                this.currentAngle = d3;
            } else {
                double d6;

                for (d6 = d3 - this.currentAngle; d6 < -Math.PI; d6 += (Math.PI * 2D)) {}

                while (d6 >= Math.PI) {
                    d6 -= (Math.PI * 2D);
                }

                if (d6 < -1.0D) {
                    d6 = -1.0D;
                }

                if (d6 > 1.0D) {
                    d6 = 1.0D;
                }

                this.angleDelta += d6 * 0.1D;
                this.angleDelta *= 0.8D;
                this.currentAngle += this.angleDelta;
            }

            int i;
            // patch start
            if (FancyDial.update(this, itemFrameRenderer)) {
                return;
            }
            // patch end
            for (i = (int) ((this.currentAngle / (Math.PI * 2D) + 1.0D) * (double) this.framesTextureData.size())
                % this.framesTextureData.size(); i
                    < 0; i = (i + this.framesTextureData.size()) % this.framesTextureData.size()) {}

            if (i != this.frameCounter) {
                this.frameCounter = i;
                TextureUtil.uploadTextureMipmap(
                    this.framesTextureData.get(this.frameCounter),
                    this.width,
                    this.height,
                    this.originX,
                    this.originY,
                    false,
                    false);
            }
        }
    }
}
