package org.modsauce.otyacraftenginerenewed.fabric.data.provider;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import org.jspecify.annotations.NonNull;
import org.modsauce.otyacraftenginerenewed.data.CrossDataGeneratorAccess;
import org.modsauce.otyacraftenginerenewed.data.provider.BlockStateAndModelProviderWrapper;
import org.modsauce.otyacraftenginerenewed.fabric.data.model.BlockStateAndModelProviderAccessImpl;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;

public class WrappedFabricBlockModelProvider extends FabricModelProvider {
    private final CrossDataGeneratorAccess crossDataGeneratorAccess;
    private final BlockStateAndModelProviderWrapper blockStateAndModelProviderWrapper;

    public WrappedFabricBlockModelProvider(FabricPackOutput output, CrossDataGeneratorAccess crossDataGeneratorAccess, BlockStateAndModelProviderWrapper blockStateAndModelProviderWrapper) {
        super(output);
        this.crossDataGeneratorAccess = crossDataGeneratorAccess;
        this.blockStateAndModelProviderWrapper = blockStateAndModelProviderWrapper;
    }

    @Override
    public @NonNull String getName() {
        return "Model Definitions (Block)";
    }

    @Override
    public void generateBlockStateModels(@NonNull BlockModelGenerators blockStateModelGenerator) {
        this.blockStateAndModelProviderWrapper.generateStatesAndModels(new BlockStateAndModelProviderAccessImpl(crossDataGeneratorAccess, blockStateModelGenerator));
    }

    @Override
    public void generateItemModels(@NonNull ItemModelGenerators itemModelGenerator) {
    }
}
