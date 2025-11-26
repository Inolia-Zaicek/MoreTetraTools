package com.inolia_zaicek.more_tetra_tools.Effect;

import com.inolia_zaicek.more_tetra_tools.Damage.MTTTickZero;
import com.inolia_zaicek.more_tetra_tools.Register.MTTEffectsRegister;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import se.mickelus.tetra.items.modular.IModularItem;

import static com.inolia_zaicek.more_tetra_tools.Effect.Clent.MTTEffectGuiStats.fieryHymnSPledgeEffect;
import static net.minecraft.tags.DamageTypeTags.IS_FIRE;

public class FieryHymnSPledgeEvent {
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void hurt(LivingHurtEvent event) {
        if (event.getSource().getEntity() instanceof Player player) {
            var mob = event.getEntity();
            var map = mob.getActiveEffectsMap();
            ItemStack mainHandItem = player.getMainHandItem();
            ItemStack offhandItem = player.getOffhandItem();
            float effectLevel = 0;
            if (mainHandItem.getItem() instanceof IModularItem item) {
                float mainEffectLevel = item.getEffectLevel(mainHandItem, fieryHymnSPledgeEffect);
                if (mainEffectLevel > 0) {
                    effectLevel += mainEffectLevel;
                }
            }
            if (offhandItem.getItem() instanceof IModularItem item) {
                float offEffectLevel = item.getEffectLevel(offhandItem, fieryHymnSPledgeEffect);
                if (offEffectLevel > 0) {
                    effectLevel += offEffectLevel;
                }
            }
            if (effectLevel > 0 && mob.hasEffect(MTTEffectsRegister.EmberBrand.get())&&player.hasEffect(MTTEffectsRegister.HerrscherOfFlamescion.get())) {
                int buffLevel = mob.getEffect(MTTEffectsRegister.EmberBrand.get()).getAmplifier();
                if (event.getSource().is(IS_FIRE)||event.getSource().is(MTTTickZero.TickFireDamage)) {
                    float finish = event.getAmount() * (1 + buffLevel * effectLevel / 100);
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
                float mainEffectLevel = item.getEffectLevel(mainHandItem, fieryHymnSPledgeEffect);
                if (mainEffectLevel > 0) {
                    effectLevel += mainEffectLevel;
                }
            }
            if (offhandItem.getItem() instanceof IModularItem item) {
                float offEffectLevel = item.getEffectLevel(offhandItem, fieryHymnSPledgeEffect);
                if (offEffectLevel > 0) {
                    effectLevel += offEffectLevel;
                }
            }
            if (effectLevel > 0 && mob.hasEffect(MTTEffectsRegister.EmberBrand.get())&&player.hasEffect(MTTEffectsRegister.HerrscherOfFlamescion.get())) {
                int buffLevel = mob.getEffect(MTTEffectsRegister.EmberBrand.get()).getAmplifier();
                if (event.getSource().is(IS_FIRE)||event.getSource().is(MTTTickZero.TickFireDamage)) {
                    float finish = event.getAmount() * (1 + buffLevel * effectLevel / 100);
                    event.setAmount(finish);
                }
            }
        }
    }
}
