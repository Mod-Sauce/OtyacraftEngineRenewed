package org.modsauce.otyacraftenginerenewed.util;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dev.felnull.fnjl.util.FNDataUtil;
import org.modsauce.otyacraftenginerenewed.shape.IkisugiVoxelShape;
import org.modsauce.otyacraftenginerenewed.shape.IkisugiVoxelShapes;
import org.modsauce.otyacraftenginerenewed.shape.RotateAngledAxis;
import org.modsauce.otyacraftenginerenewed.shape.bundle.DirectionVoxelShapesBundle;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.apache.commons.lang3.ArrayUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * VoxelShape関係のユーティリティ
 *
 * @author MORIMORI0317
 */
public final class OEVoxelShapeUtils {
    private static final Gson GSON = new Gson();

    /**
     * VoxelShapeを作成する
     *
     * @param x1 開始X座標
     * @param y1 開始Y座標
     * @param z1 開始Z座標
     * @param x2 終了X座標
     * @param y2 終了Y座標
     * @param z2 終了Z座標
     * @return 作成したVoxelShape
     */
    public static VoxelShape makeBox(double x1, double y1, double z1, double x2, double y2, double z2) {
        return Block.box(x1, y1, z1, x2, y2, z2);
    }

    /**
     * Y90度回転したVoxelShapeを作成する
     *
     * @param x1 開始X座標
     * @param y1 開始Y座標
     * @param z1 開始Z座標
     * @param x2 終了X座標
     * @param y2 終了Y座標
     * @param z2 終了Z座標
     * @return 作成したVoxelShape
     */
    public static VoxelShape makeBoxY90(double x1, double y1, double z1, double x2, double y2, double z2) {
        return makeBox(z1, y1, 16 - x1, z2, y2, 16 - x2);
    }

    /**
     * Y180度回転したVoxelShapeを作成する
     *
     * @param x1 開始X座標
     * @param y1 開始Y座標
     * @param z1 開始Z座標
     * @param x2 終了X座標
     * @param y2 終了Y座標
     * @param z2 終了Z座標
     * @return 作成したVoxelShape
     */
    public static VoxelShape makeBoxY180(double x1, double y1, double z1, double x2, double y2, double z2) {
        return makeBox(16 - x1, y1, 16 - z1, 16 - x2, y2, 16 - z2);
    }

    /**
     * Y270度回転したVoxelShapeを作成する
     *
     * @param x1 開始X座標
     * @param y1 開始Y座標
     * @param z1 開始Z座標
     * @param x2 終了X座標
     * @param y2 終了Y座標
     * @param z2 終了Z座標
     * @return 作成したVoxelShape
     */
    public static VoxelShape makeBoxY270(double x1, double y1, double z1, double x2, double y2, double z2) {
        return makeBoxY180(z1, y1, 16 - x1, z2, y2, 16 - x2);
    }

    /**
     * VoxelShapeを相対座標で作成する
     *
     * @param x1 開始X座標
     * @param y1 開始Y座標
     * @param z1 開始Z座標
     * @param x2 終了X相対座標
     * @param y2 終了Y相対座標
     * @param z2 終了Z相対座標
     * @return 作成したVoxelShape
     */
    public static VoxelShape addBoxY0(double x1, double y1, double z1, double x2, double y2, double z2) {
        return makeBox(x1, y1, z1, x1 + x2, y1 + y2, z1 + z2);
    }

    /**
     * Y90度回転したVoxelShapeを相対座標で作成する
     *
     * @param x1 開始X座標
     * @param y1 開始Y座標
     * @param z1 開始Z座標
     * @param x2 終了X相対座標
     * @param y2 終了Y相対座標
     * @param z2 終了Z相対座標
     * @return 作成したVoxelShape
     */
    public static VoxelShape addBoxY90(double x1, double y1, double z1, double x2, double y2, double z2) {
        return makeBoxY90(x1, y1, z1, x1 + x2, y1 + y2, z1 + z2);
    }

    /**
     * Y180度回転したVoxelShapeを相対座標で作成する
     *
     * @param x1 開始X座標
     * @param y1 開始Y座標
     * @param z1 開始Z座標
     * @param x2 終了X相対座標
     * @param y2 終了Y相対座標
     * @param z2 終了Z相対座標
     * @return 作成したVoxelShape
     */
    public static VoxelShape addBoxY180(double x1, double y1, double z1, double x2, double y2, double z2) {
        return makeBoxY180(x1, y1, z1, x1 + x2, y1 + y2, z1 + z2);
    }

    /**
     * Y270度回転したVoxelShapeを相対座標で作成する
     *
     * @param x1 開始X座標
     * @param y1 開始Y座標
     * @param z1 開始Z座標
     * @param x2 終了X相対座標
     * @param y2 終了Y相対座標
     * @param z2 終了Z相対座標
     * @return 作成したVoxelShape
     */
    public static VoxelShape addBoxY270(double x1, double y1, double z1, double x2, double y2, double z2) {
        return makeBoxY270(x1, y1, z1, x1 + x2, y1 + y2, z1 + z2);
    }

    /**
     * VoxelShapeを合体する
     *
     * @param shapes 合わせるVoxelShape
     * @return 合体したVoxelShape
     */
    public static VoxelShape uniteBox(VoxelShape... shapes) {
        return Shapes.or(shapes[0], ArrayUtils.remove(shapes, 0));
    }

    /**
     * VoxelShapeを合体する
     *
     * @param shapes 合わせるVoxelShape
     * @return 合体したVoxelShape
     */
    public static VoxelShape uniteBox(List<VoxelShape> shapes) {
        if (shapes.isEmpty()) return Shapes.empty();
        List<VoxelShape> shapesCp = new ArrayList<>(shapes);
        var voxelShape = shapesCp.remove(0);
        var shape = shapesCp.stream().reduce(voxelShape, Shapes::or);
        return IkisugiVoxelShapes.getInstance().unite(shape, shapes.stream().map(n -> (IkisugiVoxelShape) n).toList());
    }

    /**
     * VoxelShapeを移動する
     * 1.0fで16分の１ブロック分移動
     *
     * @param shape 元のVoxelShape
     * @param x     X方向
     * @param y     Y方向
     * @param z     Z方向
     * @return 移動し終わったあとのVoxelShape
     */
    public static VoxelShape moveBox(VoxelShape shape, double x, double y, double z) {
        List<VoxelShape> shapes = new ArrayList<>();
        for (AABB aabb : shape.toAabbs()) {
            shapes.add(Shapes.create(aabb.move(1d / 16d * x, 1d / 16d * y, 1d / 16d * z)));
        }
        var ushape = uniteBox(shapes);
        return IkisugiVoxelShapes.getInstance().move(ushape, (IkisugiVoxelShape) shape, x, y, z);
    }

    /**
     * VoxelShapeを指定した角度に回転する
     *
     * @param shape      回転するVoxelShape
     * @param angledAxis 角度
     * @return 回転したVoxelShape
     */
    public static VoxelShape rotateBox(VoxelShape shape, RotateAngledAxis angledAxis) {
        List<VoxelShape> shapes = new ArrayList<>();
        for (AABB aabb : shape.toAabbs()) {
            shapes.add(Shapes.create(angledAxis.rotationAABB(aabb)));
        }
        var ushape = uniteBox(shapes);
        return IkisugiVoxelShapes.getInstance().rotate(ushape, (IkisugiVoxelShape) shape, angledAxis);
    }

    /**
     * VoxelShapeをY90度回転する
     *
     * @param shape 回転するVoxelShape
     * @return 回転したVoxelShape
     */
    public static VoxelShape rotateBoxY90(VoxelShape shape) {
        return rotateBox(shape, RotateAngledAxis.Y90);
    }

    /**
     * VoxelShapeをY180度回転する
     *
     * @param shape 回転するVoxelShape
     * @return 回転したVoxelShape
     */
    public static VoxelShape rotateBoxY180(VoxelShape shape) {
        return rotateBox(shape, RotateAngledAxis.Y180);
    }

    /**
     * VoxelShapeをY270度回転する
     *
     * @param shape 回転するVoxelShape
     * @return 回転したVoxelShape
     */
    public static VoxelShape rotateBoxY270(VoxelShape shape) {
        return rotateBox(shape, RotateAngledAxis.Y270);
    }

    /**
     * VoxelShapeをX90度回転する
     *
     * @param shape 回転するVoxelShape
     * @return 回転したVoxelShape
     * @since 2.0
     */
    public static VoxelShape rotateBoxX90(VoxelShape shape) {
        return rotateBox(shape, RotateAngledAxis.X90);
    }

    /**
     * VoxelShapeをX180度回転する
     *
     * @param shape 回転するVoxelShape
     * @return 回転したVoxelShape
     * @since 2.0
     */
    public static VoxelShape rotateBoxX180(VoxelShape shape) {
        return rotateBox(shape, RotateAngledAxis.X180);
    }

    /**
     * VoxelShapeをX270度回転する
     *
     * @param shape 回転するVoxelShape
     * @return 回転したVoxelShape
     * @since 2.0
     */
    public static VoxelShape rotateBoxX270(VoxelShape shape) {
        return rotateBox(shape, RotateAngledAxis.X270);
    }

    /**
     * VoxelShapeをZ90度回転する
     *
     * @param shape 回転するVoxelShape
     * @return 回転したVoxelShape
     * @since 2.0
     */
    public static VoxelShape rotateBoxZ90(VoxelShape shape) {
        return rotateBox(shape, RotateAngledAxis.Z90);
    }

    /**
     * VoxelShapeをZ180度回転する
     *
     * @param shape 回転するVoxelShape
     * @return 回転したVoxelShape
     * @since 2.0
     */
    public static VoxelShape rotateBoxZ180(VoxelShape shape) {
        return rotateBox(shape, RotateAngledAxis.Z180);
    }

    /**
     * VoxelShapeをDirectionの方向に回転する
     *
     * @param shape     回転するVoxelShape
     * @param direction 回転する方向のDirection
     * @return 回転したVoxelShape
     */
    public static VoxelShape rotateBoxDirection(VoxelShape shape, Direction direction) {
        return switch (direction) {
            case SOUTH -> rotateBoxY180(shape);
            case EAST -> rotateBoxY270(shape);
            case WEST -> rotateBoxY90(shape);
            default -> shape;
        };
    }

    /**
     * リソースからVoxelShapeを読み込む
     *
     * @param location    ロケーション
     * @param classLoader 対象パッケージのクラス
     * @return 読み込まれたVoxelShape
     */
    public static VoxelShape getShapeFromResource(ResourceLocation location, Class<?> classLoader) {
        InputStream stream = FNDataUtil.resourceExtractor(classLoader, "data/" + location.getNamespace() + "/voxel_shape/" + location.getPath() + ".json");
        if (stream == null)
            throw new IllegalStateException("Failed to load shape: " + location);

        try (Reader reader = new InputStreamReader(stream, StandardCharsets.UTF_8)) {
            return IkisugiVoxelShapes.getInstance().getShapeFromJson(GSON.fromJson(reader, JsonObject.class), location);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * ４方向のVoxelShapeを取得
     *
     * @param shape 元のVoxelShapes
     * @return 4方向VoxelShapes
     */
    public static DirectionVoxelShapesBundle makeAllDirection(VoxelShape shape) {
        return new DirectionVoxelShapesBundle(shape);
    }
}
