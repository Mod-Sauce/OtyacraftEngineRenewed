package org.modsauce.otyacraftenginerenewed.neoforge.handler;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.EntityEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import org.modsauce.otyacraftenginerenewed.event.OECommonEventHooks;

public class CommonHandlerForge {

  @SubscribeEvent
  public static void onEntityConstructing(EntityEvent.EntityConstructing e) {
    OECommonEventHooks.onEntityDefineSynchedData(
      e.getEntity(),
      e.getEntity().getEntityData()
    );
  }

  @SubscribeEvent
  public static void onLivingTick(EntityTickEvent.Pre e) {
    if (
      e.getEntity() instanceof
        net.minecraft.world.entity.LivingEntity livingEntity
    ) {
      if (!OECommonEventHooks.onLivingEntityTick(livingEntity)) e.setCanceled(
        true
      );
    }
  }
}
