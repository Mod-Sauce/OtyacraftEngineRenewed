package org.modsauce.otyacraftenginerenewed.data.model.impl;

import net.minecraft.resources.Identifier;
import org.modsauce.otyacraftenginerenewed.data.model.FileTexture;

public record FileTextureImpl(Identifier location, boolean existingCheck) implements FileTexture {
  @Override
  public Identifier getLocation() {
    return location;
  }

  @Override
  public boolean isExistingCheck() {
    return existingCheck;
  }
}
