package org.modsauce.otyacraftenginerenewed.item.location.factory;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import org.modsauce.otyacraftenginerenewed.item.location.HandItemLocation;

public class HandItemLocationFactory implements PlayerItemLocationFactory<HandItemLocation> {
  @Override
  public HandItemLocation create(CompoundTag tag) {
    return new HandItemLocation(tag.getBoolean("hand") ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND);
  }
}
