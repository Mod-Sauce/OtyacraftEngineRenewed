package dev.felnull.otyacraftengine.forge.data.provider;

import dev.felnull.otyacraftengine.data.provider.PoiTypeTagProviderWrapper;
import dev.felnull.otyacraftengine.data.provider.TagProviderWrapper;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.PoiTypeTagsProvider;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class WrappedPoiTypeTagsProvider extends PoiTypeTagsProvider {
    private final PoiTypeTagProviderWrapper tagProviderWrapper;

    public WrappedPoiTypeTagsProvider(PackOutput arg, CompletableFuture<HolderLookup.Provider> lookup, String modId, ExistingFileHelper existingFileHelper, PoiTypeTagProviderWrapper tagProviderWrapper) {
        super(arg, lookup, modId, existingFileHelper);
        this.tagProviderWrapper = tagProviderWrapper;
    }

    @Override
    protected void addTags(HolderLookup.Provider arg) {
        tagProviderWrapper.generateTag(new PoiTypeTagProviderAccessImpl());
    }


    private class PoiTypeTagProviderAccessImpl implements TagProviderWrapper.TagProviderAccess<PoiType, TagProviderWrapper.TagAppenderWrapper<PoiType>> {
        @Override
        public TagProviderWrapper.TagAppenderWrapper<PoiType> tag(TagKey<PoiType> tagKey) {
            return new WrappedTagsProvider.TagAppenderWrapperImpl<>(WrappedPoiTypeTagsProvider.this.tag(tagKey));
        }
    }
}
