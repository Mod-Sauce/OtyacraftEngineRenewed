package org.modsauce.otyacraftenginerenewed.client.model;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface ModelBundle {
  @NotNull
  List<ModelHolder> getAllHolders();
}
