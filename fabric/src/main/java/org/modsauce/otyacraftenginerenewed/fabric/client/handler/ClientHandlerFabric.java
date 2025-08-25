package org.modsauce.otyacraftenginerenewed.fabric.client.handler;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import org.modsauce.otyacraftenginerenewed.client.callpoint.ClientCallPointManager;
import org.modsauce.otyacraftenginerenewed.client.callpoint.LayerRegister;
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityFeatureRendererRegistrationCallback;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;

public class ClientHandlerFabric {
    private static Multimap<EntityType<?>, LayerRegister.LayerFactory<?, ?>> LAYERS;

    public static void init() {
        LivingEntityFeatureRendererRegistrationCallback.EVENT.register(ClientHandlerFabric::registerRenderers);
    }

    private static void registerRenderers(EntityType<? extends LivingEntity> entityType, LivingEntityRenderer<?, ?> entityRenderer, LivingEntityFeatureRendererRegistrationCallback.RegistrationHelper registrationHelper, EntityRendererProvider.Context context) {
        if (LAYERS == null)
            LAYERS = getLayers();

        LAYERS.get(entityType).stream()
                .map(layerFactory -> create(layerFactory, entityRenderer, context.getModelSet()))
                .forEach(registrationHelper::register);
    }

    private static Multimap<EntityType<?>, LayerRegister.LayerFactory<?, ?>> getLayers() {
        ImmutableMultimap.Builder<EntityType<?>, LayerRegister.LayerFactory<?, ?>> builder = ImmutableMultimap.builder();

        ClientCallPointManager.getInstance().call().onLayerRegistry(new LayerRegister() {
            @Override
            public <T extends LivingEntity, M extends EntityModel<T>> void addLayerV2(EntityType<T> entityType, LayerFactory<T, M> layer) {
                builder.put(entityType, layer);
            }
        });

        return builder.build();
    }

    private static <L extends LivingEntity, E extends EntityModel<L>> RenderLayer<L, E> create(LayerRegister.LayerFactory<?, ?> factory, RenderLayerParent<?, ?> parentLayer, EntityModelSet modelSet) {
        LayerRegister.LayerFactory<L, E> f = (LayerRegister.LayerFactory<L, E>) factory;
        return f.create((RenderLayerParent<L, E>) parentLayer, modelSet);
    }
}
