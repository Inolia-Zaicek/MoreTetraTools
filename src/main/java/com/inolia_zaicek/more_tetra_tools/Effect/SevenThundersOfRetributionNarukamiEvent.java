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

public class SevenThundersOfRetributionNarukamiEvent {
    @SubscribeEvent
    public static void hurt(LivingHurtEvent event) {
        if (event.getSource().getEntity() instanceof Player player) {
            var mob = event.getEntity();
            ItemStack mainHandItem = player.getMainHandItem();
            ItemStack offhandItem = player.getOffhandItem();
            float effectLevel = 0;
            if (mainHandItem.getItem() instanceof IModularItem item) {
                effectLevel += item.getEffectLevel(mainHandItem, forfeiture_Effect);
            }
            if (offhandItem.getItem() instanceof IModularItem item) {
                effectLevel += item.getEffectLevel(offhandItem, forfeiture_Effect);
            }
            //造成伤害转化为雷伤
            if (MTTDamageSourceHelper.isMeleeAttack(event.getSource())) {
                //奔夜
                if (effectLevel > 0) {
                    var DamageType = MTTTickZero.hasSource(player.level(), MTTTickZero.TickLightningDamage, player);
                    mob.setLastHurtByPlayer(player);
                    mob.invulnerableTime = 0;
                    mob.hurt(DamageType, event.getAmount());
                    mob.setLastHurtByPlayer(player);
                    event.setAmount(0);
                }
            }
        }
    }
}