package com.MissFrom.AshenMod.main.advancement;

import com.MissFrom.AshenMod.main.AshenMod;
import net.minecraft.advancements.Advancement;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public class ModAdvancements {
    public static void grantAdvancement(ServerPlayer player, String advancementId) {
        ResourceLocation location = new ResourceLocation(AshenMod.MOD_ID, advancementId);
        Advancement advancement = player.server.getAdvancements().getAdvancement(location);

        if (advancement != null) {
            player.getAdvancements().award(advancement, "requirement");
        }
    }
}
