package com.inolia_zaicek.more_tetra_tools.Modular;

import com.inolia_zaicek.more_tetra_tools.MoreTetraTools;
import se.mickelus.tetra.TetraMod;
import se.mickelus.tetra.items.modular.impl.bow.ModularBowItem;

import com.google.common.collect.Multimap;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ObjectHolder;
import se.mickelus.mutil.network.PacketHandler;
import se.mickelus.tetra.data.DataManager;
import se.mickelus.tetra.gui.GuiModuleOffsets;
import se.mickelus.tetra.module.SchematicRegistry;
import se.mickelus.tetra.module.schematic.RepairSchematic;

import static com.inolia_zaicek.more_tetra_tools.MoreTetraTools.items;

public class ModularRipplesOfThePast extends ModularBowItem {
    //部件类型/槽位——[slot]
    public final static String modular_ripples_of_the_past_stave = "modular_ripples_of_the_past/stave";
    public final static String modular_ripples_of_the_past_string = "modular_ripples_of_the_past/string";
    public final static String modular_ripples_of_the_past_zero = "modular_ripples_of_the_past/zero";
    public final static String modular_ripples_of_the_past_thirteen = "modular_ripples_of_the_past/thirteen";
    public final static String modular_ripples_of_the_past_infinite = "modular_ripples_of_the_past/infinite";
    public final static String identifier = "modular_ripples_of_the_past";
    private static final GuiModuleOffsets majorOffsets = new GuiModuleOffsets(2, -3, 2, 22);
    private static final GuiModuleOffsets minorOffsets = new GuiModuleOffsets(-12, 27, -22, 12, -12, -3);

    @ObjectHolder(registryName = "item", value = "tetra:modular_ripples_of_the_past")

    public static ModularRipplesOfThePast instance;
    public ModularRipplesOfThePast() {
        // 调用父类（ModularItem）的构造函数，并设置物品的基本属性：// new Item.Properties(): 创建物品属性对象。// .stacksTo(1): 设置该物品堆叠上限为1，表示项链是独立的、不可堆叠的物品。// .fireResistant(): 使该物品具有防火属性，在火焰中不会被烧毁。
        super();
        //可否打磨
        canHone = true;
        //设置主要部件有什么
        majorModuleKeys = new String[]{modular_ripples_of_the_past_stave,modular_ripples_of_the_past_string};
        //设置次要部件有什么
        minorModuleKeys = new String[]{modular_ripples_of_the_past_zero,modular_ripples_of_the_past_thirteen,modular_ripples_of_the_past_infinite};
        // 定义该项链所必需的模块（Required Modules）。游戏会确保这些模块至少存在一个，否则物品可能无法正常工作或显示。
        requiredModules = new String[]{modular_ripples_of_the_past_stave,modular_ripples_of_the_past_string};
        //可修复
        SchematicRegistry.instance.registerSchematic(new RepairSchematic(this, identifier));
        //连携
        items.add(this);
    }
    //连携
    @Override
    public void commonInit(PacketHandler packetHandler) {
        DataManager.instance.synergyData.onReload(() -> synergies = DataManager.instance.synergyData.getOrdered("modular_ripples_of_the_past/"));
    }
    @OnlyIn(Dist.CLIENT) // 标记此方法仅在客户端环境执行。
    public GuiModuleOffsets getMajorGuiOffsets(ItemStack itemStack) {
        return majorOffsets;
    }
    @OnlyIn(Dist.CLIENT) // 标记此方法仅在客户端执行。
    public GuiModuleOffsets getMinorGuiOffsets(ItemStack itemStack) {
        // 返回预先定义的minorOffsets，用于在GUI中定位次要模块。
        return minorOffsets;
    }
    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(ItemStack itemStack) {
        return super.getAttributeModifiers(itemStack);
    }
}
