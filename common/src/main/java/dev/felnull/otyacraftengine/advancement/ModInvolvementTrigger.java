package dev.felnull.otyacraftengine.advancement;

import com.google.gson.JsonObject;
import dev.felnull.otyacraftengine.OtyacraftEngine;
import dev.felnull.otyacraftengine.util.OEItemUtils;
import net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.advancements.critereon.DeserializationContext;
import java.util.Optional;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ModInvolvementTrigger extends SimpleCriterionTrigger<ModInvolvementTrigger.TriggerInstance> {
    static final ResourceLocation ID = new ResourceLocation(OtyacraftEngine.MODID, "mod_involvement");

//    @Override
    public ResourceLocation getId() {
        return ID;
    }

    @Override
    public @NotNull TriggerInstance createInstance(@NotNull JsonObject jsonObject, Optional<ContextAwarePredicate> contextAwarePredicate, @NotNull DeserializationContext deserializationContext) {
        String mid = jsonObject.has("modid") ? jsonObject.get("modid").getAsString() : "";
        return new TriggerInstance(contextAwarePredicate.orElse(null), mid);
    }

    public static void trigger(ServerPlayer serverPlayer, ItemStack itemStack) {
        OECriteriaTriggers.MOD_INVOLVEMENT_TRIGGER.trigger_(serverPlayer, itemStack);
    }

    public static void trigger(ServerPlayer serverPlayer, String modId) {
        OECriteriaTriggers.MOD_INVOLVEMENT_TRIGGER.trigger_(serverPlayer, modId);
    }

    private void trigger_(ServerPlayer serverPlayer, ItemStack itemStack) {
        this.trigger(serverPlayer, (triggerInstance) -> triggerInstance.matches(itemStack));
    }

    private void trigger_(ServerPlayer serverPlayer, String modId) {
        this.trigger(serverPlayer, (triggerInstance) -> triggerInstance.matches(modId));
    }

    public static class TriggerInstance extends AbstractCriterionTriggerInstance {
        @NotNull
        private final String modId;

        public TriggerInstance(ContextAwarePredicate contextAwarePredicate, @NotNull String modId) {
            //Fix me
            super(ID, contextAwarePredicate);
            this.modId = modId;
        }

        private boolean matches(ItemStack stack) {
            var id = OEItemUtils.getCreatorModId(stack);
            return matches(id);
        }

        private boolean matches(String modId) {
            return this.modId.equals(modId);
        }
    }
}
