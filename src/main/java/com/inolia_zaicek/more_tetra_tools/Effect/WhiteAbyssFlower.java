package com.inolia_zaicek.more_tetra_tools.Effect;

import com.inolia_zaicek.more_tetra_tools.Register.MTTEffectsRegister;
import com.inolia_zaicek.more_tetra_tools.Util.MTTDamageSourceHelper;
import com.inolia_zaicek.more_tetra_tools.Util.MTTEffectHelper;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.mickelus.tetra.blocks.workbench.gui.WorkbenchStatsGui;
import se.mickelus.tetra.gui.stats.StatsHelper;
import se.mickelus.tetra.gui.stats.bar.GuiStatBar;
import se.mickelus.tetra.gui.stats.getter.*;
import se.mickelus.tetra.items.modular.impl.holo.gui.craft.HoloStatsGui;

import java.util.Optional;
import java.util.UUID;

import static com.inolia_zaicek.more_tetra_tools.Effect.Clent.MTTEffectGuiStats.*;

public class WhiteAbyssFlower {
    private static final Logger log = LoggerFactory.getLogger(WhiteAbyssFlower.class);

    @OnlyIn(Dist.CLIENT)
    public static void init() {
        var statGetter1 = new StatGetterEffectLevel(the_six_divine_key_of_creation_Effect, 1);
        var statGetter2 = new StatGetterEffectEfficiency(the_six_divine_key_of_creation_Effect, 1);
        IStatGetter[] statGetters = {statGetter1, statGetter2};
        IStatFormat[] statFormats = {StatFormat.noDecimal, StatFormat.noDecimal};
        GuiStatBar statBar1 = new GuiStatBar(0, 0, StatsHelper.barLength,
                the_six_divine_key_of_creation_Name, 0, 10, false, false, false,
                statGetter1, LabelGetterBasic.integerLabel,
                new TooltipGetterMultiValue(the_six_divine_key_of_creation_Tooltip, statGetters, statFormats)
        );
        WorkbenchStatsGui.addBar(statBar1);
        HoloStatsGui.addBar(statBar1);
    }

    @SubscribeEvent
    public static void hurt(LivingHurtEvent event) {
        if (MTTDamageSourceHelper.isMeleeAttack(event.getSource())) {
            if (event.getSource().getEntity() instanceof LivingEntity livingEntity) {
                //如果是玩家，但是计时器不满足
                if (livingEntity instanceof Player player && player.getAttackStrengthScale(0.5f) <= 0.9f) {
                    return;
                }
                float effectLevel = MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectLevel(livingEntity, the_six_divine_key_of_creation_Effect);
                float effectEfficiency = MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectEfficiency(livingEntity, the_six_divine_key_of_creation_Effect);
                if (effectLevel > 0||effectEfficiency>0) {
                    float hp = livingEntity.getHealth();
                    float mhp = livingEntity.getMaxHealth();
                    float healNumber = effectLevel+ mhp*effectEfficiency/100;
                    livingEntity.heal(healNumber);
                    float damage = 0;
                    //治疗大于已损失生命值
                    if(healNumber>mhp-hp){
                        //溢出生命值
                        damage+=healNumber-(mhp-hp);
                        event.setAmount(event.getAmount()+damage);
                    }
                }
            }else if (event.getSource().getDirectEntity() instanceof LivingEntity livingEntity) {
                //如果是玩家，但是计时器不满足
                if (livingEntity instanceof Player player && player.getAttackStrengthScale(0.5f) <= 0.9f) {
                    return;
                }
                float effectLevel = MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectLevel(livingEntity, the_six_divine_key_of_creation_Effect);
                float effectEfficiency = MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectEfficiency(livingEntity, the_six_divine_key_of_creation_Effect);
                if (effectLevel > 0||effectEfficiency>0) {
                    float hp = livingEntity.getHealth();
                    float mhp = livingEntity.getMaxHealth();
                    float healNumber = effectLevel+ mhp*effectEfficiency/100;
                    livingEntity.heal(healNumber);
                    float damage = 0;
                    //治疗大于已损失生命值
                    if(healNumber>mhp-hp){
                        //溢出生命值
                        damage+=healNumber-(mhp-hp);
                        event.setAmount(event.getAmount()+damage);
                    }
                }
            }
        }
    }
}
