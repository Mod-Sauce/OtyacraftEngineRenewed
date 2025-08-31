package org.modsauce.otyacraftenginerenewed.item;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

/**
 * アイテムスタックを使用しない場合はバニラのEquipmentを利用してください
 */
public interface EquipmentItem {
  @Nullable EquipmentSlot getEquipmentSlotType(ItemStack stack);
}