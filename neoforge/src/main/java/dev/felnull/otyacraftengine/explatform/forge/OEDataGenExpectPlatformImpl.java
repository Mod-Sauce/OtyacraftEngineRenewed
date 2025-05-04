package dev.felnull.otyacraftengine.explatform.forge;

import net.minecraftforge.data.loading.DatagenModLoader;

public class OEDataGenExpectPlatformImpl {
    public static boolean isDataGenerating() {
        return DatagenModLoader.isRunningDataGen();
    }
}
