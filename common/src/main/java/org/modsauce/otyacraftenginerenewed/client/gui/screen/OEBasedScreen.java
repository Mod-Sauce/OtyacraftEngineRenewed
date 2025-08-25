package org.modsauce.otyacraftenginerenewed.client.gui.screen;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class OEBasedScreen extends Screen implements OEBaseScreen {
    @Nullable
    private final Screen parentScreen;

    protected OEBasedScreen(@NotNull Component message, @Nullable Screen parentScreen) {
        super(message);
        this.parentScreen = parentScreen;
    }

    protected OEBasedScreen(Component message) {
        this(message, null);
    }

    public @Nullable Screen getParentScreen() {
        return parentScreen;
    }

    @Override
    public void onClose() {
        if (parentScreen != null) {
            mc.setScreen(parentScreen);
        } else {
            super.onClose();
        }
    }
}
