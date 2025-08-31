package org.modsauce.otyacraftenginerenewed.shape.bundle;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.function.Consumer;
import java.util.function.Function;

public class BlockStateVoxelShapesBundle extends AbstractVoxelShapeBundle<BlockState> {
  private final Block block;
  private final Function<BlockState, VoxelShape> shapeGen;

  public BlockStateVoxelShapesBundle(Block block, Function<BlockState, VoxelShape> shapeGen, boolean preGen) {
    this.block = block;
    this.shapeGen = shapeGen;
    if (preGen) preGen();
  }

  @Override
  void forAssumption(Consumer<BlockState> consumer) {
    block.getStateDefinition().getPossibleStates().forEach(consumer);
  }

  @Override
  VoxelShape generate(BlockState value) {
    return shapeGen.apply(value);
  }
}
