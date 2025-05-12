package org.modsauce.otyacraftenginerenewed.client.model;

import org.modsauce.otyacraftenginerenewed.client.callpoint.ModelRegister;
import org.modsauce.otyacraftenginerenewed.client.model.impl.ModelHolderImpl;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public interface ModelHolder {
    @NotNull
    static ModelHolder create(@NotNull ResourceLocation location) {
        return new ModelHolderImpl(location);
    }

    @NotNull
    BakedModel get();

    void registering(@NotNull ModelRegister register);
}
