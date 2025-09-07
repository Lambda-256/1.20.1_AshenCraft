package com.MissFrom.AshenMod.main.keyhandler;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import com.MissFrom.AshenMod.main.AshenMod;

public class KeyInputHandler {
    public static final KeyMapping OPEN_LEVEL_GUI = new KeyMapping(
            "key.ashenmod.open_level_gui",
            InputConstants.Type.KEYSYM,
            InputConstants.KEY_L,
            "key.categories.ashenmod"
    );

    // MODイベントバスでキー登録
    @Mod.EventBusSubscriber(modid = AshenMod.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModEvents {
        @SubscribeEvent
        public static void registerKeyMappings(RegisterKeyMappingsEvent event) {
            event.register(OPEN_LEVEL_GUI);
        }
    }
}
