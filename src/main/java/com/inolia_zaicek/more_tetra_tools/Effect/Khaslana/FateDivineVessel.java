package com.inolia_zaicek.more_tetra_tools.Effect.Khaslana;

import com.inolia_zaicek.more_tetra_tools.Damage.MTTTickZero;
import com.inolia_zaicek.more_tetra_tools.MoreTetraTools;
import com.inolia_zaicek.more_tetra_tools.Register.MTTEffectsRegister;
import com.inolia_zaicek.more_tetra_tools.Util.MTTDamageSourceHelper;
import com.inolia_zaicek.more_tetra_tools.Util.MTTUtil;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import se.mickelus.tetra.items.modular.IModularItem;

import static com.inolia_zaicek.more_tetra_tools.Effect.Clent.MTTEffectGuiStats.*;

@Mod.EventBusSubscriber(modid = MoreTetraTools.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class FateDivineVessel {
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    //退出（到时间
    public static void buffOut(MobEffectEvent.Expired event) {
        MobEffectInstance expiredInstance = event.getEffectInstance();
        if (expiredInstance != null) {
            MobEffect expiredEffect = expiredInstance.getEffect();
            if (event.getEntity() instanceof Player player && expiredEffect == MTTEffectsRegister.HeWhoBearsTheWorldMustBurn.get()) {
                ItemStack mainHandItem = player.getMainHandItem();
                if (mainHandItem.getItem() instanceof IModularItem item) {
                    float effectLevel = (float) item.getEffectLevel(player.getMainHandItem(), fateDivineVesselEffect);
                    if (effectLevel > 0) {
                        player.heal(player.getMaxHealth() / 4);
                        var mobList = MTTUtil.mobList(9, player);
                        float number = effectLevel / 100;
                        for (Mob mobs : mobList) {
                            if (mobs != null) {
                                //获取伤害类型
                                mobs.invulnerableTime = 0;
                                mobs.setLastHurtByPlayer(player);
                                float atk = (float) player.getAttributeValue(Attributes.ATTACK_DAMAGE);
                                var DamageType = MTTTickZero.hasSource(player.level(), MTTTickZero.TRUEDAMAGE, player);
                                mobs.hurt(DamageType, atk * number);
                                mobs.setLastHurtByPlayer(player);
                            }
                        }
                        player.removeEffect(MTTEffectsRegister.Scourge.get());
                        player.removeEffect(MTTEffectsRegister.Scourge1.get());
                        player.removeEffect(MTTEffectsRegister.Scourge2.get());
                        player.removeEffect(MTTEffectsRegister.Scourge3.get());
                        player.removeEffect(MTTEffectsRegister.Scourge4.get());
                        ;
                        player.removeEffect(MTTEffectsRegister.Soulscorch.get());
                        if(player.getMaxHealth()<player.getHealth()){
                            player.setHealth(player.getMaxHealth());
                        }
                    }
                }
            }
        }
    }

    //免死
    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void LivingDeathVampire(LivingDeathEvent event) {
        if (event.getEntity() instanceof Player player) {
            ItemStack mainHandItem = player.getMainHandItem();
            if (mainHandItem.getItem() instanceof IModularItem item) {
                float effectLevel = (float) item.getEffectLevel(player.getMainHandItem(), fateDivineVesselEffect);
                if (effectLevel > 0 && player.hasEffect(MTTEffectsRegister.HeWhoBearsTheWorldMustBurn.get())) {
                    player.removeEffect(MTTEffectsRegister.HeWhoBearsTheWorldMustBurn.get());
                    //复活
                    player.setHealth(player.getMaxHealth() / 4);
                    player.deathTime = -2;
                    player.isAlive();
                    player.invulnerableTime = 40;
                    event.setCanceled(true);
                    //范围伤害
                    var mobList = MTTUtil.mobList(9, player);
                    float number = effectLevel / 100;
                    for (Mob mobs : mobList) {
                        if (mobs != null) {
                            //获取伤害类型
                            mobs.invulnerableTime = 0;
                            mobs.setLastHurtByPlayer(player);
                            float atk = (float) player.getAttributeValue(Attributes.ATTACK_DAMAGE);
                            var DamageType = MTTTickZero.hasSource(player.level(), MTTTickZero.TRUEDAMAGE, player);
                            mobs.hurt(DamageType, atk * number);
                            mobs.setLastHurtByPlayer(player);
                        }
                    }
                    player.removeEffect(MTTEffectsRegister.Scourge.get());
                    player.removeEffect(MTTEffectsRegister.Scourge1.get());
                    player.removeEffect(MTTEffectsRegister.Scourge2.get());
                    player.removeEffect(MTTEffectsRegister.Scourge3.get());
                    player.removeEffect(MTTEffectsRegister.Scourge4.get());
                    player.removeEffect(MTTEffectsRegister.Soulscorch.get());
                    if(player.getMaxHealth()<player.getHealth()){
                        player.setHealth(player.getMaxHealth());
                    }
                }
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void hurt(LivingHurtEvent event) {
        //挨打
        if (event.getEntity() instanceof Player player) {
            if (!player.hasEffect(MTTEffectsRegister.HeWhoBearsTheWorldMustBurn.get())) {
                ItemStack mainHandItem = player.getMainHandItem();
                if (mainHandItem.getItem() instanceof IModularItem item) {
                    //火种
                    float effectLevel = (float) item.getEffectLevel(player.getMainHandItem(), fateDivineVesselEffect);
                    if (effectLevel > 0) {
                        //14 15
                        if (player.hasEffect(MTTEffectsRegister.Coreflame.get())) {
                            int buffLevel = player.getEffect(MTTEffectsRegister.Coreflame.get()).getAmplifier();
                            player.addEffect(new MobEffectInstance(MTTEffectsRegister.Coreflame.get(), 400, Math.min(14, buffLevel + 1)));
                        }
                        //13
                        else if (player.hasEffect(MTTEffectsRegister.Coreflame12.get())) {
                            player.addEffect(new MobEffectInstance(MTTEffectsRegister.Coreflame.get(), 400, 12));
                            player.removeEffect(MTTEffectsRegister.Coreflame12.get());
                        }
                        //12
                        else if (player.hasEffect(MTTEffectsRegister.Coreflame11.get())) {
                            player.removeEffect(MTTEffectsRegister.Coreflame11.get());
                            player.addEffect(new MobEffectInstance(MTTEffectsRegister.Coreflame12.get(), 400, 11));
                        }
                        //11
                        else if (player.hasEffect(MTTEffectsRegister.Coreflame10.get())) {
                            player.removeEffect(MTTEffectsRegister.Coreflame10.get());
                            player.addEffect(new MobEffectInstance(MTTEffectsRegister.Coreflame11.get(), 400, 10));
                        }
                        //10
                        else if (player.hasEffect(MTTEffectsRegister.Coreflame9.get())) {
                            player.removeEffect(MTTEffectsRegister.Coreflame9.get());
                            player.addEffect(new MobEffectInstance(MTTEffectsRegister.Coreflame10.get(), 400, 9));
                        }
                        //9
                        else if (player.hasEffect(MTTEffectsRegister.Coreflame8.get())) {
                            player.removeEffect(MTTEffectsRegister.Coreflame8.get());
                            player.addEffect(new MobEffectInstance(MTTEffectsRegister.Coreflame9.get(), 400, 8));
                        }
                        //8
                        else if (player.hasEffect(MTTEffectsRegister.Coreflame7.get())) {
                            player.removeEffect(MTTEffectsRegister.Coreflame7.get());
                            player.addEffect(new MobEffectInstance(MTTEffectsRegister.Coreflame8.get(), 400, 7));
                        }
                        //7
                        else if (player.hasEffect(MTTEffectsRegister.Coreflame6.get())) {
                            player.removeEffect(MTTEffectsRegister.Coreflame6.get());
                            player.addEffect(new MobEffectInstance(MTTEffectsRegister.Coreflame7.get(), 400, 6));
                        }
                        //6
                        else if (player.hasEffect(MTTEffectsRegister.Coreflame5.get())) {
                            player.removeEffect(MTTEffectsRegister.Coreflame5.get());
                            player.addEffect(new MobEffectInstance(MTTEffectsRegister.Coreflame6.get(), 400, 5));
                        }
                        //5
                        else if (player.hasEffect(MTTEffectsRegister.Coreflame4.get())) {
                            player.removeEffect(MTTEffectsRegister.Coreflame4.get());
                            player.addEffect(new MobEffectInstance(MTTEffectsRegister.Coreflame5.get(), 400, 4));
                        }
                        //4
                        else if (player.hasEffect(MTTEffectsRegister.Coreflame3.get())) {
                            player.removeEffect(MTTEffectsRegister.Coreflame3.get());
                            player.addEffect(new MobEffectInstance(MTTEffectsRegister.Coreflame4.get(), 400, 3));
                        }
                        //3
                        else if (player.hasEffect(MTTEffectsRegister.Coreflame2.get())) {
                            player.removeEffect(MTTEffectsRegister.Coreflame2.get());
                            player.addEffect(new MobEffectInstance(MTTEffectsRegister.Coreflame3.get(), 400, 2));
                        }
                        //2
                        else if (player.hasEffect(MTTEffectsRegister.Coreflame1.get())) {
                            player.removeEffect(MTTEffectsRegister.Coreflame1.get());
                            player.addEffect(new MobEffectInstance(MTTEffectsRegister.Coreflame2.get(), 400, 1));
                        }
                        //1
                        else if (!player.hasEffect(MTTEffectsRegister.Coreflame1.get())) {
                            player.addEffect(new MobEffectInstance(MTTEffectsRegister.Coreflame1.get(), 400, 0));
                        }
                    }
                }
            }
        }
        //进攻
        if (event.getSource().getEntity() instanceof Player player) {
            if (!player.hasEffect(MTTEffectsRegister.HeWhoBearsTheWorldMustBurn.get())&&player.getAttackStrengthScale(0.5f) > 0.9f && MTTDamageSourceHelper.isMeleeAttack(event.getSource())) {
                ItemStack mainHandItem = player.getMainHandItem();
                if (mainHandItem.getItem() instanceof IModularItem item && MTTDamageSourceHelper.isMeleeAttack(event.getSource())) {
                    //火种
                    float effectLevel = (float) item.getEffectLevel(player.getMainHandItem(), fateDivineVesselEffect);
                    if (effectLevel > 0) {
                        //14 15
                        if (player.hasEffect(MTTEffectsRegister.Coreflame.get())) {
                            int buffLevel = player.getEffect(MTTEffectsRegister.Coreflame.get()).getAmplifier();
                            player.addEffect(new MobEffectInstance(MTTEffectsRegister.Coreflame.get(), 400, Math.min(14, buffLevel + 1)));
                        }
                        //13
                        else if (player.hasEffect(MTTEffectsRegister.Coreflame12.get())) {
                            player.addEffect(new MobEffectInstance(MTTEffectsRegister.Coreflame.get(), 400, 12));
                            player.removeEffect(MTTEffectsRegister.Coreflame12.get());
                        }
                        //12
                        else if (player.hasEffect(MTTEffectsRegister.Coreflame11.get())) {
                            player.removeEffect(MTTEffectsRegister.Coreflame11.get());
                            player.addEffect(new MobEffectInstance(MTTEffectsRegister.Coreflame12.get(), 400, 11));
                        }
                        //11
                        else if (player.hasEffect(MTTEffectsRegister.Coreflame10.get())) {
                            player.removeEffect(MTTEffectsRegister.Coreflame10.get());
                            player.addEffect(new MobEffectInstance(MTTEffectsRegister.Coreflame11.get(), 400, 10));
                        }
                        //10
                        else if (player.hasEffect(MTTEffectsRegister.Coreflame9.get())) {
                            player.removeEffect(MTTEffectsRegister.Coreflame9.get());
                            player.addEffect(new MobEffectInstance(MTTEffectsRegister.Coreflame10.get(), 400, 9));
                        }
                        //9
                        else if (player.hasEffect(MTTEffectsRegister.Coreflame8.get())) {
                            player.removeEffect(MTTEffectsRegister.Coreflame8.get());
                            player.addEffect(new MobEffectInstance(MTTEffectsRegister.Coreflame9.get(), 400, 8));
                        }
                        //8
                        else if (player.hasEffect(MTTEffectsRegister.Coreflame7.get())) {
                            player.removeEffect(MTTEffectsRegister.Coreflame7.get());
                            player.addEffect(new MobEffectInstance(MTTEffectsRegister.Coreflame8.get(), 400, 7));
                        }
                        //7
                        else if (player.hasEffect(MTTEffectsRegister.Coreflame6.get())) {
                            player.removeEffect(MTTEffectsRegister.Coreflame6.get());
                            player.addEffect(new MobEffectInstance(MTTEffectsRegister.Coreflame7.get(), 400, 6));
                        }
                        //6
                        else if (player.hasEffect(MTTEffectsRegister.Coreflame5.get())) {
                            player.removeEffect(MTTEffectsRegister.Coreflame5.get());
                            player.addEffect(new MobEffectInstance(MTTEffectsRegister.Coreflame6.get(), 400, 5));
                        }
                        //5
                        else if (player.hasEffect(MTTEffectsRegister.Coreflame4.get())) {
                            player.removeEffect(MTTEffectsRegister.Coreflame4.get());
                            player.addEffect(new MobEffectInstance(MTTEffectsRegister.Coreflame5.get(), 400, 4));
                        }
                        //4
                        else if (player.hasEffect(MTTEffectsRegister.Coreflame3.get())) {
                            player.removeEffect(MTTEffectsRegister.Coreflame3.get());
                            player.addEffect(new MobEffectInstance(MTTEffectsRegister.Coreflame4.get(), 400, 3));
                        }
                        //3
                        else if (player.hasEffect(MTTEffectsRegister.Coreflame2.get())) {
                            player.removeEffect(MTTEffectsRegister.Coreflame2.get());
                            player.addEffect(new MobEffectInstance(MTTEffectsRegister.Coreflame3.get(), 400, 2));
                        }
                        //2
                        else if (player.hasEffect(MTTEffectsRegister.Coreflame1.get())) {
                            player.removeEffect(MTTEffectsRegister.Coreflame1.get());
                            player.addEffect(new MobEffectInstance(MTTEffectsRegister.Coreflame2.get(), 400, 1));
                        }
                        //1
                        else if (!player.hasEffect(MTTEffectsRegister.Coreflame1.get())) {
                            player.addEffect(new MobEffectInstance(MTTEffectsRegister.Coreflame1.get(), 400, 0));
                        }
                    }
                }
            }
        }
    }
}