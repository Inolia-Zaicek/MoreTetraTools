package com.inolia_zaicek.more_tetra_tools.Register.Effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class TidalElegyArmorBuff extends MobEffect {
    private static final String UUID_CU = "4F6E8F57-02E0-7942-6D9C-64F7F12BC9C2";
    private static final double CU = 0.10;
    public TidalElegyArmorBuff() {
        super(MobEffectCategory.BENEFICIAL, 0);
        this.addAttributeModifier(Attributes.ARMOR, UUID_CU, CU, AttributeModifier.Operation.MULTIPLY_TOTAL);
        this.addAttributeModifier(Attributes.ARMOR_TOUGHNESS, UUID_CU, CU, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }
}
