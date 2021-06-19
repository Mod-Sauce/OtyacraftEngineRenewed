package red.felnull.otyacraftengine.util;

import dev.architectury.utils.GameInstance;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.chunk.LevelChunk;
import red.felnull.otyacraftengine.impl.OEPacketExpectPlatform;
import red.felnull.otyacraftengine.packet.IPacketMessage;
import red.felnull.otyacraftengine.packet.IPacketMessageClientHandler;
import red.felnull.otyacraftengine.packet.IPacketMessageServerHandler;

import java.util.UUID;
import java.util.function.Function;

public class IKSGPacketUtil {
    public static <MSG extends IPacketMessage> void registerSendToClientPacket(Class<MSG> message, IPacketMessageClientHandler<MSG> handler) {
        OEPacketExpectPlatform.registerSendToClientPacket(message, IPacketMessage::encode, n -> {
            try {
                MSG pm = message.newInstance();
                pm.decode(n);
                return pm;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }, handler);
    }

    public static <MSG extends IPacketMessage> void registerSendToServerPacket(Class<MSG> message, IPacketMessageServerHandler<MSG> handler) {
        OEPacketExpectPlatform.registerSendToServerPacket(message, IPacketMessage::encode, n -> {
            try {
                MSG pm = message.newInstance();
                pm.decode(n);
                return pm;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }, handler);
    }

    public static <MSG extends IPacketMessage> void sendToServerPacket(MSG message) {
        OEPacketExpectPlatform.sendToServerPacket(message);
    }

    public static <MSG extends IPacketMessage> void sendToClientPacket(ServerPlayer player, MSG message) {
        OEPacketExpectPlatform.sendToClientPacket(player, message);
    }

    public static <MSG extends IPacketMessage> void sendToClientPacketPlayerd(ServerPlayer player, Function<ServerPlayer, MSG> message) {
        OEPacketExpectPlatform.sendToClientPacket(player, message.apply(player));
    }

    public static <MSG extends IPacketMessage> void sendToClientPacket(LevelChunk chunk, MSG message) {
        ((ServerChunkCache) chunk.getLevel().getChunkSource()).chunkMap.getPlayers(chunk.getPos(), false).forEach(n -> sendToClientPacket(n, message));
    }

    public static <MSG extends IPacketMessage> void sendToClientPacketPlayerd(LevelChunk chunk, Function<ServerPlayer, MSG> message) {
        ((ServerChunkCache) chunk.getLevel().getChunkSource()).chunkMap.getPlayers(chunk.getPos(), false).forEach(n -> sendToClientPacketPlayerd(n, message));
    }

    public static <MSG extends IPacketMessage> void sendToClientPacket(MinecraftServer server, MSG message) {
        server.getPlayerList().getPlayers().forEach(n -> sendToClientPacket(n, message));
    }

    public static <MSG extends IPacketMessage> void sendToClientPacket(MSG message) {
        sendToClientPacket(GameInstance.getServer(), message);
    }

    public static <MSG extends IPacketMessage> void sendToClientPacketPlayerd(MinecraftServer server, Function<ServerPlayer, MSG> message) {
        server.getPlayerList().getPlayers().forEach(n -> sendToClientPacketPlayerd(n, message));
    }

    public static <MSG extends IPacketMessage> void sendToClientPacketPlayerd(Function<ServerPlayer, MSG> message) {
        sendToClientPacketPlayerd(GameInstance.getServer(), message);
    }

    public static <MSG extends IPacketMessage> void sendToClientPacket(UUID playerID, MSG message) {
        sendToClientPacket(GameInstance.getServer(), playerID, message);
    }

    public static <MSG extends IPacketMessage> void sendToClientPacket(MinecraftServer server, UUID playerID, MSG message) {
        sendToClientPacket(server.getPlayerList().getPlayer(playerID), message);
    }

    public static <MSG extends IPacketMessage> void sendToClientPacket(UUID playerID, Function<ServerPlayer, MSG> message) {
        sendToClientPacketPlayerd(GameInstance.getServer(), playerID, message);
    }

    public static <MSG extends IPacketMessage> void sendToClientPacketPlayerd(MinecraftServer server, UUID playerID, Function<ServerPlayer, MSG> message) {
        sendToClientPacketPlayerd(server.getPlayerList().getPlayer(playerID), message);
    }

}
