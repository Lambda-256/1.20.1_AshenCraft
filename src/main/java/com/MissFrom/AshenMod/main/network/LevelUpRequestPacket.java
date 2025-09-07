package com.MissFrom.AshenMod.main.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import com.MissFrom.AshenMod.main.status.level.PlayerLevelProvider;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.PacketDistributor;
import java.util.function.Supplier;

public class LevelUpRequestPacket {
    public LevelUpRequestPacket() {}

    public static void encode(LevelUpRequestPacket pkt, FriendlyByteBuf buf) {}
    public static LevelUpRequestPacket decode(FriendlyByteBuf buf) { return new LevelUpRequestPacket(); }

    public static void handle(LevelUpRequestPacket pkt, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();
            player.getCapability(PlayerLevelProvider.PLAYER_LEVEL).ifPresent(cap -> {
                if (!cap.canLevelUp()) {
                    // 足りない場合は無視
                    return;
                }
                cap.levelUp();
                NetworkHandler.CHANNEL.send(
                        PacketDistributor.PLAYER.with(() -> player),
                        new LevelSyncPacket(
                                cap.getLevel(),
                                cap.getExperience(),
                                cap.getExpToNextLevel()
                        )
                );
            });
        });
        ctx.get().setPacketHandled(true);
    }
}
