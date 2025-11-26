package com.inolia_zaicek.more_tetra_tools.Register.Effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class FlameheartBuff extends MobEffect {
    private static final String UUID_CU = "D5B198AF-AFAC-0A03-A6A3-724A74A2E975";
    private static final double CU = 0.7;
    public FlameheartBuff() {
        super(MobEffectCategory.BENEFICIAL, 0);
        this.addAttributeModifier(Attributes.ATTACK_SPEED, UUID_CU, CU, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }
}
