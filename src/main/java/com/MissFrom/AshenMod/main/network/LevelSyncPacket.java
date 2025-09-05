package com.MissFrom.AshenMod.main.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import java.util.function.Supplier;

public class LevelSyncPacket {
    private final int level;

    public LevelSyncPacket(int level) {
        this.level = level;
    }

    public static void encode(LevelSyncPacket pkt, FriendlyByteBuf buf) {
        buf.writeInt(pkt.level);
    }

    public static LevelSyncPacket decode(FriendlyByteBuf buf) {
        return new LevelSyncPacket(buf.readInt());
    }

    public static void handle(LevelSyncPacket pkt, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ClientLevelStorage.setClientLevel(pkt.level);
        });
        ctx.get().setPacketHandled(true);
    }
}
