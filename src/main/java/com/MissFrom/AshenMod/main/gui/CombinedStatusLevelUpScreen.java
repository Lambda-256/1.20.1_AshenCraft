package com.MissFrom.AshenMod.main.gui;

import com.MissFrom.AshenMod.main.storage.ClientLevelStorage;
import com.MissFrom.AshenMod.main.storage.ClientStrengthStorage;
import com.MissFrom.AshenMod.main.network.NetworkHandler;
import com.MissFrom.AshenMod.main.network.StatUpRequestPacket;
import com.MissFrom.AshenMod.main.status.StatType;
import com.MissFrom.AshenMod.main.storage.ClientVitalityStorage;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.PacketDistributor;

public class CombinedStatusLevelUpScreen extends AbstractContainerScreen<LevelUpMenu> {
    private final Player player;

    public CombinedStatusLevelUpScreen(LevelUpMenu menu, Inventory inv, Component title, Player player) {
        super(menu, inv, title);
        this.player = player;
        this.imageWidth = 350; // 幅を拡大して左右レイアウト対応
        this.imageHeight = 200; // 高さも調整
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        // 背景のグレー矩形を描画
        renderBackground(guiGraphics);

        // パネル背景とウィジェット（ボタン等）を描画
        renderBg(guiGraphics, partialTick, mouseX, mouseY);

        // 右側にステータス情報を描画
        renderStatusInfo(guiGraphics);

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
            guiGraphics.drawString(font, "経験値が足りません",
                    leftPos + 10, messageY - 17, 0xFF5555);
        }
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        // 左側：ステ振りパネル
        int leftPanelWidth = 176;
        guiGraphics.fill(leftPos, topPos, leftPos + leftPanelWidth, topPos + imageHeight, 0xCC000000);

        // 右側：ステータス表示パネル
        int rightPanelX = leftPos + leftPanelWidth + 5;
        int rightPanelWidth = imageWidth - leftPanelWidth - 5;
        guiGraphics.fill(rightPanelX, topPos, rightPanelX + rightPanelWidth, topPos + imageHeight, 0xCC000000);

        // 左側パネル：ステ振り系描画
        renderLevelUpPanel(guiGraphics);
    }

    private void renderLevelUpPanel(GuiGraphics guiGraphics) {
        int baseX = leftPos + 10;
        int baseY = topPos + 10;
        int lineHeight = font.lineHeight + 4;
        int statCount = StatType.values().length;

        // レベルと経験値情報
        int currentLevel = ClientLevelStorage.getClientLevel();
        guiGraphics.drawString(font, "レベル: " + currentLevel, baseX, baseY, 0xFFFF00);

        int currentExp = ClientLevelStorage.getClientExperience();
        int expToNext = ClientLevelStorage.getClientExpToNextLevel();
        String expText = currentExp + " / " + expToNext;
        int expTextWidth = font.width(expText);
        guiGraphics.drawString(font, expText,
                leftPos + 176 - 10 - expTextWidth, baseY, 0xFFFF00);

        // ステータス一覧
        int statStartY = baseY + lineHeight + 2;
        int lineColor = 0xFF777777;

        for (int i = 0; i < statCount; i++) {
            StatType stat = StatType.values()[i];
            int rowY = statStartY + i * lineHeight;

            // ボーダーライン
            guiGraphics.fill(leftPos + 5, rowY - 3, leftPos + 176 - 5, rowY - 2, lineColor);

            // ステータス名と値
            // TODO: ステータス追加
            int value = (stat == StatType.VITALITY)
                    ? ClientVitalityStorage.getClientVitality()
                    : (stat == StatType.STRENGTH)
                    ? ClientStrengthStorage.getClientStrength()
                    : 1;
            guiGraphics.drawString(font,
                    stat.getDisplayName() + ": " + value,
                    baseX, rowY - 1, 0xFFFFFF);
        }
    }

    private void renderStatusInfo(GuiGraphics guiGraphics) {
        // 右側パネルの座標計算
        int rightPanelX = leftPos + 176 + 15; // 左パネル幅 + マージン
        int rightPanelY = topPos + 20;

        // ステータス取得（元のStatusクラスから移植）
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
        int totalSlots = player.getInventory().items.size();

        // ステータス描画
        guiGraphics.drawString(font, "=== ステータス ===", rightPanelX, rightPanelY, 0xFFFFFF, false);
        guiGraphics.drawString(font, "HP: " + (int)health + " / " + (int)maxHealth,
                rightPanelX, rightPanelY + 20, 0xFF5555, false);
        guiGraphics.drawString(font, "Armor: " + armor,
                rightPanelX, rightPanelY + 35, 0x55AAFF, false);
        guiGraphics.drawString(font, "Attack: " + String.format("%.1f", attack),
                rightPanelX, rightPanelY + 50, 0xFFAA00, false);
        guiGraphics.drawString(font, "Food: " + food + "  Sat: " + String.format("%.1f", saturation),
                rightPanelX, rightPanelY + 65, 0x55FF55, false);
        guiGraphics.drawString(font, "EnchantLv: " + level,
                rightPanelX, rightPanelY + 80, 0xFFFF55, false);
        guiGraphics.drawString(font, "Inventory: " + usedSlots + " / " + totalSlots,
                rightPanelX, rightPanelY + 95, 0xFFFFFF, false);
    }

    @Override
    protected void init() {
        super.init();
        int x = leftPos, y = topPos;
        int lineHeight = font.lineHeight + 4;
        int statStartY = y + 10 + lineHeight + 2;
        StatType[] stats = StatType.values();

        // 経験値チェック
        int exp = ClientLevelStorage.getClientExperience();
        int expToNext = ClientLevelStorage.getClientExpToNextLevel();
        boolean hasEnoughExp = exp >= expToNext;

        for (int i = 0; i < stats.length; i++) {
            StatType stat = stats[i];
            int rowY = statStartY + i * lineHeight;

            // "+" ボタンを作成（左パネル内に配置）
            Button btn = Button.builder(
                            Component.literal("+"),
                            b -> NetworkHandler.CHANNEL.send(
                                    PacketDistributor.SERVER.noArg(),
                                    new StatUpRequestPacket(stat)
                            )
                    )
                    .bounds(x + 140, rowY - 2, 12, 12)
                    .build();

            // ボタンのアクティブ状態制御
            btn.active = hasEnoughExp;

            addRenderableWidget(btn);
        }
    }

    @Override
    public boolean isPauseScreen() {
        return false; // ゲームが動き続ける
    }
}

