package org.modsauce.otyacraftenginerenewed.client.model;

import org.modsauce.otyacraftenginerenewed.client.callpoint.ModelRegister;
import org.modsauce.otyacraftenginerenewed.client.model.impl.ModelCollectiveRegisterImpl;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public interface ModelCollectiveRegister {
    @NotNull
    static ModelCollectiveRegister create() {
        return new ModelCollectiveRegisterImpl();
    }

    void registering(@NotNull ModelRegister register);

    @NotNull
    ModelHolder register(@NotNull ResourceLocation location);

    @NotNull <T extends ModelBundle> T register(@NotNull T bundle);
}
