package dev.felnull.otyacraftengine.forge.data;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import dev.architectury.platform.Mod;
import dev.architectury.platform.Platform;
import dev.felnull.otyacraftengine.data.CrossDataGeneratorAccess;
import dev.felnull.otyacraftengine.data.provider.*;
import dev.felnull.otyacraftengine.forge.data.provider.*;
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
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.data.event.GatherDataEvent;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiFunction;

@ApiStatus.Internal
public class CrossDataGeneratorAccessImpl implements CrossDataGeneratorAccess {
    private final List<Path> resourceInputFolders = new ArrayList<>();
    private final GatherDataEvent gatherDataEvent;

    protected CrossDataGeneratorAccessImpl(GatherDataEvent gatherDataEvent) {
        this.gatherDataEvent = gatherDataEvent;
    }

    @Override
    public @NotNull DataGenerator getVanillaGenerator() {
        return gatherDataEvent.getGenerator();
    }

    @Override
    public <T extends DataProvider> T addProvider(DataProvider.@NotNull Factory<T> factory) {
        return getVanillaGenerator().addProvider(true, factory);
    }

    @Override
    public <T extends DataProvider> T addProvider(@NotNull BiFunction<PackOutput, CompletableFuture<HolderLookup.Provider>, T> dataProviderSupplier) {
        var lookup = gatherDataEvent.getLookupProvider();
        return getVanillaGenerator().addProvider(true, (DataProvider.Factory<T>) arg -> dataProviderSupplier.apply(arg, lookup));
    }

    @Override
    public Mod getMod() {
        return Platform.getMod(gatherDataEvent.getModContainer().getModId());
    }

    @Override
    public RecipeProvider createRecipeProvider(PackOutput packOutput, RecipeProviderWrapper recipeProviderWrapper) {
        return new WrappedRecipeProvider(packOutput, recipeProviderWrapper);
    }

    @Override
    public TagsProvider<Item> createItemTagProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookup, ItemTagProviderWrapper itemTagProviderWrapper, @NotNull BlockTagProviderWrapper blockTagProviderWrapper) {
        if (!(blockTagProviderWrapper.getProvider() instanceof BlockTagsProvider blockTagsProvider))
            throw new IllegalArgumentException("Not BlockTagsProvider");


        return new WrappedItemTagsProvider(packOutput, lookup, blockTagsProvider.contentsGetter(), gatherDataEvent.getModContainer().getModId(), gatherDataEvent.getExistingFileHelper(), itemTagProviderWrapper);
    }

    @Override
    public TagsProvider<Fluid> createFluidTagProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookup, FluidTagProviderWrapper fluidTagProviderWrapper) {
        return new WrappedFluidTagsProvider(packOutput, lookup, fluidTagProviderWrapper, gatherDataEvent.getModContainer().getModId(), gatherDataEvent.getExistingFileHelper());
    }

    @Override
    public TagsProvider<Block> createBlockTagProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookup, BlockTagProviderWrapper blockTagProviderWrapper) {
        return new WrappedBlockTagsProvider(packOutput, lookup, gatherDataEvent.getModContainer().getModId(), gatherDataEvent.getExistingFileHelper(), blockTagProviderWrapper);
    }

    @Override
    public TagsProvider<PoiType> createPoiTypeTagProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookup, PoiTypeTagProviderWrapper poiTypeTagProviderWrapper) {
        return new WrappedPoiTypeTagsProvider(packOutput, lookup, gatherDataEvent.getModContainer().getModId(), gatherDataEvent.getExistingFileHelper(), poiTypeTagProviderWrapper);
    }

    @Override
    public TagsProvider<DamageType> createDamageTypeTagProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookup, DamageTypeTagsProviderWrapper damageTypeTagsProviderWrapper) {
        return new WrappedDamageTypeTagsProvider(packOutput, lookup, gatherDataEvent.getModContainer().getModId(), gatherDataEvent.getExistingFileHelper(), damageTypeTagsProviderWrapper);
    }

    @Override
    public TagsProvider<Biome> createBiomeTagProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookup, BiomeTagsProviderWrapper biomeTagsProviderWrapper) {
        return new WrappedBiomeTagsProvider(packOutput, lookup, gatherDataEvent.getModContainer().getModId(), gatherDataEvent.getExistingFileHelper(), biomeTagsProviderWrapper);
    }

    @Override
    public DataProvider createBasicProvider(BasicProviderWrapper basicProviderWrapper) {
        return new WrappedBasicProvider(basicProviderWrapper);
    }

    @Override
    public DataProvider createBlockLootTableProvider(PackOutput packOutput, BlockLootTableProviderWrapper blockLootTableProviderWrapper) {
        return new WrappedBlockLootTableProvider(packOutput, blockLootTableProviderWrapper);
    }

    @Override
    public DataProvider createAdvancementProvider(PackOutput packOutput, AdvancementProviderWrapper advancementProviderWrapper, List<AdvancementSubProviderWrapper> subProviderWrappers) {
        return new WrappedAdvancementProvider(getVanillaGenerator().getPackOutput(), gatherDataEvent.getLookupProvider(), subProviderWrappers, advancementProviderWrapper);
    }

    @Override
    public DataProvider createItemModelProvider(PackOutput packOutput, ItemModelProviderWrapper itemModelProviderWrapper) {
        return new WrappedItemModelProvider(packOutput, gatherDataEvent.getModContainer().getModId(), gatherDataEvent.getExistingFileHelper(), itemModelProviderWrapper);
    }

    @Override
    public DataProvider createBlockStateAndModelProvider(PackOutput packOutput, BlockStateAndModelProviderWrapper blockStateAndModelProviderWrapper) {
        return new WrappedBlockStateProvider(packOutput, gatherDataEvent.getModContainer().getModId(), gatherDataEvent.getExistingFileHelper(), blockStateAndModelProviderWrapper);
    }

    @Override
    public RegistriesDatapackGenerator createRegistriesDatapackGenerator(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookup, RegistrySetBuilder registrySetBuilder) {
        return new DatapackBuiltinEntriesProvider(packOutput, lookup, registrySetBuilder, ImmutableSet.of(gatherDataEvent.getModContainer().getModId()));
    }

    @Override
    public Collection<Path> getResourceInputFolders() {
        return ImmutableList.copyOf(resourceInputFolders);
    }

    @Override
    public void addResourceInputFolders(Path path) {
        resourceInputFolders.add(path);
    }
}
