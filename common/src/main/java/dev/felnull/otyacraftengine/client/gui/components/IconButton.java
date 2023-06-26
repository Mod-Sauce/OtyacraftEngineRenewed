package dev.felnull.otyacraftengine.client.gui.components;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import dev.felnull.otyacraftengine.client.gui.TextureRegion;
import dev.felnull.otyacraftengine.client.gui.components.base.OEBaseComponent;
import dev.felnull.otyacraftengine.client.util.OERenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class IconButton extends Button implements OEBaseComponent {
    @NotNull
    private TextureRegion texture;

    public IconButton(int x, int y, int width, int height, Component message, OnPress onPress, @NotNull TextureRegion texture) {
        this(x, y, width, height, message, onPress, Supplier::get, texture);
    }

    public IconButton(int x, int y, int width, int height, Component message, OnPress onPress, CreateNarration createNarration, @NotNull TextureRegion texture) {
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

    @Override
    public void renderWidget(GuiGraphics guiGraphics, int i, int j, float f) {
       /* RenderSystem.setShaderTexture(0, WIDGETS_LOCATION);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, this.alpha);
        RenderSystem.enableBlend();
        RenderSystem.enableDepthTest();
        blitNineSliced(poseStack, this.getX(), this.getY(), this.getWidth(), this.getHeight(), 20, 4, 200, 20, 0, this.getTextureY());
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);*/

        guiGraphics.setColor(1.0F, 1.0F, 1.0F, this.alpha);
        guiGraphics.blitNineSliced(WIDGETS_LOCATION, this.getX(), this.getY(), this.getWidth(), this.getHeight(), 20, 4, 200, 20, 0, this.getTextureY());
        guiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);

        renderIcon(guiGraphics, i, j, f);
    }

    private int getTextureY() {
        int i = 1;
        if (!this.active) {
            i = 0;
        } else if (this.isHoveredOrFocused()) {
            i = 2;
        }

        return 46 + i * 20;
    }

    protected void renderIcon(@NotNull GuiGraphics guiGraphics, int i, int j, float f) {
        RenderSystem.setShaderTexture(0, getTexture().location());
        RenderSystem.enableDepthTest();
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, this.alpha);
        OERenderUtils.blitFloat(guiGraphics.pose(), getX() + (width - texture.uvWidth()) / 2f, getY() + (height - texture.uvHeight()) / 2f, getTexture().u0(), getTexture().v0(), getTexture().uvWidth(), getTexture().uvHeight(), getTexture().width(), getTexture().height());
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
    }
}
