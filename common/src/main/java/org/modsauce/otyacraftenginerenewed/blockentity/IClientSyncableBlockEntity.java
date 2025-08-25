package org.modsauce.otyacraftenginerenewed.blockentity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;

public interface IClientSyncableBlockEntity {
    boolean isSyncUpdate();

    void saveToUpdateTag(CompoundTag tag);

    void loadToUpdateTag(CompoundTag tag);

    default void syncToClient() {
        if (this instanceof BlockEntity blockEntity && isSyncUpdate())
            blockEntity.getLevel().sendBlockUpdated(blockEntity.getBlockPos(), blockEntity.getBlockState(), blockEntity.getBlockState(), Block.UPDATE_CLIENTS);
    }

    void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt);
}
