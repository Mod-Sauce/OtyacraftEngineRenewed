package org.modsauce.otyacraftenginerenewed.fabric.data.provider;

import org.jspecify.annotations.NonNull;
import org.modsauce.otyacraftenginerenewed.data.provider.IntrinsicHolderTagsProviderWrapper;
import org.modsauce.otyacraftenginerenewed.data.provider.ItemTagProviderWrapper;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;


public class WrappedFabricItemTagProvider extends FabricTagsProvider.ItemTagsProvider {
    private final ItemTagProviderWrapper tagProviderWrapper;

    public WrappedFabricItemTagProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> completableFuture, @Nullable BlockTagsProvider blockTagProvider, ItemTagProviderWrapper tagProviderWrapper) {
        super(output, completableFuture, blockTagProvider);
        this.tagProviderWrapper = tagProviderWrapper;
    }

    @Override
    protected void addTags(HolderLookup.@NonNull Provider arg) {
        this.tagProviderWrapper.generateTag(new ItemTagProviderAccessImpl());
    }


    private class ItemTagProviderAccessImpl implements ItemTagProviderWrapper.ItemTagProviderAccess {
        @Override
        public void copy(TagKey<Block> blockTag, TagKey<Item> itemTag) {
            WrappedFabricItemTagProvider.this.copy(blockTag, itemTag);
        }

        @Override
        public IntrinsicHolderTagsProviderWrapper.IntrinsicTagAppenderWrapper<Item> tag(TagKey<Item> tagKey) {
            return new WrappedFabricIntrinsicHolderTagsProvider.IntrinsicHolderTagAppenderWrapperImpl<>(WrappedFabricItemTagProvider.this.tag(tagKey), tagProviderWrapper);
        }
    }
}

