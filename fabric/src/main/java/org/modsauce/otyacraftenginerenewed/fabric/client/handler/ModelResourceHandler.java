package org.modsauce.otyacraftenginerenewed.fabric.client.handler;

import org.modsauce.otyacraftenginerenewed.client.callpoint.ClientCallPointManager;
import net.fabricmc.fabric.api.client.model.ExtraModelProvider;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;

import java.util.function.Consumer;

public class ModelResourceHandler implements ExtraModelProvider {
    public static void init() {
        ModelLoadingRegistry.INSTANCE.registerModelProvider(new ModelResourceHandler());
    }

    @Override
    public void provideExtraModels(ResourceManager manager, Consumer<ResourceLocation> out) {
        ClientCallPointManager.getInstance().call().onModelRegistry(out::accept);
    }
}
