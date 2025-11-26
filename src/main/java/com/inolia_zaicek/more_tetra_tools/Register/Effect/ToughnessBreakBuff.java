package com.inolia_zaicek.more_tetra_tools.Register.Effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ToughnessBreakBuff extends MobEffect {
    private static final String UUID_CU = "793B0438-9E2B-A40C-D2FB-1C9526322C39";
    private static final double CU = -0.99;
    private static final double CU2 = -0.2;
    public ToughnessBreakBuff() {
        super(MobEffectCategory.NEUTRAL, 0);
        this.addAttributeModifier(Attributes.MOVEMENT_SPEED, UUID_CU, CU, AttributeModifier.Operation.MULTIPLY_TOTAL);
        this.addAttributeModifier(Attributes.ATTACK_DAMAGE, UUID_CU, CU, AttributeModifier.Operation.MULTIPLY_TOTAL);
        this.addAttributeModifier(Attributes.ATTACK_SPEED, UUID_CU, CU, AttributeModifier.Operation.MULTIPLY_TOTAL);
        this.addAttributeModifier(Attributes.FLYING_SPEED, UUID_CU, CU, AttributeModifier.Operation.MULTIPLY_TOTAL);
        this.addAttributeModifier(Attributes.ARMOR_TOUGHNESS, UUID_CU, CU2, AttributeModifier.Operation.MULTIPLY_TOTAL);
        this.addAttributeModifier(Attributes.ARMOR, UUID_CU, CU2, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }
    //治疗物品
    @Override
    public List<ItemStack> getCurativeItems() {
        //治疗物品组
        ArrayList<ItemStack> ret = new ArrayList<>();
        //把牛奶删了ret.remove(new ItemStack(Items.MILK_BUCKET));
        //ret.remove(new ItemStack(Items.MILK_BUCKET));
        //返回空的数组，啥都没有，没法牛奶解除
        return ret;
    }
}
