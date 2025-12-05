package com.inolia_zaicek.more_tetra_tools.Effect;

import com.inolia_zaicek.more_tetra_tools.MoreTetraTools;
import com.inolia_zaicek.more_tetra_tools.Util.MTTDamageSourceHelper;
import com.inolia_zaicek.more_tetra_tools.Util.MTTEffectHelper;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import se.mickelus.tetra.blocks.workbench.gui.WorkbenchStatsGui;
import se.mickelus.tetra.gui.stats.StatsHelper;
import se.mickelus.tetra.gui.stats.bar.GuiStatBar;
import se.mickelus.tetra.gui.stats.getter.*;
import se.mickelus.tetra.items.modular.impl.holo.gui.craft.HoloStatsGui;

import java.math.BigDecimal;

import static com.inolia_zaicek.more_tetra_tools.Effect.Clent.MTTEffectGuiStats.*;
import static com.inolia_zaicek.more_tetra_tools.Effect.Clent.MTTEffectGuiStats.resurgence_Effect;
import static com.inolia_zaicek.more_tetra_tools.Effect.Clent.MTTEffectGuiStats.resurgence_Name;
import static com.inolia_zaicek.more_tetra_tools.Effect.Clent.MTTEffectGuiStats.resurgence_Tooltip;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE,modid = MoreTetraTools.MODID)
public class BloodSacrificeBlade {
    @OnlyIn(Dist.CLIENT)
    public static void init() {
        var statGetter1 = new StatGetterEffectLevel(blood_devourer_Effect, 1);
        var statGetter2 = new StatGetterEffectEfficiency(blood_devourer_Effect, 1);
        IStatGetter[] statGetters = {statGetter1, statGetter2};
        IStatFormat[] statFormats = {StatFormat.noDecimal, StatFormat.noDecimal};
        GuiStatBar statBar1 = new GuiStatBar(0, 0, StatsHelper.barLength,
                blood_devourer_Name, 0, 100, false, false, false,
                statGetter1, LabelGetterBasic.integerLabel,
                new TooltipGetterMultiValue(blood_devourer_Tooltip, statGetters, statFormats)
        );
        WorkbenchStatsGui.addBar(statBar1);
        HoloStatsGui.addBar(statBar1);
        var statGetter3 = new StatGetterEffectLevel(blood_glare_Effect, 1);
        var statGetter4 = new StatGetterEffectEfficiency(blood_glare_Effect, 1);
        IStatGetter[] statGetters1 = {statGetter3, statGetter4};
        IStatFormat[] statFormats1 = {StatFormat.noDecimal, StatFormat.noDecimal};
        GuiStatBar statBar2 = new GuiStatBar(0, 0, StatsHelper.barLength,
                blood_glare_Name, 0, 100, false, false, false,
                statGetter3, LabelGetterBasic.integerLabel,
                new TooltipGetterMultiValue(blood_glare_Tooltip, statGetters1, statFormats1)
        );
        WorkbenchStatsGui.addBar(statBar2);
        HoloStatsGui.addBar(statBar2);
    }

    @SubscribeEvent
    public static void hurt(LivingHurtEvent event) {
        if (MTTDamageSourceHelper.isMeleeAttack(event.getSource())) {
            if (event.getSource().getEntity() instanceof LivingEntity livingEntity) {
                float effectLevel1 = MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectLevel(livingEntity, blood_devourer_Effect);
                float effectEfficiency1 = MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectEfficiency(livingEntity, blood_devourer_Effect);
                float effectLevel2 = MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectLevel(livingEntity, blood_glare_Effect);
                float effectEfficiency2 = MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectEfficiency(livingEntity, blood_glare_Effect);
                float hp = livingEntity.getHealth();
                float mhp = livingEntity.getMaxHealth();
                float dhp = hp/mhp;
                float nhp = 1-dhp;
                if(effectLevel1>0||effectLevel2>0){
                    //特殊列表加成（吸血量）
                    if(isUltimateBossEntity(event.getEntity().getType())&&effectEfficiency1 > 0){
                        effectEfficiency1=effectEfficiency1*3;
                    }
                    //特殊列表加成（2词条增伤
                    if(isUltimateBossEntity(event.getEntity().getType())&&effectLevel2 > 0){
                        effectLevel2=effectLevel2*3;
                    }
                    //1增伤之前的伤害
                    float damage = event.getAmount() * (1 + effectLevel1 /100+(effectLevel2/100)*dhp);
                    //回血量占生命值比例比
                    float hpDamage = Math.min(1,(damage*effectEfficiency1/100)/mhp);
                    //最终伤害：1增伤*回血量/最大生命值+2增伤*当前生命值比例
                    event.setAmount( event.getAmount() * (1 + (effectLevel1 * hpDamage )/100 + (effectLevel2/100)*dhp) );
                    //最终回血不吃当前百分比生命值加成
                    livingEntity.heal(damage *effectEfficiency1/100);
                }
            }
        }
        //挨打
        if (event.getEntity()!=null) {
            LivingEntity livingEntity = event.getEntity();
            float effectEfficiency2 = MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectEfficiency(livingEntity, blood_glare_Effect);
            float hp = livingEntity.getHealth();
            float mhp = livingEntity.getMaxHealth();
            //101%-已损失生命值比例，剩余1%就是101-1=100%%，满额
            float nhp = 1.01F - hp/mhp;
            if(effectEfficiency2>0) {
                //特殊列表加成（2词条减伤
                if(isUltimateBossEntity(event.getEntity().getType())){
                    effectEfficiency2=effectEfficiency2*2;
                }
                //减伤：已损失(1+99)%*减伤数额
                event.setAmount(event.getAmount() * (1 - nhp * effectEfficiency2 / 100));
            }
        }
    }
}
