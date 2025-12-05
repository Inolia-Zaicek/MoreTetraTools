package com.inolia_zaicek.more_tetra_tools.Event;

import com.inolia_zaicek.more_tetra_tools.Damage.MTTTickZero;
import com.inolia_zaicek.more_tetra_tools.MoreTetraTools;
import com.inolia_zaicek.more_tetra_tools.Register.MTTEffectsRegister;
import com.inolia_zaicek.more_tetra_tools.Util.MTTUtil;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.inolia_zaicek.more_tetra_tools.Effect.Clent.MTTEffectGuiStats.isUltimateBossEntity;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE,modid = MoreTetraTools.MODID)
public class SetsugetsuKaEvent {
    @SubscribeEvent
    //退出
    public static void buffOut(MobEffectEvent.Expired event) {
        MobEffectInstance expiredInstance = event.getEffectInstance();
        if (expiredInstance != null) {
            MobEffect expiredEffect = expiredInstance.getEffect();
            if (event.getEntity()!=null && expiredEffect == MTTEffectsRegister.SetsugetsuKa.get()) {
                LivingEntity livingEntity = event.getEntity();
                //buff等级
                int buffLevel = 1+livingEntity.getEffect(MTTEffectsRegister.SetsugetsuKa.get()).getAmplifier();
                if(isUltimateBossEntity(event.getEntity().getType())&&buffLevel > 0){
                    buffLevel=buffLevel*10;
                }
                var DamageType = MTTTickZero.source(livingEntity.level(), MTTTickZero.TickFreezeDamage);
                //伤害
                livingEntity.hurt(DamageType,buffLevel*3);
                //防止无掉落物
                var playerList = MTTUtil.PlayerList(13, livingEntity);
                for (Player player : playerList) {
                    //有玩家
                    if(player!=null) {
                        livingEntity.setLastHurtByPlayer(player);
                    }else{
                        //没玩家
                        var mobList = MTTUtil.mobList(13, livingEntity);
                        for (Mob mobs : mobList) {
                            //有主人的随从
                            if ( (mobs instanceof OwnableEntity ownableEntity && ownableEntity.getOwnerUUID() != null) ) {
                                livingEntity.setLastHurtByMob(mobs);
                            }
                        }
                    }
                }
            }
        }
    }
}
