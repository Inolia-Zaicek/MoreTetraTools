package com.inolia_zaicek.more_tetra_tools.Effect.ReturnToAshKatana;

import com.inolia_zaicek.more_tetra_tools.Damage.MTTTickZero;
import com.inolia_zaicek.more_tetra_tools.Util.MTTEffectHelper;
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
import static net.minecraft.tags.DamageTypeTags.IS_FIRE;

public class DuskToDusk {
    @OnlyIn(Dist.CLIENT)
    public static void init() {
        var statGetter1 = new StatGetterEffectLevel(dusk_to_dusk_Effect, 1);
        var statGetter2 = new StatGetterEffectEfficiency(dusk_to_dusk_Effect, 1);
        IStatGetter[] statGetters = {statGetter1, statGetter2};
        IStatFormat[] statFormats = {StatFormat.noDecimal, StatFormat.noDecimal};
        GuiStatBar statBar = new GuiStatBar(0, 0, StatsHelper.barLength,
                dusk_to_dusk_Name, 0, 100, false, false, false,
                statGetter1, LabelGetterBasic.integerLabel,
                new TooltipGetterMultiValue(dusk_to_dusk_Tooltip, statGetters, statFormats)
        );
        WorkbenchStatsGui.addBar(statBar);
        HoloStatsGui.addBar(statBar);
    }
    @SubscribeEvent
    public static void hurt(LivingHurtEvent event) {
            if (event.getSource().getEntity() instanceof LivingEntity livingEntity) {
                float effectLevel = MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectLevel(livingEntity, dusk_to_dusk_Effect);
                float hpLevel = MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectEfficiency(livingEntity, dusk_to_dusk_Effect);
                float dhp = event.getEntity().getHealth() / event.getEntity().getMaxHealth();
                if(isUltimateBossEntity(event.getEntity().getType())&&effectLevel > 0){
                    effectLevel=effectLevel*5;
                }
                if(isUltimateBossEntity(event.getEntity().getType())&&hpLevel > 0){
                    hpLevel=hpLevel*3;
                }
                if (effectLevel > 0&&dhp<=hpLevel) {
                    float baseDamage = event.getAmount();
                    event.setAmount(baseDamage * (1 + effectLevel / 100));
                }
            } else if (event.getSource().getDirectEntity() instanceof LivingEntity livingEntity) {
                float effectLevel = MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectLevel(livingEntity, dusk_to_dusk_Effect);
                float hpLevel = MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectEfficiency(livingEntity, dusk_to_dusk_Effect);
                float dhp = event.getEntity().getHealth() / event.getEntity().getMaxHealth();
                if(isUltimateBossEntity(event.getEntity().getType())&&effectLevel > 0){
                    effectLevel=effectLevel*5;
                }
                if(isUltimateBossEntity(event.getEntity().getType())&&hpLevel > 0){
                    hpLevel=hpLevel*3;
                }
                if (effectLevel > 0&&dhp<=hpLevel) {
                    float baseDamage = event.getAmount();
                    event.setAmount(baseDamage * (1 + effectLevel / 100));
                }
            }
    }
}