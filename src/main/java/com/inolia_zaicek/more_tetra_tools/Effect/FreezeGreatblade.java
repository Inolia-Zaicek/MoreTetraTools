package com.inolia_zaicek.more_tetra_tools.Effect;

import com.inolia_zaicek.more_tetra_tools.Damage.MTTTickZero;
import com.inolia_zaicek.more_tetra_tools.MoreTetraTools;
import com.inolia_zaicek.more_tetra_tools.Register.MTTEffectsRegister;
import com.inolia_zaicek.more_tetra_tools.Util.MTTDamageSourceHelper;
import com.inolia_zaicek.more_tetra_tools.Util.MTTUtil;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import se.mickelus.tetra.items.modular.IModularItem;

import static com.inolia_zaicek.more_tetra_tools.Effect.Clent.MTTEffectGuiStats.*;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE,modid = MoreTetraTools.MODID)
public class FreezeGreatblade {
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
                effectLevel += item.getEffectLevel(mainHandItem, freezeBladeEffect);
            }
            if (offhandItem.getItem() instanceof IModularItem item) {
                effectLevel += item.getEffectLevel(offhandItem, freezeBladeEffect);
            }
            if(isUltimateBossEntity(event.getEntity().getType())&&effectLevel > 0){
                effectLevel=effectLevel*5;
            }
            if (MTTDamageSourceHelper.isMeleeAttack(event.getSource())) {
                if (effectLevel > 0) {
                    var DamageType = MTTTickZero.hasSource(player.level(), MTTTickZero.TickFreezeDamage, player);
                    float baseDamage=event.getAmount();
                    mob.invulnerableTime = 0;
                    //增伤在其他地方
                    mob.hurt(DamageType, baseDamage);
                    event.setAmount(0);
                    if(! isUltimateBossEntity(event.getEntity().getType()) ) {
                        mob.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 40, 1));
                        map.put(MobEffects.MOVEMENT_SLOWDOWN,
                                new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 40, 1));
                    }else{
                        mob.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 9));
                        map.put(MobEffects.MOVEMENT_SLOWDOWN,
                                new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 9));
                    }
                }
            }
        }
        //减伤
        if (event.getEntity() instanceof Player player) {
            ItemStack mainHandItem = player.getMainHandItem();
            ItemStack offhandItem = player.getOffhandItem();
            float effectLevel = 0;
            if (mainHandItem.getItem() instanceof IModularItem item) {
                effectLevel += item.getEffectLevel(mainHandItem, FreezeTimeEffect);
            }
            if (offhandItem.getItem() instanceof IModularItem item) {
                effectLevel += item.getEffectLevel(offhandItem, FreezeTimeEffect);
            }
            if(isUltimateBossEntity(event.getEntity().getType())&&effectLevel > 0){
                effectLevel=effectLevel*5;
            }
                if (effectLevel > 0 && player.hasEffect(MTTEffectsRegister.FreezeTime.get()) ) {
                    float baseDamage=event.getAmount();
                    event.setAmount(baseDamage * (1 - effectLevel / 100));
            }
        }
    }
    @SubscribeEvent
    public static void tick(TickEvent.PlayerTickEvent event) {
        Player player = event.player;
        ItemStack mainHandItem = player.getMainHandItem();
        ItemStack offhandItem = player.getOffhandItem();
        float effectLevel = 0;
        if (mainHandItem.getItem() instanceof IModularItem item) {
            effectLevel += item.getEffectLevel(mainHandItem, FreezeTimeEffect);
        }
        if (offhandItem.getItem() instanceof IModularItem item) {
            effectLevel += item.getEffectLevel(offhandItem, FreezeTimeEffect);
        }
        if (effectLevel > 0 && player.hasEffect(MTTEffectsRegister.FreezeTime.get()) && player.level().getGameTime() % 10 == 0) {
            var mobList = MTTUtil.mobList(5, player);
            float number = effectLevel / 100;
            for (Mob mobs : mobList) {
                //有主人的随从才不会挨打
                if ( !(mobs instanceof OwnableEntity ownableEntity && ownableEntity.getOwnerUUID() != null) ) {
                    //获取伤害类型
                    mobs.invulnerableTime = 0;
                    if(isUltimateBossEntity(mobs.getType())){
                        number=number*5;
                    }
                    mobs.setLastHurtByPlayer(player);
                    float atk = (float) player.getAttributeValue(Attributes.ATTACK_DAMAGE);
                    var DamageType = MTTTickZero.hasSource(player.level(), MTTTickZero.TickFreezeDamage,player);
                    mobs.hurt(DamageType, atk * number);
                    mobs.setLastHurtByPlayer(player);
                    mobs.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 40, 1));
                    var map = mobs.getActiveEffectsMap();
                    map.put(MobEffects.MOVEMENT_SLOWDOWN,
                            new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 40, 1));
                }
            }
        }
    }
}