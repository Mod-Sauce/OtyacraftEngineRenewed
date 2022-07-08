package dev.felnull.otyacraftengine.server.util;

import dev.felnull.otyacraftengine.server.level.saveddata.OEBaseSavedData;
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

        return level.getDataStorage().computeIfAbsent(tag -> {
            var d = createSupplier.get();
            d.load(tag);
            return d;
        }, createSupplier, name);
    }

    @NotNull
    public static <T extends OEBaseSavedData> T getSaveData(@NotNull MinecraftServer server, @NotNull String name, @NotNull Supplier<T> createSupplier) {
        return getSaveData(Objects.requireNonNull(server.getLevel(Level.OVERWORLD)), name, createSupplier);
    }
}
