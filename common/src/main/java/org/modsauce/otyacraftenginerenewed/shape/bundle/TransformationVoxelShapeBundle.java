package org.modsauce.otyacraftenginerenewed.shape.bundle;

import net.minecraft.world.phys.shapes.VoxelShape;

public abstract class TransformationVoxelShapeBundle<T> extends AbstractVoxelShapeBundle<T> {
  protected final VoxelShape baseShape;

  protected TransformationVoxelShapeBundle(VoxelShape baseShape) {
    this.baseShape = baseShape;
    preGen();
  }
}
