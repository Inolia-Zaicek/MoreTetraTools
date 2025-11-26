package com.inolia_zaicek.more_tetra_tools.Register.Effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class CelestialGlobeBuff extends MobEffect {
    private static final String UUID_CU = "4170A70E-7C64-474C-7430-660765AD3A01";
    private static final double CU = 0.07;
    private static final double CU1 = 0.04;
    public CelestialGlobeBuff() {
        super(MobEffectCategory.BENEFICIAL, 0);
        this.addAttributeModifier(Attributes.ARMOR, UUID_CU, CU1, AttributeModifier.Operation.MULTIPLY_TOTAL);
        this.addAttributeModifier(Attributes.ARMOR_TOUGHNESS, UUID_CU, CU1, AttributeModifier.Operation.MULTIPLY_TOTAL);
        this.addAttributeModifier(Attributes.ATTACK_SPEED, UUID_CU, CU, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }
}
