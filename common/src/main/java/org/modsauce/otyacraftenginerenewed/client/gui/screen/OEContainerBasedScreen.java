package org.modsauce.otyacraftenginerenewed.client.gui.screen;

import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import org.modsauce.otyacraftenginerenewed.inventory.OEBaseMenu;

public abstract class OEContainerBasedScreen<T extends OEBaseMenu>
  extends AbstractContainerScreen<T>
  implements InstructionScreen, OEBaseScreen {

  protected static final Minecraft mc = Minecraft.getInstance();
  protected final UUID id = UUID.randomUUID();
  protected int bgTextureWidth = 256;
  protected int bgTextureHeight = 256;

  public OEContainerBasedScreen(
    T abstractContainerMenu,
    Inventory inventory,
    Component component
  ) {
    super(abstractContainerMenu, inventory, component);
  }

  @Override
  public void extractRenderState(GuiGraphicsExtractor guiGraphics, int i, int j, float f) {
    this.renderBg(guiGraphics, f, i, j);
    super.extractRenderState(guiGraphics, i, j, f);
    this.extractTooltip(guiGraphics, i, j);
  }

  protected void renderBg(GuiGraphicsExtractor guiGraphics, float f, int i, int j) {
    if (getBackGrandTexture() != null) {
      /* RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.setShaderTexture(0, getBackGrandTexture());
          blit(poseStack, leftPos, topPos, 0, 0, this.imageWidth, this.imageHeight, bgTextureWidth, bgTextureHeight);*/
      guiGraphics.blit(
        getBackGrandTexture(),
        leftPos,
        topPos,
        0,
        0,
        this.imageWidth,
        this.imageHeight,
        bgTextureWidth,
        bgTextureHeight
      );
    }
  }

  protected abstract Identifier getBackGrandTexture();

  @Override
  public boolean mouseDragged(MouseButtonEvent event, double dx, double dy) {
    boolean flag1 = super.mouseDragged(event, dx, dy);
    boolean flag2 =
      this.getFocused() != null &&
      this.isDragging() &&
      event.button() == 0 &&
      this.getFocused().mouseDragged(event, dx, dy);
    return flag1 & flag2;
  }

  @Override
  protected void containerTick() {
    super.containerTick();
    for (GuiEventListener child : children()) {
      if (child instanceof EditBox) {
        // tick() method was removed in MC 1.21.1
        // EditBox now handles its own ticking internally
      }
    }
  }

  @Override
  public boolean keyPressed(final KeyEvent event) {
    if (event.key() == 256) mc.player.closeContainer();

    for (GuiEventListener child : children()) {
      if (child instanceof EditBox editBox) {
        if (
          editBox.keyPressed(event) || editBox.canConsumeInput()
        ) return true;
      }
    }

    return super.keyPressed(event);
  }

  @Override
  public void onInstructionReturn(String name, CompoundTag data) {}

  @Override
  public UUID getInstructionID() {
    return id;
  }
}
