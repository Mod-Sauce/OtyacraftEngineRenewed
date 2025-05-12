package org.modsauce.otyacraftenginerenewed.inventory;

import com.mojang.datafixers.util.Pair;
import org.modsauce.otyacraftenginerenewed.inventory.slot.LockedSlot;
import org.modsauce.otyacraftenginerenewed.item.location.PlayerItemLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.stream.IntStream;

public abstract class OEItemBEBaseMenu extends OEBaseMenu {
    private final BlockPos pos;
    private final ItemStack itemStack;
    private final PlayerItemLocation location;

    protected OEItemBEBaseMenu(@Nullable MenuType<?> menuType, int windowId, Inventory playerInventory, Container container, BlockPos pos, ItemStack itemStack, PlayerItemLocation location, int playerSlotX, int playerSlotY) {
        super(menuType, windowId, playerInventory, container, -1, -1);
        this.pos = pos;
        this.itemStack = itemStack;
        this.location = location;
        setPlayerSlot(playerSlotX, playerSlotY);
    }

    @Override
    protected void setPlayerSlot(int x, int y) {
        if (itemStack != null && !itemStack.isEmpty()) {
            if (x >= 0 && y >= 0) {
                IntStream.range(0, 3).forEach(k -> IntStream.range(0, 9).forEach(i1 -> this.addSlot(new LockedSlot(getPlayerInventory(), itemStack, i1 + k * 9 + 9, x + i1 * 18, y + k * 18))));
                IntStream.range(0, 9).forEach(l -> this.addSlot(new LockedSlot(getPlayerInventory(), itemStack, l, x + l * 18, y + 58)));
            }
            if (isActiveOffHandSlot())
                this.addSlot(new LockedSlot(getPlayerInventory(), itemStack, Inventory.SLOT_OFFHAND, x + 166, y + 58) {
                    public Pair<ResourceLocation, ResourceLocation> getNoItemIcon() {
                        return Pair.of(InventoryMenu.BLOCK_ATLAS, InventoryMenu.EMPTY_ARMOR_SLOT_SHIELD);
                    }
                });
        } else {
            super.setPlayerSlot(x, y);
        }
    }

    @Override
    protected boolean isActiveOffHandSlot() {
        return true;
    }

    public BlockPos getPos() {
        return pos;
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
        return location == null;
    }
}