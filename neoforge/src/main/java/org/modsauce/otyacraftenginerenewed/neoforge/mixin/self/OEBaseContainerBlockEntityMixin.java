package org.modsauce.otyacraftenginerenewed.neoforge.mixin.self;

import org.modsauce.otyacraftenginerenewed.blockentity.IClientSyncableBlockEntity;
import org.modsauce.otyacraftenginerenewed.blockentity.OEBaseContainerBlockEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.neoforged.neoforge.common.extensions.IBlockEntityExtension;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(OEBaseContainerBlockEntity.class)
public abstract class OEBaseContainerBlockEntityMixin implements IBlockEntityExtension, IClientSyncableBlockEntity {
    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        CompoundTag tag = pkt.getTag();
        if (tag != null)
            loadToUpdateTag(tag);
    }
}
