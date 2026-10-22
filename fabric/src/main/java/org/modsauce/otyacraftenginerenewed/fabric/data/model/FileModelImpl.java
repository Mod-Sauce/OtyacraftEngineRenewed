package org.modsauce.otyacraftenginerenewed.fabric.data.model;

import org.modsauce.otyacraftenginerenewed.data.model.FileModel;
import net.minecraft.resources.Identifier;

public class FileModelImpl implements FileModel {
    private final Identifier location;

    public FileModelImpl(Identifier location) {
        this.location = location;
    }

    @Override
    public Identifier getLocation() {
        return location;
    }
}
