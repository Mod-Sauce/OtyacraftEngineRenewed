package org.modsauce.otyacraftenginerenewed.tag;

import org.modsauce.otyacraftenginerenewed.explatform.OETagsExpectPlatform;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.Unmodifiable;

import java.util.List;
import java.util.Optional;

public class PlatformItemTags {

    /**
     * Fabricではこのタグは廃止される予定<br>
     * バニラの{@link ItemTags#PICKAXES}も利用してください
     */
    public static Optional<TagKey<Item>> pickaxes() {
        return OETagsExpectPlatform.pickaxes();
    }

    /**
     * Fabricではこのタグは廃止される予定<br>
     * バニラの{@link ItemTags#SHOVELS}も利用してください
     */
    public static Optional<TagKey<Item>> shovels() {
        return OETagsExpectPlatform.shovels();
    }

    /**
     * Fabricではこのタグは廃止される予定<br>
     * バニラの{@link ItemTags#HOES}も利用してください
     */
    public static Optional<TagKey<Item>> hoes() {
        return OETagsExpectPlatform.hoes();
    }

    /**
     * Fabricではこのタグは廃止される予定<br>
     * バニラの{@link ItemTags#AXES}も利用してください
     */
    public static Optional<TagKey<Item>> axes() {
        return OETagsExpectPlatform.axes();
    }

    public static TagKey<Item> shears() {
        return OETagsExpectPlatform.shears();
    }

    /**
     * Fabricではこのタグは廃止される予定<br>
     * バニラの{@link ItemTags#SWORDS}も利用してください
     */
    public static Optional<TagKey<Item>> swords() {
        return OETagsExpectPlatform.swords();
    }

    public static TagKey<Item> bows() {
        return OETagsExpectPlatform.bows();
    }

    public static TagKey<Item> ironIngots() {
        return OETagsExpectPlatform.ironIngots();
    }

    public static TagKey<Item> goldIngots() {
        return OETagsExpectPlatform.goldIngots();
    }

    public static TagKey<Item> copperIngots() {
        return OETagsExpectPlatform.copperIngots();
    }

    public static TagKey<Item> netheriteIngots() {
        return OETagsExpectPlatform.netheriteIngots();
    }

    public static TagKey<Item> redstoneDusts() {
        return OETagsExpectPlatform.redstoneDusts();
    }

    public static TagKey<Item> diamonds() {
        return OETagsExpectPlatform.diamonds();
    }

    public static TagKey<Item> glassBlocks() {
        return OETagsExpectPlatform.glassBlocks();
    }

    public static TagKey<Item> glassPanes() {
        return OETagsExpectPlatform.glassPanes();
    }

    public static TagKey<Item> books() {
        return OETagsExpectPlatform.books();
    }

    public static ManualTagHolder<Item> ironNuggets() {
        return OETagsExpectPlatform.ironNuggets();
    }

    public static ManualTagHolder<Item> enderPearls() {
        return OETagsExpectPlatform.enderPearls();
    }

    public static ManualTagHolder<Item> stone() {
        return OETagsExpectPlatform.stone();
    }

    public static ManualTagHolder<Item> redstoneBlocks() {
        return OETagsExpectPlatform.redstoneBlocks();
    }

    public static ManualTagHolder<Item> rawMeats() {
        return OETagsExpectPlatform.rawMeats();
    }

    public static ManualTagHolder<Item> cookedMeats() {
        return OETagsExpectPlatform.cookedMeats();
    }

    public static ManualTagHolder<Item> rawFishes() {
        return OETagsExpectPlatform.rawFishes();
    }

    public static ManualTagHolder<Item> cookedFishes() {
        return OETagsExpectPlatform.cookedFishes();
    }

    public static ManualTagHolder<Item> wheatBreads() {
        return OETagsExpectPlatform.wheatBreads();
    }

    public static ManualTagHolder<Item> breads() {
        return OETagsExpectPlatform.breads();
    }

    public static ManualTagHolder<Item> vegetables() {
        return OETagsExpectPlatform.vegetables();
    }

    public static ManualTagHolder<Item> carrots() {
        return OETagsExpectPlatform.carrots();
    }

    public static ManualTagHolder<Item> potatoes() {
        return OETagsExpectPlatform.potatoes();
    }

    public static ManualTagHolder<Item> beetroots() {
        return OETagsExpectPlatform.beetroots();
    }

    public static ManualTagHolder<Item> wheatGrains() {
        return OETagsExpectPlatform.wheatGrains();
    }

    public static ManualTagHolder<Item> grains() {
        return OETagsExpectPlatform.grains();
    }

    public static ManualTagHolder<Item> seeds() {
        return OETagsExpectPlatform.seeds();
    }

    public static ManualTagHolder<Item> fruits() {
        return OETagsExpectPlatform.fruits();
    }

    public static ManualTagHolder<Item> milks() {
        return OETagsExpectPlatform.milks();
    }

    public static ManualTagHolder<Item> drinks() {
        return OETagsExpectPlatform.drinks();
    }

    public static ManualTagHolder<Item> ironBlocks() {
        return OETagsExpectPlatform.ironBlocks();
    }

    @Unmodifiable
    public static List<ManualTagHolder<Item>> slimeBalls() {
        return OETagsExpectPlatform.slimeBalls();
    }

    public static ManualTagHolder<Item> clayBalls() {
        return OETagsExpectPlatform.clayBalls();
    }
}
