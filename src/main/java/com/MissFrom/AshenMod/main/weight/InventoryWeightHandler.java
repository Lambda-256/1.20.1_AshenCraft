package com.MissFrom.AshenMod.main.weight;

import com.MissFrom.AshenMod.main.AshenMod;
import com.MissFrom.AshenMod.main.status.strength.StrengthHandler;
import com.MissFrom.AshenMod.main.sync.WeightSyncPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;

@Mod.EventBusSubscriber(modid = AshenMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class InventoryWeightHandler {

    @SubscribeEvent
    public static void onItemPickup(EntityItemPickupEvent event) {
        // 同期はティックで行うため特に何もしない
    }

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent event) {
        if (event.phase != PlayerTickEvent.Phase.END) return;
        if (!(event.player instanceof ServerPlayer player)) return;

        double current = getCurrentWeight(player);
        double max = StrengthHandler.getMaxWeight(player);

        // クライアントへ重量同期パケットを送信
        WeightSyncPacket.CHANNEL.send(
                PacketDistributor.PLAYER.with(() -> player),
                new WeightSyncPacket(current, max)
        );
    }

    public static double getCurrentWeight(ServerPlayer player) {
        double sum = 0.0;
        for (var stack : player.getInventory().items) {
            if (!stack.isEmpty()) {
                sum += ItemWeightCalculator.calculateWeight(stack);
            }
        }
        return sum;
    }
}
