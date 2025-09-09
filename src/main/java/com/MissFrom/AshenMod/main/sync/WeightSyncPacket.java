package com.MissFrom.AshenMod.main.sync;

import com.MissFrom.AshenMod.main.storage.ClientWeightStorage;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Supplier;

public class WeightSyncPacket {
    private final double currentWeight;
    private final double maxWeight;

    public WeightSyncPacket(double currentWeight, double maxWeight) {
        this.currentWeight = currentWeight;
        this.maxWeight     = maxWeight;
    }

    public static void encode(WeightSyncPacket pkt, FriendlyByteBuf buf) {
        buf.writeDouble(pkt.currentWeight);
        buf.writeDouble(pkt.maxWeight);
    }

    public static WeightSyncPacket decode(FriendlyByteBuf buf) {
        return new WeightSyncPacket(buf.readDouble(), buf.readDouble());
    }

    public static void handle(WeightSyncPacket pkt, Supplier<NetworkEvent.Context> ctx) {
        // CLIENTサイドで実行
        ctx.get().enqueueWork(() -> {
            ClientWeightStorage.setCurrentWeight(pkt.currentWeight);
            ClientWeightStorage.setMaxWeight(pkt.maxWeight);
        });
        ctx.get().setPacketHandled(true);
    }

    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            new ResourceLocation("ashenmod", "weight_sync"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );
    private static int id = 0;

    public static void register() {
        CHANNEL.registerMessage(
                id++,
                WeightSyncPacket.class,
                WeightSyncPacket::encode,
                WeightSyncPacket::decode,
                WeightSyncPacket::handle
        );
    }
}