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

public class AstralSword {
    @SubscribeEvent
    public static void hurt(LivingHurtEvent event) {
        if (event.getSource().getEntity() instanceof LivingEntity player) {
            var mob = event.getEntity();
            float effectLevel = (MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectLevel(player, astralSwordEffect));
            if (MTTDamageSourceHelper.isMeleeAttack(event.getSource())) {
                //星剑
                if (effectLevel > 0) {
                    if (player.hasEffect(MTTEffectsRegister.AstralSwordMorning.get()) || player.hasEffect(MTTEffectsRegister.AstralSwordAfternoon.get())
                            || player.hasEffect(MTTEffectsRegister.AstralSwordDawn.get()) || player.hasEffect(MTTEffectsRegister.AstralSwordDusk.get())
                            || player.hasEffect(MTTEffectsRegister.AstralSwordNight.get())
                    ) {
                        var DamageType = MTTTickZero.hasSource(player.level(), MTTTickZero.TICKMAMAGE, player);
                        
                    if(player instanceof Player player1) {
                        mob.setLastHurtByPlayer(player1);
                    }
                        mob.invulnerableTime = 0;
                        mob.hurt(DamageType, event.getAmount());
                        event.setAmount(0);
                    }
                }
            }
        }
        //夜晚减伤
        if (event.getEntity()!=null) {
            LivingEntity player = event.getEntity();
            float effectLevel = (MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectLevel(player, astralSwordEffect));
                //星剑
                if (effectLevel > 0&& player.hasEffect(MTTEffectsRegister.AstralSwordNight.get())) {
                        event.setAmount( event.getAmount() * (1-effectLevel/100) );
            }
        }
    }
}