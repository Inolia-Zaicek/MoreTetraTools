package com.inolia_zaicek.more_tetra_tools.Effect;

import com.inolia_zaicek.more_tetra_tools.Damage.MTTTickZero;
import com.inolia_zaicek.more_tetra_tools.Register.MTTEffectsRegister;
import com.inolia_zaicek.more_tetra_tools.Util.MTTDamageSourceHelper;
import com.inolia_zaicek.more_tetra_tools.Util.MTTEffectHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.ForgeRegistries;
import se.mickelus.tetra.items.modular.IModularItem;

import java.util.Objects;

import static com.inolia_zaicek.more_tetra_tools.Effect.Clent.MTTEffectGuiStats.*;

public class MortalBlade {
    @SubscribeEvent
    public static void hurt(LivingHurtEvent event) {
        if (event.getSource().getEntity() instanceof LivingEntity player) {
            var mob = event.getEntity();
            var map = mob.getActiveEffectsMap();
            float effectLevel = (MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectLevel(player, reviverKillerEffect));
            float effectLevel2 = (MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectLevel(player, divineDragonPowerEffect));
            //有1词条或者樱龙
                if (effectLevel > 0||player.hasEffect(MTTEffectsRegister.DivineDragonPower.get())) {
                    //不死斩诅咒
                    if(effectLevel > 0){
                        mob.addEffect(new MobEffectInstance(MTTEffectsRegister.Unrevive.get(), 200, 0));
                        map.put(MTTEffectsRegister.Unrevive.get(),
                                new MobEffectInstance(MTTEffectsRegister.Unrevive.get(), 200, 0));
                        //莱特兰//通过id直接给buff
                        if (ModList.get().isLoaded("l2complements")) {
                        map.put(Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("l2complements", "curse")))
                                ,new MobEffectInstance(Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("l2complements", "curse"))), 200, 4));
                        mob.addEffect(new MobEffectInstance(Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("l2complements", "curse"))), 200, 4));
                    }
                        }
                    //倍率
                    float reviverKiller = effectLevel;
                    if(player.hasEffect(MTTEffectsRegister.DivineDragonPower.get())){
                        reviverKiller+=effectLevel2;
                    }
                    float number = 1 + reviverKiller / 100;
                    //亡灵or节肢
                    if(event.getEntity().getMobType() == MobType.UNDEAD || event.getEntity().getMobType() == MobType.ARTHROPOD){
                        event.setAmount(event.getAmount()*number);
                    }
            }
        }
        else if (event.getSource().getDirectEntity() instanceof LivingEntity player) {
            var mob = event.getEntity();
            var map = mob.getActiveEffectsMap();
            float effectLevel = (MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectLevel(player, reviverKillerEffect));
            float effectLevel2 = (MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectLevel(player, divineDragonPowerEffect));
            //有1词条或者樱龙
            if (effectLevel > 0||player.hasEffect(MTTEffectsRegister.DivineDragonPower.get())) {
                //不死斩诅咒
                if(effectLevel > 0){
                    mob.addEffect(new MobEffectInstance(MTTEffectsRegister.Unrevive.get(), 200, 0));
                    map.put(MTTEffectsRegister.Unrevive.get(),
                            new MobEffectInstance(MTTEffectsRegister.Unrevive.get(), 200, 0));
                    //莱特兰//通过id直接给buff
                    if (ModList.get().isLoaded("l2complements")) {
                        map.put(Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("l2complements", "curse")))
                                ,new MobEffectInstance(Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("l2complements", "curse"))), 200, 4));
                        mob.addEffect(new MobEffectInstance(Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("l2complements", "curse"))), 200, 4));
                    }
                }
                //倍率
                float reviverKiller = effectLevel;
                if(player.hasEffect(MTTEffectsRegister.DivineDragonPower.get())){
                    reviverKiller+=effectLevel2;
                }
                float number = 1 + reviverKiller / 100;
                //亡灵or节肢
                if(event.getEntity().getMobType() == MobType.UNDEAD || event.getEntity().getMobType() == MobType.ARTHROPOD){
                    event.setAmount(event.getAmount()*number);
                }
            }
        }
    }
}