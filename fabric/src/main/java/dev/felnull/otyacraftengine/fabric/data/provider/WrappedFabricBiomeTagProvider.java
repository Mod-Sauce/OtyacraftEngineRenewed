package dev.felnull.otyacraftengine.fabric.data.provider;

import dev.felnull.otyacraftengine.data.provider.BiomeTagsProviderWrapper;
import dev.felnull.otyacraftengine.data.provider.TagProviderWrapper;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

import java.util.concurrent.CompletableFuture;

public class WrappedFabricBiomeTagProvider extends FabricTagProvider<Biome> {
    private final BiomeTagsProviderWrapper tagProviderWrapper;

    public WrappedFabricBiomeTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture, BiomeTagsProviderWrapper tagProviderWrapper) {
        super(output, Registries.BIOME, registriesFuture);
        this.tagProviderWrapper = tagProviderWrapper;
    }

    @Override
    protected void addTags(HolderLookup.Provider arg) {
        this.tagProviderWrapper.generateTag(new BiomeTagProviderAccessImpl());
    }


    private class BiomeTagProviderAccessImpl implements TagProviderWrapper.TagProviderAccess<Biome, TagProviderWrapper.TagAppenderWrapper<Biome>> {
        @Override
        public TagProviderWrapper.TagAppenderWrapper<Biome> tag(TagKey<Biome> tagKey) {
            return new WrappedFabricTagProvider.TagAppenderWrapperImpl<>(WrappedFabricBiomeTagProvider.this.tag(tagKey));
        }
    }
}
