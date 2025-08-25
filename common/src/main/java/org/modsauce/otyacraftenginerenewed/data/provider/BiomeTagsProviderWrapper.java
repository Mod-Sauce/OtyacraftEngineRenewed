package org.modsauce.otyacraftenginerenewed.data.provider;

import org.modsauce.otyacraftenginerenewed.data.CrossDataGeneratorAccess;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.world.level.biome.Biome;

import java.util.concurrent.CompletableFuture;

public abstract class BiomeTagsProviderWrapper extends TagProviderWrapper<Biome, TagProviderWrapper.TagProviderAccess<Biome, TagProviderWrapper.TagAppenderWrapper<Biome>>> {
    private final TagsProvider<Biome> biomeTagsProvider;

    public BiomeTagsProviderWrapper(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookup, CrossDataGeneratorAccess crossDataGeneratorAccess) {
        super(packOutput, lookup, crossDataGeneratorAccess);
        this.biomeTagsProvider = crossDataGeneratorAccess.createBiomeTagProvider(packOutput, lookup, this);
    }

    @Override
    public TagsProvider<Biome> getProvider() {
        return this.biomeTagsProvider;
    }
}
