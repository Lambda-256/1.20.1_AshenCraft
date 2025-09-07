package com.MissFrom.AshenMod.main.gui;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.item.ItemStack;

public class LevelUpMenu extends AbstractContainerMenu {
    public static final MenuType<LevelUpMenu> TYPE =
            new MenuType<>(LevelUpMenu::new, FeatureFlagSet.of());

    public LevelUpMenu(int id, Inventory inv) {
        super(TYPE, id);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }
}
