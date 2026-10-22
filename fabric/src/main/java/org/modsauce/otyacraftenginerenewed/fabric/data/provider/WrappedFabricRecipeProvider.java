package org.modsauce.otyacraftenginerenewed.fabric.data.provider;

import java.util.concurrent.CompletableFuture;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import org.jspecify.annotations.NonNull;
import org.modsauce.otyacraftenginerenewed.OtyacraftEngine;
import org.modsauce.otyacraftenginerenewed.data.provider.RecipeProviderWrapper;

public class WrappedFabricRecipeProvider extends FabricRecipeProvider {

  private final RecipeProviderWrapper recipeProviderWrapper;

  public WrappedFabricRecipeProvider(
          FabricPackOutput output,
    CompletableFuture<HolderLookup.Provider> registryLookup,
    RecipeProviderWrapper recipeProviderWrapper
  ) {
    super(output, registryLookup);
    this.recipeProviderWrapper = recipeProviderWrapper;
  }

  @Override
  protected @NonNull RecipeProvider createRecipeProvider(HolderLookup.@NonNull Provider registryLookup, @NonNull RecipeOutput exporter) {
    return new RecipeProvider(registryLookup, exporter) {
      @Override
      public void buildRecipes() {
        HolderLookup.RegistryLookup<Item> itemLookup = registries.lookupOrThrow(Registries.ITEM);
      }
    };
  }

  @Override
  public @NonNull String getName() {
    return OtyacraftEngine.MODID;
  }

  private static class RecipeProviderAccessImpl
    implements RecipeProviderWrapper.RecipeProviderAccess {

    @Override
    public InventoryChangeTrigger.TriggerInstance has(
      MinMaxBounds.Ints ints,
      ItemLike itemLike
    ) {
      return RecipeProvider.has(ints, itemLike).triggerInstance();
    }

    @Override
    public InventoryChangeTrigger.TriggerInstance has(ItemLike itemLike) {
      return RecipeProvider.has(itemLike).triggerInstance();
    }

    @Override
    public InventoryChangeTrigger.TriggerInstance has(TagKey<Item> tagKey) {
      return RecipeProvider.has(tagKey).triggerInstance();
    }

    @Override
    public String getHasName(ItemLike itemLike) {
      return RecipeProvider.getHasName(itemLike);
    }

    @Override
    public String getItemName(ItemLike itemLike) {
      return RecipeProvider.getItemName(itemLike);
    }
  }
}
