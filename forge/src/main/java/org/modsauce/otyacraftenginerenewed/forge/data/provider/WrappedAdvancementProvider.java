package org.modsauce.otyacraftenginerenewed.forge.data.provider;

import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.data.PackOutput;
import net.minecraft.data.advancements.AdvancementProvider;
import net.minecraft.data.advancements.AdvancementSubProvider;
import net.minecraft.core.HolderLookup;
import org.modsauce.otyacraftenginerenewed.data.provider.AdvancementProviderWrapper;
import org.modsauce.otyacraftenginerenewed.data.provider.AdvancementSubProviderWrapper;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class WrappedAdvancementProvider extends AdvancementProvider {
    private final AdvancementProviderWrapper advancementProviderWrapper;

    public WrappedAdvancementProvider(PackOutput arg, CompletableFuture<HolderLookup.Provider> completableFuture, List<AdvancementSubProviderWrapper> subProviderWrappers, AdvancementProviderWrapper advancementProviderWrapper) {
        super(arg, completableFuture, 
            // Create a list of AdvancementSubProvider that adapts to the new API
            subProviderWrappers.stream().map(wrapper -> 
                (AdvancementSubProvider) (registries, consumer) -> 
                    wrapper.generateAdvancementHolder(consumer)
            ).toList());
        this.advancementProviderWrapper = advancementProviderWrapper;
    }
}