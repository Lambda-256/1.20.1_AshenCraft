package com.MissFrom.AshenMod.main.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import com.MissFrom.AshenMod.main.AshenMod;
import com.MissFrom.AshenMod.main.network.ClientLevelStorage;

@Mod.EventBusSubscriber(modid = AshenMod.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class LevelOverlayRenderer {

    @SubscribeEvent
    public static void onRenderPost(RenderGuiOverlayEvent.Post event) {
        if (event.getOverlay() != VanillaGuiOverlay.PLAYER_HEALTH.type()) return;

        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;
        if (player == null) return;

        // Capabilityのチェックを削除し、直接ClientLevelStorageを使用
        int level = ClientLevelStorage.getClientLevel();
        // Capabilityから現在の経験値と次レベルまでの必要経験値を取得
        int exp = ClientLevelStorage.getClientExperience();
        int expToNext = ClientLevelStorage.getClientExpToNextLevel();

        // GuiGraphicsから描画情報を取得
        GuiGraphics graphics = event.getGuiGraphics();
        Font font = mc.font;

        // スクリーンサイズを取得
        int screenWidth = mc.getWindow().getGuiScaledWidth();
        int screenHeight = mc.getWindow().getGuiScaledHeight();

        // ハート左横の位置を計算
        int x = 10;      // ハート左横のXオフセット
        int y = screenHeight - 39; // デフォルトのハートY位置

        // 描画（GuiGraphicsを使用）
        graphics.drawString(font, "Lv " + level, x, y, 0xFFFFFF);
        graphics.drawString(font,
                exp + " / " + expToNext + " EXP",
                x, y + font.lineHeight + 2,
                0xFFFF00  // 黄色
        );
    }
}


