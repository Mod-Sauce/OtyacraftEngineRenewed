package dev.felnull.otyacraftengine.client.handler;

import dev.architectury.networking.NetworkManager;
import dev.felnull.otyacraftengine.client.gui.screen.InstructionBEScreen;
import dev.felnull.otyacraftengine.client.gui.screen.InstructionItemScreen;
import dev.felnull.otyacraftengine.networking.OEPackets;
import net.minecraft.client.Minecraft;

public class ClientMessageHandler {
    private static final Minecraft mc = Minecraft.getInstance();

    public static void onBlockEntityInstructionReturn(OEPackets.BlockEntityInstructionMessage message, NetworkManager.PacketContext packetContext) {
        packetContext.queue(() -> {
            if (!message.blockEntityExistence().check(mc.level))
                return;
            if (mc.screen instanceof InstructionBEScreen insScreen && insScreen.getInstructionID().equals(message.instructionScreenID()))
                insScreen.onInstructionReturn(message.name(), message.data());
        });
    }

    public static void onItemInstructionReturn(OEPackets.ItemInstructionMessage message, NetworkManager.PacketContext packetContext) {
        packetContext.queue(() -> {
            if (!message.itemExistence().check(mc.player))
                return;
            if (mc.screen instanceof InstructionItemScreen insScreen && insScreen.getInstructionID().equals(message.instructionScreenID()))
                insScreen.onInstructionReturn(message.name(), message.data());
        });
    }
}
