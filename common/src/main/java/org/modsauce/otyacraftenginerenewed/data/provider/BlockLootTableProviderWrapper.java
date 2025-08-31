package org.modsauce.otyacraftenginerenewed.data.provider;

import dev.architectury.registry.registries.DeferredRegister;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import org.modsauce.otyacraftenginerenewed.data.CrossDataGeneratorAccess;

import java.util.ArrayList;
import java.util.List;

public abstract class BlockLootTableProviderWrapper extends DataProviderWrapper<DataProvider> {
  private final DataProvider blockLootTableProvider;

  public BlockLootTableProviderWrapper(PackOutput packOutput, CrossDataGeneratorAccess crossDataGeneratorAccess) {
    super(packOutput, crossDataGeneratorAccess);
    this.blockLootTableProvider = crossDataGeneratorAccess.createBlockLootTableProvider(packOutput, this);
  }

  @Override
  public DataProvider getProvider() {
    return blockLootTableProvider;
  }

  public abstract void generateBlockLootTables(BlockLootSubProvider blockLoot, BlockLootTableProviderAccess providerAccess);

  public abstract Iterable<Block> getKnownBlocks();

  protected Iterable<Block> extract(DeferredRegister<Block> deferredRegister) {
    List<Block> blocks = new ArrayList<>();
    deferredRegister.iterator().forEachRemaining(r -> blocks.add(r.get()));
    return blocks;
  }

  public static interface BlockLootTableProviderAccess {
    void excludeFromStrictValidation(Block block);

    void dropSelf(Block block);

    void dropOther(Block block, ItemLike itemLike);

    void dropWhenSilkTouch(Block block);

    void otherWhenSilkTouch(Block block, Block drop);

    void add(Block block, LootTable.Builder builder);
  }
}
