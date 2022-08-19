package dev.felnull.otyacraftengine.client.util;

import com.mojang.authlib.GameProfile;
import com.mojang.blaze3d.platform.InputConstants;
import dev.felnull.otyacraftengine.client.entity.ClientPlayerInfoManager;
import dev.felnull.otyacraftengine.client.entity.PlayerNameByUUIDResult;
import dev.felnull.otyacraftengine.client.entity.PlayerUUIDByNameResult;
import dev.felnull.otyacraftengine.explatform.client.OEClientExpectPlatform;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.util.tinyfd.TinyFileDialogs;

import java.io.File;
import java.nio.file.Path;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * クライアントのユーティリティ
 *
 * @author MORIMORI0317
 */
public class OEClientUtils {
    private static final Minecraft mc = Minecraft.getInstance();

    /**
     * 特定の時間でループする値
     *
     * @param loopTime 0.0から1.0までの時間
     * @return 0.0~1.0までの値
     */
    public static float getParSecond(long loopTime) {
        return (float) (System.currentTimeMillis() % loopTime) / (float) loopTime;
    }

    /**
     * keyMappingからkeyを取得
     *
     * @param keyMapping 対象
     * @return key
     */
    public static InputConstants.Key getKey(@NotNull KeyMapping keyMapping) {
        return OEClientExpectPlatform.getKey(keyMapping);
    }

    /**
     * プレイヤー名から一部欠落したプロファイルを読み込む
     * ほとんどテクスチャ取得用
     *
     * @param name プレイヤー名
     * @return プロファイ
     */
    @NotNull
    public static GameProfile getPlayerLackProfileTolerance(@NotNull String name) {
        return ClientPlayerInfoManager.getInstance().getLackProfileTolerance(name);
    }

    /**
     * プレイヤー名からプレイヤーUUIDを取得する
     * {@link dev.felnull.otyacraftengine.util.OEPlayerUtils#getUUIDByName(String)}との違いはクライアント側で取得できるプレイヤーからも検索する
     *
     * @param name プレイヤー名
     * @return UUID
     */
    @NotNull
    public static Optional<UUID> getPlayerUUIDByName(@NotNull String name) {
        return ClientPlayerInfoManager.getInstance().getUUIDByName(name);
    }

    /**
     * 非同期でプレイヤー名からプレイヤーUUIDを取得する
     * {@link dev.felnull.otyacraftengine.util.OEPlayerUtils#getUUIDByNameAsync(String)}との違いはクライアント側で取得できるプレイヤーからも検索する
     *
     * @param name プレイヤー名
     * @return UUID
     */
    @NotNull
    public static CompletableFuture<Optional<UUID>> getPlayerUUIDByNameAsync(@NotNull String name) {
        return ClientPlayerInfoManager.getInstance().getUUIDByNameAsync(name);
    }

    /**
     * プレイヤーUUIDからプレイヤー名を取得する
     * {@link dev.felnull.otyacraftengine.util.OEPlayerUtils#getNameByUUID(UUID)}との違いはクライアント側で取得できるプレイヤーからも検索する
     *
     * @param uuid プレイヤーUUID
     * @return 名前
     */
    @NotNull
    public static Optional<String> getPlayerNameByUUID(@NotNull UUID uuid) {
        return ClientPlayerInfoManager.getInstance().getNameByUUID(uuid);
    }

    /**
     * 非同期でプレイヤーUUIDからプレイヤー名を取得する
     * {@link dev.felnull.otyacraftengine.util.OEPlayerUtils#getNameByUUIDAsync(UUID)}との違いはクライアント側で取得できるプレイヤーからも検索する
     *
     * @param uuid プレイヤーUUID
     * @return 名前
     */
    @NotNull
    public static CompletableFuture<Optional<String>> getPlayerNameByUUIDAsync(@NotNull UUID uuid) {
        return ClientPlayerInfoManager.getInstance().getNameByUUIDAsync(uuid);
    }

    /**
     * プレイヤー名からプレイヤーUUIDを取得する
     * {@link #getPlayerUUIDByName(String)}との違いは取得失敗時や、取得中でも即座に結果を返す
     * 描画など即座に出力するために利用
     *
     * @param name プレイヤー名
     * @return 取得結果
     */
    @NotNull
    public PlayerUUIDByNameResult getPlayerUUIDByNameTolerance(@NotNull String name) {
        return ClientPlayerInfoManager.getInstance().getUUIDByNameTolerance(name);
    }

    /**
     * プレイヤーUUIDからプレイヤー名を取得する
     * {@link #getPlayerNameByUUID(UUID)}との違いは取得失敗時や、取得中でも即座に結果を返す
     * 描画など即座に出力するために利用
     *
     * @param uuid プレイヤーUUID
     * @return 取得結果
     */
    @NotNull
    public PlayerNameByUUIDResult getPlayerNameByUUIDTolerance(@NotNull UUID uuid) {
        return ClientPlayerInfoManager.getInstance().getNameByUUIDTolerance(uuid);
    }

    /**
     * キーが押されているか
     *
     * @param keyCode キーコード
     * @return 押されているか
     */
    public static boolean isKeyInput(int keyCode) {
        if (keyCode < GLFW.GLFW_MOUSE_BUTTON_1)
            return false;

        long winID = Minecraft.getInstance().getWindow().getWindow();

        if (keyCode <= GLFW.GLFW_MOUSE_BUTTON_8)
            return GLFW.glfwGetMouseButton(winID, keyCode) == 1;

        return InputConstants.isKeyDown(winID, keyCode);
    }

    /**
     * キーが押されているか
     *
     * @param keyMapping キー
     * @return 押されているか
     */
    public static boolean isKeyInput(@NotNull KeyMapping keyMapping) {
        return isKeyInput(getKey(keyMapping).getValue());
    }

    /**
     * マウスのX座標
     *
     * @return x
     */
    public static double getMouseX() {
        return mc.mouseHandler.xpos() * mc.getWindow().getGuiScaledWidth() / mc.getWindow().getScreenWidth();
    }

    /**
     * マウスのY座標
     *
     * @return y
     */
    public static double getMouseY() {
        return mc.mouseHandler.ypos() * mc.getWindow().getGuiScaledHeight() / mc.getWindow().getScreenHeight();
    }

    /**
     * ファイル選択を開く
     *
     * @param title                タイトル
     * @param defaultPath          初期パス
     * @param singleFilter         フィルター
     * @param allowMultipleSelects 複数選択するかどうか
     * @return 選択結果
     */
    @Nullable
    public static File[] openFileChooser(@Nullable String title, @Nullable Path defaultPath, @Nullable String singleFilter, boolean allowMultipleSelects) {
        var st = TinyFileDialogs.tinyfd_openFileDialog(title, defaultPath != null ? defaultPath.toString() : null, null, singleFilter, allowMultipleSelects);
        if (st == null) return null;
        try {
            if (allowMultipleSelects) {
                String[] sp = st.split("\\|");
                File[] fl = new File[sp.length];
                for (int i = 0; i < sp.length; i++) {
                    fl[i] = new File(sp[i]);
                }
                return fl;
            } else {
                return new File[]{new File(st)};
            }
        } catch (Exception ex) {
            return null;
        }
    }

    public static String getWidthOmitText(String text, float maxWidth, String exit) {
        int wh = mc.font.width(text);
        if (wh > maxWidth) {
            int exwh = mc.font.width(exit);
            StringBuilder sb = new StringBuilder();

            for (char c : text.toCharArray()) {
                sb.append(c);
                if (mc.font.width(sb.toString()) > maxWidth - exwh)
                    break;
            }
            sb.deleteCharAt(sb.length() - 1);
            sb.append(exit);
            return sb.toString();
        }
        return text;
    }
}
