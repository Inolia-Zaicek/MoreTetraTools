package com.inolia_zaicek.more_tetra_tools.Effect.RipplesOfThePast;

import com.inolia_zaicek.more_tetra_tools.MoreTetraTools;
import com.inolia_zaicek.more_tetra_tools.Register.MTTEffectsRegister;
import com.inolia_zaicek.more_tetra_tools.Util.MTTEffectHelper;
import com.inolia_zaicek.more_tetra_tools.Util.MTTUtil;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.inolia_zaicek.more_tetra_tools.Effect.Clent.MTTEffectGuiStats.bloom_elysium_of_beyond_Effect;
import static com.inolia_zaicek.more_tetra_tools.Effect.Clent.MTTEffectGuiStats.this_ode_to_all_lives_Effect;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE,modid = MoreTetraTools.MODID)
public class ThisOdeToAllLives {
    @SubscribeEvent
    public static void tick(LivingEvent.LivingTickEvent event) {
        LivingEntity livingEntity = event.getEntity();
        int effectLevel = MTTEffectHelper.getInstance().getMainOffHandSumEffectLevel(livingEntity, this_ode_to_all_lives_Effect);
        //涟漪状态
        if (effectLevel > 0 && livingEntity.hasEffect(MTTEffectsRegister.TheOdeOfAmphoreus.get())){
            var mobList = MTTUtil.mobList(13, livingEntity);
            var playerList = MTTUtil.PlayerList(13, livingEntity);
            for (Mob mobs : mobList) {
                //有主人的随从
                if ( (mobs instanceof OwnableEntity ownableEntity && ownableEntity.getOwnerUUID() != null) ) {
                    mobs.addEffect(new MobEffectInstance(MTTEffectsRegister.TheOdeOfAmphoreus.get(), 100, effectLevel-1));
                    var map = mobs.getActiveEffectsMap();
                    map.put(MTTEffectsRegister.TheOdeOfAmphoreus.get(),
                            new MobEffectInstance(MTTEffectsRegister.TheOdeOfAmphoreus.get(), 100, effectLevel-1));
                }
            }
            for (Player player : playerList) {
                player.addEffect(new MobEffectInstance(MTTEffectsRegister.TheOdeOfAmphoreus.get(), 100, effectLevel-1));
            }
        }
    }
    @SubscribeEvent
    public static void hurt(LivingHurtEvent event) {
        if (event.getSource().getEntity() instanceof LivingEntity livingEntity) {
            if (livingEntity.hasEffect(MTTEffectsRegister.TheOdeOfAmphoreus.get())) {
                //乐土增伤
                int buffLevel = 1 + livingEntity.getEffect(MTTEffectsRegister.TheOdeOfAmphoreus.get()).getAmplifier();
                event.setAmount(event.getAmount() * (1 + buffLevel * 0.13f));
            }
        }
    }
}