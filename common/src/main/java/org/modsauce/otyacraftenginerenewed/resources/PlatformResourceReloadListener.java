package org.modsauce.otyacraftenginerenewed.resources;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import org.jetbrains.annotations.NotNull;

public abstract class PlatformResourceReloadListener<T> extends SimplePreparableReloadListener<T> {
  @NotNull
  public abstract ResourceLocation getId();
}
