package dev.felnull.otyacraftengine.forge.data.provider;

import dev.felnull.otyacraftengine.data.provider.RecipeProviderWrapper;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;

import java.util.function.Consumer;

public class WrappedRecipeProvider extends RecipeProvider {
    private final RecipeProviderWrapper recipeProviderWrapper;

    public WrappedRecipeProvider(PackOutput arg, RecipeProviderWrapper recipeProviderWrapper) {
        super(arg);
        this.recipeProviderWrapper = recipeProviderWrapper;
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
        recipeProviderWrapper.generateRecipe(consumer, new RecipeProviderAccessImpl());
    }

    private static class RecipeProviderAccessImpl implements RecipeProviderWrapper.RecipeProviderAccess {
        @Override
        public InventoryChangeTrigger.TriggerInstance has(MinMaxBounds.Ints ints, ItemLike itemLike) {
            return RecipeProvider.has(ints, itemLike);
        }

        @Override
        public InventoryChangeTrigger.TriggerInstance has(ItemLike itemLike) {
            return RecipeProvider.has(itemLike);
        }

        @Override
        public InventoryChangeTrigger.TriggerInstance has(TagKey<Item> tagKey) {
            return RecipeProvider.has(tagKey);
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
