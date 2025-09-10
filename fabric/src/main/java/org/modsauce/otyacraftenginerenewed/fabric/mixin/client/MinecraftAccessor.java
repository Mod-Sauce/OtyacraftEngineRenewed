package org.modsauce.otyacraftenginerenewed.fabric.mixin.client;

import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Minecraft.class)
public interface MinecraftAccessor {
  // TODO: Fix field mapping for MC 1.21.1 - pausePartialTick field name changed
  // @Accessor
  // float getPausePartialTick();
}
