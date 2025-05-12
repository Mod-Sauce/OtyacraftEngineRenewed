package org.modsauce.otyacraftenginerenewed.client.util;

import com.sun.jna.platform.win32.Shell32Util;
import com.sun.jna.platform.win32.ShlObj;
import net.minecraft.Util;
import org.jetbrains.annotations.Nullable;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * ネイティブ関係のユーティリティ
 */
public class OENativeUtils {
    @Nullable
    public static Path getMyMusicFolder() {
        try {
            if (Util.getPlatform() == Util.OS.WINDOWS)
                return Paths.get(Shell32Util.getSpecialFolderPath(ShlObj.CSIDL_MYMUSIC, false));
        } catch (Throwable ignored) {
        }
        return null;
    }

    @Nullable
    public static Path getMyPicturesFolder() {
        try {
            if (Util.getPlatform() == Util.OS.WINDOWS)
                return Paths.get(Shell32Util.getSpecialFolderPath(ShlObj.CSIDL_MYPICTURES, false));
        } catch (Throwable ignored) {
        }
        return null;
    }
}
