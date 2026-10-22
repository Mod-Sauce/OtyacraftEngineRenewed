package org.modsauce.otyacraftenginerenewed.fabric.client.handler;

import java.util.ArrayList;
import java.util.List;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.minecraft.resources.Identifier;
import org.modsauce.otyacraftenginerenewed.client.callpoint.ClientCallPointManager;

public class ModelResourceHandler implements ModelLoadingPlugin {

  public static void init() {
    ModelLoadingPlugin.register(new ModelResourceHandler());
  }

  @Override
  public void initialize(Context pluginContext) {
    List<Identifier> models = new ArrayList<>();
    ClientCallPointManager.getInstance().call().onModelRegistry(models::add);
    pluginContext.addModels(models);
  }
}
