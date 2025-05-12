package org.modsauce.otyacraftenginerenewed.fabric.data.provider;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.minecraft.advancements.AdvancementHolder;
import org.modsauce.otyacraftenginerenewed.data.provider.AdvancementProviderWrapper;
import org.modsauce.otyacraftenginerenewed.data.provider.AdvancementSubProviderWrapper;

import java.util.List;
import java.util.function.Consumer;

public class WrappedFabricAdvancementProvider extends FabricAdvancementProvider {
    private final AdvancementProviderWrapper wrapper;
    private final List<AdvancementSubProviderWrapper> subProviders;

    public WrappedFabricAdvancementProvider(FabricDataOutput output, AdvancementProviderWrapper wrapper, List<AdvancementSubProviderWrapper> subProviders) {
        super(output);
        this.wrapper = wrapper;
        this.subProviders = subProviders;
    }

    @Override
    public void generateAdvancement(Consumer<AdvancementHolder> consumer) {
        // Call the appropriate method on the wrapper
        // Assuming the wrapper has a method to handle AdvancementHolder
        if (wrapper != null) {
            wrapper.generateAdvancementHolder(consumer);
        }
        
        // Process sub-providers if needed
        if (subProviders != null) {
            for (AdvancementSubProviderWrapper subProvider : subProviders) {
                subProvider.generateAdvancementHolder(consumer);
            }
        }
    }
}