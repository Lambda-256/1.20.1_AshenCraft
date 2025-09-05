package com.MissFrom.AshenMod.main;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class Status {

    @SubscribeEvent
    public static void onRenderOverlay(RenderGuiOverlayEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        if (player == null) return;

        GuiGraphics gui = event.getGuiGraphics();
        Font font = mc.font;

        // --- プレイヤーステータス取得 ---
        float health = player.getHealth();
        float maxHealth = player.getMaxHealth();
        int level = player.experienceLevel;
        int armor = player.getArmorValue();
        double attack = 0.0;
        int food = player.getFoodData().getFoodLevel();
        float saturation = player.getFoodData().getSaturationLevel();

        if (player.getAttribute(Attributes.ATTACK_DAMAGE) != null) {
            // TODO; getValue NULLの可能性あり
            attack = player.getAttribute(Attributes.ATTACK_DAMAGE).getValue();
        }

        // --- 描画位置 ---
        int x = 10;
        int y = 10;

        // --- ステータス描画 ---
        gui.drawString(font, "HP: " + (int)health + "/" + (int)maxHealth, x, y, 0xFFFF5555, true);
        gui.drawString(font, "Level: " + level, x, y + 10, 0xFFFFFFFF, true);
        gui.drawString(font, "Armor: " + armor, x, y + 20, 0xFFAAAAFF, true);
        gui.drawString(font, "Attack: " + String.format("%.1f", attack), x, y + 30, 0xFFFFAA00, true);
        gui.drawString(font, "Food: " + food + "  Sat: " + String.format("%.1f", saturation), x, y + 40, 0xFF55FF55, true);
    }
}
