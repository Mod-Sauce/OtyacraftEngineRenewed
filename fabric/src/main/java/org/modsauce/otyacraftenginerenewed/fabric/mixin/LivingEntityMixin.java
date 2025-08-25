package org.modsauce.otyacraftenginerenewed.fabric.mixin;

import org.modsauce.otyacraftenginerenewed.event.OECommonEventHooks;
import org.modsauce.otyacraftenginerenewed.item.EquipmentItem;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @Inject(method = "getEquipmentSlotForItem", at = @At("HEAD"), cancellable = true)
    private static void getEquipmentSlotForItem(ItemStack itemStack, CallbackInfoReturnable<EquipmentSlot> cir) {
        if (itemStack.getItem() instanceof EquipmentItem equipmentItem) {
            var slot = equipmentItem.getEquipmentSlotType(itemStack);
            if (slot != null)
                cir.setReturnValue(slot);
        }
    }

    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    private void tick(CallbackInfo ci) {
        if (!OECommonEventHooks.onLivingEntityTick((LivingEntity) (Object) this))
            ci.cancel();
    }
}