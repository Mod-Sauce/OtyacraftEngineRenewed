package org.modsauce.otyacraftenginerenewed.client.renderer.blockentity;

import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.world.level.block.entity.BlockEntity;

public abstract class AbstractBlockEntityRenderer<T extends BlockEntity, S extends BlockEntityRenderState> implements BlockEntityRenderer<T, S> {
  private final BlockEntityRendererProvider.Context context;

  protected AbstractBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
    this.context = context;
  }

  public BlockEntityRendererProvider.Context getContext() {
    return context;
  }
}
