package mist475.mcpatcherforge.mixins.early;

import java.io.File;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.prupe.mcpatcher.Config;

@Mixin(GameSettings.class)
public abstract class MixinGameSettings {

    @Shadow
    private File optionsFile;

    @Shadow
    public abstract void loadOptions();

    @Inject(
        method = "<init>(Lnet/minecraft/client/Minecraft;Ljava/io/File;)V",
        at = @At(value = "RETURN", target = "Lnet/minecraft/client/settings/GameSettings;loadOptions()V"))
    private void modifyConstructor(Minecraft p_i1016_1_, File p_i1016_2_, CallbackInfo ci) {
        // Wasteful, can't inject into anything earlier unfortunately
        optionsFile = Config.getOptionsTxt(p_i1016_2_, "options.txt");
        loadOptions();
    }
}
