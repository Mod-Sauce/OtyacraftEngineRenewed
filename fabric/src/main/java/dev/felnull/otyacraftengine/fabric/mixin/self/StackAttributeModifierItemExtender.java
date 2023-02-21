package dev.felnull.otyacraftengine.fabric.mixin.self;

import com.google.common.collect.Multimap;
import dev.felnull.otyacraftengine.item.StackAttributeModifierItem;
import net.fabricmc.fabric.api.item.v1.FabricItem;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = StackAttributeModifierItem.class, remap = false)
public interface StackAttributeModifierItemExtender extends FabricItem {
    @Override
    default Multimap<Attribute, AttributeModifier> getAttributeModifiers(ItemStack stack, EquipmentSlot slot) {
        var ths = (StackAttributeModifierItem) this;
        return ths.getStackAttributeModifiers(slot, stack);
    }
}
