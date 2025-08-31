package org.modsauce.otyacraftenginerenewed.server.handler;

import dev.architectury.networking.NetworkManager;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import org.modsauce.otyacraftenginerenewed.blockentity.IInstructionBlockEntity;
import org.modsauce.otyacraftenginerenewed.item.IInstructionItem;
import org.modsauce.otyacraftenginerenewed.networking.OEPackets;

public class ServerMessageHandler {

  public static void onBlockEntityInstructionMessage(
    OEPackets.BlockEntityInstructionMessage message,
    NetworkManager.PacketContext packetContext
  ) {
    packetContext.queue(() -> {
      var player = (ServerPlayer) packetContext.getPlayer();
      if (!message.blockEntityExistence().check(player.level())) return;
      var ibe = (IInstructionBlockEntity) player
        .level()
        .getBlockEntity(message.blockEntityExistence().blockPos());
      if (
        ibe != null &&
        ibe.canInstructionWith(player, message.name(), message.data())
      ) {
        CompoundTag retag = ibe.onInstruction(
          player,
          message.name(),
          message.data()
        );
        if (retag != null) NetworkManager.sendToPlayer(
          player,
          OEPackets.BLOCK_ENTITY_INSTRUCTION_RETURN,
          new OEPackets.BlockEntityInstructionMessage(
            message.instructionScreenID(),
            message.blockEntityExistence(),
            message.name(),
            retag
          ).toFBB()
        );
      }
    });
  }

  public static void onItemInstructionMessage(
    OEPackets.ItemInstructionMessage message,
    NetworkManager.PacketContext packetContext
  ) {
    packetContext.queue(() -> {
      var player = (ServerPlayer) packetContext.getPlayer();
      if (!message.itemExistence().check(player)) return;
      var is = message.itemExistence().location().getItem(player);
      var ii = (IInstructionItem) is.getItem();
      if (ii.canInstructionWith(is, player, message.name(), message.data())) {
        CompoundTag retag = ii.onInstruction(
          is,
          player,
          message.name(),
          message.data()
        );
        if (retag != null) NetworkManager.sendToPlayer(
          player,
          OEPackets.ITEM_INSTRUCTION_RETURN,
          new OEPackets.ItemInstructionMessage(
            message.instructionScreenID(),
            message.itemExistence(),
            message.name(),
            retag
          ).toFBB()
        );
      }
    });
  }
}
