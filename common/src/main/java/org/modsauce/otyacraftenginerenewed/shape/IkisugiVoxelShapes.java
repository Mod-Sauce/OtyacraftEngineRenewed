package org.modsauce.otyacraftenginerenewed.shape;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dev.architectury.utils.Env;
import dev.architectury.utils.EnvExecutor;
import org.modsauce.otyacraftenginerenewed.util.OEVoxelShapeUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.apache.commons.lang3.ArrayUtils;
import org.modsauce.otyacraftenginerenewed.client.shape.ClientIVShapeManager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class IkisugiVoxelShapes {
    private static final IkisugiVoxelShapes INSTANCE = new IkisugiVoxelShapes();

    public static IkisugiVoxelShapes getInstance() {
        return INSTANCE;
    }

    public VoxelShape getShapeFromJson(JsonObject shapeJson, ResourceLocation location) {
        var version = shapeJson.get("version");

        if (version != null && version.isJsonPrimitive()) {
            if (version.getAsInt() == 2) return getShapeFromJsonV2(shapeJson, location);

            if (version.getAsInt() == 3) return getShapeFromJsonV3(shapeJson, location);

            if (version.getAsInt() >= 4)
                throw new IllegalStateException("Not support ikisugi voxel shape version: " + version.getAsInt());
        }

        return getShapeFromJsonV1(shapeJson);
    }

    private VoxelShape getShapeFromJsonV1(JsonObject shapeJ) {
        List<VoxelShape> shapes = new ArrayList<>();
        for (JsonElement jshape : shapeJ.getAsJsonArray("shapes")) {
            JsonArray ja = jshape.getAsJsonArray();
            VoxelShape shape = OEVoxelShapeUtils.makeBox(ja.get(0).getAsDouble(), ja.get(1).getAsDouble(), ja.get(2).getAsDouble(), ja.get(3).getAsDouble(), ja.get(4).getAsDouble(), ja.get(5).getAsDouble());
            shapes.add(shape);
        }
        return OEVoxelShapeUtils.uniteBox(shapes);
    }

    private VoxelShape getShapeFromJsonV2(JsonObject shapeJ, ResourceLocation location) {
        List<VoxelShape> shapes = new ArrayList<>();
        for (JsonElement jshape : shapeJ.getAsJsonArray("shapes")) {
            JsonArray ja = jshape.getAsJsonArray();
            VoxelShape shape = OEVoxelShapeUtils.makeBox(ja.get(0).getAsDouble() * 16, ja.get(1).getAsDouble() * 16, ja.get(2).getAsDouble() * 16, ja.get(3).getAsDouble() * 16, ja.get(4).getAsDouble() * 16, ja.get(5).getAsDouble() * 16);
            shapes.add(shape);
        }
        var shape = OEVoxelShapeUtils.uniteBox(shapes);

        EnvExecutor.runInEnv(Env.CLIENT, () -> () -> {
            Set<VoxelEdge> edges = new HashSet<>();
            for (JsonElement jshape : shapeJ.getAsJsonArray("edges")) {
                var ed = VoxelEdge.parse(jshape.getAsJsonArray());
                if (ed != null) edges.add(ed);
            }
            ClientIVShapeManager.getInstance().addLegacyShapes(location, edges);
        });

        ((IkisugiVoxelShape) shape).setRenderEdges(new VoxelEntry(location));
        return shape;
    }

    private VoxelShape getShapeFromJsonV3(JsonObject shapeJ, ResourceLocation location) {
        List<VoxelShape> shapes = new ArrayList<>();
        for (JsonElement jshape : shapeJ.getAsJsonArray("shapes")) {
            JsonArray ja = jshape.getAsJsonArray();
            VoxelShape shape = OEVoxelShapeUtils.makeBox(ja.get(0).getAsDouble() * 16, ja.get(1).getAsDouble() * 16, ja.get(2).getAsDouble() * 16, ja.get(3).getAsDouble() * 16, ja.get(4).getAsDouble() * 16, ja.get(5).getAsDouble() * 16);
            shapes.add(shape);
        }

        var shape = OEVoxelShapeUtils.uniteBox(shapes);
        ResourceLocation relocation;
        if (shapeJ.has("render_edges") && shapeJ.get("render_edges").isJsonPrimitive()) {
            var reloc = shapeJ.get("render_edges").getAsString();
            relocation = ResourceLocation.parse(reloc);
        } else {
            relocation = location;
        }

        ((IkisugiVoxelShape) shape).setRenderEdges(new VoxelEntry(relocation));
        return shape;
    }

    public VoxelShape copy(VoxelShape target, IkisugiVoxelShape source) {
        ((IkisugiVoxelShape) target).setRenderEdges(source.getRenderEdges());
        return target;
    }

    public VoxelShape unite(VoxelShape target, VoxelShape... shapes) {
        VoxelEntry[] voxelEntries = ((IkisugiVoxelShape) target).getRenderEdges();

        for (VoxelShape shape : shapes) {
            var ve = ((IkisugiVoxelShape) shape).getRenderEdges();
            if (ve != null) voxelEntries = ArrayUtils.addAll(voxelEntries, ve);
        }

        ((IkisugiVoxelShape) target).setRenderEdges(voxelEntries);
        return target;
    }

    public VoxelShape unite(VoxelShape target, List<IkisugiVoxelShape> shapes) {
        VoxelEntry[] voxelEntries = ((IkisugiVoxelShape) target).getRenderEdges();

        for (IkisugiVoxelShape shape : shapes) {
            var ve = shape.getRenderEdges();
            if (ve != null) voxelEntries = ArrayUtils.addAll(voxelEntries, ve);
        }

        ((IkisugiVoxelShape) target).setRenderEdges(voxelEntries);
        return target;
    }

    public VoxelShape move(VoxelShape target, IkisugiVoxelShape source, double x, double y, double z) {
        if (source.getRenderEdges() == null) return target;
        x /= 16;
        y /= 16;
        z /= 16;
        VoxelEntry[] voxelEntries = null;

        for (VoxelEntry renderEdge : source.getRenderEdges()) {
            var pp = renderEdge.getPose();
            voxelEntries = ArrayUtils.addAll(voxelEntries, new VoxelEntry(renderEdge.getLocation(), new VoxelPose(pp.x() + x, pp.y() + y, pp.z() + z, pp.axis())));
        }

        ((IkisugiVoxelShape) target).setRenderEdges(voxelEntries);
        return target;
    }

    public VoxelShape rotate(VoxelShape target, IkisugiVoxelShape source, RotateAngledAxis angledAxis) {
        VoxelEntry[] voxelEntries = null;

        for (VoxelEntry renderEdge : source.getRenderEdges()) {
            var pp = renderEdge.getPose();
            voxelEntries = ArrayUtils.addAll(voxelEntries, new VoxelEntry(renderEdge.getLocation(), new VoxelPose(pp.x(), pp.y(), pp.z(), ArrayUtils.add(pp.axis(), angledAxis))));
        }

        ((IkisugiVoxelShape) target).setRenderEdges(voxelEntries);
        return target;
    }
}
