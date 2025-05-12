package org.modsauce.otyacraftenginerenewed.shape.bundle;

import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public abstract class AbstractVoxelShapeBundle<T> implements VoxelShapeBundle<T> {
    private final Map<T, VoxelShape> shapeCache = new HashMap<>();

    protected AbstractVoxelShapeBundle() {
    }

    protected void preGen() {
        forAssumption(v -> shapeCache.put(v, generate(v)));
    }

    abstract void forAssumption(Consumer<T> consumer);

    abstract VoxelShape generate(T value);

    @Override
    public VoxelShape getShape(T value) {
        return shapeCache.get(value);
    }
}
