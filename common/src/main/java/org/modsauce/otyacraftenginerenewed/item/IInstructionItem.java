package org.modsauce.otyacraftenginerenewed.item;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

public interface IInstructionItem {
  CompoundTag onInstruction(ItemStack stack, ServerPlayer player, String name, CompoundTag data);

  default boolean canInstructionWith(ItemStack stack, ServerPlayer player, String name, CompoundTag data) {
    return !stack.isEmpty();
  }
}
