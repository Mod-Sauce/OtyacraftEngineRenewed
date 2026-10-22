package org.modsauce.otyacraftenginerenewed.fabric.data.provider;

import org.jspecify.annotations.NonNull;
import org.modsauce.otyacraftenginerenewed.data.provider.BasicProviderWrapper;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;

import java.util.concurrent.CompletableFuture;

public class WrappedFabricBasicProvider implements DataProvider {
    private final BasicProviderWrapper basicProviderWrapper;

    public WrappedFabricBasicProvider(BasicProviderWrapper basicProviderWrapper) {
        this.basicProviderWrapper = basicProviderWrapper;
    }

    @Override
    public @NonNull CompletableFuture<?> run(@NonNull CachedOutput cachedOutput) {
        return basicProviderWrapper.run(cachedOutput);
    }

    @Override
    public @NonNull String getName() {
        return basicProviderWrapper.getName();
    }
}
