package org.modsauce.otyacraftenginerenewed.data.provider;

import net.minecraft.advancements.AdvancementHolder;
import org.modsauce.otyacraftenginerenewed.data.CrossDataGeneratorAccess;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;

import java.util.List;
import java.util.function.Consumer;

public class AdvancementProviderWrapper extends DataProviderWrapper<DataProvider> {
    private final DataProvider advancementProvider;

    public AdvancementProviderWrapper(PackOutput packOutput, CrossDataGeneratorAccess crossDataGeneratorAccess, List<AdvancementSubProviderWrapper> subProviderWrappers) {
        super(packOutput, crossDataGeneratorAccess);
        this.advancementProvider = crossDataGeneratorAccess.createAdvancementProvider(packOutput, this, subProviderWrappers);
    }

    @Override
    public DataProvider getProvider() {
        return advancementProvider;
    }

    // Add this method to handle the new AdvancementHolder type
    public void generateAdvancementHolder(Consumer<AdvancementHolder> consumer) {
        // Implementation depends on your specific needs
        // This might involve adapting between different advancement types
    }
}
