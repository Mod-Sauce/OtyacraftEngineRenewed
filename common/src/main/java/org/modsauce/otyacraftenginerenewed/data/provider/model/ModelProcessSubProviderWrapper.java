package org.modsauce.otyacraftenginerenewed.data.provider.model;

import net.minecraft.data.CachedOutput;
import org.modsauce.otyacraftenginerenewed.data.CrossDataGeneratorAccess;
import org.modsauce.otyacraftenginerenewed.data.provider.DataProviderWrapperBase;
import org.modsauce.otyacraftenginerenewed.data.provider.ModelProcessProviderWrapper;

import java.util.List;

public abstract class ModelProcessSubProviderWrapper implements DataProviderWrapperBase {
  protected final CrossDataGeneratorAccess crossDataGeneratorAccess;

  public ModelProcessSubProviderWrapper(CrossDataGeneratorAccess crossDataGeneratorAccess) {
    this.crossDataGeneratorAccess = crossDataGeneratorAccess;
  }

  @Override
  public CrossDataGeneratorAccess getCrossGeneratorAccess() {
    return crossDataGeneratorAccess;
  }

  public List<ModelProcessProviderWrapper.ModelData> process(CachedOutput cachedOutput, List<ModelProcessProviderWrapper.ModelData> modelData) {
    return modelData.stream().flatMap(r -> process(cachedOutput, r).stream()).toList();
  }

  public abstract List<ModelProcessProviderWrapper.ModelData> process(CachedOutput cachedOutput, ModelProcessProviderWrapper.ModelData modelData);
}
