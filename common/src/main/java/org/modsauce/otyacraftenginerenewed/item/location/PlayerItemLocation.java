package org.modsauce.otyacraftenginerenewed.item.location;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.modsauce.otyacraftenginerenewed.item.location.factory.PlayerItemLocationFactory;

public interface PlayerItemLocation {
  ItemStack getItem(Player player);

  CompoundTag createTag();

  PlayerItemLocationFactory<? extends PlayerItemLocation> getFactory();
}