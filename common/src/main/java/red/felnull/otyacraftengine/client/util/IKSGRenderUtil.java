package red.felnull.otyacraftengine.client.util;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.block.ModelBlockRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Fluid;
import red.felnull.otyacraftengine.client.impl.OEClientExpectPlatform;
import red.felnull.otyacraftengine.fluid.IIkisugibleFluid;
import red.felnull.otyacraftengine.util.IKSGColorUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class IKSGRenderUtil {
    private static final Minecraft mc = Minecraft.getInstance();
    private static final Map<ResourceLocation, BakedModel> BAKED_MODELS = new HashMap<>();
    private static final Map<ResourceLocation, BakedModel> BAKED_OBJMODELS = new HashMap<>();

    public static void drawPlayerFase(PoseStack psstack, String name, int x, int y) {
        psstack.pushPose();
        ResourceLocation plskin = IKSGTextureUtil.getPlayerSkinTexture(name);
        guiBindAndBlit(plskin, psstack, x, y, 8, 8, 8, 8, 64, 64);
        guiBindAndBlit(plskin, psstack, x, y, 40, 8, 8, 8, 64, 64);
        psstack.popPose();
    }

    public static void guiBindAndBlit(ResourceLocation location, PoseStack psstack, int x, int y, int textureStartX, int textureStartY, int textureFinishWidth, int textureFinishHeight) {
        guiBindAndBlit(location, psstack, x, y, textureStartX, textureStartY, textureFinishWidth, textureFinishHeight, 256, 256);
    }

    public static void guiBindAndBlit(ResourceLocation location, PoseStack psstack, int x, int y, float textureStartX, float textureStartY, int textureFinishWidth, int textureFinishHeight, int textureSizeX, int textureSizeY) {
        psstack.pushPose();
        mc.getTextureManager().bind(location);
        GuiComponent.blit(psstack, x, y, textureStartX, textureStartY, textureFinishWidth, textureFinishHeight, textureSizeX, textureSizeY);
        psstack.popPose();
    }


    public static void matrixTranslatef16Divisions(PoseStack psstack, double x, double y, double z) {
        float pix = 1f / 16f;
        psstack.translate(pix * x, pix * y, pix * z);
    }

    public static void matrixScalf(PoseStack psstack, float allsclae) {
        psstack.scale(allsclae, allsclae, allsclae);
    }

    public static void matrixRotateDegreef(PoseStack psstack, float x, float y, float z) {
        matrixRotateDegreefX(psstack, x);
        matrixRotateDegreefY(psstack, y);
        matrixRotateDegreefZ(psstack, z);
    }

    public static void matrixRotateDegreefX(PoseStack psstack, float x) {
        psstack.mulPose(Vector3f.XP.rotationDegrees(x));
    }

    public static void matrixRotateDegreefY(PoseStack psstack, float y) {
        psstack.mulPose(Vector3f.YP.rotationDegrees(y));
    }

    public static void matrixRotateDegreefZ(PoseStack psstack, float z) {
        psstack.mulPose(Vector3f.ZP.rotationDegrees(z));
    }

    public static void matrixRotateHorizontal(PoseStack psstack, BlockState state) {
        Direction direction = state.getValue(BlockStateProperties.HORIZONTAL_FACING);
        matrixRotateDirection(psstack, direction);
    }

    public static void matrixRotateDirection(PoseStack psstack, Direction direction) {
        if (direction == Direction.WEST) {
            matrixRotateDegreefY(psstack, 180);
            psstack.translate(-1f, 0f, -1f);
        } else if (direction == Direction.NORTH) {
            matrixRotateDegreefY(psstack, 90);
            psstack.translate(-1f, 0f, 0f);
        } else if (direction == Direction.SOUTH) {
            matrixRotateDegreefY(psstack, 270);
            psstack.translate(0f, 0f, -1f);
        }
    }

    public static ModelBakery getModelBakery() {
        return OEClientExpectPlatform.getModelBakery();
    }

    public static BakedModel getBakedModel(ResourceLocation location) {

        if (BAKED_MODELS.containsKey(location))
            return BAKED_MODELS.get(location);

        BakedModel model = getModelBakery().bake(location, BlockModelRotation.X0_Y0);
        BAKED_MODELS.put(location, model);
        return model;
    }

    public static void renderBlockBakedModel(BlockAndTintGetter getter, BakedModel model, BlockState state, BlockPos pos, PoseStack poseStack, VertexConsumer consumer, boolean checkSides, Random randomIn, long rand, int combinedOverlayIn) {
        BlockRenderDispatcher brd = Minecraft.getInstance().getBlockRenderer();
        ModelBlockRenderer bmr = brd.getModelRenderer();
        bmr.tesselateBlock(getter, model, state, pos, poseStack, consumer, checkSides, randomIn, rand, combinedOverlayIn);
    }

    public static void renderBlockBakedModel(BlockAndTintGetter getter, BakedModel model, BlockState state, BlockPos pos, PoseStack poseStack, VertexConsumer consumer, Random randomIn, int combinedOverlayIn) {
        renderBlockBakedModel(getter, model, state, pos, poseStack, consumer, false, randomIn, 0, combinedOverlayIn);
    }

    public static void renderBlockBakedModel(BakedModel model, PoseStack poseStack, VertexConsumer consumer, int combinedOverlayIn, BlockEntity tile) {
        renderBlockBakedModel(tile.getLevel(), model, tile.getBlockState(), tile.getBlockPos(), poseStack, consumer, tile.getLevel().random, combinedOverlayIn);
    }

    public static float partialTicksMisalignment(float val, float prevVal, float partialTicks) {
        return val + (prevVal - val) * partialTicks;
    }

    public static BakedModel getBlockBakedModel(ModelResourceLocation modelResourceLocation) {
        BlockRenderDispatcher blockrendererdispatcher = mc.getBlockRenderer();
        ModelManager modelmanager = blockrendererdispatcher.getBlockModelShaper().getModelManager();
        return modelmanager.getModel(modelResourceLocation);
    }

    public static void renderBakedModel(PoseStack poseStack, VertexConsumer vertexConsumer, BlockState state, BakedModel bakedModel, int combinedLight, int combinedOverlay) {
        BlockRenderDispatcher brd = mc.getBlockRenderer();
        ModelBlockRenderer bmr = brd.getModelRenderer();
        bmr.renderModel(poseStack.last(), vertexConsumer, state, bakedModel, 1.0F, 1.0F, 1.0F, combinedLight, combinedOverlay);
    }

    public static RenderType getTextuerRenderType(ResourceLocation locationIn) {
        return RenderType.text(locationIn);
    }

    public static void renderSpritePanel(ResourceLocation texlocation, PoseStack poseStack, MultiBufferSource multiBufferSource, float x, float y, float z, float pitch, float yaw, float roll, float w, float h, float texStartX, float texStartY, float texFinishX, float texFinishY, float texSizeW, float texSizeH, int combinedOverlayIn, int combinedLightIn) {
        renderSpritePanel(texlocation, poseStack, multiBufferSource, x, y, z, 1f, 1f, 1f, 1f, pitch, yaw, roll, w, h, texStartX, texStartY, texFinishX, texFinishY, texSizeW, texSizeH, combinedOverlayIn, combinedLightIn);
    }

    public static void renderSpritePanel(ResourceLocation texlocation, PoseStack poseStack, MultiBufferSource multiBufferSource, float x, float y, float z, float r, float g, float b, float a, float pitch, float yaw, float roll, float w, float h, float texStartX, float texStartY, float texFinishX, float texFinishY, float texSizeW, float texSizeH, int combinedOverlayIn, int combinedLightIn) {
        poseStack.pushPose();
        matrixRotateDegreefY(poseStack, yaw);
        matrixRotateDegreefX(poseStack, pitch);
        matrixRotateDegreefZ(poseStack, roll);
        VertexConsumer vc = multiBufferSource.getBuffer(getTextuerRenderType(texlocation));
        float wst = texStartX / texSizeW;
        float wft = texFinishX / texSizeW + wst;
        float hst = texStartY / texSizeH;
        float hft = texFinishY / texSizeH + hst;
        PoseStack.Pose pose = poseStack.last();
        vertexed(vc, pose, 0, 0, 0, texStartX, texStartY, r, g, b, a, combinedOverlayIn, combinedLightIn);
        vertexed(vc, pose, w, 0, 0, texFinishX, texStartY, r, g, b, a, combinedOverlayIn, combinedLightIn);
        vertexed(vc, pose, w, h, 0, texFinishX, texFinishY, r, g, b, a, combinedOverlayIn, combinedLightIn);
        vertexed(vc, pose, 0, h, 0, texStartX, texFinishY, r, g, b, a, combinedOverlayIn, combinedLightIn);
        poseStack.popPose();
    }

    private static void vertexed(VertexConsumer builder, PoseStack.Pose pose, float x, float y, float z, float u, float v, float r, float g, float b, float a, int combinedOverlayIn, int combinedLightIn) {
        builder.vertex(pose.pose(), x, y, z).color(r, g, b, a).uv(u, v).overlayCoords(combinedOverlayIn).uv2(combinedLightIn).normal(pose.normal(), 0f, 0f, 0f).endVertex();
    }

    public static void renderSpritePanel(TextureAtlasSprite sprite, PoseStack poseStack, MultiBufferSource multiBufferSource, float x, float y, float z, float r, float g, float b, float a, float pitch, float yaw, float roll, float w, float h, float texStartX, float texStartY, float texFinishX, float texFinishY, int combinedOverlayIn, int combinedLightIn) {
        poseStack.pushPose();
        poseStack.translate(x, y, z);
        matrixRotateDegreefY(poseStack, yaw);
        matrixRotateDegreefX(poseStack, pitch);
        matrixRotateDegreefZ(poseStack, roll);
        VertexConsumer vc = multiBufferSource.getBuffer(RenderType.text(sprite.atlas().location()));

        PoseStack.Pose pose = poseStack.last();
        vertexed(vc, pose, 0, 0, 0, texStartX, texStartY, r, g, b, a, combinedOverlayIn, combinedLightIn);
        vertexed(vc, pose, w, 0, 0, texFinishX, texStartY, r, g, b, a, combinedOverlayIn, combinedLightIn);
        vertexed(vc, pose, w, h, 0, texFinishX, texFinishY, r, g, b, a, combinedOverlayIn, combinedLightIn);
        vertexed(vc, pose, 0, h, 0, texStartX, texFinishY, r, g, b, a, combinedOverlayIn, combinedLightIn);
        poseStack.popPose();
    }

    public static void renderFluid(Fluid fluid, BlockAndTintGetter getter, BlockPos pos, PoseStack poseStack, MultiBufferSource mbs, double parsent, float x, float y, float z, float w, float h, int combinedLightIn, int combinedOverlayIn) {
        if (fluid instanceof IIkisugibleFluid) {
            if (getter != null && pos != null)
                renderFluid(fluid, ((IIkisugibleFluid) fluid).getProperties().getWorldColor(getter, pos), poseStack, mbs, parsent, x, y, z, w, h, combinedLightIn, combinedOverlayIn);
            else
                renderFluid(fluid, poseStack, mbs, parsent, x, y, z, w, h, combinedLightIn, combinedOverlayIn);
        }
    }

    public static void renderFluid(Fluid fluid, PoseStack poseStack, MultiBufferSource mbs, double parsent, float x, float y, float z, float w, float h, int combinedLightIn, int combinedOverlayIn) {
        if (fluid instanceof IIkisugibleFluid) {
            renderFluid(fluid, ((IIkisugibleFluid) fluid).getProperties().getColor(), poseStack, mbs, parsent, x, y, z, w, h, combinedLightIn, combinedOverlayIn);
        }
    }

    private static void renderFluid(Fluid fluid, int color, PoseStack poseStack, MultiBufferSource mbs, double parsent, float x, float y, float z, float w, float h, int combinedLightIn, int combinedOverlayIn) {
        ResourceLocation location = ((IIkisugibleFluid) fluid).getProperties().getStillTexture();
        TextureAtlasSprite sprite = mc.getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(location);
        float r = (float) IKSGColorUtil.getRed(color) / 255f;
        float g = (float) IKSGColorUtil.getGreen(color) / 255f;
        float b = (float) IKSGColorUtil.getBlue(color) / 255f;
        float a = 1;//(float) IKSGColorUtil.getAlpha(color) / 255f;

        float hight = (float) (h * parsent);

        IKSGRenderUtil.renderSpritePanel(sprite, poseStack, mbs, x, y + hight, z + w, r, g, b, a, -90, 0, 0, w, h, sprite.getU(16d * x), sprite.getV(16d * y), sprite.getU(16d * x + 16d * w), sprite.getV(16d * y + 16d * h), combinedOverlayIn, combinedLightIn);

        IKSGRenderUtil.renderSpritePanel(sprite, poseStack, mbs, x + w, y, z, r, g, b, a, 0, 180, 0, w, hight, sprite.getU(16d * x), sprite.getV(16d * y), sprite.getU(16d * x + 16d * w), sprite.getV(16d * y + 16d * hight), combinedOverlayIn, combinedLightIn);

        IKSGRenderUtil.renderSpritePanel(sprite, poseStack, mbs, x + w, y, z + h, r, g, b, a, 0, 90, 0, w, hight, sprite.getU(16d * x), sprite.getV(16d * y), sprite.getU(16d * x + 16d * w), sprite.getV(16d * y + 16d * hight), combinedOverlayIn, combinedLightIn);

        IKSGRenderUtil.renderSpritePanel(sprite, poseStack, mbs, x, y, z + h, r, g, b, a, 0, 0, 0, w, hight, sprite.getU(16d * x), sprite.getV(16d * y), sprite.getU(16d * x + 16d * w), sprite.getV(16d * y + 16d * hight), combinedOverlayIn, combinedLightIn);

        IKSGRenderUtil.renderSpritePanel(sprite, poseStack, mbs, x, y, z, r, g, b, a, 0, 270, 0, w, hight, sprite.getU(16d * x), sprite.getV(16d * y), sprite.getU(16d * x + 16d * w), sprite.getV(16d * y + 16d * hight), combinedOverlayIn, combinedLightIn);

        IKSGRenderUtil.renderSpritePanel(sprite, poseStack, mbs, x, y, z, r, g, b, a, -270, 0, 0, w, h, sprite.getU(16d * x), sprite.getV(16d * y), sprite.getU(16d * x + 16d * w), sprite.getV(16d * y + 16d * h), combinedOverlayIn, combinedLightIn);
    }
}
