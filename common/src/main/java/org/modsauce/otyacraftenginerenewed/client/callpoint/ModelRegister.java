package org.modsauce.otyacraftenginerenewed.client.callpoint;

import net.minecraft.resources.Identifier;
import org.modsauce.otyacraftenginerenewed.client.util.OEModelUtils;

import java.util.function.Supplier;

public interface ModelRegister {
    void addModelLoad(Identifier location);

    default Supplier<Object> addAndGetModel(Identifier location) {
        addModelLoad(location);
        return () -> OEModelUtils.getModel(location);
    }
}
