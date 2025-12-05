package com.inolia_zaicek.more_tetra_tools.Effect;

import com.inolia_zaicek.more_tetra_tools.Register.MTTEffectsRegister;
import com.inolia_zaicek.more_tetra_tools.Util.MTTEffectHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import se.mickelus.tetra.items.modular.IModularItem;

import static com.inolia_zaicek.more_tetra_tools.Effect.Clent.MTTEffectGuiStats.rapidSlashingEffect;
import static com.inolia_zaicek.more_tetra_tools.Effect.Clent.MTTEffectGuiStats.reapEffect;

public class LaPlumaScythe2 {
    @SubscribeEvent
    public static void hurt(LivingHurtEvent event) {
        if (event.getSource().getEntity() instanceof LivingEntity player) {
            var mob = event.getEntity();
            float effectLevel = (MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectLevel(player, rapidSlashingEffect));
            float effectLevel2 = (MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectLevel(player, reapEffect));
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