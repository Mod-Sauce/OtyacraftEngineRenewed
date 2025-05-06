package dev.felnull.otyacraftengine.client.util;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Axis;
import dev.felnull.otyacraftengine.client.ClientMixinTemp;
import dev.felnull.otyacraftengine.client.renderer.OERenderTypes;
import dev.felnull.otyacraftengine.explatform.client.OEClientExpectPlatform;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;

import java.util.Objects;
import java.util.UUID;
import java.util.function.Consumer;

/**
 * 描画関係のユーティリティ
 *
 * @author MORIMORI0317
 */
public final class OERenderUtils {
    private static final Minecraft mc = Minecraft.getInstance();
    public static final float MIN_BREADTH = 1.0E-3F;

    /**
     * PoseStackを16分の１単位で移動する
     *
     * @param poseStack PoseStack
     * @param x         X
     * @param y         Y
     * @param z         Z
     */
    public static void poseTrans16(@NotNull PoseStack poseStack, double x, double y, double z) {
        float pix = 1f / 16f;
        poseStack.translate(pix * x, pix * y, pix * z);
    }

    /**
     * PoseStackをすべてのスケールを設定する
     *
     * @param poseStack PoseStack
     * @param scale     すべてのスケール
     */
    public static void poseScaleAll(@NotNull PoseStack poseStack, float scale) {
        poseStack.scale(scale, scale, scale);
    }

    /**
     * PoseStackの角度をそれぞれ設定する
     *
     * @param poseStack PoseStack
     * @param x         X角度
     * @param y         Y角度
     * @param z         Z角度
     */
    public static void poseRotateAll(@NotNull PoseStack poseStack, float x, float y, float z) {
        poseRotateX(poseStack, x);
        poseRotateY(poseStack, y);
        poseRotateZ(poseStack, z);
    }

    /**
     * PoseStackのX角度を設定する
     *
     * @param poseStack PoseStack
     * @param angle     角度
     */
    public static void poseRotateX(@NotNull PoseStack poseStack, float angle) {
        poseStack.mulPose(Axis.XP.rotationDegrees(angle));
    }

    /**
     * PoseStackのY角度を設定する
     *
     * @param poseStack PoseStack
     * @param angle     角度
     */
    public static void poseRotateY(@NotNull PoseStack poseStack, float angle) {
        poseStack.mulPose(Axis.YP.rotationDegrees(angle));
    }

    /**
     * PoseStackのZ角度を設定する
     *
     * @param poseStack PoseStack
     * @param angle     角度
     */
    public static void poseRotateZ(@NotNull PoseStack poseStack, float angle) {
        poseStack.mulPose(Axis.ZP.rotationDegrees(angle));
    }

    /**
     * PoseStackの方向のブロックステートに設定する
     *
     * @param poseStack PoseStack
     * @param state     角度
     * @param roted     回転ずれ
     */
    public static void poseRotateHorizontalState(@NotNull PoseStack poseStack, @NotNull BlockState state, int roted) {
        Direction direction = state.getValue(BlockStateProperties.HORIZONTAL_FACING);
        poseRotateDirection(poseStack, direction, roted);
    }

    /**
     * PoseStackをDirectionの方向にする
     *
     * @param poseStack PoseStack
     * @param direction 方向
     * @param roted     回転ずれ
     */
    public static void poseRotateDirection(@NotNull PoseStack poseStack, @NotNull Direction direction, int roted) {
        for (int i = 0; i < roted; i++) {
            direction = direction.getClockWise();
        }
        if (direction == Direction.WEST) {
            poseRotateY(poseStack, 180);
            poseStack.translate(-1f, 0f, -1f);
        } else if (direction == Direction.NORTH) {
            poseRotateY(poseStack, 90);
            poseStack.translate(-1f, 0f, 0f);
        } else if (direction == Direction.SOUTH) {
            poseRotateY(poseStack, 270);
            poseStack.translate(0f, 0f, -1f);
        }
    }

    /**
     * 中心でposeを変更する
     *
     * @param poseStack         PoseStack
     * @param centerX           中心X
     * @param centerY           中心Y
     * @param centerZ           中心Z
     * @param poseStackConsumer 中心での処理
     */
    public static void poseCenterConsumer(@NotNull PoseStack poseStack, float centerX, float centerY, float centerZ, @NotNull Consumer<PoseStack> poseStackConsumer) {
        poseStack.translate(centerX, centerY, centerZ);
        poseStackConsumer.accept(poseStack);
        poseStack.translate(-centerX, -centerY, -centerZ);
    }

    /**
     * GUI上でテクスチャを描画する
     *
     * @param location      テクスチャ
     * @param poseStack     PoseStack
     * @param x             X
     * @param y             Y
     * @param u0            テクスチャの開始地点X
     * @param v0            テクスチャの開始地点Y
     * @param ru1           相対テクスチャの終了地点X
     * @param rv1           相対テクスチャの終了地点Y
     * @param textureWidth  テクスチャの横サイズ
     * @param textureHeight テクスチャの縦サイズ
     */
    public static void drawTexture(@NotNull ResourceLocation location, @NotNull PoseStack poseStack, float x, float y, float u0, float v0, float ru1, float rv1, float textureWidth, float textureHeight) {
        setPreDraw(location);
        blitFloat(poseStack, x, y, u0, v0, ru1, rv1, textureWidth, textureHeight);
    }

    /**
     * GUI上でテクスチャを描画する
     * テクスチャサイズは256x256
     *
     * @param location  テクスチャ
     * @param poseStack PoseStack
     * @param x         X
     * @param y         Y
     * @param ru0       相対テクスチャの開始地点X
     * @param rv0       相対テクスチャの開始地点Y
     * @param ru1       テクスチャの終了地点X
     * @param rv1       テクスチャの終了地点Y
     */
    public static void drawTexture(@NotNull ResourceLocation location, @NotNull PoseStack poseStack, float x, float y, float ru0, float rv0, float ru1, float rv1) {
        drawTexture(location, poseStack, x, y, ru0, rv0, ru1, rv1, 256, 256);
    }

    /**
     * テクスチャ描画前に呼び出し
     *
     * @param location テクスチャ
     */
    public static void setPreDraw(@NotNull ResourceLocation location) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, location);
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
    }

    /**
     * 半透明で描画のための呼び出し
     *
     * @param location テクスチャ
     * @param draw     描画処理
     */
    public static void setAndDrawAlpha(@NotNull ResourceLocation location, Runnable draw) {
        setPreDraw(location);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        draw.run();
        RenderSystem.disableBlend();
    }

    /**
     * GUI上で半透明テクスチャを描画する
     *
     * @param location      テクスチャ
     * @param poseStack     PoseStack
     * @param x             X
     * @param y             Y
     * @param u0            テクスチャの開始地点X
     * @param v0            テクスチャの開始地点Y
     * @param ru1           相対テクスチャの終了地点X
     * @param rv1           相対テクスチャの終了地点Y
     * @param textureWidth  テクスチャの横サイズ
     * @param textureHeight テクスチャの縦サイズ
     */
    public static void drawTextureAlpha(@NotNull ResourceLocation location, @NotNull PoseStack poseStack, float x, float y, float u0, float v0, float ru1, float rv1, float textureWidth, float textureHeight) {
        setAndDrawAlpha(location, () -> blitFloat(poseStack, x, y, u0, v0, ru1, rv1, textureWidth, textureHeight));
    }

    /**
     * GUI上で半透明テクスチャを描画する
     * テクスチャサイズは256x256
     *
     * @param location  テクスチャ
     * @param poseStack PoseStack
     * @param x         X
     * @param y         Y
     * @param u0        相対テクスチャの開始地点X
     * @param v0        相対テクスチャの開始地点Y
     * @param ru1       テクスチャの終了地点X
     * @param rv1       テクスチャの終了地点Y
     */
    public static void drawTextureAlpha(@NotNull ResourceLocation location, @NotNull PoseStack poseStack, float x, float y, float u0, float v0, float ru1, float rv1) {
        drawTextureAlpha(location, poseStack, x, y, u0, v0, ru1, rv1, 256, 256);
    }

    /**
     * 指定済みテクスチャとシェーダーで描画
     *
     * @param poseStack     PoseStack
     * @param x             X
     * @param y             Y
     * @param u0            テクスチャの開始地点X
     * @param v0            テクスチャの開始地点Y
     * @param rvu1          相対テクスチャの終了地点X
     * @param rvv1          相対テクスチャの終了地点Y
     * @param textureWidth  テクスチャの横サイズ
     * @param textureHeight テクスチャの縦サイズ
     */
    public static void blitFloat(@NotNull PoseStack poseStack, float x, float y, float u0, float v0, float rvu1, float rvv1, float textureWidth, float textureHeight) {
        Matrix4f matrix4f = poseStack.last().pose();
        float ry = x + rvu1;
        float rh = y + rvv1;
        float ru0 = u0 / textureWidth;
        float ru1 = (u0 + rvu1) / textureWidth;
        float rv0 = v0 / textureHeight;
        float rv1 = (v0 + rvv1) / textureHeight;

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        BufferBuilder bufferBuilder = Tesselator.getInstance().getBuilder();
        bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        bufferBuilder.vertex(matrix4f, x, rh, 0).uv(ru0, rv1).endVertex();
        bufferBuilder.vertex(matrix4f, ry, rh, 0).uv(ru1, rv1).endVertex();
        bufferBuilder.vertex(matrix4f, ry, y, 0).uv(ru1, rv0).endVertex();
        bufferBuilder.vertex(matrix4f, x, y, 0).uv(ru0, rv0).endVertex();
        BufferUploader.drawWithShader(bufferBuilder.end());
    }

    /**
     * 指定済みテクスチャとシェーダーで描画
     * テクスチャサイズは256x256
     *
     * @param poseStack PoseStack
     * @param x         X
     * @param y         Y
     * @param ru0       テクスチャの開始地点X
     * @param rv0       テクスチャの開始地点Y
     * @param u1        相対テクスチャの終了地点X
     * @param v1        相対テクスチャの終了地点Y
     */
    public static void blitFloat(@NotNull PoseStack poseStack, float x, float y, float ru0, float rv0, float u1, float v1) {
        blitFloat(poseStack, x, y, ru0, rv0, u1, v1, 256, 256);
    }

    /**
     * GUI上を塗りつぶす
     * intの場合はGuiComponent.fillを推奨
     *
     * @param poseStack PoseStack
     * @param x         X
     * @param y         Y
     * @param width     幅
     * @param height    高さ
     * @param color     塗りつぶし色
     */
    public static void drawFill(@NotNull PoseStack poseStack, float x, float y, float width, float height, int color) {
        innerFill(poseStack.last().pose(), x, y, width, height, color);
    }

    private static void innerFill(Matrix4f matrix4f, float x, float y, float w, float h, int color) {
        float o;
        if (x < w) {
            o = x;
            x = w;
            w = o;
        }

        if (y < h) {
            o = y;
            y = h;
            h = o;
        }

        float f = (float) FastColor.ARGB32.alpha(color) / 255.0F;
        float g = (float) FastColor.ARGB32.red(color) / 255.0F;
        float h2 = (float) FastColor.ARGB32.green(color) / 255.0F;
        float p = (float) FastColor.ARGB32.blue(color) / 255.0F;
        BufferBuilder bufferBuilder = Tesselator.getInstance().getBuilder();
        RenderSystem.enableBlend();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
        bufferBuilder.vertex(matrix4f, x, y, 0).color(g, h2, p, f).endVertex();
        bufferBuilder.vertex(matrix4f, x, h, 0).color(g, h2, p, f).endVertex();
        bufferBuilder.vertex(matrix4f, w, h, 0).color(g, h2, p, f).endVertex();
        bufferBuilder.vertex(matrix4f, w, y, 0).color(g, h2, p, f).endVertex();
        BufferUploader.drawWithShader(bufferBuilder.end());
        RenderSystem.disableBlend();
    }

    /**
     * モデルを描画する
     *
     * @param poseStack       PoseStack
     * @param vertexConsumer  VertexConsumer
     * @param bakedModel      BakedModel
     * @param combinedLight   CombinedLight
     * @param combinedOverlay CombinedOverlay
     */
    public static void renderModel(PoseStack poseStack, VertexConsumer vertexConsumer, @NotNull BakedModel bakedModel, int combinedLight, int combinedOverlay) {
        Objects.requireNonNull(bakedModel);
        var bmr = mc.getBlockRenderer().getModelRenderer();
        bmr.renderModel(poseStack.last(), vertexConsumer, null, bakedModel, 1.0F, 1.0F, 1.0F, combinedLight, combinedOverlay);
    }

    /**
     * モデルを描画する
     *
     * @param poseStack       PoseStack
     * @param vertexConsumer  VertexConsumer
     * @param bakedModel      BakedModel
     * @param combinedLight   CombinedLight
     * @param combinedOverlay CombinedOverlay
     * @param color           色
     */
    public static void renderModel(PoseStack poseStack, VertexConsumer vertexConsumer, @NotNull BakedModel bakedModel, int combinedLight, int combinedOverlay, int color) {
        Objects.requireNonNull(bakedModel);
        var bmr = mc.getBlockRenderer().getModelRenderer();
        float r = (float) (color >> 16 & 255) / 255.0F;
        float g = (float) (color >> 8 & 255) / 255.0F;
        float b = (float) (color & 255) / 255.0F;
        bmr.renderModel(poseStack.last(), vertexConsumer, null, bakedModel, r, g, b, combinedLight, combinedOverlay);
    }

    @Deprecated
    public static void renderTextureSprite(ResourceLocation location, PoseStack poseStack, MultiBufferSource multiBufferSource, float x, float y, float z, float pitch, float yaw, float roll, float width, float height, float u0, float v0, float u1, float v1, float textureWidth, float textureHeight, int combinedLightIn, int combinedOverlayIn) {
        poseStack.pushPose();
        poseStack.translate(x, y, z);
        poseRotateY(poseStack, yaw);
        poseRotateX(poseStack, pitch);
        poseRotateZ(poseStack, roll);
        renderTextureSprite(location, poseStack, multiBufferSource, width, height, u0, v0, u1, v1, textureWidth, textureHeight, combinedLightIn, combinedOverlayIn);
        poseStack.popPose();
    }


    public static void renderTextureSprite(ResourceLocation location, PoseStack poseStack, MultiBufferSource multiBufferSource, float width, float height, float u0, float v0, float u1, float v1, float textureWidth, float textureHeight, int combinedLightIn, int combinedOverlayIn) {
        renderSprite(poseStack, multiBufferSource.getBuffer(OERenderTypes.simpleSpriteCutout(location)), width, height, u0, v0, u1, v1, textureWidth, textureHeight, combinedLightIn, combinedOverlayIn);
    }

    public static void renderColorfulTextureSprite(ResourceLocation location, PoseStack poseStack, MultiBufferSource multiBufferSource, float width, float height, float u0, float v0, float u1, float v1, float textureWidth, float textureHeight, int color, int combinedLightIn, int combinedOverlayIn) {
        renderColorfulSprite(poseStack, multiBufferSource.getBuffer(OERenderTypes.simpleSpriteCutout(location)), width, height, u0, v0, u1, v1, textureWidth, textureHeight, color, combinedLightIn, combinedOverlayIn);
    }

    public static void renderSprite(PoseStack poseStack, VertexConsumer vertexConsumer, float width, float height, float u0, float v0, float u1, float v1, float textureWidth, float textureHeight, int combinedLightIn, int combinedOverlayIn) {
        renderColorfulSprite(poseStack, vertexConsumer, width, height, u0, v0, u1, v1, textureWidth, textureHeight, 0xFFFFFFFF, combinedLightIn, combinedOverlayIn);
    }

    public static void renderColorfulSprite(PoseStack poseStack, VertexConsumer vertexConsumer, float width, float height, float u0, float v0, float u1, float v1, float textureWidth, float textureHeight, int color, int combinedLightIn, int combinedOverlayIn) {
        float wst = u0 / textureWidth;
        float wft = u1 / textureWidth + wst;
        float hst = v0 / textureHeight;
        float hft = v1 / textureHeight + hst;

        float a = (float) (color >> 24 & 255) / 255.0F;
        float r = (float) (color >> 16 & 255) / 255.0F;
        float g = (float) (color >> 8 & 255) / 255.0F;
        float b = (float) (color & 255) / 255.0F;

        PoseStack.Pose pose = poseStack.last();
        vertex(vertexConsumer, pose, 0, 0, 0, wst, hft, r, g, b, a, combinedOverlayIn, combinedLightIn);
        vertex(vertexConsumer, pose, width, 0, 0, wft, hft, r, g, b, a, combinedOverlayIn, combinedLightIn);
        vertex(vertexConsumer, pose, width, height, 0, wft, hst, r, g, b, a, combinedOverlayIn, combinedLightIn);
        vertex(vertexConsumer, pose, 0, height, 0, wst, hst, r, g, b, a, combinedOverlayIn, combinedLightIn);
    }

    private static void vertex(VertexConsumer builder, PoseStack.Pose pose, float x, float y, float z, float u, float v, float r, float g, float b, float a, int combinedOverlayIn, int combinedLightIn) {
        builder.vertex(pose.pose(), x, y, z).color(r, g, b, a).uv(u, v).overlayCoords(combinedOverlayIn).uv2(combinedLightIn).normal(pose.normal(), 0f, 0f, 0f).endVertex();
    }

    public static void posePlayerArm(PoseStack poseStack, HumanoidArm arm, float swingProgress, float equipProgress) {
        boolean bl = arm != HumanoidArm.LEFT;
        float h = bl ? 1.0F : -1.0F;
        float j = Mth.sqrt(swingProgress);
        float k = -0.3F * Mth.sin(j * Mth.PI);
        float l = 0.4F * Mth.sin(j * Mth.TWO_PI);
        float m = -0.4F * Mth.sin(swingProgress * Mth.PI);

        poseStack.translate(h * (k + 0.64000005F), l + -0.6F + equipProgress * -0.6F, m + -0.71999997F);
        poseStack.mulPose(Axis.YP.rotationDegrees(h * 45.0F));
        float n = Mth.sin(swingProgress * swingProgress * Mth.PI);
        float o = Mth.sin(j * Mth.PI);
        poseStack.mulPose(Axis.YP.rotationDegrees(h * o * 70.0F));
        poseStack.mulPose(Axis.ZP.rotationDegrees(h * n * -20.0F));
        poseStack.translate(h * -1.0F, 3.5999999046325684D, 3.5D);
        poseStack.mulPose(Axis.ZP.rotationDegrees(h * 120.0F));
        poseStack.mulPose(Axis.XP.rotationDegrees(200.0F));
        poseStack.mulPose(Axis.YP.rotationDegrees(h * -135.0F));
        poseStack.translate(h * 5.6F, 0.0D, 0.0D);
    }

    public static void renderPlayerArm(PoseStack poseStack, MultiBufferSource multiBufferSource, HumanoidArm arm, int light) {
        if (mc.player.isInvisible()) return;
        boolean bl = arm != HumanoidArm.LEFT;
        var pr = (PlayerRenderer) mc.getEntityRenderDispatcher().getRenderer(mc.player);
        //fix me
        RenderSystem.setShaderTexture(0, mc.player.getSkinTextureLocation());
        if (bl) {
            pr.renderRightHand(poseStack, multiBufferSource, light, mc.player);
        } else {
            pr.renderLeftHand(poseStack, multiBufferSource, light, mc.player);
        }
    }

    public static void poseHandItem(PoseStack poseStack, HumanoidArm arm, float swingProgress, float equipProgress) {
        boolean handFlg = arm == HumanoidArm.RIGHT;
        float s = -0.4F * Mth.sin(Mth.sqrt(swingProgress) * Mth.PI);
        float r = 0.2F * Mth.sin(Mth.sqrt(swingProgress) * Mth.TWO_PI);
        float l = -0.2F * Mth.sin(swingProgress * Mth.PI);
        int t = handFlg ? 1 : -1;
        poseStack.translate((float) t * s, r, l);
        poseStack.translate((float) t * 0.56F, -0.52F + equipProgress * -0.6F, -0.7200000286102295D);
        float g = Mth.sin(swingProgress * swingProgress * Mth.PI);
        poseRotateY(poseStack, (float) t * (45.0F + g * -20.0F));
        float h = Mth.sin(Mth.sqrt(swingProgress) * Mth.PI);
        poseRotateZ(poseStack, (float) t * h * -20.0F);
        poseRotateX(poseStack, h * -80.0F);
        poseRotateY(poseStack, (float) t * -45.0F);
    }

    public static void renderHandItem(PoseStack poseStack, MultiBufferSource multiBufferSource, HumanoidArm arm, ItemStack stack, int light) {
        boolean handFlg = arm == HumanoidArm.RIGHT;
        mc.gameRenderer.itemInHandRenderer.renderItem(mc.player, stack, handFlg ? ItemDisplayContext.FIRST_PERSON_RIGHT_HAND : ItemDisplayContext.FIRST_PERSON_LEFT_HAND, !handFlg, poseStack, multiBufferSource, light);
    }

    /**
     * 中央ぞろえで文字描画
     *
     * @param guiGraphics guiGraphics
     * @param text        文字
     * @param x           中央X
     * @param y           Y
     * @param color       色(ARGB)
     * @since 2.0
     */
    public static void drawCenterFont(GuiGraphics guiGraphics, Component text, float x, float y, int color) {
        //  mc.font.draw(poseStack, text, x - ((float) mc.font.width(text) / 2f), y, color);
        guiGraphics.drawString(mc.font, text, (int) (x - ((float) mc.font.width(text) / 2f)), (int) y, color, false);
    }

    /**
     * 中央ぞろえ文字描画
     *
     * @param guiGraphics GuiGraphics
     * @param str         文字
     * @param x           中央X
     * @param y           Y
     * @param color       色(ARGB)
     * @since 2.0
     */
    public static void drawCenterFont(GuiGraphics guiGraphics, String str, float x, float y, int color) {
        //mc.font.draw(poseStack, str, x - ((float) mc.font.width(str) / 2f), y, color);
        guiGraphics.drawString(mc.font, str, (int) (x - ((float) mc.font.width(str) / 2f)), (int) y, color, false);
    }

    /**
     * 文字のスプライトを描画する
     *
     * @param text              テキスト
     * @param x                 X
     * @param y                 Y
     * @param color             色
     * @param shadow            影をつけるかどうか
     * @param lastPose          pose
     * @param multiBufferSource multiBufferSource
     * @param displayMode       表示モード
     * @param bakedGlyphColor   背景色
     * @param packedLightCoords light
     * @return size?
     */
    public static int fontDrawInBatch(Component text, float x, float y, int color, boolean shadow, Matrix4f lastPose, MultiBufferSource multiBufferSource, Font.DisplayMode displayMode, int bakedGlyphColor, int packedLightCoords) {
        return mc.font.drawInBatch(text, x, y, color, shadow, lastPose, multiBufferSource, displayMode, bakedGlyphColor, packedLightCoords);
    }

    /**
     * 文字のスプライトを描画する
     *
     * @param text              テキスト
     * @param x                 X
     * @param y                 Y
     * @param color             色
     * @param shadow            影をつけるかどうか
     * @param lastPose          pose
     * @param multiBufferSource multiBufferSource
     * @param displayMode       表示モード
     * @param bakedGlyphColor   背景色
     * @param packedLightCoords light
     * @return size?
     */
    public static int fontDrawInBatch(String text, float x, float y, int color, boolean shadow, Matrix4f lastPose, MultiBufferSource multiBufferSource, Font.DisplayMode displayMode, int bakedGlyphColor, int packedLightCoords) {
        return mc.font.drawInBatch(text, x, y, color, shadow, lastPose, multiBufferSource, displayMode, bakedGlyphColor, packedLightCoords);
    }

    @Deprecated
    public static void renderTextSprite(PoseStack poseStack, MultiBufferSource multiBufferSource, Component text, float x, float y, float z, float size, float textX, float textY, int color, int combinedLightIn) {
        poseStack.pushPose();
        poseStack.translate(x, y, z);
        poseStack.scale(0.010416667F * size, -0.010416667F * size, 0.010416667F * size);
        mc.font.drawInBatch(text, textX, -mc.font.lineHeight + textY, color, false, poseStack.last().pose(), multiBufferSource, Font.DisplayMode.NORMAL, 0, combinedLightIn);
        poseStack.popPose();
    }

    @Deprecated
    public static void renderTextSprite(PoseStack poseStack, MultiBufferSource multiBufferSource, Component text, float x, float y, float z, float size, float textX, float textY, int combinedLightIn) {
        poseStack.pushPose();
        poseStack.translate(x, y, z);
        poseStack.scale(0.010416667F * size, -0.010416667F * size, 0.010416667F * size);
        mc.font.drawInBatch(text, textX, -mc.font.lineHeight + textY, 0, false, poseStack.last().pose(), multiBufferSource, Font.DisplayMode.NORMAL, 0, combinedLightIn);
        poseStack.popPose();
    }

    @Deprecated
    public static void renderCenterTextSprite(PoseStack poseStack, MultiBufferSource multiBufferSource, Component text, float x, float y, float z, float size, float textX, float textY, int color, int combinedLightIn) {
        poseStack.pushPose();
        poseStack.translate(x, y, z);
        poseStack.scale(0.010416667F * size, -0.010416667F * size, 0.010416667F * size);
        mc.font.drawInBatch(text, ((float) -mc.font.width(text) / 2f) + textX, -mc.font.lineHeight + textY, color, false, poseStack.last().pose(), multiBufferSource, Font.DisplayMode.NORMAL, 0, combinedLightIn);
        poseStack.popPose();
    }

    @Deprecated
    public static void renderCenterTextSprite(PoseStack poseStack, MultiBufferSource multiBufferSource, Component text, float x, float y, float z, float size, float textX, float textY, int combinedLightIn) {
        poseStack.pushPose();
        poseStack.translate(x, y, z);
        poseStack.scale(0.010416667F * size, -0.010416667F * size, 0.010416667F * size);
        mc.font.drawInBatch(text, ((float) -mc.font.width(text) / 2f) + textX, -mc.font.lineHeight + textY, 0, false, poseStack.last().pose(), multiBufferSource, Font.DisplayMode.NORMAL, 0, combinedLightIn);
        poseStack.popPose();
    }

    /**
     * 文字のスプライトを描画する
     *
     * @param text              テキスト
     * @param x                 X
     * @param y                 Y
     * @param color             色
     * @param shadow            影をつけるかどうか
     * @param poseStack         pose
     * @param multiBufferSource multiBufferSource
     * @param displayMode       表示モード
     * @param bakedGlyphColor   背景色
     * @param packedLightCoords light
     */
    public static void renderFontSprite(Component text, int x, int y, int color, boolean shadow, PoseStack poseStack, MultiBufferSource multiBufferSource, Font.DisplayMode displayMode, int bakedGlyphColor, int packedLightCoords) {
        poseStack.pushPose();
        poseStack.scale(-0.025F, -0.025F, 0.025F);
        Matrix4f matrix4f = poseStack.last().pose();
        fontDrawInBatch(text, x, y, color, shadow, matrix4f, multiBufferSource, displayMode, bakedGlyphColor, packedLightCoords);
        poseStack.popPose();
    }

    /**
     * 文字のスプライトを描画する
     *
     * @param text              テキスト
     * @param x                 X
     * @param y                 Y
     * @param color             色
     * @param shadow            影をつけるかどうか
     * @param poseStack         pose
     * @param multiBufferSource multiBufferSource
     * @param displayMode       表示モード
     * @param bakedGlyphColor   背景色
     * @param packedLightCoords light
     */
    public static void renderFontSprite(String text, int x, int y, int color, boolean shadow, PoseStack poseStack, MultiBufferSource multiBufferSource, Font.DisplayMode displayMode, int bakedGlyphColor, int packedLightCoords) {
        poseStack.pushPose();
        poseStack.scale(-0.025F, -0.025F, 0.025F);
        Matrix4f matrix4f = poseStack.last().pose();
        fontDrawInBatch(text, x, y, color, shadow, matrix4f, multiBufferSource, displayMode, bakedGlyphColor, packedLightCoords);
        poseStack.popPose();
    }

    /**
     * 文字のスプライトを描画する
     *
     * @param text              テキスト
     * @param x                 X
     * @param y                 Y
     * @param color             色
     * @param shadow            影をつけるかどうか
     * @param poseStack         pose
     * @param multiBufferSource multiBufferSource
     * @param displayMode       表示モード
     * @param bakedGlyphColor   背景色
     * @param packedLightCoords light
     */
    public static void renderCenterFontSprite(Component text, int x, int y, int color, boolean shadow, PoseStack poseStack, MultiBufferSource multiBufferSource, Font.DisplayMode displayMode, int bakedGlyphColor, int packedLightCoords) {
        poseStack.pushPose();
        poseStack.scale(-0.025F, -0.025F, 0.025F);
        Matrix4f matrix4f = poseStack.last().pose();
        x += (float) (-mc.font.width(text) / 2);
        fontDrawInBatch(text, x, y, color, shadow, matrix4f, multiBufferSource, displayMode, bakedGlyphColor, packedLightCoords);
        poseStack.popPose();
    }


    /**
     * 文字のスプライトを描画する
     *
     * @param text              テキスト
     * @param x                 X
     * @param y                 Y
     * @param color             色
     * @param shadow            影をつけるかどうか
     * @param poseStack         pose
     * @param multiBufferSource multiBufferSource
     * @param displayMode       表示モード
     * @param bakedGlyphColor   背景色
     * @param packedLightCoords light
     */
    public static void renderCenterFontSprite(String text, int x, int y, int color, boolean shadow, PoseStack poseStack, MultiBufferSource multiBufferSource, Font.DisplayMode displayMode, int bakedGlyphColor, int packedLightCoords) {
        poseStack.pushPose();
        poseStack.scale(-0.025F, -0.025F, 0.025F);
        Matrix4f matrix4f = poseStack.last().pose();
        x += (float) (-mc.font.width(text) / 2);
        fontDrawInBatch(text, x, y, color, shadow, matrix4f, multiBufferSource, displayMode, bakedGlyphColor, packedLightCoords);
        poseStack.popPose();
    }

    /**
     * 幅を固定して文字を描画
     * 幅に入りきらないと縮小し描画される
     *
     * @param guiGraphics GuiGraphics
     * @param text        文字
     * @param x           中央X
     * @param y           Y
     * @param color       色(ARGB)
     * @param width       幅
     */
    public static void drawFixedWidthFont(GuiGraphics guiGraphics, Component text, float x, float y, int color, float width) {
        PoseStack poseStack = guiGraphics.pose();

        int size = mc.font.width(text);
        poseStack.pushPose();
        if (size > width) {
            float scale = width / size;
            x /= scale;
            y /= scale;
            poseScaleAll(poseStack, scale);
        }

        // mc.font.draw(poseStack, text, x, y, color);
        guiGraphics.drawString(mc.font, text, (int) x, (int) y, color,false);

        poseStack.popPose();
    }

    /**
     * 幅を固定して文字を描画
     * 幅に入りきらないと縮小し描画される
     *
     * @param guiGraphics GuiGraphics
     * @param text        文字
     * @param x           中央X
     * @param y           Y
     * @param color       色(ARGB)
     * @param width       幅
     */
    public static void drawFixedWidthFont(GuiGraphics guiGraphics, String text, float x, float y, int color, float width) {
        PoseStack poseStack = guiGraphics.pose();

        int size = mc.font.width(text);
        poseStack.pushPose();
        if (size > width) {
            float scale = width / size;
            x /= scale;
            y /= scale;
            poseScaleAll(poseStack, scale);
        }
        //mc.font.draw(poseStack, text, x, y, color);

        guiGraphics.drawString(mc.font, text, (int) x, (int) y, color,false);

        poseStack.popPose();
    }

    /**
     * GUI上でUUIDから取得したプレイヤーの顔を描画する
     *
     * @param poseStack PoseStack
     * @param uuid      プレイヤーUUID
     * @param x         X
     * @param y         Y
     * @since 2.0
     */
    public static void drawPlayerFace(PoseStack poseStack, UUID uuid, float x, float y) {
        drawPlayerFace(poseStack, uuid, x, y, 8);
    }

    /**
     * GUI上でUUIDから取得したプレイヤーの顔を描画する
     * サイズ変更可
     *
     * @param poseStack PoseStack
     * @param uuid      プレイヤーUUID
     * @param x         X
     * @param y         Y
     * @param size      サイズ
     * @since 2.0
     */
    public static void drawPlayerFace(PoseStack poseStack, UUID uuid, float x, float y, float size) {
        poseStack.pushPose();
        float sc = size / 8f;
        ResourceLocation plskin = OETextureUtils.getPlayerSkinTexture(uuid);
        drawTexture(plskin, poseStack, x, y, 8f * sc, 8f * sc, 8f * sc, 8f * sc, 64f * sc, 64f * sc);
        drawTexture(plskin, poseStack, x, y, 40f * sc, 8f * sc, 8f * sc, 8f * sc, 64f * sc, 64f * sc);
        poseStack.popPose();
    }

    /**
     * GUI上で名前から取得したプレイヤーの顔を描画する
     *
     * @param poseStack PoseStack
     * @param name      プレイヤー名
     * @param x         X
     * @param y         Y
     */
    public static void drawPlayerFace(PoseStack poseStack, String name, float x, float y) {
        drawPlayerFace(poseStack, name, x, y, 8);
    }

    /**
     * GUI上で名前から取得したプレイヤーの顔を描画する
     * サイズ変更可
     *
     * @param poseStack PoseStack
     * @param name      プレイヤー名
     * @param x         X
     * @param y         Y
     * @param size      サイズ
     * @since 2.0
     */
    public static void drawPlayerFace(PoseStack poseStack, String name, float x, float y, float size) {
        poseStack.pushPose();
        float sc = size / 8f;
        ResourceLocation plskin = OETextureUtils.getPlayerSkinTexture(name);
        drawTexture(plskin, poseStack, x, y, 8f * sc, 8f * sc, 8f * sc, 8f * sc, 64f * sc, 64f * sc);
        drawTexture(plskin, poseStack, x, y, 40f * sc, 8f * sc, 8f * sc, 8f * sc, 64f * sc, 64f * sc);
        poseStack.popPose();
    }

    /**
     * UUIDから取得したプレイヤーの顔スプライトを描画する
     *
     * @param poseStack         PoseStack
     * @param multiBufferSource MultiBufferSource
     * @param uuid              プレイヤーUUID
     * @param size              サイズ
     * @param combinedLightIn   CombinedLightIn
     * @param combinedOverlayIn CombinedOverlayIn
     */
    public static void renderPlayerFaceSprite(PoseStack poseStack, MultiBufferSource multiBufferSource, UUID uuid, float size, int combinedLightIn, int combinedOverlayIn) {
        renderPlayerFaceSprite(poseStack, multiBufferSource.getBuffer(OERenderTypes.simpleSpriteCutout(OETextureUtils.getPlayerSkinTexture(uuid))), size, combinedLightIn, combinedOverlayIn);
    }

    /**
     * UUIDから取得したプレイヤーの顔スプライトを描画する
     *
     * @param poseStack         PoseStack
     * @param multiBufferSource MultiBufferSource
     * @param name              プレイヤー名
     * @param size              サイズ
     * @param combinedLightIn   CombinedLightIn
     * @param combinedOverlayIn CombinedOverlayIn
     */
    public static void renderPlayerFaceSprite(PoseStack poseStack, MultiBufferSource multiBufferSource, String name, float size, int combinedLightIn, int combinedOverlayIn) {
        renderPlayerFaceSprite(poseStack, multiBufferSource.getBuffer(OERenderTypes.simpleSpriteCutout(OETextureUtils.getPlayerSkinTexture(name))), size, combinedLightIn, combinedOverlayIn);
    }

    /**
     * プレイヤーの顔スプライトを描画する
     *
     * @param poseStack         PoseStack
     * @param vertexConsumer    VertexConsumer
     * @param size              サイズ
     * @param combinedLightIn   CombinedLightIn
     * @param combinedOverlayIn CombinedOverlayIn
     */
    public static void renderPlayerFaceSprite(PoseStack poseStack, VertexConsumer vertexConsumer, float size, int combinedLightIn, int combinedOverlayIn) {
        poseStack.pushPose();
        float sc = size / 8f;
        renderSprite(poseStack, vertexConsumer, size, size, 8f * sc, 8f * sc, 8f * sc, 8f * sc, 64f * sc, 64f * sc, combinedLightIn, combinedOverlayIn);

        poseStack.pushPose();
        poseStack.translate(0, 0, Mth.EPSILON);
        renderSprite(poseStack, vertexConsumer, size, size, 40f * sc, 8f * sc, 8f * sc, 8f * sc, 64f * sc, 64f * sc, combinedLightIn, combinedOverlayIn);
        poseStack.popPose();

        poseStack.popPose();
    }

    public static float getPartialTicks() {
        return OEClientExpectPlatform.getPartialTicks();
    }

    public static void noTransAndRotModelPart(Runnable runnable) {
        ClientMixinTemp.SKIP_TRANSANDROT_MODELPART.set(true);
        runnable.run();
        ClientMixinTemp.SKIP_TRANSANDROT_MODELPART.set(false);
    }

    public static void renderPlayerArmNoTransAndRot(PoseStack poseStack, MultiBufferSource multiBufferSource, HumanoidArm arm, int light) {
        noTransAndRotModelPart(() -> renderPlayerArm(poseStack, multiBufferSource, arm, light));
    }
}
