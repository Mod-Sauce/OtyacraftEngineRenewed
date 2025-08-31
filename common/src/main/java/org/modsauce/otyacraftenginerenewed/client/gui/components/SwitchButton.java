package org.modsauce.otyacraftenginerenewed.client.gui.components;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;
import org.modsauce.otyacraftenginerenewed.client.gui.TextureRegion;
import org.modsauce.otyacraftenginerenewed.client.gui.components.base.OEBasedButton;
import org.modsauce.otyacraftenginerenewed.client.util.OERenderUtils;

public class SwitchButton extends OEBasedButton {
  private final boolean showLabel;
  private boolean enable;

  public SwitchButton(int x, int y, @NotNull Component message, boolean showLabel) {
    this(x, y, message, TextureRegion.relative(OE_WIDGETS, 40, 0, 40, 34), showLabel);
  }

  public SwitchButton(int x, int y, @NotNull Component message, @NotNull TextureRegion texture, boolean showLabel) {
    super(x, y, 20, 10, message, "switchButton", texture);
    this.showLabel = showLabel;
  }

  @Override
  public void onPress() {
    this.enable = !this.enable;
  }


  @Override
  public void renderWidget(GuiGraphics guiGraphics, int mx, int my, float delta) {
    PoseStack poseStack = guiGraphics.pose();

    Minecraft minecraft = Minecraft.getInstance();
    RenderSystem.setShaderTexture(0, getTexture().location());
    RenderSystem.enableDepthTest();
    Font font = minecraft.font;
    RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, this.alpha);
    OERenderUtils.blitFloat(poseStack, getX(), getY(), getTexture().u0() + (this.isHoveredOrFocused() ? 20 : 0), getTexture().v0() + (this.enable ? 10 : 0), 20, 10, getTexture().width(), getTexture().height());
    OERenderUtils.blitFloat(poseStack, getX() + (this.enable ? (width - 8) : 0), getY() - 2, getTexture().u0() + (this.isHoveredOrFocused() ? 8 : 0), getTexture().v0() + 20, 8, 14, getTexture().width(), getTexture().height());

    RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
    if (this.showLabel) {
      //   drawString(poseStack, font, this.getMessage(), this.getX() + 24, this.getY() + (this.height - 8) / 2, 14737632 | Mth.ceil(this.alpha * 255.0F) << 24);
      guiGraphics.drawString(font, this.getMessage(), this.getX() + 24, this.getY() + (this.height - 8) / 2, 14737632 | Mth.ceil(this.alpha * 255.0F) << 24);
    }
  }

  public boolean isEnable() {
    return enable;
  }
}
