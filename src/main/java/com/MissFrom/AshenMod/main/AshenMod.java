package com.MissFrom.AshenMod.main;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import com.MissFrom.AshenMod.main.capability.CapabilityEventHandler;

@Mod("ashenmod")
public class AshenMod {

    public static final String MOD_ID = "ashenmod";

    public AshenMod(){
        // Capabilityイベントを登録
        MinecraftForge.EVENT_BUS.register(CapabilityEventHandler.class);
    }
}
