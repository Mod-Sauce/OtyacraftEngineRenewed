package dev.felnull.otyacraftengine.blockentity;

import net.minecraft.world.item.ItemStack;

import java.util.Collection;

public interface DroppedBlockEntity {
    Collection<ItemStack> getDroppedItems();

    default boolean isRetainDrop() {
        return false;
    }

    default ItemStack createRetainDropItem() {
        return ItemStack.EMPTY;
    }
}
