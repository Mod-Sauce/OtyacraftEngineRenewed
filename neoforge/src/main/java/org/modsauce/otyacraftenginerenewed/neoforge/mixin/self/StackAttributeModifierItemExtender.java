package org.modsauce.otyacraftenginerenewed.neoforge.mixin.self;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.neoforged.neoforge.common.extensions.IItemExtension;
import org.modsauce.otyacraftenginerenewed.item.StackAttributeModifierItem;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = StackAttributeModifierItem.class, remap = false)
public interface StackAttributeModifierItemExtender extends IItemExtension {
    @Override
    default ItemAttributeModifiers getDefaultAttributeModifiers(ItemStack stack) {
        var ths = (StackAttributeModifierItem) this;
        // Convert the old Multimap-based API to the new ItemAttributeModifiers API
        // We need to iterate through all equipment slots to build the complete modifiers
        ItemAttributeModifiers.Builder builder = ItemAttributeModifiers.builder();
        
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            var modifiers = ths.getStackAttributeModifiers(slot, stack);
            modifiers.forEach((attribute, modifier) -> {
                // Convert EquipmentSlot to EquipmentSlotGroup and Attribute to Holder<Attribute>
                EquipmentSlotGroup slotGroup = EquipmentSlotGroup.bySlot(slot);
                builder.add(BuiltInRegistries.ATTRIBUTE.wrapAsHolder(attribute), modifier, slotGroup);
            });
        }
        
        return builder.build();
    }
}
