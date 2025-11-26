package com.inolia_zaicek.more_tetra_tools.Register.Effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.common.ForgeMod;

public class ExecutionModeBuff extends MobEffect {
    private static final String ATTACK_SPEED_UUID_CU = "AA4CC9FC-DEF4-024D-134C-185101C12215";
    private static final double ATTACK_SPEED_CU = 0.25;
    public ExecutionModeBuff() {
        super(MobEffectCategory.BENEFICIAL, 0);
        this.addAttributeModifier(Attributes.MOVEMENT_SPEED, ATTACK_SPEED_UUID_CU, ATTACK_SPEED_CU, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }
}
