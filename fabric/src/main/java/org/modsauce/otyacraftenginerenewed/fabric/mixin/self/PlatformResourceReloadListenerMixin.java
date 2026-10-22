package org.modsauce.otyacraftenginerenewed.fabric.mixin.self;

import org.jspecify.annotations.NonNull;
import org.modsauce.otyacraftenginerenewed.resources.PlatformResourceReloadListener;
import net.fabricmc.fabric.api.resource.SimpleResourceReloadListener;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import org.spongepowered.asm.mixin.Mixin;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Mixin(value = PlatformResourceReloadListener.class, remap = false)
public abstract class PlatformResourceReloadListenerMixin<T> extends SimplePreparableReloadListener<T> implements SimpleResourceReloadListener<T> {

    @Override
    public @NonNull Identifier getFabricId() {
        PlatformResourceReloadListener<T> ths = (PlatformResourceReloadListener<T>) (Object) this;
        return ths.getId();
    }
}
