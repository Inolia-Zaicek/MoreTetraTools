package com.inolia_zaicek.more_tetra_tools.Effect;

import com.inolia_zaicek.more_tetra_tools.Damage.MTTTickZero;
import com.inolia_zaicek.more_tetra_tools.Util.MTTDamageSourceHelper;
import net.minecraft.world.entity.player.Player;
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
            if (event.getSource().getEntity() instanceof Player player) {
                ItemStack mainHandItem = player.getMainHandItem();
                ItemStack offhandItem = player.getOffhandItem();
                float effectLevel = 0;
                if (mainHandItem.getItem() instanceof IModularItem item) {
                    effectLevel += item.getEffectLevel(mainHandItem, freezeBladeEffect);
                }
                if (offhandItem.getItem() instanceof IModularItem item) {
                    effectLevel += item.getEffectLevel(offhandItem, freezeBladeEffect);
                }
                if (isUltimateBossEntity(event.getEntity().getType()) && effectLevel > 0) {
                    effectLevel = effectLevel * 5;
                }
                if (effectLevel > 0) {
                    float baseDamage = event.getAmount();
                    event.setAmount(baseDamage * (1 + effectLevel / 100));
                }
            } else if (event.getSource().getDirectEntity() instanceof Player player) {
                ItemStack mainHandItem = player.getMainHandItem();
                ItemStack offhandItem = player.getOffhandItem();
                float effectLevel = 0;
                if (mainHandItem.getItem() instanceof IModularItem item) {
                    effectLevel += item.getEffectLevel(mainHandItem, freezeBladeEffect);
                }
                if (offhandItem.getItem() instanceof IModularItem item) {
                    effectLevel += item.getEffectLevel(offhandItem, freezeBladeEffect);
                }
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