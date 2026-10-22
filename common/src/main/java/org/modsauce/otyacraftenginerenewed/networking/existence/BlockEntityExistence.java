package org.modsauce.otyacraftenginerenewed.networking.existence;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public record BlockEntityExistence(Identifier dimension, BlockPos blockPos, Identifier blockEntityName) {
  @NotNull
  public static BlockEntityExistence read(@NotNull FriendlyByteBuf buf) {
    return new BlockEntityExistence(buf.readIdentifier(), buf.readBlockPos(), buf.readIdentifier());
  }

  @NotNull
  public static BlockEntityExistence getByBlockEntity(@NotNull BlockEntity blockEntity) {
    var bereg = BuiltInRegistries.BLOCK_ENTITY_TYPE.getKey(blockEntity.getType());
    return new BlockEntityExistence(blockEntity.getLevel().dimension().identifier(), blockEntity.getBlockPos(), bereg);
  }

  public boolean check(@Nullable Level level) {
    if (level == null) return false;
    if (!level.dimension().identifier().equals(dimension))
      return false;
    var be = level.getBlockEntity(blockPos);
    return be != null && blockEntityName.equals(BuiltInRegistries.BLOCK_ENTITY_TYPE.getKey(be.getType()));
  }

  public void write(FriendlyByteBuf buf) {
    buf.writeIdentifier(dimension);
    buf.writeBlockPos(blockPos);
    buf.writeIdentifier(blockEntityName);
  }
}
