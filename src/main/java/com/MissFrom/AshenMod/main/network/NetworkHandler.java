package com.MissFrom.AshenMod.main.network;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import com.MissFrom.AshenMod.main.AshenMod;

public class NetworkHandler {
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(AshenMod.MOD_ID, "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    private static int packetId = 0;
    private static int id() {
        return packetId++;
    }

    public static void register() {
        // レベル同期パケット（サーバー → クライアント）
        CHANNEL.messageBuilder(LevelSyncPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .encoder(LevelSyncPacket::encode)
                .decoder(LevelSyncPacket::decode)
                .consumerMainThread(LevelSyncPacket::handle)
                .add();

        // レベルアップ要求パケット（クライアント → サーバー）
        CHANNEL.messageBuilder(LevelUpRequestPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .encoder(LevelUpRequestPacket::encode)
                .decoder(LevelUpRequestPacket::decode)
                .consumerMainThread(LevelUpRequestPacket::handle)
                .add();
    }
}

