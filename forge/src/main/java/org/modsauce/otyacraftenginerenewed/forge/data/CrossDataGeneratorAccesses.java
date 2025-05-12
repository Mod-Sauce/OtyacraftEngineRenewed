package org.modsauce.otyacraftenginerenewed.forge.data;


import net.minecraftforge.data.event.GatherDataEvent;
import org.jetbrains.annotations.NotNull;
import org.modsauce.otyacraftenginerenewed.data.CrossDataGeneratorAccess;

public interface CrossDataGeneratorAccesses {
    @NotNull
    static CrossDataGeneratorAccess create(GatherDataEvent gatherDataEvent) {
        return new CrossDataGeneratorAccessImpl(gatherDataEvent);
    }
}
