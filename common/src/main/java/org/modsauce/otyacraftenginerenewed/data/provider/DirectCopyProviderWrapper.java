package org.modsauce.otyacraftenginerenewed.data.provider;

import org.modsauce.otyacraftenginerenewed.data.CrossDataGeneratorAccess;
import org.modsauce.otyacraftenginerenewed.util.OEDataGenUtils;
import net.minecraft.Util;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.PackOutput;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;

public class DirectCopyProviderWrapper extends InputBaseProviderWrapper {
    public DirectCopyProviderWrapper(PackOutput packOutput, PackOutput.Target target, String kind, CrossDataGeneratorAccess crossDataGeneratorAccess) {
        super(packOutput, target, kind, crossDataGeneratorAccess);
    }

    public DirectCopyProviderWrapper(PackOutput packOutput, PackOutput.Target target, String modId, String kind, CrossDataGeneratorAccess crossDataGeneratorAccess) {
        super(packOutput, target, modId, kind, crossDataGeneratorAccess);
    }

    @Override
    protected @Nullable CompletableFuture<?> runTask(CachedOutput cachedOutput, Path inputRoot, Path target) {
        return CompletableFuture.runAsync(() -> {
            var locEx = toResourceLocationAndExtension(inputRoot, target);
            try (InputStream stream = new FileInputStream(target.toFile()); InputStream bufStream = new BufferedInputStream(stream)) {
                OEDataGenUtils.save(cachedOutput, bufStream, pathProvider.file(locEx.getKey(), locEx.getValue()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }, Util.backgroundExecutor());
    }

    @Override
    protected boolean isTarget(Path rootPath, Path targetPath) {
        return true;
    }

    @Override
    public String getName() {
        return "Input copy";
    }
}
