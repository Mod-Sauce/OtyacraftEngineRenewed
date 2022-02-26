package dev.felnull.otyacraftengine.mixin.client;

import dev.felnull.otyacraftengine.client.event.OEClientEventHooks;
import net.minecraft.client.server.IntegratedServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.BooleanSupplier;

@Mixin(IntegratedServer.class)
public class IntegratedServerMixin {
    @Shadow
    private boolean paused;
    private boolean lastPaused;

    @Inject(method = "tickServer", at = @At("HEAD"))
    private void tickServer(BooleanSupplier booleanSupplier, CallbackInfo ci) {
        this.lastPaused = paused;
    }

    @Inject(method = "tickServer", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/server/IntegratedServer;getProfiler()Lnet/minecraft/util/profiling/ProfilerFiller;"))
    private void tickServerPaused(BooleanSupplier booleanSupplier, CallbackInfo ci) {
        if (lastPaused != paused)
            OEClientEventHooks.onIntegratedServerPauseChange(paused);
    }
}