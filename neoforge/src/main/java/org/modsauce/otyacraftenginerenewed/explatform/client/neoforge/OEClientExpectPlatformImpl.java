package org.modsauce.otyacraftenginerenewed.explatform.client.neoforge;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;
import org.modsauce.otyacraftenginerenewed.client.renderer.item.BEWLItemRenderer;
import org.modsauce.otyacraftenginerenewed.neoforge.client.renderer.item.ItemRendererRegisterForge;

public class OEClientExpectPlatformImpl {

  private static final Minecraft mc = Minecraft.getInstance();

  public static InputConstants.Key getKey(KeyMapping key) {
    return key.getKey();
  }

  public static BakedModel getModel(ResourceLocation location) {
    ModelResourceLocation modelLocation = new ModelResourceLocation(location, "standalone");
    return mc.getModelManager().getModel(modelLocation);
  }

  public static float getPartialTicks() {
    return mc.getTimer().getGameTimeDeltaPartialTick(mc.isPaused());
  }

  public static void registerItemRenderer(
    ItemLike item,
    BEWLItemRenderer renderer
  ) {
    ItemRendererRegisterForge.register(item, renderer);
  }
}
