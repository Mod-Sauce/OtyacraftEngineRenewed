package org.modsauce.otyacraftenginerenewed.client.gui.screen;


import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import org.modsauce.otyacraftenginerenewed.inventory.OEItemBaseMenu;
import org.modsauce.otyacraftenginerenewed.item.location.PlayerItemLocation;

public abstract class OEItemContainerBasedScreen<T extends OEItemBaseMenu> extends OEContainerBasedScreen<T> implements InstructionItemScreen {
  public OEItemContainerBasedScreen(T abstractContainerMenu, Inventory inventory, Component component) {
    super(abstractContainerMenu, inventory, component);
  }

  public ItemStack getItem() {
    return getMenu().getItemStack(mc.player);
  }

  public PlayerItemLocation getItemLocation() {
    return menu.getLocation();
  }

  @Override
  public void instruction(String name, CompoundTag data) {
    InstructionItemScreen.instructionItem(this, getItem(), getItemLocation(), name, data);
  }
}
