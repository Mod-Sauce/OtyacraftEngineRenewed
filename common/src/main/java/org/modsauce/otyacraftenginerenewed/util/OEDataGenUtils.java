package org.modsauce.otyacraftenginerenewed.util;

import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import com.google.common.hash.HashingOutputStream;
import dev.felnull.fnjl.util.FNDataUtil;
import net.minecraft.data.CachedOutput;
import org.modsauce.otyacraftenginerenewed.explatform.OEDataGenExpectPlatform;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

/**
 * Data生成関係
 *
 * @author MORIMORI0317
 */
public final class OEDataGenUtils {
  /**
   * Data生成中かどうか
   *
   * @return 生成中かどうか
   */
  public static boolean isDataGenerating() {
    return OEDataGenExpectPlatform.isDataGenerating();
  }

  /**
   * 保存
   *
   * @param cachedOutput CachedOutput
   * @param stream       Stream
   * @param path         Path
   * @throws IOException Error
   */
  public static void save(CachedOutput cachedOutput, InputStream stream, Path path) throws IOException {
    byte[] bs;
    HashCode hashCode;
    try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(); HashingOutputStream hashingOutputStream = new HashingOutputStream(Hashing.sha1(), byteArrayOutputStream)) {
      FNDataUtil.i2o(stream, hashingOutputStream);
      bs = byteArrayOutputStream.toByteArray();
      hashCode = hashingOutputStream.hash();
    }
    cachedOutput.writeIfNeeded(path, bs, hashCode);
  }
}
