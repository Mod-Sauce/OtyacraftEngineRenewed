package org.modsauce.otyacraftenginerenewed.neoforge.client.handler;

import net.minecraft.client.resources.PlayerSkin;
import org.modsauce.otyacraftenginerenewed.OtyacraftEngine;
import org.modsauce.otyacraftenginerenewed.client.OtyacraftEngineClient;
import org.modsauce.otyacraftenginerenewed.client.callpoint.ClientCallPointManager;
import org.modsauce.otyacraftenginerenewed.client.callpoint.LayerRegister;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@Mod.EventBusSubscriber(modid = OtyacraftEngine.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientBusHandler {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        NeoForge.EVENT_BUS.register(ClientHandlerForge.class);
        NeoForge.EVENT_BUS.register(RenderHandlerForge.class);
        OtyacraftEngineClient.init();
    }

    @SubscribeEvent
    public static void onAddLayers(EntityRenderersEvent.AddLayers e) {
        ClientCallPointManager.getInstance().call().onLayerRegistry(new LayerRegister() {
            @Override
            public <T extends LivingEntity, M extends EntityModel<T>> void addLayerV2(EntityType<T> entityType, LayerFactory<T, M> layer) {
                if (entityType == EntityType.PLAYER) {
                    for (PlayerSkin.Model skin : e.getSkins()) {
                        var renderer = e.getSkin(PlayerSkin.Model.valueOf(String.valueOf(skin)));
                        if (renderer != null) {
                            RenderLayer theLayer = layer.create((RenderLayerParent<T, M>) renderer, e.getEntityModels());
                            renderer.addLayer(theLayer);
                        }
                    }
                } else {
                    LivingEntityRenderer<T, M> renderer = e.getRenderer(entityType);
                    if (renderer != null) {
                        RenderLayer<T, M> theLayer = layer.create(renderer, e.getEntityModels());
                        renderer.addLayer(theLayer);
                    }
                }
            }
        });
    }
}
