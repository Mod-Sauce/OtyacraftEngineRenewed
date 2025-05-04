package dev.felnull.otyacraftengine.forge.handler;

import dev.felnull.otyacraftengine.event.OECommonEventHooks;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

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
