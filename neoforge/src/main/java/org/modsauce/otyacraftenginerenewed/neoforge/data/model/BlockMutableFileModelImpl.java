package org.modsauce.otyacraftenginerenewed.neoforge.data.model;

import org.modsauce.otyacraftenginerenewed.data.model.FileModel;
import org.modsauce.otyacraftenginerenewed.data.model.MutableFileModel;
import org.modsauce.otyacraftenginerenewed.data.model.OverridePredicate;
import org.jetbrains.annotations.NotNull;
import net.neoforged.neoforge.client.model.generators.BlockModelBuilder;

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
