package com.MissFrom.AshenMod.main.network;

public class ClientLevelStorage {
    private static int clientLevel = 1;
    public static void setClientLevel(int lvl) { clientLevel = lvl; }
    public static int getClientLevel() { return clientLevel; }
}