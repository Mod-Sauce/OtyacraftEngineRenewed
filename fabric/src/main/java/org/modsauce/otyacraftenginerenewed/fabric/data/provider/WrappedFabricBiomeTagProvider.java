package org.modsauce.otyacraftenginerenewed.fabric.data.provider;

import org.jspecify.annotations.NonNull;
import org.modsauce.otyacraftenginerenewed.data.provider.BiomeTagsProviderWrapper;
import org.modsauce.otyacraftenginerenewed.data.provider.TagProviderWrapper;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

import java.util.concurrent.CompletableFuture;

public class WrappedFabricBiomeTagProvider extends FabricTagsProvider<Biome> {
    private final BiomeTagsProviderWrapper tagProviderWrapper;

    public WrappedFabricBiomeTagProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture, BiomeTagsProviderWrapper tagProviderWrapper) {
        super(output, Registries.BIOME, registriesFuture);
        this.tagProviderWrapper = tagProviderWrapper;
    }

    @Override
    protected void addTags(HolderLookup.@NonNull Provider arg) {
        this.tagProviderWrapper.generateTag(new BiomeTagProviderAccessImpl());
    }


    private class BiomeTagProviderAccessImpl implements TagProviderWrapper.TagProviderAccess<Biome, TagProviderWrapper.TagAppenderWrapper<Biome>> {
        @Override
        public TagProviderWrapper.TagAppenderWrapper<Biome> tag(TagKey<Biome> tagKey) {
            return new WrappedFabricTagProvider.TagAppenderWrapperImpl<>(WrappedFabricBiomeTagProvider.this.tag(tagKey));
        }
    }
}
