package org.modsauce.otyacraftenginerenewed.fabric;

import net.fabricmc.api.ModInitializer;
import net.minecraft.advancements.CriteriaTriggers;
import org.modsauce.otyacraftenginerenewed.OtyacraftEngine;
import org.modsauce.otyacraftenginerenewed.advancement.OECriteriaTriggers;

public class OtyacraftEngineFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        CriteriaTriggers.register("mod_involvement", OECriteriaTriggers.MOD_INVOLVEMENT_TRIGGER);

        OECriteriaTriggers.init();

        OtyacraftEngine.init();
    }
}
