package dev.felnull.otyacraftengine.forge.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.felnull.otyacraftengine.client.util.OERenderUtils;
import dev.felnull.otyacraftengine.forge.client.renderer.item.ItemRendererRegisterForge;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockEntityWithoutLevelRenderer.class)
public class BlockEntityWithoutLevelRendererMixin {
    @Inject(method = "renderByItem", at = @At("HEAD"), cancellable = true)
    private void renderByItem(ItemStack stack, ItemDisplayContext displayContext, PoseStack poseStack, MultiBufferSource multiBufferSource, int light, int overlay, CallbackInfo info) {
        var renderer = ItemRendererRegisterForge.getRenderer(stack.getItem());
        if (renderer != null) {
            renderer.render(stack, displayContext, poseStack, multiBufferSource, OERenderUtils.getPartialTicks(), light, overlay);
            info.cancel();
        }
    }
}
