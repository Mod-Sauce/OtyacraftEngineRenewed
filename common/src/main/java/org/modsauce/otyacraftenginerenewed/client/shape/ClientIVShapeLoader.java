package org.modsauce.otyacraftenginerenewed.client.shape;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.io.Reader;
import java.util.Map;

/**
 * Client Ikisugi Voxel Shape Loader
 */
public class ClientIVShapeLoader {
    public static final Logger LOGGER = LogManager.getLogger(ClientIVShapeLoader.class);
    private static final Gson GSON = new Gson();
    private final Map<ResourceLocation, VoxelClientShape> voxelClientShapes;

    protected ClientIVShapeLoader(@NotNull ResourceManager resourceManager, @NotNull ProfilerFiller profilerFiller) {
        profilerFiller.startTick();

        ImmutableMap.Builder<ResourceLocation, VoxelClientShape> builder = ImmutableMap.builder();

        resourceManager.listResources("voxel_shape", loc -> loc.getPath().endsWith(".json")).forEach(((location, resource) -> {
            profilerFiller.push(location.toString());
            try (Reader reader = resource.openAsReader()) {
                JsonObject jo = GSON.fromJson(reader, JsonObject.class);
                var p = location.getPath();
                var vs = VoxelClientShape.parse(jo);
                if (vs != null)
                    builder.put(new ResourceLocation(location.getNamespace(), p.substring("voxel_shape/".length(), p.length() - ".json".length())), vs);
            } catch (Exception e) {
                LOGGER.error("Error occurred while loading shape resource json " + location, e);
            }
            profilerFiller.pop();
        }));

        profilerFiller.endTick();

        voxelClientShapes = builder.build();
    }

    public Map<ResourceLocation, VoxelClientShape> getVoxelClientShapes() {
        return voxelClientShapes;
    }
}
