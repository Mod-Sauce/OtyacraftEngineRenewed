package org.modsauce.otyacraftenginerenewed.client.gui.screen;

import dev.architectury.networking.NetworkManager;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import org.modsauce.otyacraftenginerenewed.item.IInstructionItem;
import org.modsauce.otyacraftenginerenewed.item.location.PlayerItemLocation;
import org.modsauce.otyacraftenginerenewed.networking.OEPackets;
import org.modsauce.otyacraftenginerenewed.networking.existence.ItemExistence;

public interface InstructionItemScreen extends InstructionScreen {
  public static void instructionItem(
    InstructionItemScreen screen,
    ItemStack itemStack,
    PlayerItemLocation location,
    String name,
    CompoundTag data
  ) {
    if (
      Minecraft.getInstance().screen == screen &&
      itemStack.getItem() instanceof IInstructionItem &&
      location != null
    ) {
      NetworkManager.sendToServer(
        OEPackets.ITEM_INSTRUCTION,
        new OEPackets.ItemInstructionMessage(
          screen.getInstructionID(),
          ItemExistence.getByItemLocation(itemStack, location),
          name,
          data
        ).toRFBB()
      );
    }
  }
}
