package org.modsauce.otyacraftenginerenewed.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
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

import java.util.ArrayList;
import java.util.List;


@Mixin(LevelRenderer.class)
public abstract class LevelRendererMixin {
  @Shadow
  @Nullable
  private ClientLevel level;

  @Inject(method = "renderHitOutline", at = @At("HEAD"), cancellable = true)
  private void renderHitOutline(PoseStack poseStack, VertexConsumer vertexConsumer, Entity entity, double d, double e, double f, BlockPos blockPos, BlockState blockState, CallbackInfo ci) {
    var shapeType = OtyacraftEngineClientDebug.getInstance().getHighlightVoxelShape();
    if (shapeType != HighlightVoxelShapeType.OFF) {
      var shape = shapeType.getGetter();
      if (shape != null)
        LevelRenderer.renderVoxelShape(poseStack, vertexConsumer, shape.getShape(blockState, level, blockPos, CollisionContext.of(entity)), (double) blockPos.getX() - d, (double) blockPos.getY() - e, (double) blockPos.getZ() - f, 0.0F, 0.0F, 0.0F, 0.4F, true);
      ci.cancel();
    }
  }

  @Inject(method = "renderShape", at = @At("HEAD"), cancellable = true)
  private static void renderShape(PoseStack poseStack, VertexConsumer vertexConsumer, VoxelShape voxelShape, double x, double y, double z, float r, float g, float b, float a, CallbackInfo ci) {
    if (((IkisugiVoxelShape) voxelShape).getRenderEdges() == null) return;
    ci.cancel();

    for (VoxelEntry entry : ((IkisugiVoxelShape) voxelShape).getRenderEdges()) {
      var cls = ClientIVShapeManager.getInstance().getVoxelClientShape(entry.getLocation());
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
        renderShapeEdge(pose, vertexConsumer, edge.stX(), edge.stY(), edge.stZ(), edge.enX(), edge.enY(), edge.enZ(), x, y, z, r, g, b, a);
      }

      poseStack.popPose();
    }
  }


  private static void renderShapeEdge(PoseStack.Pose pose, VertexConsumer vertexConsumer, double sx, double sy, double sz, double ex, double ey, double ez, double x, double y, double z, float r, float g, float b, float a) {
    float q = (float) (ex - sx);
    float r2 = (float) (ey - sy);
    float s = (float) (ez - sz);
    float t = Mth.sqrt(q * q + r2 * r2 + s * s);
    q /= t;
    r2 /= t;
    s /= t;
    vertexConsumer.vertex(pose.pose(), (float) (sx + x), (float) (sy + y), (float) (sz + z)).color(r, g, b, a).normal(pose.normal(), q, r2, s).endVertex();
    vertexConsumer.vertex(pose.pose(), (float) (ex + x), (float) (ey + y), (float) (ez + z)).color(r, g, b, a).normal(pose.normal(), q, r2, s).endVertex();
  }
}
