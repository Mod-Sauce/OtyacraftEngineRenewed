package org.modsauce.otyacraftenginerenewed.integration;

import dev.architectury.platform.Platform;
import org.modsauce.otyacraftenginerenewed.util.OEDataGenUtils;

public abstract class BaseIntegration {
    abstract public String getModId();

    abstract public boolean isConfigEnabled();

    public boolean isEnable() {
        return Platform.isModLoaded(getModId()) && isConfigEnabled();
    }

    public boolean isEnableElement() {
        return OEDataGenUtils.isDataGenerating() || isEnable();
    }
}
