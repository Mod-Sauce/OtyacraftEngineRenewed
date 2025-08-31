package org.modsauce.otyacraftenginerenewed.client.debug;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public enum HighlightVoxelShapeType {
  OFF(null),
  SHAPE(BlockBehaviour.BlockStateBase::getShape),
  VISUAL_SHAPE(BlockBehaviour.BlockStateBase::getVisualShape),
  COLLISION_SHAPE(BlockBehaviour.BlockStateBase::getCollisionShape),
  INTERACTION_SHAPE((blockState, blockGetter, blockPos, collisionContext) -> blockState.getInteractionShape(blockGetter, blockPos)),
  BLOCKSUPPORT_SHAPE((blockState, blockGetter, blockPos, collisionContext) -> blockState.getBlockSupportShape(blockGetter, blockPos)),
  OCCLUSION_SHAPE((blockState, blockGetter, blockPos, collisionContext) -> blockState.getOcclusionShape(blockGetter, blockPos)),
  SHAPE_NOCOLLISION((blockState, blockGetter, blockPos, collisionContext) -> blockState.getShape(blockGetter, blockPos)),
  COLLISION_SHAPE_NOCOLLISION((blockState, blockGetter, blockPos, collisionContext) -> blockState.getCollisionShape(blockGetter, blockPos));
  private final HighlightVoxelShapeGetter getter;

  HighlightVoxelShapeType(HighlightVoxelShapeGetter getter) {
    this.getter = getter;
  }

  public HighlightVoxelShapeGetter getGetter() {
    return getter;
  }

  public static interface HighlightVoxelShapeGetter {
    VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext);
  }
}