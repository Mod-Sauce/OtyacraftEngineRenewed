package org.modsauce.otyacraftenginerenewed.client.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
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
   * @param resourceLocation ロケーション
   * @return モデル
   */
  public static BakedModel getModel(ResourceLocation resourceLocation) {
    return OEClientExpectPlatform.getModel(resourceLocation);
  }

  /**
   * モデルリソースロケーションからモデルを取得
   *
   * @param location モデルリソースロケーション
   * @return モデル
   */
  public static BakedModel getModelByMRL(ModelResourceLocation location) {
    return mc.getModelManager().getModel(location);
  }

  /**
   * ブロックステートからモデルを取得
   *
   * @param state ブロックステート
   * @return モデル
   */
  public static BakedModel getModel(BlockState state) {
    return mc.getModelManager().getBlockModelShaper().getBlockModel(state);
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
      return playerInfo.getSkin().model() == PlayerSkin.Model.SLIM;
    }
    return false;
  }
}
