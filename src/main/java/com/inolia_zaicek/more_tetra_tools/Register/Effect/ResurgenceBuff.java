package com.inolia_zaicek.more_tetra_tools.Register.Effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class ResurgenceBuff extends MobEffect {
    private static final String UUID_CU = "A2E3E3FA-91BD-1E25-C76D-1FD47AE4AA40";
    private static final double CU = 0.10;
    public ResurgenceBuff() {
        super(MobEffectCategory.BENEFICIAL, 0);
        this.addAttributeModifier(Attributes.ATTACK_DAMAGE, UUID_CU, CU, AttributeModifier.Operation.MULTIPLY_TOTAL);
        this.addAttributeModifier(Attributes.MOVEMENT_SPEED, UUID_CU, CU, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }
}
