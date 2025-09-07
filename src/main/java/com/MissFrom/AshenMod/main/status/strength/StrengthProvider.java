package com.MissFrom.AshenMod.main.status.strength;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.common.util.LazyOptional;

public class StrengthProvider implements ICapabilitySerializable<CompoundTag> {
    public static final Capability<IStrength> STRENGTH_CAPABILITY =
            CapabilityManager.get(new CapabilityToken<>(){});

    private final IStrength instance = new Strength();
    private final LazyOptional<IStrength> optional = LazyOptional.of(() -> instance);

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
        if (cap == STRENGTH_CAPABILITY) {
            return optional.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        nbt.putInt("Strength", instance.getStrength());
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        if (nbt.contains("Strength")) {
            int savedStrength = nbt.getInt("Strength");
            int currentStrength = instance.getStrength();
            int diff = savedStrength - currentStrength;
            if (diff != 0) {
                instance.addStrength(diff);
            }
        }
    }

    public void invalidate() {
        optional.invalidate();
    }
}
