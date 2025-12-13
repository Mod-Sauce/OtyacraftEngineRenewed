package org.modsauce.otyacraftenginerenewed.networking;

import dev.architectury.networking.NetworkManager;
import dev.architectury.platform.Platform;
import java.util.UUID;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import org.modsauce.otyacraftenginerenewed.OtyacraftEngine;
import org.modsauce.otyacraftenginerenewed.client.handler.ClientMessageHandler;
import org.modsauce.otyacraftenginerenewed.networking.existence.BlockEntityExistence;
import org.modsauce.otyacraftenginerenewed.networking.existence.ItemExistence;
import org.modsauce.otyacraftenginerenewed.server.handler.ServerMessageHandler;

public class OEPackets {

  public static final ResourceLocation BLOCK_ENTITY_INSTRUCTION =
    ResourceLocation.fromNamespaceAndPath(
      OtyacraftEngine.MODID,
      "block_entity_instruction"
    );
  public static final ResourceLocation BLOCK_ENTITY_INSTRUCTION_RETURN =
    ResourceLocation.fromNamespaceAndPath(
      OtyacraftEngine.MODID,
      "block_entity_instruction_return"
    );
  public static final ResourceLocation ITEM_INSTRUCTION =
    ResourceLocation.fromNamespaceAndPath(
      OtyacraftEngine.MODID,
      "item_instruction"
    );
  public static final ResourceLocation ITEM_INSTRUCTION_RETURN =
    ResourceLocation.fromNamespaceAndPath(
      OtyacraftEngine.MODID,
      "item_instruction_return"
    );

  public static void init() {
    NetworkManager.registerReceiver(
      NetworkManager.c2s(),
      BLOCK_ENTITY_INSTRUCTION,
      (friendlyByteBuf, packetContext) ->
        ServerMessageHandler.onBlockEntityInstructionMessage(
          new BlockEntityInstructionMessage(friendlyByteBuf),
          packetContext
        )
    );
    NetworkManager.registerReceiver(
      NetworkManager.c2s(),
      ITEM_INSTRUCTION,
      (friendlyByteBuf, packetContext) ->
        ServerMessageHandler.onItemInstructionMessage(
          new ItemInstructionMessage(friendlyByteBuf),
          packetContext
        )
    );
  }

  public static void clientInit() {
    // S2C Packets - Only register on client side to avoid Fabric networking issues
    // The server doesn't need to register S2C receivers, only the client does
    NetworkManager.registerReceiver(
      NetworkManager.s2c(),
      BLOCK_ENTITY_INSTRUCTION_RETURN,
      (friendlyByteBuf, packetContext) ->
        ClientMessageHandler.onBlockEntityInstructionReturn(
          new BlockEntityInstructionMessage((RegistryFriendlyByteBuf) friendlyByteBuf),
          packetContext
        )
    );
    NetworkManager.registerReceiver(
      NetworkManager.s2c(),
      ITEM_INSTRUCTION_RETURN,
      (friendlyByteBuf, packetContext) ->
        ClientMessageHandler.onItemInstructionReturn(
          new ItemInstructionMessage((RegistryFriendlyByteBuf) friendlyByteBuf),
          packetContext
        )
    );
  }

  public static record BlockEntityInstructionMessage(
    UUID instructionScreenID,
    BlockEntityExistence blockEntityExistence,
    String name,
    CompoundTag data
  ) implements PacketMessage {
    public BlockEntityInstructionMessage(RegistryFriendlyByteBuf bf) {
      this(
        bf.readUUID(),
        BlockEntityExistence.read(bf),
        bf.readUtf(),
        bf.readNbt()
      );
    }

    @Override
    public RegistryFriendlyByteBuf toRFBB(RegistryFriendlyByteBuf buf) {
      buf.writeUUID(instructionScreenID);
      blockEntityExistence.write(buf);
      buf.writeUtf(name);
      buf.writeNbt(data);
      return buf;
    }
  }

  public static record ItemInstructionMessage(
    UUID instructionScreenID,
    ItemExistence itemExistence,
    String name,
    CompoundTag data
  ) implements PacketMessage {
    public ItemInstructionMessage(RegistryFriendlyByteBuf bf) {
      this(bf.readUUID(), ItemExistence.read(bf), bf.readUtf(), bf.readNbt());
    }

    @Override
    public RegistryFriendlyByteBuf toRFBB(RegistryFriendlyByteBuf buf) {
      buf.writeUUID(instructionScreenID);
      itemExistence.write(buf);
      buf.writeUtf(name);
      buf.writeNbt(data);
      return buf;
    }
  }
}
