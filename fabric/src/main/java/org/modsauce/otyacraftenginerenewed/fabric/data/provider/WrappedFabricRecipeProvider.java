package org.modsauce.otyacraftenginerenewed.fabric.data.provider;

import org.modsauce.otyacraftenginerenewed.data.provider.RecipeProviderWrapper;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;

import java.util.function.Consumer;

public class WrappedFabricRecipeProvider extends FabricRecipeProvider {
    private final RecipeProviderWrapper recipeProviderWrapper;

    public WrappedFabricRecipeProvider(FabricDataOutput output, RecipeProviderWrapper recipeProviderWrapper) {
        super(output);
        this.recipeProviderWrapper = recipeProviderWrapper;
    }

    @Override
    public void buildRecipes(Consumer<RecipeOutput> exporter) {
        recipeProviderWrapper.generateRecipe(exporter, new RecipeProviderAccessImpl());
    }

    private static class RecipeProviderAccessImpl implements RecipeProviderWrapper.RecipeProviderAccess {
        @Override
        public InventoryChangeTrigger.TriggerInstance has(MinMaxBounds.Ints ints, ItemLike itemLike) {
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
