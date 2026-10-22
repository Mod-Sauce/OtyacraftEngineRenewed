package org.modsauce.otyacraftenginerenewed.fabric.data.provider;

import org.modsauce.otyacraftenginerenewed.data.provider.FluidTagProviderWrapper;
import org.modsauce.otyacraftenginerenewed.data.provider.IntrinsicHolderTagsProviderWrapper;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.material.Fluid;

import java.util.concurrent.CompletableFuture;

public class WrappedFabricFluidTagProvider extends FabricTagsProvider.FluidTagsProvider {
    private final FluidTagProviderWrapper fluidTagProviderWrapper;

    public WrappedFabricFluidTagProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> completableFuture, FluidTagProviderWrapper fluidTagProviderWrapper) {
        super(output, completableFuture);
        this.fluidTagProviderWrapper = fluidTagProviderWrapper;
    }

    @Override
    protected void addTags(HolderLookup.Provider arg) {
        this.fluidTagProviderWrapper.generateTag(new FluidTagProviderAccessImpl());
    }

    private class FluidTagProviderAccessImpl implements IntrinsicHolderTagsProviderWrapper.IntrinsicTagProviderAccess<Fluid> {
        @Override
        public IntrinsicHolderTagsProviderWrapper.IntrinsicTagAppenderWrapper<Fluid> tag(TagKey<Fluid> tagKey) {
            return new WrappedFabricIntrinsicHolderTagsProvider.IntrinsicHolderTagAppenderWrapperImpl<>(WrappedFabricFluidTagProvider.this.tag(tagKey), fluidTagProviderWrapper);
        }
    }
}
