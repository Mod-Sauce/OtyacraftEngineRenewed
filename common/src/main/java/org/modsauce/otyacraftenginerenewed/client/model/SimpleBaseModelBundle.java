package org.modsauce.otyacraftenginerenewed.client.model;

import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public abstract class SimpleBaseModelBundle implements ModelBundle {
    private final List<ModelHolder> holders = new ArrayList<>();

    @Override
    public @NotNull List<ModelHolder> getAllHolders() {
        return holders;
    }

    @NotNull
    protected ModelHolder holder(@NotNull ResourceLocation location) {
        var hol = ModelHolder.create(location);
        holders.add(hol);
        return hol;
    }
}
