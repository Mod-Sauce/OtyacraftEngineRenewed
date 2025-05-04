package dev.felnull.otyacraftengine.forge.data.model;

import dev.felnull.otyacraftengine.data.model.FileModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.generators.ModelFile;
import org.jetbrains.annotations.NotNull;

public class FileModelImpl implements FileModel {
    private final ModelFile modelFile;

    public FileModelImpl(ModelFile modelFile) {
        this.modelFile = modelFile;
    }

    @Override
    public @NotNull ResourceLocation getLocation() {
        return modelFile.getLocation();
    }

    public static ModelFile getModelFile(FileModel fileModel) {
        if (fileModel instanceof FileModelImpl fmi)
            return fmi.modelFile;

        throw new IllegalStateException("Not forge impl model file");
    }
}