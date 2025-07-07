package org.modsauce.otyacraftenginerenewed.data.provider;

import net.minecraft.data.recipes.RecipeOutput;
import org.modsauce.otyacraftenginerenewed.data.CrossDataGeneratorAccess;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;

import java.util.function.Consumer;

public abstract class RecipeProviderWrapper extends DataProviderWrapper<RecipeProvider> {
    private final RecipeProvider recipeProvider;

    public RecipeProviderWrapper(PackOutput packOutput, CrossDataGeneratorAccess crossDataGeneratorAccess) {
        super(packOutput, crossDataGeneratorAccess);
        this.recipeProvider = crossDataGeneratorAccess.createRecipeProvider(packOutput, this);
    }

    @Override
    public RecipeProvider getProvider() {
        return this.recipeProvider;
    }

    public abstract void generateRecipe(Consumer<FinishedRecipe> exporter, RecipeProviderAccess providerAccess);

    public static interface RecipeProviderAccess {
        InventoryChangeTrigger.TriggerInstance has(MinMaxBounds.Ints ints, ItemLike itemLike);

        InventoryChangeTrigger.TriggerInstance has(ItemLike itemLike);

        InventoryChangeTrigger.TriggerInstance has(TagKey<Item> tagKey);

        String getHasName(ItemLike itemLike);

        String getItemName(ItemLike itemLike);
    }

    // Bridge method to handle the new RecipeOutput type in 1.20.2
    public void generateRecipesOutput(RecipeOutput recipeOutput) {
        // Create a RecipeProviderAccess implementation
        RecipeProviderAccess providerAccess = new RecipeProviderAccess() {
            @Override
            public InventoryChangeTrigger.TriggerInstance has(MinMaxBounds.Ints ints, ItemLike itemLike) {
                return InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(itemLike).withCount(ints).build());
            }

            @Override
            public InventoryChangeTrigger.TriggerInstance has(ItemLike itemLike) {
                return InventoryChangeTrigger.TriggerInstance.hasItems(itemLike);
            }

            @Override
            public InventoryChangeTrigger.TriggerInstance has(TagKey<Item> tagKey) {
                return InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(tagKey).build());
            }

            @Override
            public String getHasName(ItemLike itemLike) {
                return "has_" + getItemName(itemLike);
            }

            @Override
            public String getItemName(ItemLike itemLike) {
                return RecipeProvider.getItemName(itemLike);
            }
        };

        // Convert RecipeOutput to Consumer<FinishedRecipe> for backward compatibility
        Consumer<FinishedRecipe> finishedRecipeConsumer = finishedRecipe -> {
            recipeOutput.accept(finishedRecipe.getId(), finishedRecipe.serializeRecipe(), finishedRecipe.serializeAdvancement());
        };

        // Call the abstract method with the converted consumer
        generateRecipe(finishedRecipeConsumer, providerAccess);
    }
}
