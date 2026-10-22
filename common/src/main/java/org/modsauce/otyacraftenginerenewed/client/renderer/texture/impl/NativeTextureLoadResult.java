package org.modsauce.otyacraftenginerenewed.client.renderer.texture.impl;

import net.minecraft.resources.Identifier;
import org.modsauce.otyacraftenginerenewed.client.renderer.texture.TextureLoadProgress;
import org.modsauce.otyacraftenginerenewed.client.renderer.texture.TextureLoadResult;

public class NativeTextureLoadResult implements TextureLoadResult {
  private final Identifier location;
  private final Throwable throwable;
  private TextureLoadProgress progress;

  public NativeTextureLoadResult(Identifier location, Throwable throwable, TextureLoadProgress progress) {
    this.location = location;
    this.throwable = throwable;
    this.progress = progress;
  }

  public NativeTextureLoadResult(Identifier location, Exception exception) {
    this(location, exception, null);
  }

  public NativeTextureLoadResult() {
    this(null, null, new TextureLoadProgressImpl());
  }

  @Override
  public boolean isSuccess() {
    return !isLoading() && !isError();
  }

  @Override
  public Throwable getThrowable() {
    return throwable;
  }

  @Override
  public TextureLoadProgress getProgress() {
    return progress;
  }

  @Override
  public Identifier getLocation() {
    return location;
  }

  @Override
  public boolean isLoading() {
    return location == null && throwable == null;
  }

  @Override
  public boolean isError() {
    return throwable != null;
  }

  public void setProgress(TextureLoadProgress progress) {
    this.progress = progress;
  }
}
