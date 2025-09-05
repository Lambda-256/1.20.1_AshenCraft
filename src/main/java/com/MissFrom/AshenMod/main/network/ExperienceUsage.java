package com.MissFrom.AshenMod.main.network;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.PacketDistributor;
import com.MissFrom.AshenMod.main.status.level.PlayerLevelProvider;

public class ExperienceUsage {
    /**
     * プレイヤーに経験値を付与し、レベルアップ条件を手動判定、通知するサンプルメソッド。
     *
     * @param player 経験値を付与する対象のサーバープレイヤー
     * @param exp    付与する経験値量
     */
    public void grantExpAndNotify(ServerPlayer player, int exp) {
        player.getCapability(PlayerLevelProvider.PLAYER_LEVEL).ifPresent(cap -> {
            // 経験値を追加（自動レベルアップ処理は不要ならremove loop版にする）
            cap.addExperience(exp);

            // 手動でレベルアップ判定・実行
            if (cap.canLevelUp()) {
                cap.levelUp();
                NetworkHandler.CHANNEL.send(
                        PacketDistributor.PLAYER.with(() -> player),
                        new LevelSyncPacket(cap.getLevel())
                );
                player.sendSystemMessage(
                        Component.literal("レベルアップ！ 現在のレベル: " + cap.getLevel())
                );
            } else {
                // レベルアップしない場合は現在の経験値状況を通知
                player.sendSystemMessage(
                        Component.literal("経験値を " + exp + " 追加しました (現在EXP: " + cap.getExperience() + ")")
                );
            }
        });
    }
}
