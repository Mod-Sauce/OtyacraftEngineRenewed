package org.modsauce.otyacraftenginerenewed.item.location.factory;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.Identifier;
import org.modsauce.otyacraftenginerenewed.item.location.PlayerItemLocation;
import org.modsauce.otyacraftenginerenewed.item.location.PlayerItemLocations;

public interface PlayerItemLocationFactory<T extends PlayerItemLocation> {
  T create(CompoundTag tag);

  default Identifier getLocation() {
    return PlayerItemLocations.getIdentifierByFactory(this);
  }
}
