package org.modsauce.otyacraftenginerenewed.item.location.factory;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import org.modsauce.otyacraftenginerenewed.item.location.PlayerItemLocation;
import org.modsauce.otyacraftenginerenewed.item.location.PlayerItemLocations;

public interface PlayerItemLocationFactory<T extends PlayerItemLocation> {
  T create(CompoundTag tag);

  default ResourceLocation getLocation() {
    return PlayerItemLocations.getResourceLocationByFactory(this);
  }
}
