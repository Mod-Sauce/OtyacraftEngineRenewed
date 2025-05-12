package org.modsauce.otyacraftenginerenewed.forge.client.handler;

import org.modsauce.otyacraftenginerenewed.client.callpoint.ClientCallPointManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModelHandler {

    @SubscribeEvent
    public static void onModelRegistry(ModelEvent.RegisterAdditional e) {
        ClientCallPointManager.getInstance().call().onModelRegistry(e::register);
    }
}
