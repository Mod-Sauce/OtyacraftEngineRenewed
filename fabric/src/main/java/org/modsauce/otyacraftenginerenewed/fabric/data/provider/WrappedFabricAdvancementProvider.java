package org.modsauce.otyacraftenginerenewed.fabric.data.provider;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.core.HolderLookup;
import org.modsauce.otyacraftenginerenewed.data.provider.AdvancementProviderWrapper;
import org.modsauce.otyacraftenginerenewed.data.provider.AdvancementSubProviderWrapper;

public class WrappedFabricAdvancementProvider
  extends FabricAdvancementProvider {

  private final AdvancementProviderWrapper advancementProviderWrapper;
  private final List<AdvancementSubProviderWrapper> subProviderWrappers;

  public WrappedFabricAdvancementProvider(
    FabricDataOutput output,
    CompletableFuture<HolderLookup.Provider> registryLookup,
    AdvancementProviderWrapper advancementProviderWrapper,
    List<AdvancementSubProviderWrapper> subProviderWrappers
  ) {
    super(output, registryLookup);
    this.advancementProviderWrapper = advancementProviderWrapper;
    this.subProviderWrappers = subProviderWrappers;
  }

  @Override
  public void generateAdvancement(
    HolderLookup.Provider registries,
    Consumer<AdvancementHolder> consumer
  ) {
    for (AdvancementSubProviderWrapper subProviderWrapper : subProviderWrappers) {
      subProviderWrapper.generate(consumer);
    }
  }
}
