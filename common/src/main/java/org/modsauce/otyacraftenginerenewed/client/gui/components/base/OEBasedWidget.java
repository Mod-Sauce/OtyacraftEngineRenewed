package org.modsauce.otyacraftenginerenewed.client.gui.components.base;

import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.modsauce.otyacraftenginerenewed.client.gui.TextureRegion;

public abstract class OEBasedWidget extends AbstractWidget implements OEBaseComponent {
  @Nullable
  private final String widgetTypeName;
  @NotNull
  private TextureRegion texture;

  public OEBasedWidget(int x, int y, int width, int height, @NotNull Component message, @NotNull TextureRegion texture) {
    this(x, y, width, height, null, message, texture);
  }

  public OEBasedWidget(int x, int y, int width, int height, @Nullable String widgetTypeName, @NotNull Component message, @NotNull TextureRegion texture) {
    super(x, y, width, height, message);
    this.widgetTypeName = widgetTypeName;
    this.texture = texture;
  }

  @Override
  protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {
    this.defaultButtonNarrationText(narrationElementOutput);
  }

  @Override
  protected void defaultButtonNarrationText(NarrationElementOutput narrationElementOutput) {
    if (this.widgetTypeName == null) {
      super.defaultButtonNarrationText(narrationElementOutput);
      return;
    }

    narrationElementOutput.add(NarratedElementType.TITLE, this.createNarrationMessage());
    if (this.active) {
      if (this.isFocused()) {
        narrationElementOutput.add(NarratedElementType.USAGE, Component.translatable("narration." + widgetTypeName + ".usage.focused"));
      } else {
        narrationElementOutput.add(NarratedElementType.USAGE, Component.translatable("narration." + widgetTypeName + ".usage.hovered"));
      }
    }
  }

  @Override
  protected MutableComponent createNarrationMessage() {
    if (widgetTypeName == null)
      return super.createNarrationMessage();
    return Component.translatable("gui.narrate." + widgetTypeName, getMessage());
  }

  @Override
  public void onClick(double d, double e) {
    this.onFocusedClick();
  }

  @Override
  public boolean keyPressed(int i, int j, int k) {
    if (this.active && this.visible) {
      if (i != 257 && i != 32 && i != 335) return false;
      this.playDownSound(mc.getSoundManager());
      this.onFocusedClick();
      return true;
    }
    return false;
  }

  @Override
  public @Nullable String getWidgetTypeName() {
    return widgetTypeName;
  }

  public abstract void onFocusedClick();

  @NotNull
  @Override
  public TextureRegion getTexture() {
    return texture;
  }

  @Override
  public void setTexture(@NotNull TextureRegion texture) {
    this.texture = texture;
  }
}
