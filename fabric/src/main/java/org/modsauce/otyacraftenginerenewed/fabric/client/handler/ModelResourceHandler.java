package org.modsauce.otyacraftenginerenewed.fabric.client.handler;

import java.util.ArrayList;
import java.util.List;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.minecraft.resources.ResourceLocation;
import org.modsauce.otyacraftenginerenewed.client.callpoint.ClientCallPointManager;

public class ModelResourceHandler implements ModelLoadingPlugin {

  public static void init() {
    ModelLoadingPlugin.register(new ModelResourceHandler());
  }

  @Override
  public void onInitializeModelLoader(Context pluginContext) {
    List<ResourceLocation> models = new ArrayList<>();
    ClientCallPointManager.getInstance().call().onModelRegistry(models::add);
    pluginContext.addModels(models);
  }
}
