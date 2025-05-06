package dev.felnull.otyacraftengine.advancement;

import net.minecraft.advancements.CriteriaTriggers;


public class OECriteriaTriggers {
    public static final ModInvolvementTrigger MOD_INVOLVEMENT_TRIGGER = new ModInvolvementTrigger();

    public static void init() {
        //Does this work?
        CriteriaTriggers.register("",MOD_INVOLVEMENT_TRIGGER);
    }
}