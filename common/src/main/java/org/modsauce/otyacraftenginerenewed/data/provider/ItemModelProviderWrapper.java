package org.modsauce.otyacraftenginerenewed.data.provider;

import org.modsauce.otyacraftenginerenewed.data.CrossDataGeneratorAccess;
import org.modsauce.otyacraftenginerenewed.data.model.ItemModelProviderAccess;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;

public abstract class ItemModelProviderWrapper extends DataProviderWrapper<DataProvider> {
    private final DataProvider itemModelProvider;

    public ItemModelProviderWrapper(PackOutput packOutput, CrossDataGeneratorAccess crossDataGeneratorAccess) {
        super(packOutput, crossDataGeneratorAccess);
        this.itemModelProvider = crossDataGeneratorAccess.createItemModelProvider(packOutput, this);
    }

    @Override
    public DataProvider getProvider() {
        return itemModelProvider;
    }

    public abstract void generateItemModels(ItemModelProviderAccess providerAccess);
}
