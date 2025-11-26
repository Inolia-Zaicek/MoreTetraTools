package com.inolia_zaicek.more_tetra_tools.Register.Effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class SlashedDream extends MobEffect {
    private static final String UUID_CU = "60092A41-C853-3FE5-12F8-BE2DEBFDC841";
    private static final double CU = 0.05;
    public SlashedDream() {
        super(MobEffectCategory.NEUTRAL, 0);
        this.addAttributeModifier(Attributes.ATTACK_DAMAGE, UUID_CU, CU, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }
}
