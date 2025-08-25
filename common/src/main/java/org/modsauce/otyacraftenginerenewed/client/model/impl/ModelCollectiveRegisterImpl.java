package org.modsauce.otyacraftenginerenewed.client.model.impl;

import org.modsauce.otyacraftenginerenewed.client.callpoint.ModelRegister;
import org.modsauce.otyacraftenginerenewed.client.model.ModelBundle;
import org.modsauce.otyacraftenginerenewed.client.model.ModelCollectiveRegister;
import org.modsauce.otyacraftenginerenewed.client.model.ModelHolder;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

public class ModelCollectiveRegisterImpl implements ModelCollectiveRegister {
    private final Set<ModelHolder> models = new HashSet<>();

    @Override
    public @NotNull ModelHolder register(@NotNull ResourceLocation location) {
        var holder = ModelHolder.create(location);
        models.add(holder);
        return holder;
    }

    @Override
    public <T extends ModelBundle> @NotNull T register(@NotNull T bundle) {
        models.addAll(bundle.getAllHolders());
        return bundle;
    }

    @Override
    public void registering(@NotNull ModelRegister register) {
        for (ModelHolder model : models) {
            model.registering(register);
        }
    }
}
