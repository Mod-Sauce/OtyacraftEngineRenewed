package org.modsauce.otyacraftenginerenewed.data.provider;

import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import org.modsauce.otyacraftenginerenewed.data.CrossDataGeneratorAccess;

import java.util.List;

public class AdvancementProviderWrapper extends DataProviderWrapper<DataProvider> {
  private final DataProvider advancementProvider;

  public AdvancementProviderWrapper(PackOutput packOutput, CrossDataGeneratorAccess crossDataGeneratorAccess, List<AdvancementSubProviderWrapper> subProviderWrappers) {
    super(packOutput, crossDataGeneratorAccess);
    this.advancementProvider = crossDataGeneratorAccess.createAdvancementProvider(packOutput, this, subProviderWrappers);
  }

  @Override
  public DataProvider getProvider() {
    return advancementProvider;
  }
}
