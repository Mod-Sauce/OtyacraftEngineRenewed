package org.modsauce.otyacraftenginerenewed.client.renderer.texture.impl;

import org.modsauce.otyacraftenginerenewed.client.renderer.texture.TextureLoadProgress;

public class TextureLoadProgressImpl implements TextureLoadProgress {
  private String stateName;
  private int total;
  private int complete;

  public TextureLoadProgressImpl() {

  }

  public TextureLoadProgressImpl(String stateName, int total, int complete) {
    this.stateName = stateName;
    this.total = total;
    this.complete = complete;
  }

  @Override
  public int getTotal() {
    return total;
  }

  @Override
  public int getComplete() {
    return complete;
  }

  @Override
  public String getStateName() {
    return stateName;
  }

  public void setComplete(int complete) {
    this.complete = complete;
  }

  public void setStateName(String stateName) {
    this.stateName = stateName;
  }

  public void setTotal(int total) {
    this.total = total;
  }
}
