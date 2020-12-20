package red.felnull.otyacraftengine.data;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import red.felnull.otyacraftengine.api.event.common.ReceiverEvent;
import red.felnull.otyacraftengine.api.registries.OERegistries;
import red.felnull.otyacraftengine.util.IKSGFileLoadUtil;
import red.felnull.otyacraftengine.util.IKSGPathUtil;
import red.felnull.otyacraftengine.util.IKSGServerUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ServerDataReceiver extends Thread {
    private static final Map<String, Map<String, DataReceiverBuffer>> RECEIVS = new HashMap<>();
    private final String name;
    private final String uuid;
    private final ResourceLocation location;
    private final String playerUUID;
    private final SendReceiveLogger logger;
    private int cont;
    private final long fristTime;
    private long logTime;

    public ServerDataReceiver(String playerUUID, String uuid, ResourceLocation location, String name, int datasize) {
        this.playerUUID = playerUUID;
        this.uuid = uuid;
        this.location = location;
        this.name = name;
        this.fristTime = System.currentTimeMillis();
        this.logTime = System.currentTimeMillis();
        if (!RECEIVS.containsKey(playerUUID)) {
            RECEIVS.put(playerUUID, new HashMap<>());
        }
        RECEIVS.get(playerUUID).put(uuid, new DataReceiverBuffer(datasize, uuid, location, name));
        String det = "PlayerUUID:" + playerUUID + " UUID:" + uuid + " Location:" + location.toString() + " Name:" + name + " Size:" + datasize + "byte";
        this.logger = new SendReceiveLogger(location.toString(), det, Dist.DEDICATED_SERVER, SendReceiveLogger.SndOrRec.RECEIVE);
    }

    public static void addBufferBytes(String pluuid, String uuid, byte[] bytes) {
        if (RECEIVS.containsKey(pluuid) && RECEIVS.get(pluuid).containsKey(uuid)) {
            RECEIVS.get(pluuid).get(uuid).addBytes(bytes);
        }
    }

    public void receiveFinish(SendReceiveLogger.SRResult result) {
        this.logger.addFinishLogLine(result, System.currentTimeMillis() - fristTime, RECEIVS.get(playerUUID).get(uuid).getCont());
        RECEIVS.get(playerUUID).remove(uuid);
        this.logger.createLog();
        MinecraftForge.EVENT_BUS.post(new ReceiverEvent.Server.Pos(IKSGServerUtil.getMinecraftServer().getPlayerList().getPlayerByUUID(UUID.fromString(playerUUID)), uuid, location, name, result));

    }

    public void run() {
        try {
            this.logger.addStartLogLine();
            MinecraftForge.EVENT_BUS.post(new ReceiverEvent.Server.Pre(IKSGServerUtil.getMinecraftServer().getPlayerList().getPlayerByUUID(UUID.fromString(playerUUID)), uuid, location, name));
            long time = System.currentTimeMillis();
            while (!RECEIVS.get(playerUUID).get(uuid).isPerfectByte()) {
                if (System.currentTimeMillis() - logTime >= 3000) {
                    this.logger.addProgress(RECEIVS.get(playerUUID).get(uuid).getCont(), RECEIVS.get(playerUUID).get(uuid).allcont - RECEIVS.get(playerUUID).get(uuid).getCont(), System.currentTimeMillis() - fristTime, System.currentTimeMillis() - time, SendReceiveLogger.SndOrRec.RECEIVE);
                    logTime = System.currentTimeMillis();
                }
                if (RECEIVS.get(playerUUID).get(uuid).stop) {
                    this.logger.addLogLine(new TranslationTextComponent("rslog.err.stop"));
                    receiveFinish(SendReceiveLogger.SRResult.FAILURE);
                    return;
                }
                if (!IKSGServerUtil.isOnlinePlayer(playerUUID)) {
                    this.logger.addLogLine(new TranslationTextComponent("rslog.err.playerExitedWorld"));
                    receiveFinish(SendReceiveLogger.SRResult.FAILURE);
                    return;
                }
                if ((cont == RECEIVS.get(playerUUID).get(uuid).getCont() && System.currentTimeMillis() - time >= 10000)) {
                    this.logger.addLogLine(new TranslationTextComponent("rslog.err.timeout"));
                    receiveFinish(SendReceiveLogger.SRResult.FAILURE);
                    return;
                }
                if (cont != RECEIVS.get(playerUUID).get(uuid).getCont()) {
                    cont = RECEIVS.get(playerUUID).get(uuid).getCont();
                    time = System.currentTimeMillis();
                    MinecraftForge.EVENT_BUS.post(new ReceiverEvent.Server.Run(IKSGServerUtil.getMinecraftServer().getPlayerList().getPlayerByUUID(UUID.fromString(playerUUID)), uuid, location, name, RECEIVS.get(playerUUID).get(uuid).allcont, cont));
                }
                sleep(1);
            }
            IKSGFileLoadUtil.fileBytesWriter(RECEIVS.get(playerUUID).get(uuid).getBytes(), IKSGPathUtil.getWorldSaveDataPath().resolve(OERegistries.SERVER_RECEVED_PATH.get(location).resolve(name)));
        } catch (Exception ex) {
            this.logger.addExceptionLogLine(ex);
            ex.printStackTrace();
            receiveFinish(SendReceiveLogger.SRResult.FAILURE);
        }
        receiveFinish(SendReceiveLogger.SRResult.SUCCESS);
    }
}
