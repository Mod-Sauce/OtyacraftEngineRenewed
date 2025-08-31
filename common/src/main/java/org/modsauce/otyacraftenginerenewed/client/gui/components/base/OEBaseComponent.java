package org.modsauce.otyacraftenginerenewed.client.gui.components.base;

import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.modsauce.otyacraftenginerenewed.OtyacraftEngine;
import org.modsauce.otyacraftenginerenewed.client.gui.OEBaseGUI;
import org.modsauce.otyacraftenginerenewed.client.gui.TextureRegion;


public interface OEBaseComponent extends OEBaseGUI {
  ResourceLocation OE_WIDGETS = ResourceLocation.fromNamespaceAndPath(OtyacraftEngine.MODID, "textures/gui/widgets.png");

  @NotNull
  TextureRegion getTexture();

  void setTexture(@NotNull TextureRegion texture);

  @Nullable
  String getWidgetTypeName();
}
