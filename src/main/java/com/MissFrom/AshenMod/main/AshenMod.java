package com.MissFrom.AshenMod.main;

import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import com.MissFrom.AshenMod.main.capability.CapabilityEventHandler;
import org.lwjgl.glfw.GLFW;

@Mod("ashenmod")
@Mod.EventBusSubscriber(modid = "ashenmod", value = Dist.CLIENT)

public class AshenMod {

    public static final String MOD_ID = "ashenmod";

    private static final KeyMapping STATUS_KEY = new KeyMapping(
            "key.ashenmod.status",
            GLFW.GLFW_KEY_O, // Oキーで開く
            "key.categories.inventory"
    );

    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key event) {
        if (STATUS_KEY.isDown()) {
            Minecraft mc = Minecraft.getInstance();
            if (mc.player != null) {
                mc.setScreen(new Status(mc.player));
            }
        }
    }

    public AshenMod(){
        // Capabilityイベントを登録
        MinecraftForge.EVENT_BUS.register(CapabilityEventHandler.class);
    }
}
