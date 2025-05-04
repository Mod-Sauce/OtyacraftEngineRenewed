package dev.felnull.otyacraftengine.forge.mixin.self;

import com.google.common.collect.Multimap;
import dev.felnull.otyacraftengine.item.StackAttributeModifierItem;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.extensions.IForgeItem;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = StackAttributeModifierItem.class, remap = false)
public interface StackAttributeModifierItemExtender extends IForgeItem {
    @Override
    default Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        var ths = (StackAttributeModifierItem) this;
        return ths.getStackAttributeModifiers(slot,stack);
    }
}
