package org.modsauce.otyacraftenginerenewed.client.renderer.texture;

import net.minecraft.util.Mth;

public interface TextureLoadProgress {

    int getTotal();

    int getComplete();

    default float getParent() {
        return Mth.clamp((float) getComplete() / (float) getTotal(), 0f, 1f);
    }

    String getStateName();
}
