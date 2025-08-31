package org.modsauce.otyacraftenginerenewed.mixin.client;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import org.modsauce.otyacraftenginerenewed.client.event.OEClientEventHooks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HumanoidModel.class)
public class HumanoidModelMixin<T extends LivingEntity> {
  @Inject(method = "poseRightArm", at = @At("HEAD"), cancellable = true)
  private void poseRightArm(T livingEntity, CallbackInfo ci) {
    var hand = livingEntity.getMainArm() == HumanoidArm.RIGHT ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND;
    if (!OEClientEventHooks.onPoseHumanoidArm(HumanoidArm.RIGHT, hand, (HumanoidModel<T>) (Object) this, livingEntity))
      ci.cancel();
  }

  @Inject(method = "poseLeftArm", at = @At("HEAD"), cancellable = true)
  private void poseLeftArm(T livingEntity, CallbackInfo ci) {
    var hand = livingEntity.getMainArm() == HumanoidArm.LEFT ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND;
    if (!OEClientEventHooks.onPoseHumanoidArm(HumanoidArm.LEFT, hand, (HumanoidModel<T>) (Object) this, livingEntity))
      ci.cancel();
  }

  @Inject(method = "poseRightArm", at = @At("TAIL"))
  private void poseRightArmPost(T livingEntity, CallbackInfo ci) {
    var hand = livingEntity.getMainArm() == HumanoidArm.RIGHT ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND;
    OEClientEventHooks.onPoseHumanoidArmPost(HumanoidArm.RIGHT, hand, (HumanoidModel<T>) (Object) this, livingEntity);
  }


  @Inject(method = "poseLeftArm", at = @At("TAIL"))
  private void poseLeftArmPost(T livingEntity, CallbackInfo ci) {
    var hand = livingEntity.getMainArm() == HumanoidArm.LEFT ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND;
    OEClientEventHooks.onPoseHumanoidArmPost(HumanoidArm.LEFT, hand, (HumanoidModel<T>) (Object) this, livingEntity);
  }

  @Inject(method = "setupAnim(Lnet/minecraft/world/entity/LivingEntity;FFFFF)V", at = @At("TAIL"))
  private void setupAnim(T livingEntity, float f, float g, float h, float i, float j, CallbackInfo ci) {
    OEClientEventHooks.onSetupHumanoidAnimPost((HumanoidModel<T>) (Object) this, livingEntity, f, g, h, i, j);
  }
}

