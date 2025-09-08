package com.MissFrom.AshenMod.main.storage;

public class ClientTechniqueStorage {
    private static int clientTechnique = 1;

    public static void setClientTechnique(int technique) {
        clientTechnique = technique;
    }

    public static int getClientTechnique() {
        return clientTechnique;
    }
}
