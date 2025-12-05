package com.inolia_zaicek.more_tetra_tools.Effect;

import com.inolia_zaicek.more_tetra_tools.Register.MTTEffectsRegister;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import se.mickelus.tetra.items.modular.IModularItem;

import static com.inolia_zaicek.more_tetra_tools.Effect.Clent.MTTEffectGuiStats.*;

public class MnemonosGrapheus {
    @SubscribeEvent
    public static void hurt(LivingHurtEvent event) {
        if (event.getSource().getEntity() instanceof Player player) {
            var mob = event.getEntity();
            var map = mob.getActiveEffectsMap();
            ItemStack mainHandItem = player.getMainHandItem();
            float effectLevel1 = 0;
            float effectLevel2 = 0;
            //笔
            if (mainHandItem.getItem() instanceof IModularItem item) {
                effectLevel1 += item.getEffectLevel(mainHandItem, weave_Effect);
                if (effectLevel1 > 0 && player.getAttackStrengthScale(0.5f) > 0.9f ) {
                    if (mob.hasEffect(MTTEffectsRegister.MemoryWeaving.get())) {
                        int buffLevel = mob.getEffect(MTTEffectsRegister.MemoryWeaving.get()).getAmplifier();
                        int buffTime = mob.getEffect(MTTEffectsRegister.MemoryWeaving.get()).getDuration();
                        map.put(MTTEffectsRegister.MemoryWeaving.get(),
                                new MobEffectInstance(MTTEffectsRegister.MemoryWeaving.get(), (int) (buffTime+20*effectLevel1), Math.min(9,buffLevel+1) ));
                    }else{
                        map.put(MTTEffectsRegister.MemoryWeaving.get(),
                                new MobEffectInstance(MTTEffectsRegister.MemoryWeaving.get(), (int) (20*effectLevel1), 0));
                    }
                }
                effectLevel2 += item.getEffectLevel(mainHandItem, delete_Effect);
                //冷却
                if (effectLevel2 > 0 && !player.getCooldowns().isOnCooldown(mainHandItem.getItem()) && !player.onGround() ) {
                    mob.addEffect(new MobEffectInstance(MTTEffectsRegister.MindDelete.get(), (int) (20*effectLevel2), 0));
                    map.put(MTTEffectsRegister.MindDelete.get(),
                            new MobEffectInstance(MTTEffectsRegister.MindDelete.get(), (int) (20*effectLevel2), 0));
                    player.getCooldowns().addCooldown(mainHandItem.getItem(), 200);//设置冷却时间
                }
            }
        }
        //副手 挨打
        if (event.getEntity()!=null) {
            LivingEntity player = event.getEntity();
            ItemStack mainHandItem = player.getMainHandItem();
            float effectLevel1 = 0;
            //笔
            if (mainHandItem.getItem() instanceof IModularItem item) {
                effectLevel1 += item.getEffectLevel(mainHandItem, forget_Effect);
                if (effectLevel1 > 0) {
                    if (
                            //直接或者间接满足其一
                            ( event.getSource().getEntity() != null && isBossEntity(event.getSource().getEntity().getType()) )
                            ||
                            ( event.getSource().getDirectEntity() != null && isBossEntity(event.getSource().getDirectEntity().getType()) )
                    ) {
                        event.setAmount(event.getAmount()*(1-effectLevel1/50));
                    }else{
                        event.setAmount(event.getAmount()*(1-effectLevel1/100));
                    }
                }
            }
        }
    }
}