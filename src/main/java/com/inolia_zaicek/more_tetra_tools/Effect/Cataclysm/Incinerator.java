package com.inolia_zaicek.more_tetra_tools.Effect.Cataclysm;

import com.github.L_Ender.cataclysm.init.ModEffect;
import com.github.L_Ender.cataclysm.init.ModItems;
import com.inolia_zaicek.more_tetra_tools.Damage.MTTTickZero;
import com.inolia_zaicek.more_tetra_tools.MoreTetraTools;
import com.inolia_zaicek.more_tetra_tools.Util.MTTDamageSourceHelper;
import com.inolia_zaicek.more_tetra_tools.Util.MTTEffectHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import se.mickelus.tetra.blocks.workbench.gui.WorkbenchStatsGui;
import se.mickelus.tetra.gui.stats.StatsHelper;
import se.mickelus.tetra.gui.stats.bar.GuiStatBar;
import se.mickelus.tetra.gui.stats.getter.*;
import se.mickelus.tetra.items.modular.impl.holo.gui.craft.HoloStatsGui;

import java.util.Objects;
import java.util.Random;

import static com.inolia_zaicek.more_tetra_tools.Effect.Clent.MTTEffectGuiStats.*;
import static com.inolia_zaicek.more_tetra_tools.Effect.Clent.MTTEffectGuiStats.kami_ori_Effect;
import static com.inolia_zaicek.more_tetra_tools.Effect.Clent.MTTEffectGuiStats.kami_ori_Name;
import static com.inolia_zaicek.more_tetra_tools.Effect.Clent.MTTEffectGuiStats.kami_ori_Tooltip;
import static net.minecraft.tags.DamageTypeTags.IS_FIRE;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE,modid = MoreTetraTools.MODID)
public class Incinerator {
    @OnlyIn(Dist.CLIENT)
    public static void init() {
        var statGetter1 = new StatGetterEffectLevel(break_waves_Effect, 1);
        var statGetter2 = new StatGetterEffectEfficiency(break_waves_Effect, 1);
        IStatGetter[] statGetters = {statGetter1, statGetter2};
        IStatFormat[] statFormats = {StatFormat.noDecimal, StatFormat.noDecimal};
        GuiStatBar statBar1 = new GuiStatBar(0, 0, StatsHelper.barLength,
                break_waves_Name, 0, 100, false, false, false,
                statGetter1, LabelGetterBasic.integerLabel,
                new TooltipGetterMultiValue(break_waves_Tooltip, statGetters, statFormats)
        );
        WorkbenchStatsGui.addBar(statBar1);
        HoloStatsGui.addBar(statBar1);
        var statGetter3 = new StatGetterEffectLevel(tempestuous_waves_Effect, 1);
        var statGetter4 = new StatGetterEffectEfficiency(tempestuous_waves_Effect, 1);
        IStatGetter[] statGetters2 = {statGetter3, statGetter4};
        IStatFormat[] statFormats2 = {StatFormat.noDecimal, StatFormat.noDecimal};
        GuiStatBar statBar2 = new GuiStatBar(0, 0, StatsHelper.barLength,
                tempestuous_waves_Name, 0, 100, false, false, false,
                statGetter3, LabelGetterBasic.integerLabel,
                new TooltipGetterMultiValue(tempestuous_waves_Tooltip, statGetters2, statFormats2)
        );
        WorkbenchStatsGui.addBar(statBar2);
        HoloStatsGui.addBar(statBar2);
        var statGetter5 = new StatGetterEffectLevel(netherite_base_Effect, 1);
        var statGetter6 = new StatGetterEffectEfficiency(netherite_base_Effect, 1);
        IStatGetter[] statGetters3 = {statGetter5, statGetter6};
        IStatFormat[] statFormats3 = {StatFormat.noDecimal, StatFormat.noDecimal};
        GuiStatBar statBar3 = new GuiStatBar(0, 0, StatsHelper.barLength,
                netherite_base_Name, 0, 100, false, false, false,
                statGetter5, LabelGetterBasic.integerLabel,
                new TooltipGetterMultiValue(netherite_base_Tooltip, statGetters3, statFormats3)
        );
        WorkbenchStatsGui.addBar(statBar3);
        HoloStatsGui.addBar(statBar3);
        var statGetter7 = new StatGetterEffectLevel(earthquake_strike_Effect, 1);
        var statGetter8 = new StatGetterEffectEfficiency(earthquake_strike_Effect, 1);
        IStatGetter[] statGetters4 = {statGetter7, statGetter8};
        IStatFormat[] statFormats4 = {StatFormat.noDecimal, StatFormat.noDecimal};
        GuiStatBar statBar4 = new GuiStatBar(0, 0, StatsHelper.barLength,
                earthquake_strike_Name, 0, 100, false, false, false,
                statGetter7, LabelGetterBasic.integerLabel,
                new TooltipGetterMultiValue(earthquake_strike_Tooltip, statGetters4, statFormats4)
        );
        WorkbenchStatsGui.addBar(statBar4);
        HoloStatsGui.addBar(statBar4);
        var statGetter9 = new StatGetterEffectLevel(cursium_assault_Effect, 1);
        var statGetter10 = new StatGetterEffectEfficiency(cursium_assault_Effect, 1);
        IStatGetter[] statGetters13 = {statGetter9, statGetter10};
        IStatFormat[] statFormats13 = {StatFormat.noDecimal, StatFormat.noDecimal};
        GuiStatBar statBar13 = new GuiStatBar(0, 0, StatsHelper.barLength,
                cursium_assault_Name, 0, 100, false, false, false,
                statGetter9, LabelGetterBasic.integerLabel,
                new TooltipGetterMultiValue(cursium_assault_Tooltip, statGetters13, statFormats13)
        );
        WorkbenchStatsGui.addBar(statBar13);
        HoloStatsGui.addBar(statBar13);
        var statGetter11 = new StatGetterEffectLevel(cursium_field_Effect, 1);
        var statGetter12 = new StatGetterEffectEfficiency(cursium_field_Effect, 1);
        IStatGetter[] statGetters14 = {statGetter11, statGetter12};
        IStatFormat[] statFormats14 = {StatFormat.noDecimal, StatFormat.noDecimal};
        GuiStatBar statBar14 = new GuiStatBar(0, 0, StatsHelper.barLength,
                cursium_field_Name, 0, 5, false, false, false,
                statGetter11, LabelGetterBasic.integerLabel,
                new TooltipGetterMultiValue(cursium_field_Tooltip, statGetters14, statFormats14)
        );
        WorkbenchStatsGui.addBar(statBar14);
        HoloStatsGui.addBar(statBar14);
        var statGetter13 = new StatGetterEffectLevel(soul_annihilator_Effect, 1);
        var statGetter14 = new StatGetterEffectEfficiency(soul_annihilator_Effect, 1);
        IStatGetter[] statGetters23 = {statGetter13, statGetter14};
        IStatFormat[] statFormats23 = {StatFormat.noDecimal, StatFormat.noDecimal};
        GuiStatBar statBar23 = new GuiStatBar(0, 0, StatsHelper.barLength,
                soul_annihilator_Name, 0, 100, false, false, false,
                statGetter13, LabelGetterBasic.integerLabel,
                new TooltipGetterMultiValue(soul_annihilator_Tooltip, statGetters23, statFormats23)
        );
        WorkbenchStatsGui.addBar(statBar23);
        HoloStatsGui.addBar(statBar23);
        var statGetter15 = new StatGetterEffectLevel(cursium_annihilator_Effect, 1);
        var statGetter16 = new StatGetterEffectEfficiency(cursium_annihilator_Effect, 1);
        IStatGetter[] statGetters24 = {statGetter15, statGetter16};
        IStatFormat[] statFormats24 = {StatFormat.noDecimal, StatFormat.noDecimal};
        GuiStatBar statBar24 = new GuiStatBar(0, 0, StatsHelper.barLength,
                cursium_annihilator_Name, 0, 100, false, false, false,
                statGetter15, LabelGetterBasic.integerLabel,
                new TooltipGetterMultiValue(cursium_annihilator_Tooltip, statGetters24, statFormats24)
        );
        WorkbenchStatsGui.addBar(statBar24);
        HoloStatsGui.addBar(statBar24);
        var statGetter23 = new StatGetterEffectLevel(thunder_astrape_Effect, 1);
        var statGetter24 = new StatGetterEffectEfficiency(thunder_astrape_Effect, 1);
        IStatGetter[] statGetters33 = {statGetter23, statGetter24};
        IStatFormat[] statFormats33 = {StatFormat.noDecimal, StatFormat.noDecimal};
        GuiStatBar statBar33 = new GuiStatBar(0, 0, StatsHelper.barLength,
                thunder_astrape_Name, 0, 100, false, false, false,
                statGetter23, LabelGetterBasic.integerLabel,
                new TooltipGetterMultiValue(thunder_astrape_Tooltip, statGetters33, statFormats33)
        );
        WorkbenchStatsGui.addBar(statBar33);
        HoloStatsGui.addBar(statBar33);
        var statGetter25 = new StatGetterEffectLevel(thunder_field_Effect, 1);
        var statGetter26 = new StatGetterEffectEfficiency(thunder_field_Effect, 1);
        IStatGetter[] statGetters34 = {statGetter25, statGetter26};
        IStatFormat[] statFormats34 = {StatFormat.noDecimal, StatFormat.noDecimal};
        GuiStatBar statBar34 = new GuiStatBar(0, 0, StatsHelper.barLength,
                thunder_field_Name, 0, 30, false, false, false,
                statGetter25, LabelGetterBasic.integerLabel,
                new TooltipGetterMultiValue(thunder_field_Tooltip, statGetters34, statFormats34)
        );
        WorkbenchStatsGui.addBar(statBar34);
        HoloStatsGui.addBar(statBar34);
    }
    @SubscribeEvent
    public static void hurt(LivingHurtEvent event) {
        if(ModList.get().isLoaded("cataclysm")) {
            Random random = new Random();
            if (event.getSource().getEntity() instanceof LivingEntity livingEntity) {
                var mob = event.getEntity();
                var map = mob.getActiveEffectsMap();
                int chance = (MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectLevel(livingEntity, incinerat_break_Effect));
                if (random.nextInt(101) <= chance&&chance>0) {
                    if (mob.hasEffect(Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("cataclysm", "blazing_brand"))))) {
                        int buffLevel = mob.getEffect(Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("cataclysm", "blazing_brand")))).getAmplifier();
                        mob.addEffect(new MobEffectInstance(Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("cataclysm", "blazing_brand"))), 300, Math.min(4,buffLevel+1) ));
                        map.put(Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("cataclysm", "blazing_brand"))),
                                new MobEffectInstance(Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("cataclysm", "blazing_brand"))), 300, Math.min(4,buffLevel+1) ));
                    }else{
                        mob.addEffect(new MobEffectInstance(Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("cataclysm", "blazing_brand"))), 300, 0));
                        map.put(Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("cataclysm", "blazing_brand"))),
                                new MobEffectInstance(Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("cataclysm", "blazing_brand"))), 300, 0));
                    }
                }
            }
            else if (event.getSource().getDirectEntity() instanceof LivingEntity livingEntity) {
                var mob = event.getEntity();
                var map = mob.getActiveEffectsMap();
                int chance = (MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectLevel(livingEntity, incinerat_break_Effect));
                if (random.nextInt(101) <= chance&&chance>0) {
                    if (mob.hasEffect(Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("cataclysm", "blazing_brand"))))) {
                        int buffLevel = mob.getEffect(Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("cataclysm", "blazing_brand")))).getAmplifier();
                        mob.addEffect(new MobEffectInstance(Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("cataclysm", "blazing_brand"))), 300, Math.min(4,buffLevel+1) ));
                        map.put(Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("cataclysm", "blazing_brand"))),
                                new MobEffectInstance(Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("cataclysm", "blazing_brand"))), 300, Math.min(4,buffLevel+1) ));
                    }else{
                        mob.addEffect(new MobEffectInstance(Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("cataclysm", "blazing_brand"))), 300, 0));
                        map.put(Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("cataclysm", "blazing_brand"))),
                                new MobEffectInstance(Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("cataclysm", "blazing_brand"))), 300, 0));
                    }
                }
            }
            //下界合金巨兽
            if (event.getEntity()!=null&&event.getSource().is(IS_FIRE)) {
                LivingEntity livingEntity = event.getEntity();
                int effectLevel = (MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectLevel(livingEntity, netherite_base_Effect));
                if(effectLevel<100){
                    event.setAmount(event.getAmount()*(1-effectLevel));
                }else{
                    event.setCanceled(true);
                }
            }
            if (event.getSource().getEntity() instanceof LivingEntity livingEntity) {
                var mob = event.getEntity();
                float effectLevel = (MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectEfficiency(livingEntity, netherite_base_Effect));
                float damage = event.getAmount();
                if (MTTDamageSourceHelper.isMeleeAttack(event.getSource())&&effectLevel>0) {
                    var DamageType = MTTTickZero.hasSource(livingEntity.level(), DamageTypes.LAVA, livingEntity);
                    mob.invulnerableTime = 0;
                    mob.hurt(DamageType, damage * effectLevel/100);
                    mob.invulnerableTime = 0;
                }
            }else if (event.getSource().getDirectEntity() instanceof LivingEntity livingEntity) {
                var mob = event.getEntity();
                float effectLevel = (MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectEfficiency(livingEntity, netherite_base_Effect));
                float damage = event.getAmount();
                if (MTTDamageSourceHelper.isMeleeAttack(event.getSource())&&effectLevel>0) {
                    var DamageType = MTTTickZero.hasSource(livingEntity.level(), DamageTypes.LAVA, livingEntity);
                    mob.invulnerableTime = 0;
                    mob.hurt(DamageType, damage * effectLevel/100);
                    mob.invulnerableTime = 0;
                }
            }
        }
    }
    /// 暴击事件
    @SubscribeEvent
    public static void onLivingAttack(CriticalHitEvent event) {
        /// 挨打的
        Entity attacked = event.getTarget();
        /// 攻击的
        LivingEntity attacker = event.getEntity();
        ItemStack weapon = attacker.getMainHandItem();
        if (!weapon.isEmpty()) {
            if (attacked instanceof LivingEntity livingEntity) {
                //获取主手的锤1词条（爆伤
                float effectLevel = (MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectLevel(attacker, soul_annihilator_Effect));
                if (effectLevel>0) {
                    event.setDamageModifier(1.5F+effectLevel/100);
                }
            }
        }
    }
}
