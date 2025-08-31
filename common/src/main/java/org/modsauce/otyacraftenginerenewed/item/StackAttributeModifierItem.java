package org.modsauce.otyacraftenginerenewed.item;

import com.google.common.collect.Multimap;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;

public interface StackAttributeModifierItem {
  Multimap<Attribute, AttributeModifier> getStackAttributeModifiers(EquipmentSlot slot, ItemStack stack);
}
