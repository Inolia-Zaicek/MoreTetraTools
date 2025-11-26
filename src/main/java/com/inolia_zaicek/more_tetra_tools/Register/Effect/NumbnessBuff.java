package com.inolia_zaicek.more_tetra_tools.Register.Effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class NumbnessBuff extends MobEffect {
    private static final String UUID_CU = "01A14E41-86F5-5E20-CE54-D5DD4D563F97";
    private static final double CU = 0.1;
    private static final double CUA = 0.25;
    public NumbnessBuff() {
        super(MobEffectCategory.NEUTRAL, 0);
        this.addAttributeModifier(Attributes.ATTACK_DAMAGE, UUID_CU, CU, AttributeModifier.Operation.MULTIPLY_TOTAL);
        this.addAttributeModifier(Attributes.ARMOR, UUID_CU, CU, AttributeModifier.Operation.MULTIPLY_TOTAL);
        this.addAttributeModifier(Attributes.ARMOR_TOUGHNESS, UUID_CU, CU, AttributeModifier.Operation.MULTIPLY_TOTAL);
        this.addAttributeModifier(Attributes.ATTACK_SPEED, UUID_CU, CUA, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }
}
