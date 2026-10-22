package org.modsauce.otyacraftenginerenewed.item.location;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.Identifier;
import org.modsauce.otyacraftenginerenewed.OtyacraftEngine;
import org.modsauce.otyacraftenginerenewed.item.location.factory.HandItemLocationFactory;
import org.modsauce.otyacraftenginerenewed.item.location.factory.PlayerItemLocationFactory;

public class PlayerItemLocations {
  private static final BiMap<Identifier, PlayerItemLocationFactory<? extends PlayerItemLocation>> FACTORS = HashBiMap.create();
  public static final HandItemLocationFactory HAND_ITEM = new HandItemLocationFactory();

  public static void init() {
    register(Identifier.fromNamespaceAndPath(OtyacraftEngine.MODID, "hand"), HAND_ITEM);
  }

  private static void register(Identifier location, PlayerItemLocationFactory<? extends PlayerItemLocation> factory) {
    FACTORS.put(location, factory);
  }

  public static Identifier getIdentifierByFactory(PlayerItemLocationFactory<? extends PlayerItemLocation> factory) {
    return FACTORS.inverse().get(factory);
  }

  public static CompoundTag saveToTag(PlayerItemLocation location) {
    var rl = location.getFactory().getLocation();
    if (rl == null)
      throw new IllegalArgumentException("Unregistered player item location");
    var tag = new CompoundTag();
    tag.putString("id", rl.toString());
    tag.put("data", location.createTag());
    return tag;
  }

  public static PlayerItemLocation loadFromTag(CompoundTag tag) {
    var rl = Identifier.parse(tag.getString("id").get());
    var factory = FACTORS.get(rl);
    if (factory == null)
      throw new IllegalArgumentException("Unregistered player item location");

    return factory.create(tag.getCompound("data").get());
  }
}
