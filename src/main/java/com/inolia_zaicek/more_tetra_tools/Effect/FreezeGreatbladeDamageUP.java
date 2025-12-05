package com.inolia_zaicek.more_tetra_tools.Effect;

import com.inolia_zaicek.more_tetra_tools.Damage.MTTTickZero;
import com.inolia_zaicek.more_tetra_tools.Util.MTTDamageSourceHelper;
import com.inolia_zaicek.more_tetra_tools.Util.MTTEffectHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import se.mickelus.tetra.items.modular.IModularItem;

import static com.inolia_zaicek.more_tetra_tools.Effect.Clent.MTTEffectGuiStats.*;
import static net.minecraft.tags.DamageTypeTags.IS_FREEZING;
import static net.minecraft.tags.DamageTypeTags.WITCH_RESISTANT_TO;

public class FreezeGreatbladeDamageUP {
    @SubscribeEvent
    public static void hurt(LivingHurtEvent event) {
        if (event.getSource().is(IS_FREEZING) || event.getSource().is(MTTTickZero.TickFreezeDamage)) {
            if (event.getSource().getEntity() instanceof LivingEntity player) {
                float effectLevel = (MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectLevel(player, freezeBladeEffect));
                if (isUltimateBossEntity(event.getEntity().getType()) && effectLevel > 0) {
                    effectLevel = effectLevel * 5;
                }
                if (effectLevel > 0) {
                    float baseDamage = event.getAmount();
                    event.setAmount(baseDamage * (1 + effectLevel / 100));
                }
            } else if (event.getSource().getDirectEntity() instanceof LivingEntity player) {
                float effectLevel = (MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectLevel(player, freezeBladeEffect));
                if (isUltimateBossEntity(event.getEntity().getType()) && effectLevel > 0) {
                    effectLevel = effectLevel * 5;
                }
                if (effectLevel > 0) {
                    float baseDamage = event.getAmount();
                    event.setAmount(baseDamage * (1 + effectLevel / 100));
                }
            }
        }
    }
}