package com.inolia_zaicek.more_tetra_tools.Register.Effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class CrescentTransmigrationBuff extends MobEffect {
    private static final String UUID_CU = "87CE51D0-D740-AAA3-F14F-4A764281100E";
    private static final double CU = 0.075;
    public CrescentTransmigrationBuff() {
        super(MobEffectCategory.BENEFICIAL, 0);
        this.addAttributeModifier(Attributes.ATTACK_DAMAGE, UUID_CU, CU, AttributeModifier.Operation.MULTIPLY_BASE);
    }
}
