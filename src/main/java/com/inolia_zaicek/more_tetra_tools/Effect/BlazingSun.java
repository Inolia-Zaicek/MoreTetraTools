package com.inolia_zaicek.more_tetra_tools.Effect;

import com.inolia_zaicek.more_tetra_tools.Damage.MTTTickZero;
import com.inolia_zaicek.more_tetra_tools.Register.MTTEffectsRegister;
import com.inolia_zaicek.more_tetra_tools.Util.MTTDamageSourceHelper;
import com.inolia_zaicek.more_tetra_tools.Util.MTTEffectHelper;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import se.mickelus.tetra.items.modular.IModularItem;

import static com.inolia_zaicek.more_tetra_tools.Effect.Clent.MTTEffectGuiStats.*;

public class BlazingSun {
    @SubscribeEvent
    public static void hurt(LivingHurtEvent event) {
        if (event.getSource().getEntity() instanceof LivingEntity player) {
            var mob = event.getEntity();
            var map = mob.getActiveEffectsMap();
            float effectLevel = (MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectLevel(player, blazingSunSObeisanceEffect));
            if (MTTDamageSourceHelper.isMeleeAttack(event.getSource())) {
                if (effectLevel > 0&&player.hasEffect(MTTEffectsRegister.BlazingSunSObeisance.get())) {
                    float number = effectLevel / 100;
                    float atk = (float) player.getAttributeValue(Attributes.ATTACK_DAMAGE);
                    var DamageType = MTTTickZero.hasSource(player.level(), MTTTickZero.TRUEDAMAGE, player);
                    if(player instanceof Player player1) {
                        mob.setLastHurtByPlayer(player1);
                    }
                    mob.invulnerableTime = 0;
                    mob.hurt(DamageType, atk * number);
                    mob.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 40, 2));
                    map.put(MobEffects.MOVEMENT_SLOWDOWN,
                            new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 40, 2));
                    mob.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 40, 2));
                    map.put(MobEffects.WEAKNESS,
                            new MobEffectInstance(MobEffects.WEAKNESS, 40, 2));
                }
            }
        }
    }
}