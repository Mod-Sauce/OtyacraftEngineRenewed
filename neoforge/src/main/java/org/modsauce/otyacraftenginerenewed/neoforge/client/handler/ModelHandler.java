package org.modsauce.otyacraftenginerenewed.neoforge.client.handler;

import org.modsauce.otyacraftenginerenewed.OtyacraftEngine;
import org.modsauce.otyacraftenginerenewed.client.callpoint.ClientCallPointManager;
import org.modsauce.otyacraftenginerenewed.client.callpoint.ModelRegister;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.api.distmarker.Dist;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.resources.model.ModelResourceLocation;

@EventBusSubscriber(modid = OtyacraftEngine.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModelHandler {

    @SubscribeEvent
    public static void onModelRegistry(ModelEvent.RegisterAdditional e) {
        ClientCallPointManager.getInstance().call().onModelRegistry(new ModelRegister() {
            @Override
            public void addModelLoad(ResourceLocation location) {
                ModelResourceLocation modelLocation = new ModelResourceLocation(location, "inventory");
                e.register(modelLocation);
            }
        });
    }
}
