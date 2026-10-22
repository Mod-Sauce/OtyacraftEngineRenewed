package org.modsauce.otyacraftenginerenewed.fabric.data.model;

import java.util.function.BiConsumer;

import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.blockstates.BlockStateGenerator;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.blockstates.Variant;
import net.minecraft.client.data.models.blockstates.VariantProperties;
import net.minecraft.client.data.models.model.*;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import org.jetbrains.annotations.NotNull;
import org.modsauce.otyacraftenginerenewed.data.CrossDataGeneratorAccess;
import org.modsauce.otyacraftenginerenewed.data.model.*;

public class BlockStateAndModelProviderAccessImpl
  implements BlockStateAndModelProviderAccess {

  private final CrossDataGeneratorAccess crossDataGeneratorAccess;
  private final BlockModelGenerators blockModelGenerators;
  private final ItemModelProviderAccess itemModelProviderAccess;

  public BlockStateAndModelProviderAccessImpl(
    CrossDataGeneratorAccess crossDataGeneratorAccess,
    BlockModelGenerators blockModelGenerators
  ) {
    this.crossDataGeneratorAccess = crossDataGeneratorAccess;
    this.blockModelGenerators = blockModelGenerators;
    this.itemModelProviderAccess = new ItemModelProviderAccessImpl(
      blockModelGenerators.modelOutput
    );
  }

  @Override
  public void simpleFlatBlockItemModel(@NotNull Block block) {
    this.blockModelGenerators.createFlatItemModel(Item.byBlock(block));
  }

  @Override
  public @NotNull FileModel uncheckedModel(
    @NotNull Identifier modelLocation
  ) {
    return of(modelLocation);
  }

  @Override
  public void stairsBlockItemModel(
    @NotNull StairBlock stairBlock,
    @NotNull FileModel baseBlockModel
  ) {
    TextureMapping mapping = TextureMapping.cube(baseBlockModel.getLocation());
    MultiVariant innerModel = ModelTemplates.STAIRS_INNER.create(
      stairBlock,
      mapping,
      this.blockModelGenerators.modelOutput
    );
    MultiVariant straightModel = ModelTemplates.STAIRS_STRAIGHT.create(
      stairBlock,
      mapping,
      this.blockModelGenerators.modelOutput
    );
    MultiVariant outerModel = ModelTemplates.STAIRS_OUTER.create(
      stairBlock,
      mapping,
      this.blockModelGenerators.modelOutput
    );

    this.blockModelGenerators.blockStateOutput.accept(
      BlockModelGenerators.createStairs(
        stairBlock,
        innerModel,
        straightModel,
        outerModel
      )
    );
    this.blockModelGenerators.delegateItemModel(stairBlock, straightModel);
  }

  @Override
  public void slabBlockItemModel(
    @NotNull SlabBlock slabBlock,
    @NotNull FileModel baseBlockModel
  ) {
    Block fullBlockModel = baseBlockModel.getLocation();
    TextureMapping mapping = TextureMapping.cube(fullBlockModel);

    Identifier slabBottomModel = ModelTemplates.SLAB_BOTTOM.create(
      slabBlock,
      mapping,
      this.blockModelGenerators.modelOutput
    );
    Identifier slabTop = ModelTemplates.SLAB_TOP.create(
      slabBlock,
      mapping,
      this.blockModelGenerators.modelOutput
    );

    this.blockModelGenerators.blockStateOutput.accept(
      BlockModelGenerators.createSlab(
        slabBlock,
        slabBottomModel,
        slabTop,
        fullBlockModel
      )
    );
  }

  @Override
  public void logBlockItemModel(@NotNull RotatedPillarBlock block) {
    this.blockModelGenerators.woodProvider(block).logWithHorizontal(block);
  }

  @Override
  public @NotNull BiConsumer<Identifier, ModelInstance> modelOutput() {
    return this.blockModelGenerators.modelOutput;
  }

  @Override
  public void simpleCubeBlockStateModelAndItemModel(@NotNull Block block) {
    this.blockModelGenerators.createTrivialCube(block);
  }

  private FileModel of(Identifier location) {
    return new FileModelImpl(location);
  }

  @Override
  public @NotNull FileModel cubeAllBlockModel(
    @NotNull Identifier blockLocation,
    @NotNull FileTexture fileTexture
  ) {
    return of(
      ModelTemplates.CUBE_ALL.create(
        blockLoc(blockLocation),
        TextureMapping.cube(fileTexture.getLocation()),
        blockModelGenerators.modelOutput
      )
    );
  }

  @Override
  public @NotNull FileModel cubeAllBlockModel(
    @NotNull Block block,
    @NotNull FileTexture fileTexture
  ) {
    return of(
      ModelTemplates.CUBE_ALL.create(
        block,
        TextureMapping.cube(fileTexture.getLocation()),
        blockModelGenerators.modelOutput
      )
    );
  }

  @Override
  public @NotNull FileModel cubeBlockModel(
    @NotNull Identifier blockLocation,
    @NotNull FileTexture down,
    @NotNull FileTexture up,
    @NotNull FileTexture north,
    @NotNull FileTexture south,
    @NotNull FileTexture east,
    @NotNull FileTexture west
  ) {
    TextureMapping mapping = new TextureMapping();
    mapping.put(TextureSlot.DOWN, down.getLocation());
    mapping.put(TextureSlot.UP, up.getLocation());
    mapping.put(TextureSlot.NORTH, north.getLocation());
    mapping.put(TextureSlot.SOUTH, south.getLocation());
    mapping.put(TextureSlot.EAST, east.getLocation());
    mapping.put(TextureSlot.WEST, west.getLocation());
    mapping.put(TextureSlot.PARTICLE, north.getLocation());

    return of(
      ModelTemplates.CUBE.create(
        blockLoc(blockLocation),
        mapping,
        blockModelGenerators.modelOutput
      )
    );
  }

  @Override
  public @NotNull FileModel cubeBlockModel(
    @NotNull Block block,
    @NotNull FileTexture down,
    @NotNull FileTexture up,
    @NotNull FileTexture north,
    @NotNull FileTexture south,
    @NotNull FileTexture east,
    @NotNull FileTexture west
  ) {
    TextureMapping mapping = new TextureMapping();
    mapping.put(TextureSlot.DOWN, down.getLocation());
    mapping.put(TextureSlot.UP, up.getLocation());
    mapping.put(TextureSlot.NORTH, north.getLocation());
    mapping.put(TextureSlot.SOUTH, south.getLocation());
    mapping.put(TextureSlot.EAST, east.getLocation());
    mapping.put(TextureSlot.WEST, west.getLocation());
    mapping.put(TextureSlot.PARTICLE, north.getLocation());

    return of(
      ModelTemplates.CUBE.create(
        block,
        mapping,
        blockModelGenerators.modelOutput
      )
    );
  }

  @Override
  public @NotNull FileModel cubeBottomTopBlockModel(
    @NotNull Identifier blockLocation,
    @NotNull FileTexture bottom,
    @NotNull FileTexture side,
    @NotNull FileTexture top
  ) {
    TextureMapping mapping = new TextureMapping();
    mapping.put(TextureSlot.TOP, top.getLocation());
    mapping.put(TextureSlot.BOTTOM, bottom.getLocation());
    mapping.put(TextureSlot.SIDE, side.getLocation());

    return of(
      ModelTemplates.CUBE_BOTTOM_TOP.create(
        blockLoc(blockLocation),
        mapping,
        blockModelGenerators.modelOutput
      )
    );
  }

  @Override
  public @NotNull FileModel cubeBottomTopBlockModel(
    @NotNull Block block,
    @NotNull FileTexture bottom,
    @NotNull FileTexture side,
    @NotNull FileTexture top
  ) {
    TextureMapping mapping = new TextureMapping();
    mapping.put(TextureSlot.TOP, top.getLocation());
    mapping.put(TextureSlot.BOTTOM, bottom.getLocation());
    mapping.put(TextureSlot.SIDE, side.getLocation());

    return of(
      ModelTemplates.CUBE_BOTTOM_TOP.create(
        block,
        mapping,
        blockModelGenerators.modelOutput
      )
    );
  }

  private Identifier blockLoc(Identifier loc) {
    return Identifier.fromNamespaceAndPath(
      loc.getNamespace(),
      "block/" + loc.getPath()
    );
  }

  private Identifier blockLoc(String name) {
    return Identifier.fromNamespaceAndPath(
      crossDataGeneratorAccess.getMod().getModId(),
      "block/" + name
    );
  }

  @Override
  public @NotNull MutableFileModel parentedBlockModel(
    @NotNull Block block,
    @NotNull Identifier parentLocation
  ) {
    var ji = new JsonModelInjector(this.blockModelGenerators.modelOutput);
    var loc = ModelLocationUtils.getModelLocation(block);
    ji.injectedModelOutput().accept(loc, new DelegatedModel(parentLocation));
    return new MutableFileModelImpl(loc, ji);
  }

  @Override
  public @NotNull FileModel existingModel(@NotNull Identifier location) {
    return of(location);
  }

  @Override
  public @NotNull FileModel particleOnlyModel(
    @NotNull Block block,
    @NotNull FileTexture particleLocation
  ) {
    TextureMapping textureMapping = TextureMapping.particle(
      particleLocation.getLocation()
    );
    return of(
      ModelTemplates.PARTICLE_ONLY.create(
        block,
        textureMapping,
        blockModelGenerators.modelOutput
      )
    );
  }

  @Override
  public void simpleBlockState(@NotNull Block block, @NotNull FileModel model) {
    this.blockModelGenerators.blockStateOutput.accept(
      BlockModelGenerators.createSimpleBlock(block, model.getLocation())
    );
  }

  @Override
  public void simpleBlockItemModel(
    @NotNull Block block,
    @NotNull FileModel model
  ) {
    this.blockModelGenerators.delegateItemModel(block, model.getLocation());
  }

  @Override
  public void horizontalBlockState(
      @NotNull Block block,
      @NotNull FileModel model
  ) {
      this.blockModelGenerators.blockStateOutput.accept(
          MultiVariantGenerator.dispatch(
              block,
              BlockModelGenerators.plainVariant(model.getLocation())
          ).with(BlockModelGenerators.ROTATION_HORIZONTAL_FACING)
      );
  }

  @Override
  public void parentedBlockItemModel(
    @NotNull Block block,
    @NotNull Identifier parentLocation
  ) {
    this.blockModelGenerators.delegateItemModel(block, parentLocation);
  }

  @Override
  public void addBlockStateGenerator(
          @NotNull BlockStateGenerator blockStateGenerator
  ) {
    this.blockModelGenerators.blockStateOutput.accept(blockStateGenerator);
  }

  @Override
  public ItemModelProviderAccess itemModelProviderAccess() {
    return this.itemModelProviderAccess;
  }
}
