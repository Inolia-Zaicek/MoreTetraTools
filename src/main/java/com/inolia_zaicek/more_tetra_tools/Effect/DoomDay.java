package com.inolia_zaicek.more_tetra_tools.Effect;

import com.inolia_zaicek.more_tetra_tools.Util.MTTDamageSourceHelper;
import com.inolia_zaicek.more_tetra_tools.Util.MTTEffectHelper;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import se.mickelus.tetra.blocks.workbench.gui.WorkbenchStatsGui;
import se.mickelus.tetra.gui.stats.StatsHelper;
import se.mickelus.tetra.gui.stats.bar.GuiStatBar;
import se.mickelus.tetra.gui.stats.getter.*;
import se.mickelus.tetra.items.modular.IModularItem;
import se.mickelus.tetra.items.modular.impl.holo.gui.craft.HoloStatsGui;

import java.util.Random;

import static com.inolia_zaicek.more_tetra_tools.Effect.Clent.MTTEffectGuiStats.*;

public class DoomDay {
    @OnlyIn(Dist.CLIENT)
    public static void init() {
        var statGetter1 = new StatGetterEffectLevel(doom_day_Effect, 1);
        var statGetter2 = new StatGetterEffectEfficiency(doom_day_Effect, 1);
        IStatGetter[] statGetters = {statGetter1, statGetter2};
        IStatFormat[] statFormats = {StatFormat.noDecimal, StatFormat.noDecimal};
        GuiStatBar statBar = new GuiStatBar(0, 0, StatsHelper.barLength,
                doom_day_Name, 0, 15, false, false, false,
                statGetter1, LabelGetterBasic.integerLabel,
                new TooltipGetterMultiValue(doom_day_Tooltip, statGetters, statFormats)
        );
        WorkbenchStatsGui.addBar(statBar);
        HoloStatsGui.addBar(statBar);
    }
    @SubscribeEvent
    public static void hurt(LivingHurtEvent event) {
        if (event.getSource().getEntity() instanceof LivingEntity livingEntity && event.getEntity()!=null) {
            var mob = event.getEntity();
            float chance = MTTEffectHelper.getInstance().getMainOffHandSumEffectLevel(livingEntity, doom_day_Effect);
            float effectLevel = MTTEffectHelper.getInstance().getMainOffHandSumEffectEfficiency(livingEntity, doom_day_Effect);
            Random random = new Random();
            if(effectLevel>0&&random.nextInt(100) <= chance&& MTTDamageSourceHelper.isMeleeAttack(event.getSource())){
                event.setAmount(event.getAmount()+mob.getMaxHealth()*effectLevel/100);
            }
        }else if (event.getSource().getDirectEntity() instanceof LivingEntity livingEntity && event.getEntity()!=null) {
            var mob = event.getEntity();
            float chance = MTTEffectHelper.getInstance().getMainOffHandSumEffectLevel(livingEntity, doom_day_Effect);
            float effectLevel = MTTEffectHelper.getInstance().getMainOffHandSumEffectEfficiency(livingEntity, doom_day_Effect);
            Random random = new Random();
            if(effectLevel>0&&random.nextInt(100) <= chance&& MTTDamageSourceHelper.isMeleeAttack(event.getSource())){
                event.setAmount(event.getAmount()+mob.getMaxHealth()*effectLevel/100);
            }
        }
    }
}
