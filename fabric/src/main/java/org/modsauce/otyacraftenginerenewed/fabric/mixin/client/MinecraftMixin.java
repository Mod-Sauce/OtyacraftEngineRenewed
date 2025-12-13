package org.modsauce.otyacraftenginerenewed.fabric.mixin.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.InteractionHand;
import org.jetbrains.annotations.Nullable;
import org.modsauce.otyacraftenginerenewed.client.event.OEClientEventHooks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Minecraft.class)
public class MinecraftMixin {

  @Shadow
  public ClientLevel level;

  @Shadow
  @Nullable
  public LocalPlayer player;

  @Inject(method = "setLevel", at = @At("HEAD"))
  private void setLevel(
    ClientLevel clientLevel,
    net.minecraft.client.gui.screens.ReceivingLevelScreen.Reason reason,
    CallbackInfo ci
  ) {
    if (level != null) OEClientEventHooks.onLevelUnload(level);
  }

  // TODO: Fix for MC 1.21.1 - clearLevel method was renamed or removed
  // The level unload functionality is handled by setLevel injection and NeoForge events

  @Inject(
    method = "continueAttack",
    at = @At(
      value = "INVOKE",
      target = "Lnet/minecraft/world/phys/BlockHitResult;getDirection()Lnet/minecraft/core/Direction;",
      shift = At.Shift.BEFORE
    ),
    cancellable = true
  )
  private void continueAttack(boolean bl, CallbackInfo ci) {
    if (
      player != null &&
      !OEClientEventHooks.onHandAttack(
        player.getItemInHand(InteractionHand.MAIN_HAND)
      )
    ) ci.cancel();
  }

  @Inject(
    method = "startAttack",
    at = @At(
      value = "INVOKE",
      target = "Lnet/minecraft/world/phys/HitResult;getType()Lnet/minecraft/world/phys/HitResult$Type;",
      shift = At.Shift.BEFORE
    ),
    cancellable = true
  )
  private void startAttack(CallbackInfoReturnable<Boolean> cir) {
    if (
      player != null &&
      !OEClientEventHooks.onHandAttack(
        player.getItemInHand(InteractionHand.MAIN_HAND)
      )
    ) cir.cancel();
  }
}
