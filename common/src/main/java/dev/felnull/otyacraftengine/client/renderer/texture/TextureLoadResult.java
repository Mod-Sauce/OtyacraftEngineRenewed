package dev.felnull.otyacraftengine.client.renderer.texture;

import dev.felnull.otyacraftengine.client.util.OETextureUtils;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public interface TextureLoadResult {
    ResourceLocation getLocation();

    boolean isLoading();

    boolean isError();

    boolean isSuccess();

    Throwable getThrowable();

    @NotNull
    default ResourceLocation of(@NotNull ResourceLocation loadingLocation, @NotNull ResourceLocation errorlLocation) {
        if (isLoading()) return loadingLocation;
        if (isError()) return errorlLocation;
        return getLocation();
    }

    @NotNull
    default ResourceLocation of(@NotNull ResourceLocation errorLocation) {
        return of(OETextureUtils.getLoadingIcon(), errorLocation);
    }

    @NotNull
    default ResourceLocation of() {
        return of(OETextureUtils.getLoadingIcon(), OETextureUtils.getErrorIcon());
    }

    TextureLoadProgress getProgress();
}
