package org.modsauce.otyacraftenginerenewed.inventory.slot;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class LockedSlot extends Slot {
  private final ItemStack lockItem;

  public LockedSlot(Container container, ItemStack lockItem, int i, int j, int k) {
    super(container, i, j, k);
    this.lockItem = lockItem;
  }

  @Override
  public boolean mayPickup(Player player) {
    return getItem() != this.lockItem;
  }

  @Override
  public boolean mayPlace(ItemStack itemStack) {
    return getItem() != this.lockItem;
  }
}
