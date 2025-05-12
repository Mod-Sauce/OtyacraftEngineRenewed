package org.modsauce.otyacraftenginerenewed.forge.mixin;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.MobBucketItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(MobBucketItem.class)
public interface MobBucketItemInvoker {
    @Invoker(value = "getFishType", remap = false)
    EntityType<?> invokeGetFishTypeInvoker();
}