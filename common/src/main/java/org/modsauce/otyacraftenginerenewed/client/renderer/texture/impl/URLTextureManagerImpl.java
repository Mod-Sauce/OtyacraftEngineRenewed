package org.modsauce.otyacraftenginerenewed.client.renderer.texture.impl;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import dev.felnull.fnjl.util.FNDataUtil;
import dev.felnull.fnjl.util.FNStringUtil;
import dev.felnull.fnjl.util.FNURLUtil;
import org.modsauce.otyacraftenginerenewed.OtyacraftEngine;
import org.modsauce.otyacraftenginerenewed.client.event.OEClientEventHooks;
import org.modsauce.otyacraftenginerenewed.client.renderer.texture.NativeTextureManager;
import org.modsauce.otyacraftenginerenewed.client.renderer.texture.TextureLoadProgress;
import org.modsauce.otyacraftenginerenewed.client.renderer.texture.TextureLoadResult;
import org.modsauce.otyacraftenginerenewed.client.renderer.texture.URLTextureManager;
import org.modsauce.otyacraftenginerenewed.util.OEPaths;
import org.modsauce.otyacraftenginerenewed.util.OEUtils;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.apache.commons.lang3.tuple.Pair;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.regex.Pattern;

public class URLTextureManagerImpl implements URLTextureManager {
    public static final URLTextureManagerImpl INSTANCE = new URLTextureManagerImpl();
    private final Map<String, UUID> caches = new HashMap<>();
    private final Map<String, URLTextureEntry> loadURLTextures = new HashMap<>();
    private ExecutorService executorService = createExecutor();
    private Function<String, String> hashCache = createHashCache();
    private UUID runtimeId = UUID.randomUUID();

    @Override
    public void init() {
        load();
        optimize();
    }

    private void load() {
        long st = System.currentTimeMillis();

        var urlIndex = getIndexPath().toFile();
        if (!urlIndex.exists()) return;

        JsonObject index;

        try {
            if (urlIndex.exists()) {
                index = OEUtils.readJson(urlIndex, JsonObject.class);
            } else {
                index = new JsonObject();
            }
        } catch (JsonParseException e) {
            OtyacraftEngine.LOGGER.error("url texture cache index file is corrupt and will be removed", e);
            index = new JsonObject();
        } catch (IOException e) {
            OtyacraftEngine.LOGGER.error("Failed to load url texture cache indexes", e);
            return;
        }

        synchronized (caches) {
            caches.clear();

            index.entrySet().forEach(entry -> {
                var v = entry.getValue();
                if (v != null && v.isJsonPrimitive() && v.getAsJsonPrimitive().isString()) {
                    var uuid = FNStringUtil.getUUIDFromNoHyphenStringNonThrow(v.getAsString());
                    if (uuid != null) {
                        var file = getCacheFolderPath().resolve(v.getAsString()).toFile();
                        if (file.exists()) caches.put(entry.getKey(), uuid);
                    }
                }
            });
        }

        if (caches.size() > 0)
            OtyacraftEngine.LOGGER.info(String.format("Loaded %s URL texture cache indexes in %s ms.", caches.size(), System.currentTimeMillis() - st));
    }

    private void optimize() {
        long st = System.currentTimeMillis();

        var urlIndex = getIndexPath().toFile();
        if (!urlIndex.exists()) return;

        var fol = getCacheFolderPath().toFile();
        if (!fol.exists() || !fol.isDirectory()) return;

        var fils = fol.listFiles();
        if (fils == null) return;

        List<File> noExistFiles = new ArrayList<>();
        synchronized (caches) {
            for (File fil : fils) {
                var name = fil.getName();
                boolean flg = false;
                var uuid = FNStringUtil.getUUIDFromNoHyphenStringNonThrow(name);
                if (uuid != null) flg = caches.containsValue(uuid);

                if (!flg) noExistFiles.add(fil);
            }
        }

        int ct = 0;
        for (File neFile : noExistFiles) {
            if (neFile.delete()) ct++;
        }

        if (ct > 0)
            OtyacraftEngine.LOGGER.info(String.format("Removed %s unnecessary URL Texture Cache files in %sms.", ct, System.currentTimeMillis() - st));
    }

    @Override
    public synchronized void save() {
        if (caches.isEmpty())
            return;

        long st = System.currentTimeMillis();
        var fol = getCacheFolderPath().toFile();

        if (!FNDataUtil.wishMkdir(fol, (f) -> OtyacraftEngine.LOGGER.error("Failed to create url textures folder")))
            return;

        var urlIndex = getIndexPath().toFile();
        JsonObject index = new JsonObject();

        synchronized (caches) {
            caches.forEach((url, id) -> {
                var idStr = id.toString().replace("-", "");
                var file = getCacheFolderPath().resolve(idStr).toFile();
                if (file.exists()) index.addProperty(url, idStr);
            });
        }

        if (!FNDataUtil.wishMkdir(urlIndex.getParentFile(), (f) -> OtyacraftEngine.LOGGER.error("Failed to create url textures folder")))
            return;

        try {
            OEUtils.writeJson(urlIndex, index);
        } catch (IOException e) {
            OtyacraftEngine.LOGGER.error("Failed to save url textures index", e);
            return;
        }

        if (caches.size() > 0)
            OtyacraftEngine.LOGGER.info(String.format("Saved %s URL texture cache indexes in %s ms.", caches.size(), System.currentTimeMillis() - st));
    }

    @Override
    public synchronized void release() {
        executorService.shutdown();
        executorService = createExecutor();

        hashCache = createHashCache();
        runtimeId = UUID.randomUUID();

        synchronized (loadURLTextures) {
            var ntm = NativeTextureManager.getInstance();
            for (URLTextureEntry entry : loadURLTextures.values()) {
                var u = entry.result.getUUID();
                if (u != null)
                    ntm.freeNativeTexture(u);
            }
            loadURLTextures.clear();
        }
    }

    @Override
    public void tick() {

    }

    private ExecutorService createExecutor() {
        return Executors.newFixedThreadPool(OtyacraftEngine.getConfig().getClientConfig().getUrlTextureConfig().getMaxLoaderCount(), new BasicThreadFactory.Builder().namingPattern(OtyacraftEngine.MODID + "-url-texture-loader-%d").daemon(true).build());
    }

    private Function<String, String> createHashCache() {
        return FNDataUtil.memoize(n -> {
            try {
                byte[] md5 = FNDataUtil.createMD5Hash(n.getBytes(StandardCharsets.UTF_8));
                return new String(Hex.encodeHex(md5));
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private Path getIndexPath() {
        return OEPaths.getClientOEFolderPath().resolve("url_textures_index.json");
    }

    private Path getCacheFolderPath() {
        return OEPaths.getClientOEFolderPath().resolve("url_texture_cache");
    }

    private Path getCachePath(UUID uuid) {
        return getCacheFolderPath().resolve(uuid.toString().replace("-", ""));
    }

    @Override
    public TextureLoadResult getAndAsyncLoad(String url, boolean cached) {
        var hash = hashCache.apply(url);
        URLTextureEntry entry;
        synchronized (loadURLTextures) {
            entry = loadURLTextures.computeIfAbsent(hash, h -> loadStart(h, url, cached));
            if (!entry.cached && cached) {
                entry.cached = true;
                if (entry.cachedEnd) loadCacheOnly(hash, url, entry.uuid);
            }
        }

        return entry.result;
    }

    private URLTextureEntry loadStart(String hash, String url, boolean cached) {
        var entry = new URLTextureEntry(new URLTextureLoadResult(null, false, -1, null, null), cached);
        entry.result.setProgress(new TextureLoadProgressImpl("Load waiting", 0, 0));
        final UUID ruid = runtimeId;

        CompletableFuture.runAsync(() -> {
            try {
                checkUrl(url);
            } catch (Exception ex) {
                throw new URLNotReloadException(ex);
            }
        }, executorService).thenApplyAsync(n -> {
            try {
                return loadTexture(hash, url);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }, executorService).thenApplyAsync(ret -> {
            try {
                entry.uuid = ret.uuid;
                entry.cachedEnd = true;
                if (entry.cached && ret.cached)
                    return writeCache(hash, ret, entry.result::setProgress);

                return new TextureCachedPipe(ret.stream, ret.uuid);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }, executorService).thenApplyAsync(ret -> {
            var ntm = NativeTextureManager.getInstance();
            var r = ntm.getAndLoadTexture(ret.uuid, ret.stream, entry.result::setProgress);

            if (ruid != runtimeId) {
                NativeTextureManager.getInstance().freeNativeTexture(ret.uuid);
                throw new RuntimeException("Released");
            }

            return new URLTextureLoadResult(null, false, r, ret.uuid());
        }, executorService).whenCompleteAsync((ret, e) -> {
            if (e != null) ret = new URLTextureLoadResult(e, !(e instanceof URLNotReloadException), null, null);
            entry.result = ret;
        }, executorService);

        return entry;
    }

    private void loadCacheOnly(String hash, String url, UUID uuid) {
        CompletableFuture.supplyAsync(() -> {
            try {
                var r = connect(url);
                return new TextureLoadedPipe(r.getLeft(), r.getRight(), true, uuid);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }, executorService).thenApplyAsync(ret -> {
            try {
                return writeCache(hash, ret, r -> {
                });
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private TextureCachedPipe writeCache(String hash, TextureLoadedPipe pipe, Consumer<TextureLoadProgress> progress) throws IOException {
        var fil = getCachePath(pipe.uuid).toFile();
        FNDataUtil.wishMkdir(fil.getParentFile());

        progress.accept(new TextureLoadProgressImpl("Cache getting", (int) pipe.length, 0));
        try (InputStream inputStream = pipe.stream) {
            FNDataUtil.fileWriteToProgress(inputStream, pipe.length, fil, progressListener -> progress.accept(new TextureLoadProgressImpl("Cache getting", (int) progressListener.getWrittenLength(), (int) progressListener.getLength())));
        }
        progress.accept(new TextureLoadProgressImpl("Cache getting", (int) pipe.length, (int) pipe.length));

        synchronized (caches) {
            caches.put(hash, pipe.uuid);
        }
        return new TextureCachedPipe(new BufferedInputStream(new FileInputStream(fil)), pipe.uuid);
    }

    private TextureLoadedPipe loadTexture(String hash, String url) throws IOException {
        UUID fileCache;
        synchronized (caches) {
            fileCache = caches.get(hash);
        }

        if (fileCache != null) {
            File cf = getCachePath(fileCache).toFile();
            if (cf.exists()) {
                return new TextureLoadedPipe(new BufferedInputStream(new FileInputStream(cf)), -1, false, fileCache);
            }
        }
        var c = connect(url);
        return new TextureLoadedPipe(c.getLeft(), c.getRight(), true, UUID.randomUUID());
    }

    private Pair<InputStream, Long> connect(String url) throws IOException {
        var con = FNURLUtil.getConnection(new URL(OEClientEventHooks.onSwapTextureURL(url)));
        long max = 1024L * 1024L * 10;

        try (InputStream in = con.getInputStream(); InputStream bin = new BufferedInputStream(in); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            long size = FNDataUtil.inputToOutputLimit(bin, out, (int) max);
            if (size <= -1) throw new IOException("Size Over: " + max + "byte");

            return Pair.of(new ByteArrayInputStream(out.toByteArray()), size);
        }
    }

    private record TextureLoadedPipe(InputStream stream, long length, boolean cached, UUID uuid) {
    }

    private record TextureCachedPipe(InputStream stream, UUID uuid) {
    }

    private static class URLTextureEntry {
        private URLTextureLoadResult result;
        private boolean cached;
        private boolean cachedEnd;
        private UUID uuid;

        private URLTextureEntry(URLTextureLoadResult result, boolean cached) {
            this.result = result;
            this.cached = cached;
        }
    }

    private void checkUrl(String url) {
        if (url.length() > 300) throw new IllegalArgumentException("URL is too long");

        boolean oea = Pattern.compile(OtyacraftEngine.getConfig().getClientConfig().getUrlTextureConfig().getUrlRegex()).matcher(url).matches();
        boolean eva = OEClientEventHooks.onCheckTextureURL(url);
        boolean aflg = oea || eva;

        if (!aflg) throw new IllegalArgumentException("Not allowed URL");
    }

    private static class URLNotReloadException extends RuntimeException {
        public URLNotReloadException(Throwable throwable) {
            super(throwable);
        }
    }
}
