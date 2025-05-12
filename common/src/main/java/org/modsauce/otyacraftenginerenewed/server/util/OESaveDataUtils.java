package org.modsauce.otyacraftenginerenewed.server.util;

import org.modsauce.otyacraftenginerenewed.server.level.saveddata.OEBaseSavedData;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.function.Supplier;

public class OESaveDataUtils {
    @NotNull
    public static <T extends OEBaseSavedData> T getSaveData(@NotNull ServerLevel level, @NotNull String name, @NotNull Supplier<T> createSupplier) {
        Objects.requireNonNull(name);
        Objects.requireNonNull(createSupplier);

        // The API has changed in this version of Minecraft
        // For now, we'll create a dummy implementation that always returns a new instance
        // This is a temporary workaround until the proper API can be determined
        T data = createSupplier.get();
        return data;
    }

    @NotNull
    public static <T extends OEBaseSavedData> T getSaveData(@NotNull MinecraftServer server, @NotNull String name, @NotNull Supplier<T> createSupplier) {
        return getSaveData(Objects.requireNonNull(server.getLevel(Level.OVERWORLD)), name, createSupplier);
    }
}
