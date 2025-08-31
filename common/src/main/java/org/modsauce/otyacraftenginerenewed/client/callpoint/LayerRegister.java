package org.modsauce.otyacraftenginerenewed.client.callpoint;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;

import java.util.function.Function;

public interface LayerRegister {
  /**
   * {@link #addLayerV2(EntityType, LayerFactory)}を利用してください
   */
  @Deprecated
  default <T extends LivingEntity> void addLayer(EntityType<T> entityType, Function<LivingEntityRenderer<T, ? extends EntityModel<T>>, RenderLayer<T, ? extends EntityModel<T>>> layer) {
    addLayerV2(entityType, new LayerFactory<T, EntityModel<T>>() {
      @Override
      public RenderLayer<T, EntityModel<T>> create(RenderLayerParent<T, EntityModel<T>> parentLayer, EntityModelSet modelSet) {
        return (RenderLayer<T, EntityModel<T>>) layer.apply((LivingEntityRenderer<T, ? extends EntityModel<T>>) parentLayer);
      }
    });
  }

  default <T extends LivingEntity, M extends EntityModel<T>> void addLayerV2(EntityType<T> entityType, LayerFactory<T, M> layer) {
  }

  interface LayerFactory<T extends LivingEntity, M extends EntityModel<T>> {
    RenderLayer<T, M> create(RenderLayerParent<T, M> parentLayer, EntityModelSet modelSet);
  }
}
