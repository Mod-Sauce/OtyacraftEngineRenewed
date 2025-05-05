package dev.felnull.otyacraftengine.forge.handler;

import dev.felnull.otyacraftengine.event.OECommonEventHooks;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.EntityEvent;
import net.neoforged.neoforge.event.entity.living.LivingEvent;

public class CommonHandlerForge {
    @SubscribeEvent
    public static void onEntityConstructing(EntityEvent.EntityConstructing e) {
        OECommonEventHooks.onEntityDefineSynchedData(e.getEntity(), e.getEntity().getEntityData());
    }

    @SubscribeEvent
    public static void onLivingTick(LivingEvent.LivingTickEvent e) {
        if (!OECommonEventHooks.onLivingEntityTick(e.getEntity()))
            e.setCanceled(true);
    }
}
