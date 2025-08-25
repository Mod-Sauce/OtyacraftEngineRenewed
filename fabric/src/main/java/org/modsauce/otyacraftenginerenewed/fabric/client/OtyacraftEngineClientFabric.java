package org.modsauce.otyacraftenginerenewed.fabric.client;

import org.modsauce.otyacraftenginerenewed.client.OtyacraftEngineClient;
import org.modsauce.otyacraftenginerenewed.fabric.client.handler.ClientHandlerFabric;
import org.modsauce.otyacraftenginerenewed.fabric.client.handler.ModelResourceHandler;
import net.fabricmc.api.ClientModInitializer;

public class OtyacraftEngineClientFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        OtyacraftEngineClient.init();
        ClientHandlerFabric.init();
        ModelResourceHandler.init();
    }
}
