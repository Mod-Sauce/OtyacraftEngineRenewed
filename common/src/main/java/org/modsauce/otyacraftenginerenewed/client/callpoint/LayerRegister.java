package org.modsauce.otyacraftenginerenewed.client.callpoint;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;

import java.util.function.Function;

public interface LayerRegister {

  @Deprecated
  default <T extends LivingEntity, S extends LivingEntityRenderState, M extends EntityModel<? super S>> void addLayer(
          EntityType<T> entityType,
          Function<LivingEntityRenderer<T, S, M>, RenderLayer<S, M>> layer) {
    addLayerV2(entityType, new LayerFactory<S, M>() {
      @Override
      public RenderLayer<S, M> create(RenderLayerParent<S, M> parentLayer, EntityModelSet modelSet) {
        return layer.apply((LivingEntityRenderer<T, S, M>) parentLayer);
      }
    });
  }

  // Primary modern method using RenderState and EntityModel
  <T extends LivingEntity, S extends LivingEntityRenderState, M extends EntityModel<? super S>> void addLayerV2(
          EntityType<T> entityType,
          LayerFactory<S, M> layerFactory
  );

  // Optional: If you need legacy/direct entity-model factories without state,
  // give them distinct method names (e.g., addLegacyLayer) to prevent erasure clashes completely.
  //
  // <T extends LivingEntity, M extends EntityModel<T>> void addLegacyLayer(
  //         EntityType<T> entityType,
  //         LegacyLayerFactory<T, M> layer
  // );

  interface LayerFactory<S extends LivingEntityRenderState, M extends EntityModel<? super S>> {
    RenderLayer<S, M> create(RenderLayerParent<S, M> parentLayer, EntityModelSet modelSet);
  }
}