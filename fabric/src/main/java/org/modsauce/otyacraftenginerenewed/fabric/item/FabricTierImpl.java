package org.modsauce.otyacraftenginerenewed.fabric.item;

import java.util.function.Supplier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

@ApiStatus.Internal
public record FabricTierImpl(
  int uses,
  float speed,
  float attackDamageBonus,
  TagKey<Block> incorrectBlocksForDrops,
  int enchantmentValue,
  Supplier<Ingredient> repairIngredient
) implements Tier {
  @Override
  public int getUses() {
    return uses;
  }

  @Override
  public float getSpeed() {
    return speed;
  }

  @Override
  public float getAttackDamageBonus() {
    return attackDamageBonus;
  }

  @Override
  public @NotNull TagKey<Block> getIncorrectBlocksForDrops() {
    return incorrectBlocksForDrops;
  }

  @Override
  public int getEnchantmentValue() {
    return enchantmentValue;
  }

  @Override
  public @NotNull Ingredient getRepairIngredient() {
    return repairIngredient.get();
  }
}
