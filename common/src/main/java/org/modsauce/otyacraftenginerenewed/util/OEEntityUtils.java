package org.modsauce.otyacraftenginerenewed.util;

import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;
import org.modsauce.otyacraftenginerenewed.explatform.OEExpectPlatform;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * エンティテ関係のユーティリティ
 *
 * @author MORIMORI0317
 */
public final class OEEntityUtils {
  /**
   * エンティティタイプからタグのストリームを取得
   *
   * @param entityType エンティティタイプ
   * @return タグストリーム
   */
  @NotNull
  public static Stream<TagKey<EntityType<?>>> getTags(@NotNull EntityType<?> entityType) {
    return OEExpectPlatform.getTags(entityType);
  }

  /**
   * エンティティの腕からメインハンドかオフハンドか取得する
   *
   * @param entity 対象エンティティ
   * @param arm    腕
   * @return メインハンドもしくはオフハンド
   */
  @NotNull
  public static InteractionHand getHandByArm(@NotNull LivingEntity entity, @NotNull HumanoidArm arm) {
    Objects.requireNonNull(arm);
    return entity.getMainArm() == arm ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND;
  }

  /**
   * エンティティのメインハンドもしくはオフハンドから腕を取得
   *
   * @param entity 対象エンティティ
   * @param hand   メインハンドかオフハンド
   * @return 腕
   */
  @NotNull
  public static HumanoidArm getArmByHand(@NotNull LivingEntity entity, @NotNull InteractionHand hand) {
    Objects.requireNonNull(hand);
    return hand == InteractionHand.MAIN_HAND ? entity.getMainArm() : entity.getMainArm().getOpposite();
  }

  /**
   * メインハンドもしくはオフハンドの逆側を取得
   *
   * @param hand メインハンドもしくはオフハンド
   * @return 逆
   */
  @NotNull
  public static InteractionHand getOppositeHand(@NotNull InteractionHand hand) {
    Objects.requireNonNull(hand);
    return hand == InteractionHand.MAIN_HAND ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND;
  }
}
