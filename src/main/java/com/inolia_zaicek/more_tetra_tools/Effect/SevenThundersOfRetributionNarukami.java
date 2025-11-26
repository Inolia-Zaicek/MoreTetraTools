package com.inolia_zaicek.more_tetra_tools.Effect;

import com.inolia_zaicek.more_tetra_tools.Damage.MTTTickZero;
import com.inolia_zaicek.more_tetra_tools.Register.MTTEffectsRegister;
import com.inolia_zaicek.more_tetra_tools.Util.MTTDamageSourceHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import se.mickelus.tetra.items.modular.IModularItem;

import static com.inolia_zaicek.more_tetra_tools.Effect.Clent.MTTEffectGuiStats.forfeiture_Effect;
import static net.minecraft.tags.DamageTypeTags.IS_FIRE;
import static net.minecraft.tags.DamageTypeTags.IS_LIGHTNING;

public class SevenThundersOfRetributionNarukami {
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void hurt(LivingHurtEvent event) {
        if (event.getSource().getEntity() instanceof Player player) {
            var mob = event.getEntity();
            var map = mob.getActiveEffectsMap();
            ItemStack mainHandItem = player.getMainHandItem();
            ItemStack offhandItem = player.getOffhandItem();
            int effectLevel = 0;
            if (mainHandItem.getItem() instanceof IModularItem item) {
                float mainEffectLevel = item.getEffectLevel(mainHandItem, forfeiture_Effect);
                if (mainEffectLevel > 0) {
                    effectLevel += (int) mainEffectLevel;
                }
            }
            if (offhandItem.getItem() instanceof IModularItem item) {
                float offEffectLevel = item.getEffectLevel(offhandItem, forfeiture_Effect);
                if (offEffectLevel > 0) {
                    effectLevel += (int) offEffectLevel;
                }
            }
            //附加
            if (effectLevel > 0&& MTTDamageSourceHelper.isMeleeAttack(event.getSource())) {
                //麻痹
                if(mob.hasEffect(MTTEffectsRegister.Numbness.get())) {
                    int buffLevel = mob.getEffect(MTTEffectsRegister.Numbness.get()).getAmplifier();
                    map.put(MTTEffectsRegister.Numbness.get(),
                            new MobEffectInstance(MTTEffectsRegister.Numbness.get(), 400, Math.min(39, buffLevel + effectLevel)));
                }else{
                    map.put(MTTEffectsRegister.Numbness.get(),
                            new MobEffectInstance(MTTEffectsRegister.Numbness.get(), 400, effectLevel-1));
                }
                //鸣神
                if(mob.hasEffect(MTTEffectsRegister.Narukami.get())) {
                    int buffLevel = mob.getEffect(MTTEffectsRegister.Narukami.get()).getAmplifier();
                    //player.sendSystemMessage(Component.literal(String.valueOf(buffLevel+1)).withStyle(ChatFormatting.GREEN));
                    map.put(MTTEffectsRegister.Narukami.get(),
                            new MobEffectInstance(MTTEffectsRegister.Narukami.get(), 400, Math.min(29, buffLevel + effectLevel)));
                }else{
                    //player.sendSystemMessage(Component.literal(String.valueOf(1)).withStyle(ChatFormatting.BLUE));
                    map.put(MTTEffectsRegister.Narukami.get(),
                            new MobEffectInstance(MTTEffectsRegister.Narukami.get(), 400, effectLevel));
                }
            }
            //增伤
            if(mob.hasEffect(MTTEffectsRegister.Narukami.get())){
                int buffLevel = mob.getEffect(MTTEffectsRegister.Narukami.get()).getAmplifier();
                if (event.getSource().is(IS_LIGHTNING)||event.getSource().is(MTTTickZero.TickLightningDamage)) {
                    float finish = event.getAmount() * (1 + buffLevel * 0.1f);
                    event.setAmount(finish);
                }
            }
        } else if (event.getSource().getDirectEntity() instanceof Player player) {
            var mob = event.getEntity();
            var map = mob.getActiveEffectsMap();
            ItemStack mainHandItem = player.getMainHandItem();
            ItemStack offhandItem = player.getOffhandItem();
            float effectLevel = 0;
            if (mainHandItem.getItem() instanceof IModularItem item) {
                float mainEffectLevel = item.getEffectLevel(mainHandItem, forfeiture_Effect);
                if (mainEffectLevel > 0) {
                    effectLevel += mainEffectLevel;
                }
            }
            if (offhandItem.getItem() instanceof IModularItem item) {
                float offEffectLevel = item.getEffectLevel(offhandItem, forfeiture_Effect);
                if (offEffectLevel > 0) {
                    effectLevel += offEffectLevel;
                }
            }
            //附加
            if (effectLevel > 0) {
                //麻痹
                if(!mob.hasEffect(MTTEffectsRegister.Numbness.get())) {
                    map.put(MTTEffectsRegister.Numbness.get(),
                            new MobEffectInstance(MTTEffectsRegister.Numbness.get(), 400, 0));
                }else{
                    int buffLevel = mob.getEffect(MTTEffectsRegister.Numbness.get()).getAmplifier();
                    map.put(MTTEffectsRegister.Numbness.get(),
                            new MobEffectInstance(MTTEffectsRegister.Numbness.get(), 400, Math.min(39, buffLevel + 1)));
                }
                //鸣神
                if(!mob.hasEffect(MTTEffectsRegister.Narukami.get())) {
                    //player.sendSystemMessage(Component.literal(String.valueOf(1)).withStyle(ChatFormatting.BLUE));
                    map.put(MTTEffectsRegister.Narukami.get(),
                            new MobEffectInstance(MTTEffectsRegister.Narukami.get(), 400, 0));
                }else{
                    int buffLevel = mob.getEffect(MTTEffectsRegister.Narukami.get()).getAmplifier();
                    //player.sendSystemMessage(Component.literal(String.valueOf(buffLevel+1)).withStyle(ChatFormatting.GREEN));
                    map.put(MTTEffectsRegister.Narukami.get(),
                            new MobEffectInstance(MTTEffectsRegister.Narukami.get(), 400, Math.min(29, buffLevel + 1)));
                }
            }
            //增伤
            if(mob.hasEffect(MTTEffectsRegister.Narukami.get())){
                int buffLevel = mob.getEffect(MTTEffectsRegister.Narukami.get()).getAmplifier();
                if (event.getSource().is(IS_LIGHTNING)||event.getSource().is(MTTTickZero.TickLightningDamage)) {
                    float finish = event.getAmount() * (1 + buffLevel * 0.1f);
                    event.setAmount(finish);
                }
            }
        }
    }
}
