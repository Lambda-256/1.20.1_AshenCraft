package com.MissFrom.AshenMod.main.status.strength;

import net.minecraft.nbt.CompoundTag;

public class Strength implements IStrength {
    private int strength = 1;
    private static final int BASE_SLOTS = 3;
    private static final int SLOTS_PER_STRENGTH = 2;
    private static final int MAX_SLOTS = 36;

    @Override
    public int getStrength() {
        return strength;
    }

    @Override
    public void setStrength(int strength) {
        this.strength = Math.max(0, strength);
    }

    @Override
    public int getMaxInventorySlots() {
        int totalSlots = BASE_SLOTS + (strength * SLOTS_PER_STRENGTH);
        return Math.min(totalSlots, MAX_SLOTS);
    }

    @Override
    public void addStrength(int amount) {
        setStrength(strength + amount);
    }

    @Override
    public CompoundTag saveToNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putInt("strength", strength);
        return tag;
    }

    @Override
    public void loadFromNBT(CompoundTag nbt) {
        strength = nbt.getInt("strength");
    }
}
