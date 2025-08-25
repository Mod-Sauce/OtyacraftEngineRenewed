package org.modsauce.otyacraftenginerenewed.handler;

import dev.architectury.event.events.common.PlayerEvent;
import org.modsauce.otyacraftenginerenewed.entity.PlayerInfoManager;
import net.minecraft.server.level.ServerPlayer;

public class CommonHandler {
    public static void init() {
        PlayerEvent.PLAYER_JOIN.register(CommonHandler::onPlayerJoin);
        PlayerEvent.PLAYER_QUIT.register(CommonHandler::onPlayerQuit);
    }

    public static void onPlayerJoin(ServerPlayer player) {
        PlayerInfoManager.getInstance().clear(player.getGameProfile());
    }

    public static void onPlayerQuit(ServerPlayer player) {
        PlayerInfoManager.getInstance().clear(player.getGameProfile());
    }
}
