package com.MissFrom.AshenMod.main.sync;

import com.MissFrom.AshenMod.main.storage.ClientLevelStorage;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import java.util.function.Supplier;

public class LevelSyncPacket {
    private final int level;
    private final int experience;
    private final int expToNext;

    public LevelSyncPacket(int level, int experience, int expToNext) {
        this.level = level;
        this.experience = experience;
        this.expToNext = expToNext;
    }

    public static void encode(LevelSyncPacket pkt, FriendlyByteBuf buf) {
        buf.writeInt(pkt.level);
        buf.writeInt(pkt.experience);
        buf.writeInt(pkt.expToNext);
    }

    public static LevelSyncPacket decode(FriendlyByteBuf buf) {
        return new LevelSyncPacket(buf.readInt(), buf.readInt(), buf.readInt());
    }

    public static void handle(LevelSyncPacket pkt, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ClientLevelStorage.setClientLevel(pkt.level);
            ClientLevelStorage.setClientExperience(pkt.experience);
            ClientLevelStorage.setClientExpToNext(pkt.expToNext);
        });
        ctx.get().setPacketHandled(true);
    }
}
