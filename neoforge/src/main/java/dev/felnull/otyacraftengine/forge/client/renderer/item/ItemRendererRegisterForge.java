package dev.felnull.otyacraftengine.forge.client.renderer.item;

import dev.felnull.otyacraftengine.client.renderer.item.BEWLItemRenderer;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ItemRendererRegisterForge {
    private static final Map<Item, BEWLItemRenderer> RENDERERS = new HashMap<>();

    public static void register(ItemLike item, BEWLItemRenderer renderer) {
        Objects.requireNonNull(item, "item like is null");
        Objects.requireNonNull(item.asItem(), "item is null");
        Objects.requireNonNull(renderer, "renderer is null");

        if (RENDERERS.putIfAbsent(item.asItem(), renderer) != null)
            throw new IllegalArgumentException("Item " + ForgeRegistries.ITEMS.getKey(item.asItem()) + " already has a builtin renderer!");
    }

    public static BEWLItemRenderer getRenderer(Item item) {
        return RENDERERS.get(item);
    }
}
