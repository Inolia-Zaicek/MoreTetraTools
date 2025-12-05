package com.inolia_zaicek.more_tetra_tools.Effect;

import com.inolia_zaicek.more_tetra_tools.MoreTetraTools;
import com.inolia_zaicek.more_tetra_tools.Register.MTTEffectsRegister;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import se.mickelus.tetra.items.modular.IModularItem;

import static com.inolia_zaicek.more_tetra_tools.Effect.Clent.MTTEffectGuiStats.*;
import static net.minecraft.tags.DamageTypeTags.WITCH_RESISTANT_TO;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE,modid = MoreTetraTools.MODID)
public class CelestialGlobe {
    private static final ResourceLocation celestial_globe_tick = new ResourceLocation(MoreTetraTools.MODID, "celestial_globe");
    @SubscribeEvent
    public static void tick(LivingEvent.LivingTickEvent event) {
        LivingEntity player = event.getEntity();
        ItemStack mainHandItem = player.getMainHandItem();
        if (mainHandItem.getItem() instanceof IModularItem item) {
            float effectLevel = (float) item.getEffectLevel(player.getMainHandItem(), celestialGlobeEffect);
            if(effectLevel>0){
                //获取工具nbt数值
                CompoundTag persistentData = mainHandItem.getOrCreateTag();
                //不存在就是0
                int tick =persistentData.getInt(String.valueOf(celestial_globe_tick));
                //每秒提升tick，最多4
                persistentData.putInt(String.valueOf(celestial_globe_tick), Math.min(20*4*2, tick + 1));
                //tick满足4
                if(tick>=20*4 *2) {
                    //2-5
                    if (player.hasEffect(MTTEffectsRegister.CelestialGlobe.get())) {
                        int buffLevel = player.getEffect(MTTEffectsRegister.CelestialGlobe.get()).getAmplifier();
                        player.addEffect(new MobEffectInstance(MTTEffectsRegister.CelestialGlobe.get(), 300, Math.min(4,buffLevel+1)));
                        persistentData.putInt(String.valueOf(celestial_globe_tick), 0);
                    }
                    //1
                    else{
                        player.addEffect(new MobEffectInstance(MTTEffectsRegister.CelestialGlobe.get(), 300, 0));
                        persistentData.putInt(String.valueOf(celestial_globe_tick), 0);
                    }
                }
            }else{
                player.removeEffect(MTTEffectsRegister.CelestialGlobe.get());
            }
        }else{
            player.removeEffect(MTTEffectsRegister.CelestialGlobe.get());
        }
    }
    //星剑2词条增伤
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void hurt(LivingHurtEvent event) {
        if (event.getSource().getEntity() instanceof LivingEntity player) {
            var mob = event.getEntity();
            var map = mob.getActiveEffectsMap();
            ItemStack mainHandItem = player.getMainHandItem();
            ItemStack offhandItem = player.getOffhandItem();
            float effectLevel = 0;
            if (mainHandItem.getItem() instanceof IModularItem item) {
                effectLevel += item.getEffectLevel(mainHandItem, celestialGlobeEffect);
                effectLevel += item.getEffectLevel(mainHandItem, astralSwordEffect);
                //黄昏状态，数额+1
                if(player.hasEffect(MTTEffectsRegister.AstralSwordDusk.get())){
                    effectLevel += item.getEffectLevel(mainHandItem, astralSwordEffect);
                }
            }
            if (offhandItem.getItem() instanceof IModularItem item) {
                effectLevel += item.getEffectLevel(offhandItem, celestialGlobeEffect);
                effectLevel += item.getEffectLevel(offhandItem, astralSwordEffect);
                //黄昏状态，数额+1
                if(player.hasEffect(MTTEffectsRegister.AstralSwordDusk.get())){
                    effectLevel += item.getEffectLevel(offhandItem, astralSwordEffect);
                }
            }
            if (effectLevel > 0) {
                if (event.getSource().is(WITCH_RESISTANT_TO)) {
                    float finish = event.getAmount() * (1 + effectLevel / 100);
                    event.setAmount(finish);
                }
            }
        } else if (event.getSource().getDirectEntity() instanceof LivingEntity player) {
            var mob = event.getEntity();
            var map = mob.getActiveEffectsMap();
            ItemStack mainHandItem = player.getMainHandItem();
            ItemStack offhandItem = player.getOffhandItem();
            float effectLevel = 0;
            if (mainHandItem.getItem() instanceof IModularItem item) {
                effectLevel += item.getEffectLevel(mainHandItem, celestialGlobeEffect);
                effectLevel += item.getEffectLevel(mainHandItem, astralSwordEffect);
                //黄昏状态，数额+1
                if(player.hasEffect(MTTEffectsRegister.AstralSwordDusk.get())){
                    effectLevel += item.getEffectLevel(mainHandItem, astralSwordEffect);
                }
            }
            if (offhandItem.getItem() instanceof IModularItem item) {
                effectLevel += item.getEffectLevel(offhandItem, celestialGlobeEffect);
                effectLevel += item.getEffectLevel(offhandItem, astralSwordEffect);
                //黄昏状态，数额+1
                if(player.hasEffect(MTTEffectsRegister.AstralSwordDusk.get())){
                    effectLevel += item.getEffectLevel(offhandItem, astralSwordEffect);
                }
            }
            if (effectLevel > 0) {
                if (event.getSource().is(WITCH_RESISTANT_TO)) {
                    float finish = event.getAmount() * (1 + effectLevel / 100);
                    event.setAmount(finish);
                }
            }
        }
    }
}
