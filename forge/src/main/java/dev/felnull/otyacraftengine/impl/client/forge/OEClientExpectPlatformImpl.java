package dev.felnull.otyacraftengine.impl.client.forge;

import com.mojang.blaze3d.platform.InputConstants;
import dev.felnull.otyacraftengine.client.renderer.item.BEWLItemRenderer;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.world.level.ItemLike;

public class OEClientExpectPlatformImpl {
    private static final Minecraft mc = Minecraft.getInstance();

    public static InputConstants.Key getKey(KeyMapping key) {
        return key.getKey();
    }

    public static void registerItemRenderer(ItemLike item, BEWLItemRenderer renderer) {
        //temRendererRegisterFG.register(item, renderer);
    }
}
