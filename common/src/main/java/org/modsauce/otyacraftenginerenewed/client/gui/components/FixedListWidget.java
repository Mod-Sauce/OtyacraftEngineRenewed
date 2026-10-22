package org.modsauce.otyacraftenginerenewed.client.gui.components;

import java.util.List;
import java.util.function.Function;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ARGB;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.glfw.GLFW;
import org.modsauce.otyacraftenginerenewed.client.gui.TextureRegion;
import org.modsauce.otyacraftenginerenewed.client.gui.components.base.OEBasedWidget;
import org.modsauce.otyacraftenginerenewed.client.util.OEClientUtils;

public abstract class FixedListWidget<E> extends OEBasedWidget {

    private static final Identifier BTN = Identifier.withDefaultNamespace("widget/button");
    private static final Identifier BTN_HOVER = Identifier.withDefaultNamespace("widget/button_highlighted");
    private static final Identifier BTN_OFF = Identifier.withDefaultNamespace("widget/button_disabled");


  private static final TextureRegion DEFAULT_TEXTURE = TextureRegion.relative(
    OE_WIDGETS,
    40,
    34,
    18,
    42
  );

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
  public FixedListWidget(
    int x,
    int y,
    int width,
    int height,
    @NotNull Component message,
    int entryShowCount,
    @NotNull List<E> entryList,
    @NotNull Function<E, Component> entryName,
    @Nullable PressEntry<E> onPressEntry,
    boolean selectable,
    @Nullable FixedListWidget<E> old
  ) {
    this(
      x,
      y,
      width,
      height,
      message,
      entryShowCount,
      entryList,
      entryName,
      onPressEntry,
      selectable,
      DEFAULT_TEXTURE,
      old
    );
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

  public FixedListWidget(
    int x,
    int y,
    int width,
    int height,
    @NotNull Component message,
    int entryShowCount,
    @NotNull List<E> entryList,
    @NotNull Function<E, Component> entryName,
    @Nullable PressEntry<E> onPressEntry,
    boolean selectable,
    @NotNull TextureRegion texture,
    @Nullable FixedListWidget<E> old
  ) {
    super(x, y, width, height, "fixedListWidget", message, texture);
    this.entryShowCount = entryShowCount;
    this.entryList = entryList;
    this.entryName = entryName;
    this.onPressEntry = onPressEntry;
    this.selectable = selectable;
    copyValue(old);
  }


  public void renderWidget(
    GuiGraphicsExtractor guiGraphics,
    int mx,
    int my,
    float parTick
  ) {
    if (this.visible) {
      this.hoveredNumber = (my - getY()) / getIndividualHeight();
      this.isHoveredScrollBar =
        mx >= this.getX() + getIndividualWidth() &&
        my >= this.getY() &&
        mx < this.getX() + this.width &&
        my < this.getY() + this.height;
    }

    for (int i = 0; i < entryShowCount; i++) {
      int cn = getCurrentFirstEntryIndex() + i;
      if (cn >= entryList.size() || cn < 0) break;
      var e = entryList.get(cn);
      renderOneButton(
        guiGraphics,
        e,
        cn,
        i,
        getX(),
        getY() + getIndividualHeight() * i,
        mx,
        my,
        parTick,
        selectedEntry == entryList.get(cn)
      );
    }
    renderScrollbar(
      guiGraphics,
      this.getX() + getIndividualWidth(),
      this.getY(),
      9,
      height
    );
  }

  private void piece(GuiGraphicsExtractor g, int x, int y, int u, int v, int w, int h) {
    if (w <= 0 || h <= 0) return;
    var t = getTexture();
    g.blit(RenderPipelines.GUI_TEXTURED, t.location(), x, y, (float) u, (float) v, w, h,
        (int) t.width(), (int) t.height(), ARGB.white(this.alpha));
  }

  protected void renderScrollbar(GuiGraphicsExtractor guiGraphics, int x, int y, int w, int h) {
    boolean hv = isScrollBarHovered() || isFocused();
    int u = (int) getTexture().u0();
    int v = (int) getTexture().v0();
    int tu = u + (hv ? 9 : 0);   // track
    int bu = u + (hv ? 7 : 0);   // thumb

    // Track: top cap, repeated middle, remainder, bottom cap
    piece(guiGraphics, x, y, tu, v, 9, 3);
    int bsct = (height - 6) / 16;
    for (int i = 0; i < bsct; i++) piece(guiGraphics, x, y + 3 + i * 16, tu, v + 3, 9, 16);
    piece(guiGraphics, x, y + 3 + bsct * 16, tu, v + 3, 9, (height - 6) % 16);
    piece(guiGraphics, x, y + height - 3, tu, v + 19, 9, 3);

    // Thumb
    int barHeight = getBarHeight();
    int barY = (int) ((getActualHeight() - barHeight) * scrollAmount);
    piece(guiGraphics, x + 1, y + 1 + barY, bu, v + 22, 7, 3);
    int ssct = (barHeight - 6) / 14;
    for (int i = 0; i < ssct; i++) piece(guiGraphics, x + 1, y + 4 + i * 14 + barY, bu, v + 25, 7, 14);
    piece(guiGraphics, x + 1, y + 4 + ssct * 14 + barY, bu, v + 25, 7, (barHeight - 6) % 14);
    piece(guiGraphics, x + 1, y + 1 + barHeight - 3 + barY, bu, v + 39, 7, 3);
  }


  protected void renderOneButton(GuiGraphicsExtractor guiGraphics, E item, int lnum, int bnum,
      int bX, int bY, int mx, int my, float parTick, boolean selected) {
    Identifier sprite = !this.active ? BTN_OFF : (this.isEntryHovered(bnum) ? BTN_HOVER : BTN);
    guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, sprite, bX, bY,
        this.getIndividualWidth(), this.getIndividualHeight(), ARGB.white(this.alpha));

    int k = this.active ? 16777215 : 10526880;
    this.renderString(guiGraphics, this.getMessage(lnum), k | (Mth.ceil(this.alpha * 255.0F) << 24), bX, bY);
  }

  public void renderString(
    GuiGraphicsExtractor guiGraphics,
    Component message,
    int i,
    int x,
    int y
  ) {
    this.renderScrollingString(guiGraphics, message, 2, i, x, y);
  }

  protected void renderScrollingString(
      GuiGraphicsExtractor guiGraphics, Component message, int i, int j, int x, int y) {
    int minX = x + i;
    int maxX = x + this.getIndividualWidth() - i;
    int minY = y;
    int maxY = y + this.getIndividualHeight();

    var font = mc.font;
    int available = maxX - minX;
    int textY = (minY + maxY - font.lineHeight) / 2 + 1;

    if (font.width(message) <= available) {
      // fits: center it
      int textX = minX + (available - font.width(message)) / 2;
      guiGraphics.text(font, message, textX, textY, j);
    } else {
      // too long: truncate with an ellipsis
      FormattedText cut = font.substrByWidth(message, available - font.width("..."));
      guiGraphics.text(font, Language.getInstance().getVisualOrder(
          FormattedText.composite(cut, FormattedText.of("..."))), minX, textY, j);
    }
  }

  @Override
  public void onFocusedClick() {}

  @Override
  public void playDownSound(@NotNull SoundManager soundManager) {
    boolean flg = isEntryHovered(hoveredNumber);
    boolean nflg = entryList.size() > hoveredNumber;
    if (!(flg && !nflg)) super.playDownSound(soundManager);
  }

  @Override
  public void onClick(final MouseButtonEvent event, final boolean doubleClick) {
    super.onClick(event, doubleClick);
  }


  public boolean mouseDragged(final MouseButtonEvent event, final double dx, final double dy) {
    scroll(dy);
    return super.mouseDragged(event, dx, dy);
  }

  @Override
  public boolean mouseClicked(final MouseButtonEvent event, final boolean doubleClick) {
    canScroll = isScrollBarHovered();
    // scroll(my);
    return super.mouseClicked(event, doubleClick);
  }

  public boolean mouseScrolled(double d, double e, double f, double g) {
    this.setScrollAmount(this.getScrollAmount() - getMouseScrollAmount(g));
    return true;
  }

  @Override
  public boolean keyPressed(int i, int j, int k) {
    if (this.active && this.visible && isHoveredOrFocused()) {
      if (i == GLFW.GLFW_KEY_UP) {
        this.setScrollAmount(
          this.getScrollAmount() -
            0.3f * ((float) entryShowCount / (float) height)
        );
        return true;
      } else if (i == GLFW.GLFW_KEY_DOWN) {
        this.setScrollAmount(
          this.getScrollAmount() +
            0.3f * ((float) entryShowCount / (float) height)
        );
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
      if (onPressEntry != null) onPressEntry.onPressEntry(this, e);
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
    if (
      selectedEntry == null ||
      entryList.stream().anyMatch(n -> n == selectedEntry)
    ) {
      this.selectedEntry = selectedEntry;
      if (selectedEntry == null) selectedEntryIndex = -1;
      else selectedEntryIndex = entryList.indexOf(selectedEntry);
    }
  }

  public @Nullable E getSelectedEntry() {
    if (
      entryList.stream().anyMatch(n -> n == selectedEntry)
    ) return selectedEntry;
    return null;
  }

  public int getSelectedEntryIndex() {
    if (getSelectedEntry() == null) return -1;
    return selectedEntryIndex;
  }

  protected int getCurrentFirstEntryIndex() {
    if (entryList.size() <= entryShowCount) return 0;
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
    return Mth.clamp(
      (int) (getActualHeight() /
        (((float) entryList.size() / (float) entryShowCount))),
      10,
      getActualHeight()
    );
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

  public @NotNull List<E> getEntryList() {
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
    float am =
      (float) getIndividualHeight() /
      ((float) getIndividualHeight() *
        (float) Math.max((entryList.size() - entryShowCount), 1));

    float m = 1;
    if (OEClientUtils.isKeyInput(mc.options.keyShift)) m *= 3;
    if (OEClientUtils.isKeyInput(mc.options.keySprint)) m *= 10;

    return (float) mouseAmount * am * m;
  }

  public static interface PressEntry<E> {
    void onPressEntry(FixedListWidget<E> widget, E item);
  }
}
