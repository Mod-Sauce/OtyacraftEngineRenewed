package org.modsauce.otyacraftenginerenewed.server.level;

import net.minecraft.nbt.CompoundTag;

/**
 * NBTタグに保存やNBTタグから読み込むことができる実装
 *
 * @author MORIMORI0317
 */
public interface TagSerializable {
  void save(CompoundTag tag);

  void load(CompoundTag tag);

  default CompoundTag createSavedTag() {
    var t = new CompoundTag();
    save(t);
    return t;
  }

  static <T extends TagSerializable> T loadSavedTag(CompoundTag tag, T serializable) {
    serializable.load(tag);
    return serializable;
  }
}
