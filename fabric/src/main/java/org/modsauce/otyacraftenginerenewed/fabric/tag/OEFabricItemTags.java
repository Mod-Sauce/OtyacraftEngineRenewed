package org.modsauce.otyacraftenginerenewed.fabric.tag;

import com.google.common.base.Suppliers;
import org.modsauce.otyacraftenginerenewed.data.provider.IntrinsicHolderTagsProviderWrapper;
import org.modsauce.otyacraftenginerenewed.tag.ManualTagHolder;
import net.fabricmc.fabric.impl.tag.convention.TagRegistration;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class OEFabricItemTags {
    public static final Supplier<ManualTagHolder<Item>> IRON_NUGGETS = bind("iron_nuggets", tp -> tp.add(Items.IRON_NUGGET));
    public static final Supplier<ManualTagHolder<Item>> ENDER_PEARLS = bind("ender_pearls", tp -> tp.add(Items.ENDER_PEARL));
    public static final Supplier<ManualTagHolder<Item>> STONE = bind("stone", tp -> {
        tp.add(Items.STONE, Items.ANDESITE, Items.DIORITE, Items.GRANITE, Items.DEEPSLATE);
        tp.add(Items.POLISHED_ANDESITE, Items.POLISHED_DIORITE, Items.POLISHED_GRANITE, Items.POLISHED_DEEPSLATE);
    });
    public static final Supplier<ManualTagHolder<Item>> REDSTONE_BLOCKS = bind("redstone_blocks", tp -> tp.add(Items.REDSTONE_BLOCK));
    public static final Supplier<ManualTagHolder<Item>> IRON_BLOCKS = bind("iron_blocks", tp -> tp.add(Items.IRON_BLOCK));
    public static final Supplier<TagKey<Item>> BOOKS = bind("books");
    public static final Supplier<ManualTagHolder<Item>> RAW_MEATS = bind("raw_meats", tp -> tp.add(Items.BEEF, Items.PORKCHOP, Items.CHICKEN, Items.MUTTON, Items.RABBIT).addOptionalTag(cLoc("raw_meat")));
    public static final Supplier<ManualTagHolder<Item>> COOKED_MEATS = bind("cooked_meats", tp -> tp.add(Items.COOKED_BEEF, Items.COOKED_PORKCHOP, Items.COOKED_CHICKEN, Items.COOKED_MUTTON, Items.COOKED_RABBIT).addOptionalTag(cLoc("cooked_meat")));
    public static final Supplier<ManualTagHolder<Item>> RAW_FISHES = bind("raw_fishes", tp -> tp.add(Items.COD, Items.SALMON, Items.TROPICAL_FISH, Items.PUFFERFISH).addOptionalTag(cLoc("raw_fish")));
    public static final Supplier<ManualTagHolder<Item>> COOKED_FISHES = bind("cooked_fishes", tp -> tp.add(Items.COOKED_COD, Items.COOKED_SALMON).addOptionalTag(cLoc("cooked_fish")));
    public static final Supplier<ManualTagHolder<Item>> WHEAT_BREADS = bind("wheat_breads", tp -> tp.add(Items.BREAD).addOptionalTag(cLoc("bread/wheat")));
    public static final Supplier<ManualTagHolder<Item>> BREADS = bind("breads", tp -> tp.addTagHolder(WHEAT_BREADS.get()).addOptionalTag(cLoc("bread")));
    public static final Supplier<ManualTagHolder<Item>> VEGETABLES = bind("vegetables", tp -> tp.add(Items.CARROT, Items.POTATO, Items.BEETROOT));
    public static final Supplier<ManualTagHolder<Item>> CARROTS = bind("carrots", tp -> tp.add(Items.CARROT));
    public static final Supplier<ManualTagHolder<Item>> POTATOES = bind("potatoes", tp -> tp.add(Items.POTATO));
    public static final Supplier<ManualTagHolder<Item>> BEETROOTS = bind("beetroots", tp -> tp.add(Items.BEETROOT));
    public static final Supplier<ManualTagHolder<Item>> WHEAT_GRAINS = bind("wheat_grains", tp -> tp.add(Items.WHEAT).addOptionalTag(cLoc("grain/wheat")));
    public static final Supplier<ManualTagHolder<Item>> GRAINS = bind("grains", tp -> tp.addTagHolder(WHEAT_GRAINS.get()).addOptionalTag(cLoc("grain")));
    public static final Supplier<ManualTagHolder<Item>> SEEDS = bind("seeds", tp -> tp.add(Items.WHEAT_SEEDS, Items.BEETROOT_SEEDS, Items.MELON_SEEDS, Items.PUMPKIN_SEEDS));
    public static final Supplier<ManualTagHolder<Item>> FRUITS = bind("fruits", tp -> tp.add(Items.APPLE, Items.MELON, Items.SWEET_BERRIES, Items.GLOW_BERRIES, Items.CHORUS_FRUIT));
    public static final Supplier<ManualTagHolder<Item>> MILKS = bind("milks", tp -> tp.add(Items.MILK_BUCKET));
    public static final Supplier<ManualTagHolder<Item>> DRINKS = bind("drinks", tp -> tp.addOptionalTag(drinks()).addTagHolder(MILKS.get()));
    public static final Supplier<ManualTagHolder<Item>> SLIMEBALLS = bind("slimeballs", tp -> tp.add(Items.SLIME_BALL));
    public static final Supplier<ManualTagHolder<Item>> SLIME_BALLS = bind("slime_balls", tp -> tp.add(Items.SLIME_BALL));
    public static final Supplier<ManualTagHolder<Item>> CLAY = bind("clay", tp -> tp.add(Items.CLAY_BALL));

    private static Supplier<ManualTagHolder<Item>> bind(String id, Consumer<IntrinsicHolderTagsProviderWrapper.IntrinsicTagAppenderWrapper<Item>> tagRegister) {
        return Suppliers.memoize(() -> ManualTagHolder.of(TagRegistration.ITEM_TAG_REGISTRATION.registerCommon(id), tagRegister));
    }

    private static Supplier<TagKey<Item>> bind(String id) {
        return Suppliers.memoize(() -> TagRegistration.ITEM_TAG_REGISTRATION.registerCommon(id));
    }

    private static ResourceLocation cLoc(String path) {
        return new ResourceLocation("c", path);
    }

    private static ResourceLocation[] drinks() {
        return new ResourceLocation[]{cLoc("juices"), cLoc("banana_smoothies"), cLoc("strawberry_smoothies"), cLoc("coffees"), cLoc("lemonades"), cLoc("limeades"), cLoc("kale_smoothies"), cLoc("fruit_smoothies"), cLoc("chocolate_milkshakes"), cLoc("beers"), cLoc("wines"), cLoc("meads"), cLoc("rums"), cLoc("pumpkin_spice_lattes"), cLoc("milk_bottles"), cLoc("tea")};
    }
}
