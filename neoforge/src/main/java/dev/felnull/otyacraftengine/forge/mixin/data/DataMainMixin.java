package dev.felnull.otyacraftengine.forge.mixin.data;


import net.minecraft.data.Main;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Main.class)
public class DataMainMixin {
    @Inject(at = @At("TAIL"), method = "main", remap = false)
    private static void main(String[] strings, CallbackInfo ci) {
        System.exit(0);
    }
}

