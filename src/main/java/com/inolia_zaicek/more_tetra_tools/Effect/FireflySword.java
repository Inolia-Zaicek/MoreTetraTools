package com.inolia_zaicek.more_tetra_tools.Effect;

import com.inolia_zaicek.more_tetra_tools.Damage.MTTTickZero;
import com.inolia_zaicek.more_tetra_tools.Register.MTTEffectsRegister;
import com.inolia_zaicek.more_tetra_tools.Util.MTTDamageSourceHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import se.mickelus.tetra.blocks.workbench.gui.WorkbenchStatsGui;
import se.mickelus.tetra.gui.stats.StatsHelper;
import se.mickelus.tetra.gui.stats.bar.GuiStatBar;
import se.mickelus.tetra.gui.stats.getter.*;
import se.mickelus.tetra.items.modular.IModularItem;
import se.mickelus.tetra.items.modular.impl.holo.gui.craft.HoloStatsGui;

import static com.inolia_zaicek.more_tetra_tools.Effect.Clent.MTTEffectGuiStats.*;

public class FireflySword {
    @OnlyIn(Dist.CLIENT)
    public static void init() {
        var statGetter1 = new StatGetterEffectLevel(fyrefly_type_iv_Effect, 1);
        var statGetter2 = new StatGetterEffectEfficiency(fyrefly_type_iv_Effect, 1);
        IStatGetter[] statGetters = {statGetter1, statGetter2};
        IStatFormat[] statFormats = {StatFormat.noDecimal, StatFormat.noDecimal};
        GuiStatBar statBar = new GuiStatBar(0, 0, StatsHelper.barLength,
                fyrefly_type_iv_Name, 0, 10, false, false, false,
                statGetter1, LabelGetterBasic.integerLabel,
                new TooltipGetterMultiValue(fyrefly_type_iv_Tooltip, statGetters, statFormats)
        );
        WorkbenchStatsGui.addBar(statBar);
        HoloStatsGui.addBar(statBar);
    }
    @SubscribeEvent
    public static void hurt(LivingHurtEvent event) {
        //近战攻击
        if (event.getSource().getEntity() instanceof Player player) {
            var mob = event.getEntity();
            var map = mob.getActiveEffectsMap();
            ItemStack mainHandItem = player.getMainHandItem();
            ItemStack offhandItem = player.getOffhandItem();
            int effectLevelBuffLevel = 0;
            float effectLevelDamage = 0;
            if (mainHandItem.getItem() instanceof IModularItem item) {
                effectLevelBuffLevel += item.getEffectLevel(mainHandItem, fyrefly_type_iv_Effect);
                effectLevelDamage += item.getEffectEfficiency(mainHandItem, fyrefly_type_iv_Effect);
            }
            if (MTTDamageSourceHelper.isMeleeAttack(event.getSource())&&player.getAttackStrengthScale(0.5f) > 0.9f ) {
                if (effectLevelBuffLevel > 0&&effectLevelDamage>0) {
                    //击破
                    if(mob.hasEffect(MTTEffectsRegister.ToughnessBreak.get())){
                        float number = effectLevelDamage/100;
                        event.setAmount(event.getAmount()*(1+number));
                    }
                    //非击破
                    else{
                        if (mob.hasEffect(MTTEffectsRegister.ToughnessReduction.get())) {
                            int buffLevel = mob.getEffect(MTTEffectsRegister.ToughnessReduction.get()).getAmplifier();
                            //boss
                            if(isBossEntity(event.getEntity().getType())) {
                                //当前+下一次>99级→下一次就是破百了
                                if(buffLevel+effectLevelBuffLevel>98) {
                                    //击破
                                    map.put(MTTEffectsRegister.ToughnessBreak.get(),
                                            new MobEffectInstance(MTTEffectsRegister.ToughnessBreak.get(), 200, 1));
                                    mob.removeEffect(MTTEffectsRegister.ToughnessReduction.get());
                                }else{
                                    //等级提升
                                    map.put(MTTEffectsRegister.ToughnessReduction.get(),
                                            new MobEffectInstance(MTTEffectsRegister.ToughnessReduction.get(), 400, buffLevel + effectLevelBuffLevel));
                                }
                            }
                            //非boss
                            else{
                                //当前+下一次>29级→下一次就是破百了
                                if(buffLevel+effectLevelBuffLevel>28) {
                                    //击破
                                    map.put(MTTEffectsRegister.ToughnessBreak.get(),
                                            new MobEffectInstance(MTTEffectsRegister.ToughnessBreak.get(), 100, 0));
                                    mob.removeEffect(MTTEffectsRegister.ToughnessReduction.get());
                                }else{
                                    //等级提升
                                    map.put(MTTEffectsRegister.ToughnessReduction.get(),
                                            new MobEffectInstance(MTTEffectsRegister.ToughnessReduction.get(), 400, buffLevel + effectLevelBuffLevel));
                                }
                            }
                        }else{
                            map.put(MTTEffectsRegister.ToughnessReduction.get(),
                                    new MobEffectInstance(MTTEffectsRegister.ToughnessReduction.get(), 400, effectLevelBuffLevel-1));
                        }
                    }
                }
            }
        }
        //挨打

        if (event.getSource().getEntity() instanceof Player player) {
            var mob = event.getEntity();
            var map = mob.getActiveEffectsMap();
            ItemStack mainHandItem = player.getMainHandItem();
            ItemStack offhandItem = player.getOffhandItem();
            float effectLevelHp = 0;
            float effectLevelNumber = 0;
            if (mainHandItem.getItem() instanceof IModularItem item) {
                effectLevelHp += item.getEffectLevel(mainHandItem, fyrefly_type_iv_Effect);
                effectLevelNumber += item.getEffectEfficiency(mainHandItem, fyrefly_type_iv_Effect);
            }
            if (offhandItem.getItem() instanceof IModularItem item) {
                effectLevelHp += item.getEffectLevel(offhandItem, fyrefly_type_iv_Effect);
                effectLevelNumber += item.getEffectEfficiency(offhandItem, fyrefly_type_iv_Effect);
            }
            if (effectLevelHp > 0&&effectLevelNumber>0) {
                //生命值比例
                float dhp = (player.getHealth())/(player.getMaxHealth());
                //正式生命值比例
                float dhpNumber =Math.max(dhp,effectLevelHp/100);
                //已损失比例*减伤数额
                float number = (1-dhpNumber)*(effectLevelNumber/100);
                if(dhp<1) {
                    event.setAmount(event.getAmount() * (1 - number));
                }
            }
        }
    }
}