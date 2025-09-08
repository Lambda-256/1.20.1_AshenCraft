package com.MissFrom.AshenMod.main.status.strength;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;

public interface IStrength extends INBTSerializable<CompoundTag> {
    // 筋力
    int getStrength();
    void setStrength(int strength);
    void addStrength(int amount);
    // インベントリ数
    int getMaxInventorySlots();
}
