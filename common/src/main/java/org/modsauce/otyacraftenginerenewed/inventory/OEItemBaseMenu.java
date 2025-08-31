package org.modsauce.otyacraftenginerenewed.inventory;

import com.mojang.datafixers.util.Pair;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.modsauce.otyacraftenginerenewed.inventory.slot.LockedSlot;
import org.modsauce.otyacraftenginerenewed.item.location.PlayerItemLocation;

import java.util.stream.IntStream;

public abstract class OEItemBaseMenu extends OEBaseMenu {
  private final ItemStack itemStack;
  private final PlayerItemLocation location;

  protected OEItemBaseMenu(@Nullable MenuType<?> menuType, int i, Inventory playerInventory, Container container, ItemStack stack, PlayerItemLocation location, int playerSlotX, int playerSlotY) {
    super(menuType, i, playerInventory, container, -1, -1);
    this.itemStack = stack;
    this.location = location;
    setPlayerSlot(playerSlotX, playerSlotY);
  }

  @Override
  protected void setPlayerSlot(int x, int y) {
    if (x >= 0 && y >= 0) {
      IntStream.range(0, 3).forEach(k -> IntStream.range(0, 9).forEach(i1 -> this.addSlot(new LockedSlot(getPlayerInventory(), itemStack, i1 + k * 9 + 9, x + i1 * 18, y + k * 18))));
      IntStream.range(0, 9).forEach(l -> this.addSlot(new LockedSlot(getPlayerInventory(), itemStack, l, x + l * 18, y + 58)));
      if (isActiveOffHandSlot())
        this.addSlot(new LockedSlot(getPlayerInventory(), itemStack, Inventory.SLOT_OFFHAND, x + 166, y + 58) {
          public Pair<ResourceLocation, ResourceLocation> getNoItemIcon() {
            return Pair.of(InventoryMenu.BLOCK_ATLAS, InventoryMenu.EMPTY_ARMOR_SLOT_SHIELD);
          }
        });
    }
  }

  @Override
  protected boolean isActiveOffHandSlot() {
    return true;
  }

  public ItemStack getItemStack() {
    return itemStack;
  }

  public ItemStack getItemStack(Player player) {
    if (location == null)
      return ItemStack.EMPTY;
    return location.getItem(player);
  }

  public PlayerItemLocation getLocation() {
    return location;
  }

  @Override
  public boolean isBlock() {
    return false;
  }
}