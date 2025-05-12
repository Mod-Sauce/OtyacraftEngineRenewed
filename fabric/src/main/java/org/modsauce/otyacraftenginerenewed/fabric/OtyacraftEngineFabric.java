package org.modsauce.otyacraftenginerenewed.fabric;

import org.modsauce.otyacraftenginerenewed.OtyacraftEngine;
import net.fabricmc.api.ModInitializer;

public class OtyacraftEngineFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        OtyacraftEngine.init();
    }
}
