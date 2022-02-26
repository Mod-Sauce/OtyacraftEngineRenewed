package dev.felnull.otyacraftengine.impl.client.fabric;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.platform.NativeImage;
import dev.felnull.otyacraftengine.client.renderer.item.BEWLItemRenderer;
import dev.felnull.otyacraftengine.client.util.OERenderUtil;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.SubtitleOverlay;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.Tickable;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;

import java.util.List;

public class OEClientExpectPlatformImpl {
    private static final Minecraft mc = Minecraft.getInstance();

    public static InputConstants.Key getKey(KeyMapping key) {
        return key.key;
    }
    public static void registerItemRenderer(ItemLike item, BEWLItemRenderer renderer) {
        BuiltinItemRendererRegistry.INSTANCE.register(item, (stack, mode, matrices, vertexConsumers, light, overlay) -> renderer.render(stack, mode, matrices, vertexConsumers, OERenderUtil.getPartialTicks(), light, overlay));
    }
}
