package com.MissFrom.AshenMod.main.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class StrengthSyncPacket {
    private final int strength;

    public StrengthSyncPacket(int strength) {
        this.strength = strength;
    }

    public static void encode(StrengthSyncPacket pkt, FriendlyByteBuf buf) {
        buf.writeInt(pkt.strength);
    }

    public static StrengthSyncPacket decode(FriendlyByteBuf buf) {
        return new StrengthSyncPacket(buf.readInt());
    }

    public static void handle(StrengthSyncPacket pkt, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            // クライアント側でストレングス値を更新
            ClientStrengthStorage.setClientStrength(pkt.strength);
        });
        ctx.get().setPacketHandled(true);
    }
}
