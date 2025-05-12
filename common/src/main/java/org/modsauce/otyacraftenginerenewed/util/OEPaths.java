package org.modsauce.otyacraftenginerenewed.util;

import org.modsauce.otyacraftenginerenewed.OtyacraftEngine;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.storage.LevelResource;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * パス関係
 *
 * @author MORIMORI0317
 */
public final class OEPaths {
    /**
     * クライアント側のOtyacraftEngineフォルダのパス
     *
     * @return パス
     */
    public static Path getClientOEFolderPath() {
        return Paths.get(OtyacraftEngine.MODID);
    }

    /**
     * ワールドデータのパス
     *
     * @param server MinecraftServer
     * @return パス
     */
    public static Path getWorldSaveDataPath(MinecraftServer server) {
        return server.getWorldPath(LevelResource.ROOT);
    }
}
