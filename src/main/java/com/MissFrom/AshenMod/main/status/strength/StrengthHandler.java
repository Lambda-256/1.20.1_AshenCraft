package com.MissFrom.AshenMod.main.status.strength;

import com.MissFrom.AshenMod.main.AshenMod;
import com.MissFrom.AshenMod.main.status.StatType;
import com.MissFrom.AshenMod.main.weight.InventoryWeightHandler;
import com.MissFrom.AshenMod.main.weight.ItemWeightCalculator;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ServerboundPlayerCommandPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.UUID;

@Mod.EventBusSubscriber(modid = AshenMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class StrengthHandler {
    private static final UUID OVERLOAD_SPEED_UUID = UUID.fromString("e3a5f1f0-1234-4abc-9f1e-abcdef123456");
    private static final AttributeModifier OVERLOAD_SPEED_MOD =
            new AttributeModifier(OVERLOAD_SPEED_UUID, "overload_speed", -0.4, AttributeModifier.Operation.MULTIPLY_TOTAL);
    private static final double BASE_CAPACITY  = 2.2;

    /** プレイヤーの最大積載量 */
    public static double getMaxWeight(ServerPlayer player) {
        return player.getCapability(StrengthProvider.STRENGTH_CAPABILITY)
                .map(str -> str.getStrength() * BASE_CAPACITY)
                .orElse(BASE_CAPACITY);
    }

    /**
     * 筋力ステータス変更時の処理
     */
    public static void onStatUpgrade(ServerPlayer player, StatType statType) {
        // 筋力ステータスに応じて攻撃力上昇
//        if (statType == StatType.STRENGTH) {
//
//        }
    }

    /**
     * 毎ティック、重量オーバーなら移動速度を低下
     */
    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.PlayerTickEvent.Phase.START) return;
        if (!(event.player instanceof ServerPlayer player)) return;

        double current = InventoryWeightHandler.getCurrentWeight(player);
        double max     = getMaxWeight(player);
        boolean overloaded = current > max;

        var speedAttr = player.getAttribute(Attributes.MOVEMENT_SPEED);
        if (overloaded) {
            if (speedAttr.getModifier(OVERLOAD_SPEED_UUID) == null) {
                speedAttr.addTransientModifier(OVERLOAD_SPEED_MOD);
            }
        } else {
            speedAttr.removeModifier(OVERLOAD_SPEED_UUID);
        }
    }

    /**
     * プレイヤーのインベントリ内重量を計算
     */
    private static double getCurrentWeight(ServerPlayer player) {
        double sum = 0.0;
        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            var stack = player.getInventory().getItem(i);
            if (!stack.isEmpty()) {
                sum += ItemWeightCalculator.calculateWeight(stack);
            }
        }
        return sum;
    }
}
