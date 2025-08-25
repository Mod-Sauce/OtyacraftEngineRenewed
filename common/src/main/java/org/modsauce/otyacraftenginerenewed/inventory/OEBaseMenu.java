package org.modsauce.otyacraftenginerenewed.inventory;

import com.mojang.datafixers.util.Pair;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public abstract class OEBaseMenu extends AbstractContainerMenu {
    private final Inventory playerInventory;
    private final Container container;

    protected OEBaseMenu(@Nullable MenuType<?> menuType, int i, Inventory playerInventory, Container container, int playerSlotX, int playerSlotY) {
        super(menuType, i);
        this.playerInventory = playerInventory;
        this.container = container;
        setSlot();
        setPlayerSlot(playerSlotX, playerSlotY);
    }

    protected abstract void setSlot();

    @Override
    public boolean stillValid(Player player) {
        return this.container.stillValid(player);
    }

    @Override
    public void removed(Player player) {
        super.removed(player);
        this.container.stopOpen(player);
    }

    protected boolean isActiveOffHandSlot() {
        return false;
    }

    public Container getContainer() {
        return container;
    }

    protected void setPlayerSlot(int x, int y) {
        if (x >= 0 && y >= 0) {
            IntStream.range(0, 3).forEach(k -> IntStream.range(0, 9).forEach(i1 -> this.addSlot(new Slot(playerInventory, i1 + k * 9 + 9, x + i1 * 18, y + k * 18))));
            IntStream.range(0, 9).forEach(l -> this.addSlot(new Slot(playerInventory, l, x + l * 18, y + 58)));
            if (isActiveOffHandSlot())
                this.addSlot(new Slot(playerInventory, Inventory.SLOT_OFFHAND, x + 166, y + 58) {
                    @Override
                    public @NotNull Pair<ResourceLocation, ResourceLocation> getNoItemIcon() {
                        return Pair.of(InventoryMenu.BLOCK_ATLAS, InventoryMenu.EMPTY_ARMOR_SLOT_SHIELD);
                    }
                });
        }
    }

    public Inventory getPlayerInventory() {
        return playerInventory;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot.hasItem()) {
            ItemStack slotitem = slot.getItem();
            itemstack = slotitem.copy();
            if (index <= getNonPlayerInventorySlotCount() - 1) {
                if (!this.moveItemStackTo(slotitem, getNonPlayerInventorySlotCount(), 36 + getNonPlayerInventorySlotCount(), false)) {
                    return ItemStack.EMPTY;
                }
            } else if (index <= getNonPlayerInventorySlotCount() + 26) {
                for (int i = 0; i < getNonPlayerInventorySlotCount(); i++) {
                    if (getNonPlayerInventorySlots().get(i).mayPlace(slotitem) && !this.moveItemStackTo(slotitem, i, i + 1, false)) {
                        return ItemStack.EMPTY;
                    }
                }
                if (!this.moveItemStackTo(slotitem, getNonPlayerInventorySlotCount() + 27, getNonPlayerInventorySlotCount() + 36, false)) {
                    return ItemStack.EMPTY;
                }
            } else {
                for (int i = 0; i < getNonPlayerInventorySlotCount(); i++) {
                    if (getNonPlayerInventorySlots().get(i).mayPlace(slotitem) && !this.moveItemStackTo(slotitem, i, i + 1, false)) {
                        return ItemStack.EMPTY;
                    }
                }
                if (!this.moveItemStackTo(slotitem, getNonPlayerInventorySlotCount(), getNonPlayerInventorySlotCount() + 26, false)) {
                    return ItemStack.EMPTY;
                }
            }
            if (slotitem.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
            if (slotitem.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }
            slot.onTake(player, slotitem);
        }
        return itemstack;
    }

    public int getNonPlayerInventorySlotCount() {
        return getNonPlayerInventorySlots().size();
    }

    public List<Slot> getNonPlayerInventorySlots() {
        return slots.stream().filter(n -> n.container != getPlayerInventory()).collect(Collectors.toList());
    }

    abstract public boolean isBlock();
}
