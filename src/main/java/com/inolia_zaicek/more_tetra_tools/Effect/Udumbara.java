package com.inolia_zaicek.more_tetra_tools.Effect;

import com.inolia_zaicek.more_tetra_tools.Damage.MTTTickZero;
import com.inolia_zaicek.more_tetra_tools.Register.MTTEffectsRegister;
import com.inolia_zaicek.more_tetra_tools.Util.MTTDamageSourceHelper;
import com.inolia_zaicek.more_tetra_tools.Util.MTTUtil;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
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
        if (event.getSource().getEntity() instanceof Player player) {
            var mob = event.getEntity();
            var map = mob.getActiveEffectsMap();
            ItemStack mainHandItem = player.getMainHandItem();
            ItemStack offhandItem = player.getOffhandItem();
            float effectLevel=0;
            float effectLevel2=0;
            if (mainHandItem.getItem() instanceof IModularItem item) {
                effectLevel += item.getEffectLevel(mainHandItem, moon_on_glacial_river_Effect);
                effectLevel2 += item.getEffectLevel(mainHandItem, crescent_transmigration_Effect);
            }
            if (offhandItem.getItem() instanceof IModularItem item) {
                effectLevel += item.getEffectLevel(offhandItem, moon_on_glacial_river_Effect);
                effectLevel2 += item.getEffectLevel(offhandItem, crescent_transmigration_Effect);
            }
            if (MTTDamageSourceHelper.isMeleeAttack(event.getSource())) {
                if (effectLevel > 0){
                    var mobList = MTTUtil.mobList(4, player);
                    float number = effectLevel / 100;
                    for (Mob mobs : mobList) {
                        //有主人的随从才不会挨打
                        if ( !(mobs instanceof OwnableEntity ownableEntity && ownableEntity.getOwnerUUID() != null) ) {
                            //获取伤害类型
                            mobs.invulnerableTime = 0;
                            mobs.setLastHurtByPlayer(player);
                            float atk = (float) player.getAttributeValue(Attributes.ATTACK_DAMAGE);
                            var DamageType = MTTTickZero.hasSource(player.level(), MTTTickZero.TickFreezeDamage,player);
                            mobs.hurt(DamageType, atk * number);
                        }
                    }
                }
                if (effectLevel2 > 0) {
                    var DamageType1 = MTTTickZero.source(player.level(), DamageTypes.MAGIC);
                    if(player.getHealth()>1) {
                        player.hurt(DamageType1, Math.max(1,player.getMaxHealth() * effectLevel2 / 100)  );
                    }else{
                        player.hurt(DamageType1, 0);
                    }
                    if (player.hasEffect(MTTEffectsRegister.CrescentTransmigration.get())) {
                        int buffLevel = player.getEffect(MTTEffectsRegister.CrescentTransmigration.get()).getAmplifier();
                        player.addEffect(new MobEffectInstance(MTTEffectsRegister.CrescentTransmigration.get(), 200, Math.min(3, buffLevel + 1)));
                    }else{
                        player.addEffect(new MobEffectInstance(MTTEffectsRegister.CrescentTransmigration.get(), 200, 0));
                    }
                }
            }
        }
    }
}