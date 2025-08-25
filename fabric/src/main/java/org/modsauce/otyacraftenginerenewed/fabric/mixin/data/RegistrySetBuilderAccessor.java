package org.modsauce.otyacraftenginerenewed.fabric.mixin.data;

import net.minecraft.core.RegistrySetBuilder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(value = RegistrySetBuilder.class, remap = false)
public interface RegistrySetBuilderAccessor {
    @Accessor
    List<RegistrySetBuilder.RegistryStub<?>> getEntries();
}
