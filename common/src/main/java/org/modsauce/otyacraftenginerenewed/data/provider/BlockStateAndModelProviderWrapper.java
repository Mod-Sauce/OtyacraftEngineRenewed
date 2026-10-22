package org.modsauce.otyacraftenginerenewed.data.provider;

import org.modsauce.otyacraftenginerenewed.OtyacraftEngine;
import org.modsauce.otyacraftenginerenewed.data.CrossDataGeneratorAccess;
import org.modsauce.otyacraftenginerenewed.data.model.BlockStateAndModelProviderAccess;

import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;

public abstract class BlockStateAndModelProviderWrapper extends DataProviderWrapper<DataProvider> {
  public static final Identifier OE_BASE_BLOCK_ENTITY_MODEL = Identifier.fromNamespaceAndPath(OtyacraftEngine.MODID, "base/block_entity");
  public static final Identifier OE_BASE_BLOCK_ENTITY_APPEARANCE_MODEL = Identifier.fromNamespaceAndPath(OtyacraftEngine.MODID, "base/block_entity_appearance");
  private final DataProvider blockStateAndModelProvider;

  public BlockStateAndModelProviderWrapper(PackOutput packOutput, CrossDataGeneratorAccess crossDataGeneratorAccess) {
    super(packOutput, crossDataGeneratorAccess);
    this.blockStateAndModelProvider = crossDataGeneratorAccess.createBlockStateAndModelProvider(packOutput, this);
  }

  @Override
  public DataProvider getProvider() {
    return blockStateAndModelProvider;
  }

  public abstract void generateStatesAndModels(BlockStateAndModelProviderAccess providerAccess);
}
