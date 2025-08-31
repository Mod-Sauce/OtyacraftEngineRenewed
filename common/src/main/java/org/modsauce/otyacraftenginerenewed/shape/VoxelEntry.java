package org.modsauce.otyacraftenginerenewed.shape;

import net.minecraft.resources.ResourceLocation;

public class VoxelEntry {
  private final ResourceLocation location;
  private final VoxelPose pose;

  public VoxelEntry(ResourceLocation location, VoxelPose pose) {
    this.location = location;
    this.pose = pose;
  }

  public VoxelEntry(ResourceLocation location) {
    this(location, new VoxelPose(0, 0, 0));
  }

  public ResourceLocation getLocation() {
    return location;
  }

  public VoxelPose getPose() {
    return pose;
  }
}
