package com.inolia_zaicek.more_tetra_tools.Effect;

import com.inolia_zaicek.more_tetra_tools.Damage.MTTTickZero;
import com.inolia_zaicek.more_tetra_tools.Register.MTTEffectsRegister;
import com.inolia_zaicek.more_tetra_tools.Util.MTTDamageSourceHelper;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import se.mickelus.tetra.items.modular.IModularItem;

import static com.inolia_zaicek.more_tetra_tools.Effect.Clent.MTTEffectGuiStats.*;

public class IronRider {
    @SubscribeEvent
    public static void hurt(LivingHurtEvent event) {
        //铜，减伤
        if (event.getEntity() instanceof Player player) {
            ItemStack mainHandItem = player.getMainHandItem();
            ItemStack offhandItem = player.getOffhandItem();
            float effectLevel = 0;
            float effectLevel2 = 0;
            if (mainHandItem.getItem() instanceof IModularItem item) {
                float mainEffectLevel = item.getEffectLevel(mainHandItem, copperSealEffect);
                if (mainEffectLevel > 0) {
                    effectLevel += mainEffectLevel;
                }
                float mainEffectLevel2 = item.getEffectLevel(mainHandItem, ironDefenseEffect);
                if (mainEffectLevel2 > 0) {
                    effectLevel2 += mainEffectLevel2;
                }
            }
            if (offhandItem.getItem() instanceof IModularItem item) {
                float offEffectLevel = item.getEffectLevel(offhandItem, copperSealEffect);
                if (offEffectLevel > 0) {
                    effectLevel += offEffectLevel;
                }
                float mainEffectLevel2 = item.getEffectLevel(offhandItem, ironDefenseEffect);
                if (mainEffectLevel2 > 0) {
                    effectLevel2 += mainEffectLevel2;
                }
            }
            //减伤
            if (effectLevel > 0 && player.hasEffect(MTTEffectsRegister.CopperSeal.get())) {
                float number = effectLevel / 100;
                event.setAmount(event.getAmount() * (1 - number));
            }
            //反伤不减少buff
            if (effectLevel2 > 0) {
                if (player.hasEffect(MTTEffectsRegister.IronDefense.get())) {
                    //反伤，不减少buff
                    var DamageType = MTTTickZero.hasSource(player.level(), MTTTickZero.TICKAMAGE, player);
                    float atk = (float) player.getAttributeValue(Attributes.ATTACK_DAMAGE);
                    float number = effectLevel2 / 100;
                    int buffLevel = player.getEffect(MTTEffectsRegister.IronDefense.get()).getAmplifier();
                    if (event.getSource().getEntity() instanceof LivingEntity mob) {
                        mob.setLastHurtByPlayer(player);
                        mob.invulnerableTime = 0;
                        mob.hurt(DamageType, atk * number);
                        player.addEffect(new MobEffectInstance(MTTEffectsRegister.IronDefense.get(), 300, buffLevel+1));
                    } else if (event.getSource().getDirectEntity() instanceof LivingEntity mob) {
                        mob.setLastHurtByPlayer(player);
                        mob.invulnerableTime = 0;
                        mob.hurt(DamageType, atk * number);
                        player.addEffect(new MobEffectInstance(MTTEffectsRegister.IronDefense.get(), 300, buffLevel+1));
                    }
                }else{
                    if(event.getSource().getEntity()!=null || event.getSource().getDirectEntity()!=null) {
                        player.addEffect(new MobEffectInstance(MTTEffectsRegister.IronDefense.get(), 300, 0));
                    }
                }
            }
        }
        //进攻
        if (event.getSource().getEntity() instanceof Player player) {
            var mob = event.getEntity();
            var map = mob.getActiveEffectsMap();
            ItemStack mainHandItem = player.getMainHandItem();
            ItemStack offhandItem = player.getOffhandItem();
            float effectLevel = 0;
            if (mainHandItem.getItem() instanceof IModularItem item) {
                float mainEffectLevel = item.getEffectLevel(mainHandItem, ironDefenseEffect);
                if (mainEffectLevel > 0) {
                    effectLevel += mainEffectLevel;
                }
            }
            if (offhandItem.getItem() instanceof IModularItem item) {
                float offEffectLevel = item.getEffectLevel(offhandItem, ironDefenseEffect);
                if (offEffectLevel > 0) {
                    effectLevel += offEffectLevel;
                }
            }
            if (MTTDamageSourceHelper.isMeleeAttack(event.getSource())) {
                if (effectLevel > 0&&player.hasEffect(MTTEffectsRegister.IronDefense.get())) {
                    float number = effectLevel / 100;
                    float atk = (float) player.getAttributeValue(Attributes.ATTACK_DAMAGE);
                    var DamageType = MTTTickZero.hasSource(player.level(), MTTTickZero.TICKAMAGE, player);
                    mob.setLastHurtByPlayer(player);
                    mob.invulnerableTime = 0;
                    mob.hurt(DamageType, atk * number);
                    int buffLevel = mob.getEffect(MTTEffectsRegister.IronDefense.get()).getAmplifier();
                    player.removeEffect(MTTEffectsRegister.IronDefense.get());
                    if(buffLevel>0) {
                        player.addEffect(new MobEffectInstance(MTTEffectsRegister.IronDefense.get(), 300, buffLevel - 1));
                    }
                }
            }
        }
    }
}