package org.modsauce.otyacraftenginerenewed.fabric.data;

import org.modsauce.otyacraftenginerenewed.data.CrossDataGeneratorAccess;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import org.jetbrains.annotations.NotNull;

public interface CrossDataGeneratorAccesses {
    @NotNull
    static CrossDataGeneratorAccess create(FabricDataGenerator fabricDataGenerator) {
        return new CrossDataGeneratorAccessImpl(fabricDataGenerator);
    }
}
