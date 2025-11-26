package com.inolia_zaicek.more_tetra_tools.Effect;

import com.inolia_zaicek.more_tetra_tools.Damage.MTTTickZero;
import com.inolia_zaicek.more_tetra_tools.Register.MTTEffectsRegister;
import com.inolia_zaicek.more_tetra_tools.Util.MTTDamageSourceHelper;
import com.inolia_zaicek.more_tetra_tools.Util.MTTUtil;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import se.mickelus.tetra.items.modular.IModularItem;

import static com.inolia_zaicek.more_tetra_tools.Effect.Clent.MTTEffectGuiStats.*;

public class LaPlumaScythe {
    @SubscribeEvent
    public static void hurt(LivingHurtEvent event) {
        if (event.getSource().getEntity() instanceof Player player) {
            var mob = event.getEntity();
            var map = mob.getActiveEffectsMap();
            ItemStack mainHandItem = player.getMainHandItem();
            ItemStack offhandItem = player.getOffhandItem();
            float effectLevel = 0;
            float effectLevel2 = 0;
            if (mainHandItem.getItem() instanceof IModularItem item) {
                float mainEffectLevel = item.getEffectLevel(mainHandItem, rapidSlashingEffect);
                if (mainEffectLevel > 0) {
                    effectLevel += mainEffectLevel;
                }
                float mainEffectLevel2 = item.getEffectLevel(mainHandItem, reapEffect);
                if (mainEffectLevel2 > 0) {
                    effectLevel2 += mainEffectLevel2;
                }
            }
            if (offhandItem.getItem() instanceof IModularItem item) {
                float offEffectLevel = item.getEffectLevel(offhandItem, rapidSlashingEffect);
                if (offEffectLevel > 0) {
                    effectLevel += offEffectLevel;
                }

                float offEffectLevel2 = item.getEffectLevel(offhandItem, reapEffect);
                if (offEffectLevel2 > 0) {
                    effectLevel2 += offEffectLevel2;
                }
            }
            if (player.hasEffect(MTTEffectsRegister.IntoTheGroove.get())) {
                int buffLevel = player.getEffect(MTTEffectsRegister.IntoTheGroove.get()).getAmplifier();
                if (buffLevel >= 11 && effectLevel > 0) {
                    event.setAmount(event.getAmount() * 1.08f);
                }
            }
            //攻击冷却条满了
            if (player.getAttackStrengthScale(0.5f) > 0.9f && MTTDamageSourceHelper.isMeleeAttack(event.getSource())) {
                if (effectLevel > 0) {
                    if (!player.hasEffect(MTTEffectsRegister.IntoTheGroove.get())) {
                        player.addEffect(new MobEffectInstance(MTTEffectsRegister.IntoTheGroove.get(), 200, 0));
                    } else {
                        int buffLevel = player.getEffect(MTTEffectsRegister.IntoTheGroove.get()).getAmplifier();
                        player.addEffect(new MobEffectInstance(MTTEffectsRegister.IntoTheGroove.get(), 200, Math.min(11, buffLevel + 1)));
                    }
                    var mobList = MTTUtil.mobList(2, mob);
                    float number = effectLevel / 100;
                    for (Mob mobs : mobList) {
                        if (mobs != null) {
                            //获取伤害类型
                            mobs.invulnerableTime = 0;
                            mobs.setLastHurtByPlayer(player);
                            float atk = event.getAmount();
                            var DamageType = MTTTickZero.hasSource(player.level(), MTTTickZero.TICKAMAGE,player);
                            mobs.hurt(DamageType, atk * number);
                            mobs.setLastHurtByPlayer(player);
                        }
                    }
                }
            }
        }
    }
}