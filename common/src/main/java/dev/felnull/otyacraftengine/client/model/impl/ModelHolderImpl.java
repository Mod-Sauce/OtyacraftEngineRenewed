package dev.felnull.otyacraftengine.client.model.impl;

import dev.felnull.otyacraftengine.client.callpoint.ModelRegister;
import dev.felnull.otyacraftengine.client.model.ModelHolder;
import dev.felnull.otyacraftengine.client.util.OEModelUtils;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class ModelHolderImpl implements ModelHolder {
    private final ResourceLocation modelLocation;

    public ModelHolderImpl(ResourceLocation modelLocation) {
        this.modelLocation = modelLocation;
    }

    @Override
    public @NotNull BakedModel get() {
        return OEModelUtils.getModel(modelLocation);
    }

    @Override
    public void registering(@NotNull ModelRegister register) {
        register.addModelLoad(modelLocation);
    }

    @Override
    public String toString() {
        return "ModelHolder{" + "location=" + modelLocation + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ModelHolderImpl that = (ModelHolderImpl) o;
        return Objects.equals(modelLocation, that.modelLocation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(modelLocation);
    }
}
