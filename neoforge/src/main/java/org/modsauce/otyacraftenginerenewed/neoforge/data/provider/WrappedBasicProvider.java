package org.modsauce.otyacraftenginerenewed.neoforge.data.provider;

import org.modsauce.otyacraftenginerenewed.data.provider.BasicProviderWrapper;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;

import java.util.concurrent.CompletableFuture;

public class WrappedBasicProvider implements DataProvider {
    private final BasicProviderWrapper basicProviderWrapper;

    public WrappedBasicProvider(BasicProviderWrapper basicProviderWrapper) {
        this.basicProviderWrapper = basicProviderWrapper;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput arg) {
        return basicProviderWrapper.run(arg);
    }

    @Override
    public String getName() {
        return basicProviderWrapper.getName();
    }
}
