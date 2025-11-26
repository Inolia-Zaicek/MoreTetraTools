package com.inolia_zaicek.more_tetra_tools.Effect;

import com.inolia_zaicek.more_tetra_tools.Register.MTTEffectsRegister;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import se.mickelus.tetra.items.modular.IModularItem;

import java.util.Random;

import static com.inolia_zaicek.more_tetra_tools.Effect.Clent.MTTEffectGuiStats.*;

public class PinusSylvestris {
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void hurt(LivingHurtEvent event) {
        //攻击
        if (event.getSource().getEntity() instanceof Player player) {
            var mob = event.getEntity();
            ItemStack mainHandItem = player.getMainHandItem();
            ItemStack offhandItem = player.getOffhandItem();
            float effectLevel = 0;
            if (mainHandItem.getItem() instanceof IModularItem item) {
                float mainEffectLevel = item.getEffectLevel(mainHandItem, vanguardSwordsmanshipEffect);
                if (mainEffectLevel > 0) {
                    effectLevel += mainEffectLevel;
                }
            }
            if (offhandItem.getItem() instanceof IModularItem item) {
                float offEffectLevel = item.getEffectLevel(offhandItem, vanguardSwordsmanshipEffect);
                if (offEffectLevel > 0) {
                    effectLevel += offEffectLevel;
                }
            }
                if (effectLevel > 0&&player.hasEffect(MTTEffectsRegister.VanguardSwordsmanship.get())) {
                    float number = 1+effectLevel / 100;
                    event.setAmount(event.getAmount()*number);
                    player.removeEffect(MTTEffectsRegister.VanguardSwordsmanship.get());
            }
        }else if (event.getSource().getDirectEntity() instanceof Player player) {
            ItemStack mainHandItem = player.getMainHandItem();
            ItemStack offhandItem = player.getOffhandItem();
            float effectLevel = 0;
            if (mainHandItem.getItem() instanceof IModularItem item) {
                float mainEffectLevel = item.getEffectLevel(mainHandItem, vanguardSwordsmanshipEffect);
                if (mainEffectLevel > 0) {
                    effectLevel += mainEffectLevel;
                }
            }
            if (offhandItem.getItem() instanceof IModularItem item) {
                float offEffectLevel = item.getEffectLevel(offhandItem, vanguardSwordsmanshipEffect);
                if (offEffectLevel > 0) {
                    effectLevel += offEffectLevel;
                }
            }
            if (effectLevel > 0&&player.hasEffect(MTTEffectsRegister.VanguardSwordsmanship.get())) {
                float number = 1+effectLevel / 100;
                event.setAmount(event.getAmount()*number);
                player.removeEffect(MTTEffectsRegister.VanguardSwordsmanship.get());
            }
        }

    }
}