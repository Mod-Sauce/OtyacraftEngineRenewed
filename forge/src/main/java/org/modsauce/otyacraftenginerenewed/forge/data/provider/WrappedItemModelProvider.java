package org.modsauce.otyacraftenginerenewed.forge.data.provider;

import org.modsauce.otyacraftenginerenewed.data.provider.ItemModelProviderWrapper;
import org.modsauce.otyacraftenginerenewed.forge.data.model.ItemModelProviderAccessImpl;
import net.minecraft.data.PackOutput;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class WrappedItemModelProvider extends ItemModelProvider {
    private final ItemModelProviderWrapper itemModelProviderWrapper;

    public WrappedItemModelProvider(PackOutput output, String modid, ExistingFileHelper existingFileHelper, ItemModelProviderWrapper itemModelProviderWrapper) {
        super(output, modid, existingFileHelper);
        this.itemModelProviderWrapper = itemModelProviderWrapper;
    }

    @Override
    protected void registerModels() {
        this.itemModelProviderWrapper.generateItemModels(new ItemModelProviderAccessImpl(this));
    }
}
