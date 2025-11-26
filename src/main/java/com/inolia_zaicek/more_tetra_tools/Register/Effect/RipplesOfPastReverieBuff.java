package com.inolia_zaicek.more_tetra_tools.Register.Effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class RipplesOfPastReverieBuff extends MobEffect {
    private static final String UUID_CU = "EAB828A9-DBE3-E444-5AE8-3692EAB7D675";
    private static final double CU = 0.113;
    public RipplesOfPastReverieBuff() {
        super(MobEffectCategory.BENEFICIAL, 0);
        this.addAttributeModifier(Attributes.MAX_HEALTH, UUID_CU, CU, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }
}
