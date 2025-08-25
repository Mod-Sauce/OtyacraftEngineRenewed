package org.modsauce.otyacraftenginerenewed.neoforge.client.handler;

import org.modsauce.otyacraftenginerenewed.client.callpoint.ClientCallPointManager;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.api.distmarker.Dist;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModelHandler {

    @SubscribeEvent
    public static void onModelRegistry(ModelEvent.RegisterAdditional e) {
        ClientCallPointManager.getInstance().call().onModelRegistry(e::register);
    }
}
