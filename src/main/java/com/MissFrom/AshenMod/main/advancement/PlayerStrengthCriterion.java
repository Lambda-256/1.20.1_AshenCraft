package com.MissFrom.AshenMod.main.advancement;

import com.MissFrom.AshenMod.main.AshenMod;
import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.GsonHelper;

public class PlayerStrengthCriterion extends SimpleCriterionTrigger<PlayerStrengthCriterion.TriggerInstance> {

    public static final ResourceLocation ID = new ResourceLocation(AshenMod.MOD_ID, "player_strength_level");

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    public void trigger(ServerPlayer player, int strength) {
        this.trigger(player, instance -> instance.matches(strength));
    }

    @Override
    protected TriggerInstance createInstance(JsonObject json, ContextAwarePredicate predicate, DeserializationContext context) {
        int level = GsonHelper.getAsInt(json, "level", 0);
        return new TriggerInstance(predicate, level);
    }

    public static class TriggerInstance extends AbstractCriterionTriggerInstance {
        private final int requiredLevel;

        public TriggerInstance(ContextAwarePredicate predicate, int requiredLevel) {
            super(PlayerStrengthCriterion.ID, predicate);
            this.requiredLevel = requiredLevel;
        }

        public boolean matches(int currentStrength) {
            return currentStrength >= this.requiredLevel;
        }
    }
}
