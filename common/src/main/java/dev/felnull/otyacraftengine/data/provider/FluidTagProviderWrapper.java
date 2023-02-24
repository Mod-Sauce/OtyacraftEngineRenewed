package dev.felnull.otyacraftengine.data.provider;

import dev.felnull.otyacraftengine.data.CrossDataGeneratorAccess;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.material.Fluid;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

public abstract class FluidTagProviderWrapper extends IntrinsicHolderTagsProviderWrapper<Fluid, IntrinsicHolderTagsProviderWrapper.IntrinsicTagProviderAccess<Fluid>> {
    private final TagsProvider<Fluid> fluidTagsProvider;

    public FluidTagProviderWrapper(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookup, CrossDataGeneratorAccess crossDataGeneratorAccess) {
        super(packOutput, lookup, crossDataGeneratorAccess);
        this.fluidTagsProvider = crossDataGeneratorAccess.createFluidTagProvider(packOutput, lookup, this);
    }

    @Override
    public TagsProvider<Fluid> getProvider() {
        return fluidTagsProvider;
    }

    @Override
    public Function<Fluid, ResourceKey<Fluid>> getKeyExtractor() {
        return (fluid) -> fluid.builtInRegistryHolder().key();
    }
}
