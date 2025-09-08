package com.MissFrom.AshenMod.main.status.vitality;

import net.minecraft.nbt.CompoundTag;

public class Vitality implements IVitality {
    private int vitality = 1;       // 初期値1
    public static final int MAX_VITALITY = 99;

    @Override
    public int getVitality() {
        return vitality;
    }

    @Override
    public void setVitality(int value) {
        this.vitality = Math.max(1, Math.min(MAX_VITALITY, value));
    }

    @Override
    public void addVitality(int amount) {
        setVitality(vitality + amount);
    }

    @Override
    public CompoundTag saveToNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putInt("Vitality", this.vitality);
        return tag;
    }

    @Override
    public void loadFromNBT(CompoundTag tag) {
        if (tag.contains("Vitality")) {
            this.vitality = tag.getInt("Vitality");
        }
    }
}

