package org.modsauce.otyacraftenginerenewed.client.renderer.shader;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier; // was ResourceLocation before 26.1
import org.modsauce.otyacraftenginerenewed.OtyacraftEngine;

public class OEShaders {
  public static final RenderPipeline SIMPLE_SPRITE_SOLID = pipeline("rendertype_simple_sprite_solid");
  public static final RenderPipeline SIMPLE_SPRITE_CUTOUT = pipeline("rendertype_simple_sprite_cutout");
  public static final RenderPipeline WAVE = pipeline("rendertype_wave");

  private static RenderPipeline pipeline(String name) {
    Identifier shader = Identifier.fromNamespaceAndPath(OtyacraftEngine.MODID, "core/" + name);
    return RenderPipelines.register(
        RenderPipeline.builder(RenderPipelines.ENTITY_SNIPPET)
            .withLocation(Identifier.fromNamespaceAndPath(OtyacraftEngine.MODID, "pipeline/" + name))
            .withVertexShader(shader)
            .withFragmentShader(shader)
            .build());
  }
}
