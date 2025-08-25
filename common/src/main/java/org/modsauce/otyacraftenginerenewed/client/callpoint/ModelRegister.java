package org.modsauce.otyacraftenginerenewed.client.callpoint;

import org.modsauce.otyacraftenginerenewed.client.util.OEModelUtils;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Supplier;

public interface ModelRegister {
    void addModelLoad(ResourceLocation location);

    default Supplier<BakedModel> addAndGetModel(ResourceLocation location) {
        addModelLoad(location);
        return () -> OEModelUtils.getModel(location);
    }
}
