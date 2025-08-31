package org.modsauce.otyacraftenginerenewed.networking;

import io.netty.buffer.Unpooled;
import net.minecraft.network.RegistryFriendlyByteBuf;

public interface PacketMessage {
  default RegistryFriendlyByteBuf toRFBB() {
    return toRFBB(new RegistryFriendlyByteBuf(Unpooled.buffer()));
  }

  RegistryFriendlyByteBuf toRFBB(RegistryFriendlyByteBuf buf);
}
