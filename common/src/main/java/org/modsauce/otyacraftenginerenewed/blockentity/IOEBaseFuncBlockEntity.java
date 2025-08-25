package org.modsauce.otyacraftenginerenewed.blockentity;

import net.minecraft.world.level.block.entity.BlockEntity;

public interface IOEBaseFuncBlockEntity extends IClientSyncableBlockEntity {
    default void baseAfterTick() {
        if (!(this instanceof BlockEntity blockEntity)) return;
        var level = blockEntity.getLevel();
        if (level == null) return;

        if (!level.isClientSide()) {
            if (isUpdateMarked() && isSyncUpdate()) {
                syncToClient();
                setUpdateMarked(false);
            }
        }
    }

    default void updateMarked() {
        setUpdateMarked(true);
    }

    boolean isUpdateMarked();

    void setUpdateMarked(boolean marked);
}
