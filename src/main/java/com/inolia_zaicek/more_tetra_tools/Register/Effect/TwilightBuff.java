package com.inolia_zaicek.more_tetra_tools.Register.Effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.common.ForgeMod;

public class TwilightBuff extends MobEffect {
    private static final String UUID_CU = "243D7B40-91EA-9042-FEA0-6BF6B4A131F1";
    private static final double CU = 0.05;
    private static final double CU2 = 0.0825;
    private static final double CU3 = 0.1;
    public TwilightBuff() {
        super(MobEffectCategory.BENEFICIAL, 0);
        this.addAttributeModifier(ForgeMod.ENTITY_REACH.get(), UUID_CU, CU, AttributeModifier.Operation.MULTIPLY_TOTAL);
        this.addAttributeModifier(Attributes.ATTACK_DAMAGE, UUID_CU, CU2, AttributeModifier.Operation.MULTIPLY_TOTAL);
        this.addAttributeModifier(Attributes.MAX_HEALTH, UUID_CU, CU3, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }
}
