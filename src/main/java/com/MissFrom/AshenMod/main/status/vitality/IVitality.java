package com.MissFrom.AshenMod.main.status.vitality;

import net.minecraft.nbt.CompoundTag;

public interface IVitality {
    // 生命力
    int getVitality();
    void setVitality(int vitality);
    void addVitality(int amount);
    // ハート数

    // データ永続化
    CompoundTag saveToNBT();
    void loadFromNBT(CompoundTag nbt);
}
