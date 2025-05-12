package org.modsauce.otyacraftenginerenewed.client.gui;

import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

/**
 * テクスチャの画像ロケーション、表示位置の指定
 *
 * @param location テクスチャロケーション
 * @param width    テクスチャの幅
 * @param height   テクスチャの高さ
 * @param u0       テクスチャの開始地点X
 * @param v0       テクスチャの開始地点Y
 * @param u1       テクスチャの終了地点X
 * @param v1       テクスチャの終了地点Y
 */
public record TextureRegion(@NotNull ResourceLocation location, float width, float height, float u0, float v0, float u1, float v1) {
    /**
     * テクスチャの画像ロケーション、表示位置の指定
     *
     * @param location テクスチャロケーション
     * @param u0       テクスチャの開始地点X
     * @param v0       テクスチャの開始地点Y
     * @param u1       テクスチャの終了地点X
     * @param v1       テクスチャの終了地点Y
     */
    public TextureRegion(@NotNull ResourceLocation location, float u0, float v0, float u1, float v1) {
        this(location, 256, 256, u0, v0, u1, v1);
    }

    /**
     * 相対位置で指定
     *
     * @param location テクスチャロケーション
     * @param width    テクスチャの幅
     * @param height   テクスチャの高さ
     * @param u0       テクスチャの開始地点X
     * @param v0       テクスチャの開始地点Y
     * @param ru1      テクスチャの相対終了地点X
     * @param rv1      テクスチャの相対終了地点Y
     * @return TextureRegion
     */
    public static TextureRegion relative(@NotNull ResourceLocation location, float width, float height, float u0, float v0, float ru1, float rv1) {
        return new TextureRegion(location, width, height, u0, v0, u0 + ru1, v0 + rv1);
    }

    /**
     * 相対位置で指定
     *
     * @param location テクスチャロケーション
     * @param u0       テクスチャの開始地点X
     * @param v0       テクスチャの開始地点Y
     * @param ru1      テクスチャの相対終了地点X
     * @param rv1      テクスチャの相対終了地点Y
     * @return TextureRegion
     */
    public static TextureRegion relative(@NotNull ResourceLocation location, float u0, float v0, float ru1, float rv1) {
        return new TextureRegion(location, u0, v0, u0 + ru1, v0 + rv1);
    }

    public float uvWidth() {
        return u1 - u0;
    }

    public float uvHeight() {
        return v1 - v0;
    }
}
