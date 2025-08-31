package org.modsauce.otyacraftenginerenewed.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;
import org.modsauce.otyacraftenginerenewed.client.util.OERenderUtils;

public interface OEBaseGUI {
  Minecraft mc = Minecraft.getInstance();

  default void drawTextBase(@NotNull GuiGraphics guiGraphics, @NotNull Component text, int x, int y, int color) {
    /*GuiComponent.drawString(poseStack, mc.font, text, x, y, color);*/
    guiGraphics.drawString(mc.font, text, x, y, color);
  }

  default void drawTextBase(@NotNull GuiGraphics guiGraphics, @NotNull String text, int x, int y, int color) {
    //  GuiComponent.drawString(poseStack, mc.font, text, x, y, color);
    guiGraphics.drawString(mc.font, text, x, y, color);
  }

  default void drawTextBase(@NotNull GuiGraphics guiGraphics, @NotNull Component text, float x, float y, int color) {
    //   mc.font.drawShadow(poseStack, text, x, y, color);
    guiGraphics.drawString(mc.font, text, (int) x, (int) y, color);
  }

  default void drawTextBase(@NotNull GuiGraphics guiGraphics, @NotNull String text, float x, float y, int color) {
    //mc.font.drawShadow(poseStack, text, x, y, color);
    guiGraphics.drawString(mc.font, text, (int) x, (int) y, color, false);
  }

  default void drawRelativeFill(@NotNull GuiGraphics guiGraphics, int x, int y, int w, int h, int col) {
    // GuiComponent.fill(poseStack, x, y, x + w, y + h, col);
    guiGraphics.fill(x, y, x + w, y + h, col);
  }

  default void drawRelativeFill(@NotNull GuiGraphics guiGraphics, float x, float y, float w, float h, int col) {
    OERenderUtils.drawFill(guiGraphics.pose(), x, y, x + w, y + h, col);
  }
}
