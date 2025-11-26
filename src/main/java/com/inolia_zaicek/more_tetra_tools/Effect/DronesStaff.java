package com.inolia_zaicek.more_tetra_tools.Effect;

import com.inolia_zaicek.more_tetra_tools.Damage.MTTTickZero;
import com.inolia_zaicek.more_tetra_tools.MoreTetraTools;
import com.inolia_zaicek.more_tetra_tools.Register.MTTEffectsRegister;
import com.inolia_zaicek.more_tetra_tools.Util.MTTUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import se.mickelus.tetra.blocks.workbench.gui.WorkbenchStatsGui;
import se.mickelus.tetra.gui.stats.StatsHelper;
import se.mickelus.tetra.gui.stats.bar.GuiStatBar;
import se.mickelus.tetra.gui.stats.getter.*;
import se.mickelus.tetra.items.modular.IModularItem;
import se.mickelus.tetra.items.modular.impl.holo.gui.craft.HoloStatsGui;

import java.util.Random;

import static com.inolia_zaicek.more_tetra_tools.Effect.Clent.MTTEffectGuiStats.*;
import static net.minecraft.tags.DamageTypeTags.WITCH_RESISTANT_TO;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE,modid = MoreTetraTools.MODID)
public class DronesStaff {
    //新建nbt，用于被动冷却(不然无脑连炸了）
    private static final ResourceLocation beacon_s_wrath_tick = new ResourceLocation(MoreTetraTools.MODID, "beacon_s_wrath_nbt");
    @OnlyIn(Dist.CLIENT)
    public static void init() {
        var statGetter1 = new StatGetterEffectLevel(beacon_s_wrath_Effect, 1);
        var statGetter2 = new StatGetterEffectEfficiency(beacon_s_wrath_Effect, 1);
        IStatGetter[] statGetters = {statGetter1, statGetter2};
        IStatFormat[] statFormats = {StatFormat.noDecimal, StatFormat.noDecimal};
        GuiStatBar statBar = new GuiStatBar(0, 0, StatsHelper.barLength,
                beacon_s_wrath_Name, 0, 50, false, false, false,
                statGetter1, LabelGetterBasic.integerLabel,
                new TooltipGetterMultiValue(beacon_s_wrath_Tooltip, statGetters, statFormats)
        );
        WorkbenchStatsGui.addBar(statBar);
        HoloStatsGui.addBar(statBar);
    }

    @SubscribeEvent
    public static void tick(TickEvent.PlayerTickEvent event) {
        Player player = event.player;
        ItemStack mainHandItem = player.getMainHandItem();
        ItemStack offhandItem = player.getOffhandItem();
        if(player.level().getGameTime() % 10 == 0) {
            //主手1技能大于0，赋予一个nbt，
            if (mainHandItem.getItem() instanceof IModularItem item && item.getEffectLevel(mainHandItem, beacon_s_wrath_Effect) > 0) {
                //获取工具nbt数值
                CompoundTag persistentData = mainHandItem.getOrCreateTag();
                //每tick归零
                persistentData.putInt(String.valueOf(beacon_s_wrath_tick), 0);
            }
            else if (offhandItem.getItem() instanceof IModularItem item && item.getEffectLevel(offhandItem, beacon_s_wrath_Effect) > 0) {
                //获取工具nbt数值
                CompoundTag persistentData = offhandItem.getOrCreateTag();
                //每tick归零
                persistentData.putInt(String.valueOf(beacon_s_wrath_tick), 0);
            }
        }
        //三技能
        float thirdLevel = 0;
        if(mainHandItem.getItem() instanceof IModularItem item){
            thirdLevel += item.getEffectLevel(mainHandItem, crystalline_shine_Effect);
        }
        if (offhandItem.getItem() instanceof IModularItem item) {
            thirdLevel += item.getEffectLevel(offhandItem, crystalline_shine_Effect);
        }
        if(thirdLevel>0&&player.hasEffect(MTTEffectsRegister.CrystallineShine.get())){
            var DamageType = MTTTickZero.hasSource(player.level(), MTTTickZero.TICKMAMAGE, player);
            float atkDamage = (float) player.getAttributeValue(Attributes.ATTACK_DAMAGE);
            float atkSpeed = (float) player.getAttributeValue(Attributes.ATTACK_SPEED);
            int attackTick = Math.max(1, (int) (40 - atkSpeed));
            if(player.level().getGameTime() % (attackTick) == 0){
                //造成范围伤害
                var mobList = MTTUtil.mobList(11, player);
                float number = thirdLevel / 100;
                for (Mob mobs : mobList) {
                    if (mobs != null) {
                        mobs.invulnerableTime = 0;
                        mobs.hurt(DamageType, atkDamage * number);
                        mobs.invulnerableTime = 0;
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
            }
        }
    }
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void hurt(LivingHurtEvent event) {
        if (event.getSource().getEntity() instanceof Player player) {
            ItemStack mainHandItem = player.getMainHandItem();
            ItemStack offhandItem = player.getOffhandItem();
            var mob = event.getEntity();
            float baseLevel = 0;
            float firstLevel = 0;
            float baseLevel2 = 0;
            float secondLevel = 0;
            float thirdLevel = 0;
            if (mainHandItem.getItem() instanceof IModularItem item) {
                baseLevel += item.getEffectLevel(mainHandItem, beacon_s_wrath_Effect);
                baseLevel2 += item.getEffectEfficiency(mainHandItem, beacon_s_wrath_Effect);
                firstLevel += item.getEffectLevel(mainHandItem, scattering_sparks_Effect);
                secondLevel += item.getEffectLevel(mainHandItem, surging_current_Effect);
                thirdLevel += item.getEffectLevel(mainHandItem, crystalline_shine_Effect);
            }
            if (offhandItem.getItem() instanceof IModularItem item) {
                baseLevel += item.getEffectLevel(offhandItem, beacon_s_wrath_Effect);
                baseLevel2 += item.getEffectEfficiency(offhandItem, beacon_s_wrath_Effect);
                firstLevel += item.getEffectLevel(offhandItem, scattering_sparks_Effect);
                secondLevel += item.getEffectLevel(offhandItem, surging_current_Effect);
                thirdLevel += item.getEffectLevel(offhandItem, crystalline_shine_Effect);
            }
            //二技能赋予状态放在法伤增幅部分
            //引爆【af，to，iron的放在法伤增幅内
            if(baseLevel>0&&event.getSource().is(WITCH_RESISTANT_TO)){
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
                            mobs.setLastHurtByPlayer(player);
                            //有2技能buff
                            if(mobs.hasEffect(MTTEffectsRegister.SurgingCurrent.get())) {
                                int buffTime = mobs.getEffect(MTTEffectsRegister.SurgingCurrent.get()).getDuration();
                                //1秒0.01,20tick--0.01/20=0.0005
                                mobs.hurt(DamageType, damage * number * (1+buffTime*0.0005f));
                            }else{
                                mobs.hurt(DamageType, damage * number);
                            }
                            mobs.setLastHurtByPlayer(player);
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
        else if (event.getSource().getDirectEntity() instanceof Player player) {
            ItemStack mainHandItem = player.getMainHandItem();
            ItemStack offhandItem = player.getOffhandItem();
            var mob = event.getEntity();
            float baseLevel = 0;
            float firstLevel = 0;
            float baseLevel2 = 0;
            float secondLevel = 0;
            float thirdLevel = 0;
            if (mainHandItem.getItem() instanceof IModularItem item) {
                baseLevel += item.getEffectLevel(mainHandItem, beacon_s_wrath_Effect);
                baseLevel2 += item.getEffectEfficiency(mainHandItem, beacon_s_wrath_Effect);
                firstLevel += item.getEffectLevel(mainHandItem, scattering_sparks_Effect);
                secondLevel += item.getEffectLevel(mainHandItem, surging_current_Effect);
                thirdLevel += item.getEffectLevel(mainHandItem, crystalline_shine_Effect);
            }
            if (offhandItem.getItem() instanceof IModularItem item) {
                baseLevel += item.getEffectLevel(offhandItem, beacon_s_wrath_Effect);
                baseLevel2 += item.getEffectEfficiency(offhandItem, beacon_s_wrath_Effect);
                firstLevel += item.getEffectLevel(offhandItem, scattering_sparks_Effect);
                secondLevel += item.getEffectLevel(offhandItem, surging_current_Effect);
                thirdLevel += item.getEffectLevel(offhandItem, crystalline_shine_Effect);
            }
            //二技能赋予状态放在法伤增幅部分
            //引爆【af，to，iron的放在法伤增幅内
            if(baseLevel>0&&event.getSource().is(WITCH_RESISTANT_TO)){
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
                            mobs.setLastHurtByPlayer(player);
                            //有2技能buff
                            if(mobs.hasEffect(MTTEffectsRegister.SurgingCurrent.get())) {
                                int buffTime = mobs.getEffect(MTTEffectsRegister.SurgingCurrent.get()).getDuration();
                                //1秒0.01,20tick--0.01/20=0.0005
                                mobs.hurt(DamageType, damage * number * (1+buffTime*0.0005f));
                            }else{
                                mobs.hurt(DamageType, damage * number);
                            }
                            mobs.setLastHurtByPlayer(player);
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
    //三技能
    @SubscribeEvent
    public static void use(PlayerInteractEvent.RightClickItem event) {
        Player player = event.getEntity();
            ItemStack clickedItem = event.getItemStack(); // 玩家右键点击的物品
            InteractionHand hand = event.getHand();     // 玩家进行交互的手
        if(clickedItem.getItem() instanceof IModularItem iModularItem) {
            //获取3技能词条
            float thirdLevel = iModularItem.getEffectLevel(clickedItem, crystalline_shine_Effect);
            if (thirdLevel > 0&&player.isShiftKeyDown()) {
                player.addEffect(new MobEffectInstance(MTTEffectsRegister.CrystallineShine.get(), 20*30, 0));
            }else{
                player.removeEffect(MTTEffectsRegister.CrystallineShine.get());
            }
        }
    }
}
