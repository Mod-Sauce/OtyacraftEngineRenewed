package org.modsauce.otyacraftenginerenewed.client.event;

import com.mojang.blaze3d.shaders.FogShape;
import dev.architectury.event.Event;
import dev.architectury.event.EventFactory;
import dev.architectury.event.EventResult;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.world.level.material.FogType;

/**
 * カメラ関係のイベント
 */
public interface ClientCameraEvent {
  Event<RenderFog> RENDER_FOG = EventFactory.createEventResult();
  Event<ComputeFogColor> COMPUTE_FOG_COLOR = EventFactory.createEventResult();

  interface RenderFog {
    EventResult onRenderFog(Camera camera, FogRenderer.FogMode fogMode, FogType fogType, float startDistance, float endDistance, FogShape fogShape, double delta, RenderFogSetter setter);
  }

  interface ComputeFogColor {
    EventResult onComputeFogColor(Camera camera, float red, float green, float blue, double delta, FogColorSetter fogColorSetter);
  }

  interface RenderFogSetter {
    void setStartDistance(float startDistance);

    void setEndDistance(float endDistance);

    void setFogShape(FogShape fogShape);
  }

  interface FogColorSetter {
    void setRed(float red);

    void setGreen(float green);

    void setBlue(float blue);
  }
}
