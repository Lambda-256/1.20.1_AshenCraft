package com.MissFrom.AshenMod.main.status.technique;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.common.util.LazyOptional;

public class TechniqueProvider implements ICapabilitySerializable<CompoundTag> {
    public static final Capability<ITechnique> TECHNIQUE_CAPABILITY =
            CapabilityManager.get(new CapabilityToken<>(){});

    private final ITechnique instance = new Technique();
    private final LazyOptional<ITechnique> optional = LazyOptional.of(() -> instance);

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
        if (cap == TECHNIQUE_CAPABILITY) {
            return optional.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        nbt.putInt("Technique", instance.getTechnique());
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        if (nbt.contains("Technique")) {
            int savedTechnique = nbt.getInt("Technique");
            int currentTechnique = instance.getTechnique();
            int diff = savedTechnique - currentTechnique;
            if (diff != 0) {
                instance.addTechnique(diff);
            }
        }
    }

    public void invalidate() {
        optional.invalidate();
    }
}
