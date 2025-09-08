package com.MissFrom.AshenMod.main.storage;

public class ClientLevelStorage {
    private static int clientLevel = 1;
    private static int clientExperience = 0;
    private static int clientExpToNext = 100;

    public static void setClientLevel(int lvl) {
        clientLevel = lvl;
    }

    public static int getClientLevel() {
        return clientLevel;
    }

    public static void setClientExperience(int exp) {
        clientExperience = exp;
    }

    public static int getClientExperience() {
        return clientExperience;
    }

    public static void setClientExpToNext(int expToNext) {
        clientExpToNext = expToNext;
    }

    public static int getClientExpToNextLevel() {
        return clientExpToNext;
    }
}