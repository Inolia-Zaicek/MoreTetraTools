package com.inolia_zaicek.more_tetra_tools.Effect;

import com.hollingsworth.arsnouveau.common.network.PacketANEffect;
import com.inolia_zaicek.more_tetra_tools.Register.MTTEffectsRegister;
import com.inolia_zaicek.more_tetra_tools.Util.MTTDamageSourceHelper;
import com.inolia_zaicek.more_tetra_tools.Util.MTTEffectHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.EffectInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import se.mickelus.tetra.blocks.workbench.gui.WorkbenchStatsGui;
import se.mickelus.tetra.gui.stats.StatsHelper;
import se.mickelus.tetra.gui.stats.bar.GuiStatBar;
import se.mickelus.tetra.gui.stats.getter.*;
import se.mickelus.tetra.items.modular.impl.holo.gui.craft.HoloStatsGui;

import java.util.Optional;
import java.util.UUID;

import static com.inolia_zaicek.more_tetra_tools.Effect.Clent.MTTEffectGuiStats.*;

public class SentinelsBladeOfNaught {
    @OnlyIn(Dist.CLIENT)
    public static void init() {
        var statGetter1 = new StatGetterEffectLevel(slashed_dream_cries_in_red_Effect, 1);
        var statGetter2 = new StatGetterEffectEfficiency(slashed_dream_cries_in_red_Effect, 1);
        IStatGetter[] statGetters = {statGetter1, statGetter2};
        IStatFormat[] statFormats = {StatFormat.noDecimal, StatFormat.noDecimal};
        GuiStatBar statBar1 = new GuiStatBar(0, 0, StatsHelper.barLength,
                slashed_dream_cries_in_red_Name, 0, 100, false, false, false,
                statGetter1, LabelGetterBasic.integerLabel,
                new TooltipGetterMultiValue(slashed_dream_cries_in_red_Tooltip, statGetters, statFormats)
        );
        WorkbenchStatsGui.addBar(statBar1);
        HoloStatsGui.addBar(statBar1);
    }

    private static final UUID uuid = UUID.fromString("797CBEA3-FB77-99FE-6456-EF27C21F5ACD");

    @SubscribeEvent
    public static void hurt(LivingHurtEvent event) {
        if (MTTDamageSourceHelper.isMeleeAttack(event.getSource())){
            if (event.getSource().getEntity() instanceof LivingEntity livingEntity) {
                float effectLevel = MTTEffectHelper.getInstance().getMainOffHandSumEffectLevel(livingEntity, atop_rainleaf_hangs_oneness_Effect);
                float effectDamage = MTTEffectHelper.getInstance().getMainOffHandSumEffectLevel(livingEntity, slashed_dream_cries_in_red_Effect);
                int effectNumber = (int) MTTEffectHelper.getInstance().getMainOffHandSumEffectEfficiency(livingEntity, slashed_dream_cries_in_red_Effect);
                if (effectLevel > 0) {
                    //不存在残梦状态
                    if (!livingEntity.hasEffect(MTTEffectsRegister.SlashedDream.get())) {
                        livingEntity.addEffect(new MobEffectInstance(MTTEffectsRegister.SlashedDream.get(), 300, 0));
                    } else {
                        //存在时，获取等级
                        int buffLevel = livingEntity.getEffect(MTTEffectsRegister.SlashedDream.get()).getAmplifier();
                        int buffLevel1 = buffLevel + 1;
                        Optional.of(event.getEntity())
                                .map(LivingEntity::getAttributes)
                                .filter(manager -> manager.hasAttribute(Attributes.ARMOR))
                                .map(manager -> manager.getInstance(Attributes.ARMOR))
                                .filter(instance -> instance.getModifier(uuid) == null)
                                .ifPresent(instance -> instance.addTransientModifier(
                                        //减防
                                        new AttributeModifier(uuid, "atop_rainleaf_hangs_oneness_armor_penetration", -1 * (buffLevel1 * effectLevel / 900), AttributeModifier.Operation.MULTIPLY_TOTAL)));
                        //如果满足9级，去除
                        if (buffLevel >= 8) {
                            LivingEntity mob = event.getEntity();
                            int neutralAndHarmfulCount = 0;
                            for (MobEffectInstance effect : mob.getActiveEffects()) {
                                // 判断是否为NEUTRAL或Harmful
                                boolean isNEUTRAL = effect.getEffect().getCategory() == MobEffectCategory.NEUTRAL;
                                boolean isHarmful = effect.getEffect().getCategory() == MobEffectCategory.HARMFUL;
                                // 统计非NEUTRAL且非Harmful的效果
                                if (isNEUTRAL || isHarmful) {
                                    neutralAndHarmfulCount++;
                                }
                            }
                            int finalNumber =9 + Math.min(neutralAndHarmfulCount,effectNumber) ;
                            event.setAmount(event.getAmount() * (1 + finalNumber * effectDamage / 100));
                            livingEntity.removeEffect(MTTEffectsRegister.SlashedDream.get());
                        }
                        //不满足，+1
                        else {
                            livingEntity.addEffect(new MobEffectInstance(MTTEffectsRegister.SlashedDream.get(), 300, buffLevel + 1));
                        }
                    }
                }
            }
        }
    }
    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent event) {
        if (event.getSource().getEntity() instanceof LivingEntity livingEntity) {
            float effectLevel = MTTEffectHelper.getInstance().getMainOffHandSumEffectLevel(livingEntity, atop_rainleaf_hangs_oneness_Effect);
            if (effectLevel > 0) {
                Optional.of(event.getEntity())
                        .map(LivingEntity::getAttributes)
                        .filter(manager -> manager.hasAttribute(Attributes.ARMOR))
                        .map(manager -> manager.getInstance(Attributes.ARMOR))
                        .ifPresent(instance -> instance.removeModifier(uuid));
            }
        }
    }
}