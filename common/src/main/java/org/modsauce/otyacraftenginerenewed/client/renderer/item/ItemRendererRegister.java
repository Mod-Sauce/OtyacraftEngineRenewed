package org.modsauce.otyacraftenginerenewed.client.renderer.item;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.world.level.ItemLike;
import org.modsauce.otyacraftenginerenewed.explatform.client.OEClientExpectPlatform;

public class ItemRendererRegister {
  public static void register(RegistrySupplier<? extends ItemLike> item, BEWLItemRenderer renderer) {
    OEClientExpectPlatform.registerItemRenderer(item.get(), renderer);
  }

  public static void register(ItemLike item, BEWLItemRenderer renderer) {
    OEClientExpectPlatform.registerItemRenderer(item, renderer);
  }
}
