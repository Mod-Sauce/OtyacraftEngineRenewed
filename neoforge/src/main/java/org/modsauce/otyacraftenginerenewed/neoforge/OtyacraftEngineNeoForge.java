package org.modsauce.otyacraftenginerenewed.neoforge;

import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.modsauce.otyacraftenginerenewed.OtyacraftEngine;
import org.modsauce.otyacraftenginerenewed.advancement.ModInvolvementTrigger;
import org.modsauce.otyacraftenginerenewed.advancement.OECriteriaTriggers;
import org.modsauce.otyacraftenginerenewed.neoforge.handler.CommonHandlerForge;

@Mod(OtyacraftEngine.MODID)
public class OtyacraftEngineNeoForge {

    // Create a DeferredRegister for criteria triggers
    public static final DeferredRegister<
        CriterionTrigger<?>
    > CRITERIA_TRIGGERS = DeferredRegister.create(
        Registries.TRIGGER_TYPE,
        OtyacraftEngine.MODID
    );

    // Register the trigger
    public static final DeferredHolder<
        CriterionTrigger<?>,
        ModInvolvementTrigger
    > MOD_INVOLVEMENT_TRIGGER = CRITERIA_TRIGGERS.register(
        "mod_involvement",
            () -> OECriteriaTriggers.MOD_INVOLVEMENT_TRIGGER
    );

    public OtyacraftEngineNeoForge(ModContainer modContainer) {
        IEventBus modEventBus = modContainer.getEventBus();

        // Register criteria triggers FIRST
        CRITERIA_TRIGGERS.register(modEventBus);

        // Register event handlers
        NeoForge.EVENT_BUS.register(CommonHandlerForge.class);

        // Initialize the rest of the mod
        OtyacraftEngine.init();
    }
}
