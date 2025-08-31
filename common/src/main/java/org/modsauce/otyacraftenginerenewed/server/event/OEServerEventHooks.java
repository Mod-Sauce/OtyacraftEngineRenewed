package org.modsauce.otyacraftenginerenewed.server.event;

import net.minecraft.server.MinecraftServer;
import org.jetbrains.annotations.NotNull;

public class OEServerEventHooks {
  public static void onServerSaving(@NotNull MinecraftServer server) {
    ServerEvent.SERVER_SAVING.invoker().stateChanged(server);
  }
}
