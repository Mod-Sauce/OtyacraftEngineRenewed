package org.modsauce.otyacraftenginerenewed.advancement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.modsauce.otyacraftenginerenewed.util.OEItemUtils;

public class ModInvolvementTrigger
  extends SimpleCriterionTrigger<ModInvolvementTrigger.TriggerInstance> {

  @NotNull
  @Override
  public Codec<TriggerInstance> codec() {
    return TriggerInstance.CODEC;
  }

  public static void trigger(ServerPlayer serverPlayer, ItemStack itemStack) {
    OECriteriaTriggers.MOD_INVOLVEMENT_TRIGGER.trigger(
      serverPlayer,
      triggerInstance -> triggerInstance.matches(itemStack)
    );
  }

  public static void trigger(ServerPlayer serverPlayer, String modId) {
    OECriteriaTriggers.MOD_INVOLVEMENT_TRIGGER.trigger(
      serverPlayer,
      triggerInstance -> triggerInstance.matches(modId)
    );
  }

  public record TriggerInstance(
    Optional<ContextAwarePredicate> player,
    String modId
  ) implements SimpleCriterionTrigger.SimpleInstance {
    public static final Codec<TriggerInstance> CODEC =
      RecordCodecBuilder.create(instance ->
        instance
          .group(
            EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf(
              "player"
            ).forGetter(TriggerInstance::player),
            Codec.STRING.optionalFieldOf("modid", "").forGetter(
              TriggerInstance::modId
            )
          )
          .apply(instance, TriggerInstance::new)
      );

    public static TriggerInstance involvedMod(String modId) {
      return new TriggerInstance(Optional.empty(), modId);
    }

    public static TriggerInstance anyMod() {
      return new TriggerInstance(Optional.empty(), "");
    }

    public boolean matches(ItemStack itemStack) {
      if (this.modId.isEmpty()) {
        return true;
      }
      return OEItemUtils.getCreatorModId(itemStack).equals(this.modId);
    }

    public boolean matches(String modId) {
      if (this.modId.isEmpty()) {
        return true;
      }
      return this.modId.equals(modId);
    }
  }
}
