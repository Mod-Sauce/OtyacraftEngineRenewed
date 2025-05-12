package org.modsauce.otyacraftenginerenewed.client.renderer.texture;

import com.madgag.gif.fmsware.GifDecoder;
import com.mojang.blaze3d.platform.NativeImage;
import dev.felnull.fnjl.util.FNImageUtil;
import org.modsauce.otyacraftenginerenewed.client.renderer.texture.impl.TextureLoadProgressImpl;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.Tickable;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.function.Consumer;

public final class DynamicGifTexture extends DynamicTexture implements Tickable {
    private final ImageFrame[] frames;
    private final long duration;
    private int last;

    private DynamicGifTexture(long duration, ImageFrame... frames) {
        super(frames[0].image());
        this.duration = duration;
        this.frames = frames;
    }

    @Override
    public void tick() {
        int ct = Math.toIntExact(getFrameByTime(System.currentTimeMillis() % duration));
        if (ct != last) {
            this.pixels = frames[ct].image();
            upload();
            last = ct;
        }
    }

    private int getFrameByTime(long time) {
        return Mth.binarySearch(0, frames.length, v -> time <= frames[v].timestamp());
    }

    @Override
    public void close() {
        super.close();
        if (frames != null) {
            for (ImageFrame image : frames) {
                if (image != null)
                    image.image().close();
            }
        }
    }

    @NotNull
    public static DynamicGifTexture create(@NotNull GifDecoder decoder, @Nullable Consumer<TextureLoadProgress> progress) throws IOException {
        ImageFrame[] frames = new ImageFrame[decoder.getFrameCount()];
        if (progress != null)
            progress.accept(new TextureLoadProgressImpl("Gif decoding", decoder.getFrameCount(), 0));
        long duration = 0;
        for (int i = 0; i < decoder.getFrameCount(); i++) {
            if (progress != null)
                progress.accept(new TextureLoadProgressImpl("Gif decoding", decoder.getFrameCount(), i + 1));
            var img = decoder.getFrame(i);
            long delay = decoder.getDelay(i);
            duration += delay;
            try (var stream = FNImageUtil.toInputStream(img, "png")) {
                frames[i] = new ImageFrame(NativeImage.read(stream), duration);
            }
        }
        return new DynamicGifTexture(duration, frames);
    }

    private static record ImageFrame(NativeImage image, long timestamp) {
    }
}
