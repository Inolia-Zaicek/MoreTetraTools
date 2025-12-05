package com.inolia_zaicek.more_tetra_tools.Effect.KaminariKatana;

import com.inolia_zaicek.more_tetra_tools.Damage.MTTTickZero;
import com.inolia_zaicek.more_tetra_tools.MoreTetraTools;
import com.inolia_zaicek.more_tetra_tools.Register.MTTEffectsRegister;
import com.inolia_zaicek.more_tetra_tools.Util.MTTEffectHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.mickelus.tetra.blocks.workbench.gui.WorkbenchStatsGui;
import se.mickelus.tetra.gui.stats.StatsHelper;
import se.mickelus.tetra.gui.stats.bar.GuiStatBar;
import se.mickelus.tetra.gui.stats.getter.*;
import se.mickelus.tetra.items.modular.impl.holo.gui.craft.HoloStatsGui;

import static com.inolia_zaicek.more_tetra_tools.Effect.Clent.MTTEffectGuiStats.*;
import static com.inolia_zaicek.more_tetra_tools.Effect.Clent.MTTEffectGuiStats.yukimau_Tooltip;
import static net.minecraft.tags.DamageTypeTags.IS_FIRE;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE,modid = MoreTetraTools.MODID)
public class Kamui {
    private static final String KamuiNbt = MoreTetraTools.MODID + ":kamui_time";
    @OnlyIn(Dist.CLIENT)
    public static void init() {
        var statGetter1 = new StatGetterEffectLevel(kamui_Effect, 1);
        var statGetter2 = new StatGetterEffectEfficiency(kamui_Effect, 1);
        IStatGetter[] statGetters = {statGetter1, statGetter2};
        IStatFormat[] statFormats = {StatFormat.noDecimal, StatFormat.noDecimal};
        GuiStatBar statBar1 = new GuiStatBar(0, 0, StatsHelper.barLength,
                kamui_Name, 0, 100, false, false, false,
                statGetter1, LabelGetterBasic.integerLabel,
                new TooltipGetterMultiValue(kamui_Tooltip, statGetters, statFormats)
        );
        WorkbenchStatsGui.addBar(statBar1);
        HoloStatsGui.addBar(statBar1);
    }
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void hurt(LivingHurtEvent event) {
        //buff消失
        if (event.getSource().getEntity() instanceof LivingEntity livingEntity&&livingEntity.hasEffect(MTTEffectsRegister.Kamui.get())) {
            int cooldownTime = (int) (20*MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectEfficiency(livingEntity, kamui_Effect));
            CompoundTag compoundTag = livingEntity.getPersistentData();
            compoundTag.putInt(KamuiNbt, cooldownTime);
                livingEntity.removeEffect(MTTEffectsRegister.Kamui.get());
        }
        if (event.getSource().getDirectEntity() instanceof LivingEntity livingEntity&&livingEntity.hasEffect(MTTEffectsRegister.Kamui.get())) {
            int cooldownTime = (int) (20*MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectEfficiency(livingEntity, kamui_Effect));
            CompoundTag compoundTag = livingEntity.getPersistentData();
            compoundTag.putInt(KamuiNbt, cooldownTime);
                livingEntity.removeEffect(MTTEffectsRegister.Kamui.get());
        }
        //减伤
        if (event.getEntity()!=null) {
            LivingEntity livingEntity = event.getEntity();
            float effectLevel = MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectLevel(livingEntity, kamui_Effect);
            if(isUltimateBossEntity(event.getEntity().getType())&&effectLevel > 0){
                effectLevel=effectLevel*3;
            }
            if (effectLevel > 0&&livingEntity.hasEffect(MTTEffectsRegister.Kamui.get())) {
                event.setAmount(event.getAmount()*(1-effectLevel/100));
            }
        }
    }

    @SubscribeEvent
    public static void tick(LivingEvent.LivingTickEvent event) {
        LivingEntity livingEntity = event.getEntity();
        CompoundTag compoundTag = livingEntity.getPersistentData();
        if (compoundTag.contains(KamuiNbt)) {
            int currentTime = compoundTag.getInt(KamuiNbt);
            if (currentTime > 0) {
                compoundTag.putInt(KamuiNbt, currentTime - 1);
            }else{
                float effectLevel = MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectLevel(livingEntity, kamui_Effect);
                if(effectLevel>0) {
                    livingEntity.addEffect(new MobEffectInstance(MTTEffectsRegister.Kamui.get(), 300, 0));
                }
            }
        }
    }
}