package com.inolia_zaicek.more_tetra_tools.Effect.ReturnToAshKatana;

import com.inolia_zaicek.more_tetra_tools.Damage.MTTTickZero;
import com.inolia_zaicek.more_tetra_tools.Util.MTTDamageSourceHelper;
import com.inolia_zaicek.more_tetra_tools.Util.MTTEffectHelper;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static com.inolia_zaicek.more_tetra_tools.Effect.Clent.MTTEffectGuiStats.ash_to_ash_Effect;
import static com.inolia_zaicek.more_tetra_tools.Effect.Clent.MTTEffectGuiStats.fateDivineVesselEffect;
import static net.minecraft.tags.DamageTypeTags.IS_FIRE;

public class AshToAsh {
    @SubscribeEvent
    public static void hurt(LivingHurtEvent event) {
        if (MTTDamageSourceHelper.isMeleeAttack(event.getSource())) {
            var mob = event.getEntity();
            if (event.getSource().getEntity() instanceof LivingEntity livingEntity) {
                float effectLevel = MTTEffectHelper.getInstance().getMainOffHandSumEffectLevel(livingEntity, ash_to_ash_Effect);
                if (effectLevel > 0) {
                    var DamageType = MTTTickZero.hasSource(livingEntity.level(), MTTTickZero.TickFireDamage, livingEntity);
                    if (livingEntity instanceof Player player) {mob.setLastHurtByPlayer(player);}
                    mob.invulnerableTime = 0;
                    mob.hurt(DamageType, event.getAmount());
                    mob.invulnerableTime = 0;
                    if (livingEntity instanceof Player player) {mob.setLastHurtByPlayer(player);}
                    event.setAmount(0);
                }
            }
            else if (event.getSource().getDirectEntity() instanceof LivingEntity livingEntity) {
                float effectLevel = MTTEffectHelper.getInstance().getMainOffHandSumEffectLevel(livingEntity, ash_to_ash_Effect);
                if (effectLevel > 0) {
                    var DamageType = MTTTickZero.hasSource(livingEntity.level(), MTTTickZero.TickFireDamage, livingEntity);
                    if (livingEntity instanceof Player player) {mob.setLastHurtByPlayer(player);}
                    mob.invulnerableTime = 0;
                    mob.hurt(DamageType, event.getAmount());
                    mob.invulnerableTime = 0;
                    if (livingEntity instanceof Player player) {mob.setLastHurtByPlayer(player);}
                    event.setAmount(0);
                }
            }
        }
    }
}