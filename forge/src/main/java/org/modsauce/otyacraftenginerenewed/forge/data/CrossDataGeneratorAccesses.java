package org.modsauce.otyacraftenginerenewed.forge.data;

import org.modsauce.otyacraftenginerenewed.data.CrossDataGeneratorAccess;
import net.minecraftforge.data.event.GatherDataEvent;
import org.jetbrains.annotations.NotNull;

public interface CrossDataGeneratorAccesses {
    @NotNull
    static CrossDataGeneratorAccess create(GatherDataEvent gatherDataEvent) {
        return new CrossDataGeneratorAccessImpl(gatherDataEvent);
    }
}
