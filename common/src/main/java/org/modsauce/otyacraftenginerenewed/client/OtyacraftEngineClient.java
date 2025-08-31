package org.modsauce.otyacraftenginerenewed.client;

import dev.architectury.platform.Platform;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.gui.ConfigScreenProvider;
import org.modsauce.otyacraftenginerenewed.OEConfig;
import org.modsauce.otyacraftenginerenewed.OtyacraftEngine;
import org.modsauce.otyacraftenginerenewed.client.handler.ClientDebugHandler;
import org.modsauce.otyacraftenginerenewed.client.handler.ClientHandler;
import org.modsauce.otyacraftenginerenewed.client.renderer.texture.URLTextureManager;
import org.modsauce.otyacraftenginerenewed.client.shape.ClientIVShapeManager;
import org.modsauce.otyacraftenginerenewed.networking.OEPackets;

public class OtyacraftEngineClient {

  public static void init() {
    configInit();
    ClientDebugHandler.init();
    ClientHandler.init();
    URLTextureManager.getInstance().init();

    OEPackets.clientInit();
  }

  public static void preInit() {
    ClientIVShapeManager.getInstance().init();
  }

  private static void configInit() {
    Platform.getMod(OtyacraftEngine.MODID).registerConfigurationScreen(parent -> {
      ConfigScreenProvider<OEConfig> provider = (ConfigScreenProvider<OEConfig>) AutoConfig.getConfigScreen(OEConfig.class, parent);

      provider.setBuildFunction(builder -> {
        builder.setGlobalized(true);
        builder.setGlobalizedExpanded(false);
        return builder.build();
      });

      return provider.get();
    });
  }
}
