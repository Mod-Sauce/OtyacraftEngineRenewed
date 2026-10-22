package org.modsauce.otyacraftenginerenewed.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.Nullable;

public abstract class EquipmentEntityBlock extends OEBaseEntityBlock implements SimpleWaterloggedBlock {
  public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

  protected EquipmentEntityBlock(Properties properties) {
    super(properties);
    this.registerDefaultState(this.stateDefinition.any().setValue(WATERLOGGED, false));
  }

  public boolean propagatesSkylightDown(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
    return !blockState.getValue(WATERLOGGED);
  }

  @Override
  public FluidState getFluidState(BlockState blockState) {
    return blockState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(blockState);
  }

  @Nullable
  @Override
  public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
    FluidState fluidState = blockPlaceContext.getLevel().getFluidState(blockPlaceContext.getClickedPos());
    return super.getStateForPlacement(blockPlaceContext).setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER);
  }

  @Override
  public BlockState updateShape(final BlockState blockState, final LevelReader levelAccessor, final ScheduledTickAccess ticks, final BlockPos blockPos, final Direction directionToNeighbour, final BlockPos neighbourPos, final BlockState neighbourState, final RandomSource random) {
    // if (blockState.getValue(WATERLOGGED)) {
    //   levelAccessor.scheduleTick(blockPos, Fluids.WATER, Fluids.WATER.getTickDelay(levelAccessor));
    // }

    return super.updateShape(blockState, levelAccessor, ticks, blockPos, directionToNeighbour, neighbourPos, neighbourState, random);
  }

  @Override
  protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
    super.createBlockStateDefinition(builder);
    builder.add(WATERLOGGED);
  }
}
