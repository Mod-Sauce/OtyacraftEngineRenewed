package org.modsauce.otyacraftenginerenewed.item.location.factory;

import org.modsauce.otyacraftenginerenewed.item.location.PlayerItemLocation;
import org.modsauce.otyacraftenginerenewed.item.location.PlayerItemLocations;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;

public interface PlayerItemLocationFactory<T extends PlayerItemLocation> {
    T create(CompoundTag tag);

    default ResourceLocation getLocation() {
        return PlayerItemLocations.getResourceLocationByFactory(this);
    }
}
