package com.MissFrom.AshenMod.main.capability;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import com.MissFrom.AshenMod.main.AshenMod;
import com.MissFrom.AshenMod.main.status.level.PlayerLevelProvider;

@Mod.EventBusSubscriber(modid = AshenMod.MOD_ID)
public class CapabilityEventHandler {

    @SubscribeEvent
    public static void attachCapability(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player) {
            event.addCapability(
                    // TODO: 現状v1.20.1対応のためエラーそのままでよさそう？
                    new ResourceLocation(AshenMod.MOD_ID, "player_level"),
                    new PlayerLevelProvider()
            );
        }
    }

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        if (event.isWasDeath()) {
            event.getOriginal().getCapability(PlayerLevelProvider.PLAYER_LEVEL).ifPresent(oldCap -> {
                event.getEntity().getCapability(PlayerLevelProvider.PLAYER_LEVEL).ifPresent(newCap -> {
                    newCap.loadFromNBT(oldCap.saveToNBT());
                });
            });
        }
    }
}


