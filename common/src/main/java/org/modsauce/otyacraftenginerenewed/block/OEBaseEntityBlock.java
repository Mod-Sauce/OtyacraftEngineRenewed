package org.modsauce.otyacraftenginerenewed.block;

import com.google.common.collect.ImmutableList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Container;
import net.minecraft.world.Containers;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import org.jetbrains.annotations.Nullable;
import org.modsauce.otyacraftenginerenewed.blockentity.IDroppedBlockEntity;
import org.modsauce.otyacraftenginerenewed.util.OEItemUtils;

import java.util.List;

public abstract class OEBaseEntityBlock extends BaseEntityBlock {
  protected OEBaseEntityBlock(Properties properties) {
    super(properties);
  }

  @Override
  public void setPlacedBy(Level level, BlockPos blockPos, BlockState blockState, @Nullable LivingEntity livingEntity, ItemStack itemStack) {
    super.setPlacedBy(level, blockPos, blockState, livingEntity, itemStack);
    if (itemStack.hasCustomHoverName()) {
      if (level.getBlockEntity(blockPos) instanceof BaseContainerBlockEntity container) {
        container.setCustomName(itemStack.getHoverName());
      }
    }
  }

  @Override
  public void onRemove(BlockState blockState, Level level, BlockPos blockPos, BlockState blockState2, boolean bl) {
    if (!blockState.is(blockState2.getBlock())) {
      var be = level.getBlockEntity(blockPos);
      if (!level.isClientSide() && level instanceof ServerLevel) {
        if (be instanceof IDroppedBlockEntity droppedBlockEntity) {
          if (!droppedBlockEntity.isRetainDrop()) {
            var drops = droppedBlockEntity.getDroppedItems();
            NonNullList<ItemStack> items = NonNullList.create();
            items.addAll(drops);
            Containers.dropContents(level, blockPos, items);
          }
        } else if (be instanceof Container container) {
          Containers.dropContents(level, blockPos, container);
        }
      }
    }
    super.onRemove(blockState, level, blockPos, blockState2, bl);
  }

  @Override
  public void playerWillDestroy(Level level, BlockPos blockPos, BlockState blockState, Player player) {
    var be = level.getBlockEntity(blockPos);
    if (be instanceof IDroppedBlockEntity droppedBlockEntity && droppedBlockEntity.isRetainDrop()) {
      if (!level.isClientSide && player.isCreative()) {
        var dropItem = droppedBlockEntity.createRetainDropItem();
        if (!dropItem.isEmpty()) {
          if (be instanceof BaseContainerBlockEntity named) {
            if (named.hasCustomName())
              dropItem.setHoverName(named.getCustomName());
          }

          var itemEntity = OEItemUtils.createItemEntity(dropItem, level, (double) blockPos.getX() + 0.5D, (double) blockPos.getY() + 0.5D, (double) blockPos.getZ() + 0.5D);
          level.addFreshEntity(itemEntity);
        }
      }
    }
    super.playerWillDestroy(level, blockPos, blockState, player);
  }

  @Override
  public List<ItemStack> getDrops(BlockState blockState, LootParams.Builder builder) {
    var blockEntity = builder.getOptionalParameter(LootContextParams.BLOCK_ENTITY);
    if (blockEntity instanceof IDroppedBlockEntity icbe && icbe.isRetainDrop())
      return ImmutableList.of(icbe.createRetainDropItem());

    return super.getDrops(blockState, builder);
  }
}
