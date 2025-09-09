package com.MissFrom.AshenMod.main.weight;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class ItemWeightCalculator {

    /** スタック全体の重量を返す */
    public static double calculateWeight(ItemStack stack) {
        // カスタム重量タグがあれば優先して返す
        if (stack.hasTag() && stack.getTag().contains("CustomWeight")) {
            return stack.getTag().getDouble("CustomWeight");
        }
        // 通常の素材ベース重量
        double unit = getUnitWeight(stack.getItem());
        return unit * stack.getCount();
    }

    /** アイテム単体の重量（既存実装） */
    private static double getUnitWeight(Item item) {
        // 木材系
        if (item == Items.OAK_PLANKS || item == Items.BIRCH_PLANKS ||
                item == Items.SPRUCE_PLANKS || item == Items.JUNGLE_PLANKS ||
                item == Items.ACACIA_PLANKS || item == Items.DARK_OAK_PLANKS ||
                item == Items.MANGROVE_PLANKS || item == Items.CHERRY_PLANKS ||
                item == Items.BAMBOO_PLANKS || item == Items.CRIMSON_PLANKS ||
                item == Items.WARPED_PLANKS ||
                item == Items.OAK_LOG || item == Items.BIRCH_LOG ||
                item == Items.SPRUCE_LOG || item == Items.JUNGLE_LOG ||
                item == Items.ACACIA_LOG || item == Items.DARK_OAK_LOG ||
                item == Items.STICK) {
            return MaterialWeight.WOOD;
        }
        // 石系
        if (item == Items.STONE || item == Items.COBBLESTONE ||
                item == Items.STONE_BRICKS || item == Items.MOSSY_COBBLESTONE) {
            return MaterialWeight.STONE;
        }
        // 鉄系
        if (item == Items.IRON_INGOT || item == Items.IRON_ORE ||
                item == Items.RAW_IRON || item == Items.IRON_BLOCK ||
                item == Items.IRON_SWORD || item == Items.IRON_PICKAXE ||
                item == Items.IRON_AXE || item == Items.IRON_SHOVEL ||
                item == Items.IRON_HOE || item == Items.IRON_HELMET ||
                item == Items.IRON_CHESTPLATE || item == Items.IRON_LEGGINGS ||
                item == Items.IRON_BOOTS || item == Items.SHIELD) {
            return MaterialWeight.IRON;
        }
        // 金系
        if (item == Items.GOLD_INGOT || item == Items.GOLD_ORE ||
                item == Items.RAW_GOLD || item == Items.GOLD_BLOCK ||
                item == Items.GOLDEN_SWORD || item == Items.GOLDEN_PICKAXE ||
                item == Items.GOLDEN_AXE || item == Items.GOLDEN_SHOVEL ||
                item == Items.GOLDEN_HOE || item == Items.GOLDEN_HELMET ||
                item == Items.GOLDEN_CHESTPLATE || item == Items.GOLDEN_LEGGINGS ||
                item == Items.GOLDEN_BOOTS) {
            return MaterialWeight.GOLD;
        }
        // ネザライト系
        if (item == Items.NETHERITE_INGOT || item == Items.NETHERITE_SCRAP ||
                item == Items.ANCIENT_DEBRIS || item == Items.NETHERITE_BLOCK ||
                item == Items.NETHERITE_SWORD || item == Items.NETHERITE_PICKAXE ||
                item == Items.NETHERITE_AXE || item == Items.NETHERITE_SHOVEL ||
                item == Items.NETHERITE_HOE || item == Items.NETHERITE_HELMET ||
                item == Items.NETHERITE_CHESTPLATE || item == Items.NETHERITE_LEGGINGS ||
                item == Items.NETHERITE_BOOTS) {
            return MaterialWeight.NETHERITE;
        }
        return MaterialWeight.DEFAULT;
    }
}
