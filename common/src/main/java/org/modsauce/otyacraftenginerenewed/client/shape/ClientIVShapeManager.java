package org.modsauce.otyacraftenginerenewed.client.shape;

import dev.architectury.registry.ReloadListenerRegistry;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.modsauce.otyacraftenginerenewed.OtyacraftEngine;
import org.modsauce.otyacraftenginerenewed.resources.PlatformResourceReloadListener;
import org.modsauce.otyacraftenginerenewed.shape.VoxelEdge;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ClientIVShapeManager extends PlatformResourceReloadListener<ClientIVShapeLoader> {
  private static final ClientIVShapeManager INSTANCE = new ClientIVShapeManager();
  private static final Identifier VOXEL_SHAPE = Identifier.fromNamespaceAndPath(OtyacraftEngine.MODID, "voxel_shape");

  private Map<Identifier, VoxelClientShape> voxelClientShapes = new HashMap<>();
  private final Map<Identifier, VoxelClientShape> legacyVoxelClientShapes = new HashMap<>();

  public static ClientIVShapeManager getInstance() {
    return INSTANCE;
  }

  public void init() {
    // Newer Architectury takes the ID as a separate argument
    ReloadListenerRegistry.register(PackType.CLIENT_RESOURCES, this, VOXEL_SHAPE);
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
  public VoxelClientShape getVoxelClientShape(@Nullable Identifier location) {
    if (location == null)
      return null;
    var ret = voxelClientShapes.get(location);
    if (ret != null)
      return ret;
    return legacyVoxelClientShapes.get(location);
  }

  public void addLegacyShapes(Identifier location, Set<VoxelEdge> edges) {
    legacyVoxelClientShapes.put(location, new VoxelClientShape(null, null, edges));
  }

  // No @Override on purpose: compiles whether or not the base class still declares getId()
  public @NotNull Identifier getId() {
    return VOXEL_SHAPE;
  }
}
