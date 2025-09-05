package com.MissFrom.AshenMod.main;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;

@Mod("ashenmod")
public class AshenMod {

    public static final String MOD_ID = "ashenmod";

    public AshenMod(){
        MinecraftForge.EVENT_BUS.register(Status.class);
    }
}
