package org.modsauce.otyacraftenginerenewed.server.level.saveddata;

import net.minecraft.resources.Identifier;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedDataType;

import java.util.function.Supplier;

import com.mojang.serialization.Codec;

public abstract class OEBaseSavedData extends SavedData {

    public static <T extends OEBaseSavedData> SavedDataType<T> createFactory(
            Identifier identifier,
            Supplier<T> constructor,
            Codec<T> codec,
            DataFixTypes dataFixTypes) {

        return new SavedDataType<T>(
            identifier,
            constructor,
            codec,
            dataFixTypes
        );
    }
}
