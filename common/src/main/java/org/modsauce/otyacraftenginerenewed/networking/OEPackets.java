package org.modsauce.otyacraftenginerenewed.networking;

import dev.architectury.networking.NetworkManager;
import org.modsauce.otyacraftenginerenewed.OtyacraftEngine;
import org.modsauce.otyacraftenginerenewed.client.handler.ClientMessageHandler;
import org.modsauce.otyacraftenginerenewed.networking.existence.BlockEntityExistence;
import org.modsauce.otyacraftenginerenewed.networking.existence.ItemExistence;
import org.modsauce.otyacraftenginerenewed.server.handler.ServerMessageHandler;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

import java.util.UUID;

public class OEPackets {
    public static final ResourceLocation BLOCK_ENTITY_INSTRUCTION = new ResourceLocation(OtyacraftEngine.MODID, "block_entity_instruction");
    public static final ResourceLocation BLOCK_ENTITY_INSTRUCTION_RETURN = new ResourceLocation(OtyacraftEngine.MODID, "block_entity_instruction_return");
    public static final ResourceLocation ITEM_INSTRUCTION = new ResourceLocation(OtyacraftEngine.MODID, "item_instruction");
    public static final ResourceLocation ITEM_INSTRUCTION_RETURN = new ResourceLocation(OtyacraftEngine.MODID, "item_instruction_return");

    public static void init() {
        NetworkManager.registerReceiver(NetworkManager.c2s(), BLOCK_ENTITY_INSTRUCTION, (friendlyByteBuf, packetContext) -> ServerMessageHandler.onBlockEntityInstructionMessage(new BlockEntityInstructionMessage(friendlyByteBuf), packetContext));
        NetworkManager.registerReceiver(NetworkManager.c2s(), ITEM_INSTRUCTION, (friendlyByteBuf, packetContext) -> ServerMessageHandler.onItemInstructionMessage(new ItemInstructionMessage(friendlyByteBuf), packetContext));
    }

    public static void clientInit() {
        NetworkManager.registerReceiver(NetworkManager.s2c(), BLOCK_ENTITY_INSTRUCTION_RETURN, (friendlyByteBuf, packetContext) -> ClientMessageHandler.onBlockEntityInstructionReturn(new BlockEntityInstructionMessage(friendlyByteBuf), packetContext));
        NetworkManager.registerReceiver(NetworkManager.s2c(), ITEM_INSTRUCTION_RETURN, (friendlyByteBuf, packetContext) -> ClientMessageHandler.onItemInstructionReturn(new ItemInstructionMessage(friendlyByteBuf), packetContext));
    }

    public static record BlockEntityInstructionMessage(UUID instructionScreenID,
                                                       BlockEntityExistence blockEntityExistence, String name,
                                                       CompoundTag data) implements PacketMessage {
        public BlockEntityInstructionMessage(FriendlyByteBuf bf) {
            this(bf.readUUID(), BlockEntityExistence.read(bf), bf.readUtf(), bf.readNbt());
        }

        @Override
        public FriendlyByteBuf toFBB(FriendlyByteBuf buf) {
            buf.writeUUID(instructionScreenID);
            blockEntityExistence.write(buf);
            buf.writeUtf(name);
            buf.writeNbt(data);
            return buf;
        }
    }

    public static record ItemInstructionMessage(UUID instructionScreenID, ItemExistence itemExistence, String name,
                                                CompoundTag data) implements PacketMessage {
        public ItemInstructionMessage(FriendlyByteBuf bf) {
            this(bf.readUUID(), ItemExistence.read(bf), bf.readUtf(), bf.readNbt());
        }

        @Override
        public FriendlyByteBuf toFBB(FriendlyByteBuf buf) {
            buf.writeUUID(instructionScreenID);
            itemExistence.write(buf);
            buf.writeUtf(name);
            buf.writeNbt(data);
            return buf;
        }
    }

}
