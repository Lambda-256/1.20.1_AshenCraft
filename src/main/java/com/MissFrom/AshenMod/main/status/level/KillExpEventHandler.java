package com.MissFrom.AshenMod.main.status.level;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import com.MissFrom.AshenMod.main.AshenMod;
import com.MissFrom.AshenMod.main.network.ExperienceUsage;

@Mod.EventBusSubscriber(modid = AshenMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class KillExpEventHandler {

    // 敵を倒した直後に呼ばれる
    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {
        LivingEntity dead = event.getEntity();
        if (!(event.getSource().getEntity() instanceof Player player)) return;

        // 倒した相手がプレイヤー自身でないかチェック
        if (dead == player) return;

        // 経験値量を決めるs
        int expGained = 10;

        // 経験値付与＆通知
        new ExperienceUsage().grantExpAndNotify((ServerPlayer) player, expGained);
    }
}

