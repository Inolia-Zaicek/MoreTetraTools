package com.inolia_zaicek.more_tetra_tools.Register.Effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class MemoryWeavingBuff extends MobEffect {
    private static final String UUID_CU = "6BF1665A-443C-C4F8-0E3B-129318A2DF69";
    private static final double CU = -0.05;
    public MemoryWeavingBuff() {
        super(MobEffectCategory.NEUTRAL, 0);
        this.addAttributeModifier(Attributes.MOVEMENT_SPEED, UUID_CU, CU, AttributeModifier.Operation.MULTIPLY_TOTAL);
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
