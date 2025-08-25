package org.modsauce.otyacraftenginerenewed.client.renderer.shader;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import dev.architectury.event.events.client.ClientReloadShadersEvent;
import org.modsauce.otyacraftenginerenewed.OtyacraftEngine;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.server.packs.resources.ResourceProvider;

import java.io.IOException;

public class OEShaders {
    private static ShaderInstance RENDERTYPE_SIMPLE_SPRITE_SOLID_SHADER;
    private static ShaderInstance RENDERTYPE_SIMPLE_SPRITE_CUTOUT_SHADER;
    private static ShaderInstance RENDERTYPE_WAVE;

    public static void reload(ResourceProvider provider, ClientReloadShadersEvent.ShadersSink sink) throws IOException {

        sink.registerShader(new ShaderInstance(provider, OtyacraftEngine.MODID + "_rendertype_simple_sprite_solid", DefaultVertexFormat.NEW_ENTITY), shaderInstance -> {
            RENDERTYPE_SIMPLE_SPRITE_SOLID_SHADER = shaderInstance;
        });
        sink.registerShader(new ShaderInstance(provider, OtyacraftEngine.MODID + "_rendertype_simple_sprite_cutout", DefaultVertexFormat.NEW_ENTITY), shaderInstance -> {
            RENDERTYPE_SIMPLE_SPRITE_CUTOUT_SHADER = shaderInstance;
        });
        sink.registerShader(new ShaderInstance(provider, OtyacraftEngine.MODID + "_rendertype_wave", DefaultVertexFormat.NEW_ENTITY), shaderInstance -> {
            RENDERTYPE_WAVE = shaderInstance;
        });
    }

    public static ShaderInstance getRendertypeSimpleSpriteSolidShader() {
        return RENDERTYPE_SIMPLE_SPRITE_SOLID_SHADER;
    }

    public static ShaderInstance getRendertypeSimpleSpriteCutoutShader() {
        return RENDERTYPE_SIMPLE_SPRITE_CUTOUT_SHADER;
    }

    public static ShaderInstance getRendertypeWave() {
        return RENDERTYPE_WAVE;
    }
}
