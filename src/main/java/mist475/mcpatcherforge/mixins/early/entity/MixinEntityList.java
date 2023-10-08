package mist475.mcpatcherforge.mixins.early.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.prupe.mcpatcher.cc.ColorizeItem;

@Mixin(EntityList.class)
public abstract class MixinEntityList {

    @Inject(method = "addMapping(Ljava/lang/Class;Ljava/lang/String;III)V", at = @At("HEAD"))
    private static void modifyAddMapping(Class<? extends Entity> p_75614_0_, String p_75614_1_, int p_75614_2_,
        int p_75614_3_, int p_75614_4_, CallbackInfo ci) {
        ColorizeItem.setupSpawnerEgg(p_75614_1_, p_75614_2_, p_75614_3_, p_75614_4_);
    }
}
