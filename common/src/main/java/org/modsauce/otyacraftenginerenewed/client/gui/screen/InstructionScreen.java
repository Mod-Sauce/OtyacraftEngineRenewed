package org.modsauce.otyacraftenginerenewed.client.gui.screen;

import net.minecraft.nbt.CompoundTag;

import java.util.UUID;

public interface InstructionScreen {
  UUID getInstructionID();

  void instruction(String name, CompoundTag data);

  void onInstructionReturn(String name, CompoundTag data);
}
