package org.modsauce.otyacraftenginerenewed.event;

import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

public class OECommonEventHooks {
    public static void onEntityDefineSynchedData(@NotNull Entity entity, @NotNull SynchedEntityData entityData) {
        MoreEntityEvent.ENTITY_DEFINE_SYNCHED_DATA.invoker().onDefineSynchedData(entity, entityData);
    }

    public static boolean onLivingEntityTick(@NotNull LivingEntity livingEntity) {
        var event = MoreEntityEvent.LIVING_ENTITY_TICK.invoker().livingEntityTick(livingEntity);
        return event.isEmpty() || event.isTrue();
    }
}
