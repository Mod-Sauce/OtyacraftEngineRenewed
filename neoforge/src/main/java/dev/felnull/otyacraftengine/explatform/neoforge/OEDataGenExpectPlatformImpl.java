package dev.felnull.otyacraftengine.explatform.neoforge;

import net.neoforged.neoforge.data.loading.DatagenModLoader;

public class OEDataGenExpectPlatformImpl {
    public static boolean isDataGenerating() {
        return DatagenModLoader.isRunningDataGen();
    }
}
