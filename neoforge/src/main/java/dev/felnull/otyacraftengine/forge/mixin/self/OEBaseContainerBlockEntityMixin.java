package dev.felnull.otyacraftengine.forge.mixin.self;

import dev.felnull.otyacraftengine.blockentity.IClientSyncableBlockEntity;
import dev.felnull.otyacraftengine.blockentity.OEBaseContainerBlockEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraftforge.common.extensions.IForgeBlockEntity;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(OEBaseContainerBlockEntity.class)
public abstract class OEBaseContainerBlockEntityMixin implements IForgeBlockEntity, IClientSyncableBlockEntity {
    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        CompoundTag tag = pkt.getTag();
        if (tag != null)
            loadToUpdateTag(tag);
    }
}
