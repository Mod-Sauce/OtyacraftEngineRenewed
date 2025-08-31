package org.modsauce.otyacraftenginerenewed.mixin;

import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.modsauce.otyacraftenginerenewed.shape.IkisugiVoxelShape;
import org.modsauce.otyacraftenginerenewed.shape.IkisugiVoxelShapes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Arrays;

@Mixin(Shapes.class)
public class ShapesMixin {
  @Inject(method = "or(Lnet/minecraft/world/phys/shapes/VoxelShape;[Lnet/minecraft/world/phys/shapes/VoxelShape;)Lnet/minecraft/world/phys/shapes/VoxelShape;", at = @At("RETURN"), cancellable = true)
  private static void or(VoxelShape voxelShape, VoxelShape[] voxelShapes, CallbackInfoReturnable<VoxelShape> cir) {
    var shape = cir.getReturnValue();
    cir.setReturnValue(IkisugiVoxelShapes.getInstance().unite(shape, Arrays.stream(voxelShapes).map(n -> (IkisugiVoxelShape) n).toList()));
  }
}
