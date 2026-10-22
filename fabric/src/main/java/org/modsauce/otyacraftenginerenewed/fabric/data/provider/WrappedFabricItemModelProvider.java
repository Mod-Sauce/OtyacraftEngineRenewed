package org.modsauce.otyacraftenginerenewed.fabric.data.provider;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import org.jspecify.annotations.NonNull;
import org.modsauce.otyacraftenginerenewed.data.provider.ItemModelProviderWrapper;
import org.modsauce.otyacraftenginerenewed.fabric.data.model.ItemModelProviderAccessImpl;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;


public class WrappedFabricItemModelProvider extends FabricModelProvider {
    private final ItemModelProviderWrapper itemModelProviderWrapper;

    public WrappedFabricItemModelProvider(FabricPackOutput output, ItemModelProviderWrapper itemModelProviderWrapper) {
        super(output);
        this.itemModelProviderWrapper = itemModelProviderWrapper;
    }

    @Override
    public void generateBlockStateModels(@NonNull BlockModelGenerators blockStateModelGenerator) {
    }

    @Override
    public void generateItemModels(ItemModelGenerators itemModelGenerator) {
        this.itemModelProviderWrapper.generateItemModels(new ItemModelProviderAccessImpl(itemModelGenerator.modelOutput));
    }

    @Override
    public @NonNull String getName() {
        return "Model Definitions (Item)";
    }
}

