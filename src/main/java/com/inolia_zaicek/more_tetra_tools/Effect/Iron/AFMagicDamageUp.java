package com.inolia_zaicek.more_tetra_tools.Effect.Iron;

import com.inolia_zaicek.more_tetra_tools.Damage.MTTTickZero;
import com.inolia_zaicek.more_tetra_tools.MoreTetraTools;
import com.inolia_zaicek.more_tetra_tools.Register.MTTEffectsRegister;
import com.inolia_zaicek.more_tetra_tools.Util.MTTEffectHelper;
import com.inolia_zaicek.more_tetra_tools.Util.MTTUtil;
import net.alshanex.alshanex_familiars.datagen.AFDamageTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import se.mickelus.tetra.items.modular.IModularItem;

import java.util.Random;

import static com.inolia_zaicek.more_tetra_tools.Effect.Clent.MTTEffectGuiStats.*;

public class AFMagicDamageUp {
    private static final ResourceLocation beacon_s_wrath_tick = new ResourceLocation(MoreTetraTools.MODID, "beacon_s_wrath_nbt");
    @SubscribeEvent
    public static void hurt(LivingHurtEvent event) {
        if (ModList.get().isLoaded("irons_spellbooks")) {
            if (event.getSource().getEntity() instanceof LivingEntity player) {
                var mob = event.getEntity();
                var map = mob.getActiveEffectsMap();
                ItemStack mainHandItem = player.getMainHandItem();
                ItemStack offhandItem = player.getOffhandItem();

                float effectLevel = (MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectLevel(player, magicDamageUpEffect));
                float effectLevel2 = (MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectLevel(player, dark_greatsword_Effect));
                if(player.hasEffect(MTTEffectsRegister.DarkErosion.get())) {
                    int buffLevel = player.getEffect(MTTEffectsRegister.DarkErosion.get()).getAmplifier()+1;
                    effectLevel += buffLevel * (MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectLevel(player, dark_whispers_domination_Effect));
                }
                float baseLevel = (MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectLevel(player, beacon_s_wrath_Effect));
                float baseLevel2 = (MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectEfficiency(player, beacon_s_wrath_Effect));
                float firstLevel = (MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectLevel(player, scattering_sparks_Effect));
                float secondLevel = (MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectLevel(player, surging_current_Effect));
                float thirdLevel = (MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectLevel(player, crystalline_shine_Effect));
                if (event.getSource().is(AFDamageTypes.SOUND_MAGIC)) {
                    if (effectLevel > 0) {
                        float finish = event.getAmount() * (1 + effectLevel / 100);
                        event.setAmount(finish);
                    }
                    if (effectLevel2 > 0) {
                        float finish = event.getAmount() * (effectLevel2 / 100);
                        var playerList = MTTUtil.PlayerList(6, mob);
                        for (Player players : playerList) {
                            if (players != null) {
                                players.heal(finish);
                            }
                        }
                    }//澄闪状态赋予
                    if(secondLevel>0){
                        if(!mob.hasEffect(MTTEffectsRegister.SurgingCurrent.get())) {
                            map.put(MTTEffectsRegister.SurgingCurrent.get(),
                                    new MobEffectInstance(MTTEffectsRegister.SurgingCurrent.get(), (int) (secondLevel*20), 0));
                        }else{
                            int buffTime = mob.getEffect(MTTEffectsRegister.SurgingCurrent.get()).getDuration();
                            map.put(MTTEffectsRegister.SurgingCurrent.get(),
                                    new MobEffectInstance(MTTEffectsRegister.SurgingCurrent.get(), (int) (buffTime+secondLevel*20), 0));
                        }
                    }
                    //澄闪引爆
                    if(baseLevel>0){
                        //基础引爆几率
                        Random random = new Random();
                        float chance = 0;
                        chance+=baseLevel;
                        //玩家有1技能
                        if(firstLevel>0&&player.hasEffect(MTTEffectsRegister.ScatteringSparks.get())){
                            int buffLevel = player.getEffect(MTTEffectsRegister.ScatteringSparks.get()).getAmplifier();
                            chance += firstLevel*buffLevel;
                        }
                        CompoundTag persistentData=null;
                        //获取工具nbt数值（主副手）【必然存在，因为其中一个大于0了】
                        if(mainHandItem.getItem() instanceof IModularItem item&&item.getEffectLevel(mainHandItem, beacon_s_wrath_Effect)>0){
                            persistentData = mainHandItem.getOrCreateTag();
                        }
                        else if(offhandItem.getItem() instanceof IModularItem item&&item.getEffectLevel(offhandItem, beacon_s_wrath_Effect)>0) {
                            persistentData = offhandItem.getOrCreateTag();
                        }
                        //被动结算【且nbt等于0
                        if (random.nextInt(100) <= chance && persistentData.getInt(String.valueOf(beacon_s_wrath_tick))==0 ) {
                            //造成范围伤害
                            persistentData.putInt(String.valueOf(beacon_s_wrath_tick), 1);
                            var mobList = MTTUtil.mobList(4, mob);
                            float number = baseLevel2 / 100;
                            float damage = event.getAmount();
                            var DamageType = MTTTickZero.hasSource(player.level(), MTTTickZero.TICKMAMAGE,player);
                            //范围
                            for (Mob mobs : mobList) {
                                if (mobs != null) {
                                    //获取伤害类型
                                    mobs.invulnerableTime = 0;
                                    
                    if(player instanceof Player player1) {
                        mobs.setLastHurtByPlayer(player1);
                    }
                                    //有2技能buff
                                    if(mobs.hasEffect(MTTEffectsRegister.SurgingCurrent.get())) {
                                        int buffTime = mobs.getEffect(MTTEffectsRegister.SurgingCurrent.get()).getDuration();
                                        //1秒0.01,20tick--0.01/20=0.0005
                                        mobs.hurt(DamageType, damage * number * (1+buffTime*0.0005f));
                                    }else{
                                        mobs.hurt(DamageType, damage * number);
                                    }

                    if(player instanceof Player player1) {
                        mobs.setLastHurtByPlayer(player1);
                    }
                                }
                            }

                            player.level().playSound(
                                    null, // 玩家参数为 null 表示音效来自世界，不绑定到特定玩家
                                    player.getX(),
                                    player.getY(),
                                    player.getZ(),
                                    SoundEvents.EXPERIENCE_ORB_PICKUP, //音效
                                    SoundSource.PLAYERS, // 音效来源类别，这里是玩家动作
                                    0.6F, // 音量 (1.0f 是标准音量)
                                    1.0F
                            );
                            player.removeEffect(MTTEffectsRegister.ScatteringSparks.get());
                        }else{
                            //没触发，提升1技能等级
                            if(firstLevel>0) {
                                if (player.hasEffect(MTTEffectsRegister.ScatteringSparks.get())) {
                                    int buffLevel = player.getEffect(MTTEffectsRegister.ScatteringSparks.get()).getAmplifier();
                                    player.addEffect(new MobEffectInstance(MTTEffectsRegister.ScatteringSparks.get(), 400, buffLevel + 1));
                                } else {
                                    player.addEffect(new MobEffectInstance(MTTEffectsRegister.ScatteringSparks.get(), 400, 0));
                                }
                            }
                        }
                    }
                }
            } else if (event.getSource().getDirectEntity() instanceof LivingEntity player) {
                var mob = event.getEntity();
                var map = mob.getActiveEffectsMap();
                ItemStack mainHandItem = player.getMainHandItem();
                ItemStack offhandItem = player.getOffhandItem();
                float effectLevel = (MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectLevel(player, magicDamageUpEffect));
                float effectLevel2 = (MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectLevel(player, dark_greatsword_Effect));
                if(player.hasEffect(MTTEffectsRegister.DarkErosion.get())) {
                    int buffLevel = player.getEffect(MTTEffectsRegister.DarkErosion.get()).getAmplifier()+1;
                    effectLevel += buffLevel * (MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectLevel(player, dark_whispers_domination_Effect));
                }
                float baseLevel = (MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectLevel(player, beacon_s_wrath_Effect));
                float baseLevel2 = (MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectEfficiency(player, beacon_s_wrath_Effect));
                float firstLevel = (MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectLevel(player, scattering_sparks_Effect));
                float secondLevel = (MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectLevel(player, surging_current_Effect));
                float thirdLevel = (MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectLevel(player, crystalline_shine_Effect));
                if (event.getSource().is(AFDamageTypes.SOUND_MAGIC)) {
                    if (effectLevel > 0) {
                        float finish = event.getAmount() * (1 + effectLevel / 100);
                        event.setAmount(finish);
                    }
                    if (effectLevel2 > 0) {
                        float finish = event.getAmount() * (effectLevel2 / 100);
                        var playerList = MTTUtil.PlayerList(6, mob);
                        for (Player players : playerList) {
                            if (players != null) {
                                players.heal(finish);
                            }
                        }
                    }
                    //澄闪状态赋予
                    if(secondLevel>0){
                        if(!mob.hasEffect(MTTEffectsRegister.SurgingCurrent.get())) {
                            map.put(MTTEffectsRegister.SurgingCurrent.get(),
                                    new MobEffectInstance(MTTEffectsRegister.SurgingCurrent.get(), (int) (secondLevel*20), 0));
                        }else{
                            int buffTime = mob.getEffect(MTTEffectsRegister.SurgingCurrent.get()).getDuration();
                            map.put(MTTEffectsRegister.SurgingCurrent.get(),
                                    new MobEffectInstance(MTTEffectsRegister.SurgingCurrent.get(), (int) (buffTime+secondLevel*20), 0));
                        }
                    }
                    //澄闪引爆
                    if(baseLevel>0){
                        //基础引爆几率
                        Random random = new Random();
                        float chance = 0;
                        chance+=baseLevel;
                        //玩家有1技能
                        if(firstLevel>0&&player.hasEffect(MTTEffectsRegister.ScatteringSparks.get())){
                            int buffLevel = player.getEffect(MTTEffectsRegister.ScatteringSparks.get()).getAmplifier();
                            chance += firstLevel*buffLevel;
                        }
                        CompoundTag persistentData=null;
                        //获取工具nbt数值（主副手）【必然存在，因为其中一个大于0了】
                        if(mainHandItem.getItem() instanceof IModularItem item&&item.getEffectLevel(mainHandItem, beacon_s_wrath_Effect)>0){
                            persistentData = mainHandItem.getOrCreateTag();
                        }
                        else if(offhandItem.getItem() instanceof IModularItem item&&item.getEffectLevel(offhandItem, beacon_s_wrath_Effect)>0) {
                            persistentData = offhandItem.getOrCreateTag();
                        }
                        //被动结算【且nbt等于0
                        if (random.nextInt(100) <= chance && persistentData.getInt(String.valueOf(beacon_s_wrath_tick))==0 ) {
                            //造成范围伤害
                            persistentData.putInt(String.valueOf(beacon_s_wrath_tick), 1);
                            var mobList = MTTUtil.mobList(4, mob);
                            float number = baseLevel2 / 100;
                            float damage = event.getAmount();
                            var DamageType = MTTTickZero.hasSource(player.level(), MTTTickZero.TICKMAMAGE,player);
                            //范围
                            for (Mob mobs : mobList) {
                                if (mobs != null) {
                                    //获取伤害类型
                                    mobs.invulnerableTime = 0;

                    if(player instanceof Player player1) {
                        mobs.setLastHurtByPlayer(player1);
                    }
                                    //有2技能buff
                                    if(mobs.hasEffect(MTTEffectsRegister.SurgingCurrent.get())) {
                                        int buffTime = mobs.getEffect(MTTEffectsRegister.SurgingCurrent.get()).getDuration();
                                        //1秒0.01,20tick--0.01/20=0.0005
                                        mobs.hurt(DamageType, damage * number * (1+buffTime*0.0005f));
                                    }else{
                                        mobs.hurt(DamageType, damage * number);
                                    }

                    if(player instanceof Player player1) {
                        mobs.setLastHurtByPlayer(player1);
                    }
                                }
                            }

                            player.level().playSound(
                                    null, // 玩家参数为 null 表示音效来自世界，不绑定到特定玩家
                                    player.getX(),
                                    player.getY(),
                                    player.getZ(),
                                    SoundEvents.EXPERIENCE_ORB_PICKUP, //音效
                                    SoundSource.PLAYERS, // 音效来源类别，这里是玩家动作
                                    0.6F, // 音量 (1.0f 是标准音量)
                                    1.0F
                            );
                            player.removeEffect(MTTEffectsRegister.ScatteringSparks.get());
                        }else{
                            //没触发，提升1技能等级
                            if(firstLevel>0) {
                                if (player.hasEffect(MTTEffectsRegister.ScatteringSparks.get())) {
                                    int buffLevel = player.getEffect(MTTEffectsRegister.ScatteringSparks.get()).getAmplifier();
                                    player.addEffect(new MobEffectInstance(MTTEffectsRegister.ScatteringSparks.get(), 400, buffLevel + 1));
                                } else {
                                    player.addEffect(new MobEffectInstance(MTTEffectsRegister.ScatteringSparks.get(), 400, 0));
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
