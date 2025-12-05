package com.inolia_zaicek.more_tetra_tools.Effect;

import com.inolia_zaicek.more_tetra_tools.Damage.MTTTickZero;
import com.inolia_zaicek.more_tetra_tools.Register.MTTEffectsRegister;
import com.inolia_zaicek.more_tetra_tools.Util.MTTDamageSourceHelper;
import com.inolia_zaicek.more_tetra_tools.Util.MTTEffectHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import se.mickelus.tetra.items.modular.IModularItem;

import static com.inolia_zaicek.more_tetra_tools.Effect.Clent.MTTEffectGuiStats.*;

public class SevenThundersOfRetributionNarukamiEvent {
    @SubscribeEvent
    public static void hurt(LivingHurtEvent event) {
        if (event.getSource().getEntity() instanceof LivingEntity player) {
            var mob = event.getEntity();
            int effectLevel = (MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectLevel(player, forfeiture_Effect));
            //造成伤害转化为雷伤
            if (MTTDamageSourceHelper.isMeleeAttack(event.getSource())) {
                //奔夜
                if (effectLevel > 0) {
                    var DamageType = MTTTickZero.hasSource(player.level(), MTTTickZero.TickLightningDamage, player);
                    
                    if(player instanceof Player player1) {
                        mob.setLastHurtByPlayer(player1);
                    }
                    mob.invulnerableTime = 0;
                    mob.hurt(DamageType, event.getAmount());
                    
                    if(player instanceof Player player1) {
                        mob.setLastHurtByPlayer(player1);
                    }
                    event.setAmount(0);
                }
            }
        }
    }
}