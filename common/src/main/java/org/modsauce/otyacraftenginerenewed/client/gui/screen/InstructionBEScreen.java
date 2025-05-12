package org.modsauce.otyacraftenginerenewed.client.gui.screen;

import dev.architectury.networking.NetworkManager;
import org.modsauce.otyacraftenginerenewed.blockentity.IInstructionBlockEntity;
import org.modsauce.otyacraftenginerenewed.networking.OEPackets;
import org.modsauce.otyacraftenginerenewed.networking.existence.BlockEntityExistence;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;

public interface InstructionBEScreen extends InstructionScreen {
    static void instructionBlockEntity(InstructionBEScreen screen, BlockEntity blockEntity, String name, CompoundTag data) {
        if (Minecraft.getInstance().screen == screen && blockEntity instanceof IInstructionBlockEntity) {
            NetworkManager.sendToServer(OEPackets.BLOCK_ENTITY_INSTRUCTION, new OEPackets.BlockEntityInstructionMessage(screen.getInstructionID(), BlockEntityExistence.getByBlockEntity(blockEntity), name, data).toFBB());
        }
    }
}
