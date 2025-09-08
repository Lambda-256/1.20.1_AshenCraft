package com.MissFrom.AshenMod.main.skill.passive;

import com.MissFrom.AshenMod.main.AshenMod;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber(modid = AshenMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class PassiveSkillManager {
    private static final Map<String, IPassiveSkill> SKILLS = new HashMap<>();

    static {
        registerSkill("vitality_golden_hearts", new VitalityPassiveSkill());
        // TODO: 他のステータスのパッシブスキルもここに追加
    }

    public static void registerSkill(String id, IPassiveSkill skill) {
        SKILLS.put(id, skill);
    }

    public static void checkAndApplyPassives(ServerPlayer player) {
        for (IPassiveSkill skill : SKILLS.values()) {
            if (skill.canApply(player)) {
                skill.apply(player);
            } else {
                skill.remove(player);
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            checkAndApplyPassives(player);
        }
    }
}
