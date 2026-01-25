package org.modsauce.otyacraftenginerenewed.item;

import java.util.function.Function;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import org.modsauce.otyacraftenginerenewed.item.location.PlayerItemLocation;

public class ItemContainer implements Container {

  private final ItemStack itemStack;
  private final PlayerItemLocation location;
  private final NonNullList<ItemStack> items;
  private final String tagName;
  private final Function<Player, Boolean> valid;
  public final HolderLookup.Provider provider;

  public ItemContainer(
    ItemStack itemStack,
    PlayerItemLocation location,
    int size,
    String tagName,
    Function<Player, Boolean> valid,
    HolderLookup.Provider provider
  ) {
      // HolderLookup.Provider你妈
    this.itemStack = itemStack;
    this.items = NonNullList.withSize(size, ItemStack.EMPTY);
    this.location = location;
    loadItemList(itemStack, items, tagName, provider);
    this.tagName = tagName;
    this.valid = valid;
    this.provider = provider;
  }

  @Override
  public int getContainerSize() {
    return this.items.size();
  }

  @Override
  public boolean isEmpty() {
    return items.stream().allMatch(ItemStack::isEmpty);
  }

  @Override
  public ItemStack getItem(int i) {
    return items.get(i);
  }

  @Override
  public ItemStack removeItem(int i, int j) {
    var ret = ContainerHelper.removeItem(items, i, j);
    saveItems();
    return ret;
  }

  @Override
  public ItemStack removeItemNoUpdate(int i) {
    var ret = ContainerHelper.takeItem(items, i);
    saveItems();
    return ret;
  }

  @Override
  public void setItem(int i, ItemStack stack) {
    ItemStack itemstack = items.get(i);
    /* boolean flag = !stack.isEmpty() && stack.sameItem(itemstack) && ItemStack.tagMatches(stack, itemstack);*/
    items.set(i, stack);
    if (stack.getCount() > this.getMaxStackSize()) {
      stack.setCount(this.getMaxStackSize());
    }
    //        if (flag)
    this.setChanged();
  }

  public Function<Player, Boolean> getValid() {
    return valid;
  }

  public String getTagName() {
    return tagName;
  }

  @Override
  public void setChanged() {
    saveItems();
  }

  @Override
  public boolean stillValid(Player player) {
    return (
      !itemStack.isEmpty() &&
      valid.apply(player) &&
      location.getItem(player) == itemStack
    );
  }

  @Override
  public void clearContent() {
    items.clear();
  }

  @Override
  public void stopOpen(Player player) {
    Container.super.stopOpen(player);
    saveItems();
  }

  public NonNullList<ItemStack> getItems() {
    return items;
  }

  public PlayerItemLocation getLocation() {
    return location;
  }

  public void saveItems() {
    saveItemList(itemStack, items, tagName, provider);
  }

  public static void loadItemList(
    ItemStack itemStack,
    NonNullList<ItemStack> items,
    String tagName,
    HolderLookup.Provider provider
  ) {
    var customData = itemStack.get(DataComponents.CUSTOM_DATA);
    if (customData != null) {
      var tag = customData.copyTag();
      if (tag.contains(tagName)) {
        ContainerHelper.loadAllItems(tag.getCompound(tagName), items, provider);
      }
    }
  }

  public static void saveItemList(
    ItemStack itemStack,
    NonNullList<ItemStack> items,
    String tagName,
    HolderLookup.Provider provider
  ) {
    var customData = itemStack.getOrDefault(
      DataComponents.CUSTOM_DATA,
      CustomData.EMPTY
    );
    var tag = customData.copyTag();
    if (!tag.contains(tagName)) tag.put(tagName, new CompoundTag());
    ContainerHelper.saveAllItems(tag.getCompound(tagName), items, provider);
    itemStack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
  }

  public ItemStack getItemStack() {
    return itemStack;
  }

  public static MenuProvider createMenuProvider(
    ItemStack stack,
    PlayerItemLocation location,
    int size,
    String tagName,
    MenuFactory factory,
    HolderLookup.Provider provider
  ) {
    var con = new ItemContainer(stack, location, size, tagName, player -> {
      if (location.getItem(player).isEmpty() || stack.isEmpty()) return false;
      return location.getItem(player) == stack;
    }, provider);
    return new SimpleMenuProvider(
      (i, inventory, player1) ->
        factory.createMenu(i, inventory, con, BlockPos.ZERO, stack, location),
      stack.getHoverName()
    );
  }

  public static interface MenuFactory {
    AbstractContainerMenu createMenu(
      int i,
      Inventory playerInventory,
      Container container,
      BlockPos pos,
      ItemStack itemStack,
      PlayerItemLocation location
    );
  }
}
