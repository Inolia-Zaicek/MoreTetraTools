package com.inolia_zaicek.more_tetra_tools.Event;

import com.inolia_zaicek.more_tetra_tools.Register.MTTEffectsRegister;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class GrievousWoundsEvent {
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void heal(LivingHealEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity.hasEffect(MTTEffectsRegister.GrievousWounds.get())) {
            int buffLevel = entity.getEffect(MTTEffectsRegister.GrievousWounds.get()).getAmplifier();
            //百分比禁疗
            if (buffLevel >= 100) {
                event.setAmount(0);
            } else {
                //禁疗比例
                float number=1-( (float) buffLevel/100 );
                float heal = event.getAmount();
                event.setAmount(heal*number);
            }
        }
    }
}
