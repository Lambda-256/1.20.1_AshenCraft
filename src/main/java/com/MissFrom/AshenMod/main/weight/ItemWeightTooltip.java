package com.MissFrom.AshenMod.main.weight;

import com.MissFrom.AshenMod.main.AshenMod;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = AshenMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ItemWeightTooltip {

    @SubscribeEvent
    public static void onItemTooltip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        if (stack.isEmpty()) return;

        double weight = ItemWeightCalculator.calculateWeight(stack);
        String line = String.format("重量: %.2f", weight);

        // ツールチップに重量情報を追加
        event.getToolTip().add(Component.literal(line).withStyle(ChatFormatting.GRAY));
    }
}
