package dev.felnull.otyacraftengine.impl.forge;

import dev.felnull.fnjl.util.FNReflectionUtil;
import dev.felnull.otyacraftengine.forge.mixin.MobBucketItemAccessor;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.MobBucketItem;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.forgespi.language.ModFileScanData;
import org.objectweb.asm.Type;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class OEExpectPlatformImpl {
    public static EntityType<?> getMobBucketEntity(MobBucketItem mobBucketItem) {
        return ((MobBucketItemAccessor) mobBucketItem).getFishTypeInvoker();
    }

    public static <T> List<T> getEntryPoints(String name, Class<?> annotationClass, Class<T> interfaceClass) {
        Type type = Type.getType(annotationClass);
        List<T> lst = new ArrayList<>();

        ModList.get().getAllScanData().stream().map(ModFileScanData::getAnnotations).flatMap(Collection::stream).filter(n -> n.annotationType().equals(type)).forEach(n -> {
            Class<?> cls = null;// FNReflectionUtil.getClassForName(n.clazz().getClassName(), false);
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
}
