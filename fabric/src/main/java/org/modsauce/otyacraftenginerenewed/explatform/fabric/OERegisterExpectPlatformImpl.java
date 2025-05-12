package org.modsauce.otyacraftenginerenewed.explatform.fabric;

import dev.architectury.registry.registries.RegistrySupplier;
import org.modsauce.otyacraftenginerenewed.blockentity.BlockEntityCreateSupplier;
import org.modsauce.otyacraftenginerenewed.fabric.mixin.PoiTypesInvoker;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class OERegisterExpectPlatformImpl {
    public static <T extends BlockEntity> BlockEntityType<T> createBlockEntity(BlockEntityCreateSupplier<? extends T> supplier, Block... blocks) {
        FabricBlockEntityTypeBuilder<T> builder = FabricBlockEntityTypeBuilder.create(supplier::create, blocks);
        return builder.build();
    }

    public static void registerPoiTypeBlockStates(RegistrySupplier<PoiType> poiTypeRegistrySupplier) {
        var rk = BuiltInRegistries.POINT_OF_INTEREST_TYPE.getResourceKey(poiTypeRegistrySupplier.get()).orElseThrow();
        PoiTypesInvoker.invokeRegisterBlockStates(BuiltInRegistries.POINT_OF_INTEREST_TYPE.getHolderOrThrow(rk), poiTypeRegistrySupplier.get().matchingStates());
    }

    public static Tier createTier(int level, int uses, float speed, float attackDamageBonus, int enchantmentValue, @NotNull TagKey<Block> tag, @NotNull Supplier<Ingredient> repairIngredient) {
        return null;
    }
}
