package com.inolia_zaicek.more_tetra_tools.Effect;

import com.inolia_zaicek.more_tetra_tools.Register.MTTEffectsRegister;
import com.inolia_zaicek.more_tetra_tools.Util.MTTEffectHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import se.mickelus.tetra.items.modular.IModularItem;

import java.lang.reflect.Field;

import static com.inolia_zaicek.more_tetra_tools.Effect.Clent.MTTEffectGuiStats.*;

public class DeityHunter {
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void hurt(LivingHurtEvent event) {
        //血条判断或者是boss
        if (isBossEntity(event.getEntity().getType())) {
            //攻击
            if (event.getSource().getEntity() instanceof LivingEntity player) {
                float effectLevel = (MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectLevel(player, deityHunterEffect));
                if(isUltimateBossEntity(event.getEntity().getType())&&effectLevel > 0){
                    effectLevel=effectLevel*5;
                }
                if (effectLevel > 0) {
                    if (player.hasEffect(MTTEffectsRegister.DeityHunter.get())) {
                        event.setAmount(event.getAmount() * (1 + effectLevel / 20));
                    } else {
                        event.setAmount(event.getAmount() * (1 + effectLevel / 100));
                    }
                }
            } else if (event.getSource().getDirectEntity() instanceof LivingEntity player) {
                float effectLevel = (MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectLevel(player, deityHunterEffect));
                if(isUltimateBossEntity(event.getEntity().getType())&&effectLevel > 0){
                    effectLevel=effectLevel*5;
                }
                if (effectLevel > 0) {
                    if (player.hasEffect(MTTEffectsRegister.DeityHunter.get())) {
                        event.setAmount(event.getAmount() * (1 + effectLevel / 20));
                    } else {
                        event.setAmount(event.getAmount() * (1 + effectLevel / 100));
                    }
                }
            }
        }
        //判断攻击者，减伤
        if (event.getSource().getEntity() instanceof LivingEntity livingEntity) {
            if (isBossEntity(livingEntity.getType())) {
                //攻击
                if (event.getEntity()!=null) {
            LivingEntity player = event.getEntity();
                    float effectLevel = (MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectLevel(player, deityHunterEffect));
                    if(isUltimateBossEntity(event.getEntity().getType())&&effectLevel > 0){
                        effectLevel=effectLevel*5;
                    }
                    if (effectLevel > 0 && player.hasEffect(MTTEffectsRegister.DeityHunter.get())) {
                        event.setAmount( Math.max(0,event.getAmount() * (1 - effectLevel / 100)) );
                    }
                }
            }
        } else if (event.getSource().getDirectEntity() instanceof LivingEntity livingEntity) {
            if (isBossEntity(livingEntity.getType())) {
                //攻击
                if (event.getEntity()!=null) {
            LivingEntity player = event.getEntity();
                    float effectLevel = (MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectLevel(player, deityHunterEffect));
                    if(isUltimateBossEntity(event.getEntity().getType())&&effectLevel > 0){
                        effectLevel=effectLevel*5;
                    }
                    if (effectLevel > 0 && player.hasEffect(MTTEffectsRegister.DeityHunter.get())) {
                        event.setAmount( Math.max(0,event.getAmount() * (1 - effectLevel / 100)) );
                    }
                }
            }
        }
    }
}
