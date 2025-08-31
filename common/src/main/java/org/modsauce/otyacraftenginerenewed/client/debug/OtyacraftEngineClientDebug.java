package org.modsauce.otyacraftenginerenewed.client.debug;

import org.modsauce.otyacraftenginerenewed.OtyacraftEngine;

public class OtyacraftEngineClientDebug {
  private static final OtyacraftEngineClientDebug INSTANCE = new OtyacraftEngineClientDebug();

  public static OtyacraftEngineClientDebug getInstance() {
    return INSTANCE;
  }

  public boolean isShowTagInTooltip() {
    return OtyacraftEngine.getConfig().getDebugConfig().isShowTagTooltip();
  }

  public boolean isShowModNameInTooltip() {
    return OtyacraftEngine.getConfig().getDebugConfig().isShowModNameTooltip();
  }

  public boolean isShowWidgetData() {
    return OtyacraftEngine.getConfig().getDebugConfig().isShowWidgetData();
  }

  public HighlightVoxelShapeType getHighlightVoxelShape() {
    return OtyacraftEngine.getConfig().getDebugConfig().getHighlightVoxelShape();
  }
}
