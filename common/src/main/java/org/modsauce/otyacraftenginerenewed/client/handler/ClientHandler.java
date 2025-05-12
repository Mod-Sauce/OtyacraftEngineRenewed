package org.modsauce.otyacraftenginerenewed.client.handler;

import dev.architectury.event.events.client.ClientLifecycleEvent;
import dev.architectury.event.events.client.ClientReloadShadersEvent;
import dev.architectury.event.events.client.ClientTickEvent;
import org.modsauce.otyacraftenginerenewed.OEConfig;
import org.modsauce.otyacraftenginerenewed.OtyacraftEngine;
import org.modsauce.otyacraftenginerenewed.client.entity.ClientPlayerInfoManager;
import org.modsauce.otyacraftenginerenewed.client.event.MoreClientLifecycleEvents;
import org.modsauce.otyacraftenginerenewed.client.renderer.shader.OEShaders;
import org.modsauce.otyacraftenginerenewed.client.renderer.texture.URLTextureManager;
import org.modsauce.otyacraftenginerenewed.entity.PlayerInfoManager;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigHolder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.server.packs.resources.ResourceProvider;
import net.minecraft.world.InteractionResult;

import java.io.IOException;

public class ClientHandler {
    public static void init() {
        ClientLifecycleEvent.CLIENT_STOPPING.register(ClientHandler::onStopping);
        ClientLifecycleEvent.CLIENT_LEVEL_LOAD.register(ClientHandler::onLevelLoad);
        MoreClientLifecycleEvents.CLIENT_LEVEL_UNLOAD.register(ClientHandler::onLevelUnload);
        ClientTickEvent.CLIENT_POST.register(ClientHandler::ontClientTick);
        ClientReloadShadersEvent.EVENT.register(ClientHandler::onShaderReload);
        AutoConfig.getConfigHolder((Class<OEConfig>) OtyacraftEngine.getConfig().getClass()).registerSaveListener(ClientHandler::onConfigSave);
    }

    private static void onShaderReload(ResourceProvider provider, ClientReloadShadersEvent.ShadersSink sink) {
        try {
            OEShaders.reload(provider, sink);
        } catch (IOException e) {
            throw new RuntimeException("could not reload test shaders", e);
        }
    }

    private static void onStopping(Minecraft minecraft) {
        URLTextureManager.getInstance().save();
    }

    private static void onLevelLoad(ClientLevel world) {
        URLTextureManager.getInstance().release();
        PlayerInfoManager.getInstance().clear();
        ClientPlayerInfoManager.getInstance().clear();
    }

    private static void onLevelUnload(ClientLevel world) {
        URLTextureManager.getInstance().release();
        PlayerInfoManager.getInstance().clear();
        ClientPlayerInfoManager.getInstance().clear();
    }

    private static void ontClientTick(Minecraft instance) {
        URLTextureManager.getInstance().tick();
    }

    private static InteractionResult onConfigSave(ConfigHolder<OEConfig> configHolder, OEConfig config) {
        URLTextureManager.getInstance().release();
        return InteractionResult.PASS;
    }
}
