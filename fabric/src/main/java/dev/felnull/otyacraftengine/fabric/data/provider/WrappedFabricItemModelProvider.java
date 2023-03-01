package dev.felnull.otyacraftengine.fabric.data.provider;

import dev.felnull.otyacraftengine.data.provider.ItemModelProviderWrapper;
import dev.felnull.otyacraftengine.fabric.data.model.ItemModelProviderAccessImpl;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;


public class WrappedFabricItemModelProvider extends FabricModelProvider {
    private final ItemModelProviderWrapper itemModelProviderWrapper;

    public WrappedFabricItemModelProvider(FabricDataOutput output, ItemModelProviderWrapper itemModelProviderWrapper) {
        super(output);
        this.itemModelProviderWrapper = itemModelProviderWrapper;
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators blockStateModelGenerator) {
    }

    @Override
    public void generateItemModels(ItemModelGenerators itemModelGenerator) {
        this.itemModelProviderWrapper.generateItemModels(new ItemModelProviderAccessImpl(itemModelGenerator.output));
    }

    @Override
    public String getName() {
        return "Model Definitions (Item)";
    }
}

