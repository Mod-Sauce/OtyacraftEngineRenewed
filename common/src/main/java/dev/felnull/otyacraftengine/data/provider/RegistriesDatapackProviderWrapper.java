package dev.felnull.otyacraftengine.data.provider;

import dev.felnull.otyacraftengine.data.CrossDataGeneratorAccess;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.data.PackOutput;
import net.minecraft.data.registries.RegistriesDatapackGenerator;

import java.util.concurrent.CompletableFuture;

public class RegistriesDatapackProviderWrapper extends DataProviderWrapper<RegistriesDatapackGenerator> {
    private final RegistriesDatapackGenerator registriesDatapackGenerator;

    public RegistriesDatapackProviderWrapper(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookup, RegistrySetBuilder registrySetBuilder, CrossDataGeneratorAccess crossDataGeneratorAccess) {
        super(packOutput, crossDataGeneratorAccess);
        this.registriesDatapackGenerator = crossDataGeneratorAccess.createRegistriesDatapackGenerator(packOutput, lookup, registrySetBuilder);
    }

    @Override
    public RegistriesDatapackGenerator getProvider() {
        return this.registriesDatapackGenerator;
    }
}
