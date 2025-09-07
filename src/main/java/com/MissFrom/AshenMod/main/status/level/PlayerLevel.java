package com.MissFrom.AshenMod.main.status.level;

import net.minecraft.nbt.CompoundTag;

public class PlayerLevel implements IPlayerLevel {
    private static final int MAX_LEVEL = 120;
    private int level = 1;
    private int experience = 0;
    private int experienceToNextLevel = calculateExpForNextLevel();

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
    public int getExpToNextLevel() {
        return experienceToNextLevel;
    }

    @Override
    public void setExperience(int exp) {
        this.experience = exp;
    }

    @Override
    public void addExperience(int exp) {
        this.experience += exp;
    }

    @Override
    public boolean canLevelUp() {
        // 最大レベル未満の場合、レベルアップ可能
        return level < MAX_LEVEL && experience >= experienceToNextLevel;
    }

    @Override
    public void levelUp() {
        if (!canLevelUp()) return;
        // レベルアップに必要な経験値を消費
        experience -= experienceToNextLevel;
        level++;
        // 次レベルに必要な経験値を再計算
        experienceToNextLevel = calculateExpForNextLevel();
    }

    private int calculateExpForNextLevel() {
        // 最大レベル未満は次回レベルの必要経験値を返す
        return level < MAX_LEVEL
                ? 100 + level * 20
                : Integer.MAX_VALUE;
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