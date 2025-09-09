package com.MissFrom.AshenMod.main.weight;

import net.minecraft.core.RegistryAccess;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;

public class RecipeWeightCalculator {
    private RegistryAccess registryAccess;

    public RecipeWeightCalculator(RegistryAccess registryAccess) {
        this.registryAccess = registryAccess;
    }

    public void setRegistryAccess(RegistryAccess registryAccess) {
        this.registryAccess = registryAccess;
    }

    public RegistryAccess getRegistryAccess() {
        return this.registryAccess;
    }

    /**
     * レシピに使われた素材の総重量を計算して返す
     */
    public double calculateWeightByRecipe(CraftingRecipe recipe, CraftingContainer container) {
        // assemble だけ実行して実際の材料数を確認したい場合でも、
        // container の中身をもとに重量計算を行う。

        double totalWeight = 0.0;
        // CraftingContainer#getContainerSize() でスロット数を取得
        int size = container.getContainerSize();
        for (int i = 0; i < size; i++) {
            ItemStack inputStack = container.getItem(i);
            if (!inputStack.isEmpty()) {
                // 各スタックの重量を ItemWeightCalculator に委譲
                totalWeight += ItemWeightCalculator.calculateWeight(inputStack);
            }
        }
        return totalWeight;
    }
}