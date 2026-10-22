package org.modsauce.otyacraftenginerenewed.client.renderer;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.rendertype.RenderSetup;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Util;
import org.modsauce.otyacraftenginerenewed.OtyacraftEngine;
import org.modsauce.otyacraftenginerenewed.client.util.OEClientUtils;

import java.util.function.Function;

public final class OERenderTypes {

  public static final RenderPipeline SIMPLE_SPRITE_SOLID_PIPELINE = RenderPipelines.register(
      RenderPipeline.builder(RenderPipelines.ENTITY_SNIPPET)
          .withLocation(id("pipeline/simple_sprite_solid"))
          .withVertexShader(id("core/simple_sprite_solid"))
          .withFragmentShader(id("core/simple_sprite_solid"))
          .withSampler("Sampler0")
          .withSampler("Sampler2")
          .build());

  public static final RenderPipeline SIMPLE_SPRITE_CUTOUT_PIPELINE = RenderPipelines.register(
      RenderPipeline.builder(RenderPipelines.ENTITY_SNIPPET)
          .withLocation(id("pipeline/simple_sprite_cutout"))
          .withVertexShader(id("core/simple_sprite_cutout"))
          .withFragmentShader(id("core/simple_sprite_cutout"))
          .withSampler("Sampler0")
          .withSampler("Sampler2")
          .build());

  public static final RenderPipeline WAVE_PIPELINE = RenderPipelines.register(
      RenderPipeline.builder(RenderPipelines.ENTITY_SNIPPET)
          .withLocation(id("pipeline/wave"))
          .withVertexShader(id("core/wave"))
          .withFragmentShader(id("core/wave"))
          .withSampler("Sampler0")
          .withSampler("Sampler2")
          .build());

  private static final Function<Identifier, RenderType> SIMPLE_SPRITE_SOLID = Util.memoize(
      tex -> RenderType.create(OtyacraftEngine.MODID + "_simple_sprite_solid", setup(SIMPLE_SPRITE_SOLID_PIPELINE, tex)));

  private static final Function<Identifier, RenderType> SIMPLE_SPRITE_CUTOUT = Util.memoize(
      tex -> RenderType.create(OtyacraftEngine.MODID + "_simple_sprite_cutout", setup(SIMPLE_SPRITE_CUTOUT_PIPELINE, tex)));

  private static final Function<Identifier, RenderType> WAVE = Util.memoize(
      tex -> RenderType.create(OtyacraftEngine.MODID + "_wave", setup(WAVE_PIPELINE, tex)));

  private static RenderSetup setup(RenderPipeline pipeline, Identifier texture) {
    return RenderSetup.builder(pipeline)
        .withTexture("Sampler0", texture)
        .useLightmap()
        .useOverlay()
        .createRenderSetup();
  }

  private static Identifier id(String path) {
    return Identifier.fromNamespaceAndPath(OtyacraftEngine.MODID, path);
  }

  public static RenderType simpleSpriteSolid(Identifier location) {
    return SIMPLE_SPRITE_SOLID.apply(location);
  }

  public static RenderType simpleSpriteCutout(Identifier location) {
    return OEClientUtils.enableShader()
        ? RenderTypes.entityTranslucent(location)
        : SIMPLE_SPRITE_CUTOUT.apply(location);
  }

  public static RenderType wave(Identifier location) {
    return WAVE.apply(location);
  }

  private OERenderTypes() {}
}
