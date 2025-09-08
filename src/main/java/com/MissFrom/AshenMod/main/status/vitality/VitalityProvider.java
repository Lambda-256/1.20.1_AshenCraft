package com.MissFrom.AshenMod.main.status.vitality;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class VitalityProvider implements ICapabilitySerializable<CompoundTag> {
    // Capability の登録トークン
    public static Capability<IVitality> VITALITY_CAPABILITY =
            CapabilityManager.get(new CapabilityToken<>() {});

    private final IVitality instance = new Vitality();
    private final LazyOptional<IVitality> optional = LazyOptional.of(() -> instance);

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
        if (cap == VITALITY_CAPABILITY) {
            return optional.cast();
        }
        return LazyOptional.empty();
    }

    /** ワールド保存時のNBT書き込み */
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        nbt.putInt("Vitality", instance.getVitality());
        return nbt;
    }

    /** ワールド読み込み時のNBT読み込み */
    public void deserializeNBT(CompoundTag nbt) {
        if (nbt.contains("Vitality")) {
            int savedVitality = nbt.getInt("Vitality");
            int currentVitality = instance.getVitality();
            int diff = savedVitality - currentVitality;
            if (diff != 0) {
                instance.addVitality(diff);
            }
        }
    }
}
