package org.modsauce.otyacraftenginerenewed.data.provider;

import org.modsauce.otyacraftenginerenewed.OtyacraftEngine;
import org.modsauce.otyacraftenginerenewed.data.CrossDataGeneratorAccess;
import org.modsauce.otyacraftenginerenewed.data.model.BlockStateAndModelProviderAccess;
import net.minecraft.core.Direction;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.models.blockstates.PropertyDispatch;
import net.minecraft.data.models.blockstates.Variant;
import net.minecraft.data.models.blockstates.VariantProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

public abstract class BlockStateAndModelProviderWrapper extends DataProviderWrapper<DataProvider> {
    public static final ResourceLocation OE_BASE_BLOCK_ENTITY_MODEL = new ResourceLocation(OtyacraftEngine.MODID, "base/block_entity");
    public static final ResourceLocation OE_BASE_BLOCK_ENTITY_APPEARANCE_MODEL = new ResourceLocation(OtyacraftEngine.MODID, "base/block_entity_appearance");
    private final DataProvider blockStateAndModelProvider;

    public BlockStateAndModelProviderWrapper(PackOutput packOutput, CrossDataGeneratorAccess crossDataGeneratorAccess) {
        super(packOutput, crossDataGeneratorAccess);
        this.blockStateAndModelProvider = crossDataGeneratorAccess.createBlockStateAndModelProvider(packOutput, this);
    }

    @Override
    public DataProvider getProvider() {
        return blockStateAndModelProvider;
    }

    public abstract void generateStatesAndModels(BlockStateAndModelProviderAccess providerAccess);

    /**
     * BlockModelGenerators$createHorizontalFacingDispatchの移植
     *
     * @return PropertyDispatch
     */
    public PropertyDispatch createHorizontalFacingDispatch() {
        return PropertyDispatch.property(BlockStateProperties.HORIZONTAL_FACING)
                .select(Direction.EAST, Variant.variant().with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                .select(Direction.SOUTH, Variant.variant().with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                .select(Direction.WEST, Variant.variant().with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
                .select(Direction.NORTH, Variant.variant());
    }

    /**
     * BlockModelGenerators$createBooleanModelDispatchの移植
     *
     * @return PropertyDispatch
     */
    public PropertyDispatch createBooleanModelDispatch(BooleanProperty booleanProperty, ResourceLocation trueLoc, ResourceLocation falseLoc) {
        return PropertyDispatch.property(booleanProperty)
                .select(true, Variant.variant().with(VariantProperties.MODEL, trueLoc))
                .select(false, Variant.variant().with(VariantProperties.MODEL, falseLoc));
    }
}
