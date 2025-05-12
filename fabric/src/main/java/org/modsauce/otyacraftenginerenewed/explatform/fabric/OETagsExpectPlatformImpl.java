package org.modsauce.otyacraftenginerenewed.explatform.fabric;

import com.google.common.collect.ImmutableList;
import org.modsauce.otyacraftenginerenewed.fabric.tag.OEFabricItemTags;
import org.modsauce.otyacraftenginerenewed.tag.ManualTagHolder;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

import java.util.List;
import java.util.Optional;

public class OETagsExpectPlatformImpl {
    public static Optional<TagKey<Item>> pickaxes() {
        return Optional.empty();
    }

    public static Optional<TagKey<Item>> shovels() {
        return Optional.empty();
    }

    public static Optional<TagKey<Item>> hoes() {
        return Optional.empty();
    }

    public static Optional<TagKey<Item>> axes() {
        return Optional.empty();
    }

    public static TagKey<Item> shears() {
        return ConventionalItemTags.SHEARS;
    }

    public static Optional<TagKey<Item>> swords() {
        return Optional.empty();
    }

    public static TagKey<Item> bows() {
        return ConventionalItemTags.BOWS;
    }

    public static TagKey<Item> ironIngots() {
        return ConventionalItemTags.IRON_INGOTS;
    }

    public static TagKey<Item> goldIngots() {
        return ConventionalItemTags.GOLD_INGOTS;
    }

    public static TagKey<Item> copperIngots() {
        return ConventionalItemTags.COPPER_INGOTS;
    }

    public static TagKey<Item> netheriteIngots() {
        return ConventionalItemTags.NETHERITE_INGOTS;
    }

    public static TagKey<Item> redstoneDusts() {
        return ConventionalItemTags.REDSTONE_DUSTS;
    }

    public static TagKey<Item> diamonds() {
        return ConventionalItemTags.DIAMONDS;
    }

    public static TagKey<Item> glassBlocks() {
        return ConventionalItemTags.GLASS_BLOCKS;
    }

    public static TagKey<Item> glassPanes() {
        return ConventionalItemTags.GLASS_PANES;
    }

    public static TagKey<Item> books() {
        return OEFabricItemTags.BOOKS.get();
    }

    public static ManualTagHolder<Item> ironNuggets() {
        return OEFabricItemTags.IRON_NUGGETS.get();
    }

    public static ManualTagHolder<Item> enderPearls() {
        return OEFabricItemTags.ENDER_PEARLS.get();
    }

    public static ManualTagHolder<Item> stone() {
        return OEFabricItemTags.STONE.get();
    }

    public static ManualTagHolder<Item> redstoneBlocks() {
        return OEFabricItemTags.REDSTONE_BLOCKS.get();
    }

    public static ManualTagHolder<Item> rawMeats() {
        return OEFabricItemTags.RAW_MEATS.get();
    }

    public static ManualTagHolder<Item> cookedMeats() {
        return OEFabricItemTags.COOKED_MEATS.get();
    }

    public static ManualTagHolder<Item> rawFishes() {
        return OEFabricItemTags.RAW_FISHES.get();
    }

    public static ManualTagHolder<Item> cookedFishes() {
        return OEFabricItemTags.COOKED_FISHES.get();
    }

    public static ManualTagHolder<Item> wheatBreads() {
        return OEFabricItemTags.WHEAT_BREADS.get();
    }

    public static ManualTagHolder<Item> breads() {
        return OEFabricItemTags.BREADS.get();
    }

    public static ManualTagHolder<Item> vegetables() {
        return OEFabricItemTags.VEGETABLES.get();
    }

    public static ManualTagHolder<Item> carrots() {
        return OEFabricItemTags.CARROTS.get();
    }

    public static ManualTagHolder<Item> potatoes() {
        return OEFabricItemTags.POTATOES.get();
    }

    public static ManualTagHolder<Item> beetroots() {
        return OEFabricItemTags.BEETROOTS.get();
    }

    public static ManualTagHolder<Item> wheatGrains() {
        return OEFabricItemTags.WHEAT_GRAINS.get();
    }

    public static ManualTagHolder<Item> grains() {
        return OEFabricItemTags.GRAINS.get();
    }

    public static ManualTagHolder<Item> seeds() {
        return OEFabricItemTags.SEEDS.get();
    }

    public static ManualTagHolder<Item> fruits() {
        return OEFabricItemTags.FRUITS.get();
    }

    public static ManualTagHolder<Item> milks() {
        return OEFabricItemTags.MILKS.get();
    }

    public static ManualTagHolder<Item> drinks() {
        return OEFabricItemTags.DRINKS.get();
    }

    public static ManualTagHolder<Item> ironBlocks() {
        return OEFabricItemTags.IRON_BLOCKS.get();
    }

    public static List<ManualTagHolder<Item>> slimeBalls() {
        return ImmutableList.of(OEFabricItemTags.SLIMEBALLS.get(), OEFabricItemTags.SLIME_BALLS.get());
    }

    public static ManualTagHolder<Item> clayBalls() {
        return OEFabricItemTags.CLAY.get();
    }
}
