package org.modsauce.otyacraftenginerenewed.forge;

import dev.architectury.platform.forge.EventBuses;
import org.modsauce.otyacraftenginerenewed.OtyacraftEngine;
import org.modsauce.otyacraftenginerenewed.forge.handler.CommonHandlerForge;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(OtyacraftEngine.MODID)
public class OtyacraftEngineForge {
    public OtyacraftEngineForge() {
        MinecraftForge.EVENT_BUS.register(CommonHandlerForge.class);
        EventBuses.registerModEventBus(OtyacraftEngine.MODID, FMLJavaModLoadingContext.get().getModEventBus());
        OtyacraftEngine.init();
    }
}
