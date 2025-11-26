package com.inolia_zaicek.more_tetra_tools.Effect.SetsugetsuKatana;

import com.inolia_zaicek.more_tetra_tools.Damage.MTTTickZero;
import com.inolia_zaicek.more_tetra_tools.Register.MTTEffectsRegister;
import com.inolia_zaicek.more_tetra_tools.Util.MTTDamageSourceHelper;
import com.inolia_zaicek.more_tetra_tools.Util.MTTEffectHelper;
import com.inolia_zaicek.more_tetra_tools.Util.MTTUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import se.mickelus.tetra.blocks.workbench.gui.WorkbenchStatsGui;
import se.mickelus.tetra.gui.stats.StatsHelper;
import se.mickelus.tetra.gui.stats.bar.GuiStatBar;
import se.mickelus.tetra.gui.stats.getter.*;
import se.mickelus.tetra.items.modular.impl.holo.gui.craft.HoloStatsGui;

import static com.inolia_zaicek.more_tetra_tools.Effect.Clent.MTTEffectGuiStats.*;


public class Yukimau {
    @OnlyIn(Dist.CLIENT)
    public static void init() {
        var statGetter1 = new StatGetterEffectLevel(yukimau_Effect, 1);
        var statGetter2 = new StatGetterEffectEfficiency(yukimau_Effect, 1);
        IStatGetter[] statGetters = {statGetter1, statGetter2};
        IStatFormat[] statFormats = {StatFormat.noDecimal, StatFormat.noDecimal};
        GuiStatBar statBar1 = new GuiStatBar(0, 0, StatsHelper.barLength,
                yukimau_Name, 0, 100, false, false, false,
                statGetter1, LabelGetterBasic.integerLabel,
                new TooltipGetterMultiValue(yukimau_Tooltip, statGetters, statFormats)
        );
        WorkbenchStatsGui.addBar(statBar1);
        HoloStatsGui.addBar(statBar1);
    }
    @SubscribeEvent
    public static void hurt(LivingHurtEvent event) {
        if (MTTDamageSourceHelper.isMeleeAttack(event.getSource())) {
            //攻击
            if (event.getSource().getEntity() instanceof LivingEntity livingEntity) {
                //如果是玩家，但是计时器不满足
                if (livingEntity instanceof Player player && player.getAttackStrengthScale(0.5f) <= 0.9f) {
                    return;
                }
                var mob = event.getEntity();
                int effectLevel1 = MTTEffectHelper.getInstance().getMainOffHandSumEffectLevel(livingEntity, yukimau_Effect);
                int effectLevel2 = (int) MTTEffectHelper.getInstance().getMainOffHandSumEffectEfficiency(livingEntity, yukimau_Effect);
                if (effectLevel1 > 0) {
                    float number = (float) effectLevel1 / 100;
                    float damage = event.getAmount();
                    if(isUltimateBossEntity(event.getEntity().getType())&&number > 0){
                        number=number*3;
                    }
                    var DamageType = MTTTickZero.hasSource(livingEntity.level(), MTTTickZero.TickFreezeDamage , livingEntity);
                    //自身上buff
                    if(livingEntity.hasEffect(MTTEffectsRegister.Fusasesugetsu.get())) {
                        int buffLevel = livingEntity.getEffect(MTTEffectsRegister.Fusasesugetsu.get()).getAmplifier();
                        livingEntity.addEffect(new MobEffectInstance(MTTEffectsRegister.Fusasesugetsu.get(),200,Math.min(9,buffLevel+1) ));
                    }else{
                        livingEntity.addEffect(new MobEffectInstance(MTTEffectsRegister.Fusasesugetsu.get(),200,0 ));
                    }
                    if(effectLevel2>=1) {
                        int range = 3+effectLevel2;
                        //风花雪月状态下，范围额外提升
                        if(livingEntity.hasEffect(MTTEffectsRegister.Fusasesugetsu.get())){
                            int buffLevel = 1+livingEntity.getEffect(MTTEffectsRegister.Fusasesugetsu.get()).getAmplifier();
                            range+=buffLevel;
                        }
                        //实际范围：range*2-1
                        var mobList = MTTUtil.mobList(range,mob);
                        for (Mob mobs:mobList){
                                //获取伤害类型
                            if (livingEntity instanceof Player player1) {
                                mobs.setLastHurtByPlayer(player1);
                            }
                            mobs.invulnerableTime=0;
                            mobs.hurt(DamageType, damage * number);
                        }
                    }else{
                        mob.invulnerableTime = 0;
                        if (livingEntity instanceof Player player1) {
                            mob.setLastHurtByPlayer(player1);
                        }
                        mob.hurt(DamageType, damage * number);
                    }
                }
            }
        }
    }
}
