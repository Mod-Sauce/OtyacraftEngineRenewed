package dev.felnull.otyacraftengine.explatform.client.forge;

import com.mojang.blaze3d.platform.InputConstants;
import dev.felnull.otyacraftengine.client.renderer.item.BEWLItemRenderer;
import dev.felnull.otyacraftengine.forge.client.renderer.item.ItemRendererRegisterForge;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;

public class OEClientExpectPlatformImpl {
    private static final Minecraft mc = Minecraft.getInstance();

    public static InputConstants.Key getKey(KeyMapping key) {
        return key.getKey();
    }

    public static BakedModel getModel(ResourceLocation location) {
        return mc.getModelManager().getModel(location);
    }

    public static float getPartialTicks() {
        return mc.getPartialTick();
    }

    public static void registerItemRenderer(ItemLike item, BEWLItemRenderer renderer) {
        ItemRendererRegisterForge.register(item, renderer);
    }
}
