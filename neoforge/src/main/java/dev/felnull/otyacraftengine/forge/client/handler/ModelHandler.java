package dev.felnull.otyacraftengine.forge.client.handler;

import dev.felnull.otyacraftengine.client.callpoint.ClientCallPointManager;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModelHandler {

    @SubscribeEvent
    public static void onModelRegistry(ModelEvent.RegisterAdditional e) {
        ClientCallPointManager.getInstance().call().onModelRegistry(e::register);
    }
}
