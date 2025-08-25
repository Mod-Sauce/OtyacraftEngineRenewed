package org.modsauce.otyacraftenginerenewed.client.renderer;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import org.modsauce.otyacraftenginerenewed.OtyacraftEngine;
import org.modsauce.otyacraftenginerenewed.client.renderer.shader.OEShaders;
import net.minecraft.Util;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Function;

public class OERenderTypes extends RenderType {
    private static final ShaderStateShard SIMPLE_SPRITE_SOLID_SHADER = new ShaderStateShard(OEShaders::getRendertypeSimpleSpriteSolidShader);
    private static final ShaderStateShard SIMPLE_SPRITE_CUTOUT_SHADER = new ShaderStateShard(OEShaders::getRendertypeSimpleSpriteCutoutShader);
    private static final ShaderStateShard WAVE_SHADER = new ShaderStateShard(OEShaders::getRendertypeWave);

    private static final Function<ResourceLocation, RenderType> SIMPLE_SPRITE_SOLID = Util.memoize((resourceLocation) -> {
        RenderType.CompositeState compositeState = RenderType.CompositeState.builder().setShaderState(SIMPLE_SPRITE_SOLID_SHADER).setTextureState(new RenderStateShard.TextureStateShard(resourceLocation, false, false)).setTransparencyState(NO_TRANSPARENCY).setLightmapState(LIGHTMAP).setOverlayState(OVERLAY).createCompositeState(true);
        return OERenderTypes.create(OtyacraftEngine.MODID + "_simple_sprite_solid", DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.QUADS, 256, true, false, compositeState);
    });

    private static final Function<ResourceLocation, RenderType> SIMPLE_SPRITE_CUTOUT = Util.memoize((resourceLocation) -> {
        RenderType.CompositeState compositeState = RenderType.CompositeState.builder().setShaderState(SIMPLE_SPRITE_CUTOUT_SHADER).setTextureState(new RenderStateShard.TextureStateShard(resourceLocation, false, false)).setTransparencyState(NO_TRANSPARENCY).setLightmapState(LIGHTMAP).setOverlayState(OVERLAY).createCompositeState(true);
        return OERenderTypes.create(OtyacraftEngine.MODID + "_simple_sprite_cutout", DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.QUADS, 256, true, false, compositeState);
    });

    private static final Function<ResourceLocation, RenderType> WAVE = Util.memoize((resourceLocation) -> {
        RenderType.CompositeState compositeState = RenderType.CompositeState.builder().setShaderState(WAVE_SHADER).setTextureState(new RenderStateShard.TextureStateShard(resourceLocation, false, false)).setTransparencyState(NO_TRANSPARENCY).setLightmapState(LIGHTMAP).setOverlayState(OVERLAY).createCompositeState(true);
        return OERenderTypes.create(OtyacraftEngine.MODID + "_wave", DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.QUADS, 256, true, false, compositeState);
    });

    public OERenderTypes(String string, VertexFormat vertexFormat, VertexFormat.Mode mode, int i, boolean bl, boolean bl2, Runnable runnable, Runnable runnable2) {
        super(string, vertexFormat, mode, i, bl, bl2, runnable, runnable2);
    }

    public static RenderType simpleSpriteSolid(ResourceLocation location) {
        return SIMPLE_SPRITE_SOLID.apply(location);
    }

    public static RenderType simpleSpriteCutout(ResourceLocation location) {
        return SIMPLE_SPRITE_CUTOUT.apply(location);
    }

    public static RenderType wave(ResourceLocation location) {
        return WAVE.apply(location);
    }

    public static ShaderStateShard getSimpleSpriteCutoutShader() {
        return SIMPLE_SPRITE_CUTOUT_SHADER;
    }

    public static ShaderStateShard getSimpleSpriteSolidShader() {
        return SIMPLE_SPRITE_SOLID_SHADER;
    }

    public static ShaderStateShard getWaveShader() {
        return WAVE_SHADER;
    }
}
