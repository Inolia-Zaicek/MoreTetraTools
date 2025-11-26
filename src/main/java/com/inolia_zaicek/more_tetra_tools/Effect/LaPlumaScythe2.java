package com.inolia_zaicek.more_tetra_tools.Effect;

import com.inolia_zaicek.more_tetra_tools.Register.MTTEffectsRegister;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import se.mickelus.tetra.items.modular.IModularItem;

import static com.inolia_zaicek.more_tetra_tools.Effect.Clent.MTTEffectGuiStats.rapidSlashingEffect;
import static com.inolia_zaicek.more_tetra_tools.Effect.Clent.MTTEffectGuiStats.reapEffect;

public class LaPlumaScythe2 {
    @SubscribeEvent
    public static void hurt(LivingHurtEvent event) {
        if (event.getSource().getEntity() instanceof Player player) {
            var mob = event.getEntity();
            var map = mob.getActiveEffectsMap();
            ItemStack mainHandItem = player.getMainHandItem();
            ItemStack offhandItem = player.getOffhandItem();
            float effectLevel = 0;
            float effectLevel2 = 0;
            if (mainHandItem.getItem() instanceof IModularItem item) {
                float mainEffectLevel = item.getEffectLevel(mainHandItem, rapidSlashingEffect);
                if (mainEffectLevel > 0) {
                    effectLevel += mainEffectLevel;
                }
                float mainEffectLevel2 = item.getEffectLevel(mainHandItem, reapEffect);
                if (mainEffectLevel2 > 0) {
                    effectLevel2 += mainEffectLevel2;
                }
            }
            if (offhandItem.getItem() instanceof IModularItem item) {
                float offEffectLevel = item.getEffectLevel(offhandItem, rapidSlashingEffect);
                if (offEffectLevel > 0) {
                    effectLevel += offEffectLevel;
                }

                float offEffectLevel2 = item.getEffectLevel(offhandItem, reapEffect);
                if (offEffectLevel2 > 0) {
                    effectLevel2 += offEffectLevel2;
                }
            }
            if (player.hasEffect(MTTEffectsRegister.IntoTheGroove.get())) {
                int buffLevel = player.getEffect(MTTEffectsRegister.IntoTheGroove.get()).getAmplifier();
                if (buffLevel >= 11 && effectLevel > 0) {
                    event.setAmount(event.getAmount() * 1.08f);
                }
            }
            if (effectLevel2 > 0) {
                float number2 = 1 + effectLevel2 / 100;
                player.heal(player.getMaxHealth() * 0.05f);
                if (mob.getHealth() <= mob.getMaxHealth() * 0.5f) {
                    event.setAmount(event.getAmount() * number2);
                }
            }
        }
    }
}