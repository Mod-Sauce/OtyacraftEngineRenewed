package org.modsauce.otyacraftenginerenewed.tag;

import com.google.common.base.Suppliers;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import org.modsauce.otyacraftenginerenewed.data.provider.IntrinsicHolderTagsProviderWrapper;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class OEItemTags {
// 因为neoforge与fabric标签统一，不需要分平台了
  public static final Supplier<TagKey<Item>> IRON_NUGGETS = bindTagKey("nuggets/iron");
  public static final Supplier<ManualTagHolder<Item>> ENDER_PEARLS = bind(
    "ender_pearls",
    tp -> tp.add(Items.ENDER_PEARL).addOptionalTag(cLoc("ender_pearls"))
  );
  public static final Supplier<TagKey<Item>> STONE = bindTagKey("stones");
  public static final Supplier<TagKey<Item>> REDSTONE_BLOCKS = bindTagKey(
    "storage_blocks/redstone");
  public static final Supplier<TagKey<Item>> IRON_BLOCKS = bindTagKey("storage_blocks/iron");
  public static final Supplier<TagKey<Item>> BOOKS = bindTagKey("books");
  public static final Supplier<TagKey<Item>> RAW_MEATS = bindTagKey("foods/raw_meats");
  public static final Supplier<TagKey<Item>> COOKED_MEATS = bindTagKey("foods/cooked_meats");
  public static final Supplier<TagKey<Item>> RAW_FISHES = bindTagKey("foods/raw_fish");
  public static final Supplier<TagKey<Item>> COOKED_FISHES = bindTagKey("foods/cooked_fish");
  public static final Supplier<TagKey<Item>> WHEAT_BREADS = bindTagKey("foods/bread");
  public static final Supplier<TagKey<Item>> BREADS = bindTagKey("foods/bread");
  public static final Supplier<TagKey<Item>> VEGETABLES = bindTagKey("foods/vegetables");
  public static final Supplier<TagKey<Item>> CARROTS = bindTagKey("crops/carrot");
  public static final Supplier<TagKey<Item>> POTATOES = bindTagKey("crops/potato");
  public static final Supplier<TagKey<Item>> BEETROOTS = bindTagKey("crops/beetroot");
  public static final Supplier<TagKey<Item>> SEEDS = bindTagKey("seeds");
  public static final Supplier<TagKey<Item>> FRUITS = bindTagKey("foods/fruit");
  public static final Supplier<TagKey<Item>> MILKS = bindTagKey("drinks/milk");
  public static final Supplier<TagKey<Item>> DRINKS = bindTagKey("drinks");
  public static final Supplier<TagKey<Item>> SLIMEBALLS = bindTagKey("slime_balls");
  public static final Supplier<TagKey<Item>> SLIME_BALLS = SLIMEBALLS;
  public static final Supplier<ManualTagHolder<Item>> CLAY = bind("clay", tp ->
    tp.add(Items.CLAY_BALL)
  );

  private static Supplier<ManualTagHolder<Item>> bind(
    String id,
    Consumer<
      IntrinsicHolderTagsProviderWrapper.IntrinsicTagAppenderWrapper<Item>
    > tagRegister
  ) {
    return Suppliers.memoize(() ->
      ManualTagHolder.of(TagKey.create(Registries.ITEM, cLoc(id)), tagRegister)
    );
  }

  private static Supplier<TagKey<Item>> bindTagKey(String id) {
    return Suppliers.memoize(() -> TagKey.create(Registries.ITEM, cLoc(id)));
  }

  private static ResourceLocation cLoc(String path) {
    return ResourceLocation.fromNamespaceAndPath("c", path);
  }
}
