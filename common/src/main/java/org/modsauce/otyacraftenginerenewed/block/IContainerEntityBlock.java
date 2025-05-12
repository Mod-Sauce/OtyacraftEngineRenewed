package org.modsauce.otyacraftenginerenewed.block;

import org.modsauce.otyacraftenginerenewed.blockentity.OEBaseContainerBlockEntity;
import org.modsauce.otyacraftenginerenewed.util.OEMenuUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public interface IContainerEntityBlock {
    default InteractionResult useContainer(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
        if (isOpenContainer(blockState, level, blockPos, player, interactionHand, blockHitResult)) {
            if (level.isClientSide()) {
                return InteractionResult.SUCCESS;
            } else {
                openContainer(blockState, (ServerLevel) level, blockPos, (ServerPlayer) player, interactionHand, blockHitResult);
                return InteractionResult.CONSUME;
            }
        }
        return InteractionResult.PASS;
    }

    default boolean isOpenContainer(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
        return level.getBlockEntity(blockPos) instanceof BaseContainerBlockEntity oeBaseContainerBlock;
    }

    default void openContainer(BlockState blockState, ServerLevel level, BlockPos blockPos, ServerPlayer player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
        if (level.getBlockEntity(blockPos) instanceof OEBaseContainerBlockEntity oeBaseContainerBlock)
            OEMenuUtil.openBlockMenu(player, oeBaseContainerBlock, blockPos, oeBaseContainerBlock.getContainerSize());
    }
}
