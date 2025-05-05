package dev.felnull.otyacraftengine.forge.client.handler;

import dev.felnull.otyacraftengine.client.event.OEClientEventHooks;
import net.neoforged.neoforge.client.event.RenderHandEvent;
import net.neoforged.bus.api.SubscribeEvent;

public class RenderHandlerForge {
    @SubscribeEvent
    public static void onRenderHand(RenderHandEvent e) {
        if (!OEClientEventHooks.onRenderHand(e.getPoseStack(), e.getMultiBufferSource(), e.getHand(), e.getPackedLight(), e.getPartialTick(), e.getInterpolatedPitch(), e.getSwingProgress(), e.getEquipProgress(), e.getItemStack()))
            e.setCanceled(true);
    }
}
