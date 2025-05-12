package org.modsauce.otyacraftenginerenewed.advancement;

import com.google.gson.JsonObject;
import org.modsauce.otyacraftenginerenewed.OtyacraftEngine;
import org.modsauce.otyacraftenginerenewed.util.OEItemUtils;
import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ModInvolvementTrigger extends SimpleCriterionTrigger<ModInvolvementTrigger.TriggerInstance> {
    static final ResourceLocation ID = new ResourceLocation(OtyacraftEngine.MODID, "mod_involvement");

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    @Override
    protected @NotNull TriggerInstance createInstance(JsonObject jsonObject, ContextAwarePredicate contextAwarePredicate, @NotNull DeserializationContext deserializationContext) {
        String mid = jsonObject.has("modid") ? jsonObject.get("modid").getAsString() : "";
        return new TriggerInstance(contextAwarePredicate, mid);
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
            super(ID, contextAwarePredicate);
            this.modId = modId;
        }

        public static TriggerInstance involvedMod(String modId) {
            return new TriggerInstance(ContextAwarePredicate.ANY, modId);
        }

        private boolean matches(ItemStack stack) {
            var id = OEItemUtils.getCreatorModId(stack);
            return matches(id);
        }

        private boolean matches(String modId) {
            return this.modId.equals(modId);
        }

        @Override
        public JsonObject serializeToJson() {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("modid", modId);
            return jsonObject;
        }

    }
}
