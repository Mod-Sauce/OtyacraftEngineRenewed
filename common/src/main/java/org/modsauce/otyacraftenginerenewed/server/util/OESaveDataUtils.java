package org.modsauce.otyacraftenginerenewed.server.util;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import org.jetbrains.annotations.NotNull;
import org.modsauce.otyacraftenginerenewed.server.level.saveddata.OEBaseSavedData;

import java.util.Objects;

public class OESaveDataUtils {
    @NotNull
    public static <T extends OEBaseSavedData> T getSaveData(@NotNull ServerLevel level, @NotNull String name, @NotNull SavedData.Factory<T> factory) {
        // 之前这里怎么写的代码？？？
        Objects.requireNonNull(level);
        Objects.requireNonNull(name);

        return level.getDataStorage().computeIfAbsent(
                factory,
                name
        );
    }

    @NotNull
    public static <T extends OEBaseSavedData> T getSaveData(@NotNull MinecraftServer server, @NotNull String name, @NotNull SavedData.Factory<T> factory) {
        return getSaveData(Objects.requireNonNull(server.getLevel(Level.OVERWORLD)), name, factory);
    }
}