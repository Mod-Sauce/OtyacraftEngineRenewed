package org.modsauce.otyacraftenginerenewed.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;
import org.modsauce.otyacraftenginerenewed.client.debug.HighlightVoxelShapeType;
import org.modsauce.otyacraftenginerenewed.client.debug.OtyacraftEngineClientDebug;
import org.modsauce.otyacraftenginerenewed.client.shape.ClientIVShapeManager;
import org.modsauce.otyacraftenginerenewed.shape.IkisugiVoxelShape;
import org.modsauce.otyacraftenginerenewed.shape.RotateAngledAxis;
import org.modsauce.otyacraftenginerenewed.shape.VoxelEdge;
import org.modsauce.otyacraftenginerenewed.shape.VoxelEntry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelRenderer.class)
public abstract class LevelRendererMixin {

  @Shadow
  @Nullable
  private ClientLevel level;

  @Inject(method = "renderHitOutline", at = @At("HEAD"), cancellable = true)
    private void renderHitOutline(
        PoseStack poseStack,
        VertexConsumer vertexConsumer,
        Entity entity,
        double d,
        double e,
        double f,
        BlockPos blockPos,
        BlockState blockState,
        CallbackInfo ci
    ) {
      var shapeType =
          OtyacraftEngineClientDebug.getInstance().getHighlightVoxelShape();
      if (shapeType != HighlightVoxelShapeType.OFF) {
        var shape = shapeType.getGetter();
        if (shape != null) {
          VoxelShape voxelShape = shape.getShape(
              blockState,
              level,
              blockPos,
              CollisionContext.of(entity)
          );

          renderShapeOutline(
              poseStack,
              vertexConsumer,
              voxelShape,
              (double) blockPos.getX() - d,
              (double) blockPos.getY() - e,
              (double) blockPos.getZ() - f,
              0.0F, 0.0F, 0.0F, 0.4F
          );

          ci.cancel();
        }
      }
    }

    private void renderShapeOutline(PoseStack poseStack, VertexConsumer consumer, VoxelShape shape, double x, double y, double z, float red, float green, float blue, float alpha) {
      PoseStack.Pose pose = poseStack.last();
      shape.forAllEdges((minX, minY, minZ, maxX, maxY, maxZ) -> {
        consumer.addVertex(pose, (float)(minX + x), (float)(minY + y), (float)(minZ + z))
            .setColor(red, green, blue, alpha)
            .setNormal(pose, 1.0F, 0.0F, 0.0F);
        consumer.addVertex(pose, (float)(maxX + x), (float)(maxY + y), (float)(maxZ + z))
            .setColor(red, green, blue, alpha)
            .setNormal(pose, 1.0F, 0.0F, 0.0F);
      });
    }

  @Inject(method = "renderShape", at = @At("HEAD"), cancellable = true)
  private static void renderShape(
    PoseStack poseStack,
    VertexConsumer vertexConsumer,
    VoxelShape voxelShape,
    double x,
    double y,
    double z,
    float r,
    float g,
    float b,
    float a,
    CallbackInfo ci
  ) {
    if (((IkisugiVoxelShape) voxelShape).getRenderEdges() == null) return;
    ci.cancel();

    for (VoxelEntry entry : ((IkisugiVoxelShape) voxelShape).getRenderEdges()) {
      var cls = ClientIVShapeManager.getInstance().getVoxelClientShape(
        entry.getLocation()
      );
      if (cls == null) continue;
      poseStack.pushPose();
      var p = entry.getPose();
      var pose = poseStack.last();

      var cache = cls.getEdgeCache(entry);

      if (cache == null) {
        List<VoxelEdge> edges = new ArrayList<>();
        for (VoxelEdge edge : cls.getRenderEdges()) {
          for (RotateAngledAxis axi : p.axis()) {
            edge = axi.rotationEdge(edge);
          }
          edges.add(edge);
        }
        cache = edges.toArray(new VoxelEdge[0]);
        cls.setEdgeCache(entry, cache);
      }

      for (VoxelEdge edge : cache) {
        renderShapeEdge(
          pose,
          vertexConsumer,
          edge.stX(),
          edge.stY(),
          edge.stZ(),
          edge.enX(),
          edge.enY(),
          edge.enZ(),
          x,
          y,
          z,
          r,
          g,
          b,
          a
        );
      }

      poseStack.popPose();
    }
  }

  private static void renderShapeEdge(
    PoseStack.Pose pose,
    VertexConsumer vertexConsumer,
    double sx,
    double sy,
    double sz,
    double ex,
    double ey,
    double ez,
    double x,
    double y,
    double z,
    float r,
    float g,
    float b,
    float a
  ) {
    float q = (float) (ex - sx);
    float r2 = (float) (ey - sy);
    float s = (float) (ez - sz);
    float t = Mth.sqrt(q * q + r2 * r2 + s * s);
    q /= t;
    r2 /= t;
    s /= t;
    vertexConsumer
      .addVertex(
        pose.pose(),
        (float) (sx + x),
        (float) (sy + y),
        (float) (sz + z)
      )
      .setColor(r, g, b, a)
      .setNormal(pose, q, r2, s);
    vertexConsumer
      .addVertex(
        pose.pose(),
        (float) (ex + x),
        (float) (ey + y),
        (float) (ez + z)
      )
      .setColor(r, g, b, a)
      .setNormal(pose, q, r2, s);
  }
}
