package org.modsauce.otyacraftenginerenewed.fabric.data.provider;

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
    public CompletableFuture<?> run(CachedOutput cachedOutput) {
        return basicProviderWrapper.run(cachedOutput);
    }

    @Override
    public String getName() {
        return basicProviderWrapper.getName();
    }
}
