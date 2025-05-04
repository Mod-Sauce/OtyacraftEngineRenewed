package dev.felnull.otyacraftengine.forge.data.provider;

import dev.felnull.otyacraftengine.data.provider.AdvancementProviderWrapper;
import dev.felnull.otyacraftengine.data.provider.AdvancementSubProviderWrapper;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.advancements.AdvancementProvider;
import net.minecraft.data.advancements.AdvancementSubProvider;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class WrappedAdvancementProvider extends AdvancementProvider {
    private final AdvancementProviderWrapper advancementProviderWrapper;

    public WrappedAdvancementProvider(PackOutput arg, CompletableFuture<HolderLookup.Provider> completableFuture, List<AdvancementSubProviderWrapper> subProviderWrappers, AdvancementProviderWrapper advancementProviderWrapper) {
        super(arg, completableFuture, subProviderWrappers.stream().map(n -> (AdvancementSubProvider) (r, consumer) -> n.generate(consumer)).toList());
        this.advancementProviderWrapper = advancementProviderWrapper;
    }
}
