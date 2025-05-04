package dev.felnull.otyacraftengine.forge.data.provider;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import dev.felnull.otyacraftengine.data.provider.BlockLootTableProviderWrapper;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.Map;
import java.util.Set;

public class WrappedBlockLootTableProvider extends LootTableProvider {
    private final BlockLootTableProviderWrapper blockLootTableProviderWrapper;

    public WrappedBlockLootTableProvider(PackOutput arg, BlockLootTableProviderWrapper blockLootTableProviderWrapper) {
        super(arg, Set.of(), ImmutableList.of(new LootTableProvider.SubProviderEntry(() -> new WrappedBlockLootSubProvider(blockLootTableProviderWrapper), LootContextParamSets.BLOCK)));
        this.blockLootTableProviderWrapper = blockLootTableProviderWrapper;
    }

    @Override
    protected void validate(Map<ResourceLocation, LootTable> map, ValidationContext validationcontext) {

    }

    private static class WrappedBlockLootSubProvider extends BlockLootSubProvider {
        private final BlockLootTableProviderWrapper blockLootTableProviderWrapper;

        protected WrappedBlockLootSubProvider(BlockLootTableProviderWrapper blockLootTableProviderWrapper) {
            super(ImmutableSet.of(), FeatureFlags.REGISTRY.allFlags());
            this.blockLootTableProviderWrapper = blockLootTableProviderWrapper;
        }

        @Override
        protected Iterable<Block> getKnownBlocks() {
            return blockLootTableProviderWrapper.getKnownBlocks();
        }

        @Override
        protected void generate() {
            blockLootTableProviderWrapper.generateBlockLootTables(this, new BlockLootTableProviderAccessImpl());
        }

        private class BlockLootTableProviderAccessImpl implements BlockLootTableProviderWrapper.BlockLootTableProviderAccess {
            @Override
            public void excludeFromStrictValidation(Block block) {

            }

            @Override
            public void dropSelf(Block block) {
                WrappedBlockLootSubProvider.this.dropSelf(block);
            }

            @Override
            public void dropOther(Block block, ItemLike itemLike) {
                WrappedBlockLootSubProvider.this.dropOther(block, itemLike);
            }

            @Override
            public void dropWhenSilkTouch(Block block) {
                WrappedBlockLootSubProvider.this.dropWhenSilkTouch(block);
            }

            @Override
            public void otherWhenSilkTouch(Block block, Block drop) {
                WrappedBlockLootSubProvider.this.otherWhenSilkTouch(block, drop);
            }

            @Override
            public void add(Block block, LootTable.Builder builder) {
                WrappedBlockLootSubProvider.this.add(block, builder);
            }
        }
    }
}
