package dev.felnull.otyacraftengine.neoforge.mixin.data;

import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.IGeneratedBlockState;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(value = BlockStateProvider.class, remap = false)
public interface BlockStateProviderAccessor {
    @Accessor
    Map<Block, IGeneratedBlockState> getRegisteredBlocks();
}
