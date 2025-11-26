package com.inolia_zaicek.more_tetra_tools.Effect;

import com.inolia_zaicek.more_tetra_tools.Damage.MTTTickZero;
import com.inolia_zaicek.more_tetra_tools.Register.MTTEffectsRegister;
import com.inolia_zaicek.more_tetra_tools.Util.MTTDamageSourceHelper;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import se.mickelus.tetra.items.modular.IModularItem;

import static com.inolia_zaicek.more_tetra_tools.Effect.Clent.MTTEffectGuiStats.*;
public class BlemishineSword {
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
                float mainEffectLevel = item.getEffectLevel(mainHandItem, surgingBrillianceEffect);
                if (mainEffectLevel > 0) {
                    effectLevel += mainEffectLevel;
                }
                float mainEffectLevel2 = item.getEffectLevel(mainHandItem, divineAvatarEffect);
                if (mainEffectLevel2 > 0) {
                    effectLevel2 += mainEffectLevel2;
                }
            }
            if (offhandItem.getItem() instanceof IModularItem item) {
                float offEffectLevel = item.getEffectLevel(offhandItem, surgingBrillianceEffect);
                if (offEffectLevel > 0) {
                    effectLevel += offEffectLevel;
                }

                float offEffectLevel2 = item.getEffectLevel(offhandItem, divineAvatarEffect);
                if (offEffectLevel2 > 0) {
                    effectLevel2 += offEffectLevel2;
                }
            }
            if (MTTDamageSourceHelper.isMeleeAttack(event.getSource())) {
                if (effectLevel > 0) {
                    float number = effectLevel/100;
                    float atk =(float) player.getAttributeValue(Attributes.ATTACK_DAMAGE);
                    player.heal(atk*number);
                    if(player.hasEffect(MTTEffectsRegister.DivineAvatar.get())&&effectLevel2>0){
                        float number2 = effectLevel2/100;
                        var DamageType = MTTTickZero.hasSource(player.level(), MTTTickZero.TICKMAMAGE, player);
                        mob.setLastHurtByPlayer(player);
                        mob.invulnerableTime = 0;
                        mob.hurt(DamageType, atk*number2);
                        player.heal(atk*number);
                    }
                }
            }
        }
    }
}