package com.MissFrom.AshenMod.main.network;

import com.MissFrom.AshenMod.main.advancement.AdvancementTriggers;
import com.MissFrom.AshenMod.main.status.StatType;
import com.MissFrom.AshenMod.main.status.level.PlayerLevelProvider;
import com.MissFrom.AshenMod.main.status.strength.IStrength;
import com.MissFrom.AshenMod.main.status.strength.StrengthProvider;
import com.MissFrom.AshenMod.main.status.vitality.IVitality;
import com.MissFrom.AshenMod.main.status.vitality.VitalityHandler;
import com.MissFrom.AshenMod.main.status.vitality.VitalityProvider;
import com.MissFrom.AshenMod.main.sync.LevelSyncPacket;
import com.MissFrom.AshenMod.main.sync.StrengthSyncPacket;
import com.MissFrom.AshenMod.main.sync.VitalitySyncPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.PacketDistributor;

import java.util.function.Supplier;

public class StatUpRequestPacket {
    private final StatType stat;

    public StatUpRequestPacket(StatType stat) { this.stat = stat; }

    public static void encode(StatUpRequestPacket pkt, FriendlyByteBuf buf) {
        buf.writeEnum(pkt.stat);
    }

    public static StatUpRequestPacket decode(FriendlyByteBuf buf) {
        return new StatUpRequestPacket(buf.readEnum(StatType.class));
    }

    public static void handle(StatUpRequestPacket pkt, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();
            player.getCapability(PlayerLevelProvider.PLAYER_LEVEL).ifPresent(cap -> {
                // 経験値が足りない場合は無視
                if (!cap.canLevelUp()) return;

                // ステータス上限チェック
                boolean canUpgrade = false;
                if (pkt.stat == StatType.STRENGTH) {
                    canUpgrade = player.getCapability(StrengthProvider.STRENGTH_CAPABILITY)
                            .map(str -> str.getStrength() < 99)
                            .orElse(false);
                } else if (pkt.stat == StatType.VITALITY) {
                    canUpgrade = player.getCapability(VitalityProvider.VITALITY_CAPABILITY)
                            .map(vit -> vit.getVitality() < 99)
                            .orElse(false);
                }

                // ステータスが上限に達している場合は無視
                if (!canUpgrade) return;

                // 経験値消費・レベルアップ
                cap.levelUp();

                // TODO: 他ステータスは後で追加
                // 筋力のみ割当（既存のStrengthProviderを使用）
                if (pkt.stat == StatType.STRENGTH) {
                    player.getCapability(StrengthProvider.STRENGTH_CAPABILITY)
                            .ifPresent(str -> {
                                str.addStrength(1);
                                // 筋力ステータスに応じて攻撃力上昇
//                                StrengthHandler.onStatUpgrade(player, StatType.STRENGTH);
                                // 実績トリガー発火
                                AdvancementTriggers.STRENGTH_TRIGGER.trigger(player, str.getStrength());
                            });
                } else if (pkt.stat == StatType.VITALITY) {
                    player.getCapability(VitalityProvider.VITALITY_CAPABILITY)
                            .ifPresent(str -> {
                                str.addVitality(1);
                                VitalityHandler.onStatUpgrade(player, StatType.VITALITY);
                                // 実績トリガー発火（修正版）
                                AdvancementTriggers.VITALITY_TRIGGER.trigger(player, str.getVitality());
                            });
                }

                // 同期：レベル・EXP・ステータス
                NetworkHandler.CHANNEL.send(
                        PacketDistributor.PLAYER.with(() -> player),
                        new LevelSyncPacket(
                                cap.getLevel(), cap.getExperience(), cap.getExpToNextLevel()
                        )
                );

                // 生命力値の同期
                NetworkHandler.CHANNEL.send(
                        PacketDistributor.PLAYER.with(() -> player),
                        new VitalitySyncPacket(
                                player.getCapability(VitalityProvider.VITALITY_CAPABILITY)
                                        .map(IVitality::getVitality).orElse(1)
                        )
                );

                // 筋力値の同期
                NetworkHandler.CHANNEL.send(
                        PacketDistributor.PLAYER.with(() -> player),
                        new StrengthSyncPacket(
                                player.getCapability(StrengthProvider.STRENGTH_CAPABILITY)
                                        .map(IStrength::getStrength).orElse(1)
                        )
                );

                // TODO: 他ステータスは後で追加


            });
        });
        ctx.get().setPacketHandled(true);
    }
}

