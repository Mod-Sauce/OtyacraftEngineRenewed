package org.modsauce.otyacraftenginerenewed.fabric;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.core.registries.Registries;
import org.modsauce.otyacraftenginerenewed.OtyacraftEngine;
import org.modsauce.otyacraftenginerenewed.advancement.ModInvolvementTrigger;
import org.modsauce.otyacraftenginerenewed.advancement.OECriteriaTriggers;
import net.fabricmc.api.ModInitializer;

public class OtyacraftEngineFabric implements ModInitializer {
    // Create a DeferredRegister for criteria triggers
    public static final DeferredRegister<CriterionTrigger<?>> CRITERIA_TRIGGERS =
            DeferredRegister.create(OtyacraftEngine.MODID, Registries.TRIGGER_TYPE);

    // Register the trigger
    public static final RegistrySupplier<ModInvolvementTrigger> MOD_INVOLVEMENT_TRIGGER =
            CRITERIA_TRIGGERS.register("mod_involvement", ModInvolvementTrigger::new);

    @Override
    public void onInitialize() {
        // Register criteria triggers FIRST
        CRITERIA_TRIGGERS.register();

        // Set the trigger instance so common code can access it
        OECriteriaTriggers.setModInvolvementTrigger(MOD_INVOLVEMENT_TRIGGER.get());

        // Initialize common criteria triggers
        OECriteriaTriggers.init();

        // Initialize the rest of the mod
        OtyacraftEngine.init();
    }
}
