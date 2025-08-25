package org.modsauce.otyacraftenginerenewed.neoforge.data;

import org.modsauce.otyacraftenginerenewed.data.CrossDataGeneratorAccess;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import org.jetbrains.annotations.NotNull;

public interface CrossDataGeneratorAccesses {
    @NotNull
    static CrossDataGeneratorAccess create(GatherDataEvent gatherDataEvent) {
        return new CrossDataGeneratorAccessImpl(gatherDataEvent);
    }
}
