package org.modsauce.otyacraftenginerenewed.forge.data.model;

import org.modsauce.otyacraftenginerenewed.data.model.FileModel;
import org.modsauce.otyacraftenginerenewed.data.model.MutableFileModel;
import org.modsauce.otyacraftenginerenewed.data.model.OverridePredicate;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class BlockMutableFileModelImpl extends MutableFileModelImpl {
    private final BlockModelBuilder blockModelBuilder;

    public BlockMutableFileModelImpl(BlockModelBuilder blockModelBuilder) {
        super(blockModelBuilder);
        this.blockModelBuilder = blockModelBuilder;
    }

    @Override
    public MutableFileModel override(@NotNull FileModel model, @NotNull List<OverridePredicate> predicates) {
        throw new RuntimeException("Block model is not use override.");
    }
}
