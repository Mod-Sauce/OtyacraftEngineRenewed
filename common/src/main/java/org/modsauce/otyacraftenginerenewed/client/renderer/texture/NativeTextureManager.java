package org.modsauce.otyacraftenginerenewed.client.renderer.texture;

import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.modsauce.otyacraftenginerenewed.OtyacraftEngine;
import org.modsauce.otyacraftenginerenewed.client.renderer.texture.impl.NativeTextureLoadResult;
import org.modsauce.otyacraftenginerenewed.client.renderer.texture.impl.TextureLoadProgressImpl;
import org.modsauce.otyacraftenginerenewed.client.util.OETextureUtils;
import org.modsauce.otyacraftenginerenewed.util.FlagThread;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Consumer;

public class NativeTextureManager {
  private static final Minecraft mc = Minecraft.getInstance();
  private static final NativeTextureManager INSTANCE = new NativeTextureManager();
  private final Map<UUID, NativeTextureLoadResult> NATIVE_TEXTURE_LOADS = new HashMap<>();
  private final Map<UUID, NativeTextureLoader> NATIVE_TEXTURE_LOADERS = new HashMap<>();

  public static NativeTextureManager getInstance() {
    return INSTANCE;
  }

  @NotNull
  public NativeTextureLoadResult getAndLoadTextureAsync(@NotNull UUID uuid, @NotNull InputStream stream) {
    NativeTextureLoadResult r;
    synchronized (NATIVE_TEXTURE_LOADS) {
      r = NATIVE_TEXTURE_LOADS.get(uuid);
    }
    if (r == null) {
      r = new NativeTextureLoadResult();
      synchronized (NATIVE_TEXTURE_LOADS) {
        NATIVE_TEXTURE_LOADS.put(uuid, r);
      }
      NativeTextureLoader loader = new NativeTextureLoader(uuid, stream, r);
      synchronized (NATIVE_TEXTURE_LOADERS) {
        NATIVE_TEXTURE_LOADERS.put(uuid, loader);
      }
      loader.start();
    }
    return r;
  }

  public NativeTextureLoadResult getAndLoadTexture(@NotNull UUID uuid, @NotNull InputStream stream) {
    return getAndLoadTexture(uuid, stream, null);
  }

  public NativeTextureLoadResult getAndLoadTexture(@NotNull UUID uuid, @NotNull InputStream stream, @Nullable Consumer<TextureLoadProgress> progress) {
    Objects.requireNonNull(uuid);
    Objects.requireNonNull(stream);

    NativeTextureLoadResult r;
    synchronized (NATIVE_TEXTURE_LOADS) {
      r = NATIVE_TEXTURE_LOADS.get(uuid);
    }
    if (r == null) {
      r = loadNativeTexture(stream, progress);
      synchronized (NATIVE_TEXTURE_LOADS) {
        NATIVE_TEXTURE_LOADS.put(uuid, r);
      }
    }
    return r;
  }

  @Nullable
  public NativeTextureLoadResult getTexture(@NotNull UUID uuid) {
    synchronized (NATIVE_TEXTURE_LOADS) {
      return NATIVE_TEXTURE_LOADS.get(uuid);
    }
  }

  public void freeNativeTexture(@NotNull UUID id) {
    synchronized (NATIVE_TEXTURE_LOADERS) {
      var l = NATIVE_TEXTURE_LOADERS.get(id);
      if (l != null) {
        l.stopped();
        NATIVE_TEXTURE_LOADERS.remove(id);
        return;
      }
    }
    synchronized (NATIVE_TEXTURE_LOADS) {
      var r = NATIVE_TEXTURE_LOADS.get(id);
      if (r != null) {
        if (r.isSuccess())
          OETextureUtils.freeTexture(r.getLocation());
        NATIVE_TEXTURE_LOADS.remove(id);
      }
    }
  }

  private static NativeTextureLoadResult loadNativeTexture(InputStream stream, Consumer<TextureLoadProgress> progress) {
    ResourceLocation[] loc = new ResourceLocation[1];
    try (BufferedInputStream bufstream = new BufferedInputStream(stream)) {
      var tx = OETextureUtils.createNativeTexture(bufstream, progress);
      if (progress != null)
        progress.accept(new TextureLoadProgressImpl("Texture registering", 1, 0));
      mc.submit(() -> {
        loc[0] = mc.getTextureManager().register("native_texture", tx);
      }).get();
      if (progress != null)
        progress.accept(new TextureLoadProgressImpl("Texture registering", 1, 1));
    } catch (Exception ex) {
      return new NativeTextureLoadResult(null, ex);
    }
    return new NativeTextureLoadResult(loc[0], null);
  }

  private class NativeTextureLoader extends FlagThread {
    private final UUID uuid;
    private final InputStream stream;
    private final NativeTextureLoadResult result;

    private NativeTextureLoader(UUID uuid, InputStream stream, NativeTextureLoadResult result) {
      this.uuid = uuid;
      this.stream = stream;
      this.result = result;
      setName(OtyacraftEngine.getModName() + "-Native texture loader thread");
    }

    @Override
    public void run() {
      if (isStopped()) {
        finish();
        return;
      }
      var ret = loadNativeTexture(stream, result::setProgress);
      if (isStopped()) {
        if (ret.getLocation() != null)
          OETextureUtils.freeTexture(ret.getLocation());
        finish();
        return;
      }
      synchronized (NATIVE_TEXTURE_LOADS) {
        NATIVE_TEXTURE_LOADS.put(uuid, ret);
      }
      finish();
    }

    private void finish() {
      synchronized (NATIVE_TEXTURE_LOADERS) {
        NATIVE_TEXTURE_LOADERS.remove(uuid);
      }
    }
  }
}
