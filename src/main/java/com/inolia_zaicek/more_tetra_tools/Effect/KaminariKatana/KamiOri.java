package com.inolia_zaicek.more_tetra_tools.Effect.KaminariKatana;

import com.inolia_zaicek.more_tetra_tools.Register.MTTEffectsRegister;
import com.inolia_zaicek.more_tetra_tools.Util.MTTEffectHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import se.mickelus.tetra.blocks.workbench.gui.WorkbenchStatsGui;
import se.mickelus.tetra.gui.stats.StatsHelper;
import se.mickelus.tetra.gui.stats.bar.GuiStatBar;
import se.mickelus.tetra.gui.stats.getter.*;
import se.mickelus.tetra.items.modular.IModularItem;
import se.mickelus.tetra.items.modular.impl.holo.gui.craft.HoloStatsGui;

import java.util.Random;

import static com.inolia_zaicek.more_tetra_tools.Effect.Clent.MTTEffectGuiStats.*;

public class KamiOri {
    @OnlyIn(Dist.CLIENT)
    public static void init() {
        var statGetter1 = new StatGetterEffectLevel(kami_ori_Effect, 1);
        var statGetter2 = new StatGetterEffectEfficiency(kami_ori_Effect, 1);
        IStatGetter[] statGetters = {statGetter1, statGetter2};
        IStatFormat[] statFormats = {StatFormat.noDecimal, StatFormat.noDecimal};
        GuiStatBar statBar1 = new GuiStatBar(0, 0, StatsHelper.barLength,
                kami_ori_Name, 0, 100, false, false, false,
                statGetter1, LabelGetterBasic.integerLabel,
                new TooltipGetterMultiValue(kami_ori_Tooltip, statGetters, statFormats)
        );
        WorkbenchStatsGui.addBar(statBar1);
        HoloStatsGui.addBar(statBar1);
    }
    @SubscribeEvent
    public static void hurt(LivingHurtEvent event) {
        //攻击概率神威
        if (event.getSource().getEntity() instanceof LivingEntity livingEntity) {
            float number = (MTTEffectHelper.getInstance().getMainOffHandSumEffectLevel(livingEntity, kami_ori_Effect));
            float chance = (MTTEffectHelper.getInstance().getMainOffHandSumEffectEfficiency(livingEntity, kami_ori_Effect));
            int time = (int) (20 * MTTEffectHelper.getInstance().getMainOffHandSumEffectEfficiency(livingEntity, kami_ori_Effect));
            Random random = new Random();
            if(isUltimateBossEntity(event.getEntity().getType())&&number > 0){
                number=number*3;
            }
            if(isUltimateBossEntity(event.getEntity().getType())&&chance > 0){
                chance=chance*3;
            }
            if (random.nextInt(100) <= chance) {
                event.setAmount(event.getAmount()*(1+number/100));
                //有等级
                if (time > 0) {
                    if(isUltimateBossEntity(event.getEntity().getType())){
                        time=time*3;
                    }
                    livingEntity.addEffect(new MobEffectInstance(MTTEffectsRegister.Kamui.get(), time, 0));
                }
            }
        }else if (event.getSource().getDirectEntity() instanceof LivingEntity livingEntity) {
            float number = (MTTEffectHelper.getInstance().getMainOffHandSumEffectLevel(livingEntity, kami_ori_Effect));
            float chance = (MTTEffectHelper.getInstance().getMainOffHandSumEffectEfficiency(livingEntity, kami_ori_Effect));
            int time = (int) (20 * MTTEffectHelper.getInstance().getMainOffHandSumEffectEfficiency(livingEntity, kami_ori_Effect));
            Random random = new Random();
            if(isUltimateBossEntity(event.getEntity().getType())&&number > 0){
                number=number*3;
            }
            if(isUltimateBossEntity(event.getEntity().getType())&&chance > 0){
                chance=chance*3;
            }
            if (random.nextInt(100) <= chance) {
                event.setAmount(event.getAmount()*(1+number/100));
                //有等级
                if (time > 0) {
                    if(isUltimateBossEntity(event.getEntity().getType())){
                        time=time*3;
                    }
                    livingEntity.addEffect(new MobEffectInstance(MTTEffectsRegister.Kamui.get(), time, 0));
                }
            }
        }
    }
}
