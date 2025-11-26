package com.inolia_zaicek.more_tetra_tools.Effect;

import com.inolia_zaicek.more_tetra_tools.Damage.MTTTickZero;
import com.inolia_zaicek.more_tetra_tools.Register.MTTEffectsRegister;
import com.inolia_zaicek.more_tetra_tools.Util.MTTDamageSourceHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import se.mickelus.tetra.items.modular.IModularItem;

import static com.inolia_zaicek.more_tetra_tools.Effect.Clent.MTTEffectGuiStats.*;

public class YingXiao {
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
                float mainEffectLevel = item.getEffectLevel(mainHandItem, yingXiaoFleetingNightEffect);
                if (mainEffectLevel > 0) {
                    effectLevel += mainEffectLevel;
                }
                float mainEffectLevel2 = item.getEffectLevel(mainHandItem, yingXiaoShadowlessEffect);
                if (mainEffectLevel2 > 0) {
                    effectLevel2 += mainEffectLevel2;
                }
            }
            if (offhandItem.getItem() instanceof IModularItem item) {
                float offEffectLevel = item.getEffectLevel(offhandItem, yingXiaoFleetingNightEffect);
                if (offEffectLevel > 0) {
                    effectLevel += offEffectLevel;
                }

                float offEffectLevel2 = item.getEffectLevel(offhandItem, yingXiaoShadowlessEffect);
                if (offEffectLevel2 > 0) {
                    effectLevel2 += offEffectLevel2;
                }
            }
            if (MTTDamageSourceHelper.isMeleeAttack(event.getSource())) {
                //奔夜
                if (effectLevel > 0 && !player.hasEffect(MTTEffectsRegister.YingXiaoShadowless.get())) {
                    var DamageType = MTTTickZero.hasSource(player.level(), MTTTickZero.TICKMAMAGE, player);
                    //var DamageType = TickZero.source(player.level(), TickZero.TICKMAMAGE);
                    mob.setLastHurtByPlayer(player);
                    mob.invulnerableTime = 0;
                    mob.hurt(DamageType, event.getAmount());
                    mob.invulnerableTime = 0;
                    mob.hurt(DamageType, event.getAmount() * effectLevel / 100);
                    mob.setLastHurtByPlayer(player);
                    event.setAmount(0);
                }
                //绝影
                else if (effectLevel2 > 0 && player.hasEffect(MTTEffectsRegister.YingXiaoShadowless.get())) {
                    var DamageType = MTTTickZero.hasSource(player.level(), MTTTickZero.TRUEDAMAGE, player);
                    mob.setLastHurtByPlayer(player);
                    mob.invulnerableTime = 0;
                    mob.hurt(DamageType, event.getAmount() * effectLevel2 / 100);
                    mob.invulnerableTime = 0;
                    mob.setLastHurtByPlayer(player);
                    event.setAmount(0);
                }
            }
        }
    }
}