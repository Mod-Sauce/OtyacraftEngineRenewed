package dev.felnull.otyacraftengine.neoforge.mixin.self;

import dev.felnull.otyacraftengine.blockentity.IClientSyncableBlockEntity;
import dev.felnull.otyacraftengine.blockentity.OEBaseBlockEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.neoforged.neoforge.common.extensions.IBlockEntityExtension;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(OEBaseBlockEntity.class)
public abstract class OEBaseBlockEntityMixin implements IBlockEntityExtension, IClientSyncableBlockEntity {
    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        CompoundTag tag = pkt.getTag();
        if (tag != null)
            loadToUpdateTag(tag);
    }
}
