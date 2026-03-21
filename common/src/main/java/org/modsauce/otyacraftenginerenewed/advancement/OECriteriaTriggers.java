package org.modsauce.otyacraftenginerenewed.advancement;

import net.minecraft.advancements.CriterionTrigger;
import org.modsauce.otyacraftenginerenewed.OtyacraftEngine;

public class OECriteriaTriggers {

    public static ModInvolvementTrigger MOD_INVOLVEMENT_TRIGGER = new ModInvolvementTrigger();

    public static void init() {
        // Platform-specific code will handle registration
        // This just logs that we're initializing
        OtyacraftEngine.LOGGER.info("Initializing criteria triggers");
    }
}
