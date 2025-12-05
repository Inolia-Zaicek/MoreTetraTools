package com.inolia_zaicek.more_tetra_tools.Effect;

import com.inolia_zaicek.more_tetra_tools.Register.MTTEffectsRegister;
import com.inolia_zaicek.more_tetra_tools.Util.MTTEffectHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.Random;

import static com.inolia_zaicek.more_tetra_tools.Effect.Clent.MTTEffectGuiStats.*;

public class JiaQiuBattlestaff {
    @SubscribeEvent
    public static void hurt(LivingHurtEvent event) {
        //攻击概率神威
        if (event.getSource().getEntity() instanceof LivingEntity livingEntity) {
            //如果是玩家，但是计时器不满足
            if (livingEntity instanceof Player player && player.getAttackStrengthScale(0.5f) <= 0.9f) {
                return;
            }
            LivingEntity mob = event.getEntity();
            var map = mob.getActiveEffectsMap();
            float chance = (MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectLevel(livingEntity, act_not_with_impropriety_Effect));
            float effectLevel = (MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectLevel(livingEntity, thunderstrike_Effect));
            Random random = new Random();
            //概率施加负面
            if(chance>0 && random.nextInt(100) <= chance) {
                Random random3 = new Random();
                if (random3.nextInt(100) <= 33) {
                    if(mob.hasEffect(MTTEffectsRegister.DeathriteOfHalt.get())) {
                        int buffLevel = mob.getEffect(MTTEffectsRegister.DeathriteOfHalt.get()).getAmplifier();
                        int buffTime = mob.getEffect(MTTEffectsRegister.DeathriteOfHalt.get()).getDuration();
                        map.put(MTTEffectsRegister.DeathriteOfHalt.get(),
                                new MobEffectInstance(MTTEffectsRegister.DeathriteOfHalt.get(), buffTime, Math.min(4, buffLevel + 1) ));
                    }else{
                        map.put(MTTEffectsRegister.DeathriteOfHalt.get(),
                                new MobEffectInstance(MTTEffectsRegister.DeathriteOfHalt.get(), 300, 0));
                    }
                }
                else if (random3.nextInt(100) <= 66 && random.nextInt(100) > 33) {
                    if(mob.hasEffect(MTTEffectsRegister.DeathriteOfImpede.get())) {
                        int buffLevel = mob.getEffect(MTTEffectsRegister.DeathriteOfImpede.get()).getAmplifier();
                        int buffTime = mob.getEffect(MTTEffectsRegister.DeathriteOfImpede.get()).getDuration();
                        map.put(MTTEffectsRegister.DeathriteOfImpede.get(),
                                new MobEffectInstance(MTTEffectsRegister.DeathriteOfImpede.get(), buffTime, Math.min(4, buffLevel + 1) ));
                    }else{
                        map.put(MTTEffectsRegister.DeathriteOfImpede.get(),
                                new MobEffectInstance(MTTEffectsRegister.DeathriteOfImpede.get(), 300, 0));
                    }
                }
                else if (random3.nextInt(100) > 66) {
                    if(mob.hasEffect(MTTEffectsRegister.DeathriteOfWane.get())) {
                        int buffLevel = mob.getEffect(MTTEffectsRegister.DeathriteOfWane.get()).getAmplifier();
                        int buffTime = mob.getEffect(MTTEffectsRegister.DeathriteOfWane.get()).getDuration();
                        map.put(MTTEffectsRegister.DeathriteOfWane.get(),
                                new MobEffectInstance(MTTEffectsRegister.DeathriteOfWane.get(), buffTime, Math.min(4, buffLevel + 1) ));
                    }else{
                        map.put(MTTEffectsRegister.DeathriteOfWane.get(),
                                new MobEffectInstance(MTTEffectsRegister.DeathriteOfWane.get(), 300, 0));
                    }
                }
            }
            //增伤
            if(mob.hasEffect(MTTEffectsRegister.DeathriteOfHalt.get())) {
                int buffLevel = mob.getEffect(MTTEffectsRegister.DeathriteOfHalt.get()).getAmplifier();
                int buffTime = mob.getEffect(MTTEffectsRegister.DeathriteOfHalt.get()).getDuration();
                event.setAmount(event.getAmount()*(1+buffLevel*effectLevel/100) );
            }
        }else if (event.getSource().getDirectEntity() instanceof LivingEntity livingEntity) {
            //如果是玩家，但是计时器不满足
            if (livingEntity instanceof Player player && player.getAttackStrengthScale(0.5f) <= 0.9f) {
                return;
            }
            LivingEntity mob = event.getEntity();
            var map = mob.getActiveEffectsMap();
            float chance = (MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectLevel(livingEntity, act_not_with_impropriety_Effect));
            float effectLevel = (MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectLevel(livingEntity, thunderstrike_Effect));
            Random random = new Random();
            //概率施加负面
            if(chance>0 && random.nextInt(100) <= chance) {
                Random random3 = new Random();
                if (random3.nextInt(100) <= 33) {
                    if(mob.hasEffect(MTTEffectsRegister.DeathriteOfHalt.get())) {
                        int buffLevel = mob.getEffect(MTTEffectsRegister.DeathriteOfHalt.get()).getAmplifier();
                        int buffTime = mob.getEffect(MTTEffectsRegister.DeathriteOfHalt.get()).getDuration();
                        map.put(MTTEffectsRegister.DeathriteOfHalt.get(),
                                new MobEffectInstance(MTTEffectsRegister.DeathriteOfHalt.get(), buffTime, Math.min(4, buffLevel + 1) ));
                    }else{
                        map.put(MTTEffectsRegister.DeathriteOfHalt.get(),
                                new MobEffectInstance(MTTEffectsRegister.DeathriteOfHalt.get(), 300, 0));
                    }
                }
                else if (random3.nextInt(100) <= 66 && random.nextInt(100) > 33) {
                    if(mob.hasEffect(MTTEffectsRegister.DeathriteOfImpede.get())) {
                        int buffLevel = mob.getEffect(MTTEffectsRegister.DeathriteOfImpede.get()).getAmplifier();
                        int buffTime = mob.getEffect(MTTEffectsRegister.DeathriteOfImpede.get()).getDuration();
                        map.put(MTTEffectsRegister.DeathriteOfImpede.get(),
                                new MobEffectInstance(MTTEffectsRegister.DeathriteOfImpede.get(), buffTime, Math.min(4, buffLevel + 1) ));
                    }else{
                        map.put(MTTEffectsRegister.DeathriteOfImpede.get(),
                                new MobEffectInstance(MTTEffectsRegister.DeathriteOfImpede.get(), 300, 0));
                    }
                }
                else if (random3.nextInt(100) > 66) {
                    if(mob.hasEffect(MTTEffectsRegister.DeathriteOfWane.get())) {
                        int buffLevel = mob.getEffect(MTTEffectsRegister.DeathriteOfWane.get()).getAmplifier();
                        int buffTime = mob.getEffect(MTTEffectsRegister.DeathriteOfWane.get()).getDuration();
                        map.put(MTTEffectsRegister.DeathriteOfWane.get(),
                                new MobEffectInstance(MTTEffectsRegister.DeathriteOfWane.get(), buffTime, Math.min(4, buffLevel + 1) ));
                    }else{
                        map.put(MTTEffectsRegister.DeathriteOfWane.get(),
                                new MobEffectInstance(MTTEffectsRegister.DeathriteOfWane.get(), 300, 0));
                    }
                }
            }
            //增伤
            if(mob.hasEffect(MTTEffectsRegister.DeathriteOfHalt.get())) {
                int buffLevel = mob.getEffect(MTTEffectsRegister.DeathriteOfHalt.get()).getAmplifier();
                event.setAmount(event.getAmount()*(1+buffLevel*effectLevel/100) );
            }
        }
        //挨打
        if (event.getSource().getEntity() instanceof LivingEntity mob) {
            LivingEntity livingEntity = event.getEntity();
            float effectLevel = (MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectEfficiency(livingEntity, thunderstrike_Effect));
            //增伤
            if(mob.hasEffect(MTTEffectsRegister.DeathriteOfImpede.get())) {
                int buffLevel = mob.getEffect(MTTEffectsRegister.DeathriteOfImpede.get()).getAmplifier();
                event.setAmount(event.getAmount()*(1-buffLevel*effectLevel/100) );
            }
        }else if (event.getSource().getDirectEntity() instanceof LivingEntity mob) {
            LivingEntity livingEntity = event.getEntity();
            float effectLevel = (MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectEfficiency(livingEntity, thunderstrike_Effect));
            //增伤
            if(mob.hasEffect(MTTEffectsRegister.DeathriteOfImpede.get())) {
                int buffLevel = mob.getEffect(MTTEffectsRegister.DeathriteOfImpede.get()).getAmplifier();
                event.setAmount(event.getAmount()*(1-buffLevel*effectLevel/100) );
            }
        }
    }
}
