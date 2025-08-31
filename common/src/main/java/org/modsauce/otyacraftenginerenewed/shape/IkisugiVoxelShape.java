package org.modsauce.otyacraftenginerenewed.shape;

public interface IkisugiVoxelShape {
  VoxelEntry[] getRenderEdges();

  void setRenderEdges(VoxelEntry... edges);
}
