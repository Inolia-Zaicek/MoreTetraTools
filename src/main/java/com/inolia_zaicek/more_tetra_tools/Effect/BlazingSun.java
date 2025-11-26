package com.inolia_zaicek.more_tetra_tools.Effect;

import com.inolia_zaicek.more_tetra_tools.Damage.MTTTickZero;
import com.inolia_zaicek.more_tetra_tools.Register.MTTEffectsRegister;
import com.inolia_zaicek.more_tetra_tools.Util.MTTDamageSourceHelper;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import se.mickelus.tetra.items.modular.IModularItem;

import static com.inolia_zaicek.more_tetra_tools.Effect.Clent.MTTEffectGuiStats.*;

public class BlazingSun {
    @SubscribeEvent
    public static void hurt(LivingHurtEvent event) {
        if (event.getSource().getEntity() instanceof Player player) {
            var mob = event.getEntity();
            var map = mob.getActiveEffectsMap();
            ItemStack mainHandItem = player.getMainHandItem();
            ItemStack offhandItem = player.getOffhandItem();
            float effectLevel = 0;
            if (mainHandItem.getItem() instanceof IModularItem item) {
                float mainEffectLevel = item.getEffectLevel(mainHandItem, blazingSunSObeisanceEffect);
                if (mainEffectLevel > 0) {
                    effectLevel += mainEffectLevel;
                }
            }
            if (offhandItem.getItem() instanceof IModularItem item) {
                float offEffectLevel = item.getEffectLevel(offhandItem, blazingSunSObeisanceEffect);
                if (offEffectLevel > 0) {
                    effectLevel += offEffectLevel;
                }
            }
            if (MTTDamageSourceHelper.isMeleeAttack(event.getSource())) {
                if (effectLevel > 0&&player.hasEffect(MTTEffectsRegister.BlazingSunSObeisance.get())) {
                    float number = effectLevel / 100;
                    float atk = (float) player.getAttributeValue(Attributes.ATTACK_DAMAGE);
                    var DamageType = MTTTickZero.hasSource(player.level(), MTTTickZero.TRUEDAMAGE, player);
                    mob.setLastHurtByPlayer(player);
                    mob.invulnerableTime = 0;
                    mob.hurt(DamageType, atk * number);
                    mob.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20, 1));
                    map.put(MobEffects.MOVEMENT_SLOWDOWN,
                            new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20, 1));
                }
            }
        }
    }
}