package org.modsauce.otyacraftenginerenewed.data.model.impl;

import org.modsauce.otyacraftenginerenewed.data.model.FileTexture;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public record FileTextureImpl(ResourceLocation location, boolean existingCheck) implements FileTexture {
    @Override
    public @NotNull ResourceLocation getLocation() {
        return location;
    }

    @Override
    public boolean isExistingCheck() {
        return existingCheck;
    }
}
