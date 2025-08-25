package org.modsauce.otyacraftenginerenewed.fabric.mixin.client;

import org.modsauce.otyacraftenginerenewed.blockentity.IClientSyncableBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPacketListener.class)
public class ClientPacketListenerMixin {
    @Final
    private Minecraft minecraft;

    @Inject(method = "handleBlockEntityData", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/protocol/game/ClientboundBlockEntityDataPacket;getPos()Lnet/minecraft/core/BlockPos;", ordinal = 0), cancellable = true)
    private void handleBlockEntityData(ClientboundBlockEntityDataPacket clientboundBlockEntityDataPacket, CallbackInfo ci) {
        BlockPos blockPos = clientboundBlockEntityDataPacket.getPos();
        this.minecraft.level.getBlockEntity(blockPos, clientboundBlockEntityDataPacket.getType()).ifPresent((blockEntity) -> {
            if (blockEntity instanceof IClientSyncableBlockEntity syncableBlockEntity) {
                CompoundTag tag = clientboundBlockEntityDataPacket.getTag();
                syncableBlockEntity.loadToUpdateTag(tag);
                ci.cancel();
            }
        });
    }
}
