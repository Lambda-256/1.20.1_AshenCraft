package com.MissFrom.AshenMod.main.storage;

public class ClientWeightStorage {
    private static double currentWeight = 0.0;
    private static double maxWeight = 0.0;

    /** クライアント側に受信した現在重量を設定 */
    public static void setCurrentWeight(double weight) {
        currentWeight = weight;
    }

    /** クライアント側に受信した最大重量を設定 */
    public static void setMaxWeight(double weight) {
        maxWeight = weight;
    }

    /** 現在重量を取得 */
    public static double getCurrentWeight() {
        return currentWeight;
    }

    /** 最大重量を取得 */
    public static double getMaxWeight() {
        return maxWeight;
    }
}
