package org.modsauce.otyacraftenginerenewed.explatform.client;

import com.mojang.blaze3d.platform.InputConstants;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.ItemLike;
import org.modsauce.otyacraftenginerenewed.client.renderer.item.BEWLItemRenderer;

public class OEClientExpectPlatform {
  @ExpectPlatform
  public static InputConstants.Key getKey(KeyMapping key) {
    throw new AssertionError();
  }

  @ExpectPlatform
  public static BlockModel getModel(Identifier location) {
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
