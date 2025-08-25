package org.modsauce.otyacraftenginerenewed.util;

import com.madgag.gif.fmsware.AnimatedGifEncoder;
import com.madgag.gif.fmsware.GifDecoder;
import dev.felnull.fnjl.math.FNVec2i;
import dev.felnull.fnjl.util.FNImageUtil;

import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 画像関係のユーティリティ
 *
 * @author MORIMORI0317
 */
public final class OEImageUtils {
    /**
     * 画像のサイズを減らす
     *
     * @param data 画像のバイト配列
     * @param size サイズ指定
     * @return 減し済み画像
     * @throws IOException 変換失敗
     */
    public static byte[] reductionSize(byte[] data, long size) throws IOException {
        long lastSize = data.length;
        if (lastSize <= size) return data;

        GifDecoder decoder = new GifDecoder();
        try (InputStream stream = new ByteArrayInputStream(data)) {
            if (decoder.read(stream) != 0)
                return FNImageUtil.toByteArray(FNImageUtil.reductionSize(ImageIO.read(new ByteArrayInputStream(data)), size), "png");
        }

        float scale = (float) size / lastSize;
        try (ByteArrayOutputStream stream = new ByteArrayOutputStream()) {
            AnimatedGifEncoder encoder = new AnimatedGifEncoder();
            encoder.start(stream);
            encoder.setRepeat(0);
            FNVec2i siz = null;
            for (int i = 0; i < decoder.getFrameCount(); i++) {
                var fb = decoder.getFrame(i);
                var le = FNImageUtil.toByteArray(fb, "png");
                var img = FNImageUtil.reductionSize(fb, (long) ((float) le.length * scale));
                if (siz == null) {
                    siz = new FNVec2i(img.getWidth(), img.getHeight());
                } else {
                    if (siz.getX() > img.getWidth() && siz.getY() > img.getHeight())
                        siz = new FNVec2i(img.getWidth(), img.getHeight());
                }
            }
            if (siz != null) {
                for (int i = 0; i < decoder.getFrameCount(); i++) {
                    encoder.setDelay(decoder.getDelay(i));
                    encoder.addFrame(FNImageUtil.resize(decoder.getFrame(i), siz.getX(), siz.getY()));
                }
            }
            encoder.finish();
            return stream.toByteArray();
        }
    }


    /**
     * 画像かどうか
     *
     * @param data バイト配列
     * @return 画像かどうか
     */
    public static boolean isImage(byte[] data) {
        return isGif(data) || FNImageUtil.isImage(data);
    }

    /**
     * Gif画像かどうか
     *
     * @param data バイト配列
     * @return gifかどうか
     */
    public static boolean isGif(byte[] data) {
        GifDecoder decoder = new GifDecoder();
        try (InputStream stream = new ByteArrayInputStream(data)) {
            return decoder.read(stream) == 0;
        } catch (IOException ignored) {
        }
        return false;
    }
}
