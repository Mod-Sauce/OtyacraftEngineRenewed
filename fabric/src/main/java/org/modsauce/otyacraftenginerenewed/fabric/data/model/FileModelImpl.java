package org.modsauce.otyacraftenginerenewed.fabric.data.model;

import org.modsauce.otyacraftenginerenewed.data.model.FileModel;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class FileModelImpl implements FileModel {
    private final ResourceLocation location;

    public FileModelImpl(ResourceLocation location) {
        this.location = location;
    }

    @Override
    public @NotNull ResourceLocation getLocation() {
        return location;
    }
}
