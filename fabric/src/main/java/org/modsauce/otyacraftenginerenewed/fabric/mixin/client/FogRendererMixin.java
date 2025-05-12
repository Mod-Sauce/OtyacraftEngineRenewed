package org.modsauce.otyacraftenginerenewed.fabric.mixin.client;

import com.mojang.blaze3d.shaders.FogShape;
import org.modsauce.otyacraftenginerenewed.client.event.ClientCameraEvent;
import org.modsauce.otyacraftenginerenewed.client.event.OEClientEventHooks;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.material.FogType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(FogRenderer.class)
public class FogRendererMixin {

    @Inject(method = "setupColor", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;clearColor(FFFF)V", remap = false, ordinal = 1))
    private static void setupColor(Camera camera, float f, ClientLevel clientLevel, int i, float g, CallbackInfo ci) {
        OEClientEventHooks.onComputeFogColor(camera, FogRendererAccessor.getFogRed(), FogRendererAccessor.getFogGreen(), FogRendererAccessor.getFogBlue(), f, new ClientCameraEvent.FogColorSetter() {
            @Override
            public void setRed(float red) {
                FogRendererAccessor.setFogRed(red);
            }

            @Override
            public void setGreen(float green) {
                FogRendererAccessor.setFogGreen(green);
            }

            @Override
            public void setBlue(float blue) {
                FogRendererAccessor.setFogBlue(blue);
            }
        });
    }

    @Inject(method = "setupFog", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;setShaderFogStart(F)V", remap = false, ordinal = 0, shift = At.Shift.BY, by = -1), locals = LocalCapture.CAPTURE_FAILHARD)
    private static void setupFog(Camera camera, FogRenderer.FogMode fogMode, float f, boolean bl, float g, CallbackInfo ci, FogType fogType, Entity entity, FogRenderer.FogData fogData) {
        float[] distance = {fogData.start, fogData.end};
        FogShape[] shapes = {fogData.shape};

        if (!OEClientEventHooks.onRenderFog(camera, fogMode, fogType, fogData.start, fogData.end, fogData.shape, g, new ClientCameraEvent.RenderFogSetter() {
            @Override
            public void setStartDistance(float startDistance) {
                distance[0] = startDistance;
            }

            @Override
            public void setEndDistance(float endDistance) {
                distance[1] = endDistance;
            }

            @Override
            public void setFogShape(FogShape fogShape) {
                shapes[0] = fogShape;
            }
        })) {
            ((FogRendererFogDataAccessor) fogData).setStart(distance[0]);
            ((FogRendererFogDataAccessor) fogData).setEnd(distance[1]);
            ((FogRendererFogDataAccessor) fogData).setShape(shapes[0]);
        }
    }

}
