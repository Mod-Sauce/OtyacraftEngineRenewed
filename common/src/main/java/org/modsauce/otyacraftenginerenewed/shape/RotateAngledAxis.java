package org.modsauce.otyacraftenginerenewed.shape;

import net.minecraft.core.Direction;
import net.minecraft.world.phys.AABB;
import org.apache.commons.lang3.tuple.Triple;

public enum RotateAngledAxis {
    Y90((sx, sy, sz, ex, ey, ez) -> new ConvertPos(sz, sy, 1 - sx, ez, ey, 1 - ex), Direction.Axis.Y, 90),
    Y180((sx, sy, sz, ex, ey, ez) -> new ConvertPos(1 - sx, sy, 1 - sz, 1 - ex, ey, 1 - ez), Direction.Axis.Y, 180),
    Y270((sx, sy, sz, ex, ey, ez) -> {
        var pos = Y180.convert.convert(sx, sy, sz, ex, ey, ez);
        return new ConvertPos(pos.sz(), pos.sy(), 1 - pos.sx(), pos.ez(), pos.ey(), 1 - pos.ex());
    }, Direction.Axis.Y, 270),
    X90((sx, sy, sz, ex, ey, ez) -> new ConvertPos(sy, sx, sz, ey, ex, ez), Direction.Axis.X, 90),
    X180((sx, sy, sz, ex, ey, ez) -> new ConvertPos(1 - sx, 1 - sy, sz, 1 - ex, 1 - ey, ez), Direction.Axis.X, 180),
    X270((sx, sy, sz, ex, ey, ez) -> {
        var pos = Y180.convert.convert(sx, sy, sz, ex, ey, ez);
        return new ConvertPos(1 - pos.sy(), pos.sx(), pos.sz(), 1 - pos.ey(), pos.ex(), pos.ez());
    }, Direction.Axis.X, 270),
    Z90((sx, sy, sz, ex, ey, ez) -> new ConvertPos(sx, sz, sy, ex, ez, ey), Direction.Axis.Z, 90),
    Z180((sx, sy, sz, ex, ey, ez) -> new ConvertPos(sx, 1 - sy, 1 - sz, ex, 1 - ey, 1 - ez), Direction.Axis.Z, 180);

    private final ConvertRot convert;
    private final Direction.Axis axis;
    private final float angle;

    RotateAngledAxis(ConvertRot convert, Direction.Axis axis, float angle) {
        this.convert = convert;
        this.axis = axis;
        this.angle = angle;
    }

    public Direction.Axis getAxis() {
        return axis;
    }

    public float getAngle() {
        return angle;
    }

    public AABB rotationAABB(AABB aabb) {
        var cp = convert.convert(aabb.minX, aabb.minY, aabb.minZ, aabb.maxX, aabb.maxY, aabb.maxZ);
        return new AABB(cp.sx(), cp.sy(), cp.sz(), cp.ex(), cp.ey(), cp.ez());
    }

    public VoxelEdge rotationEdge(VoxelEdge edge) {
        var cp = convert.convert(edge.stX(), edge.stY(), edge.stZ(), edge.enX(), edge.enY(), edge.enZ());
        return new VoxelEdge(cp.sx(), cp.sy(), cp.sz(), cp.ex(), cp.ey(), cp.ez());
    }

    public Triple<Float, Float, Float> getRotation() {
        float x = 0;
        float y = 0;
        float z = 0;

        switch (axis) {
            case X -> x = angle;
            case Y -> y = angle;
            case Z -> z = angle;
        }
        return Triple.of(x, y, z);
    }

    private static interface ConvertRot {
        ConvertPos convert(double sx, double sy, double sz, double ex, double ey, double ez);
    }

    private static record ConvertPos(double sx, double sy, double sz, double ex, double ey, double ez) {
    }
}