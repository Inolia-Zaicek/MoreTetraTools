package com.inolia_zaicek.more_tetra_tools.Register.Effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class AstralSwordMorningBuff extends MobEffect {
    private static final String UUID_CU = "D94B2773-DFB7-65C8-F38F-148B89CCBB37";
    private static final double CU = 0.1;
    private static final double CUA = 0.25;
    public AstralSwordMorningBuff() {
        super(MobEffectCategory.BENEFICIAL, 0);
        this.addAttributeModifier(Attributes.ATTACK_DAMAGE, UUID_CU, CUA, AttributeModifier.Operation.MULTIPLY_TOTAL);
        this.addAttributeModifier(Attributes.ARMOR, UUID_CU, CU, AttributeModifier.Operation.MULTIPLY_TOTAL);
        this.addAttributeModifier(Attributes.ARMOR_TOUGHNESS, UUID_CU, CU, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }
}
