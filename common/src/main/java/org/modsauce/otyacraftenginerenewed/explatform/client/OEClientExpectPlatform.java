package org.modsauce.otyacraftenginerenewed.explatform.client;

import com.mojang.blaze3d.platform.InputConstants;
import dev.architectury.injectables.annotations.ExpectPlatform;
import org.modsauce.otyacraftenginerenewed.client.renderer.item.BEWLItemRenderer;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;

public class OEClientExpectPlatform {
    @ExpectPlatform
    public static InputConstants.Key getKey(KeyMapping key) {
        throw new AssertionError();
    }
    @ExpectPlatform
    public static BakedModel getModel(ResourceLocation location) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static float getPartialTicks() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static void registerItemRenderer(ItemLike item, BEWLItemRenderer renderer) {
        throw new AssertionError();
    }

}
