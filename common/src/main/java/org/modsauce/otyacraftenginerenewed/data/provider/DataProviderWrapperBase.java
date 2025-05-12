package org.modsauce.otyacraftenginerenewed.data.provider;

import org.modsauce.otyacraftenginerenewed.data.CrossDataGeneratorAccess;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;

public interface DataProviderWrapperBase {
    CrossDataGeneratorAccess getCrossGeneratorAccess();

    default ResourceLocation modLoc(String id) {
        return new ResourceLocation(getCrossGeneratorAccess().getMod().getModId(), id);
    }

    default DataGenerator getGenerator() {
        return getCrossGeneratorAccess().getVanillaGenerator();
    }
}
