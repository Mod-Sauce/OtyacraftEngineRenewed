package org.modsauce.otyacraftenginerenewed.fabric.mixin.client;

import net.minecraft.client.renderer.FogRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(FogRenderer.class)
public interface FogRendererAccessor {
    @Accessor("fogRed")
    static void setFogRed(float red) {
        throw new AssertionError();
    }

    @Accessor("fogGreen")
    static void setFogGreen(float green) {
        throw new AssertionError();
    }

    @Accessor("fogBlue")
    static void setFogBlue(float blue) {
        throw new AssertionError();
    }

    @Accessor("fogRed")
    static float getFogRed() {
        throw new AssertionError();
    }

    @Accessor("fogGreen")
    static float getFogGreen() {
        throw new AssertionError();
    }

    @Accessor("fogBlue")
    static float getFogBlue() {
        throw new AssertionError();
    }
}
