package com.inolia_zaicek.more_tetra_tools.Event;

import com.inolia_zaicek.more_tetra_tools.MoreTetraTools;
import com.inolia_zaicek.more_tetra_tools.Register.MTTEffectsRegister;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import se.mickelus.tetra.items.modular.IModularItem;

import static com.inolia_zaicek.more_tetra_tools.Effect.Clent.MTTEffectGuiStats.dark_whispers_domination_Effect;
import static com.inolia_zaicek.more_tetra_tools.Effect.Clent.MTTEffectGuiStats.isBossEntity;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE,modid = MoreTetraTools.MODID)
public class BlowoutEvent {
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void hurt(LivingHurtEvent event) {
        LivingEntity livingEntity = event.getEntity();
        if (livingEntity.hasEffect(MTTEffectsRegister.Blowout.get())) {
            int buffLevel = livingEntity.getEffect(MTTEffectsRegister.Blowout.get()).getAmplifier();
            int buffTime = livingEntity.getEffect(MTTEffectsRegister.Blowout.get()).getDuration();
            float mhp = livingEntity.getMaxHealth();
            if(isBossEntity(event.getEntity().getType())) {
                event.setAmount(event.getAmount() * 1.5f +mhp*0.01f);
            }else{
                event.setAmount(event.getAmount() * 1.5f +mhp*0.03f);
            }
            livingEntity.removeEffect(MTTEffectsRegister.Blowout.get());
            if (buffLevel > 0) {
                var map = livingEntity.getActiveEffectsMap();
                map.put(MTTEffectsRegister.Blowout.get(),
                        new MobEffectInstance(MTTEffectsRegister.Blowout.get(),  buffTime, buffLevel - 1));
            }
        }
    }
}
