package dev.felnull.otyacraftengine.forge.mixin.data;

import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.IGeneratedBlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(value = BlockStateProvider.class, remap = false)
public interface BlockStateProviderAccessor {
    @Accessor
    Map<Block, IGeneratedBlockState> getRegisteredBlocks();
}
