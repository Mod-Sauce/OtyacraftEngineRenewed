package org.modsauce.otyacraftenginerenewed.fabric.data.provider;

import org.modsauce.otyacraftenginerenewed.data.CrossDataGeneratorAccess;
import org.modsauce.otyacraftenginerenewed.data.provider.BlockStateAndModelProviderWrapper;
import org.modsauce.otyacraftenginerenewed.fabric.data.model.BlockStateAndModelProviderAccessImpl;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;

public class WrappedFabricBlockModelProvider extends FabricModelProvider {
    private final CrossDataGeneratorAccess crossDataGeneratorAccess;
    private final BlockStateAndModelProviderWrapper blockStateAndModelProviderWrapper;

    public WrappedFabricBlockModelProvider(FabricDataOutput output, CrossDataGeneratorAccess crossDataGeneratorAccess, BlockStateAndModelProviderWrapper blockStateAndModelProviderWrapper) {
        super(output);
        this.crossDataGeneratorAccess = crossDataGeneratorAccess;
        this.blockStateAndModelProviderWrapper = blockStateAndModelProviderWrapper;
    }

    @Override
    public String getName() {
        return "Model Definitions (Block)";
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators blockStateModelGenerator) {
        this.blockStateAndModelProviderWrapper.generateStatesAndModels(new BlockStateAndModelProviderAccessImpl(crossDataGeneratorAccess, blockStateModelGenerator));
    }

    @Override
    public void generateItemModels(ItemModelGenerators itemModelGenerator) {
    }
}
