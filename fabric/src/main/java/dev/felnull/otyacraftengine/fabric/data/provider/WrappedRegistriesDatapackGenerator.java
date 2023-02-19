package dev.felnull.otyacraftengine.fabric.data.provider;

import dev.felnull.otyacraftengine.fabric.mixin.data.RegistrySetBuilderAccessor;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.registries.RegistriesDatapackGenerator;
import net.minecraft.resources.RegistryDataLoader;

import java.util.HashSet;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

//ForgeのDatapackBuiltinEntriesProviderのパクり
public class WrappedRegistriesDatapackGenerator extends RegistriesDatapackGenerator {
    public WrappedRegistriesDatapackGenerator(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> completableFuture, RegistrySetBuilder registrySetBuilder) {
        super(packOutput, completableFuture.thenApply(r -> constructRegistries(r, registrySetBuilder)));
    }

    private static HolderLookup.Provider constructRegistries(HolderLookup.Provider original, RegistrySetBuilder datapackEntriesBuilder) {
        var builderKeys = new HashSet<>(((RegistrySetBuilderAccessor) datapackEntriesBuilder).getEntries().stream().map(RegistrySetBuilder.RegistryStub::key).toList());
        getUnitedDataPackRegistries().filter(data -> !builderKeys.contains(data.key())).forEach(data -> datapackEntriesBuilder.add(data.key(), context -> {
        }));

        return datapackEntriesBuilder.buildPatch(RegistryAccess.fromRegistryOfRegistries(BuiltInRegistries.REGISTRY), original);
    }

    public static Stream<RegistryDataLoader.RegistryData<?>> getUnitedDataPackRegistries() {
        return Stream.concat(RegistryDataLoader.WORLDGEN_REGISTRIES.stream(), RegistryDataLoader.DIMENSION_REGISTRIES.stream());
    }
}
