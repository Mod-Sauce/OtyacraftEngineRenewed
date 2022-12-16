package dev.felnull.otyacraftengine.data;

import dev.architectury.platform.Mod;
import dev.felnull.otyacraftengine.data.provider.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiFunction;

public interface CrossDataGeneratorAccess {
    @NotNull DataGenerator getVanillaGenerator();

    <T extends DataProvider> T addProvider(@NotNull DataProvider.Factory<T> factory);

    <T extends DataProvider> T addProvider(@NotNull BiFunction<PackOutput, CompletableFuture<HolderLookup.Provider>, T> dataProviderSupplier);

    default <T extends DataProviderWrapper<?>> T addProviderWrapper(@NotNull DataProviderWrapper.GeneratorAccessedFactory<T> factory) {
        return addProviderWrapper(packOutput -> factory.create(packOutput, CrossDataGeneratorAccess.this));
    }

    default <T extends DataProviderWrapper<?>> T addProviderWrapper(@NotNull DataProviderWrapper.Factory<T> factory) {
        AtomicReference<T> providerWrapper = new AtomicReference<>();

        addProvider(packOutput -> {
            providerWrapper.set(factory.create(packOutput));
            return providerWrapper.get().getProvider();
        });

        return providerWrapper.get();
    }

    default <T extends DataProviderWrapper<?>> T addProviderWrapper(@NotNull DataProviderWrapper.LookupGeneratorAccessedFactory<T> factory) {
        return addProviderWrapper((DataProviderWrapper.LookupFactory<T>) (packOutput, lookup) -> factory.create(packOutput, lookup, CrossDataGeneratorAccess.this));
    }

    default <T extends DataProviderWrapper<?>> T addProviderWrapper(@NotNull DataProviderWrapper.LookupFactory<T> factory) {
        AtomicReference<T> providerWrapper = new AtomicReference<>();

        addProvider((packOutput, lookup) -> {
            providerWrapper.set(factory.create(packOutput, lookup));
            return providerWrapper.get().getProvider();
        });

        return providerWrapper.get();
    }

    Mod getMod();

    RecipeProvider createRecipeProvider(PackOutput packOutput, RecipeProviderWrapper recipeProviderWrapper);

    TagsProvider<Item> createItemTagProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookup, ItemTagProviderWrapper itemTagProviderWrapper, @NotNull BlockTagProviderWrapper blockTagProviderWrapper);

    TagsProvider<Block> createBlockTagProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookup, BlockTagProviderWrapper blockTagProviderWrapper);

    TagsProvider<PoiType> createPoiTypeTagProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookup, PoiTypeTagProviderWrapper poiTypeTagProviderWrapper);

    DataProvider createBasicProvider(BasicProviderWrapper basicProviderWrapper);

    DataProvider createBlockLootTableProvider(BlockLootTableProviderWrapper blockLootTableProviderWrapper);

    DataProvider createAdvancementProvider(PackOutput packOutput, AdvancementProviderWrapper advancementProviderWrapper, List<AdvancementSubProviderWrapper> subProviderWrappers);

    DataProvider createItemModelProvider(PackOutput packOutput, ItemModelProviderWrapper itemModelProviderWrapper);

    Collection<Path> getResourceInputFolders();

    void addResourceInputFolders(Path path);
}
