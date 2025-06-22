package org.modsauce.otyacraftenginerenewed.advancement;

import com.google.gson.JsonObject;
import org.modsauce.otyacraftenginerenewed.OtyacraftEngine;
import org.modsauce.otyacraftenginerenewed.util.OEItemUtils;
import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class ModInvolvementTrigger extends SimpleCriterionTrigger<ModInvolvementTrigger.TriggerInstance> {
  static final ResourceLocation ID = new ResourceLocation(OtyacraftEngine.MODID, "mod_involvement");

  public ResourceLocation getId() {
    return ID;
  }

  @Override
  protected @NotNull TriggerInstance createInstance(JsonObject jsonObject,
                                                    Optional<ContextAwarePredicate> contextAwarePredicate, @NotNull DeserializationContext deserializationContext) {
    String mid = "";
    if (jsonObject != null && jsonObject.has("modid")) {
      try {
        mid = jsonObject.get("modid").getAsString();
      } catch (Exception e) {
        // Fallback to empty string if there's an issue
      }
    }
    return new TriggerInstance(contextAwarePredicate.orElse(null), mid);
  }

  public static void trigger(ServerPlayer serverPlayer, ItemStack itemStack) {
    if (serverPlayer != null && !itemStack.isEmpty()) {
      OECriteriaTriggers.MOD_INVOLVEMENT_TRIGGER.trigger_(serverPlayer, itemStack);
    }
  }

  public static void trigger(ServerPlayer serverPlayer, String modId) {
    if (serverPlayer != null && modId != null && !modId.isEmpty()) {
      OECriteriaTriggers.MOD_INVOLVEMENT_TRIGGER.trigger_(serverPlayer, modId);
    }
  }

  private void trigger_(ServerPlayer serverPlayer, ItemStack itemStack) {
    if (serverPlayer != null && !itemStack.isEmpty()) {
      this.trigger(serverPlayer, (triggerInstance) -> triggerInstance.matches(itemStack));
    }
  }

  private void trigger_(ServerPlayer serverPlayer, String modId) {
    if (serverPlayer != null && modId != null && !modId.isEmpty()) {
      this.trigger(serverPlayer, (triggerInstance) -> triggerInstance.matches(modId));
    }
  }

  public static class TriggerInstance extends AbstractCriterionTriggerInstance {
    @NotNull
    private final String modId;

    public TriggerInstance(ContextAwarePredicate contextAwarePredicate, @NotNull String modId) {
      super(Optional.ofNullable(contextAwarePredicate));
      this.modId = modId != null ? modId : "";
    }

    public static TriggerInstance involvedMod(String modId) {
      return new TriggerInstance(null, modId != null ? modId : "");
    }

    private boolean matches(ItemStack stack) {
      if (stack == null || stack.isEmpty()) {
        return false;
      }
      var id = OEItemUtils.getCreatorModId(stack);
      return matches(id);
    }

    private boolean matches(String modId) {
      return modId != null && this.modId.equals(modId);
    }

    @Override
    public JsonObject serializeToJson() {
      JsonObject jsonObject = new JsonObject();
      jsonObject.addProperty("modid", modId);
      return jsonObject;
    }

  }
}
