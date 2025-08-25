package org.modsauce.otyacraftenginerenewed.fabric.data.model;

import org.modsauce.otyacraftenginerenewed.data.model.FileModel;
import org.modsauce.otyacraftenginerenewed.data.model.MutableFileModel;
import org.modsauce.otyacraftenginerenewed.data.model.OverridePredicate;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MutableFileModelImpl extends FileModelImpl implements MutableFileModel {
    private final JsonModelInjector jsonModelInjector;

    public MutableFileModelImpl(ResourceLocation location, JsonModelInjector jsonModelInjector) {
        super(location);
        this.jsonModelInjector = jsonModelInjector;
    }

    @Override
    public MutableFileModel override(@NotNull FileModel model, @NotNull List<OverridePredicate> predicates) {
        this.jsonModelInjector.putOverride(model, predicates);
        return this;
    }

    @Override
    public MutableFileModel texture(@NotNull String id, @NotNull ResourceLocation location) {
        this.jsonModelInjector.putTexture(id, location);
        return this;
    }
}
