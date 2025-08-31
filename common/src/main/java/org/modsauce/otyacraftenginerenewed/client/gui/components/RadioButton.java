package org.modsauce.otyacraftenginerenewed.client.gui.components;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.modsauce.otyacraftenginerenewed.client.gui.TextureRegion;
import org.modsauce.otyacraftenginerenewed.client.gui.components.base.OEBasedButton;
import org.modsauce.otyacraftenginerenewed.client.util.OERenderUtils;

import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class RadioButton extends OEBasedButton {
  @Nullable
  private final Consumer<RadioButton> onToggle;
  @NotNull
  private final Supplier<Set<RadioButton>> group;
  private boolean showLabel;
  private boolean selected;

  public RadioButton(int x, int y, @NotNull Component message, @Nullable Consumer<RadioButton> onPress, @NotNull Supplier<Set<RadioButton>> group, boolean showLabel) {
    this(x, y, 20, 20, message, onPress, group, showLabel, TextureRegion.relative(OE_WIDGETS, 0, 0, 20, 20));
  }

  public RadioButton(int x, int y, int width, int height, @NotNull Component message, @Nullable Consumer<RadioButton> onPress, @NotNull Supplier<Set<RadioButton>> group, boolean showLabel, @NotNull TextureRegion texture) {
    super(x, y, width, height, message, "radioButton", texture);
    this.onToggle = onPress;
    this.group = group;
    this.showLabel = showLabel;
  }

  @Override
  public void onPress() {
    this.selected = true;
    for (RadioButton rdo : group.get()) {
      if (this != rdo) rdo.selected = false;
    }
    if (onToggle != null) this.onToggle.accept(this);
  }

  public boolean isSelected() {
    return selected;
  }

  public void setSelected(boolean selected) {
    this.selected = selected;
  }

  @Override
  public void renderWidget(GuiGraphics guiGraphics, int i, int j, float f) {
    Minecraft minecraft = Minecraft.getInstance();
    RenderSystem.setShaderTexture(0, getTexture().location());
    RenderSystem.enableDepthTest();
    Font font = minecraft.font;
    RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, this.alpha);
    OERenderUtils.blitFloat(guiGraphics.pose(), getX(), getY(), getTexture().u0() + (this.isHoveredOrFocused() ? 20 : 0), getTexture().v0() + (this.selected ? 20 : 0), getTexture().uvWidth(), getTexture().uvHeight(), getTexture().width(), getTexture().height());

    RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
    if (this.showLabel) {
//            drawString(poseStack, font, this.getMessage(), this.getX() + 24, this.getY() + (this.height - 8) / 2, 14737632 | Mth.ceil(this.alpha * 255.0F) << 24);
      guiGraphics.drawString(font, this.getMessage(), this.getX() + 24, this.getY() + (this.height - 8) / 2, 14737632 | Mth.ceil(this.alpha * 255.0F) << 24);
    }

  }
}
