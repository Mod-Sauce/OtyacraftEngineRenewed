package dev.felnull.otyacraftengine.inventory;

import dev.felnull.otyacraftengine.inventory.slot.LockedSlot;
import dev.felnull.otyacraftengine.item.location.IPlayerItemLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.stream.IntStream;

public abstract class OEItemBEBaseMenu extends OEBaseMenu {
    private final BlockPos pos;
    private final ItemStack itemStack;
    private final IPlayerItemLocation location;

    protected OEItemBEBaseMenu(@Nullable MenuType<?> menuType, int windowId, Inventory playerInventory, Container container, BlockPos pos, ItemStack itemStack, IPlayerItemLocation location, int playerSlotX, int playerSlotY) {
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
        } else {
            super.setPlayerSlot(x, y);
        }
    }

    public BlockPos getPos() {
        return pos;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public IPlayerItemLocation getLocation() {
        return location;
    }
}
