package org.modsauce.otyacraftenginerenewed;

import dev.architectury.platform.Platform;
import dev.architectury.utils.Env;
import dev.architectury.utils.EnvExecutor;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.PartitioningSerializer;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modsauce.otyacraftenginerenewed.advancement.OECriteriaTriggers;
import org.modsauce.otyacraftenginerenewed.client.OtyacraftEngineClient;
import org.modsauce.otyacraftenginerenewed.handler.CommonHandler;
import org.modsauce.otyacraftenginerenewed.item.location.PlayerItemLocations;
import org.modsauce.otyacraftenginerenewed.networking.OEPackets;
import org.modsauce.otyacraftenginerenewed.util.OEDataGenUtils;

import java.util.Objects;

public class OtyacraftEngine {

    public static final Logger LOGGER = LogManager.getLogger(
        OtyacraftEngine.class
    );
    public static final String MODID = "otyacraftenginerenewed";
    private static final OEConfig CONFIG = AutoConfig.register(
        OEConfig.class,
        PartitioningSerializer.wrap(Toml4jConfigSerializer::new)
    ).getConfig();

    public static void init() {
        CommonHandler.init();
        OEPackets.init();
        //OECriteriaTriggers.init();
        PlayerItemLocations.init();

        if (!OEDataGenUtils.isDataGenerating()) EnvExecutor.runInEnv(
            Env.CLIENT,
            () -> OtyacraftEngineClient::preInit
        );
    }

    public static String getModName() {
        return Platform.getMod(MODID).getName();
    }

    public static OEConfig getConfig() {
        return CONFIG;
    }
}
