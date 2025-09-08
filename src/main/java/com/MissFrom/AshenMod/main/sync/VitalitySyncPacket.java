package com.MissFrom.AshenMod.main.sync;

import com.MissFrom.AshenMod.main.storage.ClientVitalityStorage;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class VitalitySyncPacket {
    private final int vitality;

    public VitalitySyncPacket(int vitality) {
        this.vitality = vitality;
    }

    public static void encode(VitalitySyncPacket pkt, FriendlyByteBuf buf) { buf.writeInt(pkt.vitality); }

    public static VitalitySyncPacket decode(FriendlyByteBuf buf) { return new VitalitySyncPacket(buf.readInt()); }

    public static void handle(VitalitySyncPacket pkt, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            // クライアント側でバイタリティ値を更新
            ClientVitalityStorage.setClientVitality(pkt.vitality);
        });
        ctx.get().setPacketHandled(true);
    }
}
