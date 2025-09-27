package org.modsauce.otyacraftenginerenewed.fabric.mixin.client;

import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Minecraft.class)
public interface MinecraftAccessor {
  // MinecraftAccessor no longer needed in 1.21.1
  // getTimer().getGameTimeDeltaPartialTick() is now available directly
}
