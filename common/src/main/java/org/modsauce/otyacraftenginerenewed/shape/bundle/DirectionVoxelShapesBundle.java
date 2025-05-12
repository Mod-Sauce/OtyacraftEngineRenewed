package org.modsauce.otyacraftenginerenewed.shape.bundle;

import org.modsauce.otyacraftenginerenewed.util.OEVoxelShapeUtils;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.function.Consumer;

public class DirectionVoxelShapesBundle extends TransformationVoxelShapeBundle<Direction> {
    public DirectionVoxelShapesBundle(VoxelShape baseShape) {
        super(baseShape);
    }

    @Override
    void forAssumption(Consumer<Direction> consumer) {
        for (Direction value : Direction.values()) {
            consumer.accept(value);
        }
    }

    @Override
    VoxelShape generate(Direction value) {
        if (value == Direction.NORTH) return baseShape;
        return OEVoxelShapeUtils.rotateBoxDirection(baseShape, value);
    }
}