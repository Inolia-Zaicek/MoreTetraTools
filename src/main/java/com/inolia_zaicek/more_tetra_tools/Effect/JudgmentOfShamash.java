package com.inolia_zaicek.more_tetra_tools.Effect;

import com.inolia_zaicek.more_tetra_tools.Damage.MTTTickZero;
import com.inolia_zaicek.more_tetra_tools.MoreTetraTools;
import com.inolia_zaicek.more_tetra_tools.Register.MTTEffectsRegister;
import com.inolia_zaicek.more_tetra_tools.Util.MTTDamageSourceHelper;
import com.inolia_zaicek.more_tetra_tools.Util.MTTEffectHelper;
import com.inolia_zaicek.more_tetra_tools.Util.MTTUtil;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import se.mickelus.tetra.items.modular.IModularItem;

import static com.inolia_zaicek.more_tetra_tools.Effect.Clent.MTTEffectGuiStats.*;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE,modid = MoreTetraTools.MODID)
public class JudgmentOfShamash {
    @SubscribeEvent
    public static void tick(TickEvent.PlayerTickEvent event) {
        Player player = event.player;
        ItemStack mainHandItem = player.getMainHandItem();
        ItemStack offhandItem = player.getOffhandItem();
        float effectLevel = (MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectLevel(player, ember_Effect));
        if (effectLevel > 0 && player.level().getGameTime() % 20 == 0) {
            //主手工具冷却
            if (player.getCooldowns().isOnCooldown(mainHandItem.getItem())&&mainHandItem.getItem() instanceof IModularItem item&&item.getEffectLevel(mainHandItem, ember_Effect)>0) {
                var mobList = MTTUtil.mobList(5, player);
                float number = effectLevel / 100;
                for (Mob mobs : mobList) {
                    //获取伤害类型
                    mobs.invulnerableTime = 0;
                    mobs.setLastHurtByPlayer(player);
                    float atk = (float) player.getAttributeValue(Attributes.ATTACK_DAMAGE);
                    var DamageType = MTTTickZero.hasSource(player.level(), MTTTickZero.TickFireDamage, player);
                    mobs.hurt(DamageType, atk * number);
                    mobs.setLastHurtByPlayer(player);
                    }
                }
            }
            //副手工具冷却
            else if (player.getCooldowns().isOnCooldown(offhandItem.getItem())&&offhandItem.getItem() instanceof IModularItem item&&item.getEffectLevel(offhandItem, ember_Effect)>0) {
            var mobList = MTTUtil.mobList(5, player);
            float number = effectLevel / 100;
            for (Mob mobs : mobList) {
                //获取伤害类型
                mobs.invulnerableTime = 0;
                mobs.setLastHurtByPlayer(player);
                float atk = (float) player.getAttributeValue(Attributes.ATTACK_DAMAGE);
                var DamageType = MTTTickZero.hasSource(player.level(), MTTTickZero.TickFireDamage, player);
                mobs.hurt(DamageType, atk * number);
                mobs.setLastHurtByPlayer(player);
            }
        }
    }
}