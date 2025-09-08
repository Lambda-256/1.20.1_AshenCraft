package com.MissFrom.AshenMod.main.sync;

import com.MissFrom.AshenMod.main.storage.ClientTechniqueStorage;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class TechniqueSyncPacket {
    private final int technique;

    public TechniqueSyncPacket(int technique) {
        this.technique = technique;
    }

    public static void encode(TechniqueSyncPacket pkt, FriendlyByteBuf buf) {
        buf.writeInt(pkt.technique);
    }

    public static TechniqueSyncPacket decode(FriendlyByteBuf buf) {
        return new TechniqueSyncPacket(buf.readInt());
    }

    public static void handle(TechniqueSyncPacket pkt, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            // クライアント側で技術値を更新
            ClientTechniqueStorage.setClientTechnique(pkt.technique);
        });
        ctx.get().setPacketHandled(true);
    }
}
