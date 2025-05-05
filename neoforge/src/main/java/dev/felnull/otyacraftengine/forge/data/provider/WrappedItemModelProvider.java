package dev.felnull.otyacraftengine.forge.data.provider;

import dev.felnull.otyacraftengine.data.provider.ItemModelProviderWrapper;
import dev.felnull.otyacraftengine.forge.data.model.ItemModelProviderAccessImpl;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

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
