package dev.felnull.otyacraftengine.item.location;

import dev.felnull.otyacraftengine.item.location.factory.PlayerItemLocationFactory;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public record HandItemLocation(InteractionHand hand) implements PlayerItemLocation {
    @Override
    public ItemStack getItem(Player player) {
        return player.getItemInHand(hand);
    }

    @Override
    public CompoundTag createTag() {
        var tag = new CompoundTag();
        tag.putBoolean("hand", hand == InteractionHand.MAIN_HAND);
        return tag;
    }

    @Override
    public PlayerItemLocationFactory<? extends PlayerItemLocation> getFactory() {
        return PlayerItemLocations.HAND_ITEM;
    }
}
