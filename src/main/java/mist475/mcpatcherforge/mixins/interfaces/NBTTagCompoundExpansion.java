package mist475.mcpatcherforge.mixins.interfaces;

import java.util.Collection;

import net.minecraft.nbt.NBTBase;

public interface NBTTagCompoundExpansion {

    Collection<NBTBase> getTags();
}
