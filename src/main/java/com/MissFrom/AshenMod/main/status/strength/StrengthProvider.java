package com.MissFrom.AshenMod.main.status.strength;

import net.minecraft.core.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;

public class StrengthProvider implements ICapabilityProvider {
    public static final Capability<IStrength> STRENGTH_CAPABILITY =
            CapabilityManager.get(new CapabilityToken<>(){});

    private final LazyOptional<IStrength> instance =
            LazyOptional.of(Strength::new);

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
        if (cap == STRENGTH_CAPABILITY) {
            return instance.cast();
        }
        return LazyOptional.empty();
    }

    public void invalidate() {
        instance.invalidate();
    }
}
