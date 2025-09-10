package org.modsauce.otyacraftenginerenewed.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class OEBaseBlockEntity
  extends BlockEntity
  implements IOEBaseFuncBlockEntity {

  private boolean updateMark;

  public OEBaseBlockEntity(
    BlockEntityType<?> blockEntityType,
    BlockPos blockPos,
    BlockState blockState
  ) {
    super(blockEntityType, blockPos, blockState);
  }

  @Override
  public void setUpdateMarked(boolean marked) {
    this.updateMark = marked;
  }

  @Override
  public boolean isUpdateMarked() {
    return updateMark;
  }

  @Override
  public boolean isSyncUpdate() {
    return false;
  }

  @Override
  public void saveToUpdateTag(CompoundTag tag) {}

  @Override
  public void loadToUpdateTag(CompoundTag tag) {}

  @Override
  public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
    var tag = super.getUpdateTag(registries);
    saveToUpdateTag(tag);
    return tag;
  }

  @Nullable
  @Override
  public Packet<ClientGamePacketListener> getUpdatePacket() {
    if (!isSyncUpdate()) return null;
    return ClientboundBlockEntityDataPacket.create(this);
  }

  @Override
  public void setChanged() {
    super.setChanged();
    updateMarked();
  }
}
