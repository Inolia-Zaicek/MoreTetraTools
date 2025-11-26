package com.inolia_zaicek.more_tetra_tools.Effect;

import com.inolia_zaicek.more_tetra_tools.Damage.MTTTickZero;
import com.inolia_zaicek.more_tetra_tools.Util.MTTEffectHelper;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static com.inolia_zaicek.more_tetra_tools.Effect.Clent.MTTEffectGuiStats.horrible_Effect;

public class Horrible {
    @SubscribeEvent
    public static void hurt(LivingHurtEvent event) {

        if (event.getSource().getEntity() instanceof LivingEntity livingEntity) {
            float effectLevel = MTTEffectHelper.getInstance().getMainHandEffectLevel(livingEntity, horrible_Effect);
            if(effectLevel>0){
                var DamageType = MTTTickZero.source(livingEntity.level(), DamageTypes.MAGIC);
                float finish = event.getAmount()*(1+effectLevel/100);
                //秒不掉
                if(event.getEntity()!=null && finish<event.getEntity().getMaxHealth() ){
                    float hurt = livingEntity.getMaxHealth()*0.05f;
                    event.setAmount(event.getAmount()*(1+effectLevel/300));
                    if(livingEntity.getHealth()-hurt>0) {
                        livingEntity.hurt(DamageType,hurt);
                    }else{
                        livingEntity.hurt(DamageType,0);
                    }
                }else{
                    event.setAmount(event.getAmount()*(1+effectLevel/100));
                }
            }
        }else if (event.getSource().getDirectEntity() instanceof LivingEntity livingEntity) {
            float effectLevel = MTTEffectHelper.getInstance().getMainHandEffectLevel(livingEntity, horrible_Effect);
            if(effectLevel>0){
                var DamageType = MTTTickZero.source(livingEntity.level(), DamageTypes.MAGIC);
                float finish = event.getAmount()*(1+effectLevel/100);
                //秒不掉
                if(event.getEntity()!=null && finish<event.getEntity().getMaxHealth() ){
                    float hurt = livingEntity.getMaxHealth()*0.05f;
                    event.setAmount(event.getAmount()*(1+effectLevel/300));
                    if(livingEntity.getHealth()-hurt>0) {
                        livingEntity.hurt(DamageType,hurt);
                    }else{
                        livingEntity.hurt(DamageType,0);
                    }
                }else{
                    event.setAmount(event.getAmount()*(1+effectLevel/100));
                }
            }
        }
    }
}
