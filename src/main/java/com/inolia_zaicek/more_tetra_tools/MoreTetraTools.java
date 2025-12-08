package com.inolia_zaicek.more_tetra_tools;

import com.inolia_zaicek.more_tetra_tools.Effect.*;
import com.inolia_zaicek.more_tetra_tools.Effect.BanaddurTheMagicGreatsword.DarkErosionEvent;
import com.inolia_zaicek.more_tetra_tools.Effect.BanaddurTheMagicGreatsword.DarkGreatsword;
import com.inolia_zaicek.more_tetra_tools.Effect.BehemothSword.BehemothSword;
import com.inolia_zaicek.more_tetra_tools.Effect.BehemothSword.StarSpawnOfCthulhu;
import com.inolia_zaicek.more_tetra_tools.Effect.Cataclysm.*;
import com.inolia_zaicek.more_tetra_tools.Effect.Clent.MTTEffectClent;
import com.inolia_zaicek.more_tetra_tools.Effect.Iron.AFMagicDamageUp;
import com.inolia_zaicek.more_tetra_tools.Effect.Iron.IronMagicDamageUp;
import com.inolia_zaicek.more_tetra_tools.Effect.Iron.TOMagicDamageUp;
import com.inolia_zaicek.more_tetra_tools.Effect.KaminariKatana.KamiOri;
import com.inolia_zaicek.more_tetra_tools.Effect.KaminariKatana.Kamui;
import com.inolia_zaicek.more_tetra_tools.Effect.Khaslana.*;
import com.inolia_zaicek.more_tetra_tools.Effect.ReturnToAshKatana.*;
import com.inolia_zaicek.more_tetra_tools.Effect.RipplesOfThePast.*;
import com.inolia_zaicek.more_tetra_tools.Effect.SetsugetsuKatana.Gekka;
import com.inolia_zaicek.more_tetra_tools.Effect.SetsugetsuKatana.Yukimau;
import com.inolia_zaicek.more_tetra_tools.Event.*;
import com.inolia_zaicek.more_tetra_tools.Modular.ModularTravellerStick;
import com.inolia_zaicek.more_tetra_tools.Register.MTTEffectsRegister;
import com.inolia_zaicek.more_tetra_tools.Register.MTTItemRegister;
import com.inolia_zaicek.more_tetra_tools.Register.MTTTab;
import com.inolia_zaicek.more_tetra_tools.entity.MTTModEntities;
import com.inolia_zaicek.more_tetra_tools.entity.MTTMusketArrowRenderer; // 确保这个导入是正确的
import com.inolia_zaicek.more_tetra_tools.network.MTTMusketChannel;
import com.mojang.logging.LogUtils;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import se.mickelus.tetra.TetraMod;
import se.mickelus.tetra.items.InitializableItem;
import se.mickelus.tetra.items.modular.impl.bow.ModularBowItem;
import se.mickelus.tetra.items.modular.impl.crossbow.ModularCrossbowItem;
import se.mickelus.tetra.items.modular.impl.shield.ModularShieldItem;
import se.mickelus.tetra.module.SchematicRegistry;
import se.mickelus.tetra.module.schematic.RepairSchematic;

import java.util.*;


@Mod(MoreTetraTools.MODID)
public class MoreTetraTools {

    public static final String MODID = "more_tetra_tools";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static List<InitializableItem> items=new ArrayList<>(List.of());
    public MoreTetraTools() {
        init();
    }

    public void init(){
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        // 注册 Item、Tab、Entity 类型
        MTTItemRegister.register(bus);
        MTTTab.register(bus);
        MTTEffectsRegister.INOEFFECT.register(bus);
        MTTModEntities.ENTITY_TYPES.register(bus); // 注册实体类型
        MinecraftForge.EVENT_BUS.register(Udumbara.class);
        MinecraftForge.EVENT_BUS.register(MTTEffectClent.class);
        MinecraftForge.EVENT_BUS.register(MagicDamageUp.class);
        MinecraftForge.EVENT_BUS.register(IncantationMedic.class);
        MinecraftForge.EVENT_BUS.register(LaPlumaScythe.class);
        MinecraftForge.EVENT_BUS.register(LaPlumaScythe2.class);
        MinecraftForge.EVENT_BUS.register(GrievousWoundsEvent.class);
        MinecraftForge.EVENT_BUS.register(Beheading.class);
        MinecraftForge.EVENT_BUS.register(GrievousWounds.class);
        MinecraftForge.EVENT_BUS.register(YingXiao.class);
        MinecraftForge.EVENT_BUS.register(DomainOfIncandescence.class);
        MinecraftForge.EVENT_BUS.register(FieryHymnSPledgeEvent.class);
        MinecraftForge.EVENT_BUS.register(BlazingSun.class);
        MinecraftForge.EVENT_BUS.register(BlemishineSword.class);
        MinecraftForge.EVENT_BUS.register(FateDivineVessel.class);
        MinecraftForge.EVENT_BUS.register(CreationBloodthornFerry.class);
        MinecraftForge.EVENT_BUS.register(CalamitySoulscorchEdict.class);
        MinecraftForge.EVENT_BUS.register(FlameKatana.class);
        MinecraftForge.EVENT_BUS.register(BlackWorldbearingGreatblade.class);
        MinecraftForge.EVENT_BUS.register(RedWolfDagger.class);
        MinecraftForge.EVENT_BUS.register(AstralSword.class);
        MinecraftForge.EVENT_BUS.register(CelestialGlobe.class);
        MinecraftForge.EVENT_BUS.register(PinusSylvestris.class);
        MinecraftForge.EVENT_BUS.register(IronRider.class);
        MinecraftForge.EVENT_BUS.register(Decapitator.class);
        MinecraftForge.EVENT_BUS.register(UnreviveEvent.class);
        MinecraftForge.EVENT_BUS.register(MortalBlade.class);
        MinecraftForge.EVENT_BUS.register(FreezeGreatblade.class);
        MinecraftForge.EVENT_BUS.register(FreezeGreatbladeDamageUP.class);
        MinecraftForge.EVENT_BUS.register(MosterHunter.class);
        MinecraftForge.EVENT_BUS.register(DeityHunter.class);
        MinecraftForge.EVENT_BUS.register(ModularTravellerStick.class);
        MinecraftForge.EVENT_BUS.register(TenkaAshina.class);
        MinecraftForge.EVENT_BUS.register(Laevatain.class);
        MinecraftForge.EVENT_BUS.register(ToolEffect.class);
        MinecraftForge.EVENT_BUS.register(SevenThundersOfRetributionNarukamiEvent.class);
        MinecraftForge.EVENT_BUS.register(SevenThundersOfRetributionNarukami.class);
        MinecraftForge.EVENT_BUS.register(DronesStaff.class);
        MinecraftForge.EVENT_BUS.register(BlowoutEvent.class);
        MinecraftForge.EVENT_BUS.register(DarkGreatsword.class);
        MinecraftForge.EVENT_BUS.register(DarkErosionEvent.class);
        MinecraftForge.EVENT_BUS.register(MindDeleteEvent.class);
        MinecraftForge.EVENT_BUS.register(MnemonosGrapheus.class);
        MinecraftForge.EVENT_BUS.register(ToughnessBreakEvent.class);
        MinecraftForge.EVENT_BUS.register(FireflySword.class);
        MinecraftForge.EVENT_BUS.register(OceanSVengence.class);
        //昔涟
        MinecraftForge.EVENT_BUS.register(AmphoreusSagaOfHeroes.class);
        MinecraftForge.EVENT_BUS.register(BloomElysiumOfBeyond.class);
        MinecraftForge.EVENT_BUS.register(MinuetOfBloomsAndPlumes.class);
        MinecraftForge.EVENT_BUS.register(ThisOdeToAllLives.class);
        MinecraftForge.EVENT_BUS.register(VerseZeroVowInfinite.class);
        //
        MinecraftForge.EVENT_BUS.register(Horrible.class);
        MinecraftForge.EVENT_BUS.register(DoomDay.class);
        MinecraftForge.EVENT_BUS.register(UniversePower.class);
        MinecraftForge.EVENT_BUS.register(AshToAsh.class);
        MinecraftForge.EVENT_BUS.register(AshToAshDamageUp.class);
        MinecraftForge.EVENT_BUS.register(DuskToDusk.class);
        MinecraftForge.EVENT_BUS.register(Yukimau.class);
        MinecraftForge.EVENT_BUS.register(Gekka.class);
        MinecraftForge.EVENT_BUS.register(SetsugetsuKaEvent.class);
        MinecraftForge.EVENT_BUS.register(Kamui.class);
        MinecraftForge.EVENT_BUS.register(KamiOri.class);
        MinecraftForge.EVENT_BUS.register(SentinelsBladeOfNaught.class);
        MinecraftForge.EVENT_BUS.register(ButterflyFlurry.class);
        MinecraftForge.EVENT_BUS.register(BloodSacrificeBlade.class);
        MinecraftForge.EVENT_BUS.register(DeathriteOfWaneEvent.class);
        MinecraftForge.EVENT_BUS.register(JiaQiuBattlestaff.class);
        MinecraftForge.EVENT_BUS.register(BlackAbyssFlower.class);
        MinecraftForge.EVENT_BUS.register(WhiteAbyssFlower.class);
        MinecraftForge.EVENT_BUS.register(UltimateDragonSword.class);
        MinecraftForge.EVENT_BUS.register(RadianceSword.class);
        MinecraftForge.EVENT_BUS.register(BehemothSword.class);
        MinecraftForge.EVENT_BUS.register(StarSpawnOfCthulhu.class);
        MinecraftForge.EVENT_BUS.register(MasterOfLight.class);
        //联动
        if (ModList.get().isLoaded("irons_spellbooks")) {
            MinecraftForge.EVENT_BUS.register(IronMagicDamageUp.class);
            if(ModList.get().isLoaded("traveloptics")) {
                MinecraftForge.EVENT_BUS.register(TOMagicDamageUp.class);
            }
            if(ModList.get().isLoaded("alshanex_familiars")) {
                MinecraftForge.EVENT_BUS.register(AFMagicDamageUp.class);
            }
            if(ModList.get().isLoaded("cataclysm")) {
                MinecraftForge.EVENT_BUS.register(Incinerator.class);
                MinecraftForge.EVENT_BUS.register(new Incinerator());///这是灾变写的
            }
        }
        // 注册 CommonSetup 事件
        bus.addListener(this::commonSetup);

        // !!! 注册 ClientSetup 事件 !!!
        bus.addListener(this::clientSetup);
    }

    @SubscribeEvent
    public void commonSetup(FMLCommonSetupEvent event){
        items.forEach(init->init.commonInit(TetraMod.packetHandler));
        items.clear();
        items=null;
        SchematicRegistry.instance.registerSchematic(new RepairSchematic(ModularShieldItem.instance, "modular_shield"));
        SchematicRegistry.instance.registerSchematic(new RepairSchematic(ModularBowItem.instance, "modular_bow"));
        SchematicRegistry.instance.registerSchematic(new RepairSchematic(ModularCrossbowItem.instance, "modular_crossbow"));
        event.enqueueWork(() -> {
            MTTMusketChannel.init(event);
        });
    }

    // 客户端设置事件，用于注册渲染器和GUI屏幕
    // 加上 @SubscribeEvent，使其成为 Mod 事件总线上的监听器
    @SubscribeEvent
    public void clientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            Incinerator.init();
            BehemothSword.init();
            UltimateDragonSword.init();
            MTTEffectClent.init();
            DronesStaff.init();
            ButterflyFlurry.init();
            BloodSacrificeBlade.init();
            SentinelsBladeOfNaught.init();
            DarkErosionEvent.init();
            Yukimau.init();
            DarkGreatsword.init();
            FireflySword.init();
            ToughnessBreakEvent.init();
            OceanSVengence.init();
            DoomDay.init();
            UniversePower.init();
            Kamui.init();
            KamiOri.init();
            BlackAbyssFlower.init();
            WhiteAbyssFlower.init();
            // 渲染器注册
            net.minecraft.client.renderer.entity.EntityRenderers.register(MTTModEntities.MY_MUSKET_ARROW.get(), MTTMusketArrowRenderer::new);
        });
    }

    // 注册掉落物
    // 如果你不需要数据生成器，可以暂时移除 @SubscribeEvent 和该方法
    // 如果需要，以下方式是正确的
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        var lookupProvider = event.getLookupProvider();
        if (event.includeServer()) {
            // 你的数据生成逻辑...
        }
    }

    public static ResourceLocation prefix(String name){
        return new ResourceLocation(MODID,name.toLowerCase(Locale.ROOT));
    }
    public static ResourceLocation getResource(String id) {
        return new ResourceLocation("more_tetra_tools", id);
    }
}