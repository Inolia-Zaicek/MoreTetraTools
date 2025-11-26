package com.inolia_zaicek.more_tetra_tools.Register.Effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class TidalElegyHealthBuff extends MobEffect {
    private static final String UUID_CU = "B8386A5C-D5AD-F3BA-6A07-8209E533ABC2";
    private static final double CU = 0.10;
    public TidalElegyHealthBuff() {
        super(MobEffectCategory.BENEFICIAL, 0);
        this.addAttributeModifier(Attributes.MAX_HEALTH, UUID_CU, CU, AttributeModifier.Operation.MULTIPLY_TOTAL);
        this.addAttributeModifier(Attributes.KNOCKBACK_RESISTANCE, UUID_CU, CU, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }
}
