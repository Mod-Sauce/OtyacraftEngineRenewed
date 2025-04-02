package dev.felnull.otyacraftengine;

import dev.architectury.platform.Platform;
import dev.architectury.utils.Env;
import dev.architectury.utils.EnvExecutor;
import dev.felnull.otyacraftengine.advancement.OECriteriaTriggers;
import dev.felnull.otyacraftengine.client.OtyacraftEngineClient;
import dev.felnull.otyacraftengine.handler.CommonHandler;
import dev.felnull.otyacraftengine.item.location.PlayerItemLocations;
import dev.felnull.otyacraftengine.networking.OEPackets;
import dev.felnull.otyacraftengine.util.OEDataGenUtils;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.PartitioningSerializer;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.minecraft.world.item.CreativeModeTabs;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class OtyacraftEngine {
    public static final Logger LOGGER = LogManager.getLogger(OtyacraftEngine.class);
    public static final String MODID = "otyacraftengine";
    private static final OEConfig CONFIG = AutoConfig.register(OEConfig.class, PartitioningSerializer.wrap(Toml4jConfigSerializer::new)).getConfig();

    public static void init() {
        CommonHandler.init();
        OEPackets.init();
        OECriteriaTriggers.init();
        PlayerItemLocations.init();
        
        if (!OEDataGenUtils.isDataGenerating())
            EnvExecutor.runInEnv(Env.CLIENT, () -> OtyacraftEngineClient::preInit);
    }

    public static String getModName() {
        return Platform.getMod(MODID).getName();
    }

    public static OEConfig getConfig() {
        return CONFIG;
    }
}
