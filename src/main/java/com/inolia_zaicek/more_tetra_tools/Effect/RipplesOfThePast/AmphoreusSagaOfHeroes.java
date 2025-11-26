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

import static com.inolia_zaicek.more_tetra_tools.Effect.Clent.MTTEffectGuiStats.*;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE,modid = MoreTetraTools.MODID)
public class AmphoreusSagaOfHeroes {
    @SubscribeEvent
    public static void tick(LivingEvent.LivingTickEvent event) {
        LivingEntity livingEntity = event.getEntity();
        //翁法罗斯英雄纪
        if (livingEntity.hasEffect(MTTEffectsRegister.TheOdeOfAmphoreus.get())) {
            //烈阳
            int khaslana = MTTEffectHelper.getInstance().getMainOffHandSumEffectLevel(livingEntity, fateDivineVesselEffect);
            //不因毁伤而落下
            if (khaslana > 0 && livingEntity.hasEffect(MTTEffectsRegister.HeWhoBearsTheWorldMustBurn.get())) {
                livingEntity.addEffect(new MobEffectInstance(MTTEffectsRegister.HeWhoBearsTheWorldMustBurn.get(), 20 * 7, 0));
            }
        }
    }
}