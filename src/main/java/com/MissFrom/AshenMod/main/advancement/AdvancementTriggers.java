package com.MissFrom.AshenMod.main.advancement;

import net.minecraft.advancements.CriteriaTriggers;

public class AdvancementTriggers {
    public static final PlayerVitalityCriterion VITALITY_TRIGGER = new PlayerVitalityCriterion();
    public static final PlayerStrengthCriterion STRENGTH_TRIGGER = new PlayerStrengthCriterion();

    public static void register() {
        CriteriaTriggers.register(VITALITY_TRIGGER);
        CriteriaTriggers.register(STRENGTH_TRIGGER);
    }
}
