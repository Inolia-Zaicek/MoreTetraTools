package com.inolia_zaicek.more_tetra_tools.Effect;

import com.inolia_zaicek.more_tetra_tools.Register.MTTEffectsRegister;
import com.inolia_zaicek.more_tetra_tools.Util.MTTEffectHelper;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
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
        if (event.getSource().getEntity() instanceof LivingEntity player) {
            var mob = event.getEntity();
            var map = mob.getActiveEffectsMap();
            float effectLevel = (MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectLevel(player, magicDamageUpEffect));
            effectLevel += (MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectLevel(player, dark_greatsword_Effect));
            if(player.hasEffect(MTTEffectsRegister.DarkErosion.get())) {
                int buffLevel = player.getEffect(MTTEffectsRegister.DarkErosion.get()).getAmplifier()+1;
                effectLevel += buffLevel * (MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectLevel(player, dark_whispers_domination_Effect));
            }
            float secondLevel = (MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectLevel(player, surging_current_Effect));
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
        } else if (event.getSource().getDirectEntity() instanceof LivingEntity player) {
            var mob = event.getEntity();
            var map = mob.getActiveEffectsMap();
            float effectLevel = (MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectLevel(player, magicDamageUpEffect));
            effectLevel += (MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectLevel(player, dark_greatsword_Effect));
            if(player.hasEffect(MTTEffectsRegister.DarkErosion.get())) {
                int buffLevel = player.getEffect(MTTEffectsRegister.DarkErosion.get()).getAmplifier()+1;
                effectLevel += buffLevel * (MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectLevel(player, dark_whispers_domination_Effect));
            }
            float secondLevel = (MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectLevel(player, surging_current_Effect));
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
