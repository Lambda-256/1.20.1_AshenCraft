package com.MissFrom.AshenMod.main;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.client.gui.screens.Screen;

public class Status extends Screen {
    private final Player player;

    public Status(Player player) {
        super(Component.literal("ステータス画面"));
        this.player = player;
    }

    @Override
    public void render(GuiGraphics gui, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(gui);
        super.render(gui, mouseX, mouseY, partialTick);

        Font font = Minecraft.getInstance().font;

        // --- ステータス取得 ---
        float health = player.getHealth();
        float maxHealth = player.getMaxHealth();
        int level = player.experienceLevel;
        int armor = player.getArmorValue();
        int food = player.getFoodData().getFoodLevel();
        float saturation = player.getFoodData().getSaturationLevel();

        double attack = 0.0;
        if (player.getAttribute(Attributes.ATTACK_DAMAGE) != null) {
            attack = player.getAttribute(Attributes.ATTACK_DAMAGE).getValue();
        }

        // インベントリ使用スロット数計算
        int usedSlots = 0;
        for (ItemStack stack : player.getInventory().items) {
            if (!stack.isEmpty()) usedSlots++;
        }
        int totalSlots = player.getInventory().items.size(); // 36
        int emptySlots = totalSlots - usedSlots;

        // --- 描画位置 ---
        int x = this.width / 2 - 80;
        int y = this.height / 4;

        // --- ステータス描画(デモ) ---
        gui.drawString(font, "=== ステータス ===", x, y, 0xFFFFFF, false);
        gui.drawString(font, "HP: " + (int)health + " / " + (int)maxHealth, x, y + 20, 0xFF5555, false);
        gui.drawString(font, "Armor: " + armor, x, y + 35, 0x55AAFF, false);
        gui.drawString(font, "Attack: " + String.format("%.1f", attack), x, y + 50, 0xFFAA00, false);
        gui.drawString(font, "Food: " + food + "  Sat: " + String.format("%.1f", saturation), x, y + 65, 0x55FF55, false);
        gui.drawString(font, "EnchantLv: " + level, x, y + 80, 0xFFFF55, false);
        gui.drawString(font, "Inventory: " + usedSlots + " / " + totalSlots, x, y + 95, 0xFFFFFF, false);

    }

    @Override
    public boolean isPauseScreen() {
        return false; // 開いてもゲームが動き続ける
    }
}
