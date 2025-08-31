package org.modsauce.otyacraftenginerenewed.client.gui.screen;


import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.modsauce.otyacraftenginerenewed.inventory.OEItemBEBaseMenu;
import org.modsauce.otyacraftenginerenewed.item.location.PlayerItemLocation;

public abstract class OEItemBEContainerBasedScreen<T extends OEItemBEBaseMenu> extends OEContainerBasedScreen<T> implements InstructionItemScreen, InstructionBEScreen {

  public OEItemBEContainerBasedScreen(T abstractContainerMenu, Inventory inventory, Component component) {
    super(abstractContainerMenu, inventory, component);
  }

  public BlockEntity getBlockEntity() {
    return mc.level.getBlockEntity(getBlockPos());
  }

  public boolean isBlock() {
    return getMenu().isBlock();
  }

  public ItemStack getItem() {
    return getMenu().getItemStack(mc.player);
  }

  public BlockPos getBlockPos() {
    return getMenu().getPos();
  }

  public PlayerItemLocation getItemLocation() {
    return menu.getLocation();
  }

  @Override
  public void instruction(String name, CompoundTag data) {
    if (isBlock()) {
      InstructionBEScreen.instructionBlockEntity(this, getBlockEntity(), name, data);
    } else {
      InstructionItemScreen.instructionItem(this, getItem(), getItemLocation(), name, data);
    }
  }
}