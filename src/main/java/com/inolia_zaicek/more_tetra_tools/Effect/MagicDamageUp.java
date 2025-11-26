package com.inolia_zaicek.more_tetra_tools.Effect;

import com.inolia_zaicek.more_tetra_tools.Register.MTTEffectsRegister;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import se.mickelus.tetra.items.modular.IModularItem;

import static com.inolia_zaicek.more_tetra_tools.Effect.Clent.MTTEffectGuiStats.*;
import static net.minecraft.tags.DamageTypeTags.WITCH_RESISTANT_TO;

public class MagicDamageUp {
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void hurt(LivingHurtEvent event) {
        if (event.getSource().getEntity() instanceof Player player) {
            var mob = event.getEntity();
            var map = mob.getActiveEffectsMap();
            ItemStack mainHandItem = player.getMainHandItem();
            ItemStack offhandItem = player.getOffhandItem();
            float effectLevel = 0;
            float secondLevel = 0;
            if (mainHandItem.getItem() instanceof IModularItem item) {
                effectLevel += item.getEffectLevel(mainHandItem, magicDamageUpEffect);
                effectLevel += item.getEffectLevel(mainHandItem, dark_greatsword_Effect);
                if(player.hasEffect(MTTEffectsRegister.DarkErosion.get())) {
                    int buffLevel = player.getEffect(MTTEffectsRegister.DarkErosion.get()).getAmplifier()+1;
                    effectLevel += buffLevel * item.getEffectLevel(mainHandItem, dark_whispers_domination_Effect);
                }

                secondLevel += item.getEffectLevel(mainHandItem, surging_current_Effect);
            }
            if (offhandItem.getItem() instanceof IModularItem item) {
                effectLevel += item.getEffectLevel(offhandItem, magicDamageUpEffect);
                effectLevel += item.getEffectLevel(offhandItem, dark_greatsword_Effect);
                if(player.hasEffect(MTTEffectsRegister.DarkErosion.get())) {
                    int buffLevel = player.getEffect(MTTEffectsRegister.DarkErosion.get()).getAmplifier()+1;
                    effectLevel += buffLevel * item.getEffectLevel(offhandItem, dark_whispers_domination_Effect);
                }

                secondLevel += item.getEffectLevel(offhandItem, surging_current_Effect);
            }
            if (event.getSource().is(WITCH_RESISTANT_TO)) {
                if (effectLevel > 0) {
                    float finish = event.getAmount() * (1 + effectLevel / 100);
                    event.setAmount(finish);
                }
                //澄闪状态赋予
                if(secondLevel>0){
                    if(!mob.hasEffect(MTTEffectsRegister.SurgingCurrent.get())) {
                        map.put(MTTEffectsRegister.SurgingCurrent.get(),
                                new MobEffectInstance(MTTEffectsRegister.SurgingCurrent.get(), (int) (secondLevel*20), 0));
                    }else{
                        int buffTime = mob.getEffect(MTTEffectsRegister.SurgingCurrent.get()).getDuration();
                        map.put(MTTEffectsRegister.SurgingCurrent.get(),
                                new MobEffectInstance(MTTEffectsRegister.SurgingCurrent.get(), (int) (buffTime+secondLevel*20), 0));
                    }
                }
            }
        } else if (event.getSource().getDirectEntity() instanceof Player player) {
            var mob = event.getEntity();
            var map = mob.getActiveEffectsMap();
            ItemStack mainHandItem = player.getMainHandItem();
            ItemStack offhandItem = player.getOffhandItem();
            float effectLevel = 0;
            float secondLevel = 0;
            if (mainHandItem.getItem() instanceof IModularItem item) {
                effectLevel += item.getEffectLevel(mainHandItem, magicDamageUpEffect);
                effectLevel += item.getEffectLevel(mainHandItem, dark_greatsword_Effect);
                if(player.hasEffect(MTTEffectsRegister.DarkErosion.get())) {
                    int buffLevel = player.getEffect(MTTEffectsRegister.DarkErosion.get()).getAmplifier()+1;
                    effectLevel += buffLevel * item.getEffectLevel(mainHandItem, dark_whispers_domination_Effect);
                }

                secondLevel += item.getEffectLevel(mainHandItem, surging_current_Effect);
            }
            if (offhandItem.getItem() instanceof IModularItem item) {
                effectLevel += item.getEffectLevel(offhandItem, magicDamageUpEffect);
                effectLevel += item.getEffectLevel(offhandItem, dark_greatsword_Effect);
                if(player.hasEffect(MTTEffectsRegister.DarkErosion.get())) {
                    int buffLevel = player.getEffect(MTTEffectsRegister.DarkErosion.get()).getAmplifier()+1;
                    effectLevel += buffLevel * item.getEffectLevel(offhandItem, dark_whispers_domination_Effect);
                }


                secondLevel += item.getEffectLevel(offhandItem, surging_current_Effect);
            }
            if (event.getSource().is(WITCH_RESISTANT_TO)) {
                if (effectLevel > 0) {
                    float finish = event.getAmount() * (1 + effectLevel / 100);
                    event.setAmount(finish);
                }
                //澄闪状态赋予
                if(secondLevel>0){
                    if(!mob.hasEffect(MTTEffectsRegister.SurgingCurrent.get())) {
                        map.put(MTTEffectsRegister.SurgingCurrent.get(),
                                new MobEffectInstance(MTTEffectsRegister.SurgingCurrent.get(), (int) (secondLevel*20), 0));
                    }else{
                        int buffTime = mob.getEffect(MTTEffectsRegister.SurgingCurrent.get()).getDuration();
                        map.put(MTTEffectsRegister.SurgingCurrent.get(),
                                new MobEffectInstance(MTTEffectsRegister.SurgingCurrent.get(), (int) (buffTime+secondLevel*20), 0));
                    }
                }
            }
        }
    }
}
