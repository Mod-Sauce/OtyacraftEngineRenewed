package dev.felnull.otyacraftengine.advancement;

import com.google.gson.JsonObject;
import dev.felnull.otyacraftengine.OtyacraftEngine;
import dev.felnull.otyacraftengine.util.OEItemUtils;
import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public abstract class ModInvolvementTrigger extends SimpleCriterionTrigger<ModInvolvementTrigger.TriggerInstance> {
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
        public JsonObject serializeToJson(@NotNull SerializationContext serializationContext) {
            var jo = super.serializeToJson(serializationContext);
            jo.addProperty("modid", modId);
            return jo;
        }
    }
}
