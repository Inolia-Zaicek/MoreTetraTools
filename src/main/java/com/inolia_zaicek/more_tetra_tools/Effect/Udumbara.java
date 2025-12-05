package com.inolia_zaicek.more_tetra_tools.Effect;

import com.inolia_zaicek.more_tetra_tools.Damage.MTTTickZero;
import com.inolia_zaicek.more_tetra_tools.Register.MTTEffectsRegister;
import com.inolia_zaicek.more_tetra_tools.Util.MTTDamageSourceHelper;
import com.inolia_zaicek.more_tetra_tools.Util.MTTEffectHelper;
import com.inolia_zaicek.more_tetra_tools.Util.MTTUtil;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import se.mickelus.tetra.items.modular.IModularItem;

import static com.inolia_zaicek.more_tetra_tools.Effect.Clent.MTTEffectGuiStats.*;
import static com.inolia_zaicek.more_tetra_tools.Effect.Clent.MTTEffectGuiStats.chokuhi_kyoshin_joseishu_Effect;
import static com.inolia_zaicek.more_tetra_tools.Effect.Clent.MTTEffectGuiStats.tenka_ichiken_Effect;

public class Udumbara {
    @SubscribeEvent
    public static void hurt(LivingHurtEvent event) {
        if (MTTDamageSourceHelper.isMeleeAttack(event.getSource())) {
            if (event.getSource().getEntity() instanceof LivingEntity player) {
                var mob = event.getEntity();
                float effectLevel = (MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectLevel(player, moon_on_glacial_river_Effect));
                float effectLevel2 = (MTTEffectHelper.getInstance().getMainMaxOffHandUPEffectLevel(player, crescent_transmigration_Effect));
                if (effectLevel > 0) {
                    var mobList = MTTUtil.mobList(4, player);
                    float number = effectLevel / 100;
                    for (Mob mobs : mobList) {
                        //有主人的随从才不会挨打
                        if (!(mobs instanceof OwnableEntity ownableEntity && ownableEntity.getOwnerUUID() != null)) {
                            //获取伤害类型
                            mobs.invulnerableTime = 0;
                            if (player instanceof Player player1) {
                                mobs.setLastHurtByPlayer(player1);
                            }
                            float atk = (float) player.getAttributeValue(Attributes.ATTACK_DAMAGE);
                            var DamageType = MTTTickZero.hasSource(player.level(), MTTTickZero.TickFreezeDamage, player);
                            mobs.hurt(DamageType, atk * number);
                        }
                    }
                }
                if (effectLevel2 > 0) {
                    var DamageType1 = MTTTickZero.source(player.level(), DamageTypes.MAGIC);
                    if (player.getHealth() > 1) {
                        player.hurt(DamageType1, Math.max(1, player.getMaxHealth() * effectLevel2 / 100));
                    } else {
                        player.hurt(DamageType1, 0);
                    }
                    if (player.hasEffect(MTTEffectsRegister.CrescentTransmigration.get())) {
                        int buffLevel = player.getEffect(MTTEffectsRegister.CrescentTransmigration.get()).getAmplifier();
                        player.addEffect(new MobEffectInstance(MTTEffectsRegister.CrescentTransmigration.get(), 200, Math.min(7, buffLevel + 1)));
                    } else {
                        player.addEffect(new MobEffectInstance(MTTEffectsRegister.CrescentTransmigration.get(), 200, 0));
                    }
                }
            }

        }
    }
}