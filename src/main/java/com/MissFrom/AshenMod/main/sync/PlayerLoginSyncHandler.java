package com.MissFrom.AshenMod.main.sync;

import com.MissFrom.AshenMod.main.network.StrengthSyncPacket;
import com.MissFrom.AshenMod.main.status.level.PlayerLevelProvider;
import com.MissFrom.AshenMod.main.status.strength.StrengthProvider;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import com.MissFrom.AshenMod.main.AshenMod;
import com.MissFrom.AshenMod.main.network.NetworkHandler;
import com.MissFrom.AshenMod.main.network.LevelSyncPacket;
import net.minecraftforge.network.PacketDistributor;

@Mod.EventBusSubscriber(modid = AshenMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class PlayerLoginSyncHandler {

    @SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        ServerPlayer player = (ServerPlayer) event.getEntity();

        // レベル同期
        player.getCapability(PlayerLevelProvider.PLAYER_LEVEL).ifPresent(cap -> {
            NetworkHandler.CHANNEL.send(
                    PacketDistributor.PLAYER.with(() -> player),
                    new LevelSyncPacket(
                            cap.getLevel(),
                            cap.getExperience(),
                            cap.getExpToNextLevel()
                    )
            );
        });

        // 筋力同期
        player.getCapability(StrengthProvider.STRENGTH_CAPABILITY).ifPresent(cap -> {
            NetworkHandler.CHANNEL.send(
                    PacketDistributor.PLAYER.with(() -> player),
                    new StrengthSyncPacket(cap.getStrength())
            );
        });
    }
}

