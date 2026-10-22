package org.modsauce.otyacraftenginerenewed.client.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.PlayerModelType;
import net.minecraft.world.level.block.state.BlockState;
import org.modsauce.otyacraftenginerenewed.explatform.client.OEClientExpectPlatform;

/**
 * モデル関係のユーティリティ
 *
 * @author MORIMORI031c
 */
public final class OEModelUtils {

  private static final Minecraft mc = Minecraft.getInstance();

  /**
   * モデルを取得
   *
   * @param Identifier ロケーション
   * @return モデル
   */
  public static BlockModel getModel(Identifier identifier) {
    return OEClientExpectPlatform.getModel(identifier);
  }

  /**
   * モデルリソースロケーションからモデルを取得
   *
   * @param location モデルリソースロケーション
   * @return モデル
   */
  public static ItemModel getItemModelByMRL(Identifier identifier) {
    return mc.getModelManager().getItemModel(identifier);
  }

  /**
   * ブロックステートからモデルを取得
   *
   * @param state ブロックステート
   * @return モデル
   */
  public static BlockModel getModel(BlockState state) {
    return mc.getModelManager().getBlockModelSet().get(state);
  }

  /**
   * プレイヤーがスリムモデルかどうか
   *
   * @param player プレイヤー
   * @return スリムモデルかどうか
   */
  public static boolean isSlimPlayerModel(AbstractClientPlayer player) {
    // TODO: Fix getModelName API for MC 1.21.1
    // The model name API has changed - need to get from player info
    var playerInfo = Minecraft.getInstance()
      .getConnection()
      .getPlayerInfo(player.getUUID());
    if (playerInfo != null) {
      return playerInfo.getSkin().model() == PlayerModelType.SLIM;
    }
    return false;
  }
}
