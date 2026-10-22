package org.modsauce.otyacraftenginerenewed.fabric.mixin;

import dev.architectury.platform.Platform;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Set;

public class OEMixinPluginFabric implements IMixinConfigPlugin {

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        if (mixinClassName.startsWith("org.modsauce.otyacraftenginerenewed.fabric.mixin.data"))
            return Platform.isDevelopmentEnvironment();

        return true;
    }
}
