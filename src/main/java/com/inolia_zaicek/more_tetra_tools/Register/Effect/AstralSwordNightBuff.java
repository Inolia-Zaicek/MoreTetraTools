package com.inolia_zaicek.more_tetra_tools.Register.Effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class AstralSwordNightBuff extends MobEffect {
    private static final String UUID_CU = "33B8F701-8F1A-FBA5-FE7C-B408C9E256FE";
    private static final double CU = 0.1;
    private static final double CUA = 0.25;
    public AstralSwordNightBuff() {
        super(MobEffectCategory.BENEFICIAL, 0);
        this.addAttributeModifier(Attributes.ATTACK_DAMAGE, UUID_CU, CU, AttributeModifier.Operation.MULTIPLY_TOTAL);
        this.addAttributeModifier(Attributes.ARMOR, UUID_CU, CUA, AttributeModifier.Operation.MULTIPLY_TOTAL);
        this.addAttributeModifier(Attributes.ARMOR_TOUGHNESS, UUID_CU, CUA, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }
}
