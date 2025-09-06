package com.MissFrom.AshenMod.main.status.strength;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public class StrengthUtils {

    public static void addStrength(Player player, int amount) {
        player.getCapability(StrengthProvider.STRENGTH_CAPABILITY).ifPresent(cap -> {
            cap.addStrength(amount);
            if (player instanceof ServerPlayer serverPlayer) {
                syncAndUpdateInventory(serverPlayer);
            }
        });
    }

    public static int getStrength(Player player) {
        return player.getCapability(StrengthProvider.STRENGTH_CAPABILITY)
                .map(IStrength::getStrength)
                .orElse(0);
    }

    public static int getMaxInventorySlots(Player player) {
        return player.getCapability(StrengthProvider.STRENGTH_CAPABILITY)
                .map(IStrength::getMaxInventorySlots)
                .orElse(3);
    }

    private static void syncAndUpdateInventory(ServerPlayer player) {
        // クライアントへのパケット送信とインベントリGUI更新
    }
}
