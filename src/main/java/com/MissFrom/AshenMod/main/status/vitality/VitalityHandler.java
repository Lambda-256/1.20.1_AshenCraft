package com.MissFrom.AshenMod.main.status.vitality;

import com.MissFrom.AshenMod.main.AshenMod;
import com.MissFrom.AshenMod.main.skill.passive.PassiveSkillManager;
import com.MissFrom.AshenMod.main.status.StatType;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.UUID;

@Mod.EventBusSubscriber(modid = AshenMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class VitalityHandler {
    public static final UUID VITALITY_MODIFIER_UUID = UUID.fromString("abcd1234-1111-2222-3333-444455556666");

    /**
     * 生命力からハート数を計算（段階的線形補間）
     * 生命力1→4個, 30→30個, 50→40個, 99→60個
     */
    private static double calculateHearts(int vitality) {
        if (vitality <= 1) {
            return 4.0;
        } else if (vitality <= 30) {
            // 1-30区間: 4から30への線形補間
            double ratio = (vitality - 1.0) / (30.0 - 1.0);
            return 4.0 + (30.0 - 4.0) * ratio;
        } else if (vitality <= 50) {
            // 30-50区間: 30から40への線形補間
            double ratio = (vitality - 30.0) / (50.0 - 30.0);
            return 30.0 + (40.0 - 30.0) * ratio;
        } else {
            // 50-99区間: 40から60への線形補間
            double ratio = (vitality - 50.0) / (99.0 - 50.0);
            return 40.0 + (60.0 - 40.0) * ratio;
        }
    }

    /**
     * プレイヤーのHPを生命力に応じて更新
     */
    public static void updatePlayerHealth(ServerPlayer player) {
        player.getCapability(VitalityProvider.VITALITY_CAPABILITY).ifPresent(v -> {
            int vitality = v.getVitality();
            double hearts = calculateHearts(vitality);
            double targetMaxHealth = hearts * 2.0; // 1ハート = 2HP

            var attr = player.getAttribute(Attributes.MAX_HEALTH);
            if (attr == null) return;

            // 既存のVitalityModifierを除去して新規追加
            attr.removeModifier(VITALITY_MODIFIER_UUID);
            AttributeModifier mod = new AttributeModifier(
                    VITALITY_MODIFIER_UUID,
                    "vitality_health_bonus",
                    targetMaxHealth - attr.getBaseValue(),
                    AttributeModifier.Operation.ADDITION
            );
            attr.addPermanentModifier(mod);

            // 現在HPが最大を超えないようにクリップ
            if (player.getHealth() > targetMaxHealth) {
                player.setHealth((float) targetMaxHealth);
            }
        });
    }

    /**
     * ログイン時にHPを設定
     */
    @SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;
        updatePlayerHealth(player);
    }

    /**
     * ステータス振り直し後にHPを再設定（パケットハンドラ内から呼び出し）
     */
    public static void onStatUpgrade(ServerPlayer player, StatType statType) {
        if (statType == StatType.VITALITY) {
            updatePlayerHealth(player);
            // パッシブスキルチェック
            PassiveSkillManager.checkAndApplyPassives(player);
        }
    }
}