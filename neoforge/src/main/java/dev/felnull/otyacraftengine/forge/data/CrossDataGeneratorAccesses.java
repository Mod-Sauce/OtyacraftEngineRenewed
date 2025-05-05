package dev.felnull.otyacraftengine.forge.data;

import dev.felnull.otyacraftengine.data.CrossDataGeneratorAccess;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import org.jetbrains.annotations.NotNull;

public interface CrossDataGeneratorAccesses {
    @NotNull
    static CrossDataGeneratorAccess create(GatherDataEvent gatherDataEvent) {
        return new CrossDataGeneratorAccessImpl(gatherDataEvent);
    }
}
