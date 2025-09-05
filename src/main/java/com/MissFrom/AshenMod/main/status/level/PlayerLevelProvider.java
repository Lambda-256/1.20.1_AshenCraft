package com.MissFrom.AshenMod.main.status.level;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

public class PlayerLevelProvider implements ICapabilitySerializable<CompoundTag> {
    public static final Capability<IPlayerLevel> PLAYER_LEVEL =
            CapabilityManager.get(new CapabilityToken<>(){});
    private final IPlayerLevel instance = new PlayerLevel();
    private final LazyOptional<IPlayerLevel> holder = LazyOptional.of(() -> instance);

    // TODO: Notnull系
    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
        return PLAYER_LEVEL.orEmpty(cap, holder);
    }

    @Override
    public CompoundTag serializeNBT() {
        return instance.saveToNBT();
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        instance.loadFromNBT(nbt);
    }
}

