package com.inolia_zaicek.more_tetra_tools.Effect;

import com.inolia_zaicek.more_tetra_tools.Damage.MTTTickZero;
import com.inolia_zaicek.more_tetra_tools.Register.MTTEffectsRegister;
import com.inolia_zaicek.more_tetra_tools.Util.MTTDamageSourceHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import se.mickelus.tetra.items.modular.IModularItem;

import static com.inolia_zaicek.more_tetra_tools.Effect.Clent.MTTEffectGuiStats.celestialGlobeEffect;
import static com.inolia_zaicek.more_tetra_tools.Effect.Clent.MTTEffectGuiStats.astralSwordEffect;

public class AstralSword {
    @SubscribeEvent
    public static void hurt(LivingHurtEvent event) {
        if (event.getSource().getEntity() instanceof Player player) {
            var mob = event.getEntity();
            ItemStack mainHandItem = player.getMainHandItem();
            ItemStack offhandItem = player.getOffhandItem();
            float effectLevel = 0;
            if (mainHandItem.getItem() instanceof IModularItem item) {
                float mainEffectLevel = item.getEffectLevel(mainHandItem, astralSwordEffect);
                if (mainEffectLevel > 0) {
                    effectLevel += mainEffectLevel;
                }
            }
            if (offhandItem.getItem() instanceof IModularItem item) {
                float offEffectLevel = item.getEffectLevel(offhandItem, astralSwordEffect);
                if (offEffectLevel > 0) {
                    effectLevel += offEffectLevel;
                }
            }
            if (MTTDamageSourceHelper.isMeleeAttack(event.getSource())) {
                //星剑
                if (effectLevel > 0) {
                    if (player.hasEffect(MTTEffectsRegister.AstralSwordMorning.get()) || player.hasEffect(MTTEffectsRegister.AstralSwordAfternoon.get())
                            || player.hasEffect(MTTEffectsRegister.AstralSwordDawn.get()) || player.hasEffect(MTTEffectsRegister.AstralSwordDusk.get())
                            || player.hasEffect(MTTEffectsRegister.AstralSwordNight.get())
                    ) {
                        var DamageType = MTTTickZero.hasSource(player.level(), MTTTickZero.TICKMAMAGE, player);
                        mob.setLastHurtByPlayer(player);
                        mob.invulnerableTime = 0;
                        mob.hurt(DamageType, event.getAmount());
                        event.setAmount(0);
                    }
                }
            }
        }
        //夜晚减伤
        if (event.getEntity() instanceof Player player) {
            var mob = event.getEntity();
            ItemStack mainHandItem = player.getMainHandItem();
            ItemStack offhandItem = player.getOffhandItem();
            float effectLevel = 0;
            if (mainHandItem.getItem() instanceof IModularItem item) {
                float mainEffectLevel = item.getEffectLevel(mainHandItem, celestialGlobeEffect);
                if (mainEffectLevel > 0) {
                    effectLevel += mainEffectLevel;
                }
            }
            if (offhandItem.getItem() instanceof IModularItem item) {
                float offEffectLevel = item.getEffectLevel(offhandItem, celestialGlobeEffect);
                if (offEffectLevel > 0) {
                    effectLevel += offEffectLevel;
                }
                //星剑
                if (effectLevel > 0&& player.hasEffect(MTTEffectsRegister.AstralSwordNight.get())) {
                        event.setAmount( event.getAmount() * (1-effectLevel/100) );
                }
            }
        }
    }
}