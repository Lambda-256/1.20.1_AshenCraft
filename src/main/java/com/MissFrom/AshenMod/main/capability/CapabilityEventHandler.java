package com.MissFrom.AshenMod.main.capability;

import com.MissFrom.AshenMod.main.status.vitality.VitalityProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import com.MissFrom.AshenMod.main.AshenMod;
import com.MissFrom.AshenMod.main.status.level.PlayerLevelProvider;
import com.MissFrom.AshenMod.main.status.strength.StrengthProvider;

@Mod.EventBusSubscriber(modid = AshenMod.MOD_ID)
public class CapabilityEventHandler {

    @SubscribeEvent
    public static void attachCapability(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player) {
            // 既存のプレイヤーレベルCapability
            event.addCapability(
                    new ResourceLocation(AshenMod.MOD_ID, "player_level"),
                    new PlayerLevelProvider()
            );

            // 生命力Capability
            event.addCapability(
                    new ResourceLocation(AshenMod.MOD_ID, "player_vitality"),
                    new VitalityProvider()
            );

            // 新しい筋力値Capability
            event.addCapability(
                    new ResourceLocation(AshenMod.MOD_ID, "player_strength"),
                    new StrengthProvider()
            );
        }
    }

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        if (event.isWasDeath()) {
            // 既存のプレイヤーレベルデータの継承
            event.getOriginal().getCapability(PlayerLevelProvider.PLAYER_LEVEL).ifPresent(oldCap -> {
                event.getEntity().getCapability(PlayerLevelProvider.PLAYER_LEVEL).ifPresent(newCap -> {
                    newCap.loadFromNBT(oldCap.saveToNBT());
                });
            });

            // 生命力値データの継承
            event.getOriginal().getCapability(VitalityProvider.VITALITY_CAPABILITY).ifPresent(oldCap -> {
                event.getEntity().getCapability(VitalityProvider.VITALITY_CAPABILITY).ifPresent(newCap -> {
                    newCap.loadFromNBT(oldCap.saveToNBT());
                });
            });

            // 筋力値データの継承
            event.getOriginal().getCapability(StrengthProvider.STRENGTH_CAPABILITY).ifPresent(oldCap -> {
                event.getEntity().getCapability(StrengthProvider.STRENGTH_CAPABILITY).ifPresent(newCap -> {
                    newCap.loadFromNBT(oldCap.saveToNBT());
                });
            });
        }
    }
}


