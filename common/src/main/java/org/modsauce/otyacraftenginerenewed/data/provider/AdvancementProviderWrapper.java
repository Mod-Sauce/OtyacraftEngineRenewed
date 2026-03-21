package org.modsauce.otyacraftenginerenewed.data.provider;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import org.modsauce.otyacraftenginerenewed.data.CrossDataGeneratorAccess;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class AdvancementProviderWrapper extends DataProviderWrapper<DataProvider> {
  private final DataProvider advancementProvider;

  public AdvancementProviderWrapper(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookup, CrossDataGeneratorAccess crossDataGeneratorAccess, List<AdvancementSubProviderWrapper> subProviderWrappers) {
    super(packOutput, crossDataGeneratorAccess);
    this.advancementProvider = crossDataGeneratorAccess.createAdvancementProvider(packOutput, lookup, this, subProviderWrappers);
  }

  @Override
  public DataProvider getProvider() {
    return advancementProvider;
  }
}
