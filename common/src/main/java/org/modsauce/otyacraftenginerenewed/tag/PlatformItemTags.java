package org.modsauce.otyacraftenginerenewed.tag;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.Unmodifiable;
import static org.modsauce.otyacraftenginerenewed.tag.OEItemTags.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class PlatformItemTags {
    private static ResourceLocation cLoc(String path) {
        return ResourceLocation.fromNamespaceAndPath("c", path);
    }

    private static TagKey<Item> cLocItem(String path){
        return TagKey.create(Registries.ITEM, cLoc(path));
    }
    private static ResourceLocation mLoc(String path) {
        return ResourceLocation.withDefaultNamespace(path);
    }

    private static TagKey<Item> mLocItem(String path){
        return TagKey.create(Registries.ITEM, mLoc(path));
    }
    /**
   * Fabricではこのタグは廃止される予定<br>
   * バニラの{@link ItemTags#PICKAXES}も利用してください
   */
  public static Optional<TagKey<Item>> pickaxes() {
    return Optional.of(mLocItem("pickaxes"));
  }

  /**
   * Fabricではこのタグは廃止される予定<br>
   * バニラの{@link ItemTags#SHOVELS}も利用してください
   */
  public static Optional<TagKey<Item>> shovels() {
    return Optional.of(mLocItem("shovels"));
  }

  /**
   * Fabricではこのタグは廃止される予定<br>
   * バニラの{@link ItemTags#HOES}も利用してください
   */
  public static Optional<TagKey<Item>> hoes() {
    return Optional.of(mLocItem("hoes"));
  }

  /**
   * Fabricではこのタグは廃止される予定<br>
   * バニラの{@link ItemTags#AXES}も利用してください
   */
  public static Optional<TagKey<Item>> axes() {
    return Optional.of(mLocItem("axes"));
  }

  public static TagKey<Item> shears() {
    return cLocItem("tools/shear");
  }

  /**
   * Fabricではこのタグは廃止される予定<br>
   * バニラの{@link ItemTags#SWORDS}も利用してください
   */
  public static Optional<TagKey<Item>> swords() {
    return Optional.of(mLocItem("swords"));
  }

  public static TagKey<Item> bows() {
    return cLocItem("tools/bow");
  }

  public static TagKey<Item> ironIngots() {
    return cLocItem("ingots/iron");
  }

  public static TagKey<Item> goldIngots() {
    return cLocItem("ingots/gold");
  }

  public static TagKey<Item> copperIngots() {
    return cLocItem("ingots/copper");
  }

  public static TagKey<Item> netheriteIngots() {
    return cLocItem("ingots/netherite");
  }

  public static TagKey<Item> redstoneDusts() {
    return cLocItem("dusts/redstone");
  }

  public static TagKey<Item> diamonds() {
    return cLocItem("gems/diamond");
  }

  public static TagKey<Item> glassBlocks() {
    return cLocItem("glass_blocks");
  }

  public static TagKey<Item> glassPanes() {
    return cLocItem("glass_panes");
  }

  public static TagKey<Item> books() {
    return BOOKS.get();
  }

  public static TagKey<Item> ironNuggets() {
    return IRON_NUGGETS.get();
  }

  public static ManualTagHolder<Item> enderPearls() {
    return ENDER_PEARLS.get();
  }

  public static TagKey<Item> stone() {
    return STONE.get();
  }

  public static TagKey<Item> redstoneBlocks() {
    return REDSTONE_BLOCKS.get();
  }

  public static TagKey<Item> rawMeats() {
    return RAW_MEATS.get();
  }

  public static TagKey<Item> cookedMeats() {
    return COOKED_MEATS.get();
  }

  public static TagKey<Item> rawFishes() {
    return RAW_FISHES.get();
  }

  public static TagKey<Item> cookedFishes() {
    return COOKED_FISHES.get();
  }

  public static TagKey<Item> wheatBreads() {
    return WHEAT_BREADS.get();
  }

  public static TagKey<Item> breads() {
    return BREADS.get();
  }

  public static TagKey<Item> vegetables() {
    return VEGETABLES.get();
  }

  public static TagKey<Item> carrots() {
    return CARROTS.get();
  }

  public static TagKey<Item> potatoes() {
    return POTATOES.get();
  }

  public static TagKey<Item> beetroots() {
    return BEETROOTS.get();
  }

  public static TagKey<Item> seeds() {
    return SEEDS.get();
  }

  public static TagKey<Item> fruits() {
    return FRUITS.get();
  }

  public static TagKey<Item> milks() {
    return MILKS.get();
  }

  public static TagKey<Item> drinks() {
    return DRINKS.get();
  }

  public static TagKey<Item> ironBlocks() {
    return IRON_BLOCKS.get();
  }

  @Unmodifiable
  public static List<TagKey<Item>> slimeBalls() {
    return Collections.singletonList(SLIME_BALLS.get());
  }

  public static ManualTagHolder<Item> clayBalls() {
    return CLAY.get();
  }
}
