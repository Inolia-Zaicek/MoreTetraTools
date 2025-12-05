package com.inolia_zaicek.more_tetra_tools.Effect;

import com.inolia_zaicek.more_tetra_tools.Damage.MTTTickZero;
import com.inolia_zaicek.more_tetra_tools.Register.MTTEffectsRegister;
import com.inolia_zaicek.more_tetra_tools.Util.MTTDamageSourceHelper;
import com.inolia_zaicek.more_tetra_tools.Util.MTTEffectHelper;
import com.inolia_zaicek.more_tetra_tools.Util.MTTUtil;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import se.mickelus.tetra.items.modular.IModularItem;

import static com.inolia_zaicek.more_tetra_tools.Effect.Clent.MTTEffectGuiStats.*;

public class LaPlumaScythe {
    @SubscribeEvent
    public static void hurt(LivingHurtEvent event) {
        if (event.getSource().getEntity() instanceof LivingEntity player) {
            var mob = event.getEntity();
            float effectLevel = (MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectLevel(player, rapidSlashingEffect));
            float effectLevel2 = (MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectLevel(player, reapEffect));
            if (player.hasEffect(MTTEffectsRegister.IntoTheGroove.get())) {
                int buffLevel = player.getEffect(MTTEffectsRegister.IntoTheGroove.get()).getAmplifier();
                if (buffLevel >= 11 && effectLevel > 0) {
                    event.setAmount(event.getAmount() * 1.08f);
                }
            }
            //攻击冷却条满了
            if(player instanceof Player player1&&player1.getAttackStrengthScale(0.5f) <= 0.9f)return;
            if (MTTDamageSourceHelper.isMeleeAttack(event.getSource())) {
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
                            
                    if(player instanceof Player player1) {
                        mobs.setLastHurtByPlayer(player1);
                    }
                            float atk = event.getAmount();
                            var DamageType = MTTTickZero.hasSource(player.level(), MTTTickZero.TICKAMAGE,player);
                            mobs.hurt(DamageType, atk * number);
                            
                    if(player instanceof Player player1) {
                        mobs.setLastHurtByPlayer(player1);
                    }
                        }
                    }
                }
            }
        }
    }
}