package org.modsauce.otyacraftenginerenewed.explatform.neoforge;

import net.minecraftforge.data.loading.DatagenModLoader;

public class OEDataGenExpectPlatformImpl {

  public static boolean isDataGenerating() {
    return DatagenModLoader.isRunningDataGen();
  }
}
