package org.modsauce.otyacraftenginerenewed.blockentity;

import java.util.Collection;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class OEBaseContainerBlockEntity
  extends BaseContainerBlockEntity
  implements
    IDroppedBlockEntity, IInstructionBlockEntity, IOEBaseFuncBlockEntity {

  private boolean updateMark;

  protected OEBaseContainerBlockEntity(
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

  @NotNull
  public abstract NonNullList<ItemStack> getItems();

  @Override
  public int getContainerSize() {
    return getItems().size();
  }

  @Nullable
  @Override
  public Packet<ClientGamePacketListener> getUpdatePacket() {
    if (!isSyncUpdate()) return null;
    return ClientboundBlockEntityDataPacket.create(this);
  }

  @Override
  public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
    CompoundTag tag = super.getUpdateTag(registries);
    saveToUpdateTag(tag);
    return tag;
  }

  @Override
  public void saveToUpdateTag(CompoundTag tag) {
    ContainerHelper.saveAllItems(
      tag,
      getItems(),
      level != null ? level.registryAccess() : null
    );
  }

  @Override
  public void loadToUpdateTag(CompoundTag tag) {
    getItems().clear();
    ContainerHelper.loadAllItems(
      tag,
      getItems(),
      level != null ? level.registryAccess() : null
    );
  }

  @Override
  public boolean isSyncUpdate() {
    return true;
  }

  @Override
  public boolean isEmpty() {
    return getItems().stream().allMatch(ItemStack::isEmpty);
  }

  @Override
  public ItemStack getItem(int i) {
    return getItems().get(i);
  }

  @Override
  public ItemStack removeItem(int i, int j) {
    return ContainerHelper.removeItem(getItems(), i, j);
  }

  @Override
  public ItemStack removeItemNoUpdate(int i) {
    return ContainerHelper.takeItem(getItems(), i);
  }

  @Override
  public void setItem(int i, ItemStack stack) {
    getItems().set(i, stack);
    if (stack.getCount() > this.getMaxStackSize()) {
      stack.setCount(this.getMaxStackSize());
    }

    this.setChanged();
  }

  @Override
  public boolean stillValid(@NotNull Player player) {
    if (this.level.getBlockEntity(this.worldPosition) != this) return false;
    return isUsableByPlayer(player);
  }

  @Override
  public void clearContent() {
    getItems().clear();
  }

  public boolean isUsableByPlayer(Player player) {
    return (
      getLevel().getBlockEntity(getBlockPos()) == this &&
      player.distanceToSqr(
        (double) getBlockPos().getX() + 0.5D,
        (double) getBlockPos().getY() + 0.5D,
        (double) getBlockPos().getZ() + 0.5D
      ) <=
      64.0D
    );
  }

  @Override
  public Collection<ItemStack> getDroppedItems() {
    return getItems();
  }

  @Override
  protected void loadAdditional(
    CompoundTag tag,
    HolderLookup.Provider registries
  ) {
    super.loadAdditional(tag, registries);
    getItems().clear();
    ContainerHelper.loadAllItems(tag, getItems(), registries);
  }

  @Override
  protected void saveAdditional(
    CompoundTag tag,
    HolderLookup.Provider registries
  ) {
    super.saveAdditional(tag, registries);
    ContainerHelper.saveAllItems(tag, getItems(), registries);
  }

  protected ItemStack getDropItem() {
    return ItemStack.EMPTY;
  }

  @Override
  public ItemStack createRetainDropItem() {
    var itm = getDropItem();
    if (itm.isEmpty()) return itm;
    itm = itm.copy();
    saveToItem(itm, level != null ? level.registryAccess() : null);
    return itm;
  }

  @Override
  public CompoundTag onInstruction(
    ServerPlayer player,
    String name,
    CompoundTag data
  ) {
    return null;
  }

  @Override
  public boolean canInstructionWith(
    ServerPlayer player,
    String name,
    CompoundTag data
  ) {
    return stillValid(player);
  }

  @Override
  public void setChanged() {
    super.setChanged();
    updateMarked();
  }
}
