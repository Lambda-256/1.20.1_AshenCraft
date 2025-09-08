package com.MissFrom.AshenMod.main.status;

public enum StatType {
    VITALITY("生命力"),
    ENDURANCE("持久力"),
    STRENGTH("筋力"),
    SKILL("技量"),
    BLOODTINGE("血質"),
    ARCANE("神秘");

    private final String displayName;

    StatType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
