package org.modsauce.otyacraftenginerenewed.event;

import dev.architectury.event.Event;
import dev.architectury.event.EventFactory;
import dev.architectury.event.EventResult;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

public interface MoreEntityEvent {
  Event<EntityDefineSynchedData> ENTITY_DEFINE_SYNCHED_DATA = EventFactory.createLoop();
  Event<LivingEntityTick> LIVING_ENTITY_TICK = EventFactory.createEventResult();

  interface EntityDefineSynchedData {
    void onDefineSynchedData(@NotNull Entity entity, @NotNull SynchedEntityData entityData);
  }

  interface LivingEntityTick {
    EventResult livingEntityTick(@NotNull LivingEntity livingEntity);
  }
}
