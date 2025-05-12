package org.modsauce.otyacraftenginerenewed.client.event;

import dev.architectury.event.Event;
import dev.architectury.event.EventFactory;
import dev.architectury.event.EventResult;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public interface ClientEvent {
    Event<ChangeHandHeight> CHANGE_HAND_HEIGHT = EventFactory.createEventResult();
    Event<PoseHumanoidArm> POSE_HUMANOID_ARM = EventFactory.createEventResult();
    Event<PoseHumanoidArmPost> POSE_HUMANOID_ARM_POST = EventFactory.createLoop();
    Event<PauseChange> INTEGRATED_SERVER_PAUSE = EventFactory.createLoop();
    Event<HandAttack> HAND_ATTACK = EventFactory.createEventResult();
    Event<EvaluateRenderHands> EVALUATE_RENDER_HANDS = EventFactory.createLoop();
    Event<SetupHumanoidAnimPost> SETUP_HUMANOID_ANIM_POST = EventFactory.createLoop();

    interface ChangeHandHeight {
        EventResult changeHandHeight(InteractionHand hand, ItemStack oldStack, ItemStack newStack);
    }

    interface PoseHumanoidArm {
        EventResult poseHumanoidArm(HumanoidArm arm, InteractionHand hand, HumanoidModel<? extends LivingEntity> model, LivingEntity livingEntity);
    }

    interface PoseHumanoidArmPost {
        void poseHumanoidArm(HumanoidArm arm, InteractionHand hand, HumanoidModel<? extends LivingEntity> model, LivingEntity livingEntity);
    }

    interface PauseChange {
        void onPauseChange(boolean paused);
    }

    interface HandAttack {
        EventResult onHandAttack(@NotNull ItemStack itemStack);
    }

    interface EvaluateRenderHands {
        void onEvaluateRenderHands(HandRenderSelectionWrapper handRenderSelection, LocalPlayer player, EvaluateRenderHandSetter setter);
    }

    interface EvaluateRenderHandSetter {
        void setEvaluate(HandRenderSelectionWrapper wrapper);
    }

    enum HandRenderSelectionWrapper {
        RENDER_BOTH_HANDS, RENDER_MAIN_HAND_ONLY, RENDER_OFF_HAND_ONLY;

        public static HandRenderSelectionWrapper onlyForHand(InteractionHand interactionHand) {
            return interactionHand == InteractionHand.MAIN_HAND ? RENDER_MAIN_HAND_ONLY : RENDER_OFF_HAND_ONLY;
        }
    }

    interface SetupHumanoidAnimPost {
        void setupHumanoidAnimPost(HumanoidModel<? extends LivingEntity> model, LivingEntity livingEntity, float walkAnimationPosition, float walkAnimationSpeed, float deltaTick, float headYRot, float xRot);
    }
}
