package com.inolia_zaicek.more_tetra_tools.Effect;

import com.inolia_zaicek.more_tetra_tools.Register.MTTEffectsRegister;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import se.mickelus.tetra.blocks.workbench.gui.WorkbenchStatsGui;
import se.mickelus.tetra.gui.stats.StatsHelper;
import se.mickelus.tetra.gui.stats.bar.GuiStatBar;
import se.mickelus.tetra.gui.stats.getter.*;
import se.mickelus.tetra.items.modular.IModularItem;
import se.mickelus.tetra.items.modular.impl.holo.gui.craft.HoloStatsGui;

import static com.inolia_zaicek.more_tetra_tools.Effect.Clent.MTTEffectGuiStats.*;

public class OceanSVengence {
    @OnlyIn(Dist.CLIENT)
    public static void init() {
        var statGetter1 = new StatGetterEffectLevel(weak_armor_Effect, 1);
        var statGetter2 = new StatGetterEffectEfficiency(weak_armor_Effect, 1);
        IStatGetter[] statGetters = {statGetter1, statGetter2};
        IStatFormat[] statFormats = {StatFormat.noDecimal, StatFormat.noDecimal};
        GuiStatBar statBar = new GuiStatBar(0, 0, StatsHelper.barLength,
                weak_armor_Name, 0, 10, false, false, false,
                statGetter1, LabelGetterBasic.integerLabel,
                new TooltipGetterMultiValue(weak_armor_Tooltip, statGetters, statFormats)
        );
        WorkbenchStatsGui.addBar(statBar);
        HoloStatsGui.addBar(statBar);
    }
    @SubscribeEvent
    public static void hurt(LivingHurtEvent event) {
        if (event.getSource().getEntity() instanceof LivingEntity player) {
            var mob = event.getEntity();
            ItemStack mainHandItem = player.getMainHandItem();
            ItemStack offhandItem = player.getOffhandItem();
            float waterEffectLevel = 0;
            float armorEffectLevel = 0;
            float armorEffectDamage = 0;
            if (mainHandItem.getItem() instanceof IModularItem item) {
                waterEffectLevel += item.getEffectLevel(mainHandItem, ocean_will_Effect);
                armorEffectLevel += item.getEffectLevel(mainHandItem, weak_armor_Effect);
                armorEffectDamage += item.getEffectEfficiency(mainHandItem, weak_armor_Effect);
            }
            if (offhandItem.getItem() instanceof IModularItem item) {
                waterEffectLevel += item.getEffectLevel(offhandItem, ocean_will_Effect);
                armorEffectLevel += item.getEffectLevel(offhandItem, weak_armor_Effect);
                armorEffectDamage += item.getEffectEfficiency(offhandItem, weak_armor_Effect);
            }
            if(waterEffectLevel>0||armorEffectLevel>0) {
                //增伤数额
                float number = 0;
                if(waterEffectLevel>0) {
                    if (player.isUnderWater() || player.isInWater() || player.isInWaterOrRain() || player.isInWaterRainOrBubble() || player.isInWaterOrBubble() || player.isSensitiveToWater()) {
                        number += waterEffectLevel;
                    }
                }
                if(armorEffectLevel>0) {
                    float armor = (float) mob.getAttributeValue(Attributes.ATTACK_DAMAGE);
                    //如果当前阈值大于等于护甲值，满额增幅
                    if (armorEffectLevel >= armor) {
                        number += armorEffectDamage;
                    } else {
                        //当前护甲值大于阈值3倍
                        if (armor >= armorEffectLevel * 3) {
                            number += 0;
                        } else {
                            //3倍--阈值之间 → 1-(两者差额)/3倍 → 比如阈值是5，护甲是8，那也就是1-3/15=1-1/5=4/5的增伤，如果是10和28则是1-18/30=1-9/10=1/10
                            //即：距离三倍阈值的距离
                            number += armorEffectDamage * (1 - (armor - armorEffectLevel) / (armorEffectLevel * 3));
                        }
                    }
                }
                if(number>0){
                    event.setAmount(event.getAmount()*(1+number/100));
                }
            }
        }else if (event.getSource().getDirectEntity() instanceof LivingEntity player) {
            var mob = event.getEntity();
            ItemStack mainHandItem = player.getMainHandItem();
            ItemStack offhandItem = player.getOffhandItem();
            float waterEffectLevel = 0;
            float armorEffectLevel = 0;
            float armorEffectDamage = 0;
            if (mainHandItem.getItem() instanceof IModularItem item) {
                waterEffectLevel += item.getEffectLevel(mainHandItem, ocean_will_Effect);
                armorEffectLevel += item.getEffectLevel(mainHandItem, weak_armor_Effect);
                armorEffectDamage += item.getEffectEfficiency(mainHandItem, weak_armor_Effect);
            }
            if (offhandItem.getItem() instanceof IModularItem item) {
                waterEffectLevel += item.getEffectLevel(offhandItem, ocean_will_Effect);
                armorEffectLevel += item.getEffectLevel(offhandItem, weak_armor_Effect);
                armorEffectDamage += item.getEffectEfficiency(offhandItem, weak_armor_Effect);
            }
            if(waterEffectLevel>0||armorEffectLevel>0) {
                //增伤数额
                float number = 0;
                if(waterEffectLevel>0) {
                    if (player.isUnderWater() || player.isInWater() || player.isInWaterOrRain() || player.isInWaterRainOrBubble() || player.isInWaterOrBubble() || player.isSensitiveToWater()) {
                        number += waterEffectLevel;
                    }
                }
                if(armorEffectLevel>0) {
                    float armor = (float) mob.getAttributeValue(Attributes.ATTACK_DAMAGE);
                    //如果当前阈值大于等于护甲值，满额增幅
                    if (armorEffectLevel >= armor) {
                        number += armorEffectDamage;
                    } else {
                        //当前护甲值大于阈值3倍
                        if (armor >= armorEffectLevel * 3) {
                            number += 0;
                        } else {
                            //3倍--阈值之间 → 1-(两者差额)/3倍 → 比如阈值是5，护甲是8，那也就是1-3/15=1-1/5=4/5的增伤，如果是10和28则是1-18/30=1-9/10=1/10
                            //即：距离三倍阈值的距离
                            number += armorEffectDamage * (1 - (armor - armorEffectLevel) / (armorEffectLevel * 3));
                        }
                    }
                }
                if(number>0){
                    event.setAmount(event.getAmount()*(1+number/100));
                }
            }
        }
    }
}
