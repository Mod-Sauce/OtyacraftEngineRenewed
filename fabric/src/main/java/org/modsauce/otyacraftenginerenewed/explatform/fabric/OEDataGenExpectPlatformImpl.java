package org.modsauce.otyacraftenginerenewed.explatform.fabric;

import net.fabricmc.fabric.impl.datagen.FabricDataGenHelper;

public class OEDataGenExpectPlatformImpl {
    public static boolean isDataGenerating() {
        return FabricDataGenHelper.ENABLED;
    }
}
