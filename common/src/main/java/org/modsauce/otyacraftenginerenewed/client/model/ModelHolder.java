package org.modsauce.otyacraftenginerenewed.client.model;

import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;
import org.modsauce.otyacraftenginerenewed.client.callpoint.ModelRegister;
import org.modsauce.otyacraftenginerenewed.client.model.impl.ModelHolderImpl;

public interface ModelHolder {
  @NotNull
  static ModelHolder create(@NotNull Identifier location) {
    return new ModelHolderImpl(location);
  }

  @NotNull
  ModelHolder get();

  void registering(@NotNull ModelRegister register);
}
