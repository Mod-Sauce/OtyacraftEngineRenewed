package org.modsauce.otyacraftenginerenewed.client.shape;

import dev.architectury.registry.ReloadListenerRegistry;
import org.modsauce.otyacraftenginerenewed.OtyacraftEngine;
import org.modsauce.otyacraftenginerenewed.resources.PlatformResourceReloadListener;
import org.modsauce.otyacraftenginerenewed.shape.VoxelEdge;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ClientIVShapeManager extends PlatformResourceReloadListener<ClientIVShapeLoader> {
    private static final ClientIVShapeManager INSTANCE = new ClientIVShapeManager();
    private static final ResourceLocation VOXEL_SHAPE = ResourceLocation.fromNamespaceAndPath(OtyacraftEngine.MODID, "voxel_shape");
    private Map<ResourceLocation, VoxelClientShape> voxelClientShapes;
    private Map<ResourceLocation, VoxelClientShape> legacyVoxelClientShapes = new HashMap<>();

    public static ClientIVShapeManager getInstance() {
        return INSTANCE;
    }

    public void init() {
        ReloadListenerRegistry.register(PackType.CLIENT_RESOURCES, this);
    }

    @Override
    protected ClientIVShapeLoader prepare(@NotNull ResourceManager resourceManager, @NotNull ProfilerFiller profilerFiller) {
        return new ClientIVShapeLoader(resourceManager, profilerFiller);
    }

    @Override
    protected void apply(@NotNull ClientIVShapeLoader loader, @NotNull ResourceManager resourceManager, @NotNull ProfilerFiller profilerFiller) {
        voxelClientShapes = loader.getVoxelClientShapes();
    }

    @Nullable
    public VoxelClientShape getVoxelClientShape(@Nullable ResourceLocation location) {
        var ret = voxelClientShapes.get(location);
        if (ret != null)
            return ret;
        return legacyVoxelClientShapes.get(location);
    }

    public void addLegacyShapes(ResourceLocation location, Set<VoxelEdge> edges) {
        legacyVoxelClientShapes.put(location, new VoxelClientShape(null, null, edges));
    }

    @Override
    public @NotNull ResourceLocation getId() {
        return VOXEL_SHAPE;
    }
}
