package dev.felnull.otyacraftengine.neoforge;


import dev.felnull.otyacraftengine.OtyacraftEngine;
import dev.felnull.otyacraftengine.neoforge.handler.CommonHandlerForge;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.javafmlmod.FMLJavaModLoadingContext;
import net.neoforged.bus.EventBus;

@Mod(OtyacraftEngine.MODID)
public class OtyacraftEngineNeoforge {
    public OtyacraftEngineNeoforge() {
        NeoForge.EVENT_BUS.register(CommonHandlerForge.class);
        EventBus.registerEventBus(OtyacraftEngine.MODID, FMLJavaModLoadingContext.get().getModEventBus());
        OtyacraftEngine.init();

    }
}
