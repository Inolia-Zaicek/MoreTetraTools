package com.inolia_zaicek.more_tetra_tools.Register.Effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class IntoTheGrooveBuff extends MobEffect {
    private static final String ATTACK_SPEED_UUID_CU = "D9D10FC9-308D-F6AC-4022-4739CE529C2D";
    private static final double ATTACK_SPEED_CU = 0.08;
    public IntoTheGrooveBuff() {
        super(MobEffectCategory.BENEFICIAL, 0);
        this.addAttributeModifier(Attributes.ATTACK_SPEED, ATTACK_SPEED_UUID_CU, ATTACK_SPEED_CU, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }
}
