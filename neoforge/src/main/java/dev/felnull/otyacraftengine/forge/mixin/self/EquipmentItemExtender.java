package dev.felnull.otyacraftengine.forge.mixin.self;

import dev.felnull.otyacraftengine.item.EquipmentItem;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.extensions.IForgeItem;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = EquipmentItem.class, remap = false)
public interface EquipmentItemExtender extends IForgeItem {
    @Override
    default @Nullable EquipmentSlot getEquipmentSlot(ItemStack stack) {
        var ths = (EquipmentItem) this;
        return ths.getEquipmentSlotType(stack);
    }
}
