package com.inolia_zaicek.more_tetra_tools.Event;

import com.inolia_zaicek.more_tetra_tools.Damage.MTTTickZero;
import com.inolia_zaicek.more_tetra_tools.MoreTetraTools;
import com.inolia_zaicek.more_tetra_tools.Register.MTTEffectsRegister;
import com.inolia_zaicek.more_tetra_tools.Util.MTTUtil;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.inolia_zaicek.more_tetra_tools.Effect.Clent.MTTEffectGuiStats.isUltimateBossEntity;

public class DeathriteOfWaneEvent {
    @SubscribeEvent
    public static void hurt(LivingHurtEvent event) {
        if (event.getEntity() != null) {
            LivingEntity livingEntity = event.getEntity();
            if (livingEntity.hasEffect(MTTEffectsRegister.DeathriteOfWane.get())) {
                //buff等级
                int buffLevel = livingEntity.getEffect(MTTEffectsRegister.DeathriteOfWane.get()).getAmplifier();
                event.setAmount(event.getAmount()+ (buffLevel+1)*2 );
                //防止无掉落物
                var playerList = MTTUtil.PlayerList(13, livingEntity);
                for (Player player : playerList) {
                    //有玩家
                    if (player != null) {
                        livingEntity.setLastHurtByPlayer(player);
                    } else {
                        //没玩家
                        var mobList = MTTUtil.mobList(13, livingEntity);
                        for (Mob mobs : mobList) {
                            //有主人的随从
                            if ((mobs instanceof OwnableEntity ownableEntity && ownableEntity.getOwnerUUID() != null)) {
                                livingEntity.setLastHurtByMob(mobs);
                            }
                        }
                    }
                }
            }
        }
    }
}