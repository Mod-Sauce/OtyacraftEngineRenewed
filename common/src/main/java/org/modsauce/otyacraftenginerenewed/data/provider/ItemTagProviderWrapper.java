package org.modsauce.otyacraftenginerenewed.data.provider;

import org.modsauce.otyacraftenginerenewed.data.CrossDataGeneratorAccess;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

public abstract class ItemTagProviderWrapper extends IntrinsicHolderTagsProviderWrapper<Item, ItemTagProviderWrapper.ItemTagProviderAccess> {
    private final TagsProvider<Item> itemTagsProvider;

    public ItemTagProviderWrapper(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookup, CrossDataGeneratorAccess crossDataGeneratorAccess, @NotNull BlockTagProviderWrapper blockTagProviderWrapper) {
        super(packOutput, lookup, crossDataGeneratorAccess);
        this.itemTagsProvider = crossDataGeneratorAccess.createItemTagProvider(packOutput, lookup, this, blockTagProviderWrapper);
    }

    @Override
    public Function<Item, ResourceKey<Item>> getKeyExtractor() {
        return (item) -> item.builtInRegistryHolder().key();
    }

    @Override
    public TagsProvider<Item> getProvider() {
        return this.itemTagsProvider;
    }

    public static interface ItemTagProviderAccess extends IntrinsicTagProviderAccess<Item> {
        void copy(TagKey<Block> blockTag, TagKey<Item> itemTag);
    }
}
