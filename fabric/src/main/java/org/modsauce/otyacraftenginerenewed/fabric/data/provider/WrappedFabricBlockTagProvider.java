package org.modsauce.otyacraftenginerenewed.fabric.data.provider;

import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import org.modsauce.otyacraftenginerenewed.data.provider.BlockTagProviderWrapper;
import org.modsauce.otyacraftenginerenewed.data.provider.IntrinsicHolderTagsProviderWrapper;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

import java.util.concurrent.CompletableFuture;


public class WrappedFabricBlockTagProvider extends FabricTagsProvider.BlockTagsProvider {
    private final BlockTagProviderWrapper tagProviderWrapper;

    public WrappedFabricBlockTagProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture, BlockTagProviderWrapper tagProviderWrapper) {
        super(output, registriesFuture);
        this.tagProviderWrapper = tagProviderWrapper;
    }

    @Override
    protected void addTags(HolderLookup.Provider arg) {
        tagProviderWrapper.generateTag(new BlockTagProviderAccessImpl());
    }

    private class BlockTagProviderAccessImpl implements IntrinsicHolderTagsProviderWrapper.IntrinsicTagProviderAccess<Block> {
        @Override
        public IntrinsicHolderTagsProviderWrapper.IntrinsicTagAppenderWrapper<Block> tag(TagKey<Block> tagKey) {
            return new WrappedFabricIntrinsicHolderTagsProvider.IntrinsicHolderTagAppenderWrapperImpl<>(WrappedFabricBlockTagProvider.this.tag(tagKey), tagProviderWrapper);
        }
    }
}

