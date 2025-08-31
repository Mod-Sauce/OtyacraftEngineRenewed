package org.modsauce.otyacraftenginerenewed.blockentity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;

public interface IInstructionBlockEntity {
  CompoundTag onInstruction(ServerPlayer player, String name, CompoundTag data);

  boolean canInstructionWith(ServerPlayer player, String name, CompoundTag data);
}