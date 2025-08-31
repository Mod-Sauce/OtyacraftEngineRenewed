package org.modsauce.otyacraftenginerenewed.client.event;

import com.mojang.blaze3d.shaders.FogShape;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Camera;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.FogType;

import java.util.concurrent.atomic.AtomicReference;

public class OEClientEventHooks {
  public static void onLevelUnload(ClientLevel level) {
    MoreClientLifecycleEvents.CLIENT_LEVEL_UNLOAD.invoker().act(level);
  }

  public static boolean onRenderHand(PoseStack poseStack, MultiBufferSource multiBufferSource, InteractionHand hand, int packedLight, float partialTicks, float interpolatedPitch, float swingProgress, float equipProgress, ItemStack stack) {
    return !MoreRenderEvent.RENDER_ITEM_IN_HAND.invoker().renderItemInHand(poseStack, multiBufferSource, hand, packedLight, partialTicks, interpolatedPitch, swingProgress, equipProgress, stack).isFalse();
  }

  public static boolean onRenderArmWithItem(ItemInHandLayer<? extends LivingEntity, ? extends EntityModel<?>> layer, LivingEntity livingEntity, ItemStack itemStack, ItemDisplayContext displayContext, HumanoidArm humanoidArm, PoseStack poseStack, MultiBufferSource multiBufferSource, int i) {
    var event = MoreRenderEvent.RENDER_ARM_WITH_ITEM.invoker().renderArmWithItem(layer, livingEntity, itemStack, displayContext, humanoidArm, poseStack, multiBufferSource, i);
    return event.isEmpty() || event.isTrue();
  }

  public static boolean onChangeHandHeight(InteractionHand hand, ItemStack oldStack, ItemStack newStack) {
    var event = ClientEvent.CHANGE_HAND_HEIGHT.invoker().changeHandHeight(hand, oldStack, newStack);
    return event.isEmpty() || event.isTrue();
  }

  public static boolean onPoseHumanoidArm(HumanoidArm arm, InteractionHand hand, HumanoidModel<? extends LivingEntity> model, LivingEntity livingEntity) {
    var event = ClientEvent.POSE_HUMANOID_ARM.invoker().poseHumanoidArm(arm, hand, model, livingEntity);
    return event.isEmpty() || event.isTrue();
  }

  public static void onPoseHumanoidArmPost(HumanoidArm arm, InteractionHand hand, HumanoidModel<? extends LivingEntity> model, LivingEntity livingEntity) {
    ClientEvent.POSE_HUMANOID_ARM_POST.invoker().poseHumanoidArm(arm, hand, model, livingEntity);
  }

  public static void onIntegratedServerPauseChange(boolean paused) {
    ClientEvent.INTEGRATED_SERVER_PAUSE.invoker().onPauseChange(paused);
  }

  public static boolean onHandAttack(ItemStack itemStack) {
    var event = ClientEvent.HAND_ATTACK.invoker().onHandAttack(itemStack);
    return event.isEmpty() || event.isTrue();
  }

  public static ClientEvent.HandRenderSelectionWrapper onEvaluateWhichHandsToRender(ClientEvent.HandRenderSelectionWrapper handRenderSelection, LocalPlayer player) {
    AtomicReference<ClientEvent.HandRenderSelectionWrapper> wrapperAtomicReference = new AtomicReference<>();
    ClientEvent.EVALUATE_RENDER_HANDS.invoker().onEvaluateRenderHands(handRenderSelection, player, wrapperAtomicReference::set);
    var ret = wrapperAtomicReference.get();
    return ret != null ? ret : handRenderSelection;
  }

  public static boolean onCheckTextureURL(String url) {
    return TextureEvent.CHECK_TEXTURE_URL.invoker().onCheckURL(url).isTrue();
  }

  public static String onSwapTextureURL(String url) {
    String[] nurl = {url};
    TextureEvent.SWAP_TEXTURE_URL.invoker().onSwapURL(url, new TextureEvent.TextureURLSwapper() {
      @Override
      public void setURL(String url) {
        nurl[0] = url;
      }

      @Override
      public String getURL() {
        return nurl[0];
      }
    });
    return nurl[0];
  }

  public static boolean onRenderFog(Camera camera, FogRenderer.FogMode fogMode, FogType fogType, float startDistance, float endDistance, FogShape fogShape, double delta, ClientCameraEvent.RenderFogSetter setter) {
    var event = ClientCameraEvent.RENDER_FOG.invoker().onRenderFog(camera, fogMode, fogType, startDistance, endDistance, fogShape, delta, setter);
    return event.isEmpty() || event.isTrue();
  }

  public static void onComputeFogColor(Camera camera, float red, float green, float blue, double delta, ClientCameraEvent.FogColorSetter fogColorSetter) {
    ClientCameraEvent.COMPUTE_FOG_COLOR.invoker().onComputeFogColor(camera, red, green, blue, delta, fogColorSetter);
  }

  public static void onSetupHumanoidAnimPost(HumanoidModel<? extends LivingEntity> model, LivingEntity livingEntity, float walkAnimationPosition, float walkAnimationSpeed, float deltaTick, float headYRot, float xRot) {
    ClientEvent.SETUP_HUMANOID_ANIM_POST.invoker().setupHumanoidAnimPost(model, livingEntity, walkAnimationPosition, walkAnimationSpeed, deltaTick, headYRot, xRot);
  }
}
