package com.inolia_zaicek.more_tetra_tools.Effect;

import com.inolia_zaicek.more_tetra_tools.Damage.MTTTickZero;
import com.inolia_zaicek.more_tetra_tools.MoreTetraTools;
import com.inolia_zaicek.more_tetra_tools.Register.MTTEffectsRegister;
import com.inolia_zaicek.more_tetra_tools.Util.MTTDamageSourceHelper;
import com.inolia_zaicek.more_tetra_tools.Util.MTTEffectHelper;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import se.mickelus.tetra.items.modular.IModularItem;

import static com.inolia_zaicek.more_tetra_tools.Effect.Clent.MTTEffectGuiStats.*;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE,modid = MoreTetraTools.MODID)
public class Laevatain {
    @SubscribeEvent
    public static void hurt(LivingHurtEvent event) {
        if (event.getSource().getEntity() instanceof LivingEntity player) {
            var mob = event.getEntity();
            ItemStack mainHandItem = player.getMainHandItem();
            ItemStack offhandItem = player.getOffhandItem();
            float effectLevel2 = 0;
            if (mainHandItem.getItem() instanceof IModularItem item) {
                effectLevel2 += item.getEffectLevel(mainHandItem, twilight_Effect);
            }
            if (offhandItem.getItem() instanceof IModularItem item) {
                effectLevel2 += item.getEffectLevel(offhandItem, twilight_Effect);
            }
            if (MTTDamageSourceHelper.isMeleeAttack(event.getSource())) {
                if (effectLevel2 > 0) {
                    var DamageType = MTTTickZero.hasSource(player.level(), MTTTickZero.TICKMAMAGE, player);
                    
                    if(player instanceof Player player1) {
                        mob.setLastHurtByPlayer(player1);
                    }
                    mob.invulnerableTime = 0;
                    mob.hurt(DamageType, event.getAmount() * (1 + effectLevel2 / 100 ) );
                    
                    if(player instanceof Player player1) {
                        mob.setLastHurtByPlayer(player1);
                    }
                    event.setAmount(0);
                }
            }
        }
    }
    //每秒提升状态等级，同时检测状态
    @SubscribeEvent
    public static void tick(TickEvent.PlayerTickEvent event) {
        Player player = event.player;
        ItemStack mainHandItem = player.getMainHandItem();
        ItemStack offhandItem = player.getOffhandItem();
        float effectLevel = 0;
        float effectLevel2 = 0;
        if (mainHandItem.getItem() instanceof IModularItem item) {
            effectLevel += item.getEffectLevel(mainHandItem, remnant_ash_Effect);
            effectLevel2 += item.getEffectLevel(mainHandItem, twilight_Effect);
        }
        if (offhandItem.getItem() instanceof IModularItem item) {
            effectLevel += item.getEffectLevel(offhandItem, remnant_ash_Effect);
            effectLevel2 += item.getEffectLevel(offhandItem, twilight_Effect);
        }
        var DamageType = MTTTickZero.hasSource(player.level(), DamageTypes.MAGIC, player);
        //删除余烬
        if (effectLevel<=0&&player.hasEffect(MTTEffectsRegister.RemnantAsh.get())) {
            player.removeEffect(MTTEffectsRegister.RemnantAsh.get());
            player.hurt(DamageType, player.getMaxHealth()*5);
        }
        //删除黄昏
        if (effectLevel2<=0&&player.hasEffect(MTTEffectsRegister.Twilight.get())) {
            player.removeEffect(MTTEffectsRegister.Twilight.get());
            if(player.getMaxHealth()<player.getHealth()){
                player.setHealth(player.getMaxHealth());
            }
            player.hurt(DamageType, player.getMaxHealth()*5);
        }
        //黄昏等级逐步提升
        if (effectLevel2>0&&player.hasEffect(MTTEffectsRegister.Twilight.get()) && player.level().getGameTime() % 20 == 0) {
            int buffLevel = player.getEffect(MTTEffectsRegister.Twilight.get()).getAmplifier();
            int buffTime = player.getEffect(MTTEffectsRegister.Twilight.get()).getDuration();
            //player.removeEffect(MTTEffectsRegister.Twilight.get());
            if(buffLevel<19) {
                player.addEffect(new MobEffectInstance(MTTEffectsRegister.Twilight.get(), buffTime, Math.min(19, buffLevel + 1)));
            }
            //扣血
            float number =3*player.getMaxHealth()*effectLevel2/100;
            //最大扣除生命值比例*距离20级的进度
            player.hurt(DamageType, number*( (float) (buffLevel + 1) /20));
        }
    }
    //退出黄昏与余烬
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    //退出
    public static void buffOut(MobEffectEvent.Expired event) {
        MobEffectInstance expiredInstance = event.getEffectInstance();
        if (expiredInstance != null) {
            MobEffect expiredEffect = expiredInstance.getEffect();
            //退出余烬
            if (event.getEntity() instanceof Player player && expiredEffect == MTTEffectsRegister.RemnantAsh.get()) {
                ItemStack mainHandItem = player.getMainHandItem();
                ItemStack offhandItem = player.getOffhandItem();
                float effectLevel = 0;
                if (mainHandItem.getItem() instanceof IModularItem item) {
                    effectLevel += item.getEffectLevel(mainHandItem, remnant_ash_Effect);
                }
                if (offhandItem.getItem() instanceof IModularItem item) {
                    effectLevel += item.getEffectLevel(offhandItem, remnant_ash_Effect);
                }
                //余烬到期
                    if (effectLevel > 0) {
                        var DamageType = MTTTickZero.hasSource(player.level(), DamageTypes.MAGIC, player);
                        player.removeEffect(MTTEffectsRegister.Twilight.get());
                        if(player.getMaxHealth()<player.getHealth()){
                            player.setHealth(player.getMaxHealth());
                        }
                        player.hurt(DamageType, player.getMaxHealth()*5);
                    }

            }
        }
    }

    //免死
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void LivingDeathVampire(LivingDeathEvent event) {
        if (event.getEntity() instanceof Player player) {
            ItemStack mainHandItem = player.getMainHandItem();
            ItemStack offhandItem = player.getOffhandItem();
            float effectLevel = 0;
            if (mainHandItem.getItem() instanceof IModularItem item) {
                effectLevel += item.getEffectLevel(mainHandItem, remnant_ash_Effect);
            }
            if (offhandItem.getItem() instanceof IModularItem item) {
                effectLevel += item.getEffectLevel(offhandItem, remnant_ash_Effect);
            }
            //余烬免死【如果工具处于冷却，就不给予余烬了
            //死亡：
            //1.无余烬，工具未冷却：赋予余烬并且免死
            //2.无余烬，工具冷却：不赋予余烬，当场去世
            //3.有余烬，无论工具冷却，免死
            if(effectLevel>0){
                if(player.hasEffect(MTTEffectsRegister.RemnantAsh.get())) {
                    player.setHealth(1);
                    player.deathTime = -2;
                    player.isAlive();
                    event.setCanceled(true);
                }else{
                    //主手工具未冷却且余烬在主手
                    if(!player.getCooldowns().isOnCooldown(mainHandItem.getItem())
                    && mainHandItem.getItem() instanceof IModularItem item && item.getEffectLevel(mainHandItem, remnant_ash_Effect) > 0) {
                        //给予余烬
                        player.addEffect(new MobEffectInstance(MTTEffectsRegister.RemnantAsh.get(), (int) effectLevel * 20, 0));
                        player.setHealth(1);
                        player.deathTime = -2;
                        player.isAlive();
                        event.setCanceled(true);
                        player.getCooldowns().addCooldown(mainHandItem.getItem(), 20 * 9);//设置冷却时间
                        //如果副手有余烬也冷却
                        if (offhandItem.getItem() instanceof IModularItem offItem && offItem.getEffectLevel(offhandItem, remnant_ash_Effect) > 0) {
                            player.getCooldowns().addCooldown(offhandItem.getItem(), 20 * 9);//设置冷却时间
                        }
                    }
                    //副手工具未冷却且余烬在副手
                    else if(!player.getCooldowns().isOnCooldown(offhandItem.getItem())
                            && offhandItem.getItem() instanceof IModularItem item && item.getEffectLevel(offhandItem, remnant_ash_Effect) > 0) {
                        //给予余烬
                        player.addEffect(new MobEffectInstance(MTTEffectsRegister.RemnantAsh.get(), (int) effectLevel * 20, 0));
                        player.setHealth(1);
                        player.deathTime = -2;
                        player.isAlive();
                        event.setCanceled(true);
                        player.getCooldowns().addCooldown(offhandItem.getItem(), 20 * 9);//设置冷却时间
                        //如果主手有余烬也冷却
                        if (mainHandItem.getItem() instanceof IModularItem offItem && offItem.getEffectLevel(mainHandItem, remnant_ash_Effect) > 0) {
                            player.getCooldowns().addCooldown(mainHandItem.getItem(), 20 * 9);//设置冷却时间
                        }
                    }
                }
            }
        }
    }
    //余烬禁止回血
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void heal(LivingHealEvent event) {
        if(event.getEntity()!=null){
            LivingEntity player = event.getEntity();
            float effectLevel = (MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectLevel(player, remnant_ash_Effect));
            //余烬禁疗
            if(effectLevel>0&&player.hasEffect(MTTEffectsRegister.RemnantAsh.get())){
                event.setAmount(0);
            }
        }
    }
}