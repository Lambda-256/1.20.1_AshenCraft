package com.MissFrom.AshenMod.main;

import com.MissFrom.AshenMod.main.advancement.AdvancementTriggers;
import com.MissFrom.AshenMod.main.sync.WeightSyncPacket;
import net.minecraftforge.api.distmarker.Dist;
import com.MissFrom.AshenMod.main.status.level.KillExpEventHandler;
import com.MissFrom.AshenMod.main.sync.PlayerLoginSyncHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import com.MissFrom.AshenMod.main.capability.CapabilityEventHandler;
import com.MissFrom.AshenMod.main.network.NetworkHandler;

@Mod("ashenmod")
@Mod.EventBusSubscriber(modid = "ashenmod", value = Dist.CLIENT)

public class AshenMod {

    public static final String MOD_ID = "ashenmod";

    public AshenMod(){
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(this::commonSetup);
        // Capabilityイベントを登録
        MinecraftForge.EVENT_BUS.register(CapabilityEventHandler.class);
        MinecraftForge.EVENT_BUS.register(KillExpEventHandler.class);
        MinecraftForge.EVENT_BUS.register(PlayerLoginSyncHandler.class);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            NetworkHandler.register();
            AdvancementTriggers.register();
            WeightSyncPacket.register();
        });
    }
}
