package org.modsauce.otyacraftenginerenewed.server.util;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedDataType;

import org.jetbrains.annotations.NotNull;
import org.modsauce.otyacraftenginerenewed.server.level.saveddata.OEBaseSavedData;

import java.util.Objects;

public class OESaveDataUtils {
    @NotNull
    public static <T extends OEBaseSavedData> T getSaveData(@NotNull ServerLevel level, @NotNull String name, @NotNull SavedDataType<T> factory) {
        // 之前这里怎么写的代码？？？
        Objects.requireNonNull(level);
        Objects.requireNonNull(name);

        return level.getDataStorage().computeIfAbsent(
                factory
        );
    }

    @NotNull
    public static <T extends OEBaseSavedData> T getSaveData(@NotNull MinecraftServer server, @NotNull String name, @NotNull SavedDataType<T> factory) {
        return getSaveData(Objects.requireNonNull(server.getLevel(Level.OVERWORLD)), name, factory);
    }
}


// public class OESaveDataUtils {
// 	    @NotNull
// 	    public static <T extends OEBaseSavedData> T getSaveData(@NotNull ServerLevel level, @NotNull String name, @NotNull Supplier<T> createSupplier) {
// 	        Objects.requireNonNull(name);
// 	        Objects.requireNonNull(createSupplier);
//
// 	        return level.getDataStorage().computeIfAbsent(tag -> {
// 	            var d = createSupplier.get();
// 	            d.load(tag);
// 	            return d;
// 	        }, createSupplier, name);
// 	    }
//
// 	    @NotNull
// 	    public static <T extends OEBaseSavedData> T getSaveData(@NotNull MinecraftServer server, @NotNull String name, @NotNull Supplier<T> createSupplier) {
// 	        return getSaveData(Objects.requireNonNull(server.getLevel(Level.OVERWORLD)), name, createSupplier);
// 	    }
// 	}
