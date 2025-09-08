package com.MissFrom.AshenMod.main.storage;

public class ClientVitalityStorage {
    private static int clientVitality = 1;

    public static void setClientVitality(int vitality) { clientVitality = vitality; }

    public static int getClientVitality() { return clientVitality; }
}
