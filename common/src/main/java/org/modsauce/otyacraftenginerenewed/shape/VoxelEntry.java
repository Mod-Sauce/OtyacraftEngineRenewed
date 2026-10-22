package org.modsauce.otyacraftenginerenewed.shape;

import net.minecraft.resources.Identifier;

public class VoxelEntry {
  private final Identifier location;
  private final VoxelPose pose;

  public VoxelEntry(Identifier location, VoxelPose pose) {
    this.location = location;
    this.pose = pose;
  }

  public VoxelEntry(Identifier location) {
    this(location, new VoxelPose(0, 0, 0));
  }

  public Identifier getLocation() {
    return location;
  }

  public VoxelPose getPose() {
    return pose;
  }
}
