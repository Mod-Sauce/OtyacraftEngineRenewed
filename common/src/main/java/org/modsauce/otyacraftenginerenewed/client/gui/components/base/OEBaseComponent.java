package org.modsauce.otyacraftenginerenewed.client.gui.components.base;

import org.modsauce.otyacraftenginerenewed.OtyacraftEngine;
import org.modsauce.otyacraftenginerenewed.client.gui.TextureRegion;
import org.modsauce.otyacraftenginerenewed.client.gui.OEBaseGUI;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public interface OEBaseComponent extends OEBaseGUI {
    ResourceLocation OE_WIDGETS = ResourceLocation.fromNamespaceAndPath(OtyacraftEngine.MODID, "textures/gui/widgets.png");

    @NotNull
    TextureRegion getTexture();

    void setTexture(@NotNull TextureRegion texture);

    @Nullable
    String getWidgetTypeName();
}
