package org.modsauce.otyacraftenginerenewed.util;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.ai.village.poi.PoiTypes;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.modsauce.otyacraftenginerenewed.blockentity.BlockEntityCreateSupplier;
import org.modsauce.otyacraftenginerenewed.explatform.OERegisterExpectPlatform;

import java.util.Arrays;
import java.util.Set;
import java.util.function.Supplier;

/**
 * 登録関係のユーティリティ
 *
 * @author MORIMORI0317
 */
public final class OERegisterUtils {
  @NotNull
  public static <T extends BlockEntity> BlockEntityType<T> createBlockEntity(@NotNull BlockEntityCreateSupplier<? extends T> supplier, RegistrySupplier<Block>... blocks) {
    return createBlockEntity(supplier, Arrays.stream(blocks).map(Supplier::get).toList().toArray(new Block[0]));
  }

  @NotNull
  public static <T extends BlockEntity> BlockEntityType<T> createBlockEntity(@NotNull BlockEntityCreateSupplier<? extends T> supplier, Block... blocks) {
    return OERegisterExpectPlatform.createBlockEntity(supplier, blocks);
  }

  public static VillagerTrades.ItemListing createTradeEmeraldForItems(ItemLike item, int cost, int maxUses, int villagerXp) {
    return new VillagerTrades.EmeraldForItems(item, cost, maxUses, villagerXp);
  }

  public static VillagerTrades.ItemListing createTradeItemsForEmeralds(ItemStack itemStack, int emeraldCost, int numberOfItems, int maxUses, int villagerXp) {
    return new VillagerTrades.ItemsForEmeralds(itemStack, emeraldCost, numberOfItems, maxUses, villagerXp);
  }

  public static Set<BlockState> getPoiTypeBlockStates(Block block) {
    return PoiTypes.getBlockStates(block);
  }

  public static void registerPoiTypeBlockStates(@NotNull RegistrySupplier<PoiType> poiTypeRegistrySupplier) {
    OERegisterExpectPlatform.registerPoiTypeBlockStates(poiTypeRegistrySupplier);
  }

  //Wip...
   /* @NotNull
    public static Tier createTier(int level, int uses, float speed, float attackDamageBonus, int enchantmentValue, @NotNull TagKey<Block> tag, @NotNull Supplier<Ingredient> repairIngredient) {
        return OERegisterExpectPlatform.createTier(level, uses, speed, attackDamageBonus, enchantmentValue, tag, repairIngredient);
    }

    public static ArmorMaterial createArmorMaterial() {
         return null;
    }*/
}
