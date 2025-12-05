package com.inolia_zaicek.more_tetra_tools.Effect;

import com.inolia_zaicek.more_tetra_tools.Register.MTTEffectsRegister;
import com.inolia_zaicek.more_tetra_tools.Util.MTTDamageSourceHelper;
import com.inolia_zaicek.more_tetra_tools.Util.MTTEffectHelper;
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
public class BlackAbyssFlower {
    @OnlyIn(Dist.CLIENT)
    public static void init() {
        var statGetter1 = new StatGetterEffectLevel(the_six_divine_key_of_decompose_Effect, 1);
        var statGetter2 = new StatGetterEffectEfficiency(the_six_divine_key_of_decompose_Effect, 1);
        IStatGetter[] statGetters = {statGetter1, statGetter2};
        IStatFormat[] statFormats = {StatFormat.noDecimal, StatFormat.noDecimal};
        GuiStatBar statBar1 = new GuiStatBar(0, 0, StatsHelper.barLength,
                the_six_divine_key_of_decompose_Name, 0, 10, false, false, false,
                statGetter1, LabelGetterBasic.integerLabel,
                new TooltipGetterMultiValue(the_six_divine_key_of_decompose_Tooltip, statGetters, statFormats)
        );
        WorkbenchStatsGui.addBar(statBar1);
        HoloStatsGui.addBar(statBar1);
    }

    @SubscribeEvent
    public static void hurt(LivingHurtEvent event) {
        if (MTTDamageSourceHelper.isMeleeAttack(event.getSource())) {
            if (event.getSource().getEntity() instanceof LivingEntity livingEntity) {
                var mob = event.getEntity();
                var map = mob.getActiveEffectsMap();
                float effectLevel = MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectLevel(livingEntity, the_six_divine_key_of_decompose_Effect);
                float effectEfficiency = MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectEfficiency(livingEntity, the_six_divine_key_of_decompose_Effect);
                if (effectLevel > 0||effectEfficiency>0) {
                        map.put(MTTEffectsRegister.GrievousWounds.get(),
                                new MobEffectInstance(MTTEffectsRegister.GrievousWounds.get(), 200, (int) effectEfficiency));
                        //基础
                    Optional.of(event.getEntity())
                            .map(LivingEntity::getAttributes)
                            .filter(manager -> manager.hasAttribute(Attributes.ARMOR))
                            .map(manager -> manager.getInstance(Attributes.ARMOR))
                            .filter(instance -> instance.getModifier(uuid) == null)
                            .ifPresent(instance -> instance.addTransientModifier(
                                    new AttributeModifier(uuid, "the_six_divine_key_of_decompose_armor_penetration", -1 * effectLevel, AttributeModifier.Operation.ADDITION)));
                    Optional.of(event.getEntity())
                            .map(LivingEntity::getAttributes)
                            .filter(manager -> manager.hasAttribute(Attributes.ARMOR))
                            .map(manager -> manager.getInstance(Attributes.ARMOR))
                            .filter(instance -> instance.getModifier(uuid) == null)
                            .ifPresent(instance -> instance.addTransientModifier(
                                    new AttributeModifier(uuid, "the_six_divine_key_of_decompose_armor_penetration", -1 * (effectEfficiency/100), AttributeModifier.Operation.MULTIPLY_TOTAL)));
                    //韧性
                    Optional.of(event.getEntity())
                            .map(LivingEntity::getAttributes)
                            .filter(manager -> manager.hasAttribute(Attributes.ARMOR_TOUGHNESS))
                            .map(manager -> manager.getInstance(Attributes.ARMOR_TOUGHNESS))
                            .filter(instance -> instance.getModifier(uuid) == null)
                            .ifPresent(instance -> instance.addTransientModifier(
                                    new AttributeModifier(uuid, "the_six_divine_key_of_decompose_armor_penetration", -1 * effectLevel, AttributeModifier.Operation.ADDITION)));
                    Optional.of(event.getEntity())
                            .map(LivingEntity::getAttributes)
                            .filter(manager -> manager.hasAttribute(Attributes.ARMOR_TOUGHNESS))
                            .map(manager -> manager.getInstance(Attributes.ARMOR_TOUGHNESS))
                            .filter(instance -> instance.getModifier(uuid) == null)
                            .ifPresent(instance -> instance.addTransientModifier(
                                    new AttributeModifier(uuid, "the_six_divine_key_of_decompose_armor_penetration", -1 * (effectEfficiency/100), AttributeModifier.Operation.MULTIPLY_TOTAL)));
                }
            }
        }
    }
    private static final UUID uuid = UUID.fromString("8F83B186-FA04-98B7-ACEB-5C26DFE511B0");
    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent event) {
        if (event.getSource().getEntity() instanceof LivingEntity livingEntity) {
            float effectLevel = MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectLevel(livingEntity, the_six_divine_key_of_decompose_Effect);
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
