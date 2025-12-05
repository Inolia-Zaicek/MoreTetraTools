package com.inolia_zaicek.more_tetra_tools.Effect.RipplesOfThePast;

import com.inolia_zaicek.more_tetra_tools.MoreTetraTools;
import com.inolia_zaicek.more_tetra_tools.Register.MTTEffectsRegister;
import com.inolia_zaicek.more_tetra_tools.Util.MTTEffectHelper;
import com.inolia_zaicek.more_tetra_tools.Util.MTTUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.inolia_zaicek.more_tetra_tools.Effect.Clent.MTTEffectGuiStats.bloom_elysium_of_beyond_Effect;
import static com.inolia_zaicek.more_tetra_tools.Effect.Clent.MTTEffectGuiStats.verse_zero_vow_infinite_Effect;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE,modid = MoreTetraTools.MODID)
public class VerseZeroVowInfinite {
    //乐土状态赋予
    @SubscribeEvent
    public static void tick(LivingEvent.LivingTickEvent event) {
        LivingEntity livingEntity = event.getEntity();
        int effectLevel = MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectLevel(livingEntity, verse_zero_vow_infinite_Effect);
        if (effectLevel > 0) {
            //范围
            var mobList = MTTUtil.mobList(13, livingEntity);
            var playerList = MTTUtil.PlayerList(13, livingEntity);
            //追忆满24且无涟漪，进入涟漪状态
            if (livingEntity.hasEffect(MTTEffectsRegister.Recollection.get()) && ! livingEntity.hasEffect(MTTEffectsRegister.RipplesOfPastReverie.get())) {
                int recollection = livingEntity.getEffect(MTTEffectsRegister.Recollection.get()).getAmplifier();
                if(recollection>=23){
                    var map = livingEntity.getActiveEffectsMap();
                    map.put(MTTEffectsRegister.RipplesOfPastReverie.get(),
                            new MobEffectInstance(MTTEffectsRegister.RipplesOfPastReverie.get(), 20*13, effectLevel-1));
                    livingEntity.removeEffect(MTTEffectsRegister.Recollection.get());
                }
            }
            //涟漪状态下——吸收周围实体的追忆——回复秒数
            if ( livingEntity.hasEffect(MTTEffectsRegister.RipplesOfPastReverie.get()) ) {
                for (Mob mobs : mobList) {
                    //周围单位有追忆
                    if (mobs.hasEffect(MTTEffectsRegister.Recollection.get())) {
                        //获取外追忆等级，自己buff等级与时间
                        int otherRecollection = 1+mobs.getEffect(MTTEffectsRegister.Recollection.get()).getAmplifier();
                        int myEffectLevel = livingEntity.getEffect(MTTEffectsRegister.RipplesOfPastReverie.get()).getAmplifier();
                        int myEffectTime = livingEntity.getEffect(MTTEffectsRegister.RipplesOfPastReverie.get()).getDuration();
                        //为自己赋予更长时间的buff
                        livingEntity.addEffect(new MobEffectInstance(MTTEffectsRegister.RipplesOfPastReverie.get(), myEffectTime+5*otherRecollection, myEffectLevel));
                        //去除追忆
                        mobs.removeEffect(MTTEffectsRegister.Recollection.get());
                    }
                }
                for (Player player : playerList) {
                    //周围单位有追忆且不是自己
                    if (player.hasEffect(MTTEffectsRegister.Recollection.get())) {
                        //获取外追忆等级，自己buff等级与时间
                        int otherRecollection = 1+player.getEffect(MTTEffectsRegister.Recollection.get()).getAmplifier();
                        int myEffectLevel = livingEntity.getEffect(MTTEffectsRegister.RipplesOfPastReverie.get()).getAmplifier();
                        int myEffectTime = livingEntity.getEffect(MTTEffectsRegister.RipplesOfPastReverie.get()).getDuration();
                        //为自己赋予更长时间的buff
                        livingEntity.addEffect(new MobEffectInstance(MTTEffectsRegister.RipplesOfPastReverie.get(), myEffectTime+5*otherRecollection, myEffectLevel));
                        //去除追忆
                        player.removeEffect(MTTEffectsRegister.Recollection.get());
                    }
                }
            }
            //无涟漪状态下——吸收周围追忆，获得追忆等级
            else{
                for (Mob mobs : mobList) {
                    //周围单位有追忆
                    if (mobs.hasEffect(MTTEffectsRegister.Recollection.get())&&mobs!=livingEntity) {
                        int otherRecollection = 1+mobs.getEffect(MTTEffectsRegister.Recollection.get()).getAmplifier();
                        //自己有追忆——自己追忆等级+其他的
                        if (livingEntity.hasEffect(MTTEffectsRegister.Recollection.get())) {
                            int recollection = livingEntity.getEffect(MTTEffectsRegister.Recollection.get()).getAmplifier();
                            livingEntity.addEffect(new MobEffectInstance(MTTEffectsRegister.Recollection.get(), 100, recollection+otherRecollection));
                        }
                        //自己没有追忆————直接继承
                        else {
                            livingEntity.addEffect(new MobEffectInstance(MTTEffectsRegister.Recollection.get(), 100, otherRecollection));
                        }
                        //去除追忆
                        mobs.removeEffect(MTTEffectsRegister.Recollection.get());
                    }
                }
                for (Player player : playerList) {
                    //周围单位有追忆
                    if (player.hasEffect(MTTEffectsRegister.Recollection.get())&&player!=livingEntity) {
                        int otherRecollection = 1+player.getEffect(MTTEffectsRegister.Recollection.get()).getAmplifier();
                        //自己有追忆——自己追忆等级+其他的
                        if (livingEntity.hasEffect(MTTEffectsRegister.Recollection.get())) {
                            int recollection = livingEntity.getEffect(MTTEffectsRegister.Recollection.get()).getAmplifier();
                            livingEntity.addEffect(new MobEffectInstance(MTTEffectsRegister.Recollection.get(), 100, recollection+otherRecollection));
                        }
                        //自己没有追忆————直接继承
                        else {
                            livingEntity.addEffect(new MobEffectInstance(MTTEffectsRegister.Recollection.get(), 100, otherRecollection));
                        }
                        //去除追忆
                        player.removeEffect(MTTEffectsRegister.Recollection.get());
                    }
                }
            }
        }
    }
}
