package com.MissFrom.AshenMod.main.gui;

import com.MissFrom.AshenMod.main.network.ClientLevelStorage;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import com.MissFrom.AshenMod.main.network.NetworkHandler;
import com.MissFrom.AshenMod.main.network.LevelUpRequestPacket;
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
        // 背景を暗くする
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
            // ボタン位置の下に赤いテキストで表示
            guiGraphics.drawString(
                    font,
                    "経験値が足りません",
                    leftPos + 60,
                    topPos + 50 + 22,
                    0xFF5555
            );
        }
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        // レベル／EXPを取得して文字だけ描画
        guiGraphics.fill(leftPos, topPos, leftPos + imageWidth, topPos + imageHeight, 0xFF555555);
        int level = ClientLevelStorage.getClientLevel();
        int exp = ClientLevelStorage.getClientExperience();
        int expToNext = ClientLevelStorage.getClientExpToNextLevel();
        guiGraphics.drawString(font, "Lv: " + level, leftPos + 10, topPos + 10, 0xFFFFFF);
        guiGraphics.drawString(font, exp + " / " + expToNext + " EXP", leftPos + 10, topPos + 25, 0xFFFF00);
    }

    @Override
    protected void init() {
        super.init();

        // クライアント同期済みストレージから値を取得
        int clientExp = ClientLevelStorage.getClientExperience();
        int clientExpToNext = ClientLevelStorage.getClientExpToNextLevel();

        // レベルアップ可能かを判定
        boolean canLevel = clientExp >= clientExpToNext;

        // ボタン作成
        Button levelBtn = Button.builder(
                        Component.literal("Level Up"),
                        btn -> NetworkHandler.CHANNEL.send(
                                PacketDistributor.SERVER.noArg(),
                                new LevelUpRequestPacket()
                        )
                )
                .bounds(leftPos + 60, topPos + 50, 56, 20)
                .build();

        // 経験値不足時は非アクティブにする
        levelBtn.active = canLevel;

        // ウィジェットとして追加
        this.addRenderableWidget(levelBtn);
    }
}
