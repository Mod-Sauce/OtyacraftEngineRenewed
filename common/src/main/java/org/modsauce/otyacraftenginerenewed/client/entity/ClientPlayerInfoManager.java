package org.modsauce.otyacraftenginerenewed.client.entity;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.core.UUIDUtil;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.modsauce.otyacraftenginerenewed.util.OEPlayerUtils;

public class ClientPlayerInfoManager {

  private static final ClientPlayerInfoManager INSTANCE =
    new ClientPlayerInfoManager();
  private static final Minecraft mc = Minecraft.getInstance();
  private final Map<String, GameProfile> PLAYER_PROFILES = new HashMap<>();
  private final Map<String, PlayerUUIDByNameResult> UUID_BY_NAME_ENTRY =
    new HashMap<>();
  private final Map<UUID, PlayerNameByUUIDResult> NAME_BY_UUID_ENTRY =
    new HashMap<>();

  public static ClientPlayerInfoManager getInstance() {
    return INSTANCE;
  }

  public void clear() {
    synchronized (PLAYER_PROFILES) {
      PLAYER_PROFILES.clear();
    }
  }

  @NotNull
  public GameProfile getLackProfileTolerance(@NotNull String name) {
    synchronized (PLAYER_PROFILES) {
      if (PLAYER_PROFILES.containsKey(name)) return PLAYER_PROFILES.get(name);
      var gp = new GameProfile(null, name);
      PLAYER_PROFILES.put(name, gp);
      // TODO: Fix updateGameprofile API for MC 1.21.1
      // SkullBlockEntity.updateGameprofile method signature changed
      CompletableFuture.supplyAsync(() -> {
        // Placeholder for proper profile resolution
        return gp;
      }).thenAccept(p -> {
        synchronized (PLAYER_PROFILES) {
          PLAYER_PROFILES.put(name, p);
        }
      });
      return gp;
    }
  }

  @NotNull
  public Optional<UUID> getUUIDByName(@NotNull String name) {
    var cr = getUUIDByNameClient(name);
    if (cr != null) return Optional.of(cr);
    return OEPlayerUtils.getUUIDByName(name);
  }

  @NotNull
  public CompletableFuture<Optional<UUID>> getUUIDByNameAsync(
    @NotNull String name
  ) {
    var cr = getUUIDByNameClient(name);
    if (cr != null) return CompletableFuture.completedFuture(Optional.of(cr));
    return OEPlayerUtils.getUUIDByNameAsync(name);
  }

  private UUID getUUIDByNameClient(String name) {
    if (mc.player != null) {
      if (mc.player.getGameProfile().getName().equals(name)) return mc.player
        .getGameProfile()
        .getId();

      var pl = mc.player.connection.getPlayerInfo(name);
      if (pl != null && pl.getProfile() != null) return pl.getProfile().getId();
    }
    return null;
  }

  @NotNull
  public Optional<String> getNameByUUID(@NotNull UUID uuid) {
    var cr = getNameByUUIDClient(uuid);
    if (cr != null) return Optional.of(cr);
    return OEPlayerUtils.getNameByUUID(uuid);
  }

  @NotNull
  public CompletableFuture<Optional<String>> getNameByUUIDAsync(
    @NotNull UUID uuid
  ) {
    var cr = getNameByUUIDClient(uuid);
    if (cr != null) return CompletableFuture.completedFuture(Optional.of(cr));
    return OEPlayerUtils.getNameByUUIDAsync(uuid);
  }

  private String getNameByUUIDClient(UUID uuid) {
    if (mc.player != null) {
      if (mc.player.getGameProfile().getId().equals(uuid)) return mc.player
        .getGameProfile()
        .getName();

      var pl = mc.player.connection.getPlayerInfo(uuid);
      if (pl != null && pl.getProfile() != null) return pl
        .getProfile()
        .getName();
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
            UUID_BY_NAME_ENTRY.put(
              name,
              new PlayerUUIDByNameResult(uuid.orElse(null), false)
            );
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
            NAME_BY_UUID_ENTRY.put(
              uuid,
              new PlayerNameByUUIDResult(name.orElse(null), false)
            );
          }
        });
      }
      return ret;
    }
  }

  @Nullable
  public ResourceLocation getPlayerTexture(
    @NotNull MinecraftProfileTexture.Type type,
    @NotNull String name
  ) {
    if (mc.player != null) {
      var pl = mc.player.connection.getPlayerInfo(name);
      if (pl != null) return getTexture(pl, type);
    }

    // For offline players or when profile resolution fails,
    // return default skin based on player name
    return type == MinecraftProfileTexture.Type.SKIN
      ? DefaultPlayerSkin.get(UUIDUtil.createOfflinePlayerUUID(name)).texture()
      : null;
  }

  @Nullable
  public ResourceLocation getPlayerTexture(
    @NotNull MinecraftProfileTexture.Type type,
    @NotNull UUID uuid
  ) {
    if (mc.player != null) {
      var pl = mc.player.connection.getPlayerInfo(uuid);
      if (pl != null) return getTexture(pl, type);
    }
    var name = getNameByUUIDTolerance(uuid).name();
    if (name != null) return getPlayerTexture(type, name);
    return type == MinecraftProfileTexture.Type.SKIN
      ? DefaultPlayerSkin.get(uuid).texture()
      : null;
  }

  private ResourceLocation getTexture(
    PlayerInfo playerInfo,
    MinecraftProfileTexture.Type type
  ) {
    return switch (type) {
      case SKIN -> playerInfo.getSkin().texture();
      case CAPE -> playerInfo.getSkin().capeTexture();
      case ELYTRA -> playerInfo.getSkin().elytraTexture();
    };
  }
}
