package org.modsauce.otyacraftenginerenewed.advancement;

import net.minecraft.advancements.CriteriaTriggers;

public class OECriteriaTriggers {
  public static final ModInvolvementTrigger MOD_INVOLVEMENT_TRIGGER = new ModInvolvementTrigger();

  public static void init() {
    CriteriaTriggers.register(MOD_INVOLVEMENT_TRIGGER);
  }
}