package org.modsauce.otyacraftenginerenewed.fabric.data.provider;

import net.minecraft.data.tags.TagAppender;
import org.modsauce.otyacraftenginerenewed.data.provider.TagProviderWrapper;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;

import java.util.concurrent.CompletableFuture;


public class WrappedFabricTagProvider<T> extends FabricTagsProvider<T> {
    private final TagProviderWrapper<T, TagProviderWrapper.TagProviderAccess<T, TagProviderWrapper.TagAppenderWrapper<T>>> tagProviderWrapper;

    public WrappedFabricTagProvider(FabricPackOutput output, ResourceKey<? extends Registry<T>> registryKey, CompletableFuture<HolderLookup.Provider> registriesFuture, TagProviderWrapper<T, TagProviderWrapper.TagProviderAccess<T, TagProviderWrapper.TagAppenderWrapper<T>>> tagProviderWrapper) {
        super(output, registryKey, registriesFuture);
        this.tagProviderWrapper = tagProviderWrapper;
    }

    @Override
    protected void addTags(HolderLookup.Provider arg) {
        this.tagProviderWrapper.generateTag(new TagProviderAccessImpl());
    }

    private class TagProviderAccessImpl implements TagProviderWrapper.TagProviderAccess<T, TagProviderWrapper.TagAppenderWrapper<T>> {
        @Override
        public TagProviderWrapper.TagAppenderWrapper<T> tag(TagKey<T> tagKey) {
            return new TagAppenderWrapperImpl<>(WrappedFabricTagProvider.this.tag(tagKey));
        }
    }

    protected static class TagAppenderWrapperImpl<T> implements TagProviderWrapper.TagAppenderWrapper<T> {
        private final TagAppender<T> appender;

        protected TagAppenderWrapperImpl(TagAppender<T> appender) {
            this.appender = appender;
        }

        private TagAppenderWrapperImpl<T> of(TagAppender<T> appender) {
            return new TagAppenderWrapperImpl<>(appender);
        }

        @Override
        public TagProviderWrapper.TagAppenderWrapper<T> addTag(TagKey<T> tagKey) {
            return of(appender.addTag(tagKey));
        }

        @Override
        public TagProviderWrapper.TagAppenderWrapper<T> add(ResourceKey<T> resourceKey) {
            return of(appender.add(resourceKey));
        }

        @SafeVarargs
        @Override
        public final TagProviderWrapper.TagAppenderWrapper<T> add(ResourceKey<T>... resourceKeys) {
            return of(appender.add(resourceKeys));
        }

        @Override
        public TagProviderWrapper.TagAppenderWrapper<T> addOptional(Identifier Identifier) {
            return of(appender.addOptional(Identifier));
        }

        @Override
        public TagProviderWrapper.TagAppenderWrapper<T> addOptionalTag(Identifier Identifier) {
            return of(appender.addOptionalTag(Identifier));
        }

        @Override
        public TagProviderWrapper.TagAppenderWrapper<T> addVanillaTag(TagKey<T> tagKey) {
            return addOptionalTag(tagKey.location());
        }
    }
}
