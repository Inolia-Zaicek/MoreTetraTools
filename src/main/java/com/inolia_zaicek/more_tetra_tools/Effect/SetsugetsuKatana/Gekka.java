package com.inolia_zaicek.more_tetra_tools.Effect.SetsugetsuKatana;

import com.inolia_zaicek.more_tetra_tools.Register.MTTEffectsRegister;
import com.inolia_zaicek.more_tetra_tools.Util.MTTDamageSourceHelper;
import com.inolia_zaicek.more_tetra_tools.Util.MTTEffectHelper;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import se.mickelus.tetra.items.modular.IModularItem;

import static com.inolia_zaicek.more_tetra_tools.Effect.Clent.MTTEffectGuiStats.*;

public class Gekka {
    @SubscribeEvent
    public static void hurt(LivingHurtEvent event) {
        if (MTTDamageSourceHelper.isMeleeAttack(event.getSource())) {
            if (event.getSource().getEntity() instanceof LivingEntity livingEntity) {
                //如果是玩家，但是计时器不满足
                if (livingEntity instanceof Player player && player.getAttackStrengthScale(0.5f) <= 0.9f) {
                    return;
                }
                var mob = event.getEntity();
                var map = mob.getActiveEffectsMap();
                int effectLevel = MTTEffectHelper.getInstance().getMainOffHandSumEffectLevel(livingEntity, gekka_Effect);
                if (effectLevel > 0) {
                    if (mob.hasEffect(MTTEffectsRegister.SetsugetsuKa.get())) {
                        int buffLevel = mob.getEffect(MTTEffectsRegister.SetsugetsuKa.get()).getAmplifier();
                        int buffTime = mob.getEffect(MTTEffectsRegister.SetsugetsuKa.get()).getDuration();
                        map.put(MTTEffectsRegister.SetsugetsuKa.get(),
                                new MobEffectInstance(MTTEffectsRegister.SetsugetsuKa.get(), buffTime, buffLevel + effectLevel));
                    } else {
                        map.put(MTTEffectsRegister.SetsugetsuKa.get(),
                                new MobEffectInstance(MTTEffectsRegister.SetsugetsuKa.get(), 200, effectLevel - 1));
                    }
                }
            } else if (event.getSource().getDirectEntity() instanceof LivingEntity livingEntity) {
                //如果是玩家，但是计时器不满足
                if (livingEntity instanceof Player player && player.getAttackStrengthScale(0.5f) <= 0.9f) {
                    return;
                }
                var mob = event.getEntity();
                var map = mob.getActiveEffectsMap();
                int effectLevel = MTTEffectHelper.getInstance().getMainOffHandSumEffectLevel(livingEntity, gekka_Effect);
                if (effectLevel > 0) {
                    if (mob.hasEffect(MTTEffectsRegister.SetsugetsuKa.get())) {
                        int buffLevel = mob.getEffect(MTTEffectsRegister.SetsugetsuKa.get()).getAmplifier();
                        int buffTime = mob.getEffect(MTTEffectsRegister.SetsugetsuKa.get()).getDuration();
                        map.put(MTTEffectsRegister.SetsugetsuKa.get(),
                                new MobEffectInstance(MTTEffectsRegister.SetsugetsuKa.get(), buffTime, buffLevel + effectLevel));
                    } else {
                        map.put(MTTEffectsRegister.SetsugetsuKa.get(),
                                new MobEffectInstance(MTTEffectsRegister.SetsugetsuKa.get(), 200, effectLevel - 1));
                    }
                }
            }
        }
    }
}