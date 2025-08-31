package org.modsauce.otyacraftenginerenewed.util;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.modsauce.otyacraftenginerenewed.server.level.TagSerializable;

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * NBT関係のユーティリティ
 *
 * @author MORIMORI0317
 */
public final class OENbtUtils {
  /**
   * UUIDのTAG番号
   */
  public static final int TAG_UUID = Tag.TAG_INT_ARRAY;

  /**
   * NBTにリストを書き込む
   *
   * @param tag    NBTタグ
   * @param name   リストの名前
   * @param list   リスト
   * @param writer 書き込み方法
   * @param <T>    リストの種類
   * @return 書き込み済みNBT
   */
  @NotNull
  public static <T> CompoundTag writeList(@NotNull CompoundTag tag, @NotNull String name, @Nullable List<T> list, @NotNull Function<T, Tag> writer) {
    if (list != null) {
      ListTag listTag = new ListTag();
      list.forEach(n -> listTag.add(writer.apply(n)));
      tag.put(name, listTag);
    }
    return tag;
  }

  /**
   * NBTにUUIDのリストを書き込む
   *
   * @param tag   NBTタグ
   * @param name  リストの名前
   * @param uuids UUIDリスト
   * @return 書き込み済みNBT
   */
  @NotNull
  public static CompoundTag writeUUIDList(@NotNull CompoundTag tag, @NotNull String name, @Nullable List<UUID> uuids) {
    return writeList(tag, name, uuids, NbtUtils::createUUID);
  }

  /**
   * NBTからリストを読み込む
   *
   * @param tag    NBTタグ
   * @param name   リストの名前
   * @param list   書き込むリスト
   * @param reader 読み込み方法
   * @param num    TAG番号
   * @param <T>    リストの種類
   * @return 書き込み済みリスト
   */
  @NotNull
  public static <T> List<T> readList(@NotNull CompoundTag tag, @NotNull String name, @Nullable List<T> list, @NotNull Function<Tag, T> reader, int num) {
    if (list != null) list.clear();
    else list = new ArrayList<>();

    if (tag.contains(name, Tag.TAG_LIST)) {
      ListTag listTag = tag.getList(name, num);
      for (Tag lstag : listTag) {
        list.add(reader.apply(lstag));
      }
    }

    return list;
  }

  /**
   * NBTからUUIDリストを読み込む
   *
   * @param tag   NBTタグ
   * @param name  リストの名前
   * @param uuids UUIDリスト
   * @return 書き込み済みリスト
   */
  @NotNull
  public static List<UUID> readUUIDList(@NotNull CompoundTag tag, @NotNull String name, @Nullable List<UUID> uuids) {
    return readList(tag, name, uuids, NbtUtils::loadUUID, TAG_UUID);
  }

  /**
   * NBTにTagSerializableを実装した値のリストを書き込む
   *
   * @param tag              NBTタグ
   * @param name             リストの名前
   * @param serializableList TagSerializableを実装した値リスト
   * @param <T>              TagSerializableを実装した値
   * @return 書き込み済みNBT
   */
  @NotNull
  public static <T extends TagSerializable> CompoundTag writeSerializableList(@NotNull CompoundTag tag, @NotNull String name, @Nullable List<T> serializableList) {
    return writeList(tag, name, serializableList, TagSerializable::createSavedTag);
  }

  /**
   * NBTからTagSerializableを実装した値のリストを読み込む
   *
   * @param tag              NBTタグ
   * @param name             リストの名前
   * @param serializableList TagSerializableを実装した値リスト
   * @param initSerializable TagSerializableを実装した値の初期
   * @param <T>              TagSerializableを実装した値
   * @return 書き込み済みリスト
   */
  @NotNull
  public static <T extends TagSerializable> List<T> readSerializableList(@NotNull CompoundTag tag, @NotNull String name, @Nullable List<T> serializableList, @NotNull Supplier<T> initSerializable) {
    return readList(tag, name, serializableList, tag1 -> TagSerializable.loadSavedTag((CompoundTag) tag1, initSerializable.get()), Tag.TAG_COMPOUND);
  }

  /**
   * NBTにマップを書き込む
   *
   * @param tag         NBTタグ
   * @param name        マップの名前
   * @param map         マップ
   * @param keyWriter   キーの書き込み方法
   * @param valueWriter 値の書き込み方法
   * @param <T>         キー
   * @param <M>         値
   * @return 書き込み済みNBT
   */
  @NotNull
  public static <T, M> CompoundTag writeMap(@NotNull CompoundTag tag, @NotNull String name, @Nullable Map<T, M> map, @NotNull Function<T, Tag> keyWriter, @NotNull Function<M, Tag> valueWriter) {
    if (map != null) {
      List<T> key = new ArrayList<>(map.keySet());
      List<M> value = new ArrayList<>(map.values());
      var mt = new CompoundTag();

      writeList(mt, "k", key, keyWriter);
      writeList(mt, "v", value, valueWriter);
      tag.put(name, mt);
    }
    return tag;
  }

  /**
   * NBTからマップを読み込む
   *
   * @param tag         NBTタグ
   * @param name        マップの名前
   * @param map         マップ
   * @param keyReader   キーの読み込み方法
   * @param valueReader 値の読み込み方法
   * @param keyNum      キーのTAG番号
   * @param valueNum    値のTAG番号
   * @param <T>         キー
   * @param <M>         値
   * @return 書き込み済みマップ
   */
  @NotNull
  public static <T, M> Map<T, M> readMap(@NotNull CompoundTag tag, @NotNull String name, @Nullable Map<T, M> map, @NotNull Function<Tag, T> keyReader, @NotNull Function<Tag, M> valueReader, int keyNum, int valueNum) {
    if (map != null) map.clear();
    else map = new HashMap<>();
    var mt = tag.getCompound(name);
    var kr = readList(mt, "k", null, keyReader, keyNum);
    var vr = readList(mt, "v", null, valueReader, valueNum);
    if (kr.size() != vr.size()) throw new IllegalArgumentException("The count of key and value do not match.");

    for (int i = 0; i < kr.size(); i++) {
      var k = kr.get(i);
      var v = vr.get(i);
      map.put(k, v);
    }
    return map;
  }

  /**
   * NBTにUUIDがキーのマップを書き込む
   *
   * @param tag         NBTタグ
   * @param name        マップの名前
   * @param map         マップ
   * @param valueWriter 値の書き込み方法
   * @param <T>         値
   * @return 書き込み済みNBT
   */
  @NotNull
  public static <T> CompoundTag writeUUIDKeyMap(@NotNull CompoundTag tag, @NotNull String name, @Nullable Map<UUID, T> map, @NotNull Function<T, Tag> valueWriter) {
    return writeMap(tag, name, map, NbtUtils::createUUID, valueWriter);
  }

  /**
   * NBTからUUIDがキーのマップを読み込む
   *
   * @param tag         NBTタグ
   * @param name        マップの名前
   * @param map         マップ
   * @param valueReader 値の読み込み方法
   * @param valueNum    値のTAG番号
   * @param <T>         値
   * @return 読み込み済みマップ
   */
  @NotNull
  public static <T> Map<UUID, T> readUUIDKeyMap(@NotNull CompoundTag tag, @NotNull String name, @Nullable Map<UUID, T> map, @NotNull Function<Tag, T> valueReader, int valueNum) {
    return readMap(tag, name, map, NbtUtils::loadUUID, valueReader, TAG_UUID, valueNum);
  }

  /**
   * NBTにUUIDマップを書き込む
   *
   * @param tag  NBTタグ
   * @param name マップの名前
   * @param map  マップ
   * @return 書き込み済みNBT
   */
  @NotNull
  public static CompoundTag writeUUIDMap(@NotNull CompoundTag tag, @NotNull String name, @Nullable Map<UUID, UUID> map) {
    return writeUUIDKeyMap(tag, name, map, NbtUtils::createUUID);
  }

  /**
   * NBTからUUIDマップを読み込む
   *
   * @param tag  NBTタグ
   * @param name マップの名前
   * @param map  マップ
   * @return 読み込み済みマップ
   */
  @NotNull
  public static Map<UUID, UUID> readUUIDMap(@NotNull CompoundTag tag, @NotNull String name, @Nullable Map<UUID, UUID> map) {
    return readUUIDKeyMap(tag, name, map, NbtUtils::loadUUID, TAG_UUID);
  }


  @NotNull
  public static CompoundTag writeUUIDTagMap(@NotNull CompoundTag tag, @NotNull String name, @Nullable Map<UUID, CompoundTag> map) {
    return writeUUIDKeyMap(tag, name, map, tag1 -> tag1);
  }

  @NotNull
  public static Map<UUID, CompoundTag> readUUIDTagMap(@NotNull CompoundTag tag, @NotNull String name, @Nullable Map<UUID, CompoundTag> map) {
    return readUUIDKeyMap(tag, name, map, tag1 -> (CompoundTag) tag1, Tag.TAG_COMPOUND);
  }

  /**
   * NBTにUUIDがキーでTagSerializableを実装した値のマップを書き込む
   *
   * @param tag  NBTタグ
   * @param name マップの名前
   * @param map  マップ
   * @param <T>  TagSerializableを実装した値
   * @return 書き込み済みNBT
   */
  @NotNull
  public static <T extends TagSerializable> CompoundTag writeUUIDSerializableMap(@NotNull CompoundTag tag, @NotNull String name, @Nullable Map<UUID, T> map) {
    return writeUUIDKeyMap(tag, name, map, TagSerializable::createSavedTag);
  }

  /**
   * NBTからUUIDがキーでTagSerializableを実装した値のマップを読み込む
   *
   * @param tag              NBTタグ
   * @param name             マップの名前
   * @param map              マップ
   * @param initSerializable TagSerializableを実装した値の初期
   * @param <T>              TagSerializableを実装した値
   * @return 読み込み済みマップ
   */
  @NotNull
  public static <T extends TagSerializable> Map<UUID, T> readUUIDSerializableMap(@NotNull CompoundTag tag, @NotNull String name, @Nullable Map<UUID, T> map, @NotNull Supplier<T> initSerializable) {
    return readUUIDKeyMap(tag, name, map, tag1 -> TagSerializable.loadSavedTag((CompoundTag) tag1, initSerializable.get()), Tag.TAG_COMPOUND);
  }

  /**
   * 順序数で列挙型をNBTタグに書き込む
   * MODのバージョン違いなどの影響で列挙型の数や配置が違う場合に別の値になってしまう可能性があります
   * 通信など列挙型の数や配置が完全に一致することを保障できる場合に利用を推奨
   * ワールドデータの保存などMODバージョンが違って読み込む可能性がある場合は {@link #writeEnum(CompoundTag, String, Enum)} を利用してください
   *
   * @param tag   NBTタグ
   * @param name  保存名
   * @param enum_ 列挙型
   * @return 書き込み済みNBT
   */
  @NotNull
  public static CompoundTag writeEnumByOrdinal(@NotNull CompoundTag tag, @NotNull String name, @NotNull Enum<?> enum_) {
    tag.putInt(name, enum_.ordinal());
    return tag;
  }

  /**
   * 順序数で列挙型をNBTタグから読み込む
   * MODのバージョン違いなどの影響で列挙型の数や配置が違う場合に別の値になってしまう可能性があります
   * 通信など列挙型の数や配置が完全に一致することを保障できる場合に利用を推奨
   * ワールドデータの保存などMODバージョンが違って読み込む可能性がある場合は
   *
   * @param tag         NBTタグ
   * @param name        保存名
   * @param enumClass   列挙型クラス
   * @param defaultEnum デフォルト列挙型
   * @param <T>         列挙型
   * @return 読み込まれた列挙型
   */
  public static <T extends Enum<T>> T readEnumByOrdinal(@NotNull CompoundTag tag, @NotNull String name, @NotNull Class<T> enumClass, @Nullable T defaultEnum) {
    var ens = ((T[]) enumClass.getEnumConstants());
    int num = tag.getInt(name);
    if (num < 0 || ens.length <= num) return defaultEnum;
    return ens[num];
  }

  /**
   * シリアル名で列挙型をNBTタグに書き込む
   *
   * @param tag   NBTタグ
   * @param name  保存名
   * @param enum_ 列挙型
   * @return 書き込み済みNBT
   */
  @NotNull
  public static CompoundTag writeEnum(@NotNull CompoundTag tag, @NotNull String name, @NotNull Enum<? extends StringRepresentable> enum_) {
    tag.putString(name, ((StringRepresentable) (enum_)).getSerializedName());
    return tag;
  }

  /**
   * シリアル名で列挙型をNBTタグから読み込む
   *
   * @param tag         NBTタグ
   * @param name        保存名
   * @param enumClass   列挙型クラス
   * @param defaultEnum デフォルト列挙型
   * @param <T>         列挙型
   * @return 読み込まれた列挙型
   */
  public static <T extends Enum<T> & StringRepresentable> T readEnum(@NotNull CompoundTag tag, @NotNull String name, @NotNull Class<T> enumClass, @Nullable T defaultEnum) {
    var ens = ((T[]) enumClass.getEnumConstants());
    var n = tag.getString(name);
    for (T en : ens) {
      if (en.getSerializedName().equals(n))
        return en;
    }
    return defaultEnum;
  }


}
