package org.modsauce.otyacraftenginerenewed.fabric.mixin.data;

import com.google.gson.JsonElement;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.JsonOps;
import org.modsauce.otyacraftenginerenewed.fabric.data.provider.WrappedRegistriesDatapackGenerator;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.registries.RegistriesDatapackGenerator;
import net.minecraft.resources.RegistryDataLoader;
import net.minecraft.resources.RegistryOps;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Mixin(RegistriesDatapackGenerator.class)
public abstract class RegistriesDatapackGeneratorMixin {
    @Shadow
    @Final
    private CompletableFuture<HolderLookup.Provider> registries;

    @Shadow
    protected abstract <T> Optional<CompletableFuture<?>> dumpRegistryCap(CachedOutput cachedOutput, HolderLookup.Provider provider, DynamicOps<JsonElement> dynamicOps, RegistryDataLoader.RegistryData<T> registryData);

    @Inject(method = "run", at = @At("HEAD"), cancellable = true)
    private void run(CachedOutput cachedOutput, CallbackInfoReturnable<CompletableFuture<?>> cir) {
        if ((Object) this instanceof WrappedRegistriesDatapackGenerator) {
            cir.setReturnValue(this.registries.thenCompose((provider) -> {
                DynamicOps<JsonElement> dynamicOps = RegistryOps.create(JsonOps.INSTANCE, provider);
                return CompletableFuture.allOf(WrappedRegistriesDatapackGenerator.getUnitedDataPackRegistries().flatMap((registryData) -> {
                    return this.dumpRegistryCap(cachedOutput, provider, dynamicOps, registryData).stream();
                }).toArray(CompletableFuture[]::new));
            }));
        }
    }
}
