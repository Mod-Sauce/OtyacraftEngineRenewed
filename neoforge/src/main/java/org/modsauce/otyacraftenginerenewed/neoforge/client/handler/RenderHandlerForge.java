package org.modsauce.otyacraftenginerenewed.neoforge.client.handler;

import org.modsauce.otyacraftenginerenewed.client.event.OEClientEventHooks;
import net.neoforged.neoforge.client.event.RenderHandEvent;
import net.neoforged.bus.api.SubscribeEvent;

public class RenderHandlerForge {
    @SubscribeEvent
    public static void onRenderHand(RenderHandEvent e) {
        if (!OEClientEventHooks.onRenderHand(e.getPoseStack(), e.getMultiBufferSource(), e.getHand(), e.getPackedLight(), e.getPartialTick(), e.getInterpolatedPitch(), e.getSwingProgress(), e.getEquipProgress(), e.getItemStack()))
            e.setCanceled(true);
    }
}
