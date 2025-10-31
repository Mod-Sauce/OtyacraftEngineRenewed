package org.modsauce.otyacraftenginerenewed.fabric.mixin;

import org.modsauce.otyacraftenginerenewed.event.OECommonEventHooks;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public class EntityMixin {
    @Shadow
    @Final
    protected SynchedEntityData entityData;

    @Inject(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;defineSynchedData(Lnet/minecraft/network/syncher/SynchedEntityData$Builder;)V"))
    private void init(EntityType<?> entityType, Level level, CallbackInfo ci) {
        OECommonEventHooks.onEntityDefineSynchedData((Entity) (Object) this, entityData);
    }
}