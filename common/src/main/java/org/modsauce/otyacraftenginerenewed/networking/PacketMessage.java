package org.modsauce.otyacraftenginerenewed.networking;

import io.netty.buffer.Unpooled;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;

public interface PacketMessage {
  default RegistryFriendlyByteBuf toRFBB() {
    FriendlyByteBuf friendlyBuf = new FriendlyByteBuf(Unpooled.buffer());
    RegistryAccess registryAccess = RegistryAccess.fromRegistryOfRegistries(
      BuiltInRegistries.REGISTRY
    );
    return toRFBB(new RegistryFriendlyByteBuf(friendlyBuf, registryAccess));
  }

  RegistryFriendlyByteBuf toRFBB(RegistryFriendlyByteBuf buf);
}
