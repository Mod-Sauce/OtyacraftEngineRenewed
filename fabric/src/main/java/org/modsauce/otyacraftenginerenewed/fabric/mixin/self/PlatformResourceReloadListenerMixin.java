package org.modsauce.otyacraftenginerenewed.fabric.mixin.self;

import org.modsauce.otyacraftenginerenewed.resources.PlatformResourceReloadListener;
import net.fabricmc.fabric.api.resource.SimpleResourceReloadListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import org.spongepowered.asm.mixin.Mixin;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Mixin(value = PlatformResourceReloadListener.class, remap = false)
public abstract class PlatformResourceReloadListenerMixin<T> extends SimplePreparableReloadListener<T> implements SimpleResourceReloadListener<T> {
    @Override
    public CompletableFuture<T> load(ResourceManager manager, ProfilerFiller profiler, Executor executor) {
        return CompletableFuture.supplyAsync(() -> prepare(manager, profiler), executor);
    }

    @Override
    public CompletableFuture<Void> apply(T data, ResourceManager manager, ProfilerFiller profiler, Executor executor) {
        return CompletableFuture.runAsync(() -> this.apply(data, manager, profiler), executor);
    }

    @Override
    public ResourceLocation getFabricId() {
        PlatformResourceReloadListener<T> ths = (PlatformResourceReloadListener<T>) (Object) this;
        return ths.getId();
    }
}
