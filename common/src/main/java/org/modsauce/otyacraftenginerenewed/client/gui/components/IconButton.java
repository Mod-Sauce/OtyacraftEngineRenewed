package org.modsauce.otyacraftenginerenewed.client.gui.components;

import java.util.function.Supplier;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ARGB;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.modsauce.otyacraftenginerenewed.client.gui.TextureRegion;
import org.modsauce.otyacraftenginerenewed.client.gui.components.base.OEBaseComponent;

public class IconButton extends Button implements OEBaseComponent {
    private static final WidgetSprites SPRITES = new WidgetSprites(Identifier.withDefaultNamespace("widget/button"), Identifier.withDefaultNamespace("widget/button_disabled"), Identifier.withDefaultNamespace("widget/button_highlighted"));

  @NotNull
  private TextureRegion texture;

  public IconButton(
    int x,
    int y,
    int width,
    int height,
    Component message,
    OnPress onPress,
    @NotNull TextureRegion texture
  ) {
    this(x, y, width, height, message, onPress, Supplier::get, texture);
  }

  public IconButton(
    int x,
    int y,
    int width,
    int height,
    Component message,
    OnPress onPress,
    CreateNarration createNarration,
    @NotNull TextureRegion texture
  ) {
    super(x, y, width, height, message, onPress, createNarration);
    this.texture = texture;
  }

  @Override
  public @NotNull TextureRegion getTexture() {
    return texture;
  }

  @Override
  public void setTexture(@NotNull TextureRegion texture) {
    this.texture = texture;
  }

  @Override
  public @Nullable String getWidgetTypeName() {
    return "iconButton";
  }

//  @Override
//   public void extractWidgetRenderState(GuiGraphicsExtractor guiGraphics, int i, int j, float f) {
//     /* RenderSystem.setShaderTexture(0, WIDGETS_LOCATION);
//         RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, this.alpha);
//         RenderSystem.enableBlend();
//         RenderSystem.enableDepthTest();
//         blitNineSliced(poseStack, this.getX(), this.getY(), this.getWidth(), this.getHeight(), 20, 4, 200, 20, 0, this.getTextureY());
//         RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);*/
//
//     guiGraphics.setColor(1.0F, 1.0F, 1.0F, this.alpha);
//     guiGraphics.blitSprite(SPRITES.get(this.active, this.isHoveredOrFocused()), this.getX(), this.getY(), this.getWidth(), this.getHeight());
//     guiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
//
//     renderIcon(guiGraphics, i, j, f);
//   }

  protected void renderIcon(
      @NotNull GuiGraphicsExtractor guiGraphics,
      int i,
      int j,
      float f
  ) {
      var tex = getTexture();

      guiGraphics.pose().pushMatrix();
      guiGraphics.pose().translate(
          getX() + (width - tex.uvWidth()) / 2f,   // keeps the fractional centering
          getY() + (height - tex.uvHeight()) / 2f
      );
      guiGraphics.blit(
          RenderPipelines.GUI_TEXTURED,
          tex.location(),
          0, 0,
          tex.u0(), tex.v0(),
          (int) tex.uvWidth(), (int) tex.uvHeight(),
          (int) tex.width(), (int) tex.height(),
          ARGB.white(this.alpha)
      );
      guiGraphics.pose().popMatrix();
  }

  @Override
  protected void extractContents(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float a) {
      guiGraphics.blitSprite(
          RenderPipelines.GUI_TEXTURED,
          SPRITES.get(this.active, this.isHoveredOrFocused()),
          this.getX(), this.getY(),
          this.getWidth(), this.getHeight(),
          ARGB.white(this.alpha)   // white tint with the widget's alpha
      );

      renderIcon(guiGraphics, mouseX, mouseY, a);
  }
}
