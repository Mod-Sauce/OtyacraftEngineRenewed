package org.modsauce.otyacraftenginerenewed.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.ModelPart;
import org.modsauce.otyacraftenginerenewed.client.ClientMixinTemp;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ModelPart.class)
public class ModelPartMixin {
  @Inject(method = "translateAndRotate", at = @At("HEAD"), cancellable = true)
  private void translateAndRotate(PoseStack poseStack, CallbackInfo ci) {
    if (ClientMixinTemp.SKIP_TRANSANDROT_MODELPART.get())
      ci.cancel();
  }
}