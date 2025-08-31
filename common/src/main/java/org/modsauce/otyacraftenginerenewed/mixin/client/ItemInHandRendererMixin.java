package org.modsauce.otyacraftenginerenewed.mixin.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import org.modsauce.otyacraftenginerenewed.client.event.ClientEvent;
import org.modsauce.otyacraftenginerenewed.client.event.OEClientEventHooks;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemInHandRenderer.class)
public class ItemInHandRendererMixin {
  @Shadow
  @Final
  private Minecraft minecraft;
  @Shadow
  private ItemStack mainHandItem;
  @Shadow
  private ItemStack offHandItem;

  @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;isHandsBusy()Z"))
  private void tick(CallbackInfo ci) {
    var mitem = minecraft.player.getMainHandItem();
    if (!OEClientEventHooks.onChangeHandHeight(InteractionHand.MAIN_HAND, this.mainHandItem, mitem)) {
      this.mainHandItem = mitem;
    }

    var oitem = minecraft.player.getOffhandItem();
    if (!OEClientEventHooks.onChangeHandHeight(InteractionHand.OFF_HAND, this.offHandItem, oitem)) {
      this.offHandItem = oitem;
    }

  }

  @Inject(method = "evaluateWhichHandsToRender", at = @At("RETURN"), cancellable = true)
  private static void evaluateWhichHandsToRender(LocalPlayer localPlayer, CallbackInfoReturnable<ItemInHandRenderer.HandRenderSelection> cir) {
    var ret = toHandRenderSelection(OEClientEventHooks.onEvaluateWhichHandsToRender(toHandRenderSelectionWrapper(cir.getReturnValue()), localPlayer));
    if (ret != cir.getReturnValue())
      cir.setReturnValue(ret);
  }

  private static ClientEvent.HandRenderSelectionWrapper toHandRenderSelectionWrapper(ItemInHandRenderer.HandRenderSelection handRenderSelection) {
    return switch (handRenderSelection) {
      case RENDER_BOTH_HANDS -> ClientEvent.HandRenderSelectionWrapper.RENDER_BOTH_HANDS;
      case RENDER_MAIN_HAND_ONLY -> ClientEvent.HandRenderSelectionWrapper.RENDER_MAIN_HAND_ONLY;
      case RENDER_OFF_HAND_ONLY -> ClientEvent.HandRenderSelectionWrapper.RENDER_OFF_HAND_ONLY;
    };
  }

  private static ItemInHandRenderer.HandRenderSelection toHandRenderSelection(ClientEvent.HandRenderSelectionWrapper handRenderSelectionWrapper) {
    return switch (handRenderSelectionWrapper) {
      case RENDER_BOTH_HANDS -> ItemInHandRenderer.HandRenderSelection.RENDER_BOTH_HANDS;
      case RENDER_MAIN_HAND_ONLY -> ItemInHandRenderer.HandRenderSelection.RENDER_MAIN_HAND_ONLY;
      case RENDER_OFF_HAND_ONLY -> ItemInHandRenderer.HandRenderSelection.RENDER_OFF_HAND_ONLY;
    };
  }
}
