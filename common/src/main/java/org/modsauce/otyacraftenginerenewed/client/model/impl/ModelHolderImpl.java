package org.modsauce.otyacraftenginerenewed.client.model.impl;

import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;
import org.modsauce.otyacraftenginerenewed.client.callpoint.ModelRegister;
import org.modsauce.otyacraftenginerenewed.client.model.ModelHolder;

import java.util.Objects;

public class ModelHolderImpl implements ModelHolder {
  private final Identifier modelLocation;

  public ModelHolderImpl(Identifier modelLocation) {
    this.modelLocation = modelLocation;
  }

  @Override
  public @NotNull ModelHolder get() {
    return ModelHolder.create(modelLocation);
  }

  @Override
  public void registering(@NotNull ModelRegister register) {
    register.addModelLoad(modelLocation);
  }

  @Override
  public String toString() {
    return "ModelHolder{" + "location=" + modelLocation + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ModelHolderImpl that = (ModelHolderImpl) o;
    return Objects.equals(modelLocation, that.modelLocation);
  }

  @Override
  public int hashCode() {
    return Objects.hash(modelLocation);
  }
}
