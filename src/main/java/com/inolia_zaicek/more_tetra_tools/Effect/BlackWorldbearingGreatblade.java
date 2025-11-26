package com.inolia_zaicek.more_tetra_tools.Effect;

import com.inolia_zaicek.more_tetra_tools.Damage.MTTTickZero;
import com.inolia_zaicek.more_tetra_tools.Register.MTTEffectsRegister;
import com.inolia_zaicek.more_tetra_tools.Util.MTTDamageSourceHelper;
import com.inolia_zaicek.more_tetra_tools.Util.MTTEffectHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import se.mickelus.tetra.items.modular.IModularItem;

import static com.inolia_zaicek.more_tetra_tools.Effect.Clent.MTTEffectGuiStats.*;

public class BlackWorldbearingGreatblade {
    @SubscribeEvent
    public static void hurt(LivingHurtEvent event) {
        if (event.getSource().getEntity() instanceof LivingEntity livingEntity) {
            float effectLevel = MTTEffectHelper.getInstance().getMainOffHandSumEffectLevel(livingEntity, butSufferingIsEssentialEffect);
            float effectLevel2 = MTTEffectHelper.getInstance().getMainOffHandSumEffectLevel(livingEntity, cryNotForTheDiscardedEffect);
            var mob = event.getEntity();
            var map = mob.getActiveEffectsMap();
            if (MTTDamageSourceHelper.isMeleeAttack(event.getSource())) {
                //叠加容器
                if (effectLevel > 0 && !livingEntity.hasEffect(MTTEffectsRegister.SilentSorrow.get())) {
                    if (livingEntity.hasEffect(MTTEffectsRegister.LongShatteredVessel.get())) {
                        int buffLevel = livingEntity.getEffect(MTTEffectsRegister.LongShatteredVessel.get()).getAmplifier();
                        livingEntity.addEffect(new MobEffectInstance(MTTEffectsRegister.LongShatteredVessel.get(), 200, Math.min(11,buffLevel + 1) ));
                    } else {
                        livingEntity.addEffect(new MobEffectInstance(MTTEffectsRegister.LongShatteredVessel.get(), 200, 0));
                    }
                }
                //莫因舍弃而哭泣
                if(effectLevel2 > 0 && livingEntity.hasEffect(MTTEffectsRegister.SilentSorrow.get())) {
                    int buffLevel = livingEntity.getEffect(MTTEffectsRegister.SilentSorrow.get()).getAmplifier() + 1;
                    float number = effectLevel2 / 100;
                    float damage = event.getAmount();
                    var DamageType = MTTTickZero.hasSource(livingEntity.level(), MTTTickZero.TICKAMAGE, livingEntity);
                    //连击
                    for (int i = 0; i < buffLevel; i++) {
                        if(livingEntity instanceof Player player) {
                            mob.setLastHurtByPlayer(player);
                        }
                        mob.invulnerableTime = 0;
                        mob.hurt(DamageType, damage * number);
                    }
                }
            }
        }
        if (event.getEntity()!=null) {
            var livingEntity = event.getEntity();
            float effectLevel = MTTEffectHelper.getInstance().getMainOffHandSumEffectLevel(livingEntity, butSufferingIsEssentialEffect);
                if (effectLevel > 0&&livingEntity.hasEffect(MTTEffectsRegister.LongShatteredVessel.get())) {
                    int buffLevel = livingEntity.getEffect(MTTEffectsRegister.LongShatteredVessel.get()).getAmplifier()+1;
                    float number = effectLevel / 100;
                    event.setAmount(event.getAmount()*(1-buffLevel*number/12));
                }
        }
    }
}