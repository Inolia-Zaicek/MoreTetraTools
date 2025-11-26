package com.inolia_zaicek.more_tetra_tools.Effect.BanaddurTheMagicGreatsword;

import com.inolia_zaicek.more_tetra_tools.Damage.MTTTickZero;
import com.inolia_zaicek.more_tetra_tools.MoreTetraTools;
import com.inolia_zaicek.more_tetra_tools.Register.MTTEffectsRegister;
import com.inolia_zaicek.more_tetra_tools.Util.MTTDamageSourceHelper;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import se.mickelus.tetra.blocks.workbench.gui.WorkbenchStatsGui;
import se.mickelus.tetra.gui.stats.StatsHelper;
import se.mickelus.tetra.gui.stats.bar.GuiStatBar;
import se.mickelus.tetra.gui.stats.getter.*;
import se.mickelus.tetra.items.modular.IModularItem;
import se.mickelus.tetra.items.modular.impl.holo.gui.craft.HoloStatsGui;

import static com.inolia_zaicek.more_tetra_tools.Effect.Clent.MTTEffectGuiStats.*;
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE,modid = MoreTetraTools.MODID)
public class DarkErosionEvent {
    @OnlyIn(Dist.CLIENT)
    public static void init() {
        var statGetter1 = new StatGetterEffectLevel(dark_whispers_domination_Effect, 1);
        var statGetter2 = new StatGetterEffectEfficiency(dark_whispers_domination_Effect, 1);
        IStatGetter[] statGetters = {statGetter1, statGetter2};
        IStatFormat[] statFormats = {StatFormat.noDecimal, StatFormat.noDecimal};
        GuiStatBar statBar = new GuiStatBar(0, 0, StatsHelper.barLength,
                dark_whispers_domination_Name, 0, 50, false, false, false,
                statGetter1, LabelGetterBasic.integerLabel,
                new TooltipGetterMultiValue(dark_whispers_domination_Tooltip, statGetters, statFormats)
        );
        WorkbenchStatsGui.addBar(statBar);
        HoloStatsGui.addBar(statBar);
    }
    @SubscribeEvent
    public static void hurt(LivingHurtEvent event) {
        //挨打增伤
        if (event.getEntity() instanceof Player player) {
            ItemStack mainHandItem = player.getMainHandItem();
            ItemStack offhandItem = player.getOffhandItem();
            float effectLevel = 0;
            if (mainHandItem.getItem() instanceof IModularItem item) {
                effectLevel += item.getEffectEfficiency(mainHandItem, dark_whispers_domination_Effect);
            }
            if (offhandItem.getItem() instanceof IModularItem item) {
                effectLevel += item.getEffectEfficiency(offhandItem, dark_whispers_domination_Effect);
            }
            if (effectLevel > 0&&player.hasEffect(MTTEffectsRegister.DarkErosion.get())) {
                int buffLevel = player.getEffect(MTTEffectsRegister.DarkErosion.get()).getAmplifier() + 1;
                event.setAmount(event.getAmount()*(1+buffLevel*effectLevel/100) );
            }
        }
    }
    @SubscribeEvent
    //退出
    public static void buffOut(MobEffectEvent.Expired event) {
        MobEffectInstance expiredInstance = event.getEffectInstance();
        if (expiredInstance != null) {
            MobEffect expiredEffect = expiredInstance.getEffect();
            if (event.getEntity() instanceof Player player && expiredEffect == MTTEffectsRegister.DarkErosion.get()) {
                //buff等级
                int buffLevel = player.getEffect(MTTEffectsRegister.DarkErosion.get()).getAmplifier();
                //负面
                player.addEffect(new MobEffectInstance(MobEffects.DARKNESS,40,buffLevel));
                player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,40,buffLevel));
                player.addEffect(new MobEffectInstance(MobEffects.WEAKNESS,40,buffLevel));
                player.addEffect(new MobEffectInstance(MobEffects.HUNGER,40,buffLevel));
                player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS,40,buffLevel));
                player.removeEffect(MTTEffectsRegister.DarkErosion.get());
                //等级》0
                if(buffLevel>0) {
                    player.addEffect(new MobEffectInstance(MTTEffectsRegister.DarkErosion.get(),15*20,buffLevel-1));
                }
            }
        }
    }
}
