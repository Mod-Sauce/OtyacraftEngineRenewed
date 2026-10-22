package org.modsauce.otyacraftenginerenewed.client.renderer.texture;

import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;
import org.modsauce.otyacraftenginerenewed.client.util.OETextureUtils;

public interface TextureLoadResult {
  Identifier getLocation();

  boolean isLoading();

  boolean isError();

  boolean isSuccess();

  Throwable getThrowable();

  @NotNull
  default Identifier of(@NotNull Identifier loadingLocation, @NotNull Identifier errorlLocation) {
    if (isLoading()) return loadingLocation;
    if (isError()) return errorlLocation;
    return getLocation();
  }

  @NotNull
  default Identifier of(@NotNull Identifier errorLocation) {
    return of(OETextureUtils.getLoadingIcon(), errorLocation);
  }

  @NotNull
  default Identifier of() {
    return of(OETextureUtils.getLoadingIcon(), OETextureUtils.getErrorIcon());
  }

  TextureLoadProgress getProgress();
}
