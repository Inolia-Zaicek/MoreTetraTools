package com.inolia_zaicek.more_tetra_tools.Register.Effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.common.ForgeMod;

public class CrystallineShineBuff extends MobEffect {
    private static final String UUID_CU = "0351001F-81D7-082A-5810-2455AC74715D";
    private static final double CU = -0.99;
    public CrystallineShineBuff() {
        super(MobEffectCategory.NEUTRAL, 0);
        this.addAttributeModifier(Attributes.MOVEMENT_SPEED, UUID_CU, CU, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }
}
