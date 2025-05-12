package org.modsauce.otyacraftenginerenewed.explatform.client.fabric;

import com.mojang.blaze3d.platform.InputConstants;
import org.modsauce.otyacraftenginerenewed.client.renderer.item.BEWLItemRenderer;
import org.modsauce.otyacraftenginerenewed.client.util.OERenderUtils;
import org.modsauce.otyacraftenginerenewed.fabric.mixin.client.MinecraftAccessor;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.model.BakedModelManagerHelper;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;

public class OEClientExpectPlatformImpl {
    private static final Minecraft mc = Minecraft.getInstance();

    public static InputConstants.Key getKey(KeyMapping key) {
        return KeyBindingHelper.getBoundKeyOf(key);
    }

    public static BakedModel getModel(ResourceLocation location) {
        return BakedModelManagerHelper.getModel(mc.getModelManager(), location);
    }

    public static float getPartialTicks() {
        return mc.isPaused() ? ((MinecraftAccessor) mc).getPausePartialTick() : mc.getFrameTime();
    }

    public static void registerItemRenderer(ItemLike item, BEWLItemRenderer renderer) {
        BuiltinItemRendererRegistry.INSTANCE.register(item, (stack, mode, matrices, vertexConsumers, light, overlay) -> renderer.render(stack, mode, matrices, vertexConsumers, OERenderUtils.getPartialTicks(), light, overlay));
    }
}
