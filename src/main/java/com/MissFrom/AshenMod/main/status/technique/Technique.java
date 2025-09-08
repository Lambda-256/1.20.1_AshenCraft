package com.MissFrom.AshenMod.main.status.technique;

import net.minecraft.nbt.CompoundTag;

public class Technique implements ITechnique{
    private int technique = 1;

    @Override
    public int getTechnique() {
        return technique;
    }

    @Override
    public void setTechnique(int technique) {
        this.technique = Math.max(0, technique);
    }


    @Override
    public void addTechnique(int amount) {
        setTechnique(technique + amount);
    }

    @Override
    public CompoundTag saveToNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putInt("technique", technique);
        return tag;
    }

    @Override
    public void loadFromNBT(CompoundTag nbt) {
        technique = nbt.getInt("technique");
    }
}
