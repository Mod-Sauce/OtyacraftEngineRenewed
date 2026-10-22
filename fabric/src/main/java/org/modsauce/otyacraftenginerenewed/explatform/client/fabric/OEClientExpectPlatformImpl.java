package org.modsauce.otyacraftenginerenewed.explatform.client.fabric;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.ItemLike;
import org.modsauce.otyacraftenginerenewed.client.renderer.item.BEWLItemRenderer;
import org.modsauce.otyacraftenginerenewed.client.util.OERenderUtils;

public class OEClientExpectPlatformImpl {

  private static final Minecraft mc = Minecraft.getInstance();

  public static InputConstants.Key getKey(KeyMapping key) {
    return KeyMappingHelper.getBoundKeyOf(key);
  }

  public static Object getModel(Identifier location) {
    return mc.getModelManager().getModel(location);
  }

  public static void registerItemRenderer(
    ItemLike item,
    BEWLItemRenderer renderer
  ) {
    BuiltinItemRendererRegistry.INSTANCE.register(
      item,
      (stack, mode, matrices, vertexConsumers, light, overlay) ->
        renderer.render(
          stack,
          mode,
          matrices,
          vertexConsumers,
          OERenderUtils.getPartialTicks(),
          light,
          overlay
        )
    );
  }
}
