package org.modsauce.otyacraftenginerenewed.client.shape;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.modsauce.otyacraftenginerenewed.shape.VoxelEdge;
import org.modsauce.otyacraftenginerenewed.shape.VoxelEntry;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class VoxelClientShape {
  private final String meta;
  private final String version;
  private final Set<VoxelEdge> renderEdges;
  private final Map<VoxelEntry, VoxelEdge[]> edgeCache = new HashMap<>();

  public VoxelClientShape(@Nullable String meta, @Nullable String version, @NotNull Set<VoxelEdge> edges) {
    this.meta = meta;
    this.version = version;
    this.renderEdges = ImmutableSet.copyOf(edges);
  }

  @NotNull
  public Set<VoxelEdge> getRenderEdges() {
    return renderEdges;
  }

  @Nullable
  public String getVersion() {
    return version;
  }

  @Nullable
  public String getMeta() {
    return meta;
  }

  @Nullable
  public static VoxelClientShape parse(JsonObject jo) {
    if (jo == null || !(jo.has("type") && jo.get("type").isJsonPrimitive() && "ikisugi_voxel_shape".equals(jo.get("type").getAsString())))
      return null;
    String meta = null;
    if (jo.has("meta") && jo.get("meta").isJsonPrimitive())
      meta = jo.get("meta").getAsString();

    String version = null;
    if (jo.has("version") && jo.get("version").isJsonPrimitive())
      version = jo.get("version").getAsString();

    ImmutableSet.Builder<VoxelEdge> edgeBuilder = new ImmutableSet.Builder<>();

    if (jo.has("edges") && jo.get("edges").isJsonArray()) {
      var ja = jo.getAsJsonArray("edges");
      for (JsonElement element : ja) {
        if (element.isJsonArray()) {
          var ed = VoxelEdge.parse(element.getAsJsonArray());
          if (ed != null)
            edgeBuilder.add(ed);
        }
      }
    }

    return new VoxelClientShape(meta, version, edgeBuilder.build());
  }

  @Override
  public String toString() {
    return "VoxelClientShape{" +
        "meta='" + meta + '\'' +
        ", version='" + version + '\'' +
        ", renderEdges count=" + renderEdges.size() +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    VoxelClientShape that = (VoxelClientShape) o;
    return Objects.equals(meta, that.meta) && Objects.equals(version, that.version) && Objects.equals(renderEdges, that.renderEdges);
  }

  @Override
  public int hashCode() {
    return Objects.hash(meta, version, renderEdges);
  }

  public VoxelEdge[] getEdgeCache(VoxelEntry entry) {
    return edgeCache.get(entry);
  }

  public void setEdgeCache(VoxelEntry entry, VoxelEdge[] edges) {
    edgeCache.put(entry, edges);
  }
}
