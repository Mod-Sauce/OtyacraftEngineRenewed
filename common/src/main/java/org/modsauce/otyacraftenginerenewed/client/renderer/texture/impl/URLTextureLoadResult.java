package org.modsauce.otyacraftenginerenewed.client.renderer.texture.impl;

import org.modsauce.otyacraftenginerenewed.client.renderer.texture.TextureLoadProgress;
import org.modsauce.otyacraftenginerenewed.client.renderer.texture.TextureLoadResult;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class URLTextureLoadResult implements TextureLoadResult {
    private final Throwable throwable;
    private final boolean needReload;
    private final long loadedTime;
    private final NativeTextureLoadResult loadResult;
    private final UUID uuid;
    private TextureLoadProgress progress = new TextureLoadProgressImpl();

    public URLTextureLoadResult(Throwable throwable, boolean needReload, long loadedTime, NativeTextureLoadResult loadResult, UUID uuid) {
        this.throwable = throwable;
        this.needReload = needReload;
        this.loadedTime = loadedTime;
        this.loadResult = loadResult;
        this.uuid = uuid;
    }

    public URLTextureLoadResult(Throwable throwable, boolean needReload, NativeTextureLoadResult loadResult, UUID uuid) {
        this(throwable, needReload, System.currentTimeMillis(), loadResult, uuid);
    }

    @Override
    public ResourceLocation getLocation() {
        if (loadResult == null)
            return null;
        return loadResult.getLocation();
    }

    @Override
    public boolean isLoading() {
        return getLocation() == null && throwable == null && loadedTime == -1;
    }

    @Override
    public boolean isError() {
        return throwable != null;
    }

    @Override
    public boolean isSuccess() {
        return !isLoading() && !isError();
    }

    @Override
    public Throwable getThrowable() {
        if (throwable != null)
            return throwable;
        if (loadResult != null)
            return loadResult.getThrowable();
        return null;
    }

    @Override
    public TextureLoadProgress getProgress() {
        return progress;
    }

    public long getLoadedTime() {
        return loadedTime;
    }

    @Nullable
    public UUID getUUID() {
        return uuid;
    }

    public boolean isNeedReload() {
        return needReload;
    }

    public NativeTextureLoadResult getNativeResult() {
        return loadResult;
    }

    public void setProgress(TextureLoadProgress progress) {
        this.progress = progress;
    }
}
