package org.modsauce.otyacraftenginerenewed.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import org.modsauce.otyacraftenginerenewed.client.event.OEClientEventHooks;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemInHandLayer.class)
public class ItemInHandLayerMixin {
    @Inject(method = "renderArmWithItem", at = @At("HEAD"), cancellable = true)
    private void renderArmWithItem(LivingEntity livingEntity, ItemStack itemStack, ItemDisplayContext displayContext, HumanoidArm humanoidArm, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, CallbackInfo ci) {
        if (!OEClientEventHooks.onRenderArmWithItem((ItemInHandLayer<? extends LivingEntity, ? extends EntityModel<?>>) (Object) this, livingEntity, itemStack, displayContext, humanoidArm, poseStack, multiBufferSource, i))
            ci.cancel();
    }
}