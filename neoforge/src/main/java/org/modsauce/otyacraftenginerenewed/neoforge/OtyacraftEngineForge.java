package org.modsauce.otyacraftenginerenewed.neoforge;

import org.modsauce.otyacraftenginerenewed.OtyacraftEngine;
import org.modsauce.otyacraftenginerenewed.neoforge.handler.CommonHandlerForge;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;

@Mod(OtyacraftEngine.MODID)
public class OtyacraftEngineForge {
    public OtyacraftEngineForge(ModContainer modContainer) {
        NeoForge.EVENT_BUS.register(CommonHandlerForge.class);
        OtyacraftEngine.init();
    }
}
