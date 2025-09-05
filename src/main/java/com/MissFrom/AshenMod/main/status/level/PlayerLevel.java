package com.MissFrom.AshenMod.main.status.level;

import net.minecraft.nbt.CompoundTag;

public class PlayerLevel implements IPlayerLevel {
    private int level = 1;
    private int experience = 0;
    private int experienceToNextLevel = 100;

    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public int getExperience() {
        return experience;
    }

    @Override
    public void setExperience(int exp) {
        this.experience = exp;
    }

    @Override
    public void addExperience(int exp) {
        this.experience += exp;
        // 重複コメントアウト
//        while (canLevelUp()) {
//            levelUp();
//        }
    }

    @Override
    public boolean canLevelUp() {
        return experience >= experienceToNextLevel;
    }

    @Override
    public void levelUp() {
        level++;
        experience -= experienceToNextLevel;
        experienceToNextLevel = calculateExpForNextLevel();
    }

    private int calculateExpForNextLevel() {
        return 100 + level * 20;
    }

    @Override
    public CompoundTag saveToNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putInt("Level", level);
        tag.putInt("Experience", experience);
        tag.putInt("ExpToNext", experienceToNextLevel);
        return tag;
    }

    @Override
    public void loadFromNBT(CompoundTag nbt) {
        level = nbt.getInt("Level");
        experience = nbt.getInt("Experience");
        experienceToNextLevel = nbt.getInt("ExpToNext");
    }
}