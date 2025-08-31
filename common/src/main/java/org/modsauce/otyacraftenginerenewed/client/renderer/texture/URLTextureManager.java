package org.modsauce.otyacraftenginerenewed.client.renderer.texture;

import org.modsauce.otyacraftenginerenewed.client.renderer.texture.impl.URLTextureManagerImpl;

public interface URLTextureManager {
  static URLTextureManager getInstance() {
    return URLTextureManagerImpl.INSTANCE;
  }

  TextureLoadResult getAndAsyncLoad(String url, boolean cached);

  void init();

  void save();

  void release();

  void tick();
}
