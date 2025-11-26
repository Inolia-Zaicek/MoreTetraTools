package com.inolia_zaicek.more_tetra_tools.Register.Effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.common.ForgeMod;

public class BlazingSunSObeisanceBuff extends MobEffect {
    private static final String UUID_CU = "289C9C2C-7BB0-8D8B-5712-D35EFE7B648C";
    private static final double CU = 2;
    public BlazingSunSObeisanceBuff() {
        super(MobEffectCategory.BENEFICIAL, 0);
        this.addAttributeModifier(ForgeMod.ENTITY_REACH.get(), UUID_CU, CU, AttributeModifier.Operation.ADDITION);
    }
}
