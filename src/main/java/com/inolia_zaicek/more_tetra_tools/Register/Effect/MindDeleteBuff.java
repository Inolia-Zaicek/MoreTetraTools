package com.inolia_zaicek.more_tetra_tools.Register.Effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class MindDeleteBuff extends MobEffect {
    private static final String UUID_CU = "73601706-2A25-3987-F222-5BAC36B574DA";
    private static final double CU = -0.99;
    public MindDeleteBuff() {
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
