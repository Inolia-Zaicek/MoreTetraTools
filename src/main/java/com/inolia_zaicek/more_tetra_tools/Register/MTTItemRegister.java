package com.inolia_zaicek.more_tetra_tools.Register;

import com.inolia_zaicek.more_tetra_tools.Modular.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import static com.inolia_zaicek.more_tetra_tools.MoreTetraTools.MODID;


public class MTTItemRegister {
    public static final DeferredRegister<Item> ZeroingITEM=DeferredRegister.create(Registries.ITEM,MODID);
    public static final DeferredRegister<Item> CataclysmITEM=DeferredRegister.create(Registries.ITEM,MODID);
    public static List<RegistryObject<Item>> CommonItem=new ArrayList<>(List.of());

    public static RegistryObject<Item> registerCommonMaterials(DeferredRegister<Item> register,String name, Supplier<? extends Item> sup){
        RegistryObject<Item> object = register.register(name,sup);
        CommonItem.add(object);
        return object;
    }
    public static final TagKey<Item> iron_spell_casting = TagKey.create(Registries.ITEM,new ResourceLocation("more_mod_tetra","iron_spell_casting"));

    public static final RegistryObject<Item> MODULAR_AmiyaStaff = ZeroingITEM.register(ModularAmiyaStaff.identifier, ModularAmiyaStaff::new);
    public static final RegistryObject<Item> TetraAmiyaStaff = registerCommonMaterials(ZeroingITEM,"amiya_staff", () -> new TooltipItem(new Item.Properties().stacksTo(1).fireResistant() ));
    public static final RegistryObject<Item> MODULAR_Trident = ZeroingITEM.register(ModularTrident.identifier, ModularTrident::new);
    public static final RegistryObject<Item> TetraTrident = registerCommonMaterials(ZeroingITEM,"tetra_trident", () -> new TooltipItem(new Item.Properties().stacksTo(1).fireResistant() ));
    public static final RegistryObject<Item> MODULAR_LaPlumaScythe = ZeroingITEM.register(ModularLaPlumaScythe.identifier, ModularLaPlumaScythe::new);
    public static final RegistryObject<Item> TetraLaPlumaScythe = registerCommonMaterials(ZeroingITEM,"la_pluma_scythe", () -> new TooltipItem(new Item.Properties().stacksTo(1).fireResistant() ));
    public static final RegistryObject<Item> MODULAR_Chainsword = ZeroingITEM.register(ModularChainsword.identifier, ModularChainsword::new);
    public static final RegistryObject<Item> TetraChainsword = registerCommonMaterials(ZeroingITEM,"chainsword", () -> new TooltipItem(new Item.Properties().stacksTo(1).fireResistant() ));
    public static final RegistryObject<Item> MODULAR_YingXiao = ZeroingITEM.register(ModularYingXiao.identifier, ModularYingXiao::new);
    public static final RegistryObject<Item> TetraYingXiao = registerCommonMaterials(ZeroingITEM,"ying_xiao", () -> new TooltipItem(new Item.Properties().stacksTo(1).fireResistant() ));
    public static final RegistryObject<Item> MODULAR_LowTide = ZeroingITEM.register(ModularLowTide.identifier, ModularLowTide::new);
    public static final RegistryObject<Item> TetraLowTide = registerCommonMaterials(ZeroingITEM,"low_tide", () -> new TooltipItem(new Item.Properties().stacksTo(1).fireResistant() ));
    public static final RegistryObject<Item> MODULAR_DomainOfIncandescence = ZeroingITEM.register(ModularDomainOfIncandescence.identifier, ModularDomainOfIncandescence::new);
    public static final RegistryObject<Item> TetraDomainOfIncandescence = registerCommonMaterials(ZeroingITEM,"domain_of_incandescence", () -> new TooltipItem(new Item.Properties().stacksTo(1).fireResistant() ));
    public static final RegistryObject<Item> MODULAR_TidalGreatblade = ZeroingITEM.register(ModularTidalGreatblade.identifier, ModularTidalGreatblade::new);
    public static final RegistryObject<Item> TetraTidalGreatblade = registerCommonMaterials(ZeroingITEM,"tidal_greatblade", () -> new TooltipItem(new Item.Properties().stacksTo(1).fireResistant() ));
    public static final RegistryObject<Item> MODULAR_BlemishineSword = ZeroingITEM.register(ModularBlemishineSword.identifier, ModularBlemishineSword::new);
    public static final RegistryObject<Item> TetraBlemishineSword = registerCommonMaterials(ZeroingITEM,"blemishine_sword", () -> new TooltipItem(new Item.Properties().stacksTo(1).fireResistant() ));
    public static final RegistryObject<Item> MODULAR_BlazingSun = ZeroingITEM.register(ModularBlazingSun.identifier, ModularBlazingSun::new);
    public static final RegistryObject<Item> TetraBlazingSun = registerCommonMaterials(ZeroingITEM,"blazing_sun", () -> new TooltipItem(new Item.Properties().stacksTo(1).fireResistant() ));
    public static final RegistryObject<Item> MODULAR_WhiteWorldbearingGreatblade = ZeroingITEM.register(ModularWhiteWorldbearingGreatblade.identifier, ModularWhiteWorldbearingGreatblade::new);
    public static final RegistryObject<Item> TetraWhiteWorldbearingGreatblade = registerCommonMaterials(ZeroingITEM,"white_worldbearing_greatblade", () -> new TooltipItem(new Item.Properties().stacksTo(1).fireResistant() ));
    public static final RegistryObject<Item> MODULAR_BlackWorldbearingGreatblade = ZeroingITEM.register(ModularBlackWorldbearingGreatblade.identifier, ModularBlackWorldbearingGreatblade::new);
    public static final RegistryObject<Item> TetraBlackWorldbearingGreatblade = registerCommonMaterials(ZeroingITEM,"black_worldbearing_greatblade", () -> new TooltipItem(new Item.Properties().stacksTo(1).fireResistant() ));
    public static final RegistryObject<Item> MODULAR_FinalWorldbearingGreatblade = ZeroingITEM.register(ModularFinalWorldbearingGreatblade.identifier, ModularFinalWorldbearingGreatblade::new);
    public static final RegistryObject<Item> TetraFinalWorldbearingGreatblade = registerCommonMaterials(ZeroingITEM,"final_worldbearing_greatblade", () -> new TooltipItem(new Item.Properties().stacksTo(1).fireResistant() ));
    public static final RegistryObject<Item> MODULAR_FlameKatana = ZeroingITEM.register(ModularFlameKatana.identifier, ModularFlameKatana::new);
    public static final RegistryObject<Item> TetraFlameKatana = registerCommonMaterials(ZeroingITEM,"flame_katana", () -> new TooltipItem(new Item.Properties().stacksTo(1).fireResistant() ));
    public static final RegistryObject<Item> MODULAR_RedWolfDagger = ZeroingITEM.register(ModularRedWolfDagger.identifier, ModularRedWolfDagger::new);
    public static final RegistryObject<Item> TetraRedWolfDagger = registerCommonMaterials(ZeroingITEM,"red_wolf_dagger", () -> new TooltipItem(new Item.Properties().stacksTo(1).fireResistant() ));
    public static final RegistryObject<Item> MODULAR_GuardianOfTheStars = ZeroingITEM.register(ModularGuardianOfTheStars.identifier, ModularGuardianOfTheStars::new);
    public static final RegistryObject<Item> TetraGuardianOfTheStars = registerCommonMaterials(ZeroingITEM,"guardian_of_the_stars", () -> new TooltipItem(new Item.Properties().stacksTo(1).fireResistant() ));
    public static final RegistryObject<Item> MODULAR_PinusSylvestris = ZeroingITEM.register(ModularPinusSylvestris.identifier, ModularPinusSylvestris::new);
    public static final RegistryObject<Item> TetraPinusSylvestris = registerCommonMaterials(ZeroingITEM,"pinus_sylvestris", () -> new TooltipItem(new Item.Properties().stacksTo(1).fireResistant() ));
    public static final RegistryObject<Item> MODULAR_IronRider = ZeroingITEM.register(ModularIronRider.identifier, ModularIronRider::new);
    public static final RegistryObject<Item> TetraIronRider = registerCommonMaterials(ZeroingITEM,"iron_rider", () -> new TooltipItem(new Item.Properties().stacksTo(1).fireResistant() ));
    public static final RegistryObject<Item> MODULAR_Decapitator = ZeroingITEM.register(ModularDecapitator.identifier, ModularDecapitator::new);
    public static final RegistryObject<Item> TetraDecapitator = registerCommonMaterials(ZeroingITEM,"decapitator", () -> new TooltipItem(new Item.Properties().stacksTo(1).fireResistant() ));
    public static final RegistryObject<Item> MODULAR_MortalBlade = ZeroingITEM.register(ModularMortalBlade.identifier, ModularMortalBlade::new);
    public static final RegistryObject<Item> TetraMortalBlade = registerCommonMaterials(ZeroingITEM,"mortal_blade", () -> new TooltipItem(new Item.Properties().stacksTo(1).fireResistant() ));
    public static final RegistryObject<Item> MODULAR_FreezeGreatblade = ZeroingITEM.register(ModularFreezeGreatblade.identifier, ModularFreezeGreatblade::new);
    public static final RegistryObject<Item> TetraFreezeGreatblade = registerCommonMaterials(ZeroingITEM,"freeze_greatblade", () -> new TooltipItem(new Item.Properties().stacksTo(1).fireResistant() ));
    public static final RegistryObject<Item> MODULAR_DeiciderKatana = ZeroingITEM.register(ModularDeiciderKatana.identifier, ModularDeiciderKatana::new);
    public static final RegistryObject<Item> TetraDeiciderKatana = registerCommonMaterials(ZeroingITEM,"deicider_katana", () -> new TooltipItem(new Item.Properties().stacksTo(1).fireResistant() ));
    public static final RegistryObject<Item> MODULAR_TenkaAshina = ZeroingITEM.register(ModularTenkaAshina.identifier, ModularTenkaAshina::new);
    public static final RegistryObject<Item> TetraTenkaAshina = registerCommonMaterials(ZeroingITEM,"tenka_ashina", () -> new TooltipItem(new Item.Properties().stacksTo(1).fireResistant() ));
    public static final RegistryObject<Item> MODULAR_Laevatain = ZeroingITEM.register(ModularLaevatain.identifier, ModularLaevatain::new);
    public static final RegistryObject<Item> TetraLaevatain = registerCommonMaterials(ZeroingITEM,"laevatain", () -> new TooltipItem(new Item.Properties().stacksTo(1).fireResistant() ));
    public static final RegistryObject<Item> MODULAR_Udumbara = ZeroingITEM.register(ModularUdumbara.identifier, ModularUdumbara::new);
    public static final RegistryObject<Item> TetraUdumbara = registerCommonMaterials(ZeroingITEM,"udumbara", () -> new TooltipItem(new Item.Properties().stacksTo(1).fireResistant() ));
    public static final RegistryObject<Item> MODULAR_JudgmentOfShamash = ZeroingITEM.register(ModularJudgmentOfShamash.identifier, ModularJudgmentOfShamash::new);
    public static final RegistryObject<Item> TetraJudgmentOfShamash = registerCommonMaterials(ZeroingITEM,"judgment_of_shamash", () -> new TooltipItem(new Item.Properties().stacksTo(1).fireResistant() ));
    public static final RegistryObject<Item> MODULAR_SevenThundersOfRetributionNarukami = ZeroingITEM.register(ModularSevenThundersOfRetributionNarukami.identifier, ModularSevenThundersOfRetributionNarukami::new);
    public static final RegistryObject<Item> TetraSevenThundersOfRetributionNarukami = registerCommonMaterials(ZeroingITEM,"seven_thunders_of_retribution_narukami", () -> new TooltipItem(new Item.Properties().stacksTo(1).fireResistant() ));
    public static final RegistryObject<Item> MODULAR_DronesStaff = ZeroingITEM.register(ModularDronesStaff.identifier, ModularDronesStaff::new);
    public static final RegistryObject<Item> TetraDronesStaff = registerCommonMaterials(ZeroingITEM,"drones_staff", () -> new TooltipItem(new Item.Properties().stacksTo(1).fireResistant() ));
    public static final RegistryObject<Item> MODULAR_BanaddurTheMagicGreatsword = ZeroingITEM.register(ModularBanaddurTheMagicGreatsword.identifier, ModularBanaddurTheMagicGreatsword::new);
    public static final RegistryObject<Item> TetraBanaddurTheMagicGreatsword = registerCommonMaterials(ZeroingITEM,"banaddur_the_magic_greatsword", () -> new TooltipItem(new Item.Properties().stacksTo(1).fireResistant() ));
    public static final RegistryObject<Item> MODULAR_MnemonosGrapheus = ZeroingITEM.register(ModularMnemonosGrapheus.identifier, ModularMnemonosGrapheus::new);
    public static final RegistryObject<Item> TetraMnemonosGrapheus = registerCommonMaterials(ZeroingITEM,"mnemonos_grapheus", () -> new TooltipItem(new Item.Properties().stacksTo(1).fireResistant() ));
    public static final RegistryObject<Item> MODULAR_LysisMython = ZeroingITEM.register(ModularLysisMython.identifier, ModularLysisMython::new);
    public static final RegistryObject<Item> TetraLysisMython = registerCommonMaterials(ZeroingITEM,"lysis_mython", () -> new TooltipItem(new Item.Properties().stacksTo(1).fireResistant() ));
    public static final RegistryObject<Item> MODULAR_FireflySword = ZeroingITEM.register(ModularFireflySword.identifier, ModularFireflySword::new);
    public static final RegistryObject<Item> TetraFireflySword = registerCommonMaterials(ZeroingITEM,"firefly_sword", () -> new TooltipItem(new Item.Properties().stacksTo(1).fireResistant() ));
    public static final RegistryObject<Item> MODULAR_OceanSVengence = ZeroingITEM.register(ModularOceanSVengence.identifier, ModularOceanSVengence::new);
    public static final RegistryObject<Item> TetraOceanSVengence = registerCommonMaterials(ZeroingITEM,"ocean_s_vengence", () -> new TooltipItem(new Item.Properties().stacksTo(1).fireResistant() ));
    public static final RegistryObject<Item> MODULAR_RipplesOfThePast = ZeroingITEM.register(ModularRipplesOfThePast.identifier, ModularRipplesOfThePast::new);
    public static final RegistryObject<Item> TetraRipplesOfThePast = registerCommonMaterials(ZeroingITEM,"ripples_of_the_past", () -> new TooltipItem(new Item.Properties().stacksTo(1).fireResistant() ));
    public static final RegistryObject<Item> MODULAR_CoralSorrow = ZeroingITEM.register(ModularCoralSorrow.identifier, ModularCoralSorrow::new);
    public static final RegistryObject<Item> TetraCoralSorrow = registerCommonMaterials(ZeroingITEM,"coral_sorrow", () -> new TooltipItem(new Item.Properties().stacksTo(1).fireResistant() ));
    public static final RegistryObject<Item> MODULAR_ApocalypseGreatblade = ZeroingITEM.register(ModularApocalypseGreatblade.identifier, ModularApocalypseGreatblade::new);
    public static final RegistryObject<Item> TetraApocalypseGreatblade = registerCommonMaterials(ZeroingITEM,"apocalypse_greatblade", () -> new TooltipItem(new Item.Properties().stacksTo(1).fireResistant() ));
    public static final RegistryObject<Item> MODULAR_ReturnToAshKatana = ZeroingITEM.register(ModularReturnToAshKatana.identifier, ModularReturnToAshKatana::new);
    public static final RegistryObject<Item> TetraReturnToAshKatana = registerCommonMaterials(ZeroingITEM,"return_to_ash_katana", () -> new TooltipItem(new Item.Properties().stacksTo(1).fireResistant() ));
    public static final RegistryObject<Item> MODULAR_SetsugetsuKatana = ZeroingITEM.register(ModularSetsugetsuKatana.identifier, ModularSetsugetsuKatana::new);
    public static final RegistryObject<Item> TetraSetsugetsuKatana = registerCommonMaterials(ZeroingITEM,"setsugetsu_katana", () -> new TooltipItem(new Item.Properties().stacksTo(1).fireResistant() ));
    public static final RegistryObject<Item> MODULAR_KaminariKatana = ZeroingITEM.register(ModularKaminariKatana.identifier, ModularKaminariKatana::new);
    public static final RegistryObject<Item> TetraKaminariKatana = registerCommonMaterials(ZeroingITEM,"kaminari_katana", () -> new TooltipItem(new Item.Properties().stacksTo(1).fireResistant() ));
    public static final RegistryObject<Item> MODULAR_SentinelsBladeOfNaught = ZeroingITEM.register(ModularSentinelsBladeOfNaught.identifier, ModularSentinelsBladeOfNaught::new);
    public static final RegistryObject<Item> TetraSentinelsBladeOfNaught = registerCommonMaterials(ZeroingITEM,"sentinels_blade_of_naught", () -> new TooltipItem(new Item.Properties().stacksTo(1).fireResistant() ));
    public static final RegistryObject<Item> MODULAR_ButterflyFlurry = ZeroingITEM.register(ModularButterflyFlurry.identifier, ModularButterflyFlurry::new);
    public static final RegistryObject<Item> TetraButterflyFlurry = registerCommonMaterials(ZeroingITEM,"butterfly_flurry", () -> new TooltipItem(new Item.Properties().stacksTo(1).fireResistant() ));
    public static final RegistryObject<Item> MODULAR_BloodSacrificeBlade = ZeroingITEM.register(ModularBloodSacrificeBlade.identifier, ModularBloodSacrificeBlade::new);
    public static final RegistryObject<Item> TetraBloodSacrificeBlade = registerCommonMaterials(ZeroingITEM,"blood_sacrifice_blade", () -> new TooltipItem(new Item.Properties().stacksTo(1).fireResistant() ));
    public static final RegistryObject<Item> MODULAR_JiaQiuBattlestaff = ZeroingITEM.register(ModularJiaQiuBattlestaff.identifier, ModularJiaQiuBattlestaff::new);
    public static final RegistryObject<Item> TetraJiaQiuBattlestaff = registerCommonMaterials(ZeroingITEM,"jia_qiu_battlestaff", () -> new TooltipItem(new Item.Properties().stacksTo(1).fireResistant() ));
    public static final RegistryObject<Item> MODULAR_BlackAbyssFlower = ZeroingITEM.register(ModularBlackAbyssFlower.identifier, ModularBlackAbyssFlower::new);
    public static final RegistryObject<Item> TetraBlackAbyssFlower = registerCommonMaterials(ZeroingITEM,"black_abyss_flower", () -> new TooltipItem(new Item.Properties().stacksTo(1).fireResistant() ));
    public static final RegistryObject<Item> MODULAR_WhiteAbyssFlower = ZeroingITEM.register(ModularWhiteAbyssFlower.identifier, ModularWhiteAbyssFlower::new);
    public static final RegistryObject<Item> TetraWhiteAbyssFlower = registerCommonMaterials(ZeroingITEM,"white_abyss_flower", () -> new TooltipItem(new Item.Properties().stacksTo(1).fireResistant() ));

    public static final RegistryObject<Item> MODULAR_TravellerStick = ZeroingITEM.register(ModularTravellerStick.identifier, ModularTravellerStick::new);
    public static final RegistryObject<Item> TetraTravellerStick = registerCommonMaterials(ZeroingITEM,"traveller_stick", () -> new TooltipItem(new Item.Properties().stacksTo(1).fireResistant() ));
    public static final RegistryObject<Item> MODULAR_Paxel = ZeroingITEM.register(ModularPaxel.identifier, ModularPaxel::new);
    public static final RegistryObject<Item> TetraPaxel = registerCommonMaterials(ZeroingITEM,"paxel", () -> new TooltipItem(new Item.Properties().stacksTo(1).fireResistant() ));
    //原型
    public static final RegistryObject<Item> PrototypeWeapon = registerCommonMaterials(ZeroingITEM,"prototype_weapon", () -> new Item(new Item.Properties().stacksTo(64).fireResistant() ));
    public static final RegistryObject<Item> DragonTear = registerCommonMaterials(ZeroingITEM,"dragon_tear", () -> new TooltipItem(new Item.Properties().stacksTo(64).fireResistant() ));
    //引擎
    public static final RegistryObject<Item> RedstoneEngine = registerCommonMaterials(ZeroingITEM,"redstone_engine", () -> new Item(new Item.Properties().stacksTo(64).fireResistant() ));
    public static final RegistryObject<Item> ThermalEngine = registerCommonMaterials(ZeroingITEM,"thermal_engine", () -> new Item(new Item.Properties().stacksTo(64).fireResistant() ));
    public static final RegistryObject<Item> BlazeEngine = registerCommonMaterials(ZeroingITEM,"blaze_engine", () -> new Item(new Item.Properties().stacksTo(64).fireResistant() ));
    public static final RegistryObject<Item> DragonBreathEngine = registerCommonMaterials(ZeroingITEM,"dragon_breath_engine", () -> new Item(new Item.Properties().stacksTo(64).fireResistant() ));
    public static final RegistryObject<Item> WitheriteEngine = registerCommonMaterials(CataclysmITEM,"witherite_engine", () -> new Item(new Item.Properties().stacksTo(64).fireResistant() ));
    public MTTItemRegister(){
    }

    public static void register(IEventBus bus){
        ZeroingITEM.register(bus);
        if (ModList.get().isLoaded("cataclysm")) {
            CataclysmITEM.register(bus);
        }
    }
}
