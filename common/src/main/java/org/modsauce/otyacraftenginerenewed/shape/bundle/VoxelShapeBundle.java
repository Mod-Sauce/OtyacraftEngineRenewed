package org.modsauce.otyacraftenginerenewed.shape.bundle;

import net.minecraft.world.phys.shapes.VoxelShape;

public interface VoxelShapeBundle<T> {
    VoxelShape getShape(T value);
}
