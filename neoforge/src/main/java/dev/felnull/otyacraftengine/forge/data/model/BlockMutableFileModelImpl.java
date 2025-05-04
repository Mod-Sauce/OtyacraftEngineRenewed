package dev.felnull.otyacraftengine.forge.data.model;

import dev.felnull.otyacraftengine.data.model.FileModel;
import dev.felnull.otyacraftengine.data.model.MutableFileModel;
import dev.felnull.otyacraftengine.data.model.OverridePredicate;
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
