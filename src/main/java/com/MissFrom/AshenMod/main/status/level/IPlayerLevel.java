package com.MissFrom.AshenMod.main.status.level;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;

@AutoRegisterCapability
public interface IPlayerLevel {
    // レベル
    int getLevel();
    void setLevel(int level);
    // 経験値
    int getExperience();
    void setExperience(int exp);
    void addExperience(int exp);
    // レベルアップ
    boolean canLevelUp();
    void levelUp();
    // データの永続化
    CompoundTag saveToNBT();
    void loadFromNBT(CompoundTag nbt);
}
