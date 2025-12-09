package com.inolia_zaicek.more_tetra_tools.Effect;

import com.inolia_zaicek.more_tetra_tools.Damage.MTTTickZero;
import com.inolia_zaicek.more_tetra_tools.Util.MTTDamageSourceHelper;
import com.inolia_zaicek.more_tetra_tools.Util.MTTEffectHelper;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import se.mickelus.tetra.blocks.workbench.gui.WorkbenchStatsGui;
import se.mickelus.tetra.gui.stats.StatsHelper;
import se.mickelus.tetra.gui.stats.bar.GuiStatBar;
import se.mickelus.tetra.gui.stats.getter.*;
import se.mickelus.tetra.items.modular.impl.holo.gui.craft.HoloStatsGui;

import static com.inolia_zaicek.more_tetra_tools.Effect.Clent.MTTEffectGuiStats.*;
import static net.minecraft.tags.DamageTypeTags.*;

public class UltimateDragonSword {
    @OnlyIn(Dist.CLIENT)
    public static void init() {
        var statGetter1 = new StatGetterEffectLevel(mix_dragon_breath_Effect, 1);
        var statGetter2 = new StatGetterEffectEfficiency(mix_dragon_breath_Effect, 1);
        IStatGetter[] statGetters = {statGetter1, statGetter2};
        IStatFormat[] statFormats = {StatFormat.noDecimal, StatFormat.noDecimal};
        GuiStatBar statBar1 = new GuiStatBar(0, 0, StatsHelper.barLength,
                mix_dragon_breath_Name, 0, 100, false, false, false,
                statGetter1, LabelGetterBasic.integerLabel,
                new TooltipGetterMultiValue(mix_dragon_breath_Tooltip, statGetters, statFormats)
        );
        WorkbenchStatsGui.addBar(statBar1);
        HoloStatsGui.addBar(statBar1);
    }

    @SubscribeEvent
    public static void hurt(LivingHurtEvent event) {
        if (MTTDamageSourceHelper.isMeleeAttack(event.getSource())) {
            if (event.getSource().getEntity() instanceof LivingEntity livingEntity) {
                var mob = event.getEntity();
                float changeNumber = (MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectLevel(livingEntity, mix_dragon_breath_Effect));
                float damageNumber = (MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectEfficiency(livingEntity, mix_dragon_breath_Effect));
                float damage = event.getAmount();
                //伤转
                if (changeNumber > 0) {
                    event.setAmount(Math.max(0, damage * (1 - changeNumber/100)));
                    mob.invulnerableTime = 0;
                    if (livingEntity instanceof Player player1) {
                        mob.setLastHurtByPlayer(player1);
                    }
                    var DamageType1 = MTTTickZero.hasSource(livingEntity.level(), DamageTypes.ON_FIRE, livingEntity);
                    var DamageType2 = MTTTickZero.hasSource(livingEntity.level(), DamageTypes.FREEZE, livingEntity);
                    var DamageType3 = MTTTickZero.hasSource(livingEntity.level(), DamageTypes.LIGHTNING_BOLT, livingEntity);
                    var DamageType4 = MTTTickZero.hasSource(livingEntity.level(), DamageTypes.DRAGON_BREATH, livingEntity);
                    mob.invulnerableTime = 0;
                    mob.hurt(DamageType1, damage * (damageNumber / 400) * (1 + damageNumber / 100));
                    mob.invulnerableTime = 0;
                    mob.hurt(DamageType2, damage * (damageNumber / 400) * (1 + damageNumber / 100));
                    mob.invulnerableTime = 0;
                    mob.hurt(DamageType3, damage * (damageNumber / 400) * (1 + damageNumber / 100));
                    mob.invulnerableTime = 0;
                    mob.hurt(DamageType4, damage * (damageNumber / 400) * (1 + damageNumber / 100));
                }
                if (damageNumber > 0) {
                    if (event.getSource().is(IS_FIRE)) {
                        event.setAmount(damage * (1 + damageNumber / 100));
                    }
                    if (event.getSource().is(IS_FREEZING)) {
                        event.setAmount(damage * (1 + damageNumber / 100));
                    }
                    if (event.getSource().is(IS_LIGHTNING)) {
                        event.setAmount(damage * (1 + damageNumber / 100));
                    }
                    if (event.getSource().is(DamageTypes.DRAGON_BREATH)) {
                        event.setAmount(damage * (1 + damageNumber / 100));
                    }
                }
            } else if (event.getSource().getDirectEntity() instanceof LivingEntity livingEntity) {
                var mob = event.getEntity();
                float changeNumber = (MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectLevel(livingEntity, mix_dragon_breath_Effect));
                float damageNumber = (MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectEfficiency(livingEntity, mix_dragon_breath_Effect));
                float damage = event.getAmount();
                //伤转
                if (changeNumber > 0) {
                    event.setAmount(Math.max(0, damage * (1 - changeNumber/100)));
                    mob.invulnerableTime = 0;
                    if (livingEntity instanceof Player player1) {
                        mob.setLastHurtByPlayer(player1);
                    }
                    var DamageType1 = MTTTickZero.hasSource(livingEntity.level(), DamageTypes.ON_FIRE, livingEntity);
                    var DamageType2 = MTTTickZero.hasSource(livingEntity.level(), DamageTypes.FREEZE, livingEntity);
                    var DamageType3 = MTTTickZero.hasSource(livingEntity.level(), DamageTypes.LIGHTNING_BOLT, livingEntity);
                    var DamageType4 = MTTTickZero.hasSource(livingEntity.level(), DamageTypes.DRAGON_BREATH, livingEntity);
                    mob.invulnerableTime = 0;
                    mob.hurt(DamageType1, damage * (damageNumber / 400) * (1 + damageNumber / 100));
                    mob.invulnerableTime = 0;
                    mob.hurt(DamageType2, damage * (damageNumber / 400) * (1 + damageNumber / 100));
                    mob.invulnerableTime = 0;
                    mob.hurt(DamageType3, damage * (damageNumber / 400) * (1 + damageNumber / 100));
                    mob.invulnerableTime = 0;
                    mob.hurt(DamageType4, damage * (damageNumber / 400) * (1 + damageNumber / 100));
                }
                if (damageNumber > 0) {
                    if (event.getSource().is(IS_FIRE)) {
                        event.setAmount(damage * (1 + damageNumber / 100));
                    }
                    if (event.getSource().is(IS_FREEZING)) {
                        event.setAmount(damage * (1 + damageNumber / 100));
                    }
                    if (event.getSource().is(IS_LIGHTNING)) {
                        event.setAmount(damage * (1 + damageNumber / 100));
                    }
                    if (event.getSource().is(DamageTypes.DRAGON_BREATH)) {
                        event.setAmount(damage * (1 + damageNumber / 100));
                    }
                }
            }
        }
    }
}