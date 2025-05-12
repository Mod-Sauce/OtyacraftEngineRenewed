package org.modsauce.otyacraftenginerenewed.client.util;

import com.madgag.gif.fmsware.GifDecoder;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.blaze3d.platform.NativeImage;
import dev.felnull.fnjl.util.FNMath;
import org.modsauce.otyacraftenginerenewed.OtyacraftEngine;
import org.modsauce.otyacraftenginerenewed.client.entity.ClientPlayerInfoManager;
import org.modsauce.otyacraftenginerenewed.client.renderer.texture.*;
import org.modsauce.otyacraftenginerenewed.client.renderer.texture.impl.NativeTextureLoadResult;
import org.modsauce.otyacraftenginerenewed.client.renderer.texture.impl.TextureLoadProgressImpl;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.Tickable;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Consumer;

/**
 * テクスチャ関係のユーティリティ
 *
 * @author MORIMORI0317
 */
public final class OETextureUtils {
    private static final Minecraft mc = Minecraft.getInstance();
    private static final ResourceLocation ERROR_ICON = new ResourceLocation(OtyacraftEngine.MODID, "textures/gui/error_icon.png");
    private static ResourceLocation LOADING_ICON;

    /**
     * ネイティブ画像のテクスチャを読み込む
     *
     * @param stream ストリーム
     * @return テクスチャ
     * @throws IOException 例外
     */
    @NotNull
    public static DynamicTexture createNativeTexture(@NotNull InputStream stream, @Nullable Consumer<TextureLoadProgress> progress) throws IOException {
        if (progress != null) progress.accept(new TextureLoadProgressImpl("Image loading", 1, 0));

        byte[] data = stream.readAllBytes();

        if (progress != null) progress.accept(new TextureLoadProgressImpl("Image loading", 1, 1));

        try (var istream = new ByteArrayInputStream(data)) {
            GifDecoder decoder = new GifDecoder();
            if (decoder.read(istream) == 0) return DynamicGifTexture.create(decoder, progress);
        }

        try (var istream = new ByteArrayInputStream(data)) {
            return new DynamicTexture(NativeImage.read(istream));
        }
    }

    /**
     * テクスチャを開放する
     *
     * @param location テクスチャID
     */
    public synchronized static void freeTexture(@NotNull ResourceLocation location) {
        TextureManager textureManager = mc.getTextureManager();
        AbstractTexture abstractTexture = textureManager.byPath.get(location);
        if (abstractTexture != null) {
            if (abstractTexture instanceof Tickable) textureManager.tickableTextures.remove(abstractTexture);
            textureManager.safeClose(location, abstractTexture);
        }
    }

    /**
     * 非同期でネイティブテクスチャの読み込みと取得をする
     *
     * @param uuid   UUID
     * @param stream ストリーム
     * @return テクスチャ結果
     */
    @NotNull
    public static NativeTextureLoadResult getAndLoadNativeTextureAsync(@NotNull UUID uuid, @NotNull InputStream stream) {
        return NativeTextureManager.getInstance().getAndLoadTextureAsync(uuid, stream);
    }

    /**
     * ネイティブテクスチャの読み込みと取得をする
     *
     * @param uuid   UUID
     * @param stream ストリーム
     * @return テクスチャ結果
     */
    @NotNull
    public static NativeTextureLoadResult getAndLoadNativeTexture(@NotNull UUID uuid, @NotNull InputStream stream) {
        return NativeTextureManager.getInstance().getAndLoadTexture(uuid, stream);
    }

    /**
     * ネイティブテクスチャを取得
     *
     * @param uuid UUID
     * @return テクスチャ結果
     */
    @Nullable
    public static NativeTextureLoadResult getNativeTexture(@NotNull UUID uuid) {
        return NativeTextureManager.getInstance().getTexture(uuid);
    }

    /**
     * 非同期でURLテクスチャの読み込みと取得をする
     *
     * @param url    URL
     * @param cached キャッシュするかどうか
     * @return テクスチャ結果
     */
    @NotNull
    public static TextureLoadResult getAndLoadURLTextureAsync(@NotNull String url, boolean cached) {
        return URLTextureManager.getInstance().getAndAsyncLoad(url, cached);
    }

    /**
     * ロード中アイコンを取得
     *
     * @return ロケーション
     */
    @NotNull
    public static ResourceLocation getLoadingIcon() {
        if (LOADING_ICON == null) {
            ResourceManager rm = mc.getResourceManager();
            var r = rm.getResource(new ResourceLocation(OtyacraftEngine.MODID, "textures/gui/loading_icon.gif"));
            if (r.isPresent()) {
                try (var st = r.get().open()) {
                    var ret = getAndLoadNativeTexture(UUID.randomUUID(), st);
                    if (ret.isSuccess())
                        LOADING_ICON = ret.getLocation();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            if (LOADING_ICON == null)
                throw new IllegalStateException("Failed to loading icon being loaded.");
        }

        return LOADING_ICON;
    }

    /**
     * エラーアイコン
     *
     * @return ロケーション
     */
    @NotNull
    public static ResourceLocation getErrorIcon() {
        return ERROR_ICON;
    }

    /**
     * UUIDからプレイヤーテクスチャ取得
     * 存在しない場合はnullを返す
     *
     * @param type テクスチャタイプ
     * @param uuid プレイヤーUUID
     * @return プレイヤーテクスチャロケーション
     * @since 2.0
     */
    @Nullable
    public static ResourceLocation getPlayerTexture(MinecraftProfileTexture.Type type, @NotNull UUID uuid) {
        return ClientPlayerInfoManager.getInstance().getPlayerTexture(type, uuid);
    }

    /**
     * 名前からプレイヤーテクスチャ取得
     * 存在しない場合はnullを返す
     *
     * @param type テクスチャタイプ
     * @param name プレイヤー名
     * @return プレイヤーテクスチャロケーション
     */
    @Nullable
    public static ResourceLocation getPlayerTexture(MinecraftProfileTexture.Type type, @NotNull String name) {
        return ClientPlayerInfoManager.getInstance().getPlayerTexture(type, name);
    }

    /**
     * 名前からプレイヤーエリトラテクスチャ取得
     * 存在しない場合はnullを返す
     *
     * @param name プレイヤー名
     * @return プレイヤーエリトラテクスチャID
     */
    @Nullable
    public static ResourceLocation getPlayerElytraTexture(@NotNull String name) {
        return getPlayerTexture(MinecraftProfileTexture.Type.ELYTRA, name);
    }

    /**
     * 名前からプレイヤーマントテクスチャ取得
     * 存在しない場合はnullを返す
     *
     * @param name プレイヤー名
     * @return プレイヤーマントテクスチャID
     */
    @Nullable
    public static ResourceLocation getPlayerCapeTexture(@NotNull String name) {
        return getPlayerTexture(MinecraftProfileTexture.Type.CAPE, name);
    }

    /**
     * 名前からプレイヤースキンテクスチャ取得
     *
     * @param name プレイヤー名
     * @return プレイヤースキンテクスチャID
     */
    @NotNull
    public static ResourceLocation getPlayerSkinTexture(@NotNull String name) {
        return Objects.requireNonNull(getPlayerTexture(MinecraftProfileTexture.Type.SKIN, name));
    }

    /**
     * UUIDからプレイヤーエリトラテクスチャ取得
     * 存在しない場合はnullを返す
     *
     * @param uuid プレイヤーUUID
     * @return プレイヤーエリトラテクスチャID
     * @since 2.0
     */
    @Nullable
    public static ResourceLocation getPlayerElytraTexture(@NotNull UUID uuid) {
        return getPlayerTexture(MinecraftProfileTexture.Type.ELYTRA, uuid);
    }

    /**
     * UUIDからプレイヤーマントテクスチャ取得
     * 存在しない場合はnullを返す
     *
     * @param uuid プレイヤーUUID
     * @return プレイヤーマントテクスチャID
     * @since 2.0
     */
    @Nullable
    public static ResourceLocation getPlayerCapeTexture(@NotNull UUID uuid) {
        return getPlayerTexture(MinecraftProfileTexture.Type.CAPE, uuid);
    }

    /**
     * UUIDからプレイヤースキンテクスチャ取得
     *
     * @param uuid プレイヤーUUID
     * @return プレイヤースキンテクスチャID
     * @since 2.0
     */
    @NotNull
    public static ResourceLocation getPlayerSkinTexture(@NotNull UUID uuid) {
        return Objects.requireNonNull(getPlayerTexture(MinecraftProfileTexture.Type.SKIN, uuid));
    }

    /**
     * テクスチャのスケールを取得
     *
     * @param location テクスチャロケーション
     * @return スケール
     */
    public static TextureScale getTextureScale(ResourceLocation location) {
        if (location != null && mc.getTextureManager().getTexture(location) instanceof DynamicTexture texture) {
            int w = texture.getPixels().getWidth();
            int h = texture.getPixels().getHeight();
            var sc = FNMath.scale(w, h);
            return new TextureScale(sc.getX(), sc.getY());
        }
        return null;
    }
}
