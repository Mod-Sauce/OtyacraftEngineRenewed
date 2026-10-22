package org.modsauce.otyacraftenginerenewed.fabric.mixin.client;

import net.minecraft.client.multiplayer.ClientLevel;
import org.modsauce.otyacraftenginerenewed.blockentity.IClientSyncableBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPacketListener.class)
public class ClientPacketListenerMixin {
    @Shadow
    private ClientLevel level;

    @Inject(method = "handleBlockEntityData", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/protocol/game/ClientboundBlockEntityDataPacket;getPos()Lnet/minecraft/core/BlockPos;", ordinal = 0), cancellable = true)
    private void handleBlockEntityData(ClientboundBlockEntityDataPacket packet, CallbackInfo ci) {
        BlockPos blockPos = packet.getPos();
        // 就是这行代码导致的网络协议错误
        // fuck
        assert Minecraft.getInstance().level != null;
        Minecraft.getInstance().level.getBlockEntity(blockPos, packet.getType()).ifPresent((blockEntity) -> {
            if (blockEntity instanceof IClientSyncableBlockEntity syncableBlockEntity) {
                CompoundTag tag = packet.getTag();
                syncableBlockEntity.loadToUpdateTag(tag, level.registryAccess());
                ci.cancel();
            }
        });
    }
}
