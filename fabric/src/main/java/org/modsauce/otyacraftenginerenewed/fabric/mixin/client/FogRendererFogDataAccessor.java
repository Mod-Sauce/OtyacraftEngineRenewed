package org.modsauce.otyacraftenginerenewed.fabric.mixin.client;

import com.mojang.blaze3d.shaders.FogShape;
import net.minecraft.client.renderer.FogRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(FogRenderer.FogData.class)
public interface FogRendererFogDataAccessor {
    @Accessor("start")
    void setStart(float start);

    @Accessor("end")
    void setEnd(float end);

    @Accessor("shape")
    void setShape(FogShape shape);
}
