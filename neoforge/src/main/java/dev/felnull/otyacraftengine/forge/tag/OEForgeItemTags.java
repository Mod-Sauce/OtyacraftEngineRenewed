package dev.felnull.otyacraftengine.forge.tag;

import com.google.common.base.Suppliers;
import dev.felnull.otyacraftengine.data.provider.IntrinsicHolderTagsProviderWrapper;
import dev.felnull.otyacraftengine.tag.ManualTagHolder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class OEForgeItemTags {
    public static final Supplier<TagKey<Item>> BOOKS = bind("books");
    public static final Supplier<ManualTagHolder<Item>> RAW_MEATS = bind("raw_meats", tp -> tp.add(Items.PORKCHOP, Items.BEEF, Items.CHICKEN, Items.RABBIT, Items.MUTTON).addOptionalTag(fgLoc("raw_meat")));
    public static final Supplier<ManualTagHolder<Item>> COOKED_MEATS = bind("cooked_meats", tp -> tp.add(Items.COOKED_BEEF, Items.COOKED_PORKCHOP, Items.COOKED_CHICKEN, Items.COOKED_MUTTON, Items.COOKED_RABBIT).addOptionalTag(fgLoc("cooked_meat")));
    public static final Supplier<ManualTagHolder<Item>> RAW_FISHES = bind("raw_fishes", tp -> tp.add(Items.COD, Items.SALMON, Items.TROPICAL_FISH));
    public static final Supplier<ManualTagHolder<Item>> COOKED_FISHES = bind("cooked_fishes", tp -> tp.add(Items.COOKED_COD, Items.COOKED_SALMON));
    public static final Supplier<ManualTagHolder<Item>> BREAD_WHEAT = bind("bread/wheat", tp -> tp.add(Items.BREAD));
    public static final Supplier<ManualTagHolder<Item>> BREAD = bind("bread", tp -> tp.addTagHolder(BREAD_WHEAT.get()));
    public static final Supplier<ManualTagHolder<Item>> VEGETABLES = bind("vegetables", tp -> tp.add(Items.CARROT, Items.POTATO, Items.BEETROOT));
    public static final Supplier<ManualTagHolder<Item>> GRAINS_WHEAT = bind("grains/wheat", tp -> tp.add(Items.WHEAT).addOptionalTag(fgLoc("grain/wheat")));
    public static final Supplier<ManualTagHolder<Item>> GRAINS = bind("grain", tp -> tp.addTagHolder(GRAINS_WHEAT.get()).addOptionalTag(fgLoc("grain")));
    public static final Supplier<ManualTagHolder<Item>> FRUITS = bind("fruits", tp -> tp.add(Items.APPLE, Items.MELON, Items.SWEET_BERRIES, Items.GLOW_BERRIES, Items.CHORUS_FRUIT));
    public static final Supplier<ManualTagHolder<Item>> MILKS = bind("milks", tp -> tp.add(Items.MILK_BUCKET));
    public static final Supplier<ManualTagHolder<Item>> DRINKS = bind("drinks", tp -> tp.addOptionalTag(drinks()).addTagHolder(MILKS.get()));
    public static final Supplier<ManualTagHolder<Item>> CLAY = bind("clay", tp -> tp.add(Items.CLAY_BALL));

    private static Supplier<TagKey<Item>> bind(String id) {
        return Suppliers.memoize(() -> ItemTags.create(fgLoc(id)));
    }

    private static Supplier<ManualTagHolder<Item>> bind(String id, Consumer<IntrinsicHolderTagsProviderWrapper.IntrinsicTagAppenderWrapper<Item>> tagRegister) {
        return Suppliers.memoize(() -> ManualTagHolder.of(ItemTags.create(fgLoc(id)), tagRegister));
    }

    private static ResourceLocation fgLoc(String path) {
        return new ResourceLocation("forge", path);
    }

    private static ResourceLocation[] drinks() {
        return new ResourceLocation[]{fgLoc("juices"), fgLoc("banana_smoothies"), fgLoc("strawberry_smoothies"), fgLoc("coffees"), fgLoc("lemonades"), fgLoc("limeades"), fgLoc("kale_smoothies"), fgLoc("fruit_smoothies"), fgLoc("chocolate_milkshakes"), fgLoc("beers"), fgLoc("wines"), fgLoc("meads"), fgLoc("rums"), fgLoc("pumpkin_spice_lattes"), fgLoc("milk_bottles"), fgLoc("tea")};
    }
}
