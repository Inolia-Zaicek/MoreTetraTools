package com.inolia_zaicek.more_tetra_tools.Effect.ReturnToAshKatana;

import com.inolia_zaicek.more_tetra_tools.Damage.MTTTickZero;
import com.inolia_zaicek.more_tetra_tools.Util.MTTEffectHelper;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static com.inolia_zaicek.more_tetra_tools.Effect.Clent.MTTEffectGuiStats.*;
import static net.minecraft.tags.DamageTypeTags.IS_FIRE;
import static net.minecraft.tags.DamageTypeTags.IS_FREEZING;

public class AshToAshDamageUp {
    @SubscribeEvent
    public static void hurt(LivingHurtEvent event) {
        if (event.getSource().is(IS_FIRE) || event.getSource().is(MTTTickZero.TickFireDamage)) {
            if (event.getSource().getEntity() instanceof LivingEntity livingEntity) {
                float effectLevel = MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectLevel(livingEntity, ash_to_ash_Effect);
                if (effectLevel > 0) {
                    float baseDamage = event.getAmount();
                    if(event.getEntity()!=null&&event.getEntity().getRemainingFireTicks()>0){
                        event.setAmount(baseDamage * (1 + effectLevel / 50));
                        event.getEntity().setRemainingFireTicks(0);
                    }else {
                        event.setAmount(baseDamage * (1 + effectLevel / 100));
                    }
                }
            }else if (event.getSource().getDirectEntity() instanceof LivingEntity livingEntity) {
                float effectLevel = MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectLevel(livingEntity, ash_to_ash_Effect);
                if (effectLevel > 0) {
                    float baseDamage = event.getAmount();
                    if(event.getEntity()!=null&&event.getEntity().getRemainingFireTicks()>0){
                        event.setAmount(baseDamage * (1 + effectLevel / 50));
                        event.getEntity().setRemainingFireTicks(0);
                    }else {
                        event.setAmount(baseDamage * (1 + effectLevel / 100));
                    }
                }
            }
        }
    }
}