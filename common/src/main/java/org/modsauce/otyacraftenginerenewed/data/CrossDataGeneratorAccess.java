package org.modsauce.otyacraftenginerenewed.data;

import dev.architectury.platform.Mod;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiFunction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.registries.RegistriesDatapackGenerator;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;
import org.jetbrains.annotations.NotNull;
import org.modsauce.otyacraftenginerenewed.data.provider.*;

public interface CrossDataGeneratorAccess {
  @NotNull
  DataGenerator getVanillaGenerator();

  <T extends DataProvider> T addProvider(
    @NotNull DataProvider.Factory<T> factory
  );

  <T extends DataProvider> T addProvider(
    @NotNull BiFunction<
      PackOutput,
      CompletableFuture<HolderLookup.Provider>,
      T
    > dataProviderSupplier
  );

  default <T extends DataProviderWrapper<? extends DataProvider>> T addProviderWrapper(
    @NotNull DataProviderWrapper.GeneratorAccessedFactory<T> factory
  ) {
    DataProviderWrapper.Factory<T> wrapped =
      packOutput -> factory.create(packOutput, CrossDataGeneratorAccess.this);
    return addProviderWrapper(wrapped);
  }

  default <T extends DataProviderWrapper<? extends DataProvider>> T addProviderWrapper(
    @NotNull DataProviderWrapper.Factory<T> factory
  ) {
    AtomicReference<T> ref = new AtomicReference<>();

    DataProvider.Factory<DataProvider> providerFactory = packOutput -> {
      ref.set(factory.create(packOutput));          // one arg: Factory
      return ref.get().getProvider();
    };
    this.<DataProvider>addProvider(providerFactory);

    return ref.get();
  }

  default <T extends DataProviderWrapper<? extends DataProvider>> T addProviderWrapper(
    @NotNull DataProviderWrapper.LookupGeneratorAccessedFactory<T> factory
  ) {
    DataProviderWrapper.LookupFactory<T> wrapped =
      (packOutput, lookup) -> factory.create(packOutput, lookup, CrossDataGeneratorAccess.this);
    return addProviderWrapper(wrapped);
  }

  default <T extends DataProviderWrapper<? extends DataProvider>> T addProviderWrapper(
    @NotNull DataProviderWrapper.LookupFactory<T> factory
  ) {
    AtomicReference<T> ref = new AtomicReference<>();

    BiFunction<PackOutput, CompletableFuture<HolderLookup.Provider>, DataProvider> providerFactory =
      (packOutput, lookup) -> {
        ref.set(factory.create(packOutput, lookup)); // two args: LookupFactory
        return ref.get().getProvider();
      };
    this.<DataProvider>addProvider(providerFactory);

    return ref.get();
  }

  Mod getMod();

  // New 1.21.1 methods with registry lookup
  RecipeProvider createRecipeProvider(
    PackOutput packOutput,
    CompletableFuture<HolderLookup.Provider> lookup,
    RecipeProviderWrapper recipeProviderWrapper
  );

//  // Backward compatibility methods
//  default RecipeProvider createRecipeProvider(
//    PackOutput packOutput,
//    RecipeProviderWrapper recipeProviderWrapper
//  ) {
//    // For backward compatibility, use null lookup - implementations should handle this
//    return createRecipeProvider(packOutput, null, recipeProviderWrapper);
//  }

  // wdf

  TagsProvider<Item> createItemTagProvider(
    PackOutput packOutput,
    CompletableFuture<HolderLookup.Provider> lookup,
    ItemTagProviderWrapper itemTagProviderWrapper,
    @NotNull BlockTagProviderWrapper blockTagProviderWrapper
  );

  TagsProvider<Fluid> createFluidTagProvider(
    PackOutput packOutput,
    CompletableFuture<HolderLookup.Provider> lookup,
    FluidTagProviderWrapper fluidTagProviderWrapper
  );

  TagsProvider<Block> createBlockTagProvider(
    PackOutput packOutput,
    CompletableFuture<HolderLookup.Provider> lookup,
    BlockTagProviderWrapper blockTagProviderWrapper
  );

  TagsProvider<PoiType> createPoiTypeTagProvider(
    PackOutput packOutput,
    CompletableFuture<HolderLookup.Provider> lookup,
    PoiTypeTagProviderWrapper poiTypeTagProviderWrapper
  );

  TagsProvider<DamageType> createDamageTypeTagProvider(
    PackOutput packOutput,
    CompletableFuture<HolderLookup.Provider> lookup,
    DamageTypeTagsProviderWrapper damageTypeTagsProviderWrapper
  );

  TagsProvider<Biome> createBiomeTagProvider(
    PackOutput packOutput,
    CompletableFuture<HolderLookup.Provider> lookup,
    BiomeTagsProviderWrapper biomeTagsProviderWrapper
  );

  DataProvider createBasicProvider(BasicProviderWrapper basicProviderWrapper);

  DataProvider createBlockLootTableProvider(
    PackOutput packOutput,
    CompletableFuture<HolderLookup.Provider> lookup,
    BlockLootTableProviderWrapper blockLootTableProviderWrapper
  );

  DataProvider createAdvancementProvider(
    PackOutput packOutput,
    CompletableFuture<HolderLookup.Provider> lookup,
    AdvancementProviderWrapper advancementProviderWrapper,
    List<AdvancementSubProviderWrapper> subProviderWrappers
  );

  DataProvider createItemModelProvider(
    PackOutput packOutput,
    ItemModelProviderWrapper itemModelProviderWrapper
  );

  DataProvider createBlockStateAndModelProvider(
    PackOutput packOutput,
    BlockStateAndModelProviderWrapper blockStateAndModelProviderWrapper
  );

  RegistriesDatapackGenerator createRegistriesDatapackGenerator(
    PackOutput packOutput,
    CompletableFuture<HolderLookup.Provider> lookup,
    RegistrySetBuilder registrySetBuilder
  );

  Collection<Path> getResourceInputFolders();

  void addResourceInputFolders(Path path);
}
