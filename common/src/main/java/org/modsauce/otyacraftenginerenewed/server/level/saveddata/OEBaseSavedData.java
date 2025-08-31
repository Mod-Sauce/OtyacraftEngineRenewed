package org.modsauce.otyacraftenginerenewed.server.level.saveddata;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.saveddata.SavedData;

public abstract class OEBaseSavedData extends SavedData {
  abstract public void load(CompoundTag tag);
}
