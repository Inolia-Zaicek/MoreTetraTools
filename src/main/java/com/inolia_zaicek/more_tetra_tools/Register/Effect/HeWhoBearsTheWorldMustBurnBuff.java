package com.inolia_zaicek.more_tetra_tools.Register.Effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class HeWhoBearsTheWorldMustBurnBuff extends MobEffect {
    private static final String UUID_CU_1 = "F78175F9-9F99-5450-3FCC-E14A7DA48F3D";
    private static final double CU_1 = 1.6875;
    private static final String UUID_CU_2 = "A7D12B2B-3CBE-F115-0E96-0868FC371D56";
    private static final double CU_2 = 0.5;
    private static final String UUID_CU_3 = "C019019E-D921-5C62-D031-145A97463C46";
    private static final double CU_3 = 0.5;
    public HeWhoBearsTheWorldMustBurnBuff() {
        super(MobEffectCategory.BENEFICIAL, 0);
        this.addAttributeModifier(Attributes.MAX_HEALTH, UUID_CU_1, CU_1, AttributeModifier.Operation.MULTIPLY_TOTAL);
        this.addAttributeModifier(Attributes.ATTACK_DAMAGE, UUID_CU_2, CU_2, AttributeModifier.Operation.MULTIPLY_TOTAL);
        this.addAttributeModifier(Attributes.KNOCKBACK_RESISTANCE, UUID_CU_3, CU_3, AttributeModifier.Operation.ADDITION);
    }
}
