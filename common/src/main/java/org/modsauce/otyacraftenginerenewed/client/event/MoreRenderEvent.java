package org.modsauce.otyacraftenginerenewed.client.event;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.architectury.event.Event;
import dev.architectury.event.EventFactory;
import dev.architectury.event.EventResult;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public interface MoreRenderEvent {
    Event<RenderItemInHand> RENDER_ITEM_IN_HAND = EventFactory.createEventResult();
    Event<RenderArmWithItem> RENDER_ARM_WITH_ITEM = EventFactory.createEventResult();

    interface RenderItemInHand {
        EventResult renderItemInHand(PoseStack poseStack, MultiBufferSource multiBufferSource, InteractionHand hand, int packedLight, float partialTicks, float interpolatedPitch, float swingProgress, float equipProgress, ItemStack stack);
    }

    interface RenderArmWithItem {
        EventResult renderArmWithItem(ItemInHandLayer<? extends LivingEntity, ? extends EntityModel<?>> layer, LivingEntity livingEntity, ItemStack itemStack, ItemDisplayContext displayContext, HumanoidArm humanoidArm, PoseStack poseStack, MultiBufferSource multiBufferSource, int i);
    }
}
