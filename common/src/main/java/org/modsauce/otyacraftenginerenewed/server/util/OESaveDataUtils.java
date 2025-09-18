package org.modsauce.otyacraftenginerenewed.server.util;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.modsauce.otyacraftenginerenewed.server.level.saveddata.OEBaseSavedData;

public class OESaveDataUtils {

  private static final ConcurrentHashMap<
    String,
    OEBaseSavedData
  > SAVED_DATA_CACHE = new ConcurrentHashMap<>();

  @NotNull
  public static <T extends OEBaseSavedData> T getSaveData(
    @NotNull ServerLevel level,
    @NotNull String name,
    @NotNull Supplier<T> createSupplier
  ) {
    Objects.requireNonNull(level);
    Objects.requireNonNull(name);
    Objects.requireNonNull(createSupplier);

    // Create a unique key for this level and name combination
    String cacheKey = level.dimension().location().toString() + ":" + name;

    // Use computeIfAbsent for thread-safe lazy initialization
    @SuppressWarnings("unchecked")
    T result = (T) SAVED_DATA_CACHE.computeIfAbsent(cacheKey, key -> {
      T newData = createSupplier.get();
      newData.setDirty(); // Mark as dirty to ensure it gets saved
      return newData;
    });

    return result;
  }

  @NotNull
  public static <T extends OEBaseSavedData> T getSaveData(
    @NotNull MinecraftServer server,
    @NotNull String name,
    @NotNull Supplier<T> createSupplier
  ) {
    return getSaveData(
      Objects.requireNonNull(server.getLevel(Level.OVERWORLD)),
      name,
      createSupplier
    );
  }

  /**
   * Clear cache when world unloads
   */
  public static void clearCache(@NotNull ServerLevel level) {
    String levelKey = level.dimension().location().toString();
    SAVED_DATA_CACHE.entrySet().removeIf(entry ->
      entry.getKey().startsWith(levelKey + ":")
    );
  }

  /**
   * Clear all cache
   */
  public static void clearAllCache() {
    SAVED_DATA_CACHE.clear();
  }
}
