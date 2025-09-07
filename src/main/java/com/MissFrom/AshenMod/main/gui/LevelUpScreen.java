package com.MissFrom.AshenMod.main.gui;

import com.MissFrom.AshenMod.main.network.*;
import com.MissFrom.AshenMod.main.status.StatType;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.network.PacketDistributor;

public class LevelUpScreen extends AbstractContainerScreen<LevelUpMenu> {
    public LevelUpScreen(LevelUpMenu menu, Inventory inv, Component title) {
        super(menu, inv, title);
        this.imageWidth = 176;
        this.imageHeight = 114;
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        // 背景のグレー矩形を描画
        renderBackground(guiGraphics);
        // パネル背景とウィジェット（ボタン等）を描画
        renderBg(guiGraphics, partialTick, mouseX, mouseY);
        for (var widget : this.renderables) {
            widget.render(guiGraphics, mouseX, mouseY, partialTick);
        }
        // ツールチップを描画
        renderTooltip(guiGraphics, mouseX, mouseY);

        // 経験値不足メッセージの表示
        int clientExp = ClientLevelStorage.getClientExperience();
        int clientExpToNext = ClientLevelStorage.getClientExpToNextLevel();
        if (clientExp < clientExpToNext) {
            int lineHeight = font.lineHeight + 6;
            int statStartY = topPos + 10 + lineHeight + 2;
            int messageY = statStartY + StatType.values().length * lineHeight + 5;
            // ボタン位置の下に赤いテキストで表示
            guiGraphics.drawString(font, "経験値が足りません",
                    leftPos + 10, messageY - 17, 0xFF5555);
        }
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        // 半透明パネル背景
        guiGraphics.fill(leftPos, topPos, leftPos + imageWidth, topPos + imageHeight, 0xCC000000);
        int baseX = leftPos + 10;
        int baseY = topPos + 10;
        int lineHeight = font.lineHeight + 4;
        int statCount = StatType.values().length;

        // レベルと経験値情報を最上部に表示
        int currentLevel = ClientLevelStorage.getClientLevel();
        guiGraphics.drawString(font, "レベル: " + currentLevel, baseX, baseY, 0xFFFF00);

        int currentExp = ClientLevelStorage.getClientExperience();
        int expToNext = ClientLevelStorage.getClientExpToNextLevel();
        String expText = currentExp + " / " + expToNext;
        int expTextWidth = font.width(expText);
        guiGraphics.drawString(font, expText,
                leftPos + imageWidth - 10 - expTextWidth, baseY, 0xFFFF00);

        // ステータス一覧開始Y
        int statStartY = baseY + lineHeight + 2;
        // ボーダーラインの色
        int lineColor = 0xFF777777;

        for (int i = 0; i < statCount; i++) {
            StatType stat = StatType.values()[i];
            int rowY = statStartY + i * lineHeight;

            // ボーダーライン
            guiGraphics.fill(leftPos + 5, rowY - 3, leftPos + imageWidth - 5, rowY - 2, lineColor);

            // ステータス名と値
            int value = (stat == StatType.STRENGTH)
                    ? ClientStrengthStorage.getClientStrength()
                    : 1;
            guiGraphics.drawString(font,
                    stat.getDisplayName() + ": " + value,
                    baseX, rowY - 1, 0xFFFFFF);
        }
    }

    @Override
    protected void init() {
        super.init();
        int x = leftPos, y = topPos;
        int lineHeight = font.lineHeight + 4;
        int statStartY = y + 10 + lineHeight + 2;
        StatType[] stats = StatType.values();

        for (int i = 0; i < stats.length; i++) {
            StatType stat = stats[i];
            int rowY = statStartY + i * lineHeight;

            // "+" ボタンを作成
            Button btn = Button.builder(
                            Component.literal("+"),
                            b -> NetworkHandler.CHANNEL.send(
                                    PacketDistributor.SERVER.noArg(),
                                    new StatUpRequestPacket(stat)
                            )
                    )
                    .bounds(x + 140, rowY - 2, 12, 12)  // Y調整で行と中央揃え
                    .build();

            // 筋力以外は非アクティブにする
            if (stat != StatType.STRENGTH) {
                btn.active = false;
            } else {
                // 筋力は経験値で判定
                int exp = ClientLevelStorage.getClientExperience();
                int expToNext = ClientLevelStorage.getClientExpToNextLevel();
                btn.active = exp >= expToNext;
            }
            addRenderableWidget(btn);
        }
    }
}
