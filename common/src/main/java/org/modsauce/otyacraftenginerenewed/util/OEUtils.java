package org.modsauce.otyacraftenginerenewed.util;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dev.felnull.fnjl.util.FNURLUtil;
import org.jetbrains.annotations.NotNull;
import org.modsauce.otyacraftenginerenewed.explatform.OEExpectPlatform;

import java.io.*;
import java.net.URL;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * 区別するほどでもないユーティリティ
 *
 * @author MORIMORI0317
 */
public final class OEUtils {
  private static final Gson GSON = new Gson();

  /**
   * URLからJsonを取得
   *
   * @param url URL
   * @return Json
   * @throws IOException 例外
   */
  public static <T> T readJson(URL url, Class<T> clazz) throws IOException {
    try (InputStream stream = FNURLUtil.getStream(url); InputStream bufStream = new BufferedInputStream(stream); Reader reader = new InputStreamReader(bufStream)) {
      return GSON.fromJson(reader, clazz);
    }
  }

  /**
   * 非同期でJsonを取得
   * 失敗時はnulが来ます
   *
   * @param url URL
   * @return 処理結果
   */
  public static CompletableFuture<JsonObject> getURLJsonAsync(URL url) {
    return FNURLUtil.getStreamAsync(url).thenApplyAsync(ret -> {
      if (ret == null) return null;
      try (Reader reader = new InputStreamReader(new BufferedInputStream(ret))) {
        return GSON.fromJson(reader, JsonObject.class);
      } catch (IOException ex) {
        return null;
      }
    });
  }

  /**
   * Forgeならアノテーションから、FabricならEntryPointを実装しているものを呼び出さす
   *
   * @param name            名前
   * @param annotationClass アノテーション
   * @param interfaceClass  インターフェース
   * @param <T>             型
   * @return 呼び出されているインスタンス一覧
   */
  public static <T> List<T> getCallPoints(@NotNull String name, @NotNull Class<?> annotationClass, @NotNull Class<T> interfaceClass) {
    return OEExpectPlatform.getCallPoints(name, annotationClass, interfaceClass);
  }

  /**
   * ファイルからJsonを読む
   *
   * @param file  ファイル
   * @param clazz クラス
   * @param <T>   クラス
   * @return json
   * @throws IOException 例外
   */
  public static <T> T readJson(File file, Class<T> clazz) throws IOException {
    try (InputStream stream = new FileInputStream(file); InputStream bufStream = new BufferedInputStream(stream); Reader reader = new InputStreamReader(bufStream)) {
      return GSON.fromJson(reader, clazz);
    }
  }

  public static void writeJson(File file, JsonElement je) throws IOException {
    try (OutputStream stream = new FileOutputStream(file); OutputStream bufStream = new BufferedOutputStream(stream); Writer writer = new OutputStreamWriter(bufStream)) {
      GSON.toJson(je, writer);
    }
  }
}
