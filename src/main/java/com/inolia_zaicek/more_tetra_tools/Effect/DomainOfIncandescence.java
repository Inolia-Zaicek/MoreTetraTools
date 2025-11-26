package com.inolia_zaicek.more_tetra_tools.Effect;

import com.inolia_zaicek.more_tetra_tools.Damage.MTTTickZero;
import com.inolia_zaicek.more_tetra_tools.Register.MTTEffectsRegister;
import com.inolia_zaicek.more_tetra_tools.Util.MTTDamageSourceHelper;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import se.mickelus.tetra.items.modular.IModularItem;

import static com.inolia_zaicek.more_tetra_tools.Effect.Clent.MTTEffectGuiStats.*;
import static com.inolia_zaicek.more_tetra_tools.Effect.Clent.MTTEffectGuiStats.blazingChariotSTrailEffect;
import static com.inolia_zaicek.more_tetra_tools.Effect.Clent.MTTEffectGuiStats.fieryHymnSPledgeEffect;
import static com.inolia_zaicek.more_tetra_tools.Effect.Clent.MTTEffectGuiStats.lonePhoenixSPlumeEffect;
import static com.inolia_zaicek.more_tetra_tools.Effect.Clent.MTTEffectGuiStats.theBladeSupremeRekindledEffect;

public class DomainOfIncandescence {
    @SubscribeEvent
    public static void hurt(LivingHurtEvent event) {
        if (event.getSource().getEntity() instanceof LivingEntity player) {
            var mob = event.getEntity();
            var map = mob.getActiveEffectsMap();
            ItemStack mainHandItem = player.getMainHandItem();
            if (mainHandItem.getItem() instanceof IModularItem item && MTTDamageSourceHelper.isMeleeAttack(event.getSource())) {
                //焢煌之境·劫炎永燎
                float baseLevel = (float) item.getEffectLevel(player.getMainHandItem(), DomainOfIncandescenceEffect);
                //薪炎王剑的凯旋
                float flameLevel = (float) item.getEffectLevel(player.getMainHandItem(), theBladeSupremeRekindledEffect);
                //烈焰圣歌的誓约
                float voidLevel = (float) item.getEffectLevel(player.getMainHandItem(), fieryHymnSPledgeEffect);
                //燎原炎车的狼烟
                float reasonLevel = (float) item.getEffectLevel(player.getMainHandItem(), blazingChariotSTrailEffect);
                //基础数值
                float baseDamage=event.getAmount();
                float hp = player.getHealth();
                float mhp=player.getMaxHealth();
                //焢煌之境·劫炎永燎
                if (baseLevel > 0) {
                    var DamageType = MTTTickZero.hasSource(player.level(), MTTTickZero.TickFireDamage, player);
                    //player.sendSystemMessage(Component.literal("焢煌之境·劫炎永燎").withStyle(ChatFormatting.RED));
                    mob.invulnerableTime = 0;
                    mob.hurt(DamageType, baseDamage * (1 + baseLevel / 100));
                    event.setAmount(0);
                }
                //燎原炎车的狼烟
                if(reasonLevel>0&&player.hasEffect(MTTEffectsRegister.HerrscherOfFlamescion.get())){
                    float finish=hp*0.05f;
                    float fhp=0;
                    //扣血不死
                    if(hp-finish>0){
                        fhp+=finish;
                    }
                    var DamageType = MTTTickZero.source(player.level(), MTTTickZero.TickFireDamage);
                    player.hurt(DamageType,fhp);
                    //player.sendSystemMessage(Component.literal(String.valueOf(fhp)).withStyle(ChatFormatting.GOLD));
                    //回血
                    if(mob.hasEffect(MTTEffectsRegister.EmberBrand.get())) {
                        int buffLevel = mob.getEffect(MTTEffectsRegister.EmberBrand.get()).getAmplifier();
                        //等级*词条回血量*1%生命值
                        player.heal(buffLevel*reasonLevel*mhp/100);
                        //player.sendSystemMessage(Component.literal(String.valueOf(buffLevel*reasonLevel*mhp/100)).withStyle(ChatFormatting.YELLOW));
                    }
                }
                //烈焰圣歌的誓约
                if(voidLevel>0&&player.hasEffect(MTTEffectsRegister.HerrscherOfFlamescion.get())){
                    //如果是玩家，但是计时器不满足
                    if (player instanceof Player player1 && player1.getAttackStrengthScale(0.5f) <= 0.9f) {
                        return;
                    }
                    //印记增伤独立计算在另一个事件
                    if(!mob.hasEffect(MTTEffectsRegister.EmberBrand.get())) {
                        //player.sendSystemMessage(Component.literal(String.valueOf(1)).withStyle(ChatFormatting.BLUE));
                        map.put(MTTEffectsRegister.EmberBrand.get(),
                                new MobEffectInstance(MTTEffectsRegister.EmberBrand.get(), 400, 0));
                    }else{
                        int buffLevel = mob.getEffect(MTTEffectsRegister.EmberBrand.get()).getAmplifier();
                        //player.sendSystemMessage(Component.literal(String.valueOf(buffLevel+1)).withStyle(ChatFormatting.GREEN));
                        map.put(MTTEffectsRegister.EmberBrand.get(),
                                new MobEffectInstance(MTTEffectsRegister.EmberBrand.get(), 400, Math.min(9, buffLevel + 1)));
                    }
                }
            }
        }
        if(event.getEntity() instanceof Player player){
            ItemStack mainHandItem = player.getMainHandItem();
            if (mainHandItem.getItem() instanceof IModularItem item) {
                //遗世火鸟的燃羽
                float sentienceLevel = (float) item.getEffectLevel(player.getMainHandItem(), lonePhoenixSPlumeEffect);
                float baseDamage=event.getAmount();
                float hp = player.getHealth();
                float mhp=player.getMaxHealth();
                if(sentienceLevel>0&&player.hasEffect(MTTEffectsRegister.HerrscherOfFlamescion.get())){
                    float dhp100=(mhp-hp)/mhp;
                    float finish=1-dhp100*sentienceLevel/1000;
                    //差额大于0
                    if(dhp100>0){
                        if(dhp100<100){
                            event.setAmount(baseDamage*finish);
                        }else{
                            event.setAmount(0);
                        }
                    }
                    if(event.getSource().getEntity() instanceof LivingEntity mob){
                        var map = mob.getActiveEffectsMap();
                        if(mob.hasEffect(MTTEffectsRegister.EmberBrand.get())) {
                            int buffLevel = mob.getEffect(MTTEffectsRegister.EmberBrand.get()).getAmplifier();
                            //player.sendSystemMessage(Component.literal(String.valueOf(buffLevel+1)).withStyle(ChatFormatting.DARK_BLUE));
                            map.put(MTTEffectsRegister.EmberBrand.get(),
                                    new MobEffectInstance(MTTEffectsRegister.EmberBrand.get(), 400, Math.min(9, buffLevel + 1)));
                        }else{
                            //player.sendSystemMessage(Component.literal(String.valueOf(1)).withStyle(ChatFormatting.DARK_PURPLE));
                            map.put(MTTEffectsRegister.EmberBrand.get(),
                                    new MobEffectInstance(MTTEffectsRegister.EmberBrand.get(), 400, 0));
                        }
                    }
                    if(event.getSource().getDirectEntity() instanceof LivingEntity mob){
                        var map = mob.getActiveEffectsMap();
                        if(mob.hasEffect(MTTEffectsRegister.EmberBrand.get())) {
                            int buffLevel = mob.getEffect(MTTEffectsRegister.EmberBrand.get()).getAmplifier();
                            //player.sendSystemMessage(Component.literal(String.valueOf(buffLevel+1)).withStyle(ChatFormatting.LIGHT_PURPLE));
                            map.put(MTTEffectsRegister.EmberBrand.get(),
                                    new MobEffectInstance(MTTEffectsRegister.EmberBrand.get(), 400, Math.min(9, buffLevel + 1)));
                        }else{
                            //player.sendSystemMessage(Component.literal(String.valueOf(1)).withStyle(ChatFormatting.DARK_AQUA));
                            map.put(MTTEffectsRegister.EmberBrand.get(),
                                    new MobEffectInstance(MTTEffectsRegister.EmberBrand.get(), 400, 0));
                        }
                    }
                }
            }
        }
    }
}