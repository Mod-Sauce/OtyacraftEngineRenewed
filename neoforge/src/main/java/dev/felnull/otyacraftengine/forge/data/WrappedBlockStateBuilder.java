package dev.felnull.otyacraftengine.forge.data;

import com.google.gson.JsonObject;
import net.minecraft.data.models.blockstates.BlockStateGenerator;
import net.minecraftforge.client.model.generators.IGeneratedBlockState;

public record WrappedBlockStateBuilder(BlockStateGenerator blockStateGenerator) implements IGeneratedBlockState {
    @Override
    public JsonObject toJson() {
        return blockStateGenerator.get().getAsJsonObject();
    }
}
