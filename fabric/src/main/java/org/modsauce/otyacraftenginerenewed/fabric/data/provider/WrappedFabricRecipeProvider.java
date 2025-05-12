package org.modsauce.otyacraftenginerenewed.fabric.data.provider;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import org.modsauce.otyacraftenginerenewed.data.provider.RecipeProviderWrapper;

public class WrappedFabricRecipeProvider extends FabricRecipeProvider {
    private final RecipeProviderWrapper wrapper;

    public WrappedFabricRecipeProvider(FabricDataOutput output, RecipeProviderWrapper wrapper) {
        super(output);
        this.wrapper = wrapper;
    }

    @Override
    public void buildRecipes(RecipeOutput recipeOutput) {
        // Call the appropriate method on the wrapper
        // Assuming the wrapper has a method to handle RecipeOutput
        wrapper.generateRecipesOutput(recipeOutput);
    }

    // Remove the static has methods that conflict with RecipeProvider
}
