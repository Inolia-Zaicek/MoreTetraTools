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

public class YingXiao {
    @SubscribeEvent
    public static void hurt(LivingHurtEvent event) {
        if (event.getSource().getEntity() instanceof LivingEntity player) {
            var mob = event.getEntity();
            var map = mob.getActiveEffectsMap();
            float effectLevel = (MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectLevel(player, yingXiaoFleetingNightEffect));
            float effectLevel2 = (MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectLevel(player, yingXiaoShadowlessEffect));
            if (MTTDamageSourceHelper.isMeleeAttack(event.getSource())) {
                //奔夜
                if (effectLevel > 0 && !player.hasEffect(MTTEffectsRegister.YingXiaoShadowless.get())) {
                    var DamageType = MTTTickZero.hasSource(player.level(), MTTTickZero.TICKMAMAGE, player);
                    //var DamageType = TickZero.source(player.level(), TickZero.TICKMAMAGE);
                    
                    if(player instanceof Player player1) {
                        mob.setLastHurtByPlayer(player1);
                    }
                    mob.invulnerableTime = 0;
                    mob.hurt(DamageType, event.getAmount());
                    mob.invulnerableTime = 0;
                    mob.hurt(DamageType, event.getAmount() * effectLevel / 100);
                    
                    if(player instanceof Player player1) {
                        mob.setLastHurtByPlayer(player1);
                    }
                    event.setAmount(0);
                }
                //绝影
                else if (effectLevel2 > 0 && player.hasEffect(MTTEffectsRegister.YingXiaoShadowless.get())) {
                    var DamageType = MTTTickZero.hasSource(player.level(), MTTTickZero.TRUEDAMAGE, player);
                    
                    if(player instanceof Player player1) {
                        mob.setLastHurtByPlayer(player1);
                    }
                    mob.invulnerableTime = 0;
                    mob.hurt(DamageType, event.getAmount() * effectLevel2 / 100);
                    mob.invulnerableTime = 0;
                    
                    if(player instanceof Player player1) {
                        mob.setLastHurtByPlayer(player1);
                    }
                    event.setAmount(0);
                }
            }
        }
    }
}