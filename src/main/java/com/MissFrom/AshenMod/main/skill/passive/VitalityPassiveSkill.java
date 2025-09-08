package com.MissFrom.AshenMod.main.skill.passive;

import com.MissFrom.AshenMod.main.advancement.ModAdvancements;
import com.MissFrom.AshenMod.main.status.vitality.VitalityProvider;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

import java.util.UUID;

public class VitalityPassiveSkill implements IPassiveSkill {
    private static final int GOLDEN_HEARTS_DURATION = Integer.MAX_VALUE; // 永続的
    private static final int GOLDEN_HEARTS_AMPLIFIER = 2; // レベル2 = 12HP（金ハート6個）

    @Override
    public void apply(ServerPlayer player) {
        // 既存のAbsorptionエフェクトを除去
        player.removeEffect(MobEffects.ABSORPTION);

        // 新しいAbsorptionエフェクトを適用（金ハート5個分）
        MobEffectInstance absorptionEffect = new MobEffectInstance(
                MobEffects.ABSORPTION,
                GOLDEN_HEARTS_DURATION,
                GOLDEN_HEARTS_AMPLIFIER, // レベル5 = 10HP
                false, // 周囲にパーティクルを表示しない
                false  // HUDにアイコンを表示しない
        );
        player.addEffect(absorptionEffect);
    }

    @Override
    public void remove(ServerPlayer player) {
        player.removeEffect(MobEffects.ABSORPTION);
    }

    @Override
    public boolean canApply(ServerPlayer player) {
        return player.getCapability(VitalityProvider.VITALITY_CAPABILITY)
                .map(v -> v.getVitality() >= 50)
                .orElse(false);
    }

    @Override
    public String getSkillName() {
        return "vitality_golden_hearts";
    }
}
