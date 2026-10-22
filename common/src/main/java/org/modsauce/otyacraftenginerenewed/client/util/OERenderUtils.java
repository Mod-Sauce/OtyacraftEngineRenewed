package org.modsauce.otyacraftenginerenewed.client.util;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.QuadInstance;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import java.util.UUID;
import java.util.function.Consumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.player.AvatarRenderer;
import net.minecraft.client.resources.model.geometry.BakedQuad;
import net.minecraft.client.renderer.block.dispatch.BlockStateModelPart;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;
import org.modsauce.otyacraftenginerenewed.client.ClientMixinTemp;
import org.modsauce.otyacraftenginerenewed.client.renderer.OERenderTypes;
import org.modsauce.otyacraftenginerenewed.explatform.client.OEClientExpectPlatform;

/**
 * Rendering utilities
 *
 * @author MORIMORI0317
 */
public final class OERenderUtils {

  private static final Minecraft mc = Minecraft.getInstance();
  public static final float MIN_BREADTH = 1.0E-3F;

  private OERenderUtils() {}

  // ---------------------------------------------------------------- PoseStack

  /** Translates the PoseStack in units of 1/16 */
  public static void poseTrans16(@NotNull PoseStack poseStack, double x, double y, double z) {
    float pix = 1f / 16f;
    poseStack.translate(pix * x, pix * y, pix * z);
  }

  /** Sets the same scale on all axes of the PoseStack */
  public static void poseScaleAll(@NotNull PoseStack poseStack, float scale) {
    poseStack.scale(scale, scale, scale);
  }

  /** Sets the rotation of each axis of the PoseStack */
  public static void poseRotateAll(@NotNull PoseStack poseStack, float x, float y, float z) {
    poseRotateX(poseStack, x);
    poseRotateY(poseStack, y);
    poseRotateZ(poseStack, z);
  }

  public static void poseRotateX(@NotNull PoseStack poseStack, float angle) {
    poseStack.mulPose(Axis.XP.rotationDegrees(angle));
  }

  public static void poseRotateY(@NotNull PoseStack poseStack, float angle) {
    poseStack.mulPose(Axis.YP.rotationDegrees(angle));
  }

  public static void poseRotateZ(@NotNull PoseStack poseStack, float angle) {
    poseStack.mulPose(Axis.ZP.rotationDegrees(angle));
  }

  /** Orients the PoseStack to the facing of the given block state */
  public static void poseRotateHorizontalState(
    @NotNull PoseStack poseStack,
    @NotNull BlockState state,
    int roted
  ) {
    Direction direction = state.getValue(BlockStateProperties.HORIZONTAL_FACING);
    poseRotateDirection(poseStack, direction, roted);
  }

  /** Orients the PoseStack to the given Direction */
  public static void poseRotateDirection(
    @NotNull PoseStack poseStack,
    @NotNull Direction direction,
    int roted
  ) {
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

  /** Modifies the pose around a center point */
  public static void poseCenterConsumer(
    @NotNull PoseStack poseStack,
    float centerX,
    float centerY,
    float centerZ,
    @NotNull Consumer<PoseStack> poseStackConsumer
  ) {
    poseStack.translate(centerX, centerY, centerZ);
    poseStackConsumer.accept(poseStack);
    poseStack.translate(-centerX, -centerY, -centerZ);
  }

  // ---------------------------------------------------------------- GUI drawing
  // The old RenderSystem.setShader / Tesselator / BufferUploader path no longer
  // exists, so GUI drawing now goes through GuiGraphicsExtractor + RenderPipelines.
  // These methods take a GuiGraphicsExtractor instead of a PoseStack, and
  // coordinates are ints (float-precision blits are not supported anymore).

  /**
   * Draws a texture in the GUI
   *
   * @param u0 texture start X
   * @param v0 texture start Y
   * @param w  draw width (same as the width on the texture)
   * @param h  draw height (same as the height on the texture)
   */
  public static void drawTexture(
    @NotNull GuiGraphicsExtractor g,
    @NotNull Identifier location,
    int x,
    int y,
    float u0,
    float v0,
    int w,
    int h,
    int textureWidth,
    int textureHeight
  ) {
    g.blit(RenderPipelines.GUI_TEXTURED, location, x, y, u0, v0, w, h, textureWidth, textureHeight);
  }

  /** Draws a texture in the GUI (texture size is 256x256) */
  public static void drawTexture(
    @NotNull GuiGraphicsExtractor g,
    @NotNull Identifier location,
    int x,
    int y,
    float u0,
    float v0,
    int w,
    int h
  ) {
    drawTexture(g, location, x, y, u0, v0, w, h, 256, 256);
  }

  /**
   * Draws a translucent texture in the GUI.
   * The GUI_TEXTURED pipeline already has blending enabled, so this is identical to drawTexture.
   */
  public static void drawTextureAlpha(
    @NotNull GuiGraphicsExtractor g,
    @NotNull Identifier location,
    int x,
    int y,
    float u0,
    float v0,
    int w,
    int h,
    int textureWidth,
    int textureHeight
  ) {
    drawTexture(g, location, x, y, u0, v0, w, h, textureWidth, textureHeight);
  }

  /** Draws a translucent texture in the GUI (texture size is 256x256) */
  public static void drawTextureAlpha(
    @NotNull GuiGraphicsExtractor g,
    @NotNull Identifier location,
    int x,
    int y,
    float u0,
    float v0,
    int w,
    int h
  ) {
    drawTexture(g, location, x, y, u0, v0, w, h, 256, 256);
  }

  /** Fills an area in the GUI */
  public static void drawFill(
    @NotNull GuiGraphicsExtractor poseStack,
    int x,
    int y,
    int width,
    int height,
    int color
  ) {
    poseStack.fill(x, y, x + width, y + height, color);
  }

  // ---------------------------------------------------------------- Models

  /**
   * Renders a model part
   */
  public static void renderModel(
    PoseStack poseStack,
    VertexConsumer vertexConsumer,
    @NotNull BlockStateModelPart part,
    int combinedLight,
    int combinedOverlay
  ) {
    renderModel(poseStack, vertexConsumer, part, combinedLight, combinedOverlay, 0xFFFFFFFF);
  }

  /** Renders a model part with a color */
  public static void renderModel(
    PoseStack poseStack,
    VertexConsumer vertexConsumer,
    @NotNull BlockStateModelPart part,
    int combinedLight,
    int combinedOverlay,
    int color
  ) {
    PoseStack.Pose pose = poseStack.last();
    for (BakedQuad quad : part.getQuads(null)) {
      putQuad(vertexConsumer, pose, quad, color, combinedLight, combinedOverlay);
    }
    for (Direction dir : Direction.values()) {
      for (BakedQuad quad : part.getQuads(dir)) {
        putQuad(vertexConsumer, pose, quad, color, combinedLight, combinedOverlay);
      }
    }
  }


  private static void putQuad(
    VertexConsumer vertexConsumer,
    PoseStack.Pose pose,
    BakedQuad quad,
    int color,
    int light,
    int overlay
  ) {
    QuadInstance instance = new QuadInstance();
    instance.setColor(color);
    instance.setLightCoords(light);
    instance.setOverlayCoords(overlay);
    vertexConsumer.putBakedQuad(pose, quad, instance);
  }


  // ---------------------------------------------------------------- World sprites

  @Deprecated
  public static void renderTextureSprite(
    Identifier location,
    PoseStack poseStack,
    MultiBufferSource multiBufferSource,
    float x,
    float y,
    float z,
    float pitch,
    float yaw,
    float roll,
    float width,
    float height,
    float u0,
    float v0,
    float u1,
    float v1,
    float textureWidth,
    float textureHeight,
    int combinedLightIn,
    int combinedOverlayIn
  ) {
    poseStack.pushPose();
    poseStack.translate(x, y, z);
    poseRotateY(poseStack, yaw);
    poseRotateX(poseStack, pitch);
    poseRotateZ(poseStack, roll);
    renderTextureSprite(
      location, poseStack, multiBufferSource, width, height,
      u0, v0, u1, v1, textureWidth, textureHeight, combinedLightIn, combinedOverlayIn
    );
    poseStack.popPose();
  }

  public static void renderTextureSprite(
    Identifier location,
    PoseStack poseStack,
    MultiBufferSource multiBufferSource,
    float width,
    float height,
    float u0,
    float v0,
    float u1,
    float v1,
    float textureWidth,
    float textureHeight,
    int combinedLightIn,
    int combinedOverlayIn
  ) {
    renderSprite(
      poseStack,
      multiBufferSource.getBuffer(OERenderTypes.simpleSpriteCutout(location)),
      width, height, u0, v0, u1, v1, textureWidth, textureHeight,
      combinedLightIn, combinedOverlayIn
    );
  }

  public static void renderColorfulTextureSprite(
    Identifier location,
    PoseStack poseStack,
    MultiBufferSource multiBufferSource,
    float width,
    float height,
    float u0,
    float v0,
    float u1,
    float v1,
    float textureWidth,
    float textureHeight,
    int color,
    int combinedLightIn,
    int combinedOverlayIn
  ) {
    renderColorfulSprite(
      poseStack,
      multiBufferSource.getBuffer(OERenderTypes.simpleSpriteCutout(location)),
      width, height, u0, v0, u1, v1, textureWidth, textureHeight, color,
      combinedLightIn, combinedOverlayIn
    );
  }

  public static void renderSprite(
    PoseStack poseStack,
    VertexConsumer vertexConsumer,
    float width,
    float height,
    float u0,
    float v0,
    float u1,
    float v1,
    float textureWidth,
    float textureHeight,
    int combinedLightIn,
    int combinedOverlayIn
  ) {
    renderColorfulSprite(
      poseStack, vertexConsumer, width, height, u0, v0, u1, v1,
      textureWidth, textureHeight, 0xFFFFFFFF, combinedLightIn, combinedOverlayIn
    );
  }

  public static void renderColorfulSprite(
    PoseStack poseStack,
    VertexConsumer vertexConsumer,
    float width,
    float height,
    float u0,
    float v0,
    float u1,
    float v1,
    float textureWidth,
    float textureHeight,
    int color,
    int combinedLightIn,
    int combinedOverlayIn
  ) {
    float wst = u0 / textureWidth;
    float wft = u1 / textureWidth + wst;
    float hst = v0 / textureHeight;
    float hft = v1 / textureHeight + hst;

    float a = (float) ((color >> 24) & 255) / 255.0F;
    float r = (float) ((color >> 16) & 255) / 255.0F;
    float g = (float) ((color >> 8) & 255) / 255.0F;
    float b = (float) (color & 255) / 255.0F;

    PoseStack.Pose pose = poseStack.last();
    vertex(vertexConsumer, pose, 0, 0, 0, wst, hft, r, g, b, a, combinedOverlayIn, combinedLightIn);
    vertex(vertexConsumer, pose, width, 0, 0, wft, hft, r, g, b, a, combinedOverlayIn, combinedLightIn);
    vertex(vertexConsumer, pose, width, height, 0, wft, hst, r, g, b, a, combinedOverlayIn, combinedLightIn);
    vertex(vertexConsumer, pose, 0, height, 0, wst, hst, r, g, b, a, combinedOverlayIn, combinedLightIn);
  }

  private static void vertex(
    VertexConsumer builder,
    PoseStack.Pose pose,
    float x,
    float y,
    float z,
    float u,
    float v,
    float r,
    float g,
    float b,
    float a,
    int combinedOverlayIn,
    int combinedLightIn
  ) {
    builder
      .addVertex(pose.pose(), x, y, z)
      .setColor(r, g, b, a)
      .setUv(u, v)
      .setOverlay(combinedOverlayIn)
      .setLight(combinedLightIn)
      .setNormal(pose, 0f, 0f, 0f);
  }

  // ---------------------------------------------------------------- First-person arm / item

  public static void posePlayerArm(
    PoseStack poseStack,
    HumanoidArm arm,
    float swingProgress,
    float equipProgress
  ) {
    boolean bl = arm != HumanoidArm.LEFT;
    float h = bl ? 1.0F : -1.0F;
    float j = Mth.sqrt(swingProgress);
    float k = -0.3F * Mth.sin(j * Mth.PI);
    float l = 0.4F * Mth.sin(j * Mth.TWO_PI);
    float m = -0.4F * Mth.sin(swingProgress * Mth.PI);

    poseStack.translate(
      h * (k + 0.64000005F),
      l + -0.6F + equipProgress * -0.6F,
      m + -0.71999997F
    );
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

  public static void renderPlayerArm(
    PoseStack poseStack,
    SubmitNodeCollector collector,
    HumanoidArm arm,
    int light
  ) {
    if (mc.player == null || mc.player.isInvisible()) return;
    boolean right = arm != HumanoidArm.LEFT;
    var pr = (AvatarRenderer<?>) mc.getEntityRenderDispatcher().getRenderer(mc.player);
    Identifier skin = mc.player.getSkin().body().texturePath();
    if (right) {
      pr.renderRightHand(poseStack, collector, light, skin, mc.player.isModelPartShown(net.minecraft.world.entity.player.PlayerModelPart.RIGHT_SLEEVE));
    } else {
      pr.renderLeftHand(poseStack, collector, light, skin, mc.player.isModelPartShown(net.minecraft.world.entity.player.PlayerModelPart.LEFT_SLEEVE));
    }
  }

  public static void poseHandItem(
    PoseStack poseStack,
    HumanoidArm arm,
    float swingProgress,
    float equipProgress
  ) {
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

  public static void renderHandItem(
    PoseStack poseStack,
    SubmitNodeCollector collector,
    HumanoidArm arm,
    ItemStack stack,
    int light
  ) {
    boolean handFlg = arm == HumanoidArm.RIGHT;
    mc.gameRenderer.itemInHandRenderer.renderItem(
      mc.player,
      stack,
      handFlg
        ? ItemDisplayContext.FIRST_PERSON_RIGHT_HAND
        : ItemDisplayContext.FIRST_PERSON_LEFT_HAND,
      poseStack,
      collector,
      light
    );
  }

  // ---------------------------------------------------------------- Text (GUI)

  /** Draws centered text @since 2.0 */
  public static void drawCenterFont(
    GuiGraphicsExtractor g,
    Component text,
    float x,
    float y,
    int color
  ) {
    g.text(mc.font, text, (int) (x - ((float) mc.font.width(text) / 2f)), (int) y, color, false);
  }

  /** Draws centered text @since 2.0 */
  public static void drawCenterFont(
    GuiGraphicsExtractor g,
    String str,
    float x,
    float y,
    int color
  ) {
    g.text(mc.font, str, (int) (x - ((float) mc.font.width(str) / 2f)), (int) y, color, false);
  }

  /** Draws text in a fixed width. The text is scaled down if it does not fit */
  public static void drawFixedWidthFont(
    GuiGraphicsExtractor g,
    Component text,
    float x,
    float y,
    int color,
    float width
  ) {
    int size = mc.font.width(text);
    // GUI pose is a Matrix3x2fStack (2D) in 1.21.6+
    g.pose().pushMatrix();
    if (size > width) {
      float scale = width / size;
      x /= scale;
      y /= scale;
      g.pose().scale(scale, scale);
    }
    g.text(mc.font, text, (int) x, (int) y, color, false);
    g.pose().popMatrix();
  }

  /** Draws text in a fixed width. The text is scaled down if it does not fit */
  public static void drawFixedWidthFont(
    GuiGraphicsExtractor g,
    String text,
    float x,
    float y,
    int color,
    float width
  ) {
    int size = mc.font.width(text);
    g.pose().pushMatrix();
    if (size > width) {
      float scale = width / size;
      x /= scale;
      y /= scale;
      g.pose().scale(scale, scale);
    }
    g.text(mc.font, text, (int) x, (int) y, color, false);
    g.pose().popMatrix();
  }

  // ----------------- Text (world)

  public static void fontDrawInBatch(
    Component text,
    float x,
    float y,
    int color,
    boolean shadow,
    Matrix4f lastPose,
    MultiBufferSource multiBufferSource,
    Font.DisplayMode displayMode,
    int bakedGlyphColor,
    int packedLightCoords
  ) {
    mc.font.drawInBatch(
      text, x, y, color, shadow, lastPose, multiBufferSource,
      displayMode, bakedGlyphColor, packedLightCoords
    );
  }

  public static void fontDrawInBatch(
    String text,
    float x,
    float y,
    int color,
    boolean shadow,
    Matrix4f lastPose,
    MultiBufferSource multiBufferSource,
    Font.DisplayMode displayMode,
    int bakedGlyphColor,
    int packedLightCoords
  ) {
    mc.font.drawInBatch(
      text, x, y, color, shadow, lastPose, multiBufferSource,
      displayMode, bakedGlyphColor, packedLightCoords
    );
  }

  @Deprecated
  public static void renderTextSprite(
    PoseStack poseStack,
    MultiBufferSource multiBufferSource,
    Component text,
    float x,
    float y,
    float z,
    float size,
    float textX,
    float textY,
    int color,
    int combinedLightIn
  ) {
    poseStack.pushPose();
    poseStack.translate(x, y, z);
    poseStack.scale(0.010416667F * size, -0.010416667F * size, 0.010416667F * size);
    fontDrawInBatch(
      text, textX, -mc.font.lineHeight + textY, color, false,
      poseStack.last().pose(), multiBufferSource, Font.DisplayMode.NORMAL, 0, combinedLightIn
    );
    poseStack.popPose();
  }

  @Deprecated
  public static void renderTextSprite(
    PoseStack poseStack,
    MultiBufferSource multiBufferSource,
    Component text,
    float x,
    float y,
    float z,
    float size,
    float textX,
    float textY,
    int combinedLightIn
  ) {
    renderTextSprite(poseStack, multiBufferSource, text, x, y, z, size, textX, textY, 0, combinedLightIn);
  }

  @Deprecated
  public static void renderCenterTextSprite(
    PoseStack poseStack,
    MultiBufferSource multiBufferSource,
    Component text,
    float x,
    float y,
    float z,
    float size,
    float textX,
    float textY,
    int color,
    int combinedLightIn
  ) {
    poseStack.pushPose();
    poseStack.translate(x, y, z);
    poseStack.scale(0.010416667F * size, -0.010416667F * size, 0.010416667F * size);
    fontDrawInBatch(
      text, ((float) -mc.font.width(text) / 2f) + textX, -mc.font.lineHeight + textY, color, false,
      poseStack.last().pose(), multiBufferSource, Font.DisplayMode.NORMAL, 0, combinedLightIn
    );
    poseStack.popPose();
  }

  @Deprecated
  public static void renderCenterTextSprite(
    PoseStack poseStack,
    MultiBufferSource multiBufferSource,
    Component text,
    float x,
    float y,
    float z,
    float size,
    float textX,
    float textY,
    int combinedLightIn
  ) {
    renderCenterTextSprite(poseStack, multiBufferSource, text, x, y, z, size, textX, textY, 0, combinedLightIn);
  }

  /** Renders text as a sprite */
  public static void renderFontSprite(
    Component text,
    int x,
    int y,
    int color,
    boolean shadow,
    PoseStack poseStack,
    MultiBufferSource multiBufferSource,
    Font.DisplayMode displayMode,
    int bakedGlyphColor,
    int packedLightCoords
  ) {
    poseStack.pushPose();
    poseStack.scale(-0.025F, -0.025F, 0.025F);
    fontDrawInBatch(
      text, x, y, color, shadow, poseStack.last().pose(), multiBufferSource,
      displayMode, bakedGlyphColor, packedLightCoords
    );
    poseStack.popPose();
  }

  /** Renders text as a sprite */
  public static void renderFontSprite(
    String text,
    int x,
    int y,
    int color,
    boolean shadow,
    PoseStack poseStack,
    MultiBufferSource multiBufferSource,
    Font.DisplayMode displayMode,
    int bakedGlyphColor,
    int packedLightCoords
  ) {
    poseStack.pushPose();
    poseStack.scale(-0.025F, -0.025F, 0.025F);
    fontDrawInBatch(
      text, x, y, color, shadow, poseStack.last().pose(), multiBufferSource,
      displayMode, bakedGlyphColor, packedLightCoords
    );
    poseStack.popPose();
  }

  /** Renders centered text as a sprite */
  public static void renderCenterFontSprite(
    Component text,
    int x,
    int y,
    int color,
    boolean shadow,
    PoseStack poseStack,
    MultiBufferSource multiBufferSource,
    Font.DisplayMode displayMode,
    int bakedGlyphColor,
    int packedLightCoords
  ) {
    poseStack.pushPose();
    poseStack.scale(-0.025F, -0.025F, 0.025F);
    x += -mc.font.width(text) / 2;
    fontDrawInBatch(
      text, x, y, color, shadow, poseStack.last().pose(), multiBufferSource,
      displayMode, bakedGlyphColor, packedLightCoords
    );
    poseStack.popPose();
  }

  /** Renders centered text as a sprite */
  public static void renderCenterFontSprite(
    String text,
    int x,
    int y,
    int color,
    boolean shadow,
    PoseStack poseStack,
    MultiBufferSource multiBufferSource,
    Font.DisplayMode displayMode,
    int bakedGlyphColor,
    int packedLightCoords
  ) {
    poseStack.pushPose();
    poseStack.scale(-0.025F, -0.025F, 0.025F);
    x += -mc.font.width(text) / 2;
    fontDrawInBatch(
      text, x, y, color, shadow, poseStack.last().pose(), multiBufferSource,
      displayMode, bakedGlyphColor, packedLightCoords
    );
    poseStack.popPose();
  }

  // ---------------------------------------------------------------- Player faces

  /** Draws a player's face in the GUI, looked up by UUID @since 2.0 */
  public static void drawPlayerFace(GuiGraphicsExtractor g, UUID uuid, int x, int y) {
    drawPlayerFace(g, uuid, x, y, 8);
  }

  /** Draws a player's face in the GUI, looked up by UUID (resizable) @since 2.0 */
  public static void drawPlayerFace(GuiGraphicsExtractor g, UUID uuid, int x, int y, int size) {
    drawFaceTexture(g, OETextureUtils.getPlayerSkinTexture(uuid), x, y, size);
  }

  /** Draws a player's face in the GUI, looked up by name */
  public static void drawPlayerFace(GuiGraphicsExtractor g, String name, int x, int y) {
    drawPlayerFace(g, name, x, y, 8);
  }

  /** Draws a player's face in the GUI, looked up by name (resizable) @since 2.0 */
  public static void drawPlayerFace(GuiGraphicsExtractor g, String name, int x, int y, int size) {
    drawFaceTexture(g, OETextureUtils.getPlayerSkinTexture(name), x, y, size);
  }

  private static void drawFaceTexture(GuiGraphicsExtractor g, Identifier skin, int x, int y, int size) {
    // VERIFY: blit(pipeline, id, x, y, u, v, w, h, regionW, regionH, texW, texH)
    // face layer, then hat layer
    g.blit(RenderPipelines.GUI_TEXTURED, skin, x, y, 8f, 8f, size, size, 8, 8, 64, 64);
    g.blit(RenderPipelines.GUI_TEXTURED, skin, x, y, 40f, 8f, size, size, 8, 8, 64, 64);
  }

  /** Renders a player's face as a sprite, looked up by UUID */
  public static void renderPlayerFaceSprite(
    PoseStack poseStack,
    MultiBufferSource multiBufferSource,
    UUID uuid,
    float size,
    int combinedLightIn,
    int combinedOverlayIn
  ) {
    renderPlayerFaceSprite(
      poseStack,
      multiBufferSource.getBuffer(
        OERenderTypes.simpleSpriteCutout(OETextureUtils.getPlayerSkinTexture(uuid))
      ),
      size, combinedLightIn, combinedOverlayIn
    );
  }

  /** Renders a player's face as a sprite, looked up by name */
  public static void renderPlayerFaceSprite(
    PoseStack poseStack,
    MultiBufferSource multiBufferSource,
    String name,
    float size,
    int combinedLightIn,
    int combinedOverlayIn
  ) {
    renderPlayerFaceSprite(
      poseStack,
      multiBufferSource.getBuffer(
        OERenderTypes.simpleSpriteCutout(OETextureUtils.getPlayerSkinTexture(name))
      ),
      size, combinedLightIn, combinedOverlayIn
    );
  }

  /** Renders a player's face as a sprite */
  public static void renderPlayerFaceSprite(
    PoseStack poseStack,
    VertexConsumer vertexConsumer,
    float size,
    int combinedLightIn,
    int combinedOverlayIn
  ) {
    poseStack.pushPose();
    float sc = size / 8f;
    renderSprite(
      poseStack, vertexConsumer, size, size,
      8f * sc, 8f * sc, 8f * sc, 8f * sc, 64f * sc, 64f * sc,
      combinedLightIn, combinedOverlayIn
    );

    poseStack.pushPose();
    poseStack.translate(0, 0, Mth.EPSILON);
    renderSprite(
      poseStack, vertexConsumer, size, size,
      40f * sc, 8f * sc, 8f * sc, 8f * sc, 64f * sc, 64f * sc,
      combinedLightIn, combinedOverlayIn
    );
    poseStack.popPose();

    poseStack.popPose();
  }

  // ---------------------------------------------------------------- Misc

  public static float getPartialTicks() {
    return OEClientExpectPlatform.getPartialTicks();
  }

  public static void noTransAndRotModelPart(Runnable runnable) {
    ClientMixinTemp.SKIP_TRANSANDROT_MODELPART.set(true);
    try {
      runnable.run();
    } finally {
      ClientMixinTemp.SKIP_TRANSANDROT_MODELPART.set(false);
    }
  }

  public static void renderPlayerArmNoTransAndRot(
    PoseStack poseStack,
    SubmitNodeCollector collector,
    HumanoidArm arm,
    int light
  ) {
    noTransAndRotModelPart(() -> renderPlayerArm(poseStack, collector, arm, light));
  }
}
