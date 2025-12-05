package com.inolia_zaicek.more_tetra_tools.Effect;

import com.inolia_zaicek.more_tetra_tools.MoreTetraTools;
import com.inolia_zaicek.more_tetra_tools.Register.MTTEffectsRegister;
import com.inolia_zaicek.more_tetra_tools.Util.MTTDamageSourceHelper;
import com.inolia_zaicek.more_tetra_tools.Util.MTTEffectHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import se.mickelus.tetra.blocks.workbench.gui.WorkbenchStatsGui;
import se.mickelus.tetra.gui.stats.StatsHelper;
import se.mickelus.tetra.gui.stats.bar.GuiStatBar;
import se.mickelus.tetra.gui.stats.getter.*;
import se.mickelus.tetra.items.modular.impl.holo.gui.craft.HoloStatsGui;

import static com.inolia_zaicek.more_tetra_tools.Effect.Clent.MTTEffectGuiStats.*;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE,modid = MoreTetraTools.MODID)
public class ButterflyFlurry {
    @OnlyIn(Dist.CLIENT)
    public static void init() {
        var statGetter1 = new StatGetterEffectLevel(sheathed_blade_Effect, 1);
        var statGetter2 = new StatGetterEffectEfficiency(sheathed_blade_Effect, 1);
        IStatGetter[] statGetters = {statGetter1, statGetter2};
        IStatFormat[] statFormats = {StatFormat.noDecimal, StatFormat.noDecimal};
        GuiStatBar statBar1 = new GuiStatBar(0, 0, StatsHelper.barLength,
                sheathed_blade_Name, 0, 100, false, false, false,
                statGetter1, LabelGetterBasic.integerLabel,
                new TooltipGetterMultiValue(sheathed_blade_Tooltip, statGetters, statFormats)
        );
        WorkbenchStatsGui.addBar(statBar1);
        HoloStatsGui.addBar(statBar1);
        var statGetter3 = new StatGetterEffectLevel(resurgence_Effect, 1);
        var statGetter4 = new StatGetterEffectEfficiency(resurgence_Effect, 1);
        IStatGetter[] statGetters1 = {statGetter3, statGetter4};
        IStatFormat[] statFormats1 = {StatFormat.noDecimal, StatFormat.noDecimal};
        GuiStatBar statBar2 = new GuiStatBar(0, 0, StatsHelper.barLength,
                resurgence_Name, 0, 100, false, false, false,
                statGetter3, LabelGetterBasic.integerLabel,
                new TooltipGetterMultiValue(resurgence_Tooltip, statGetters1, statFormats1)
        );
        WorkbenchStatsGui.addBar(statBar2);
        HoloStatsGui.addBar(statBar2);
    }
    @SubscribeEvent
    public static void hurt(LivingHurtEvent event) {
        if (MTTDamageSourceHelper.isMeleeAttack(event.getSource())) {
            if (event.getSource().getEntity() instanceof LivingEntity livingEntity) {
                var mob = event.getEntity();
                int effectLevel1 = MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectLevel(livingEntity, sheathed_blade_Effect);
                float hpLevel = MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectEfficiency(livingEntity, sheathed_blade_Effect);
                int effectLevel2 = MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectLevel(livingEntity, resurgence_Effect);
                float dhp = mob.getHealth()/mob.getMaxHealth();
                //到达阈值
                if( dhp<=(hpLevel/100) &&effectLevel1>0){
                    float number = effectLevel1;
                    //有再现时，伤害提升lvl2
                    if(livingEntity.hasEffect(MTTEffectsRegister.Resurgence.get())){
                        number+=effectLevel2;
                    }
                    //如果该次伤害大于目标生命值，增伤数额+lvl1
                    float finish = event.getAmount()*(1+number/100);
                    if(finish>=mob.getHealth()){
                        number+=effectLevel1;
                    }
                    event.setAmount(event.getAmount() * (1+number/100) );
                }
                //未到达阈值但是可以一击必杀
                if( dhp> (hpLevel/100) && (event.getAmount())>=(mob.getHealth())){
                    float number = effectLevel1*2;
                    //有再现时，伤害提升lvl2
                    if(livingEntity.hasEffect(MTTEffectsRegister.Resurgence.get())){
                        number+=effectLevel2;
                    }
                    event.setAmount(event.getAmount() * (1+number/100) );
                }
            }
        }
    }
    //击杀
    @SubscribeEvent
    public static void entityKilled(LivingDeathEvent event) {
        if (event.getSource().getDirectEntity() instanceof LivingEntity livingEntity) {
            float effectLevel = MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectEfficiency(livingEntity, resurgence_Effect);
            if(effectLevel>0){
                if(!livingEntity.hasEffect(MTTEffectsRegister.Resurgence.get())) {
                    livingEntity.addEffect(new MobEffectInstance(MTTEffectsRegister.Resurgence.get(), (int) (20 * effectLevel), 0));
                }else{
                    int level = livingEntity.getEffect(MTTEffectsRegister.Resurgence.get()).getAmplifier();
                    int time = livingEntity.getEffect(MTTEffectsRegister.Resurgence.get()).getDuration();
                    livingEntity.addEffect(new MobEffectInstance(MTTEffectsRegister.Resurgence.get(),
                            Math.min(60*20,(int) (20 * effectLevel) + time ) ,
                            Math.min(9,level+1)
                    ));
                }
            }
        }else if (event.getSource().getEntity() instanceof LivingEntity livingEntity) {
            float effectLevel = MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectEfficiency(livingEntity, resurgence_Effect);
            if(effectLevel>0){
                if(!livingEntity.hasEffect(MTTEffectsRegister.Resurgence.get())) {
                    livingEntity.addEffect(new MobEffectInstance(MTTEffectsRegister.Resurgence.get(), (int) (20 * effectLevel), 0));
                }else{
                    int level = livingEntity.getEffect(MTTEffectsRegister.Resurgence.get()).getAmplifier();
                    int time = livingEntity.getEffect(MTTEffectsRegister.Resurgence.get()).getDuration();
                    livingEntity.addEffect(new MobEffectInstance(MTTEffectsRegister.Resurgence.get(),
                            Math.min(60*20,(int) (20 * effectLevel) + time ) ,
                            Math.min(9,level+1)
                    ));
                }
            }
        }
    }
}
