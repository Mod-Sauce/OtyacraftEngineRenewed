package org.modsauce.otyacraftenginerenewed.server.level.saveddata;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.world.level.saveddata.SavedData;

import java.util.function.BiFunction;
import java.util.function.Supplier;

public abstract class OEBaseSavedData extends SavedData {
    public static <T extends OEBaseSavedData> SavedData.Factory<T> createFactory(
            Supplier<T> constructor,
            BiFunction<CompoundTag, HolderLookup.Provider, T> loader,
            DataFixTypes dataFixTypes) {
        return new SavedData.Factory<>(constructor, loader, dataFixTypes);
    }
}
