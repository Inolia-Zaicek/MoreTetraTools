package com.inolia_zaicek.more_tetra_tools.Event;

import com.inolia_zaicek.more_tetra_tools.MoreTetraTools;
import com.inolia_zaicek.more_tetra_tools.Register.MTTEffectsRegister;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import se.mickelus.tetra.blocks.workbench.gui.WorkbenchStatsGui;
import se.mickelus.tetra.gui.stats.StatsHelper;
import se.mickelus.tetra.gui.stats.bar.GuiStatBar;
import se.mickelus.tetra.gui.stats.getter.*;
import se.mickelus.tetra.items.modular.impl.holo.gui.craft.HoloStatsGui;

import static com.inolia_zaicek.more_tetra_tools.Effect.Clent.MTTEffectGuiStats.*;

@Mod.EventBusSubscriber(modid = MoreTetraTools.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ToughnessBreakEvent {
    @OnlyIn(Dist.CLIENT)
    public static void init() {
        var statGetter1 = new StatGetterEffectLevel(chrysalid_pyronexus_Effect, 1);
        var statGetter2 = new StatGetterEffectEfficiency(chrysalid_pyronexus_Effect, 1);
        IStatGetter[] statGetters = {statGetter1, statGetter2};
        IStatFormat[] statFormats = {StatFormat.noDecimal, StatFormat.noDecimal};
        GuiStatBar statBar = new GuiStatBar(0, 0, StatsHelper.barLength,
                chrysalid_pyronexus_Name, 0, 50, false, false, false,
                statGetter1, LabelGetterBasic.integerLabel,
                new TooltipGetterMultiValue(chrysalid_pyronexus_Tooltip, statGetters, statFormats)
        );
        WorkbenchStatsGui.addBar(statBar);
        HoloStatsGui.addBar(statBar);
    }
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    //buff到时间
    public static void buffOut(MobEffectEvent.Expired event) {
        MobEffectInstance expiredInstance = event.getEffectInstance();
        if (expiredInstance != null) {
            MobEffect expiredEffect = expiredInstance.getEffect();
            LivingEntity livingEntity = event.getEntity();
            if (expiredEffect == MTTEffectsRegister.ToughnessBreak.get()) {
                CompoundTag persistentData = livingEntity.getPersistentData();
                persistentData.putBoolean("NoAI",false);
                if(livingEntity instanceof Mob mob){
                    mob.setNoAi(false);
                }
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    //失去buff
    public static void buffOut(MobEffectEvent.Remove event) {
        MobEffectInstance expiredInstance = event.getEffectInstance();
        if (expiredInstance != null) {
            MobEffect expiredEffect = expiredInstance.getEffect();
            LivingEntity livingEntity = event.getEntity();
            if (expiredEffect == MTTEffectsRegister.ToughnessBreak.get()) {
                CompoundTag persistentData = livingEntity.getPersistentData();
                persistentData.putBoolean("NoAI",false);
                if(livingEntity instanceof Mob mob){
                    mob.setNoAi(false);
                }
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    //获得buff
    public static void buffGet(MobEffectEvent.Added event) {
        MobEffectInstance expiredInstance = event.getEffectInstance();
        MobEffect expiredEffect = expiredInstance.getEffect();
        LivingEntity livingEntity = event.getEntity();
        if (expiredEffect == MTTEffectsRegister.ToughnessBreak.get()) {
            CompoundTag persistentData = livingEntity.getPersistentData();
            persistentData.putBoolean("NoAI",true);
            if(livingEntity instanceof Mob mob){
                mob.setNoAi(true);
            }
        }
    }
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    //tick持续赋予
    public static void tick(LivingEvent.LivingTickEvent event) {
        LivingEntity livingEntity = event.getEntity();
        if (livingEntity.hasEffect(MTTEffectsRegister.ToughnessBreak.get())) {
            CompoundTag persistentData = livingEntity.getPersistentData();
            persistentData.putBoolean("NoAI",true);
            if(livingEntity instanceof Mob mob){
                mob.setNoAi(true);
            }
        }
    }
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void hurt(LivingHurtEvent event) {
        if (event.getSource().getEntity() instanceof LivingEntity livingEntity&&livingEntity.hasEffect(MTTEffectsRegister.ToughnessBreak.get())) {
            event.setAmount(0);
        }
        if (event.getSource().getDirectEntity() instanceof LivingEntity livingEntity&&livingEntity.hasEffect(MTTEffectsRegister.ToughnessBreak.get())) {
            event.setAmount(0);
        }
        if (event.getEntity().hasEffect(MTTEffectsRegister.ToughnessBreak.get())) {
            int buffLevel = event.getEntity().getEffect(MTTEffectsRegister.ToughnessBreak.get()).getAmplifier();
            event.setAmount(event.getAmount()*(1+buffLevel*0.1f));
        }
    }
}
