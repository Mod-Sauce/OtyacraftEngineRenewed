package org.modsauce.otyacraftenginerenewed.fabric.data.provider;

import org.modsauce.otyacraftenginerenewed.data.provider.AdvancementProviderWrapper;
import org.modsauce.otyacraftenginerenewed.data.provider.AdvancementSubProviderWrapper;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.minecraft.advancements.Advancement;

import java.util.List;
import java.util.function.Consumer;


public class WrappedFabricAdvancementProvider extends FabricAdvancementProvider {
    private final AdvancementProviderWrapper advancementProviderWrapper;
    private final List<AdvancementSubProviderWrapper> subProviderWrappers;

    public WrappedFabricAdvancementProvider(FabricDataOutput output, AdvancementProviderWrapper advancementProviderWrapper, List<AdvancementSubProviderWrapper> subProviderWrappers) {
        super(output);
        this.advancementProviderWrapper = advancementProviderWrapper;
        this.subProviderWrappers = subProviderWrappers;
    }

    @Override
    public void generateAdvancement(Consumer<Advancement> consumer) {
        for (AdvancementSubProviderWrapper subProviderWrapper : subProviderWrappers) {
            subProviderWrapper.generate(consumer);
        }
    }
}

