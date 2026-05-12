package dev.felnull.otyacraftengine.explatform.fabric;

import dev.architectury.registry.registries.RegistrySupplier;
import dev.felnull.otyacraftengine.blockentity.BlockEntityCreateSupplier;
import java.util.function.Supplier;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.jetbrains.annotations.NotNull;

public class OERegisterExpectPlatformImpl {

  public static <T extends BlockEntity> BlockEntityType<
    T
  > createBlockEntity(
    BlockEntityCreateSupplier<? extends T> supplier,
    Block... blocks
  ) {
    FabricBlockEntityTypeBuilder<T> builder =
      FabricBlockEntityTypeBuilder.create(supplier::create, blocks);
    return builder.build();
  }

  public static Tier createTier(
    int level,
    int uses,
    float speed,
    float attackDamageBonus,
    int enchantmentValue,
    @NotNull TagKey<Block> tag,
    @NotNull Supplier<Ingredient> repairIngredient
  ) {
    return null;
  }
}
