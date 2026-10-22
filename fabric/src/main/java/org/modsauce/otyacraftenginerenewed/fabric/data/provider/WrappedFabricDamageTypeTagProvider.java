package org.modsauce.otyacraftenginerenewed.fabric.data.provider;

import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import org.jspecify.annotations.NonNull;
import org.modsauce.otyacraftenginerenewed.data.provider.DamageTypeTagsProviderWrapper;
import org.modsauce.otyacraftenginerenewed.data.provider.TagProviderWrapper;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;

import java.util.concurrent.CompletableFuture;

public class WrappedFabricDamageTypeTagProvider extends FabricTagsProvider<DamageType> {
    private final DamageTypeTagsProviderWrapper tagProviderWrapper;

    public WrappedFabricDamageTypeTagProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture, DamageTypeTagsProviderWrapper tagProviderWrapper) {
        super(output, Registries.DAMAGE_TYPE, registriesFuture);
        this.tagProviderWrapper = tagProviderWrapper;
    }

    @Override
    protected void addTags(HolderLookup.@NonNull Provider arg) {
        this.tagProviderWrapper.generateTag(new DamageTypeTagProviderAccessImpl());
    }

    private class DamageTypeTagProviderAccessImpl implements TagProviderWrapper.TagProviderAccess<DamageType, TagProviderWrapper.TagAppenderWrapper<DamageType>> {
        @Override
        public TagProviderWrapper.TagAppenderWrapper<DamageType> tag(TagKey<DamageType> tagKey) {
            return new WrappedFabricTagProvider.TagAppenderWrapperImpl<>(WrappedFabricDamageTypeTagProvider.this.tag(tagKey));
        }
    }
}
