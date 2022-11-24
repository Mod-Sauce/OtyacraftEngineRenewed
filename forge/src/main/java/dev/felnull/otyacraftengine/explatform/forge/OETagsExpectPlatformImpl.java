package dev.felnull.otyacraftengine.explatform.forge;

import dev.felnull.otyacraftengine.forge.tag.OEForgeItemTags;
import dev.felnull.otyacraftengine.tag.ManualTagHolder;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.Tags;

public class OETagsExpectPlatformImpl {
    public static TagKey<Item> pickaxes() {
        return Tags.Items.TOOLS_PICKAXES;
    }

    public static TagKey<Item> shovels() {
        return Tags.Items.TOOLS_SHOVELS;
    }

    public static TagKey<Item> hoes() {
        return Tags.Items.TOOLS_HOES;
    }

    public static TagKey<Item> axes() {
        return Tags.Items.TOOLS_AXES;
    }

    public static TagKey<Item> shears() {
        return Tags.Items.SHEARS;
    }

    public static TagKey<Item> swords() {
        return Tags.Items.TOOLS_SWORDS;
    }

    public static TagKey<Item> bows() {
        return Tags.Items.TOOLS_BOWS;
    }

    public static TagKey<Item> ironIngots() {
        return Tags.Items.INGOTS_IRON;
    }

    public static TagKey<Item> goldIngots() {
        return Tags.Items.INGOTS_GOLD;
    }

    public static TagKey<Item> copperIngots() {
        return Tags.Items.INGOTS_COPPER;
    }

    public static TagKey<Item> netheriteIngots() {
        return Tags.Items.INGOTS_NETHER_BRICK;
    }

    public static TagKey<Item> redstoneDusts() {
        return Tags.Items.DUSTS_REDSTONE;
    }

    public static TagKey<Item> diamonds() {
        return Tags.Items.GEMS_DIAMOND;
    }

    public static TagKey<Item> glassBlocks() {
        return Tags.Items.GLASS;
    }

    public static TagKey<Item> glassPanes() {
        return Tags.Items.GLASS_PANES;
    }

    public static TagKey<Item> books() {
        return OEForgeItemTags.BOOKS.get();
    }

    public static ManualTagHolder<Item> ironNuggets() {
        return ManualTagHolder.of(() -> Tags.Items.NUGGETS_IRON);
    }

    public static ManualTagHolder<Item> enderPearls() {
        return ManualTagHolder.of(() -> Tags.Items.ENDER_PEARLS);
    }

    public static ManualTagHolder<Item> stone() {
        return ManualTagHolder.of(() -> Tags.Items.STONE);
    }

    public static ManualTagHolder<Item> redstoneBlocks() {
        return ManualTagHolder.of(() -> Tags.Items.STORAGE_BLOCKS_REDSTONE);
    }

    public static ManualTagHolder<Item> rawMeats() {
        return OEForgeItemTags.RAW_MEATS.get();
    }

    public static ManualTagHolder<Item> cookedMeats() {
        return OEForgeItemTags.COOKED_MEATS.get();
    }

    public static ManualTagHolder<Item> rawFishes() {
        return OEForgeItemTags.RAW_FISHES.get();
    }

    public static ManualTagHolder<Item> cookedFishes() {
        return OEForgeItemTags.COOKED_FISHES.get();
    }

    public static ManualTagHolder<Item> wheatBreads() {
        return OEForgeItemTags.BREAD_WHEAT.get();
    }

    public static ManualTagHolder<Item> breads() {
        return OEForgeItemTags.BREAD.get();
    }

    public static ManualTagHolder<Item> vegetables() {
        return OEForgeItemTags.VEGETABLES.get();
    }

    public static ManualTagHolder<Item> carrots() {
        return ManualTagHolder.of(() -> Tags.Items.CROPS_CARROT);
    }

    public static ManualTagHolder<Item> potatoes() {
        return ManualTagHolder.of(() -> Tags.Items.CROPS_POTATO);
    }

    public static ManualTagHolder<Item> beetroots() {
        return ManualTagHolder.of(() -> Tags.Items.CROPS_BEETROOT);
    }
}
