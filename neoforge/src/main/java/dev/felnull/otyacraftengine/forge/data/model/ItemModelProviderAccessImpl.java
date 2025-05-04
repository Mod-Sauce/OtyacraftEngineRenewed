package dev.felnull.otyacraftengine.forge.data.model;

import dev.felnull.otyacraftengine.data.model.FileModel;
import dev.felnull.otyacraftengine.data.model.FileTexture;
import dev.felnull.otyacraftengine.data.model.ItemModelProviderAccess;
import dev.felnull.otyacraftengine.data.model.MutableFileModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class ItemModelProviderAccessImpl implements ItemModelProviderAccess {
    private final ItemModelProvider itemModelProvider;

    public ItemModelProviderAccessImpl(ItemModelProvider itemModelProvider) {
        this.itemModelProvider = itemModelProvider;
    }

    private MutableFileModel of(ItemModelBuilder itemModelBuilder) {
        return new ItemMutableFileModelImpl(itemModelBuilder);
    }

    @Override
    public @NotNull MutableFileModel basicFlatItem(@NotNull Item item) {
        return of(this.itemModelProvider.basicItem(item));
    }

    @Override
    public @NotNull MutableFileModel basicFlatItem(@NotNull ResourceLocation itemLocation) {
        return of(this.itemModelProvider.basicItem(itemLocation));
    }

    @Override
    public @NotNull MutableFileModel basicFlatItem(@NotNull FileTexture itemTexture) {
        var mb = this.itemModelProvider.getBuilder(itemTexture.getLocation().toString())
                .parent(new ModelFile.UncheckedModelFile("item/generated"));

        var loc = itemTexture.getLocation();
        return of(setTexture(mb, "layer0", FileTexture.of(new ResourceLocation(loc.getNamespace(), "item/" + loc.getPath()), itemTexture.isExistingCheck())));
    }

    @Override
    public @NotNull MutableFileModel basicFlatItem(@NotNull Item item, @NotNull ResourceLocation texture) {
        return basicFlatItem(Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(item)), texture);
    }

    @Override
    public @NotNull MutableFileModel basicFlatItem(@NotNull Item item, @NotNull FileTexture itemTexture) {
        return basicFlatItem(Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(item)), itemTexture);
    }

    @Override
    public @NotNull MutableFileModel basicFlatItem(@NotNull ResourceLocation itemLocation, @NotNull ResourceLocation texture) {
        return of(this.itemModelProvider.getBuilder(itemLocation.toString())
                .parent(new ModelFile.UncheckedModelFile("item/generated"))
                .texture("layer0", texture));
    }

    @Override
    public @NotNull MutableFileModel basicFlatItem(@NotNull ResourceLocation itemLocation, @NotNull FileTexture itemTexture) {
        var mb = this.itemModelProvider.getBuilder(itemLocation.toString())
                .parent(new ModelFile.UncheckedModelFile("item/generated"));

        return of(setTexture(mb, "layer0", itemTexture));
    }

    @Override
    public @NotNull MutableFileModel handheldFlatItem(@NotNull Item item) {
        return handheldFlatItem(Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(item)));
    }

    @Override
    public @NotNull MutableFileModel handheldFlatItem(@NotNull ResourceLocation itemLocation) {
        return of(this.itemModelProvider.getBuilder(itemLocation.toString())
                .parent(new ModelFile.UncheckedModelFile("item/handheld"))
                .texture("layer0", new ResourceLocation(itemLocation.getNamespace(), "item/" + itemLocation.getPath())));
    }

    @Override
    public @NotNull MutableFileModel handheldFlatItem(@NotNull FileTexture itemTexture) {
        var mb = this.itemModelProvider.getBuilder(itemTexture.getLocation().toString())
                .parent(new ModelFile.UncheckedModelFile("item/handheld"));

        var loc = itemTexture.getLocation();
        return of(setTexture(mb, "layer0", FileTexture.of(new ResourceLocation(loc.getNamespace(), "item/" + loc.getPath()), itemTexture.isExistingCheck())));
    }

    @Override
    public @NotNull MutableFileModel parentedItem(@NotNull Item item, @NotNull ResourceLocation parentLocation) {
        return parentedItem(Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(item)), parentLocation);
    }

    @Override
    public @NotNull MutableFileModel parentedItem(@NotNull ResourceLocation itemLocation, @NotNull ResourceLocation parentLocation) {
        return of(this.itemModelProvider.getBuilder(itemLocation.toString())
                .parent(new ModelFile.UncheckedModelFile(parentLocation)));
    }

    @Override
    public @NotNull FileModel existingModel(@NotNull ResourceLocation location) {
        return new FileModelImpl(this.itemModelProvider.getExistingFile(location));
    }

    private ItemModelBuilder setTexture(ItemModelBuilder itemModelBuilder, String key, FileTexture fileTexture) {
        if (fileTexture.isExistingCheck()) {
            itemModelBuilder.texture(key, fileTexture.getLocation());
        } else {
            ((UncheckedTextureModelBuilder) itemModelBuilder).uncheckedTexture(key, fileTexture.getLocation());
        }
        return itemModelBuilder;
    }
}
