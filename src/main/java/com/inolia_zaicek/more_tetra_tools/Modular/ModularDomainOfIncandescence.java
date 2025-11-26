package com.inolia_zaicek.more_tetra_tools.Modular; // 定义该类所属的包，表示它是“More Mod Tetra”模组中“Modular Curios”部分的一部分。

import com.google.common.collect.Multimap;
import com.inolia_zaicek.more_tetra_tools.Damage.MTTTickZero;
import com.inolia_zaicek.more_tetra_tools.MoreTetraTools;
import com.inolia_zaicek.more_tetra_tools.Register.MTTEffectsRegister;
import com.inolia_zaicek.more_tetra_tools.Util.MTTUtil;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ObjectHolder;
import se.mickelus.mutil.network.PacketHandler;
import se.mickelus.tetra.data.DataManager;
import se.mickelus.tetra.gui.GuiModuleOffsets;
import se.mickelus.tetra.items.modular.IModularItem;
import se.mickelus.tetra.items.modular.ItemModularHandheld;
import se.mickelus.tetra.module.ItemModule;
import se.mickelus.tetra.module.ItemUpgradeRegistry;
import se.mickelus.tetra.module.SchematicRegistry;
import se.mickelus.tetra.module.schematic.RepairSchematic;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.inolia_zaicek.more_tetra_tools.Effect.Clent.MTTEffectGuiStats.*;

@SuppressWarnings({"all", "removal"})
public class ModularDomainOfIncandescence extends ItemModularHandheld { // 声明一个名为ModularNecklace的公共类，它继承自ModularItem并实现ICurio接口。
    //部件类型/槽位——[slot]
    public final static String modular_domain_of_incandescence_guard = "modular_domain_of_incandescence/guard";
    public final static String modular_domain_of_incandescence_flame = "modular_domain_of_incandescence/flame";
    public final static String modular_domain_of_incandescence_void = "modular_domain_of_incandescence/void";
    public final static String modular_domain_of_incandescence_sentience = "modular_domain_of_incandescence/sentience";
    public final static String modular_domain_of_incandescence_reason = "modular_domain_of_incandescence/reason";
    // --- 物品标识符 ---
    /*** 模块化项链在模组内的唯一标识符。* 这个标识符用于在NBT数据、配方和其他模组交互中引用该物品。*/
    public final static String identifier = "modular_domain_of_incandescence";
    // --- GUI 偏移量定义 ---// 这些常量定义了在GUI界面中，主模块和次要模块的显示位置偏移量。// 这使得Tetra的GUI系统能够正确地将模块放置在物品的UI表示上。
    /*** 定义了主模块（Major Modules）在GUI中的偏移量。* 负数表示向左或向上偏移。*/
    //4,0右下  18,-4右下   -4, 18瞎飘别管
    private static final GuiModuleOffsets majorOffsets = new GuiModuleOffsets(
            2, -4,
            12, 9,
            2, 23,
            -12, -3,
            -12, 22);
    //根据部件数量变化
    //-12,-1左上  -21,12左中  -12,25左下
    private static final GuiModuleOffsets minorOffsets = new GuiModuleOffsets(-12, 27,2, 23,
            -12, -3);
    // --- 物品实例 ---
    /*** 使用ObjectHolder注解，使得Forge在加载时能够自动获取已注册的“tetra:modular_domain_of_incandescence”物品实例。
     * 这允许其他模组或代码直接通过`ModularNecklace.instance`来访问这个物品，而无需手动注册和查找。
     * `registryName = "item"` 指定了要查找的注册表类型是物品。
     * `value = "tetra:modular_domain_of_incandescence"` 指定了该物品在物品注册表中的名称。
     * 注意：此字段在编译时和运行时可能存在依赖关系，确保“tetra:modular_domain_of_incandescence”已正确注册。*/
    //modular_domain_of_incandescence算是类似“单头工具”、“双头工具”的物品必备ID
    @ObjectHolder(
            registryName = "item",
            value = "tetra:modular_domain_of_incandescence"
    )
    public static ModularLaPlumaScythe instance;

    /*** ModularNecklace 类的构造函数。* 这里初始化了该模块化物品的基本属性。*/
    public ModularDomainOfIncandescence() {
        // 调用父类（ModularItem）的构造函数，并设置物品的基本属性：// new Item.Properties(): 创建物品属性对象。// .stacksTo(1): 设置该物品堆叠上限为1，表示项链是独立的、不可堆叠的物品。// .fireResistant(): 使该物品具有防火属性，在火焰中不会被烧毁。
        super(new Properties().stacksTo(1).fireResistant());
        //可否打磨
        canHone = true;
        //设置主要部件有什么
        majorModuleKeys = new String[]{modular_domain_of_incandescence_flame,modular_domain_of_incandescence_guard,modular_domain_of_incandescence_void,
                modular_domain_of_incandescence_sentience,modular_domain_of_incandescence_reason};
        //设置次要部件有什么
        minorModuleKeys = new String[]{};
        // 定义该项链所必需的模块（Required Modules）。游戏会确保这些模块至少存在一个，否则物品可能无法正常工作或显示。
        requiredModules = new String[]{modular_domain_of_incandescence_guard, modular_domain_of_incandescence_void, modular_domain_of_incandescence_flame,
                modular_domain_of_incandescence_sentience,modular_domain_of_incandescence_reason};
        //可修复
        SchematicRegistry.instance.registerSchematic(new RepairSchematic(this, identifier));
        //连携
        MoreTetraTools.items.add(this);
    }
    //连携
    @Override
    public void commonInit(PacketHandler packetHandler) {
        DataManager.instance.synergyData.onReload(() -> synergies =
                DataManager.instance.synergyData.getOrdered("modular_domain_of_incandescence/"));
    }
    /*** 获取该模块化物品所有已安装的模块。** @param stack 当前物品的ItemStack。* @return 包含所有已安装模块的Collection。*/
    //不用动他
    @Override // 覆盖父类ModularItem的方法。
    public Collection<ItemModule> getAllModules(ItemStack stack) {
        // 获取物品的NBT数据。NBT数据用于持久化存储物品的自定义信息，包括所安装的模块。
        CompoundTag stackTag = stack.getTag();
        // 检查NBT数据是否存在，如果不存在（例如物品刚创建时），则返回空列表。
        if (stackTag != null) {
            // 使用Stream API来处理模块。
            return Stream.concat( // 合并两个Stream：主要模块的Stream和次要模块的Stream。
                            Arrays.stream(getMajorModuleKeys(stack)), // 获取该物品定义的主要模块键。
                            Arrays.stream(getMinorModuleKeys(stack)) // 获取该物品定义的次要模块键。
                    )
                    // 从NBT数据中获取每个模块键对应的字符串值（模块的注册名）。
                    .map(stackTag::getString)
                    // 使用ItemUpgradeRegistry来查找对应的ItemModule对象。
                    // ItemUpgradeRegistry.instance::getModule 是一个方法引用，相当于 lambda: moduleKey -> ItemUpgradeRegistry.instance.getModule(moduleKey)。
                    .map(ItemUpgradeRegistry.instance::getModule)
                    // 过滤掉那些未能成功加载的模块（即registry.getModule返回null的情况）。
                    .filter(Objects::nonNull)
                    // 将过滤后的所有模块收集到一个List中。
                    .collect(Collectors.toList());
        }

        // 如果NBT数据为空，则返回一个空的不可变列表。
        return Collections.emptyList();
    }

    /**
     * 在客户端获取主模块在GUI中的偏移量。* OnlyIn(Dist.CLIENT) 注解表明这个方法只会在客户端运行。* @param itemStack 当前 ItemStack。* @return 主模块的GUI偏移量。
     */
    @OnlyIn(Dist.CLIENT) // 标记此方法仅在客户端环境执行。
    public GuiModuleOffsets getMajorGuiOffsets(ItemStack itemStack) {
        // 返回预先定义的majorOffsets，用于在GUI中定位主模块。
        return majorOffsets;
    }

    /*** 在客户端获取次要模块在GUI中的偏移量。* OnlyIn(Dist.CLIENT) 注解表明这个方法只会在客户端运行。** @param itemStack 当前 ItemStack。* @return 次要模块的GUI偏移量。*/
    @OnlyIn(Dist.CLIENT) // 标记此方法仅在客户端执行。
    public GuiModuleOffsets getMinorGuiOffsets(ItemStack itemStack) {
        // 返回预先定义的minorOffsets，用于在GUI中定位次要模块。
        return minorOffsets;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(ItemStack itemStack) {
        return super.getAttributeModifiers(itemStack);
    }


    //物品右键使用
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand pUsedHand) {
        if (!level.isClientSide() && pUsedHand == InteractionHand.MAIN_HAND) {
            double d0 = (double) (level.getRandom().nextFloat() - 0.5F) + player.getDeltaMovement().x;
            double d1 = (double) (level.getRandom().nextFloat() - 0.5F) + player.getDeltaMovement().y;
            double d2 = (double) (level.getRandom().nextFloat() - 0.5F) + player.getDeltaMovement().z;
            double dist = (double) (1.0F + level.getRandom().nextFloat() * 0.2F);
            double d3 = d0 * dist;
            double d4 = d1 * dist;
            double d5 = d2 * dist;
            player.level().addParticle(ParticleTypes.LAVA, player.getX(), player.getEyeY() - 0.1 + (player.getEyePosition().y - player.getEyeY()), player.getZ(), d3, d4, d5);
            //攻击
            if (player.getMainHandItem().getItem() instanceof IModularItem item
            &&!player.getCooldowns().isOnCooldown(player.getMainHandItem().getItem())) {
                //获取词条等级
                //剑
                float baseLevel = (float) item.getEffectLevel(player.getMainHandItem(), DomainOfIncandescenceEffect);
                //炎
                float flameLevel = (float) item.getEffectLevel(player.getMainHandItem(), theBladeSupremeRekindledEffect);
                //识
                float sentienceLevel = (float) item.getEffectLevel(player.getMainHandItem(), lonePhoenixSPlumeEffect);
                //空
                float voidLevel = (float) item.getEffectLevel(player.getMainHandItem(), fieryHymnSPledgeEffect);
                //理
                float reasonLevel = (float) item.getEffectLevel(player.getMainHandItem(), blazingChariotSTrailEffect);
                //理————右键开启状态
                if (reasonLevel > 0&&!player.hasEffect(MTTEffectsRegister.HerrscherOfFlamescion.get())) {
                    float hp =player.getHealth();
                    float finish=hp*0.15f;
                    float fhp=0;
                    //只有一滴血
                    if(hp-finish>0){
                        fhp=finish;
                    }
                    var DamageType = MTTTickZero.source(player.level(), MTTTickZero.TickFireDamage);
                    player.hurt(DamageType,fhp);
                    player.addEffect(new MobEffectInstance(MTTEffectsRegister.HerrscherOfFlamescion.get(), 230,0));
                    player.getCooldowns().addCooldown(this, 20 * 1);//设置冷却时间
                }
                //炎————终结
                else if(flameLevel>0&&player.hasEffect(MTTEffectsRegister.HerrscherOfFlamescion.get())){
                    //斩击
                    var mobList = MTTUtil.mobList(6, player);
                    float number = flameLevel / 100;
                    for (Mob mobs : mobList) {
                        if (mobs != null) {
                            //获取伤害类型
                            mobs.invulnerableTime = 0;
                            float atk = (float) player.getAttributeValue(Attributes.ATTACK_DAMAGE);
                            var DamageType = MTTTickZero.hasSource(player.level(), MTTTickZero.TickFireDamage,player);
                            if(mobs.hasEffect(MTTEffectsRegister.EmberBrand.get())) {
                                int buffLevel = mobs.getEffect(MTTEffectsRegister.EmberBrand.get()).getAmplifier()+1;
                                mobs.hurt(DamageType, atk * (number*buffLevel));
                                mobs.removeEffect(MTTEffectsRegister.EmberBrand.get());
                            }else{
                                mobs.hurt(DamageType, atk * number);
                            }
                            mobs.setLastHurtByPlayer(player);
                        }
                    }
                    //去除buff与冷却
                    player.removeEffect(MTTEffectsRegister.HerrscherOfFlamescion.get());
                    player.getCooldowns().addCooldown(this, 20 * 5);//设置冷却时间
                }
            }
        }
        return super.use(level, player, pUsedHand);
    }
}