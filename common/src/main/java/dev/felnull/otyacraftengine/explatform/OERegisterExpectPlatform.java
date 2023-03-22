package dev.felnull.otyacraftengine.explatform;

import dev.architectury.injectables.annotations.ExpectPlatform;
import dev.architectury.registry.registries.RegistrySupplier;
import dev.felnull.otyacraftengine.blockentity.BlockEntityCreateSupplier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class OERegisterExpectPlatform {
    @ExpectPlatform
    public static <T extends BlockEntity> BlockEntityType<T> createBlockEntity(BlockEntityCreateSupplier<? extends T> supplier, Block... blocks) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static void registerPoiTypeBlockStates(RegistrySupplier<PoiType> poiTypeRegistrySupplier) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static Tier createTier(int level, int uses, float speed, float attackDamageBonus, int enchantmentValue, @NotNull TagKey<Block> tag, @NotNull Supplier<Ingredient> repairIngredient) {
        throw new AssertionError();
    }
}
