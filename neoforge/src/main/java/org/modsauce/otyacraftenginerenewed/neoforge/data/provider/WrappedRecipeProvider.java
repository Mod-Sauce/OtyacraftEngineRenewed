package org.modsauce.otyacraftenginerenewed.neoforge.data.provider;

import org.jetbrains.annotations.NotNull;
import org.modsauce.otyacraftenginerenewed.data.provider.RecipeProviderWrapper;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class WrappedRecipeProvider extends RecipeProvider {
    private final RecipeProviderWrapper recipeProviderWrapper;

    public WrappedRecipeProvider(PackOutput arg, RecipeProviderWrapper recipeProviderWrapper) {
        super(arg, CompletableFuture.completedFuture(null));
        this.recipeProviderWrapper = recipeProviderWrapper;
    }

    public WrappedRecipeProvider(PackOutput arg, CompletableFuture<HolderLookup.Provider> lookup, RecipeProviderWrapper recipeProviderWrapper) {
        super(arg, lookup);
        this.recipeProviderWrapper = recipeProviderWrapper;
    }

    @Override
    protected void buildRecipes(@NotNull RecipeOutput consumer) {
        recipeProviderWrapper.generateRecipe(consumer, new RecipeProviderAccessImpl());
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
