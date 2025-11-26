package com.inolia_zaicek.more_tetra_tools.Effect.Clent;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.ModList;
import se.mickelus.tetra.blocks.workbench.gui.WorkbenchStatsGui;
import se.mickelus.tetra.gui.stats.StatsHelper;
import se.mickelus.tetra.gui.stats.bar.GuiStatBar;
import se.mickelus.tetra.gui.stats.getter.*;
import se.mickelus.tetra.items.modular.impl.holo.gui.craft.HoloStatsGui;

import static com.inolia_zaicek.more_tetra_tools.Effect.Clent.MTTEffectGuiStats.*;
public class MTTEffectClent {
    @OnlyIn(Dist.CLIENT)
    public static void init() {
        //链锯切割：时间x0.05————effect等级为20，时间为1
        var statGetter1 = new StatGetterEffectLevel(sawSeveringEffect, 1);
        GuiStatBar statBar1 = new GuiStatBar(0, 0, StatsHelper.barLength,
                sawSeveringName, 0, 12, false, false, false,
                statGetter1, LabelGetterBasic.integerLabel,
                new se.mickelus.tetra.gui.stats.getter.TooltipGetterDecimalSingle(sawSeveringTooltip, statGetter1)
        );
        WorkbenchStatsGui.addBar(statBar1);
        HoloStatsGui.addBar(statBar1);
        var statGetter2 = new StatGetterEffectLevel(rapidSlashingEffect, 1);
        GuiStatBar statBar2 = new GuiStatBar(0, 0, StatsHelper.barLength,
                rapidSlashingName, 0, 100, false, false, false,
                statGetter2, LabelGetterBasic.integerLabel,
                new se.mickelus.tetra.gui.stats.getter.TooltipGetterDecimalSingle(rapidSlashingTooltip, statGetter2)
        );
        WorkbenchStatsGui.addBar(statBar2);
        HoloStatsGui.addBar(statBar2);
        var statGetter3 = new StatGetterEffectLevel(reapEffect, 1);
        GuiStatBar statBar3 = new GuiStatBar(0, 0, StatsHelper.barLength,
                reapName, 0, 100, false, false, false,
                statGetter3, LabelGetterBasic.integerLabel,
                new se.mickelus.tetra.gui.stats.getter.TooltipGetterDecimalSingle(reapTooltip, statGetter3)
        );
        WorkbenchStatsGui.addBar(statBar3);
        HoloStatsGui.addBar(statBar3);
        var statGetter4 = new StatGetterEffectLevel(grievousWoundsEffect, 1);
        GuiStatBar statBar4 = new GuiStatBar(0, 0, StatsHelper.barLength,
                grievousWoundsName, 0, 100, false, false, false,
                statGetter4, LabelGetterBasic.integerLabel,
                new se.mickelus.tetra.gui.stats.getter.TooltipGetterDecimalSingle(grievousWoundsTooltip, statGetter4)
        );
        WorkbenchStatsGui.addBar(statBar4);
        HoloStatsGui.addBar(statBar4);
        //法术
        if (ModList.get().isLoaded("irons_spellbooks")) {
            var statGetter5 = new StatGetterEffectLevel(ironSpellCastingEffect, 1);
            GuiStatBar statBar5 = new GuiStatBar(0, 0, StatsHelper.barLength,
                    ironSpellCastingName, 0, 1, false, false, false,
                    statGetter5, LabelGetterBasic.integerLabel,
                    new TooltipGetterInteger(ironSpellCastingTooltip, statGetter5)
            );
            WorkbenchStatsGui.addBar(statBar5);
            HoloStatsGui.addBar(statBar5);

            var statGetter6 = new StatGetterEffectLevel(incantationMedicEffect, 1);
            GuiStatBar statBar6 = new GuiStatBar(0, 0, StatsHelper.barLength,
                    incantationMedicName, 0, 100, false, false, false,
                    statGetter6, LabelGetterBasic.integerLabel,
                    new TooltipGetterInteger(incantationMedicTooltip, statGetter6)
            );
            WorkbenchStatsGui.addBar(statBar6);
            HoloStatsGui.addBar(statBar6);
        }
        var statGetter7 = new StatGetterEffectLevel(magicDamageUpEffect, 1);
        GuiStatBar statBar7 = new GuiStatBar(0, 0, StatsHelper.barLength,
                magicDamageUpName, 0, 100, false, false, false,
                statGetter7, LabelGetterBasic.integerLabel,
                new TooltipGetterInteger(magicDamageUpTooltip, statGetter7)
        );
        WorkbenchStatsGui.addBar(statBar7);
        HoloStatsGui.addBar(statBar7);
        var statGetter8 = new StatGetterEffectLevel(chainswordEffect, 1);
        GuiStatBar statBar8 = new GuiStatBar(0, 0, StatsHelper.barLength,
                chainswordName, 0, 1, false, false, false,
                statGetter8, LabelGetterBasic.integerLabel,
                new se.mickelus.tetra.gui.stats.getter.TooltipGetterDecimalSingle(chainswordTooltip, statGetter8)
        );
        WorkbenchStatsGui.addBar(statBar8);
        HoloStatsGui.addBar(statBar8);
        var statGetter9 = new StatGetterEffectLevel(beheadingEffect, 1);
        GuiStatBar statBar9 = new GuiStatBar(0, 0, StatsHelper.barLength,
                beheadingName, 0, 100, false, false, false,
                statGetter9, LabelGetterBasic.integerLabel,
                new se.mickelus.tetra.gui.stats.getter.TooltipGetterDecimalSingle(beheadingTooltip, statGetter9)
        );
        WorkbenchStatsGui.addBar(statBar9);
        HoloStatsGui.addBar(statBar9);
        var statGetter11 = new StatGetterEffectLevel(DomainOfIncandescenceEffect, 1);
        GuiStatBar statBar11 = new GuiStatBar(0, 0, StatsHelper.barLength,
                DomainOfIncandescenceName, 0, 100, false, false, false,
                statGetter11, LabelGetterBasic.integerLabel,
                new TooltipGetterInteger(DomainOfIncandescenceTooltip, statGetter11)
        );
        WorkbenchStatsGui.addBar(statBar11);
        HoloStatsGui.addBar(statBar11);
        var statGetter12 = new StatGetterEffectLevel(yingXiaoFleetingNightEffect, 1);
        GuiStatBar statBar12 = new GuiStatBar(0, 0, StatsHelper.barLength,
                yingXiaoFleetingNightName, 0, 100, false, false, false,
                statGetter12, LabelGetterBasic.integerLabel,
                new TooltipGetterInteger(yingXiaoFleetingNightTooltip, statGetter12)
        );
        WorkbenchStatsGui.addBar(statBar12);
        HoloStatsGui.addBar(statBar12);
        var statGetter13 = new StatGetterEffectLevel(yingXiaoShadowlessEffect, 1);
        GuiStatBar statBar13 = new GuiStatBar(0, 0, StatsHelper.barLength,
                yingXiaoShadowlessName, 0, 100, false, false, false,
                statGetter13, LabelGetterBasic.integerLabel,
                new TooltipGetterInteger(yingXiaoShadowlessTooltip, statGetter13)
        );
        WorkbenchStatsGui.addBar(statBar13);
        HoloStatsGui.addBar(statBar13);
        var statGetter14 = new StatGetterEffectLevel(blazingChariotSTrailEffect, 1);
        GuiStatBar statBar14 = new GuiStatBar(0, 0, StatsHelper.barLength,
                blazingChariotSTrailName, 0, 100, false, false, false,
                statGetter14, LabelGetterBasic.integerLabel,
                new TooltipGetterInteger(blazingChariotSTrailTooltip, statGetter14)
        );
        WorkbenchStatsGui.addBar(statBar14);
        HoloStatsGui.addBar(statBar14);
        var statGetter15 = new StatGetterEffectLevel(lonePhoenixSPlumeEffect, 1);
        GuiStatBar statBar15 = new GuiStatBar(0, 0, StatsHelper.barLength,
                lonePhoenixSPlumeName, 0, 100, false, false, false,
                statGetter15, LabelGetterBasic.integerLabel,
                new TooltipGetterInteger(lonePhoenixSPlumeTooltip, statGetter15)
        );
        WorkbenchStatsGui.addBar(statBar15);
        HoloStatsGui.addBar(statBar15);
        var statGetter16 = new StatGetterEffectLevel(fieryHymnSPledgeEffect, 1);
        GuiStatBar statBar16 = new GuiStatBar(0, 0, StatsHelper.barLength,
                fieryHymnSPledgeName, 0, 100, false, false, false,
                statGetter16, LabelGetterBasic.integerLabel,
                new TooltipGetterInteger(fieryHymnSPledgeTooltip, statGetter16)
        );
        WorkbenchStatsGui.addBar(statBar16);
        HoloStatsGui.addBar(statBar16);
        var statGetter17 = new StatGetterEffectLevel(theBladeSupremeRekindledEffect, 1);
        GuiStatBar statBar17 = new GuiStatBar(0, 0, StatsHelper.barLength,
                theBladeSupremeRekindledName, 0, 1000, false, false, false,
                statGetter17, LabelGetterBasic.integerLabel,
                new TooltipGetterInteger(theBladeSupremeRekindledTooltip, statGetter17)
        );
        WorkbenchStatsGui.addBar(statBar17);
        HoloStatsGui.addBar(statBar17);
        var statGetter18 = new StatGetterEffectLevel(tidalElegyAttackEffect, 1);
        GuiStatBar statBar18 = new GuiStatBar(0, 0, StatsHelper.barLength,
                tidalElegyAttackName, 0, 15, false, false, false,
                statGetter18, LabelGetterBasic.integerLabel,
                new TooltipGetterInteger(tidalElegyAttackTooltip, statGetter18)
        );
        WorkbenchStatsGui.addBar(statBar18);
        HoloStatsGui.addBar(statBar18);
        var statGetter19 = new StatGetterEffectLevel(tidalElegyArmorEffect, 1);
        GuiStatBar statBar19 = new GuiStatBar(0, 0, StatsHelper.barLength,
                tidalElegyArmorName, 0, 15, false, false, false,
                statGetter19, LabelGetterBasic.integerLabel,
                new TooltipGetterInteger(tidalElegyArmorTooltip, statGetter19)
        );
        WorkbenchStatsGui.addBar(statBar19);
        HoloStatsGui.addBar(statBar19);
        var statGetter20 = new StatGetterEffectLevel(tidalElegyHealthEffect, 1);
        GuiStatBar statBar20 = new GuiStatBar(0, 0, StatsHelper.barLength,
                tidalElegyHealthName, 0, 15, false, false, false,
                statGetter20, LabelGetterBasic.integerLabel,
                new TooltipGetterInteger(tidalElegyHealthTooltip, statGetter20)
        );
        WorkbenchStatsGui.addBar(statBar20);
        HoloStatsGui.addBar(statBar20);
        var statGetter21 = new StatGetterEffectLevel(divineAvatarEffect, 1);
        GuiStatBar statBar21 = new GuiStatBar(0, 0, StatsHelper.barLength,
                divineAvatarName, 0, 100, false, false, false,
                statGetter21, LabelGetterBasic.integerLabel,
                new TooltipGetterInteger(divineAvatarTooltip, statGetter21)
        );
        WorkbenchStatsGui.addBar(statBar21);
        HoloStatsGui.addBar(statBar21);
        var statGetter22 = new StatGetterEffectLevel(surgingBrillianceEffect, 1);
        GuiStatBar statBar22 = new GuiStatBar(0, 0, StatsHelper.barLength,
                surgingBrillianceName, 0, 100, false, false, false,
                statGetter22, LabelGetterBasic.integerLabel,
                new TooltipGetterInteger(surgingBrillianceTooltip, statGetter22)
        );
        WorkbenchStatsGui.addBar(statBar22);
        HoloStatsGui.addBar(statBar22);
        var statGetter23 = new StatGetterEffectLevel(blazingSunSObeisanceEffect, 1);
        GuiStatBar statBar23 = new GuiStatBar(0, 0, StatsHelper.barLength,
                blazingSunSObeisanceName, 0, 100, false, false, false,
                statGetter23, LabelGetterBasic.integerLabel,
                new TooltipGetterInteger(blazingSunSObeisanceTooltip, statGetter23)
        );
        WorkbenchStatsGui.addBar(statBar23);
        HoloStatsGui.addBar(statBar23);
        var statGetter24 = new StatGetterEffectLevel(letThereBeLightEffect, 1);
        var statGetter25 = new StatGetterEffectEfficiency(letThereBeLightEffect, 1);
        IStatGetter[] statGetters = { statGetter24,statGetter25};
        IStatFormat[] statFormats = {StatFormat.noDecimal,StatFormat.noDecimal};
        GuiStatBar statBar25 = new GuiStatBar(0, 0, StatsHelper.barLength,
                letThereBeLightName, 0, 100, false, false, false,
                statGetter25, LabelGetterBasic.integerLabel,
                new TooltipGetterMultiValue(letThereBeLightTooltip, statGetters,statFormats)
        );
        WorkbenchStatsGui.addBar(statBar25);
        HoloStatsGui.addBar(statBar25);
        var statGetter26 = new StatGetterEffectLevel(cryNotForTheDiscardedEffect, 1);
        GuiStatBar statBar26 = new GuiStatBar(0, 0, StatsHelper.barLength,
                cryNotForTheDiscardedName, 0, 100, false, false, false,
                statGetter26, LabelGetterBasic.integerLabel,
                new TooltipGetterInteger(cryNotForTheDiscardedTooltip, statGetter26)
        );
        WorkbenchStatsGui.addBar(statBar26);
        HoloStatsGui.addBar(statBar26);
        var statGetter27 = new StatGetterEffectLevel(butSufferingIsEssentialEffect, 1);
        GuiStatBar statBar27 = new GuiStatBar(0, 0, StatsHelper.barLength,
                butSufferingIsEssentialName, 0, 100, false, false, false,
                statGetter27, LabelGetterBasic.integerLabel,
                new TooltipGetterInteger(butSufferingIsEssentialTooltip, statGetter27)
        );
        WorkbenchStatsGui.addBar(statBar27);
        HoloStatsGui.addBar(statBar27);
        var statGetter28 = new StatGetterEffectLevel(fateDivineVesselEffect, 1);
        GuiStatBar statBar28 = new GuiStatBar(0, 0, StatsHelper.barLength,
                fateDivineVesselName, 0, 100, false, false, false,
                statGetter28, LabelGetterBasic.integerLabel,
                new TooltipGetterInteger(fateDivineVesselTooltip, statGetter28)
        );
        WorkbenchStatsGui.addBar(statBar28);
        HoloStatsGui.addBar(statBar28);
        var statGetter29 = new StatGetterEffectLevel(creationBloodthornFerryEffect, 1);
        GuiStatBar statBar29 = new GuiStatBar(0, 0, StatsHelper.barLength,
                creationBloodthornFerryName, 0, 100, false, false, false,
                statGetter29, LabelGetterBasic.integerLabel,
                new TooltipGetterInteger(creationBloodthornFerryTooltip, statGetter29)
        );
        WorkbenchStatsGui.addBar(statBar29);
        HoloStatsGui.addBar(statBar29);
        var statGetter30 = new StatGetterEffectLevel(calamitySoulscorchEdictEffect, 1);
        GuiStatBar statBar30 = new GuiStatBar(0, 0, StatsHelper.barLength,
                calamitySoulscorchEdictName, 0, 100, false, false, false,
                statGetter30, LabelGetterBasic.integerLabel,
                new TooltipGetterInteger(calamitySoulscorchEdictTooltip, statGetter30)
        );
        WorkbenchStatsGui.addBar(statBar30);
        HoloStatsGui.addBar(statBar30);
        var statGetter31 = new StatGetterEffectLevel(foundationStardeathVerdictEffect, 1);
        GuiStatBar statBar31 = new GuiStatBar(0, 0, StatsHelper.barLength,
                foundationStardeathVerdictName, 0, 100, false, false, false,
                statGetter31, LabelGetterBasic.integerLabel,
                new TooltipGetterInteger(foundationStardeathVerdictTooltip, statGetter31)
        );
        WorkbenchStatsGui.addBar(statBar31);
        HoloStatsGui.addBar(statBar31);
        var statGetter32 = new StatGetterEffectLevel(avengerEffect, 1);
        GuiStatBar statBar32 = new GuiStatBar(0, 0, StatsHelper.barLength,
                avengerName, 0, 100, false, false, false,
                statGetter32, LabelGetterBasic.integerLabel,
                new TooltipGetterInteger(avengerTooltip, statGetter32)
        );
        WorkbenchStatsGui.addBar(statBar32);
        HoloStatsGui.addBar(statBar32);
        var statGetter33 = new StatGetterEffectLevel(fireAndSteelEffect, 1);
        GuiStatBar statBar33 = new GuiStatBar(0, 0, StatsHelper.barLength,
                fireAndSteelName, 0, 100, false, false, false,
                statGetter33, LabelGetterBasic.integerLabel,
                new TooltipGetterInteger(fireAndSteelTooltip, statGetter33)
        );
        WorkbenchStatsGui.addBar(statBar33);
        HoloStatsGui.addBar(statBar33);
        var statGetter34 = new StatGetterEffectLevel(executorEffect, 1);
        GuiStatBar statBar34 = new GuiStatBar(0, 0, StatsHelper.barLength,
                executorName, 0, 100, false, false, false,
                statGetter34, LabelGetterBasic.integerLabel,
                new TooltipGetterInteger(executorTooltip, statGetter34)
        );
        WorkbenchStatsGui.addBar(statBar34);
        HoloStatsGui.addBar(statBar34);
        var statGetter35 = new StatGetterEffectLevel(ExecutionModeEffect, 1);
        GuiStatBar statBar35 = new GuiStatBar(0, 0, StatsHelper.barLength,
                ExecutionModeName, 0, 100, false, false, false,
                statGetter35, LabelGetterBasic.integerLabel,
                new TooltipGetterInteger(ExecutionModeTooltip, statGetter35)
        );
        WorkbenchStatsGui.addBar(statBar35);
        HoloStatsGui.addBar(statBar35);
        var statGetter36 = new StatGetterEffectLevel(celestialGlobeEffect, 1);
        GuiStatBar statBar36 = new GuiStatBar(0, 0, StatsHelper.barLength,
                celestialGlobeName, 0, 100, false, false, false,
                statGetter36, LabelGetterBasic.integerLabel,
                new TooltipGetterInteger(celestialGlobeTooltip, statGetter36)
        );
        WorkbenchStatsGui.addBar(statBar36);
        HoloStatsGui.addBar(statBar36);
        var statGetter37 = new StatGetterEffectLevel(astralSwordEffect, 1);
        GuiStatBar statBar37 = new GuiStatBar(0, 0, StatsHelper.barLength,
                astralSwordName, 0, 100, false, false, false,
                statGetter37, LabelGetterBasic.integerLabel,
                new TooltipGetterInteger(astralSwordTooltip, statGetter37)
        );
        WorkbenchStatsGui.addBar(statBar37);
        HoloStatsGui.addBar(statBar37);
        var statGetter38 = new StatGetterEffectLevel(vanguardSwordsmanshipEffect, 1);
        GuiStatBar statBar38 = new GuiStatBar(0, 0, StatsHelper.barLength,
                vanguardSwordsmanshipName, 0, 100, false, false, false,
                statGetter38, LabelGetterBasic.integerLabel,
                new TooltipGetterInteger(vanguardSwordsmanshipTooltip, statGetter38)
        );
        WorkbenchStatsGui.addBar(statBar38);
        HoloStatsGui.addBar(statBar38);
        var statGetter39 = new StatGetterEffectLevel(flameheartEffect, 1);
        GuiStatBar statBar39 = new GuiStatBar(0, 0, StatsHelper.barLength,
                flameheartName, 0, 100, false, false, false,
                statGetter39, LabelGetterBasic.integerLabel,
                new TooltipGetterInteger(flameheartTooltip, statGetter39)
        );
        WorkbenchStatsGui.addBar(statBar39);
        HoloStatsGui.addBar(statBar39);
        var statGetter40 = new StatGetterEffectLevel(copperSealEffect, 1);
        GuiStatBar statBar40 = new GuiStatBar(0, 0, StatsHelper.barLength,
                copperSealName, 0, 100, false, false, false,
                statGetter40, LabelGetterBasic.integerLabel,
                new TooltipGetterInteger(copperSealTooltip, statGetter40)
        );
        WorkbenchStatsGui.addBar(statBar40);
        HoloStatsGui.addBar(statBar40);
        var statGetter41 = new StatGetterEffectLevel(ironDefenseEffect, 1);
        GuiStatBar statBar41 = new GuiStatBar(0, 0, StatsHelper.barLength,
                ironDefenseName, 0, 100, false, false, false,
                statGetter41, LabelGetterBasic.integerLabel,
                new TooltipGetterInteger(ironDefenseTooltip, statGetter41)
        );
        WorkbenchStatsGui.addBar(statBar41);
        HoloStatsGui.addBar(statBar41);
        var statGetter42 = new StatGetterEffectLevel(halfMoonEffect, 1);
        GuiStatBar statBar42 = new GuiStatBar(0, 0, StatsHelper.barLength,
                halfMoonName, 0, 100, false, false, false,
                statGetter42, LabelGetterBasic.integerLabel,
                new TooltipGetterInteger(halfMoonTooltip, statGetter42)
        );
        WorkbenchStatsGui.addBar(statBar42);
        HoloStatsGui.addBar(statBar42);
        var statGetter43 = new StatGetterEffectLevel(waxingMoonEffect, 1);
        GuiStatBar statBar43 = new GuiStatBar(0, 0, StatsHelper.barLength,
                waxingMoonName, 0, 100, false, false, false,
                statGetter43, LabelGetterBasic.integerLabel,
                new TooltipGetterInteger(waxingMoonTooltip, statGetter43)
        );
        WorkbenchStatsGui.addBar(statBar43);
        HoloStatsGui.addBar(statBar43);
        var statGetter44 = new StatGetterEffectLevel(divineDragonPowerEffect, 1);
        GuiStatBar statBar44 = new GuiStatBar(0, 0, StatsHelper.barLength,
                divineDragonPowerName, 0, 200, false, false, false,
                statGetter44, LabelGetterBasic.integerLabel,
                new TooltipGetterInteger(divineDragonPowerTooltip, statGetter44)
        );
        WorkbenchStatsGui.addBar(statBar44);
        HoloStatsGui.addBar(statBar44);
        var statGetter45 = new StatGetterEffectLevel(reviverKillerEffect, 1);
        GuiStatBar statBar45 = new GuiStatBar(0, 0, StatsHelper.barLength,
                reviverKillerName, 0, 200, false, false, false,
                statGetter45, LabelGetterBasic.integerLabel,
                new TooltipGetterInteger(reviverKillerTooltip, statGetter45)
        );
        WorkbenchStatsGui.addBar(statBar45);
        HoloStatsGui.addBar(statBar45);
        var statGetter46 = new StatGetterEffectLevel(freezeBladeEffect, 1);
        GuiStatBar statBar46 = new GuiStatBar(0, 0, StatsHelper.barLength,
                freezeBladeName, 0, 100, false, false, false,
                statGetter46, LabelGetterBasic.integerLabel,
                new TooltipGetterInteger(freezeBladeTooltip, statGetter46)
        );
        WorkbenchStatsGui.addBar(statBar46);
        HoloStatsGui.addBar(statBar46);
        var statGetter47 = new StatGetterEffectLevel(FreezeTimeEffect, 1);
        GuiStatBar statBar47 = new GuiStatBar(0, 0, StatsHelper.barLength,
                FreezeTimeName, 0, 100, false, false, false,
                statGetter47, LabelGetterBasic.integerLabel,
                new TooltipGetterInteger(FreezeTimeTooltip, statGetter47)
        );
        WorkbenchStatsGui.addBar(statBar47);
        HoloStatsGui.addBar(statBar47);
        var statGetter48 = new StatGetterEffectLevel(deityHunterEffect, 1);
        GuiStatBar statBar48 = new GuiStatBar(0, 0, StatsHelper.barLength,
                deityHunterName, 0, 100, false, false, false,
                statGetter48, LabelGetterBasic.integerLabel,
                new TooltipGetterInteger(deityHunterTooltip, statGetter48)
        );
        WorkbenchStatsGui.addBar(statBar48);
        HoloStatsGui.addBar(statBar48);
        var statGetter49 = new StatGetterEffectLevel(mosterHunterEffect, 1);
        GuiStatBar statBar49 = new GuiStatBar(0, 0, StatsHelper.barLength,
                mosterHunterName, 0, 100, false, false, false,
                statGetter49, LabelGetterBasic.integerLabel,
                new TooltipGetterInteger(mosterHunterTooltip, statGetter49)
        );
        WorkbenchStatsGui.addBar(statBar49);
        HoloStatsGui.addBar(statBar49);
        var statGetter50 = new StatGetterEffectLevel(teleportationEffect, 1);
        GuiStatBar statBar50 = new GuiStatBar(0, 0, StatsHelper.barLength,
                teleportationName, 0, 1, false, false, false,
                statGetter50, LabelGetterBasic.integerLabel,
                new TooltipGetterInteger(teleportationTooltip, statGetter50)
        );
        WorkbenchStatsGui.addBar(statBar50);
        HoloStatsGui.addBar(statBar50);
        var statGetter51 = new StatGetterEffectLevel(waypointEffect, 1);
        GuiStatBar statBar51 = new GuiStatBar(0, 0, StatsHelper.barLength,
                waypointName, 0, 1, false, false, false,
                statGetter51, LabelGetterBasic.integerLabel,
                new TooltipGetterInteger(waypointTooltip, statGetter51)
        );
        WorkbenchStatsGui.addBar(statBar51);
        HoloStatsGui.addBar(statBar51);
        var statGetter52 = new StatGetterEffectLevel(gohomeEffect, 1);
        GuiStatBar statBar52 = new GuiStatBar(0, 0, StatsHelper.barLength,
                gohomeName, 0, 1, false, false, false,
                statGetter52, LabelGetterBasic.integerLabel,
                new TooltipGetterInteger(gohomeTooltip, statGetter52)
        );
        WorkbenchStatsGui.addBar(statBar52);
        HoloStatsGui.addBar(statBar52);
        var statGetter53 = new StatGetterEffectLevel(recallEffect, 1);
        GuiStatBar statBar53 = new GuiStatBar(0, 0, StatsHelper.barLength,
                recallName, 0, 1, false, false, false,
                statGetter53, LabelGetterBasic.integerLabel,
                new TooltipGetterInteger(recallTooltip, statGetter53)
        );
        WorkbenchStatsGui.addBar(statBar53);
        HoloStatsGui.addBar(statBar53);
        var statGetter54 = new StatGetterEffectLevel(tenka_ichiken_Effect, 1);
        GuiStatBar statBar54 = new GuiStatBar(0, 0, StatsHelper.barLength,
                tenka_ichiken_Name, 0, 50, false, false, false,
                statGetter54, LabelGetterBasic.integerLabel,
                new TooltipGetterInteger(tenka_ichiken_Tooltip, statGetter54)
        );
        WorkbenchStatsGui.addBar(statBar54);
        HoloStatsGui.addBar(statBar54);
        var statGetter55 = new StatGetterEffectLevel(chokuhi_kyoshin_joseishu_Effect, 1);
        GuiStatBar statBar55 = new GuiStatBar(0, 0, StatsHelper.barLength,
                chokuhi_kyoshin_joseishu_Name, 0, 100, false, false, false,
                statGetter55, LabelGetterBasic.integerLabel,
                new TooltipGetterInteger(chokuhi_kyoshin_joseishu_Tooltip, statGetter55)
        );
        WorkbenchStatsGui.addBar(statBar55);
        HoloStatsGui.addBar(statBar55);
        var statGetter56 = new StatGetterEffectLevel(twilight_Effect, 1);
        GuiStatBar statBar56 = new GuiStatBar(0, 0, StatsHelper.barLength,
                twilight_Name, 0, 100, false, false, false,
                statGetter56, LabelGetterBasic.integerLabel,
                new TooltipGetterInteger(twilight_Tooltip, statGetter56)
        );
        WorkbenchStatsGui.addBar(statBar56);
        HoloStatsGui.addBar(statBar56);
        var statGetter57 = new StatGetterEffectLevel(remnant_ash_Effect, 1);
        GuiStatBar statBar57 = new GuiStatBar(0, 0, StatsHelper.barLength,
                remnant_ash_Name, 0, 10, false, false, false,
                statGetter57, LabelGetterBasic.integerLabel,
                new TooltipGetterInteger(remnant_ash_Tooltip, statGetter57)
        );
        WorkbenchStatsGui.addBar(statBar57);
        HoloStatsGui.addBar(statBar57);
        var statGetter58 = new StatGetterEffectLevel(moon_on_glacial_river_Effect, 1);
        GuiStatBar statBar58 = new GuiStatBar(0, 0, StatsHelper.barLength,
                moon_on_glacial_river_Name, 0, 100, false, false, false,
                statGetter58, LabelGetterBasic.integerLabel,
                new TooltipGetterInteger(moon_on_glacial_river_Tooltip, statGetter58)
        );
        WorkbenchStatsGui.addBar(statBar58);
        HoloStatsGui.addBar(statBar58);
        var statGetter59 = new StatGetterEffectLevel(crescent_transmigration_Effect, 1);
        GuiStatBar statBar59 = new GuiStatBar(0, 0, StatsHelper.barLength,
                crescent_transmigration_Name, 0, 100, false, false, false,
                statGetter59, LabelGetterBasic.integerLabel,
                new TooltipGetterInteger(crescent_transmigration_Tooltip, statGetter59)
        );
        WorkbenchStatsGui.addBar(statBar59);
        HoloStatsGui.addBar(statBar59);
        var statGetter60 = new StatGetterEffectLevel(tool_Effect, 1);
        GuiStatBar statBar60 = new GuiStatBar(0, 0, StatsHelper.barLength,
                tool_Name, 0, 1, false, false, false,
                statGetter60, LabelGetterBasic.integerLabel,
                new TooltipGetterInteger(tool_Tooltip, statGetter60)
        );
        WorkbenchStatsGui.addBar(statBar60);
        HoloStatsGui.addBar(statBar60);
        var statGetter61 = new StatGetterEffectLevel(captureEffect, 1);
        GuiStatBar statBar61 = new GuiStatBar(0, 0, StatsHelper.barLength,
                captureName, 0, 1, false, false, false,
                statGetter61, LabelGetterBasic.integerLabel,
                new TooltipGetterInteger(captureTooltip, statGetter61)
        );
        WorkbenchStatsGui.addBar(statBar61);
        HoloStatsGui.addBar(statBar61);
        var statGetter62 = new StatGetterEffectLevel(ember_Effect, 1);
        GuiStatBar statBar62 = new GuiStatBar(0, 0, StatsHelper.barLength,
                ember_Name, 0, 100, false, false, false,
                statGetter62, LabelGetterBasic.integerLabel,
                new TooltipGetterInteger(ember_Tooltip, statGetter62)
        );
        WorkbenchStatsGui.addBar(statBar62);
        HoloStatsGui.addBar(statBar62);
        var statGetter63 = new StatGetterEffectLevel(shamash_assault_Effect, 1);
        GuiStatBar statBar63 = new GuiStatBar(0, 0, StatsHelper.barLength,
                shamash_assault_Name, 0, 100, false, false, false,
                statGetter63, LabelGetterBasic.integerLabel,
                new TooltipGetterInteger(shamash_assault_Tooltip, statGetter63)
        );
        WorkbenchStatsGui.addBar(statBar63);
        HoloStatsGui.addBar(statBar63);
        var statGetter64 = new StatGetterEffectLevel(narukami_divinity_Effect, 1);
        GuiStatBar statBar64 = new GuiStatBar(0, 0, StatsHelper.barLength,
                narukami_divinity_Name, 0, 100, false, false, false,
                statGetter64, LabelGetterBasic.integerLabel,
                new TooltipGetterInteger(narukami_divinity_Tooltip, statGetter64)
        );
        WorkbenchStatsGui.addBar(statBar64);
        HoloStatsGui.addBar(statBar64);
        var statGetter65 = new StatGetterEffectLevel(forfeiture_Effect, 1);
        GuiStatBar statBar65 = new GuiStatBar(0, 0, StatsHelper.barLength,
                forfeiture_Name, 0, 100, false, false, false,
                statGetter65, LabelGetterBasic.integerLabel,
                new TooltipGetterInteger(forfeiture_Tooltip, statGetter65)
        );
        WorkbenchStatsGui.addBar(statBar65);
        HoloStatsGui.addBar(statBar65);
        var statGetter66 = new StatGetterEffectLevel(crystalline_shine_Effect, 1);
        GuiStatBar statBar66 = new GuiStatBar(0, 0, StatsHelper.barLength,
                crystalline_shine_Name, 0, 100, false, false, false,
                statGetter66, LabelGetterBasic.integerLabel,
                new TooltipGetterInteger(crystalline_shine_Tooltip, statGetter66)
        );
        WorkbenchStatsGui.addBar(statBar66);
        HoloStatsGui.addBar(statBar66);
        var statGetter67 = new StatGetterEffectLevel(surging_current_Effect, 1);
        GuiStatBar statBar67 = new GuiStatBar(0, 0, StatsHelper.barLength,
                surging_current_Name, 0, 10, false, false, false,
                statGetter67, LabelGetterBasic.integerLabel,
                new TooltipGetterInteger(surging_current_Tooltip, statGetter67)
        );
        WorkbenchStatsGui.addBar(statBar67);
        HoloStatsGui.addBar(statBar67);
        var statGetter68 = new StatGetterEffectLevel(scattering_sparks_Effect, 1);
        GuiStatBar statBar68 = new GuiStatBar(0, 0, StatsHelper.barLength,
                scattering_sparks_Name, 0, 20, false, false, false,
                statGetter68, LabelGetterBasic.integerLabel,
                new TooltipGetterInteger(scattering_sparks_Tooltip, statGetter68)
        );
        WorkbenchStatsGui.addBar(statBar68);
        HoloStatsGui.addBar(statBar68);
        var statGetter69 = new StatGetterEffectLevel(weave_Effect, 1);
        GuiStatBar statBar69 = new GuiStatBar(0, 0, StatsHelper.barLength,
                weave_Name, 0, 10, false, false, false,
                statGetter69, LabelGetterBasic.integerLabel,
                new TooltipGetterInteger(weave_Tooltip, statGetter69)
        );
        WorkbenchStatsGui.addBar(statBar69);
        HoloStatsGui.addBar(statBar69);
        var statGetter70 = new StatGetterEffectLevel(delete_Effect, 1);
        GuiStatBar statBar70 = new GuiStatBar(0, 0, StatsHelper.barLength,
                delete_Name, 0, 10, false, false, false,
                statGetter70, LabelGetterBasic.integerLabel,
                new TooltipGetterInteger(delete_Tooltip, statGetter70)
        );
        WorkbenchStatsGui.addBar(statBar70);
        HoloStatsGui.addBar(statBar70);
        var statGetter71 = new StatGetterEffectLevel(disassemble_Effect, 1);
        GuiStatBar statBar71 = new GuiStatBar(0, 0, StatsHelper.barLength,
                disassemble_Name, 0, 100, false, false, false,
                statGetter71, LabelGetterBasic.integerLabel,
                new TooltipGetterInteger(disassemble_Tooltip, statGetter71)
        );
        WorkbenchStatsGui.addBar(statBar71);
        HoloStatsGui.addBar(statBar71);
        var statGetter72 = new StatGetterEffectLevel(forget_Effect, 1);
        GuiStatBar statBar72 = new GuiStatBar(0, 0, StatsHelper.barLength,
                forget_Name, 0, 100, false, false, false,
                statGetter72, LabelGetterBasic.integerLabel,
                new TooltipGetterInteger(forget_Tooltip, statGetter72)
        );
        WorkbenchStatsGui.addBar(statBar72);
        HoloStatsGui.addBar(statBar72);
        var statGetter74 = new StatGetterEffectLevel(ocean_will_Effect, 1);
        GuiStatBar statBar74 = new GuiStatBar(0, 0, StatsHelper.barLength,
                ocean_will_Name, 0, 100, false, false, false,
                statGetter74, LabelGetterBasic.integerLabel,
                new TooltipGetterInteger(ocean_will_Tooltip, statGetter74)
        );
        WorkbenchStatsGui.addBar(statBar74);
        HoloStatsGui.addBar(statBar74);
        var statGetter75 = new StatGetterEffectLevel(amphoreus_saga_of_heroes_Effect, 1);
        GuiStatBar statBar75 = new GuiStatBar(0, 0, StatsHelper.barLength,
                amphoreus_saga_of_heroes_Name, 0, 1, false, false, false,
                statGetter75, LabelGetterBasic.integerLabel,
                new TooltipGetterInteger(amphoreus_saga_of_heroes_Tooltip, statGetter75)
        );
        WorkbenchStatsGui.addBar(statBar75);
        HoloStatsGui.addBar(statBar75);
        var statGetter76 = new StatGetterEffectLevel(this_ode_to_all_lives_Effect, 1);
        GuiStatBar statBar76 = new GuiStatBar(0, 0, StatsHelper.barLength,
                this_ode_to_all_lives_Name, 0, 100, false, false, false,
                statGetter76, LabelGetterBasic.integerLabel,
                new TooltipGetterInteger(this_ode_to_all_lives_Tooltip, statGetter76)
        );
        WorkbenchStatsGui.addBar(statBar76);
        HoloStatsGui.addBar(statBar76);
        var statGetter77 = new StatGetterEffectLevel(verse_zero_vow_infinite_Effect, 1);
        GuiStatBar statBar77 = new GuiStatBar(0, 0, StatsHelper.barLength,
                verse_zero_vow_infinite_Name, 0, 10, false, false, false,
                statGetter77, LabelGetterBasic.integerLabel,
                new TooltipGetterInteger(verse_zero_vow_infinite_Tooltip, statGetter77)
        );
        WorkbenchStatsGui.addBar(statBar77);
        HoloStatsGui.addBar(statBar77);
        var statGetter78 = new StatGetterEffectLevel(bloom_elysium_of_beyond_Effect, 1);
        GuiStatBar statBar78 = new GuiStatBar(0, 0, StatsHelper.barLength,
                bloom_elysium_of_beyond_Name, 0, 10, false, false, false,
                statGetter78, LabelGetterBasic.integerLabel,
                new TooltipGetterInteger(bloom_elysium_of_beyond_Tooltip, statGetter78)
        );
        WorkbenchStatsGui.addBar(statBar78);
        HoloStatsGui.addBar(statBar78);
        var statGetter79 = new StatGetterEffectLevel(minuet_of_blooms_and_plumes_Effect, 1);
        GuiStatBar statBar79 = new GuiStatBar(0, 0, StatsHelper.barLength,
                minuet_of_blooms_and_plumes_Name, 0, 100, false, false, false,
                statGetter79, LabelGetterBasic.integerLabel,
                new TooltipGetterInteger(minuet_of_blooms_and_plumes_Tooltip, statGetter79)
        );
        WorkbenchStatsGui.addBar(statBar79);
        HoloStatsGui.addBar(statBar79);
        var statGetter80 = new StatGetterEffectLevel(horrible_Effect, 1);
        GuiStatBar statBar80 = new GuiStatBar(0, 0, StatsHelper.barLength,
                horrible_Name, 0, 200, false, false, false,
                statGetter80, LabelGetterBasic.integerLabel,
                new TooltipGetterInteger(horrible_Tooltip, statGetter80)
        );
        WorkbenchStatsGui.addBar(statBar80);
        HoloStatsGui.addBar(statBar80);
        var statGetter81 = new StatGetterEffectLevel(ash_to_ash_Effect, 1);
        GuiStatBar statBar81 = new GuiStatBar(0, 0, StatsHelper.barLength,
                ash_to_ash_Name, 0, 100, false, false, false,
                statGetter81, LabelGetterBasic.integerLabel,
                new TooltipGetterInteger(ash_to_ash_Tooltip, statGetter81)
        );
        WorkbenchStatsGui.addBar(statBar81);
        HoloStatsGui.addBar(statBar81);
        var statGetter82 = new StatGetterEffectLevel(gekka_Effect, 1);
        GuiStatBar statBar82 = new GuiStatBar(0, 0, StatsHelper.barLength,
                gekka_Name, 0, 100, false, false, false,
                statGetter82, LabelGetterBasic.integerLabel,
                new TooltipGetterInteger(gekka_Tooltip, statGetter82)
        );
        WorkbenchStatsGui.addBar(statBar82);
        HoloStatsGui.addBar(statBar82);
        var statGetter83 = new StatGetterEffectLevel(atop_rainleaf_hangs_oneness_Effect, 1);
        GuiStatBar statBar83 = new GuiStatBar(0, 0, StatsHelper.barLength,
                atop_rainleaf_hangs_oneness_Name, 0, 100, false, false, false,
                statGetter83, LabelGetterBasic.integerLabel,
                new TooltipGetterInteger(atop_rainleaf_hangs_oneness_Tooltip, statGetter83)
        );
        WorkbenchStatsGui.addBar(statBar83);
        HoloStatsGui.addBar(statBar83);
        var statGetter84 = new StatGetterEffectLevel(thunderstrike_Effect, 1);
        GuiStatBar statBar84 = new GuiStatBar(0, 0, StatsHelper.barLength,
                thunderstrike_Name, 0, 100, false, false, false,
                statGetter84, LabelGetterBasic.integerLabel,
                new TooltipGetterInteger(thunderstrike_Tooltip, statGetter84)
        );
        WorkbenchStatsGui.addBar(statBar84);
        HoloStatsGui.addBar(statBar84);
        var statGetter85 = new StatGetterEffectLevel(act_not_with_impropriety_Effect, 1);
        GuiStatBar statBar85 = new GuiStatBar(0, 0, StatsHelper.barLength,
                act_not_with_impropriety_Name, 0, 100, false, false, false,
                statGetter85, LabelGetterBasic.integerLabel,
                new TooltipGetterInteger(act_not_with_impropriety_Tooltip, statGetter85)
        );
        WorkbenchStatsGui.addBar(statBar85);
        HoloStatsGui.addBar(statBar85);
    }
}
