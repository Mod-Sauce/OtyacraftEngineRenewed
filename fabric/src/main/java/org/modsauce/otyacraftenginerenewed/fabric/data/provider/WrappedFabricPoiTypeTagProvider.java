package org.modsauce.otyacraftenginerenewed.fabric.data.provider;

import org.modsauce.otyacraftenginerenewed.data.provider.PoiTypeTagProviderWrapper;
import org.modsauce.otyacraftenginerenewed.data.provider.TagProviderWrapper;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.ai.village.poi.PoiType;

import java.util.concurrent.CompletableFuture;


public class WrappedFabricPoiTypeTagProvider extends FabricTagsProvider<PoiType> {
    private final PoiTypeTagProviderWrapper tagProviderWrapper;

    public WrappedFabricPoiTypeTagProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> lookup, PoiTypeTagProviderWrapper tagProviderWrapper) {
        super(output, Registries.POINT_OF_INTEREST_TYPE, lookup);
        this.tagProviderWrapper = tagProviderWrapper;
    }

    @Override
    protected void addTags(HolderLookup.Provider arg) {
        this.tagProviderWrapper.generateTag(new PoiTypeTagProviderAccessImpl());
    }

    private class PoiTypeTagProviderAccessImpl implements TagProviderWrapper.TagProviderAccess<PoiType, TagProviderWrapper.TagAppenderWrapper<PoiType>> {
        @Override
        public TagProviderWrapper.TagAppenderWrapper<PoiType> tag(TagKey<PoiType> tagKey) {
            return new WrappedFabricTagProvider.TagAppenderWrapperImpl<>(WrappedFabricPoiTypeTagProvider.this.tag(tagKey));
        }
    }
}

