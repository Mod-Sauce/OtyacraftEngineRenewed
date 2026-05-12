package dev.felnull.otyacraftengine.fabric.mixin;

import dev.felnull.otyacraftengine.event.OECommonEventHooks;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
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

  @Inject(method = "<init>", at = @At("TAIL"))
  private void init(CallbackInfo ci) {
    OECommonEventHooks.onEntityDefineSynchedData(
      (Entity) (Object) this,
      entityData
    );
  }
}
