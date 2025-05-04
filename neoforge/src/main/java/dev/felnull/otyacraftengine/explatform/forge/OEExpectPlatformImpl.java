package dev.felnull.otyacraftengine.explatform.forge;

import dev.felnull.fnjl.util.FNReflectionUtil;
import dev.felnull.otyacraftengine.forge.mixin.MobBucketItemInvoker;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.MobBucketItem;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.forgespi.language.ModFileScanData;
import org.objectweb.asm.Type;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

public class OEExpectPlatformImpl {
    public static EntityType<?> getMobBucketEntity(MobBucketItem mobBucketItem) {
        return ((MobBucketItemInvoker) mobBucketItem).invokeGetFishTypeInvoker();
    }

    public static Stream<TagKey<EntityType<?>>> getTags(EntityType<?> entityType) {
        return entityType.getTags();
    }

    public static String getItemCreatorModId(ItemStack stack) {
        return stack.getItem().getCreatorModId(stack);
    }

    public static <T> List<T> getCallPoints(String name, Class<?> annotationClass, Class<T> interfaceClass) {
        Type type = Type.getType(annotationClass);
        List<T> lst = new ArrayList<>();

        ModList.get().getAllScanData().stream().map(ModFileScanData::getAnnotations).flatMap(Collection::stream).filter(n -> n.annotationType().equals(type)).forEach(n -> {
            Class<?> cls = null;
            try {
                cls = Class.forName(n.clazz().getClassName());
            } catch (Exception ignored) {
            }
            if (cls == null) return;
            var inst = FNReflectionUtil.newInstance(cls);
            if (interfaceClass.isInstance(inst))
                lst.add((T) inst);
        });
        return lst;
    }

    public static FoodProperties getFoodProperties(ItemStack stack, LivingEntity livingEntity) {
        return stack.getFoodProperties(livingEntity);
    }
}
