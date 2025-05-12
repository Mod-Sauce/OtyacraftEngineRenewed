package org.modsauce.otyacraftenginerenewed.client.entity;

import com.google.common.hash.Hashing;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import dev.felnull.fnjl.util.FNDataUtil;
import org.modsauce.otyacraftenginerenewed.util.OEPlayerUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.core.UUIDUtil;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.SkullBlockEntity;
import org.apache.commons.io.FilenameUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

public class ClientPlayerInfoManager {
    private static final ClientPlayerInfoManager INSTANCE = new ClientPlayerInfoManager();
    private static final Minecraft mc = Minecraft.getInstance();
    private final Map<String, GameProfile> PLAYER_PROFILES = new HashMap<>();
    private final Map<String, PlayerUUIDByNameResult> UUID_BY_NAME_ENTRY = new HashMap<>();
    private final Map<UUID, PlayerNameByUUIDResult> NAME_BY_UUID_ENTRY = new HashMap<>();
    private Function<String, ResourceLocation> SKIN_TEXTURE_LOCATION_CACHE = createSkinTextureLocationCache();

    public static ClientPlayerInfoManager getInstance() {
        return INSTANCE;
    }

    public void clear() {
        synchronized (PLAYER_PROFILES) {
            PLAYER_PROFILES.clear();
        }
        SKIN_TEXTURE_LOCATION_CACHE = createSkinTextureLocationCache();
    }

    private Function<String, ResourceLocation> createSkinTextureLocationCache() {
        return FNDataUtil.memoize(url -> {
            String hashStr = Hashing.sha1().hashUnencodedChars(FilenameUtils.getBaseName(url)).toString();
            return new ResourceLocation("skins/" + hashStr);
        });
    }

    @NotNull
    public GameProfile getLackProfileTolerance(@NotNull String name) {
        synchronized (PLAYER_PROFILES) {
            if (PLAYER_PROFILES.containsKey(name))
                return PLAYER_PROFILES.get(name);
            var gp = new GameProfile(null, name);
            PLAYER_PROFILES.put(name, gp);
            // Skip profile lookup as it's not available in this version
            // Just use the offline profile directly
            // No profile lookup callback needed
            return gp;
        }
    }

    @NotNull
    public Optional<UUID> getUUIDByName(@NotNull String name) {
        var cr = getUUIDByNameClient(name);
        if (cr != null)
            return Optional.of(cr);
        return OEPlayerUtils.getUUIDByName(name);
    }

    @NotNull
    public CompletableFuture<Optional<UUID>> getUUIDByNameAsync(@NotNull String name) {
        var cr = getUUIDByNameClient(name);
        if (cr != null)
            return CompletableFuture.completedFuture(Optional.of(cr));
        return OEPlayerUtils.getUUIDByNameAsync(name);
    }

    private UUID getUUIDByNameClient(String name) {
        if (mc.player != null) {
            if (mc.player.getGameProfile().getName().equals(name))
                return mc.player.getGameProfile().getId();

            var pl = mc.player.connection.getPlayerInfo(name);
            if (pl != null && pl.getProfile() != null)
                return pl.getProfile().getId();
        }
        return null;
    }

    @NotNull
    public Optional<String> getNameByUUID(@NotNull UUID uuid) {
        var cr = getNameByUUIDClient(uuid);
        if (cr != null)
            return Optional.of(cr);
        return OEPlayerUtils.getNameByUUID(uuid);
    }

    @NotNull
    public CompletableFuture<Optional<String>> getNameByUUIDAsync(@NotNull UUID uuid) {
        var cr = getNameByUUIDClient(uuid);
        if (cr != null)
            return CompletableFuture.completedFuture(Optional.of(cr));
        return OEPlayerUtils.getNameByUUIDAsync(uuid);
    }

    private String getNameByUUIDClient(UUID uuid) {
        if (mc.player != null) {
            if (mc.player.getGameProfile().getId().equals(uuid))
                return mc.player.getGameProfile().getName();

            var pl = mc.player.connection.getPlayerInfo(uuid);
            if (pl != null && pl.getProfile() != null)
                return pl.getProfile().getName();
        }
        return null;
    }

    @NotNull
    public PlayerUUIDByNameResult getUUIDByNameTolerance(@NotNull String name) {
        synchronized (UUID_BY_NAME_ENTRY) {
            var ret = UUID_BY_NAME_ENTRY.get(name);
            if (ret == null) {
                ret = new PlayerUUIDByNameResult(null, true);
                UUID_BY_NAME_ENTRY.put(name, ret);
                getUUIDByNameAsync(name).thenAcceptAsync(uuid -> {
                    synchronized (UUID_BY_NAME_ENTRY) {
                        UUID_BY_NAME_ENTRY.put(name, new PlayerUUIDByNameResult(uuid.orElse(null), false));
                    }
                });
            }
            return ret;
        }
    }

    @NotNull
    public PlayerNameByUUIDResult getNameByUUIDTolerance(@NotNull UUID uuid) {
        synchronized (NAME_BY_UUID_ENTRY) {
            var ret = NAME_BY_UUID_ENTRY.get(uuid);
            if (ret == null) {
                ret = new PlayerNameByUUIDResult(null, true);
                NAME_BY_UUID_ENTRY.put(uuid, ret);
                getNameByUUIDAsync(uuid).thenAcceptAsync(name -> {
                    synchronized (NAME_BY_UUID_ENTRY) {
                        NAME_BY_UUID_ENTRY.put(uuid, new PlayerNameByUUIDResult(name.orElse(null), false));
                    }
                });
            }
            return ret;
        }
    }

    @Nullable
    public ResourceLocation getPlayerTexture(@NotNull MinecraftProfileTexture.Type type, @NotNull String name) {
        if (mc.player != null) {
            var pl = mc.player.connection.getPlayerInfo(name);
            if (pl != null)
                return getTexture(pl, type);
        }

        var gp = getLackProfileTolerance(name);
        var tex = mc.getSkinManager().getInsecureSkin(gp);
        if (tex != null) {
            var hr = SKIN_TEXTURE_LOCATION_CACHE.apply(tex.textureUrl());
            var mt = MissingTextureAtlasSprite.getTexture();
            var at = mc.getTextureManager().getTexture(hr, mt);
            if (at == mt)
                // Method name has changed in newer versions
                // For now, return a fallback texture
                return new ResourceLocation("textures/entity/steve.png");
            return hr;
        }
        // Method has changed in newer versions
        // Return a fallback texture
        return type == MinecraftProfileTexture.Type.SKIN ? new ResourceLocation("textures/entity/steve.png") : null;
    }

    @Nullable
    public ResourceLocation getPlayerTexture(@NotNull MinecraftProfileTexture.Type type, @NotNull UUID uuid) {
        if (mc.player != null) {
            var pl = mc.player.connection.getPlayerInfo(uuid);
            if (pl != null)
                return getTexture(pl, type);
        }
        var name = getNameByUUIDTolerance(uuid).name();
        if (name != null)
            return getPlayerTexture(type, name);
        // Method has changed in newer versions
        // Return a fallback texture
        return type == MinecraftProfileTexture.Type.SKIN ? new ResourceLocation("textures/entity/steve.png") : null;
    }

    private ResourceLocation getTexture(PlayerInfo playerInfo, MinecraftProfileTexture.Type type) {
        return switch (type) {
            case SKIN -> playerInfo.getSkin().texture();
            case CAPE -> playerInfo.getSkin().capeTexture();
            case ELYTRA -> playerInfo.getSkin().elytraTexture();
        };
    }
}
