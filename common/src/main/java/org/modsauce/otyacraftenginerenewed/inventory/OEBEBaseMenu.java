package org.modsauce.otyacraftenginerenewed.inventory;

import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.MenuType;
import org.jetbrains.annotations.Nullable;

public abstract class OEBEBaseMenu extends OEBaseMenu {
    private final BlockPos pos;

    protected OEBEBaseMenu(@Nullable MenuType<?> menuType, int windowId, Inventory playerInventory, Container container, BlockPos pos, int playerSlotX, int playerSlotY) {
        super(menuType, windowId, playerInventory, container, playerSlotX, playerSlotY);
        this.pos = pos;
    }

    public BlockPos getPos() {
        return pos;
    }

    @Override
    public boolean isBlock() {
        return true;
    }
}