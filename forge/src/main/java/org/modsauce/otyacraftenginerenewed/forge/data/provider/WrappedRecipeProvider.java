package org.modsauce.otyacraftenginerenewed.forge.data.provider;

import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import org.modsauce.otyacraftenginerenewed.data.provider.RecipeProviderWrapper;

public class WrappedRecipeProvider extends RecipeProvider {
    private final RecipeProviderWrapper recipeProviderWrapper;

    public WrappedRecipeProvider(PackOutput arg, RecipeProviderWrapper recipeProviderWrapper) {
        super(arg);
        this.recipeProviderWrapper = recipeProviderWrapper;
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
        recipeProviderWrapper.generateRecipesOutput(recipeOutput);
    }

    // Remove the problematic has methods
}
