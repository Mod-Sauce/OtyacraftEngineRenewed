package org.modsauce.otyacraftenginerenewed.forge.mixin.self;

import org.modsauce.otyacraftenginerenewed.blockentity.IClientSyncableBlockEntity;
import org.modsauce.otyacraftenginerenewed.blockentity.OEBaseBlockEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraftforge.common.extensions.IForgeBlockEntity;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(OEBaseBlockEntity.class)
public abstract class OEBaseBlockEntityMixin implements IForgeBlockEntity, IClientSyncableBlockEntity {
    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        CompoundTag tag = pkt.getTag();
        if (tag != null)
            loadToUpdateTag(tag);
    }
}
