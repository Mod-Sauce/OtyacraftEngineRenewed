package org.modsauce.otyacraftenginerenewed.mixin;

import net.minecraft.server.MinecraftServer;
import org.modsauce.otyacraftenginerenewed.server.event.OEServerEventHooks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MinecraftServer.class)
public class MinecraftServerMixin {
  @Inject(method = "saveAllChunks", at = @At("RETURN"))
  private void saveAllChunks(boolean bl, boolean bl2, boolean bl3, CallbackInfoReturnable<Boolean> cir) {
    OEServerEventHooks.onServerSaving((MinecraftServer) (Object) this);
  }
}