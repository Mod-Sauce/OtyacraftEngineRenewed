package dev.felnull.otyacraftengine.forge.client.handler;

import com.mojang.blaze3d.shaders.FogShape;
import dev.felnull.otyacraftengine.client.event.ClientCameraEvent;
import dev.felnull.otyacraftengine.client.event.OEClientEventHooks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.ViewportEvent;
import net.minecraftforge.event.level.LevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ClientHandlerForge {
    private static final Minecraft mc = Minecraft.getInstance();

    @SubscribeEvent
    public static void onLevelUnload(LevelEvent.Unload e) {
        if (e.getLevel().isClientSide() && e.getLevel() instanceof ClientLevel clientLevel)
            OEClientEventHooks.onLevelUnload(clientLevel);
    }

    @SubscribeEvent
    public static void onClickInput(InputEvent.InteractionKeyMappingTriggered e) {
        if (mc.player == null) return;
        if (e.getKeyMapping() == mc.options.keyAttack && !OEClientEventHooks.onHandAttack(mc.player.getItemInHand(e.getHand()))) {
            e.setCanceled(true);
            e.setSwingHand(false);
        }
    }

    @SubscribeEvent
    public static void onRenderFog(ViewportEvent.RenderFog e) {
        if (!OEClientEventHooks.onRenderFog(e.getCamera(), e.getMode(), e.getType(), e.getNearPlaneDistance(), e.getFarPlaneDistance(), e.getFogShape(), e.getPartialTick(), new ClientCameraEvent.RenderFogSetter() {
            @Override
            public void setStartDistance(float startDistance) {
                e.setNearPlaneDistance(startDistance);
            }

            @Override
            public void setEndDistance(float endDistance) {
                e.setFarPlaneDistance(endDistance);
            }

            @Override
            public void setFogShape(FogShape fogShape) {
                e.setFogShape(fogShape);
            }
        })) {
            e.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onComputeFogColor(ViewportEvent.ComputeFogColor e) {
        OEClientEventHooks.onComputeFogColor(e.getCamera(), e.getRed(), e.getGreen(), e.getBlue(), e.getPartialTick(), new ClientCameraEvent.FogColorSetter() {
            @Override
            public void setRed(float red) {
                e.setRed(red);
            }

            @Override
            public void setGreen(float green) {
                e.setGreen(green);
            }

            @Override
            public void setBlue(float blue) {
                e.setBlue(blue);
            }
        });
    }
}
