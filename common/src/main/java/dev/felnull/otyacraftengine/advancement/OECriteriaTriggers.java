package dev.felnull.otyacraftengine.advancement;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.CriterionTrigger;

import java.lang.reflect.Method;

public class OECriteriaTriggers {
    public static final ModInvolvementTrigger MOD_INVOLVEMENT_TRIGGER = new ModInvolvementTrigger();
    public static void registerModInvolvementTrigger() {
        try {
            Method registerMethod = CriteriaTriggers.class.getDeclaredMethod("register", CriterionTrigger.class);
            registerMethod.setAccessible(true);
            registerMethod.invoke(null, MOD_INVOLVEMENT_TRIGGER);
        } catch (Exception e) {
            e.printStackTrace();
            // Handle exception
        }
    }
}
/*
public class OECriteriaTriggers {
    public static final ModInvolvementTrigger MOD_INVOLVEMENT_TRIGGER = new ModInvolvementTrigger();

    public static void init() {
        CriteriaTriggers.register(MOD_INVOLVEMENT_TRIGGER);
    }

    public static void init() {
        CriteriaTriggers.;
    }
}
*/