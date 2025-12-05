package com.inolia_zaicek.more_tetra_tools.Effect.Khaslana;

import com.inolia_zaicek.more_tetra_tools.Register.MTTEffectsRegister;
import com.inolia_zaicek.more_tetra_tools.Util.MTTDamageSourceHelper;
import com.inolia_zaicek.more_tetra_tools.Util.MTTEffectHelper;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import se.mickelus.tetra.items.modular.IModularItem;

import static com.inolia_zaicek.more_tetra_tools.Effect.Clent.MTTEffectGuiStats.creationBloodthornFerryEffect;
import static com.inolia_zaicek.more_tetra_tools.Effect.Clent.MTTEffectGuiStats.kami_ori_Effect;

public class CreationBloodthornFerry {
    @SubscribeEvent
    public static void hurt(LivingHurtEvent event) {
        if (event.getSource().getEntity() instanceof LivingEntity player) {
            float effectLevel = (MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectLevel(player, creationBloodthornFerryEffect));
            if(player instanceof Player player1&&player1.getAttackStrengthScale(0.5f) <= 0.9f)return;
            if (MTTDamageSourceHelper.isMeleeAttack(event.getSource())) {
                if (effectLevel > 0&&player.hasEffect(MTTEffectsRegister.HeWhoBearsTheWorldMustBurn.get())) {
                    float number = effectLevel / 100;
                    player.heal(player.getMaxHealth()*number);
                    //6 7
                    if(player.hasEffect(MTTEffectsRegister.Scourge.get())) {
                        int buffLevel = player.getEffect(MTTEffectsRegister.Scourge.get()).getAmplifier();
                        player.addEffect(new MobEffectInstance(MTTEffectsRegister.Scourge.get(), 400, Math.min(6,buffLevel+1) ));
                    }
                    //5
                    else if(player.hasEffect(MTTEffectsRegister.Scourge4.get())){
                        player.addEffect(new MobEffectInstance(MTTEffectsRegister.Scourge.get(), 400, 4));
                        player.removeEffect(MTTEffectsRegister.Scourge4.get());
                    }
                    //4
                    else if(player.hasEffect(MTTEffectsRegister.Scourge3.get())){
                        player.removeEffect(MTTEffectsRegister.Scourge3.get());
                        player.addEffect(new MobEffectInstance(MTTEffectsRegister.Scourge4.get(), 400, 3));
                    }
                    //3
                    else if(player.hasEffect(MTTEffectsRegister.Scourge2.get())){
                        player.removeEffect(MTTEffectsRegister.Scourge2.get());
                        player.addEffect(new MobEffectInstance(MTTEffectsRegister.Scourge3.get(), 400, 2));
                    }
                    //2
                    else if(player.hasEffect(MTTEffectsRegister.Scourge1.get())){
                        player.removeEffect(MTTEffectsRegister.Scourge1.get());
                        player.addEffect(new MobEffectInstance(MTTEffectsRegister.Scourge2.get(), 400, 1));
                    }
                    //1
                    else if(!player.hasEffect(MTTEffectsRegister.Scourge1.get())) {
                        player.addEffect(new MobEffectInstance(MTTEffectsRegister.Scourge1.get(), 400, 0));
                    }
                }
            }
        }
    }
}
