package org.modsauce.otyacraftenginerenewed.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import org.jetbrains.annotations.Nullable;

public class HorizontalDirectionalBaseBlock extends HorizontalDirectionalBlock {

  public static final MapCodec<HorizontalDirectionalBaseBlock> CODEC =
    simpleCodec(HorizontalDirectionalBaseBlock::new);

  protected HorizontalDirectionalBaseBlock(Properties properties) {
    super(properties);
    this.registerDefaultState(
      this.stateDefinition.any().setValue(FACING, Direction.NORTH)
    );
  }

  @Override
  public MapCodec<HorizontalDirectionalBaseBlock> codec() {
    return CODEC;
  }

  @Override
  protected void createBlockStateDefinition(
    StateDefinition.Builder<Block, BlockState> builder
  ) {
    super.createBlockStateDefinition(builder);
    builder.add(FACING);
  }

  @Nullable
  @Override
  public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
    return super
      .getStateForPlacement(blockPlaceContext)
      .setValue(
        FACING,
        blockPlaceContext.getHorizontalDirection().getOpposite()
      );
  }
}
