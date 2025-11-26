package com.inolia_zaicek.more_tetra_tools.Effect.Clent;


import com.inolia_zaicek.more_tetra_tools.MoreTetraTools;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.registries.ForgeRegistries;
import se.mickelus.tetra.effect.ItemEffect;

import java.util.Objects;


public class MTTEffectGuiStats {

    public static final ItemEffect ironSpellCastingEffect = ItemEffect.get(MoreTetraTools.MODID + ":iron_spell_casting");
    public static final String ironSpellCastingName = MoreTetraTools.MODID + ".effect.iron_spell_casting.name";
    public static final String ironSpellCastingTooltip = MoreTetraTools.MODID + ".effect.iron_spell_casting.tooltip";

    public static final ItemEffect magicDamageUpEffect = ItemEffect.get(MoreTetraTools.MODID + ":magic_damage_up");
    public static final String magicDamageUpName = MoreTetraTools.MODID + ".effect.magic_damage_up.name";
    public static final String magicDamageUpTooltip = MoreTetraTools.MODID + ".effect.magic_damage_up.tooltip";

    public static final ItemEffect incantationMedicEffect = ItemEffect.get(MoreTetraTools.MODID + ":incantation_medic");
    public static final String incantationMedicName = MoreTetraTools.MODID + ".effect.incantation_medic.name";
    public static final String incantationMedicTooltip = MoreTetraTools.MODID + ".effect.incantation_medic.tooltip";

    public static final ItemEffect chainswordEffect = ItemEffect.get(MoreTetraTools.MODID + ":chainsword");
    public static final String chainswordName = MoreTetraTools.MODID + ".effect.chainsword.name";
    public static final String chainswordTooltip = MoreTetraTools.MODID + ".effect.chainsword.tooltip";

    public static final ItemEffect sawSeveringEffect = ItemEffect.get(MoreTetraTools.MODID + ":saw_severing");
    public static final String sawSeveringName = MoreTetraTools.MODID + ".effect.saw_severing.name";
    public static final String sawSeveringTooltip = MoreTetraTools.MODID + ".effect.saw_severing.tooltip";

    public static final ItemEffect reapEffect = ItemEffect.get(MoreTetraTools.MODID + ":reap");
    public static final String reapName = MoreTetraTools.MODID + ".effect.reap.name";
    public static final String reapTooltip = MoreTetraTools.MODID + ".effect.reap.tooltip";

    public static final ItemEffect beheadingEffect = ItemEffect.get(MoreTetraTools.MODID + ":beheading");
    public static final String beheadingName = MoreTetraTools.MODID + ".effect.beheading.name";
    public static final String beheadingTooltip = MoreTetraTools.MODID + ".effect.beheading.tooltip";

    public static final ItemEffect grievousWoundsEffect = ItemEffect.get(MoreTetraTools.MODID + ":grievous_wounds");
    public static final String grievousWoundsName = MoreTetraTools.MODID + ".effect.grievous_wounds.name";
    public static final String grievousWoundsTooltip = MoreTetraTools.MODID + ".effect.grievous_wounds.tooltip";

    public static final ItemEffect rapidSlashingEffect = ItemEffect.get(MoreTetraTools.MODID + ":rapid_slashing");
    public static final String rapidSlashingName = MoreTetraTools.MODID + ".effect.rapid_slashing.name";
    public static final String rapidSlashingTooltip = MoreTetraTools.MODID + ".effect.rapid_slashing.tooltip";

    public static final ItemEffect yingXiaoFleetingNightEffect = ItemEffect.get(MoreTetraTools.MODID + ":ying_xiao_fleeting_night");
    public static final String yingXiaoFleetingNightName = MoreTetraTools.MODID + ".effect.ying_xiao_fleeting_night.name";
    public static final String yingXiaoFleetingNightTooltip = MoreTetraTools.MODID + ".effect.ying_xiao_fleeting_night.tooltip";

    public static final ItemEffect yingXiaoShadowlessEffect = ItemEffect.get(MoreTetraTools.MODID + ":ying_xiao_shadowless");
    public static final String yingXiaoShadowlessName = MoreTetraTools.MODID + ".effect.ying_xiao_shadowless.name";
    public static final String yingXiaoShadowlessTooltip = MoreTetraTools.MODID + ".effect.ying_xiao_shadowless.tooltip";

    public static final ItemEffect DomainOfIncandescenceEffect = ItemEffect.get(MoreTetraTools.MODID + ":domain_of_incandescence");
    public static final String DomainOfIncandescenceName = MoreTetraTools.MODID + ".effect.domain_of_incandescence.name";
    public static final String DomainOfIncandescenceTooltip = MoreTetraTools.MODID + ".effect.domain_of_incandescence.tooltip";

    public static final ItemEffect blazingChariotSTrailEffect = ItemEffect.get(MoreTetraTools.MODID + ":blazing_chariot_s_trail");
    public static final String blazingChariotSTrailName = MoreTetraTools.MODID + ".effect.blazing_chariot_s_trail.name";
    public static final String blazingChariotSTrailTooltip = MoreTetraTools.MODID + ".effect.blazing_chariot_s_trail.tooltip";

    public static final ItemEffect lonePhoenixSPlumeEffect = ItemEffect.get(MoreTetraTools.MODID + ":lone_phoenix_s_plume");
    public static final String lonePhoenixSPlumeName = MoreTetraTools.MODID + ".effect.lone_phoenix_s_plume.name";
    public static final String lonePhoenixSPlumeTooltip = MoreTetraTools.MODID + ".effect.lone_phoenix_s_plume.tooltip";

    public static final ItemEffect fieryHymnSPledgeEffect = ItemEffect.get(MoreTetraTools.MODID + ":fiery_hymn_s_pledge");
    public static final String fieryHymnSPledgeName = MoreTetraTools.MODID + ".effect.fiery_hymn_s_pledge.name";
    public static final String fieryHymnSPledgeTooltip = MoreTetraTools.MODID + ".effect.fiery_hymn_s_pledge.tooltip";

    public static final ItemEffect theBladeSupremeRekindledEffect = ItemEffect.get(MoreTetraTools.MODID + ":the_blade_supreme_rekindled");
    public static final String theBladeSupremeRekindledName = MoreTetraTools.MODID + ".effect.the_blade_supreme_rekindled.name";
    public static final String theBladeSupremeRekindledTooltip = MoreTetraTools.MODID + ".effect.the_blade_supreme_rekindled.tooltip";

    public static final ItemEffect tidalElegyAttackEffect = ItemEffect.get(MoreTetraTools.MODID + ":tidal_elegy_attack");
    public static final String tidalElegyAttackName = MoreTetraTools.MODID + ".effect.tidal_elegy_attack.name";
    public static final String tidalElegyAttackTooltip = MoreTetraTools.MODID + ".effect.tidal_elegy_attack.tooltip";

    public static final ItemEffect tidalElegyArmorEffect = ItemEffect.get(MoreTetraTools.MODID + ":tidal_elegy_armor");
    public static final String tidalElegyArmorName = MoreTetraTools.MODID + ".effect.tidal_elegy_armor.name";
    public static final String tidalElegyArmorTooltip = MoreTetraTools.MODID + ".effect.tidal_elegy_armor.tooltip";

    public static final ItemEffect tidalElegyHealthEffect = ItemEffect.get(MoreTetraTools.MODID + ":tidal_elegy_health");
    public static final String tidalElegyHealthName = MoreTetraTools.MODID + ".effect.tidal_elegy_health.name";
    public static final String tidalElegyHealthTooltip = MoreTetraTools.MODID + ".effect.tidal_elegy_health.tooltip";

    public static final ItemEffect surgingBrillianceEffect = ItemEffect.get(MoreTetraTools.MODID + ":surging_brilliance");
    public static final String surgingBrillianceName = MoreTetraTools.MODID + ".effect.surging_brilliance.name";
    public static final String surgingBrillianceTooltip = MoreTetraTools.MODID + ".effect.surging_brilliance.tooltip";

    public static final ItemEffect divineAvatarEffect = ItemEffect.get(MoreTetraTools.MODID + ":divine_avatar");
    public static final String divineAvatarName = MoreTetraTools.MODID + ".effect.divine_avatar.name";
    public static final String divineAvatarTooltip = MoreTetraTools.MODID + ".effect.divine_avatar.tooltip";

    public static final ItemEffect blazingSunSObeisanceEffect = ItemEffect.get(MoreTetraTools.MODID + ":blazing_sun_s_obeisance");
    public static final String blazingSunSObeisanceName = MoreTetraTools.MODID + ".effect.blazing_sun_s_obeisance.name";
    public static final String blazingSunSObeisanceTooltip = MoreTetraTools.MODID + ".effect.blazing_sun_s_obeisance.tooltip";

    public static final ItemEffect letThereBeLightEffect = ItemEffect.get(MoreTetraTools.MODID + ":let_there_be_light");
    public static final String letThereBeLightName = MoreTetraTools.MODID + ".effect.let_there_be_light.name";
    public static final String letThereBeLightTooltip = MoreTetraTools.MODID + ".effect.let_there_be_light.tooltip";

    public static final ItemEffect butSufferingIsEssentialEffect = ItemEffect.get(MoreTetraTools.MODID + ":but_suffering_is_essential");
    public static final String butSufferingIsEssentialName = MoreTetraTools.MODID + ".effect.but_suffering_is_essential.name";
    public static final String butSufferingIsEssentialTooltip = MoreTetraTools.MODID + ".effect.but_suffering_is_essential.tooltip";

    public static final ItemEffect cryNotForTheDiscardedEffect = ItemEffect.get(MoreTetraTools.MODID + ":cry_not_for_the_discarded");
    public static final String cryNotForTheDiscardedName = MoreTetraTools.MODID + ".effect.cry_not_for_the_discarded.name";
    public static final String cryNotForTheDiscardedTooltip = MoreTetraTools.MODID + ".effect.cry_not_for_the_discarded.tooltip";

    public static final ItemEffect fateDivineVesselEffect = ItemEffect.get(MoreTetraTools.MODID + ":fate_divine_vessel");
    public static final String fateDivineVesselName = MoreTetraTools.MODID + ".effect.fate_divine_vessel.name";
    public static final String fateDivineVesselTooltip = MoreTetraTools.MODID + ".effect.fate_divine_vessel.tooltip";

    public static final ItemEffect creationBloodthornFerryEffect = ItemEffect.get(MoreTetraTools.MODID + ":creation_bloodthorn_ferry");
    public static final String creationBloodthornFerryName = MoreTetraTools.MODID + ".effect.creation_bloodthorn_ferry.name";
    public static final String creationBloodthornFerryTooltip = MoreTetraTools.MODID + ".effect.creation_bloodthorn_ferry.tooltip";

    public static final ItemEffect calamitySoulscorchEdictEffect = ItemEffect.get(MoreTetraTools.MODID + ":calamity_soulscorch_edict");
    public static final String calamitySoulscorchEdictName = MoreTetraTools.MODID + ".effect.calamity_soulscorch_edict.name";
    public static final String calamitySoulscorchEdictTooltip = MoreTetraTools.MODID + ".effect.calamity_soulscorch_edict.tooltip";

    public static final ItemEffect foundationStardeathVerdictEffect = ItemEffect.get(MoreTetraTools.MODID + ":foundation_stardeath_verdict");
    public static final String foundationStardeathVerdictName = MoreTetraTools.MODID + ".effect.foundation_stardeath_verdict.name";
    public static final String foundationStardeathVerdictTooltip = MoreTetraTools.MODID + ".effect.foundation_stardeath_verdict.tooltip";

    public static final ItemEffect avengerEffect = ItemEffect.get(MoreTetraTools.MODID + ":avenger");
    public static final String avengerName = MoreTetraTools.MODID + ".effect.avenger.name";
    public static final String avengerTooltip = MoreTetraTools.MODID + ".effect.avenger.tooltip";

    public static final ItemEffect fireAndSteelEffect = ItemEffect.get(MoreTetraTools.MODID + ":fire_and_steel");
    public static final String fireAndSteelName = MoreTetraTools.MODID + ".effect.fire_and_steel.name";
    public static final String fireAndSteelTooltip = MoreTetraTools.MODID + ".effect.fire_and_steel.tooltip";

    public static final ItemEffect executorEffect = ItemEffect.get(MoreTetraTools.MODID + ":executor");
    public static final String executorName = MoreTetraTools.MODID + ".effect.executor.name";
    public static final String executorTooltip = MoreTetraTools.MODID + ".effect.executor.tooltip";

    public static final ItemEffect ExecutionModeEffect = ItemEffect.get(MoreTetraTools.MODID + ":execution_mode");
    public static final String ExecutionModeName = MoreTetraTools.MODID + ".effect.execution_mode.name";
    public static final String ExecutionModeTooltip = MoreTetraTools.MODID + ".effect.execution_mode.tooltip";

    public static final ItemEffect celestialGlobeEffect = ItemEffect.get(MoreTetraTools.MODID + ":celestial_globe");
    public static final String celestialGlobeName = MoreTetraTools.MODID + ".effect.celestial_globe.name";
    public static final String celestialGlobeTooltip = MoreTetraTools.MODID + ".effect.celestial_globe.tooltip";

    public static final ItemEffect astralSwordEffect = ItemEffect.get(MoreTetraTools.MODID + ":astral_sword");
    public static final String astralSwordName = MoreTetraTools.MODID + ".effect.astral_sword.name";
    public static final String astralSwordTooltip = MoreTetraTools.MODID + ".effect.astral_sword.tooltip";

    public static final ItemEffect vanguardSwordsmanshipEffect = ItemEffect.get(MoreTetraTools.MODID + ":vanguard_swordsmanship");
    public static final String vanguardSwordsmanshipName = MoreTetraTools.MODID + ".effect.vanguard_swordsmanship.name";
    public static final String vanguardSwordsmanshipTooltip = MoreTetraTools.MODID + ".effect.vanguard_swordsmanship.tooltip";

    public static final ItemEffect flameheartEffect = ItemEffect.get(MoreTetraTools.MODID + ":flameheart");
    public static final String flameheartName = MoreTetraTools.MODID + ".effect.flameheart.name";
    public static final String flameheartTooltip = MoreTetraTools.MODID + ".effect.flameheart.tooltip";

    public static final ItemEffect copperSealEffect = ItemEffect.get(MoreTetraTools.MODID + ":copper_seal");
    public static final String copperSealName = MoreTetraTools.MODID + ".effect.copper_seal.name";
    public static final String copperSealTooltip = MoreTetraTools.MODID + ".effect.copper_seal.tooltip";

    public static final ItemEffect ironDefenseEffect = ItemEffect.get(MoreTetraTools.MODID + ":iron_defense");
    public static final String ironDefenseName = MoreTetraTools.MODID + ".effect.iron_defense.name";
    public static final String ironDefenseTooltip = MoreTetraTools.MODID + ".effect.iron_defense.tooltip";

    public static final ItemEffect waxingMoonEffect = ItemEffect.get(MoreTetraTools.MODID + ":waxing_moon");
    public static final String waxingMoonName = MoreTetraTools.MODID + ".effect.waxing_moon.name";
    public static final String waxingMoonTooltip = MoreTetraTools.MODID + ".effect.waxing_moon.tooltip";

    public static final ItemEffect halfMoonEffect = ItemEffect.get(MoreTetraTools.MODID + ":half_moon");
    public static final String halfMoonName = MoreTetraTools.MODID + ".effect.half_moon.name";
    public static final String halfMoonTooltip = MoreTetraTools.MODID + ".effect.half_moon.tooltip";

    public static final ItemEffect reviverKillerEffect = ItemEffect.get(MoreTetraTools.MODID + ":reviver_killer");
    public static final String reviverKillerName = MoreTetraTools.MODID + ".effect.reviver_killer.name";
    public static final String reviverKillerTooltip = MoreTetraTools.MODID + ".effect.reviver_killer.tooltip";

    public static final ItemEffect divineDragonPowerEffect = ItemEffect.get(MoreTetraTools.MODID + ":divine_dragon_power");
    public static final String divineDragonPowerName = MoreTetraTools.MODID + ".effect.divine_dragon_power.name";
    public static final String divineDragonPowerTooltip = MoreTetraTools.MODID + ".effect.divine_dragon_power.tooltip";

    public static final ItemEffect freezeBladeEffect = ItemEffect.get(MoreTetraTools.MODID + ":freeze_blade");
    public static final String freezeBladeName = MoreTetraTools.MODID + ".effect.freeze_blade.name";
    public static final String freezeBladeTooltip = MoreTetraTools.MODID + ".effect.freeze_blade.tooltip";

    public static final ItemEffect FreezeTimeEffect = ItemEffect.get(MoreTetraTools.MODID + ":freeze_time");
    public static final String FreezeTimeName = MoreTetraTools.MODID + ".effect.freeze_time.name";
    public static final String FreezeTimeTooltip = MoreTetraTools.MODID + ".effect.freeze_time.tooltip";

    public static final ItemEffect deityHunterEffect = ItemEffect.get(MoreTetraTools.MODID + ":deity_hunter");
    public static final String deityHunterName = MoreTetraTools.MODID + ".effect.deity_hunter.name";
    public static final String deityHunterTooltip = MoreTetraTools.MODID + ".effect.deity_hunter.tooltip";

    public static final ItemEffect mosterHunterEffect = ItemEffect.get(MoreTetraTools.MODID + ":moster_hunter");
    public static final String mosterHunterName = MoreTetraTools.MODID + ".effect.moster_hunter.name";
    public static final String mosterHunterTooltip = MoreTetraTools.MODID + ".effect.moster_hunter.tooltip";

    public static final ItemEffect teleportationEffect = ItemEffect.get(MoreTetraTools.MODID + ":teleportation");
    public static final String teleportationName = MoreTetraTools.MODID + ".effect.teleportation.name";
    public static final String teleportationTooltip = MoreTetraTools.MODID + ".effect.teleportation.tooltip";

    public static final ItemEffect waypointEffect = ItemEffect.get(MoreTetraTools.MODID + ":waypoint");
    public static final String waypointName = MoreTetraTools.MODID + ".effect.waypoint.name";
    public static final String waypointTooltip = MoreTetraTools.MODID + ".effect.waypoint.tooltip";

    public static final ItemEffect gohomeEffect = ItemEffect.get(MoreTetraTools.MODID + ":gohome");
    public static final String gohomeName = MoreTetraTools.MODID + ".effect.gohome.name";
    public static final String gohomeTooltip = MoreTetraTools.MODID + ".effect.gohome.tooltip";

    public static final ItemEffect recallEffect = ItemEffect.get(MoreTetraTools.MODID + ":recall");
    public static final String recallName = MoreTetraTools.MODID + ".effect.recall.name";
    public static final String recallTooltip = MoreTetraTools.MODID + ".effect.recall.tooltip";

    public static final ItemEffect captureEffect = ItemEffect.get(MoreTetraTools.MODID + ":capture");
    public static final String captureName = MoreTetraTools.MODID + ".effect.capture.name";
    public static final String captureTooltip = MoreTetraTools.MODID + ".effect.capture.tooltip";

    public static final ItemEffect tenka_ichiken_Effect = ItemEffect.get(MoreTetraTools.MODID + ":tenka_ichiken");
    public static final String tenka_ichiken_Name = MoreTetraTools.MODID + ".effect.tenka_ichiken.name";
    public static final String tenka_ichiken_Tooltip = MoreTetraTools.MODID + ".effect.tenka_ichiken.tooltip";

    public static final ItemEffect chokuhi_kyoshin_joseishu_Effect = ItemEffect.get(MoreTetraTools.MODID + ":chokuhi_kyoshin_joseishu");
    public static final String chokuhi_kyoshin_joseishu_Name = MoreTetraTools.MODID + ".effect.chokuhi_kyoshin_joseishu.name";
    public static final String chokuhi_kyoshin_joseishu_Tooltip = MoreTetraTools.MODID + ".effect.chokuhi_kyoshin_joseishu.tooltip";

    public static final ItemEffect remnant_ash_Effect = ItemEffect.get(MoreTetraTools.MODID + ":remnant_ash");
    public static final String remnant_ash_Name = MoreTetraTools.MODID + ".effect.remnant_ash.name";
    public static final String remnant_ash_Tooltip = MoreTetraTools.MODID + ".effect.remnant_ash.tooltip";

    public static final ItemEffect twilight_Effect = ItemEffect.get(MoreTetraTools.MODID + ":twilight");
    public static final String twilight_Name = MoreTetraTools.MODID + ".effect.twilight.name";
    public static final String twilight_Tooltip = MoreTetraTools.MODID + ".effect.twilight.tooltip";

    public static final ItemEffect crescent_transmigration_Effect = ItemEffect.get(MoreTetraTools.MODID + ":crescent_transmigration");
    public static final String crescent_transmigration_Name = MoreTetraTools.MODID + ".effect.crescent_transmigration.name";
    public static final String crescent_transmigration_Tooltip = MoreTetraTools.MODID + ".effect.crescent_transmigration.tooltip";

    public static final ItemEffect moon_on_glacial_river_Effect = ItemEffect.get(MoreTetraTools.MODID + ":moon_on_glacial_river");
    public static final String moon_on_glacial_river_Name = MoreTetraTools.MODID + ".effect.moon_on_glacial_river.name";
    public static final String moon_on_glacial_river_Tooltip = MoreTetraTools.MODID + ".effect.moon_on_glacial_river.tooltip";

    public static final ItemEffect tool_Effect = ItemEffect.get(MoreTetraTools.MODID + ":tool");
    public static final String tool_Name = MoreTetraTools.MODID + ".effect.tool.name";
    public static final String tool_Tooltip = MoreTetraTools.MODID + ".effect.tool.tooltip";

    public static final ItemEffect shamash_assault_Effect = ItemEffect.get(MoreTetraTools.MODID + ":shamash_assault");
    public static final String shamash_assault_Name = MoreTetraTools.MODID + ".effect.shamash_assault.name";
    public static final String shamash_assault_Tooltip = MoreTetraTools.MODID + ".effect.shamash_assault.tooltip";

    public static final ItemEffect forfeiture_Effect = ItemEffect.get(MoreTetraTools.MODID + ":forfeiture");
    public static final String forfeiture_Name = MoreTetraTools.MODID + ".effect.forfeiture.name";
    public static final String forfeiture_Tooltip = MoreTetraTools.MODID + ".effect.forfeiture.tooltip";

    public static final ItemEffect narukami_divinity_Effect = ItemEffect.get(MoreTetraTools.MODID + ":narukami_divinity");
    public static final String narukami_divinity_Name = MoreTetraTools.MODID + ".effect.narukami_divinity.name";
    public static final String narukami_divinity_Tooltip = MoreTetraTools.MODID + ".effect.narukami_divinity.tooltip";

    public static final ItemEffect ember_Effect = ItemEffect.get(MoreTetraTools.MODID + ":ember");
    public static final String ember_Name = MoreTetraTools.MODID + ".effect.ember.name";
    public static final String ember_Tooltip = MoreTetraTools.MODID + ".effect.ember.tooltip";

    public static final ItemEffect beacon_s_wrath_Effect = ItemEffect.get(MoreTetraTools.MODID + ":beacon_s_wrath");
    public static final String beacon_s_wrath_Name = MoreTetraTools.MODID + ".effect.beacon_s_wrath.name";
    public static final String beacon_s_wrath_Tooltip = MoreTetraTools.MODID + ".effect.beacon_s_wrath.tooltip";

    public static final ItemEffect scattering_sparks_Effect = ItemEffect.get(MoreTetraTools.MODID + ":scattering_sparks");
    public static final String scattering_sparks_Name = MoreTetraTools.MODID + ".effect.scattering_sparks.name";
    public static final String scattering_sparks_Tooltip = MoreTetraTools.MODID + ".effect.scattering_sparks.tooltip";

    public static final ItemEffect surging_current_Effect = ItemEffect.get(MoreTetraTools.MODID + ":surging_current");
    public static final String surging_current_Name = MoreTetraTools.MODID + ".effect.surging_current.name";
    public static final String surging_current_Tooltip = MoreTetraTools.MODID + ".effect.surging_current.tooltip";

    public static final ItemEffect crystalline_shine_Effect = ItemEffect.get(MoreTetraTools.MODID + ":crystalline_shine");
    public static final String crystalline_shine_Name = MoreTetraTools.MODID + ".effect.crystalline_shine.name";
    public static final String crystalline_shine_Tooltip = MoreTetraTools.MODID + ".effect.crystalline_shine.tooltip";

    public static final ItemEffect dark_greatsword_Effect = ItemEffect.get(MoreTetraTools.MODID + ":dark_greatsword");
    public static final String dark_greatsword_Name = MoreTetraTools.MODID + ".effect.dark_greatsword.name";
    public static final String dark_greatsword_Tooltip = MoreTetraTools.MODID + ".effect.dark_greatsword.tooltip";

    public static final ItemEffect dark_whispers_domination_Effect = ItemEffect.get(MoreTetraTools.MODID + ":dark_whispers_domination");
    public static final String dark_whispers_domination_Name = MoreTetraTools.MODID + ".effect.dark_whispers_domination.name";
    public static final String dark_whispers_domination_Tooltip = MoreTetraTools.MODID + ".effect.dark_whispers_domination.tooltip";

    public static final ItemEffect forget_Effect = ItemEffect.get(MoreTetraTools.MODID + ":forget");
    public static final String forget_Name = MoreTetraTools.MODID + ".effect.forget.name";
    public static final String forget_Tooltip = MoreTetraTools.MODID + ".effect.forget.tooltip";

    public static final ItemEffect disassemble_Effect = ItemEffect.get(MoreTetraTools.MODID + ":disassemble");
    public static final String disassemble_Name = MoreTetraTools.MODID + ".effect.disassemble.name";
    public static final String disassemble_Tooltip = MoreTetraTools.MODID + ".effect.disassemble.tooltip";

    public static final ItemEffect delete_Effect = ItemEffect.get(MoreTetraTools.MODID + ":delete");
    public static final String delete_Name = MoreTetraTools.MODID + ".effect.delete.name";
    public static final String delete_Tooltip = MoreTetraTools.MODID + ".effect.delete.tooltip";

    public static final ItemEffect weave_Effect = ItemEffect.get(MoreTetraTools.MODID + ":weave");
    public static final String weave_Name = MoreTetraTools.MODID + ".effect.weave.name";
    public static final String weave_Tooltip = MoreTetraTools.MODID + ".effect.weave.tooltip";

    public static final ItemEffect chrysalid_pyronexus_Effect = ItemEffect.get(MoreTetraTools.MODID + ":chrysalid_pyronexus");
    public static final String chrysalid_pyronexus_Name = MoreTetraTools.MODID + ".effect.chrysalid_pyronexus.name";
    public static final String chrysalid_pyronexus_Tooltip = MoreTetraTools.MODID + ".effect.chrysalid_pyronexus.tooltip";

    public static final ItemEffect fyrefly_type_iv_Effect = ItemEffect.get(MoreTetraTools.MODID + ":fyrefly_type_iv");
    public static final String fyrefly_type_iv_Name = MoreTetraTools.MODID + ".effect.fyrefly_type_iv.name";
    public static final String fyrefly_type_iv_Tooltip = MoreTetraTools.MODID + ".effect.fyrefly_type_iv.tooltip";

    public static final ItemEffect ocean_will_Effect = ItemEffect.get(MoreTetraTools.MODID + ":ocean_will");
    public static final String ocean_will_Name = MoreTetraTools.MODID + ".effect.ocean_will.name";
    public static final String ocean_will_Tooltip = MoreTetraTools.MODID + ".effect.ocean_will.tooltip";

    public static final ItemEffect weak_armor_Effect = ItemEffect.get(MoreTetraTools.MODID + ":weak_armor");
    public static final String weak_armor_Name = MoreTetraTools.MODID + ".effect.weak_armor.name";
    public static final String weak_armor_Tooltip = MoreTetraTools.MODID + ".effect.weak_armor.tooltip";

    public static final ItemEffect bloom_elysium_of_beyond_Effect = ItemEffect.get(MoreTetraTools.MODID + ":bloom_elysium_of_beyond");
    public static final String bloom_elysium_of_beyond_Name = MoreTetraTools.MODID + ".effect.bloom_elysium_of_beyond.name";
    public static final String bloom_elysium_of_beyond_Tooltip = MoreTetraTools.MODID + ".effect.bloom_elysium_of_beyond.tooltip";

    public static final ItemEffect minuet_of_blooms_and_plumes_Effect = ItemEffect.get(MoreTetraTools.MODID + ":minuet_of_blooms_and_plumes");
    public static final String minuet_of_blooms_and_plumes_Name = MoreTetraTools.MODID + ".effect.minuet_of_blooms_and_plumes.name";
    public static final String minuet_of_blooms_and_plumes_Tooltip = MoreTetraTools.MODID + ".effect.minuet_of_blooms_and_plumes.tooltip";

    public static final ItemEffect verse_zero_vow_infinite_Effect = ItemEffect.get(MoreTetraTools.MODID + ":verse_zero_vow_infinite");
    public static final String verse_zero_vow_infinite_Name = MoreTetraTools.MODID + ".effect.verse_zero_vow_infinite.name";
    public static final String verse_zero_vow_infinite_Tooltip = MoreTetraTools.MODID + ".effect.verse_zero_vow_infinite.tooltip";

    public static final ItemEffect this_ode_to_all_lives_Effect = ItemEffect.get(MoreTetraTools.MODID + ":this_ode_to_all_lives");
    public static final String this_ode_to_all_lives_Name = MoreTetraTools.MODID + ".effect.this_ode_to_all_lives.name";
    public static final String this_ode_to_all_lives_Tooltip = MoreTetraTools.MODID + ".effect.this_ode_to_all_lives.tooltip";

    public static final ItemEffect amphoreus_saga_of_heroes_Effect = ItemEffect.get(MoreTetraTools.MODID + ":amphoreus_saga_of_heroes");
    public static final String amphoreus_saga_of_heroes_Name = MoreTetraTools.MODID + ".effect.amphoreus_saga_of_heroes.name";
    public static final String amphoreus_saga_of_heroes_Tooltip = MoreTetraTools.MODID + ".effect.amphoreus_saga_of_heroes.tooltip";

    public static final ItemEffect horrible_Effect = ItemEffect.get(MoreTetraTools.MODID + ":horrible");
    public static final String horrible_Name = MoreTetraTools.MODID + ".effect.horrible.name";
    public static final String horrible_Tooltip = MoreTetraTools.MODID + ".effect.horrible.tooltip";

    public static final ItemEffect doom_day_Effect = ItemEffect.get(MoreTetraTools.MODID + ":doom_day");
    public static final String doom_day_Name = MoreTetraTools.MODID + ".effect.doom_day.name";
    public static final String doom_day_Tooltip = MoreTetraTools.MODID + ".effect.doom_day.tooltip";

    public static final ItemEffect universe_power_Effect = ItemEffect.get(MoreTetraTools.MODID + ":universe_power");
    public static final String universe_power_Name = MoreTetraTools.MODID + ".effect.universe_power.name";
    public static final String universe_power_Tooltip = MoreTetraTools.MODID + ".effect.universe_power.tooltip";

    public static final ItemEffect dusk_to_dusk_Effect = ItemEffect.get(MoreTetraTools.MODID + ":dusk_to_dusk");
    public static final String dusk_to_dusk_Name = MoreTetraTools.MODID + ".effect.dusk_to_dusk.name";
    public static final String dusk_to_dusk_Tooltip = MoreTetraTools.MODID + ".effect.dusk_to_dusk.tooltip";

    public static final ItemEffect ash_to_ash_Effect = ItemEffect.get(MoreTetraTools.MODID + ":ash_to_ash");
    public static final String ash_to_ash_Name = MoreTetraTools.MODID + ".effect.ash_to_ash.name";
    public static final String ash_to_ash_Tooltip = MoreTetraTools.MODID + ".effect.ash_to_ash.tooltip";

    public static final ItemEffect gekka_Effect = ItemEffect.get(MoreTetraTools.MODID + ":gekka");
    public static final String gekka_Name = MoreTetraTools.MODID + ".effect.gekka.name";
    public static final String gekka_Tooltip = MoreTetraTools.MODID + ".effect.gekka.tooltip";

    public static final ItemEffect yukimau_Effect = ItemEffect.get(MoreTetraTools.MODID + ":yukimau");
    public static final String yukimau_Name = MoreTetraTools.MODID + ".effect.yukimau.name";
    public static final String yukimau_Tooltip = MoreTetraTools.MODID + ".effect.yukimau.tooltip";

    public static final ItemEffect kami_ori_Effect = ItemEffect.get(MoreTetraTools.MODID + ":kami_ori");
    public static final String kami_ori_Name = MoreTetraTools.MODID + ".effect.kami_ori.name";
    public static final String kami_ori_Tooltip = MoreTetraTools.MODID + ".effect.kami_ori.tooltip";

    public static final ItemEffect kamui_Effect = ItemEffect.get(MoreTetraTools.MODID + ":kamui");
    public static final String kamui_Name = MoreTetraTools.MODID + ".effect.kamui.name";
    public static final String kamui_Tooltip = MoreTetraTools.MODID + ".effect.kamui.tooltip";

    public static final ItemEffect atop_rainleaf_hangs_oneness_Effect = ItemEffect.get(MoreTetraTools.MODID + ":atop_rainleaf_hangs_oneness");
    public static final String atop_rainleaf_hangs_oneness_Name = MoreTetraTools.MODID + ".effect.atop_rainleaf_hangs_oneness.name";
    public static final String atop_rainleaf_hangs_oneness_Tooltip = MoreTetraTools.MODID + ".effect.atop_rainleaf_hangs_oneness.tooltip";

    public static final ItemEffect slashed_dream_cries_in_red_Effect = ItemEffect.get(MoreTetraTools.MODID + ":slashed_dream_cries_in_red");
    public static final String slashed_dream_cries_in_red_Name = MoreTetraTools.MODID + ".effect.slashed_dream_cries_in_red.name";
    public static final String slashed_dream_cries_in_red_Tooltip = MoreTetraTools.MODID + ".effect.slashed_dream_cries_in_red.tooltip";

    public static final ItemEffect sheathed_blade_Effect = ItemEffect.get(MoreTetraTools.MODID + ":sheathed_blade");
    public static final String sheathed_blade_Name = MoreTetraTools.MODID + ".effect.sheathed_blade.name";
    public static final String sheathed_blade_Tooltip = MoreTetraTools.MODID + ".effect.sheathed_blade.tooltip";

    public static final ItemEffect resurgence_Effect = ItemEffect.get(MoreTetraTools.MODID + ":resurgence");
    public static final String resurgence_Name = MoreTetraTools.MODID + ".effect.resurgence.name";
    public static final String resurgence_Tooltip = MoreTetraTools.MODID + ".effect.resurgence.tooltip";

    public static final ItemEffect blood_devourer_Effect = ItemEffect.get(MoreTetraTools.MODID + ":blood_devourer");
    public static final String blood_devourer_Name = MoreTetraTools.MODID + ".effect.blood_devourer.name";
    public static final String blood_devourer_Tooltip = MoreTetraTools.MODID + ".effect.blood_devourer.tooltip";

    public static final ItemEffect blood_glare_Effect = ItemEffect.get(MoreTetraTools.MODID + ":blood_glare");
    public static final String blood_glare_Name = MoreTetraTools.MODID + ".effect.blood_glare.name";
    public static final String blood_glare_Tooltip = MoreTetraTools.MODID + ".effect.blood_glare.tooltip";

    public static final ItemEffect act_not_with_impropriety_Effect = ItemEffect.get(MoreTetraTools.MODID + ":act_not_with_impropriety");
    public static final String act_not_with_impropriety_Name = MoreTetraTools.MODID + ".effect.act_not_with_impropriety.name";
    public static final String act_not_with_impropriety_Tooltip = MoreTetraTools.MODID + ".effect.act_not_with_impropriety.tooltip";

    public static final ItemEffect thunderstrike_Effect = ItemEffect.get(MoreTetraTools.MODID + ":thunderstrike");
    public static final String thunderstrike_Name = MoreTetraTools.MODID + ".effect.thunderstrike.name";
    public static final String thunderstrike_Tooltip = MoreTetraTools.MODID + ".effect.thunderstrike.tooltip";

    public static final ItemEffect the_six_divine_key_of_decompose_Effect = ItemEffect.get(MoreTetraTools.MODID + ":the_six_divine_key_of_decompose");
    public static final String the_six_divine_key_of_decompose_Name = MoreTetraTools.MODID + ".effect.the_six_divine_key_of_decompose.name";
    public static final String the_six_divine_key_of_decompose_Tooltip = MoreTetraTools.MODID + ".effect.the_six_divine_key_of_decompose.tooltip";

    public static final ItemEffect the_six_divine_key_of_creation_Effect = ItemEffect.get(MoreTetraTools.MODID + ":the_six_divine_key_of_creation");
    public static final String the_six_divine_key_of_creation_Name = MoreTetraTools.MODID + ".effect.the_six_divine_key_of_creation.name";
    public static final String the_six_divine_key_of_creation_Tooltip = MoreTetraTools.MODID + ".effect.the_six_divine_key_of_creation.tooltip";
    //横扫
    public static final ItemEffect sweepingLevel = ItemEffect.get("sweeping");
    //速度
    public static final ItemEffect velocityLevel = ItemEffect.get("velocity");
    //冲击
    public static final ItemEffect punchLevel = ItemEffect.get("punch");
    //判断boss
    public static boolean isBossEntity(EntityType<?> entity) {
        // 检查 "more_tetra_tools:bosses" tag
        boolean isMoreTetraBoss = Objects.requireNonNull(ForgeRegistries.ENTITY_TYPES.tags()).getTag(
                TagKey.create(ForgeRegistries.ENTITY_TYPES.getRegistryKey(), new ResourceLocation("more_tetra_tools", "bosses"))
        ).contains(entity);
        // 检查 "forge:bosses" tag
        boolean isForgeBoss = Objects.requireNonNull(ForgeRegistries.ENTITY_TYPES.tags()).getTag(
                TagKey.create(ForgeRegistries.ENTITY_TYPES.getRegistryKey(), new ResourceLocation("forge", "bosses"))
        ).contains(entity);
        // 只要满足其中一个 tag 即可
        return isMoreTetraBoss || isForgeBoss;
    }
    public static boolean isUltimateBossEntity(EntityType<?> entity) {
        return Objects.requireNonNull(ForgeRegistries.ENTITY_TYPES.tags()).getTag(
                TagKey.create(ForgeRegistries.ENTITY_TYPES.getRegistryKey(), new ResourceLocation("more_tetra_tools", "ultimate_bosses"))
        ).contains(entity);
    }

    //用于计算百分比
    public static float getDecimalPercentage(float percentage, float base) {
        float totalPercentage = (base * (percentage / 100));

        return totalPercentage;
    }
    //将一个值直接加到基数上。
    public static float getExactPercentage(float base, float percentage) {
        float totalPercentage = base + percentage;

        return totalPercentage;
    }
}