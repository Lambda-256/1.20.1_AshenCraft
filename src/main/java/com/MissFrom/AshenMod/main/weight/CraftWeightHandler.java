package com.MissFrom.AshenMod.main.weight;

import com.MissFrom.AshenMod.main.AshenMod;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.entity.player.PlayerEvent.ItemCraftedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = AshenMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CraftWeightHandler {
    private static RecipeWeightCalculator weightCalculator;

    @SubscribeEvent
    public static void onItemCrafted(ItemCraftedEvent event) {
        // サーバープレイヤーのみ処理
        if (!(event.getEntity() instanceof ServerPlayer player)) return;

        // RecipeWeightCalculator の初期化（初回のみ）
        if (weightCalculator == null) {
            ServerLevel level = (ServerLevel) player.level();
            weightCalculator = new RecipeWeightCalculator(level.registryAccess());
        }

        // クラフト入力コンテナを取得
        Container craftMatrix = event.getInventory();  // net.minecraft.world.Container

        // 入力素材の重量合計を算出
        double totalWeight = 0.0;
        int size = craftMatrix.getContainerSize();
        for (int i = 0; i < size; i++) {
            ItemStack inputStack = craftMatrix.getItem(i);
            if (!inputStack.isEmpty()) {
                totalWeight += ItemWeightCalculator.calculateWeight(inputStack);
            }
        }

        // 作成されたアイテムスタック数分を掛ける
        ItemStack craftedItem = event.getCrafting();
        totalWeight *= craftedItem.getCount();

        // NBT タグに重量をセット
        craftedItem.getOrCreateTag().putDouble("CustomWeight", totalWeight);
    }
}


