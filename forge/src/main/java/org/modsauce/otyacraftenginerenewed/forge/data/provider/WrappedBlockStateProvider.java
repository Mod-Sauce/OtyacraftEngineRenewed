package org.modsauce.otyacraftenginerenewed.forge.data.provider;

import org.modsauce.otyacraftenginerenewed.data.provider.BlockStateAndModelProviderWrapper;
import org.modsauce.otyacraftenginerenewed.forge.data.model.BlockStateAndModelProviderAccessImpl;
import net.minecraft.data.PackOutput;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class WrappedBlockStateProvider extends BlockStateProvider {
    private final BlockStateAndModelProviderWrapper blockStateAndModelProviderWrapper;

    public WrappedBlockStateProvider(PackOutput output, String modid, ExistingFileHelper exFileHelper, BlockStateAndModelProviderWrapper blockStateAndModelProviderWrapper) {
        super(output, modid, exFileHelper);
        this.blockStateAndModelProviderWrapper = blockStateAndModelProviderWrapper;
    }

    @Override
    protected void registerStatesAndModels() {
        this.blockStateAndModelProviderWrapper.generateStatesAndModels(new BlockStateAndModelProviderAccessImpl(this));
    }
}
