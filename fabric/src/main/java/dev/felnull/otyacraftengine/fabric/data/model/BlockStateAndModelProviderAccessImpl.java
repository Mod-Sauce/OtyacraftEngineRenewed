package dev.felnull.otyacraftengine.fabric.data.model;

import com.google.gson.JsonElement;
import dev.felnull.otyacraftengine.data.CrossDataGeneratorAccess;
import dev.felnull.otyacraftengine.data.model.*;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.blockstates.BlockStateGenerator;
import net.minecraft.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.data.models.blockstates.Variant;
import net.minecraft.data.models.blockstates.VariantProperties;
import net.minecraft.data.models.model.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class BlockStateAndModelProviderAccessImpl implements BlockStateAndModelProviderAccess {
    private final CrossDataGeneratorAccess crossDataGeneratorAccess;
    private final BlockModelGenerators blockModelGenerators;
    private final ItemModelProviderAccess itemModelProviderAccess;

    public BlockStateAndModelProviderAccessImpl(CrossDataGeneratorAccess crossDataGeneratorAccess, BlockModelGenerators blockModelGenerators) {
        this.crossDataGeneratorAccess = crossDataGeneratorAccess;
        this.blockModelGenerators = blockModelGenerators;
        this.itemModelProviderAccess = new ItemModelProviderAccessImpl(blockModelGenerators.modelOutput);
    }

    @Override
    public @NotNull BiConsumer<ResourceLocation, Supplier<JsonElement>> modelOutput() {
        return this.blockModelGenerators.modelOutput;
    }

    @Override
    public void simpleCubeBlockStateModelAndItemModel(@NotNull Block block) {
        this.blockModelGenerators.createTrivialCube(block);
    }

    private FileModel of(ResourceLocation location) {
        return new FileModelImpl(location);
    }

    @Override
    public @NotNull FileModel cubeAllBlockModel(@NotNull ResourceLocation blockLocation, @NotNull FileTexture fileTexture) {
        return of(ModelTemplates.CUBE_ALL.create(blockLoc(blockLocation), TextureMapping.cube(fileTexture.getLocation()), blockModelGenerators.modelOutput));
    }

    @Override
    public @NotNull FileModel cubeAllBlockModel(@NotNull Block block, @NotNull FileTexture fileTexture) {
        return of(ModelTemplates.CUBE_ALL.create(block, TextureMapping.cube(fileTexture.getLocation()), blockModelGenerators.modelOutput));
    }

    @Override
    public @NotNull FileModel cubeBlockModel(@NotNull ResourceLocation blockLocation, @NotNull FileTexture down, @NotNull FileTexture up, @NotNull FileTexture north, @NotNull FileTexture south, @NotNull FileTexture east, @NotNull FileTexture west) {
        TextureMapping mapping = new TextureMapping();
        mapping.put(TextureSlot.DOWN, down.getLocation());
        mapping.put(TextureSlot.UP, up.getLocation());
        mapping.put(TextureSlot.NORTH, north.getLocation());
        mapping.put(TextureSlot.SOUTH, south.getLocation());
        mapping.put(TextureSlot.EAST, east.getLocation());
        mapping.put(TextureSlot.WEST, west.getLocation());
        mapping.put(TextureSlot.PARTICLE, north.getLocation());

        return of(ModelTemplates.CUBE.create(blockLoc(blockLocation), mapping, blockModelGenerators.modelOutput));
    }

    @Override
    public @NotNull FileModel cubeBlockModel(@NotNull Block block, @NotNull FileTexture down, @NotNull FileTexture up, @NotNull FileTexture north, @NotNull FileTexture south, @NotNull FileTexture east, @NotNull FileTexture west) {
        TextureMapping mapping = new TextureMapping();
        mapping.put(TextureSlot.DOWN, down.getLocation());
        mapping.put(TextureSlot.UP, up.getLocation());
        mapping.put(TextureSlot.NORTH, north.getLocation());
        mapping.put(TextureSlot.SOUTH, south.getLocation());
        mapping.put(TextureSlot.EAST, east.getLocation());
        mapping.put(TextureSlot.WEST, west.getLocation());
        mapping.put(TextureSlot.PARTICLE, north.getLocation());

        return of(ModelTemplates.CUBE.create(block, mapping, blockModelGenerators.modelOutput));
    }


    @Override
    public @NotNull FileModel cubeBottomTopBlockModel(@NotNull ResourceLocation blockLocation, @NotNull FileTexture bottom, @NotNull FileTexture side, @NotNull FileTexture top) {
        TextureMapping mapping = new TextureMapping();
        mapping.put(TextureSlot.TOP, top.getLocation());
        mapping.put(TextureSlot.BOTTOM, bottom.getLocation());
        mapping.put(TextureSlot.SIDE, side.getLocation());

        return of(ModelTemplates.CUBE_BOTTOM_TOP.create(blockLoc(blockLocation), mapping, blockModelGenerators.modelOutput));
    }

    @Override
    public @NotNull FileModel cubeBottomTopBlockModel(@NotNull Block block, @NotNull FileTexture bottom, @NotNull FileTexture side, @NotNull FileTexture top) {
        TextureMapping mapping = new TextureMapping();
        mapping.put(TextureSlot.TOP, top.getLocation());
        mapping.put(TextureSlot.BOTTOM, bottom.getLocation());
        mapping.put(TextureSlot.SIDE, side.getLocation());

        return of(ModelTemplates.CUBE_BOTTOM_TOP.create(block, mapping, blockModelGenerators.modelOutput));
    }

    private ResourceLocation blockLoc(ResourceLocation loc) {
        return new ResourceLocation(loc.getNamespace(), "block/" + loc.getPath());
    }

    private ResourceLocation blockLoc(String name) {
        return new ResourceLocation(crossDataGeneratorAccess.getMod().getModId(), "block/" + name);
    }

    @Override
    public @NotNull MutableFileModel parentedBlockModel(@NotNull Block block, @NotNull ResourceLocation parentLocation) {
        var ji = new JsonModelInjector(this.blockModelGenerators.modelOutput);
        var loc = ModelLocationUtils.getModelLocation(block);
        ji.injectedModelOutput().accept(loc, new DelegatedModel(parentLocation));
        return new MutableFileModelImpl(loc, ji);
    }

    @Override
    public @NotNull FileModel existingModel(@NotNull ResourceLocation location) {
        return of(location);
    }


    @Override
    public @NotNull FileModel particleOnlyModel(@NotNull Block block, @NotNull FileTexture particleLocation) {
        TextureMapping textureMapping = TextureMapping.particle(particleLocation.getLocation());
        return of(ModelTemplates.PARTICLE_ONLY.create(block, textureMapping, blockModelGenerators.modelOutput));
    }

    @Override
    public void simpleBlockState(@NotNull Block block, @NotNull FileModel model) {
        this.blockModelGenerators.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(block, model.getLocation()));
    }

    @Override
    public void simpleBlockItemModel(@NotNull Block block, @NotNull FileModel model) {
        this.blockModelGenerators.delegateItemModel(block, model.getLocation());
    }

    @Override
    public void horizontalBlockState(@NotNull Block block, @NotNull FileModel model) {
        this.blockModelGenerators.blockStateOutput.accept(MultiVariantGenerator.multiVariant(block, Variant.variant().with(VariantProperties.MODEL, model.getLocation()))
                .with(BlockModelGenerators.createHorizontalFacingDispatch()));
    }

    @Override
    public void parentedBlockItemModel(@NotNull Block block, @NotNull ResourceLocation parentLocation) {
        this.blockModelGenerators.delegateItemModel(block, parentLocation);
    }

    @Override
    public void addBlockStateGenerator(@NotNull BlockStateGenerator blockStateGenerator) {
        this.blockModelGenerators.blockStateOutput.accept(blockStateGenerator);
    }

    @Override
    public ItemModelProviderAccess itemModelProviderAccess() {
        return this.itemModelProviderAccess;
    }
}
