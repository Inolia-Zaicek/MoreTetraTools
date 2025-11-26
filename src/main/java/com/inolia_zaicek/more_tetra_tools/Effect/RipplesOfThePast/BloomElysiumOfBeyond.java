package com.inolia_zaicek.more_tetra_tools.Effect.RipplesOfThePast;

import com.inolia_zaicek.more_tetra_tools.Damage.MTTTickZero;
import com.inolia_zaicek.more_tetra_tools.MoreTetraTools;
import com.inolia_zaicek.more_tetra_tools.Register.MTTEffectsRegister;
import com.inolia_zaicek.more_tetra_tools.Util.MTTDamageSourceHelper;
import com.inolia_zaicek.more_tetra_tools.Util.MTTEffectHelper;
import com.inolia_zaicek.more_tetra_tools.Util.MTTUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import se.mickelus.tetra.items.modular.IModularItem;

import static com.inolia_zaicek.more_tetra_tools.Effect.Clent.MTTEffectGuiStats.*;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE,modid = MoreTetraTools.MODID)
public class BloomElysiumOfBeyond {
    //乐土状态赋予
    @SubscribeEvent
    public static void tick(LivingEvent.LivingTickEvent event) {
        LivingEntity livingEntity = event.getEntity();
        int effectLevel = MTTEffectHelper.getInstance().getMainOffHandSumEffectLevel(livingEntity, bloom_elysium_of_beyond_Effect);
        if (effectLevel > 0) {
            var mobList = MTTUtil.mobList(13, livingEntity);
            var playerList = MTTUtil.PlayerList(13, livingEntity);
            for (Mob mobs : mobList) {
                //有主人的随从
                if ( (mobs instanceof OwnableEntity ownableEntity && ownableEntity.getOwnerUUID() != null) ) {
                    mobs.addEffect(new MobEffectInstance(MTTEffectsRegister.ElysiumOfBeyond.get(), 100, effectLevel-1));
                    var map = mobs.getActiveEffectsMap();
                    map.put(MTTEffectsRegister.ElysiumOfBeyond.get(),
                            new MobEffectInstance(MTTEffectsRegister.ElysiumOfBeyond.get(), 100, effectLevel-1));
                }
            }
            for (Player player : playerList) {
                player.addEffect(new MobEffectInstance(MTTEffectsRegister.ElysiumOfBeyond.get(), 100, effectLevel-1));
            }
        }
    }
    @SubscribeEvent
    public static void hurt(LivingHurtEvent event) {
        if (event.getSource().getEntity() instanceof LivingEntity livingEntity) {
            if (livingEntity.hasEffect(MTTEffectsRegister.ElysiumOfBeyond.get())) {
                //乐土增伤
                int buffLevel = 1 + livingEntity.getEffect(MTTEffectsRegister.ElysiumOfBeyond.get()).getAmplifier();
                event.setAmount(event.getAmount() * (1 + buffLevel * 0.05f));
                //追忆
                if (livingEntity.hasEffect(MTTEffectsRegister.Recollection.get())) {
                    int recollection = livingEntity.getEffect(MTTEffectsRegister.Recollection.get()).getAmplifier();
                    livingEntity.addEffect(new MobEffectInstance(MTTEffectsRegister.Recollection.get(), 100, Math.min(23, recollection + 1)));
                } else {
                    livingEntity.addEffect(new MobEffectInstance(MTTEffectsRegister.Recollection.get(), 100, 0));
                }
            }
        }else if (event.getSource().getDirectEntity() instanceof LivingEntity livingEntity) {
            if (livingEntity.hasEffect(MTTEffectsRegister.ElysiumOfBeyond.get())) {
                //乐土增伤
                int buffLevel = 1 + livingEntity.getEffect(MTTEffectsRegister.ElysiumOfBeyond.get()).getAmplifier();
                event.setAmount(event.getAmount() * (1 + buffLevel * 0.05f));
                var map = livingEntity.getActiveEffectsMap();
                //追忆
                if (livingEntity.hasEffect(MTTEffectsRegister.Recollection.get())) {
                    int recollection = livingEntity.getEffect(MTTEffectsRegister.Recollection.get()).getAmplifier();
                    livingEntity.addEffect(new MobEffectInstance(MTTEffectsRegister.Recollection.get(), 100, Math.min(23, recollection + 1)));
                } else {
                    livingEntity.addEffect(new MobEffectInstance(MTTEffectsRegister.Recollection.get(), 100, 0));
                }
            }
        }
    }
}