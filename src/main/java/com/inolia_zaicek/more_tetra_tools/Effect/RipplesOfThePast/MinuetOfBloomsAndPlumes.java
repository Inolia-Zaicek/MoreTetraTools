package com.inolia_zaicek.more_tetra_tools.Effect.RipplesOfThePast;

import com.inolia_zaicek.more_tetra_tools.Damage.MTTTickZero;
import com.inolia_zaicek.more_tetra_tools.Register.MTTEffectsRegister;
import com.inolia_zaicek.more_tetra_tools.Util.MTTEffectHelper;
import com.inolia_zaicek.more_tetra_tools.Util.MTTUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static com.inolia_zaicek.more_tetra_tools.Effect.Clent.MTTEffectGuiStats.minuet_of_blooms_and_plumes_Effect;
import static net.minecraft.tags.DamageTypeTags.IS_PROJECTILE;
import static net.minecraft.tags.DamageTypeTags.WITCH_RESISTANT_TO;

public class MinuetOfBloomsAndPlumes {
    @SubscribeEvent
    public static void hurt(LivingHurtEvent event) {
        //弹射物
        if (event.getSource().is(IS_PROJECTILE) && !event.getSource().is(MTTTickZero.TickFreezeDamage) ) {
            if (event.getSource().getEntity() instanceof LivingEntity livingEntity) {
                float effectLevel = MTTEffectHelper.getInstance().getMainOffHandSumEffectLevel(livingEntity, minuet_of_blooms_and_plumes_Effect);
                if (effectLevel > 0) {
                    LivingEntity mob = event.getEntity();
                    float number = effectLevel / 100;
                    float hp = (float) livingEntity.getAttributeValue(Attributes.MAX_HEALTH);
                    float damage = event.getAmount() * number;
                    var DamageType = MTTTickZero.hasSource(livingEntity.level(), MTTTickZero.TickFreezeDamage, livingEntity);
                    //涟漪状态
                    if (livingEntity.hasEffect(MTTEffectsRegister.RipplesOfPastReverie.get())){
                        mob.hurt(DamageType, hp * number + damage);
                    }else {
                        mob.hurt(DamageType, hp * number);
                    }
                }
            }
            else if (event.getSource().getDirectEntity() instanceof LivingEntity livingEntity) {
                float effectLevel = MTTEffectHelper.getInstance().getMainOffHandSumEffectLevel(livingEntity, minuet_of_blooms_and_plumes_Effect);
                if (effectLevel > 0) {
                    LivingEntity mob = event.getEntity();
                    float number = effectLevel / 100;
                    float hp = (float) livingEntity.getAttributeValue(Attributes.MAX_HEALTH);
                    float damage = event.getAmount() * number;
                    var DamageType = MTTTickZero.hasSource(livingEntity.level(), MTTTickZero.TickFreezeDamage, livingEntity);
                    //涟漪状态
                    if (livingEntity.hasEffect(MTTEffectsRegister.RipplesOfPastReverie.get())){
                        mob.hurt(DamageType, hp * number + damage);
                    }else {
                        mob.hurt(DamageType, hp * number);
                    }
                }
            }
        }
    }
}