package dev.felnull.otyacraftengine.client.gui.components;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.felnull.otyacraftengine.client.gui.TextureRegion;
import dev.felnull.otyacraftengine.client.gui.components.base.OEBasedWidget;
import dev.felnull.otyacraftengine.client.util.OEClientUtils;
import dev.felnull.otyacraftengine.client.util.OERenderUtils;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.glfw.GLFW;

import java.util.List;
import java.util.function.Function;

public abstract class FixedListWidget<E> extends OEBasedWidget {
    private static final TextureRegion DEFAULT_TEXTURE = TextureRegion.relative(OE_WIDGETS, 40, 34, 18, 42);
    @NotNull
    private List<E> entryList;
    @NotNull
    private final Function<E, Component> entryName;
    @Nullable
    private final PressEntry<E> onPressEntry;
    private final boolean selectable;
    private final int entryShowCount;
    private boolean border;
    private float scrollAmount;
    private boolean canScroll;
    protected boolean isHoveredScrollBar;
    protected int hoveredNumber;
    @Nullable
    protected E selectedEntry;
    protected int selectedEntryIndex = -1;


    /**
     * @param x              X座標
     * @param y              Y座標
     * @param width          横幅
     * @param height         高さ
     * @param message        メッセージ
     * @param entryShowCount エントリーを表示数
     * @param entryList      エントリーのリスト
     * @param entryName      エントリーから名前を取得
     * @param onPressEntry   エントリーを押下
     * @param selectable     選択を有効にするかどうか
     * @param old            コピー用の古い値
     */
    public FixedListWidget(int x, int y, int width, int height, @NotNull Component message, int entryShowCount, @NotNull List<E> entryList, @NotNull Function<E, Component> entryName, @Nullable PressEntry<E> onPressEntry, boolean selectable, @Nullable FixedListWidget<E> old) {
        this(x, y, width, height, message, entryShowCount, entryList, entryName, onPressEntry, selectable, DEFAULT_TEXTURE, old);
    }


    /**
     * @param x              X座標
     * @param y              Y座標
     * @param width          横幅
     * @param height         高さ
     * @param message        メッセージ
     * @param entryShowCount エントリーを表示数
     * @param entryList      エントリーのリスト
     * @param entryName      エントリーから名前を取得
     * @param onPressEntry   エントリーを押下
     * @param selectable     選択を有効にするかどうか
     * @param texture        テクスチャ
     * @param old            コピー用の古い値
     */

    public FixedListWidget(int x, int y, int width, int height, @NotNull Component message, int entryShowCount, @NotNull List<E> entryList, @NotNull Function<E, Component> entryName, @Nullable PressEntry<E> onPressEntry, boolean selectable, @NotNull TextureRegion texture, @Nullable FixedListWidget<E> old) {
        super(x, y, width, height, "fixedListWidget", message, texture);
        this.entryShowCount = entryShowCount;
        this.entryList = entryList;
        this.entryName = entryName;
        this.onPressEntry = onPressEntry;
        this.selectable = selectable;
        copyValue(old);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mx, int my, float parTick) {
        if (this.visible) {
            this.hoveredNumber = (my - getY()) / getIndividualHeight();
            this.isHoveredScrollBar = mx >= this.getX() + getIndividualWidth() && my >= this.getY() && mx < this.getX() + this.width && my < this.getY() + this.height;
        }
        super.render(guiGraphics, mx, my, parTick);
    }

    @Override
    public void renderWidget(GuiGraphics guiGraphics, int mx, int my, float parTick) {
        for (int i = 0; i < entryShowCount; i++) {
            int cn = getCurrentFirstEntryIndex() + i;
            if (cn >= entryList.size() || cn < 0)
                break;
            var e = entryList.get(cn);
            renderOneButton(guiGraphics, e, cn, i, getX(), getY() + getIndividualHeight() * i, mx, my, parTick, selectedEntry == entryList.get(cn));
        }
        renderScrollbar(guiGraphics, this.getX() + getIndividualWidth(), this.getY(), 9, height);
    }

    protected void renderScrollbar(GuiGraphics guiGraphics, int x, int y, int w, int h) {
        PoseStack poseStack = guiGraphics.pose();
        boolean hv = isScrollBarHovered() || isFocused();

        OERenderUtils.drawTexture(getTexture().location(), poseStack, x, y, getTexture().u0() + (hv ? 9 : 0), getTexture().v0(), 9, 3, getTexture().width(), getTexture().height());
        int bsct = (height - 6) / 16;
        for (int i = 0; i < bsct; i++) {
            OERenderUtils.drawTexture(getTexture().location(), poseStack, x, y + 3 + (i * 16), getTexture().u0() + (hv ? 9 : 0), getTexture().v0() + 3, 9, 16, getTexture().width(), getTexture().height());
        }
        int bsam = (height - 6) % 16;
        OERenderUtils.drawTexture(getTexture().location(), poseStack, x, y + 3 + (bsct * 16), getTexture().u0() + (hv ? 9 : 0), getTexture().v0() + 3, 9, bsam, getTexture().width(), getTexture().height());
        OERenderUtils.drawTexture(getTexture().location(), poseStack, x, y + height - 3, getTexture().u0() + (hv ? 9 : 0), getTexture().v0() + 19, 9, 3, getTexture().width(), getTexture().height());

        int barHeight = getBarHeight();
        float barY = (getActualHeight() - barHeight) * scrollAmount;

        OERenderUtils.drawTexture(getTexture().location(), poseStack, x + 1, y + 1 + barY, getTexture().u0() + (hv ? 7 : 0), getTexture().v0() + 22, 7, 3, getTexture().width(), getTexture().height());
        int ssct = (barHeight - 6) / 14;
        for (int i = 0; i < ssct; i++) {
            OERenderUtils.drawTexture(getTexture().location(), poseStack, x + 1, y + 4 + (i * 14) + barY, getTexture().u0() + (hv ? 7 : 0), getTexture().v0() + 25, 7, 14, getTexture().width(), getTexture().height());
        }
        int ssam = (barHeight - 6) % 14;
        OERenderUtils.drawTexture(getTexture().location(), poseStack, x + 1, y + 4 + (ssct * 14) + barY, getTexture().u0() + (hv ? 7 : 0), getTexture().v0() + 25, 7, ssam, getTexture().width(), getTexture().height());
        OERenderUtils.drawTexture(getTexture().location(), poseStack, x + 1, y + 1 + barHeight - 3 + barY, getTexture().u0() + (hv ? 7 : 0), getTexture().v0() + 39, 7, 3, getTexture().width(), getTexture().height());
    }

    protected void renderOneButton(GuiGraphics guiGraphics, E item, int lnum, int bnum, int bX, int bY, int mx, int my, float parTick, boolean selected) {
      /*  RenderSystem.setShaderTexture(0, WIDGETS_LOCATION);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, this.alpha);
        RenderSystem.enableBlend();
        RenderSystem.enableDepthTest();
        blitNineSliced(poseStack, bX, bY, this.getIndividualWidth(), this.getIndividualHeight(), 20, 4, 200, 20, 0, this.getTextureY(this.isEntryHovered(bnum)));*/

        guiGraphics.setColor(1.0F, 1.0F, 1.0F, this.alpha);
        //Fix me
        guiGraphics.blitNineSliced(WIDGETS_LOCATION, bX, bY, this.getIndividualWidth(), this.getIndividualHeight(), 20, 4, 200, 20, 0, this.getTextureY(this.isEntryHovered(bnum)));
        guiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);

        /* RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);*/
        int k = this.active ? 16777215 : 10526880;
        this.renderString(guiGraphics, this.getMessage(lnum), k | Mth.ceil(this.alpha * 255.0F) << 24, bX, bY);

        //  RenderSystem.setShader(GameRenderer::getPositionTexShader);
     /*   RenderSystem.setShaderTexture(0, WIDGETS_LOCATION);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, this.alpha);
        int k = this.getYImage(this.isEntryHovered(bnum));
        if (selected) k = 0;
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();
        blit(poseStack, bX, bY, 0, 46 + k * 20, getIndividualWidth() / 2, getIndividualHeight());
        blit(poseStack, bX + getIndividualWidth() / 2, bY, 200 - getIndividualWidth() / 2, 46 + k * 20, getIndividualWidth() / 2, getIndividualHeight());
        this.renderBg(poseStack, mc, mx, my);

        int l = this.active ? 16777215 : 10526880;
        drawCenteredString(poseStack, mc.font, this.getMessage(lnum), this.getX() + getIndividualWidth() / 2, bY + (getIndividualHeight() - 8) / 2, l | Mth.ceil(this.alpha * 255.0F) << 24);*/
        // drawCenteredString(poseStack, mc.font, this.getMessage(lnum), this.x + getIndividualWidth() / 2, y + (getIndividualHeight() - 8) / 2, l | Mth.ceil(this.alpha * 255.0F) << 24);
    }

    public void renderString(GuiGraphics guiGraphics, Component message, int i, int x, int y) {
        this.renderScrollingString(guiGraphics, message, 2, i, x, y);
    }

    protected void renderScrollingString(GuiGraphics guiGraphics, Component message, int i, int j, int x, int y) {
        int k = x + i;
        int l = x + this.getIndividualWidth() - i;
        renderScrollingString(guiGraphics, mc.font, message, k, y, l, y + this.getIndividualHeight(), j);
    }

    private int getTextureY(boolean hoverd) {
        int i = 1;
        if (!this.active) {
            i = 0;
        } else if (hoverd) {
            i = 2;
        }

        return 46 + i * 20;
    }

    @Override
    public void onFocusedClick() {

    }

    @Override
    public void playDownSound(@NotNull SoundManager soundManager) {
        boolean flg = isEntryHovered(hoveredNumber);
        boolean nflg = entryList.size() > hoveredNumber;
        if (!(flg && !nflg))
            super.playDownSound(soundManager);
    }

    @Override
    public void onClick(double mx, double my) {
        onPress((int) ((my - getY()) / getIndividualHeight()));
        super.onClick(mx, my);
    }

    @Override
    public boolean mouseDragged(double mx, double my, int i, double f, double g) {
        scroll(my);
        return super.mouseDragged(mx, my, i, f, g);
    }

    @Override
    public boolean mouseClicked(double mx, double my, int i) {
        canScroll = isScrollBarHovered();
        scroll(my);
        return super.mouseClicked(mx, my, i);
    }

    @Override
    public boolean mouseScrolled(double d, double e, double f) {
        this.setScrollAmount(this.getScrollAmount() - getMouseScrollAmount(f));
        return true;
    }

    @Override
    public boolean keyPressed(int i, int j, int k) {
        if (this.active && this.visible && isHoveredOrFocused()) {
            if (i == GLFW.GLFW_KEY_UP) {
                this.setScrollAmount(this.getScrollAmount() - 0.3f * ((float) entryShowCount / (float) height));
                return true;
            } else if (i == GLFW.GLFW_KEY_DOWN) {
                this.setScrollAmount(this.getScrollAmount() + 0.3f * ((float) entryShowCount / (float) height));
                return true;
            }
        }
        return false;
    }

    public void onPress(int num) {
        int cn = getCurrentFirstEntryIndex() + num;
        if (cn < entryList.size() && isEntryHovered(num)) {
            var e = entryList.get(cn);
            if (selectable) {
                selectedEntry = e;
                selectedEntryIndex = cn;
            }
            if (onPressEntry != null)
                onPressEntry.onPressEntry(this, e);
        }
    }

    public void setSelectedEntry(int index) {
        if (index >= 0 && index < entryList.size()) {
            this.selectedEntry = entryList.get(index);
            this.selectedEntryIndex = index;
        } else {
            this.selectedEntry = null;
            this.selectedEntryIndex = -1;
        }
    }

    public void setSelectedEntry(@Nullable E selectedEntry) {
        if (selectedEntry == null || entryList.stream().anyMatch(n -> n == selectedEntry)) {
            this.selectedEntry = selectedEntry;
            if (selectedEntry == null)
                selectedEntryIndex = -1;
            else
                selectedEntryIndex = entryList.indexOf(selectedEntry);
        }
    }

    public @Nullable
    E getSelectedEntry() {
        if (entryList.stream().anyMatch(n -> n == selectedEntry))
            return selectedEntry;
        return null;
    }

    public int getSelectedEntryIndex() {
        if (getSelectedEntry() == null) return -1;
        return selectedEntryIndex;
    }

    protected int getCurrentFirstEntryIndex() {
        if (entryList.size() <= entryShowCount)
            return 0;
        return (int) ((entryList.size() - entryShowCount) * scrollAmount);
    }

    public boolean isEntryHovered(int lnum) {
        return this.isHovered && hoveredNumber == lnum && !isHoveredScrollBar;
    }

    public boolean isScrollBarHovered() {
        return this.isHovered && this.isHoveredScrollBar;
    }

    public Component getMessage(int index) {
        return entryName.apply(entryList.get(index));
    }

    public void scroll(double mouseY) {
        if (canScroll) {
            int cy = (int) (mouseY - this.getY() - 1 - getBarHeight() / 2);
            int sa = getActualHeight() - getBarHeight();
            if (sa > 0) {
                setScrollAmount((float) cy / (float) sa);
            }
        }
    }

    public int getBarHeight() {
        return Mth.clamp((int) (getActualHeight() / (((float) entryList.size() / (float) entryShowCount))), 10, getActualHeight());
    }

    public float getScrollAmount() {
        return scrollAmount;
    }

    public void setScrollAmount(float scrollAmount) {
        this.scrollAmount = Mth.clamp(scrollAmount, 0f, 1f);
    }

    public int getActualHeight() {
        return height - 2;
    }

    public int getIndividualHeight() {
        return height / entryShowCount;
    }

    public int getIndividualWidth() {
        return width - (9 + (border ? 0 : 1));
    }

    public @NotNull
    List<E> getEntryList() {
        return entryList;
    }

    public void setEntryList(@NotNull List<E> entryList) {
        this.entryList = entryList;
    }

    public boolean isBorder() {
        return border;
    }

    public void setBorder(boolean border) {
        this.border = border;
    }

    public void copyValue(@Nullable FixedListWidget<E> copyValue) {
        if (copyValue != null) {
            this.selectedEntry = copyValue.selectedEntry;
            this.scrollAmount = copyValue.scrollAmount;
            this.selectedEntryIndex = copyValue.selectedEntryIndex;
        }
    }

    public float getMouseScrollAmount(double mouseAmount) {
        float am = (float) getIndividualHeight() / ((float) getIndividualHeight() * (float) Math.max((entryList.size() - entryShowCount), 1));

        float m = 1;
        if (OEClientUtils.isKeyInput(mc.options.keyShift))
            m *= 3;
        if (OEClientUtils.isKeyInput(mc.options.keySprint))
            m *= 10;

        return (float) mouseAmount * am * m;
    }

    public static interface PressEntry<E> {
        void onPressEntry(FixedListWidget<E> widget, E item);
    }
}

