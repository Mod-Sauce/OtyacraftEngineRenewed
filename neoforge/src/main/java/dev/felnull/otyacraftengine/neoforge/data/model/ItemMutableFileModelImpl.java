package dev.felnull.otyacraftengine.neoforge.data.model;

import dev.felnull.otyacraftengine.data.model.FileModel;
import dev.felnull.otyacraftengine.data.model.MutableFileModel;
import dev.felnull.otyacraftengine.data.model.OverridePredicate;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ItemMutableFileModelImpl extends MutableFileModelImpl {
    private final ItemModelBuilder itemModelBuilder;

    public ItemMutableFileModelImpl(ItemModelBuilder itemModelBuilder) {
        super(ItemModelBuilder);
        this.itemModelBuilder = itemModelBuilder;
    }

    @Override
    public MutableFileModel override(@NotNull FileModel model, @NotNull List<OverridePredicate> predicates) {
        var mf = FileModelImpl.getModelFile(model);
        var ovs = itemModelBuilder.override().model(mf);

        for (OverridePredicate predicate : predicates) {
            ovs.predicate(predicate.key(), predicate.value());
        }

        ovs.end();
        return this;
    }
}
