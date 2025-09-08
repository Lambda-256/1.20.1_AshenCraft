package com.MissFrom.AshenMod.main.status.technique;

import net.minecraft.nbt.CompoundTag;

public interface ITechnique {

        // 技術
        int getTechnique();
        void setTechnique(int technique);
        void addTechnique(int amount);

        // データの永続化
        CompoundTag saveToNBT();
        void loadFromNBT(CompoundTag nbt);
}
