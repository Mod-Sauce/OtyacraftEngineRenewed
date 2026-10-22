package org.modsauce.otyacraftenginerenewed.mixin.client;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import org.modsauce.otyacraftenginerenewed.client.event.OEClientEventHooks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HumanoidModel.class)
public class HumanoidModelMixin<T extends HumanoidRenderState> {
  @Inject(method = "poseRightArm", at = @At("HEAD"), cancellable = true)
  private void poseRightArm(T state, CallbackInfo ci) {
    var hand = state.mainArm == HumanoidArm.RIGHT ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND;

    // Pass the state (or change your event hook method signature to accept HumanoidRenderState instead of LivingEntity)
    if (!OEClientEventHooks.onPoseHumanoidArm(
        HumanoidArm.RIGHT, hand, (HumanoidModel<T>) (Object) this, state)) {
      ci.cancel();
    }
  }

  @Inject(method = "poseLeftArm", at = @At("HEAD"), cancellable = true)
  private void poseLeftArm(T state, CallbackInfo ci) {
    var hand = state.mainArm == HumanoidArm.LEFT
        ? InteractionHand.MAIN_HAND
        : InteractionHand.OFF_HAND;

    if (!OEClientEventHooks.onPoseHumanoidArm(
        HumanoidArm.LEFT, hand, (HumanoidModel<T>) (Object) this, state)) {
      ci.cancel();
    }
  }

  @Inject(method = "poseRightArm", at = @At("TAIL"))
  private void poseRightArmPost(T state, CallbackInfo ci) {
    var hand = state.mainArm == HumanoidArm.RIGHT
        ? InteractionHand.MAIN_HAND
        : InteractionHand.OFF_HAND;

    // Make sure your post-hook matches if it also expects the state or entity
    OEClientEventHooks.onPoseHumanoidArmPost(
        HumanoidArm.RIGHT, hand, (HumanoidModel<T>) (Object) this, state);
  }

  @Inject(method = "poseLeftArm", at = @At("TAIL"))
  private void poseLeftArmPost(T state, CallbackInfo ci) {
    var hand = state.mainArm == HumanoidArm.LEFT
        ? InteractionHand.MAIN_HAND
        : InteractionHand.OFF_HAND;

    OEClientEventHooks.onPoseHumanoidArmPost(
        HumanoidArm.LEFT, hand, (HumanoidModel<T>) (Object) this, state);
  }

  @Inject(
        method = "setupAnim(Lnet/minecraft/client/renderer/entity/state/HumanoidRenderState;)V",
        at = @At("TAIL"))
    private void setupAnim(T state, CallbackInfo ci) {
      OEClientEventHooks.onSetupHumanoidAnimPost(
          (HumanoidModel<T>) (Object) this, state);
    }
}
