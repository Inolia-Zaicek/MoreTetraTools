package com.inolia_zaicek.more_tetra_tools.Effect;

import com.inolia_zaicek.more_tetra_tools.Damage.MTTTickZero;
import com.inolia_zaicek.more_tetra_tools.Register.MTTEffectsRegister;
import com.inolia_zaicek.more_tetra_tools.Util.MTTEffectHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import se.mickelus.tetra.items.modular.IModularItem;

import static com.inolia_zaicek.more_tetra_tools.Effect.Clent.MTTEffectGuiStats.creationBloodthornFerryEffect;
import static com.inolia_zaicek.more_tetra_tools.Effect.Clent.MTTEffectGuiStats.fieryHymnSPledgeEffect;
import static net.minecraft.tags.DamageTypeTags.IS_FIRE;

public class FieryHymnSPledgeEvent {
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void hurt(LivingHurtEvent event) {
        if (event.getSource().getEntity() instanceof LivingEntity player) {
            var mob = event.getEntity();
            float effectLevel = (MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectLevel(player, fieryHymnSPledgeEffect));
            if (effectLevel > 0 && mob.hasEffect(MTTEffectsRegister.EmberBrand.get())&&player.hasEffect(MTTEffectsRegister.HerrscherOfFlamescion.get())) {
                int buffLevel = mob.getEffect(MTTEffectsRegister.EmberBrand.get()).getAmplifier();
                if (event.getSource().is(IS_FIRE)||event.getSource().is(MTTTickZero.TickFireDamage)) {
                    float finish = event.getAmount() * (1 + buffLevel * effectLevel / 100);
                    event.setAmount(finish);
                }
            }
        } else if (event.getSource().getDirectEntity() instanceof LivingEntity player) {
            var mob = event.getEntity();
            float effectLevel = (MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectLevel(player, fieryHymnSPledgeEffect));
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
