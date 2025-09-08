package com.MissFrom.AshenMod.main.storage;

public class ClientStrengthStorage {
    private static int clientStrength = 1;

    public static void setClientStrength(int strength) {
        clientStrength = strength;
    }

    public static int getClientStrength() {
        return clientStrength;
    }
}
