package com.inolia_zaicek.more_tetra_tools.Effect;

import com.inolia_zaicek.more_tetra_tools.Damage.MTTTickZero;
import com.inolia_zaicek.more_tetra_tools.MoreTetraTools;
import com.inolia_zaicek.more_tetra_tools.Register.MTTEffectsRegister;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import se.mickelus.tetra.items.modular.IModularItem;

import static com.inolia_zaicek.more_tetra_tools.Effect.Clent.MTTEffectGuiStats.*;
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE,modid = MoreTetraTools.MODID)
public class RedWolfDagger {
    //新建nbt
    private static final ResourceLocation execution_mode_tick = new ResourceLocation(MoreTetraTools.MODID, "execution_mode");
    //buff获取
    @SubscribeEvent
    public static void tick(TickEvent.PlayerTickEvent event) {
        Player player = event.player;
        ItemStack mainHandItem = player.getMainHandItem();
        if (mainHandItem.getItem() instanceof IModularItem item) {
            //状态给予
            float effectLevel = (float) item.getEffectLevel(player.getMainHandItem(), ExecutionModeEffect);
            //获取工具nbt数值
            CompoundTag persistentData = mainHandItem.getOrCreateTag();
            if (effectLevel > 0 && player.hasEffect(MobEffects.INVISIBILITY)) {
                //不存在就是0
                int tick = persistentData.getInt(String.valueOf(execution_mode_tick));
                //上限18s(20
                persistentData.putInt(String.valueOf(execution_mode_tick), Math.min(20 * 20 *2, tick + 1));
                if (tick <= 3 * 20 *2) {
                    player.addEffect(new MobEffectInstance(MTTEffectsRegister.ExecutionMode1.get(), 200, 0));
                    player.removeEffect(MTTEffectsRegister.ExecutionMode2.get());
                    player.removeEffect(MTTEffectsRegister.ExecutionMode3.get());
                    player.removeEffect(MTTEffectsRegister.ExecutionMode4.get());
                }
                if (tick > 3 * 20 *2 && tick <= 7 * 20 *2) {
                    player.addEffect(new MobEffectInstance(MTTEffectsRegister.ExecutionMode2.get(), 200, 0));
                    player.removeEffect(MTTEffectsRegister.ExecutionMode1.get());
                    player.removeEffect(MTTEffectsRegister.ExecutionMode3.get());
                    player.removeEffect(MTTEffectsRegister.ExecutionMode4.get());
                }
                if (tick > 7 * 20 *2 && tick <= 12 * 20 *2) {
                    player.addEffect(new MobEffectInstance(MTTEffectsRegister.ExecutionMode3.get(), 200, 0));
                    player.removeEffect(MTTEffectsRegister.ExecutionMode1.get());
                    player.removeEffect(MTTEffectsRegister.ExecutionMode2.get());
                    player.removeEffect(MTTEffectsRegister.ExecutionMode4.get());
                }
                if (tick >= 18 * 20 *2) {
                    player.addEffect(new MobEffectInstance(MTTEffectsRegister.ExecutionMode4.get(), 200, 0));
                    player.removeEffect(MTTEffectsRegister.ExecutionMode1.get());
                    player.removeEffect(MTTEffectsRegister.ExecutionMode2.get());
                    player.removeEffect(MTTEffectsRegister.ExecutionMode3.get());
                }
                //不满足词条等级或者隐身
            } else {
                //状态去除
                if(effectLevel > 0 ) {
                    persistentData.putInt(String.valueOf(execution_mode_tick), 0);
                }
                player.removeEffect(MTTEffectsRegister.ExecutionMode1.get());
                player.removeEffect(MTTEffectsRegister.ExecutionMode2.get());
                player.removeEffect(MTTEffectsRegister.ExecutionMode3.get());
                player.removeEffect(MTTEffectsRegister.ExecutionMode4.get());
            }
        }
    }
    //增伤
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void hurt(LivingHurtEvent event) {
        if (event.getSource().getEntity() instanceof Player player) {
            ItemStack mainHandItem = player.getMainHandItem();
            if (mainHandItem.getItem() instanceof IModularItem item) {
                float effectLevel = (float) item.getEffectLevel(player.getMainHandItem(), ExecutionModeEffect);
                float number = effectLevel/100;
                float damage = event.getAmount();
                if (effectLevel > 0) {
                    if (player.hasEffect(MTTEffectsRegister.ExecutionMode1.get()) || player.hasEffect(MTTEffectsRegister.ExecutionMode2.get())
                            || player.hasEffect(MTTEffectsRegister.ExecutionMode3.get()) || player.hasEffect(MTTEffectsRegister.ExecutionMode4.get())) {
                        //增伤
                        if(player.hasEffect(MTTEffectsRegister.ExecutionMode1.get())){
                            event.setAmount(damage*number);
                        }
                        if(player.hasEffect(MTTEffectsRegister.ExecutionMode2.get())){
                            event.setAmount(damage*number*3);
                        }
                        if(player.hasEffect(MTTEffectsRegister.ExecutionMode3.get())){
                            event.setAmount(damage*number*5);
                        }
                        if(player.hasEffect(MTTEffectsRegister.ExecutionMode4.get())){
                            event.setAmount(damage*number*7);
                        }
                        //增伤结束，但去除buff在后面
                    }
                }
            }
        }
    }
    //受击斩杀
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onLivingDamage(LivingDamageEvent event) {
        if (event.getSource().getEntity() instanceof Player player) {
            var mob = event.getEntity();
            ItemStack mainHandItem = player.getMainHandItem();
            if (mainHandItem.getItem() instanceof IModularItem item) {
                float effectLevel = (float) item.getEffectLevel(player.getMainHandItem(), executorEffect);
                float mhp = mob.getMaxHealth();
                float hp = mob.getHealth();
                float dhp = hp / mhp;
                if (effectLevel > 0) {
                    if (player.hasEffect(MTTEffectsRegister.ExecutionMode1.get()) || player.hasEffect(MTTEffectsRegister.ExecutionMode2.get())
                            || player.hasEffect(MTTEffectsRegister.ExecutionMode3.get()) || player.hasEffect(MTTEffectsRegister.ExecutionMode4.get())) {
                        var DamageType = MTTTickZero.hasSource(player.level(), MTTTickZero.TICKAMAGE, player);
                        if(player.hasEffect(MTTEffectsRegister.ExecutionMode1.get()) && dhp<0.1f){
                            mob.setLastHurtByPlayer(player);
                            mob.invulnerableTime = 0;
                            mob.hurt(DamageType,hp*effectLevel/100);
                        }
                        if(player.hasEffect(MTTEffectsRegister.ExecutionMode2.get()) && dhp<0.2f){
                            mob.setLastHurtByPlayer(player);
                            mob.invulnerableTime = 0;
                            mob.hurt(DamageType,2*hp*effectLevel/100);
                        }
                        if(player.hasEffect(MTTEffectsRegister.ExecutionMode3.get()) && dhp<0.3f){
                            mob.setLastHurtByPlayer(player);
                            mob.invulnerableTime = 0;
                            mob.hurt(DamageType,3*hp*effectLevel/100);
                        }
                        if(player.hasEffect(MTTEffectsRegister.ExecutionMode4.get()) && dhp<0.4f){
                            mob.setLastHurtByPlayer(player);
                            mob.invulnerableTime = 0;
                            mob.hurt(DamageType,4*hp*effectLevel/100);
                        }
                        player.removeEffect(MobEffects.INVISIBILITY);
                        player.removeEffect(MTTEffectsRegister.ExecutionMode1.get());
                        player.removeEffect(MTTEffectsRegister.ExecutionMode2.get());
                        player.removeEffect(MTTEffectsRegister.ExecutionMode3.get());
                        player.removeEffect(MTTEffectsRegister.ExecutionMode4.get());
                    }
                }
            }
        }
    }
}
