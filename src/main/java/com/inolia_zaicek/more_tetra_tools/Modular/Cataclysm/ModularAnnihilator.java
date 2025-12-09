package com.inolia_zaicek.more_tetra_tools.Modular.Cataclysm; // 定义该类所属的包，表示它是“More Mod Tetra”模组中“Modular Curios”部分的一部分。

import com.github.L_Ender.cataclysm.client.particle.RingParticle;
import com.github.L_Ender.cataclysm.entity.effect.ScreenShake_Entity;
import com.github.L_Ender.cataclysm.init.ModItems;
import com.github.L_Ender.cataclysm.init.ModParticle;
import com.github.L_Ender.cataclysm.init.ModSounds;
import com.google.common.collect.Multimap;
import com.inolia_zaicek.more_tetra_tools.Modular.ModularLaPlumaScythe;
import com.inolia_zaicek.more_tetra_tools.MoreTetraTools;
import com.inolia_zaicek.more_tetra_tools.Register.MTTItemRegister;
import com.inolia_zaicek.more_tetra_tools.Util.MTTEffectHelper;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ObjectHolder;
import se.mickelus.mutil.network.PacketHandler;
import se.mickelus.tetra.data.DataManager;
import se.mickelus.tetra.gui.GuiModuleOffsets;
import se.mickelus.tetra.items.modular.ItemModularHandheld;
import se.mickelus.tetra.module.ItemModule;
import se.mickelus.tetra.module.ItemUpgradeRegistry;
import se.mickelus.tetra.module.SchematicRegistry;
import se.mickelus.tetra.module.schematic.RepairSchematic;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.inolia_zaicek.more_tetra_tools.Effect.Clent.MTTEffectGuiStats.*;

@SuppressWarnings({"all", "removal"})
public class ModularAnnihilator extends ItemModularHandheld { // 声明一个名为ModularNecklace的公共类，它继承自ModularItem并实现ICurio接口。
    //部件类型/槽位——[slot]
    public final static String modular_annihilator_left_hammer = "modular_annihilator/left_hammer";
    public final static String modular_annihilator_handle = "modular_annihilator/handle";
    public final static String modular_annihilator_right_hammer = "modular_annihilator/right_hammer";
    public final static String modular_annihilator_binding = "modular_annihilator/binding";
    public final static String modular_annihilator_socket = "modular_annihilator/socket";
    // --- 物品标识符 ---
    /*** 模块化项链在模组内的唯一标识符。* 这个标识符用于在NBT数据、配方和其他模组交互中引用该物品。*/
    public final static String identifier = "modular_annihilator";
    // --- GUI 偏移量定义 ---// 这些常量定义了在GUI界面中，主模块和次要模块的显示位置偏移量。// 这使得Tetra的GUI系统能够正确地将模块放置在物品的UI表示上。
    /*** 定义了主模块（Major Modules）在GUI中的偏移量。* 负数表示向左或向上偏移。*/
    //4,0右下  18,-4右下   -4, 18瞎飘别管
    private static final GuiModuleOffsets majorOffsets = new GuiModuleOffsets(
            2, -3,
            2, 22,
            -12, -3,
            12, 9,
            2, 23,
            -12, -3);
    //根据部件数量变化
    //-12,-1左上  -21,12左中  -12,25左下
    private static final GuiModuleOffsets minorOffsets = new GuiModuleOffsets(
            -12, 27,
            -22, 12,
            -12, -3);
    // --- 物品实例 ---
    /*** 使用ObjectHolder注解，使得Forge在加载时能够自动获取已注册的“tetra:modular_annihilator”物品实例。
     * 这允许其他模组或代码直接通过`ModularNecklace.instance`来访问这个物品，而无需手动注册和查找。
     * `registryName = "item"` 指定了要查找的注册表类型是物品。
     * `value = "tetra:modular_annihilator"` 指定了该物品在物品注册表中的名称。
     * 注意：此字段在编译时和运行时可能存在依赖关系，确保“tetra:modular_annihilator”已正确注册。*/
    //modular_annihilator算是类似“单头工具”、“双头工具”的物品必备ID
    @ObjectHolder(
            registryName = "item",
            value = "tetra:modular_annihilator"
    )
    public static ModularLaPlumaScythe instance;

    /*** ModularNecklace 类的构造函数。* 这里初始化了该模块化物品的基本属性。*/
    public ModularAnnihilator() {
        // 调用父类（ModularItem）的构造函数，并设置物品的基本属性：// new Item.Properties(): 创建物品属性对象。// .stacksTo(1): 设置该物品堆叠上限为1，表示项链是独立的、不可堆叠的物品。// .fireResistant(): 使该物品具有防火属性，在火焰中不会被烧毁。
        super(new Properties().stacksTo(1).fireResistant());
        //可否打磨
        canHone = true;
        //设置主要部件有什么
        majorModuleKeys = new String[]{modular_annihilator_left_hammer,  modular_annihilator_handle,modular_annihilator_right_hammer};
        //设置次要部件有什么
        minorModuleKeys = new String[]{modular_annihilator_socket,modular_annihilator_binding};
        // 定义该项链所必需的模块（Required Modules）。游戏会确保这些模块至少存在一个，否则物品可能无法正常工作或显示。
        requiredModules = new String[]{modular_annihilator_left_hammer, modular_annihilator_right_hammer, modular_annihilator_handle};
        //可修复
        SchematicRegistry.instance.registerSchematic(new RepairSchematic(this, identifier));
        //连携
        MoreTetraTools.items.add(this);
    }
    //连携
    @Override
    public void commonInit(PacketHandler packetHandler) {
        DataManager.instance.synergyData.onReload(() -> synergies =
                DataManager.instance.synergyData.getOrdered("modular_annihilator/"));
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
    /// 锤（暴击伤害增幅不在这里
    public UseAnim getUseAnimation(ItemStack p_77661_1_) {
        return UseAnim.SPEAR;
    }

    public int getUseDuration(ItemStack p_77626_1_) {
        return 72000;
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        ItemStack otherHand = hand == InteractionHand.MAIN_HAND ? player.getItemInHand(InteractionHand.OFF_HAND) : player.getItemInHand(InteractionHand.MAIN_HAND);
        //副手是mtt的锤子
        if (otherHand.is((Item) MTTItemRegister.MODULAR_Annihilator.get())) {
            player.startUsingItem(hand);
            return InteractionResultHolder.consume(itemstack);
        } else {
            return InteractionResultHolder.fail(itemstack);
        }
    }

    public void releaseUsing(ItemStack stack, Level level, LivingEntity livingEntity, int time) {
        //蓄力缩减（30%：1-30%=0.7
        float effectEfficiency = 1-(MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectEfficiency(livingEntity, soul_annihilator_Effect))/100;
        // 释放使用（攻击或蓄力）后触发的方法
        if (livingEntity instanceof Player player) {
            int i = this.getUseDuration(stack) - time; // 计算蓄力时间（已经按最大值减去剩余，得到实际充能时间）
            if (i >= Math.max(4,(int)(40*effectEfficiency) ) ) { // 蓄力达到40或以上（对应一定时间，确保蓄力充分）
                this.yall(level, livingEntity); // 触发范围伤害和特效（范围爆发伤害）
                if (!level.isClientSide) { // 服务器端
                    int cooldown = Math.max( 1,(int) ((int) 20* MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectEfficiency(livingEntity, cursium_annihilator_Effect)) );
                    player.getCooldowns().addCooldown(this, cooldown); // 设置冷却时间，避免连续触发
                }
            }
        }
    }

    public void onUseTick(Level worldIn, LivingEntity livingEntityIn, ItemStack stack, int count) {
        //蓄力缩减（30%：1-30%=0.7
        float effectEfficiency = 1-(MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectEfficiency(livingEntityIn, soul_annihilator_Effect))/100;
        // 玩家持续使用（蓄力）时调用，用于表现粒子和音效
        int i = this.getUseDuration(stack) - count; // 计算已蓄力量
        if (i == Math.max(1,(int)(10*effectEfficiency) ) ) {
            this.masseffectParticle(worldIn, livingEntityIn, 2.0F); // 10帧时生成粒子效果（半径2）
        }
        if (i == Math.max(2,(int)(20*effectEfficiency) ) ) {
            this.masseffectParticle(worldIn, livingEntityIn, 3.5F); // 20帧时生成粒子效果（半径3.5）
        }
        if (i == Math.max(3,(int)(30*effectEfficiency) ) ) {
            this.masseffectParticle(worldIn, livingEntityIn, 5.0F); // 30帧时生成粒子效果（半径5.0）
        }
        if (i == Math.max(4,(int)(40*effectEfficiency) ) ) {
            livingEntityIn.playSound((SoundEvent) ModSounds.MALEDICTUS_SHORT_ROAR.get(), 1.0F, 1.0F); // 40帧时播放暴怒吼声，表示蓄力达成
        }
    }
    /// 震地技能
    private void yall(Level world, LivingEntity caster) {
        // 蓄力完成后触发范围伤害，用于表现“爆发”效果
        double radius = (double)6.0F; // 伤害范围半径6格
        ScreenShake_Entity.ScreenShake(world, caster.position(), 30.0F, 0.1F, 0, 30); // 屏幕震动效果
        world.playSound((Player)null, caster.getX(), caster.getY(), caster.getZ(), SoundEvents.GENERIC_EXPLODE, SoundSource.PLAYERS, 1.5F, 1.0F / (caster.getRandom().nextFloat() * 0.4F + 0.8F)); // 播放爆炸音效（增强感官冲击）

        for(Entity entity : world.getEntities(caster, caster.getBoundingBox().inflate(radius, radius, radius))) {
            if (entity instanceof LivingEntity) {
                //伤害
                float effectLevel = (MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectLevel(caster, cursium_annihilator_Effect));
                float damage = (float)caster.getAttributeValue(Attributes.ATTACK_DAMAGE) * effectLevel/100;
                entity.hurt(world.damageSources().mobAttack(caster), damage);
            }
        }
        if (world.isClientSide) {
            // 仅客户端，显示粒子特效（增强视觉效果）
            world.addParticle(new RingParticle.RingData(0.0F, ((float)Math.PI / 2F), 30, 0.337F, 0.925F, 0.8F, 1.0F, 85.0F, false, RingParticle.EnumRingBehavior.GROW),
                    caster.getX(), caster.getY() + 0.03F, caster.getZ(), 0.0, 0.0, 0.0);
        }
    }

    private void masseffectParticle(Level world, LivingEntity caster, float radius) {
        // 生成粒子效果，用于蓄力时视觉表现
        if (world.isClientSide) {
            for (int j = 0; j < 70; ++j) {
                float angle = (float)(Math.random() * 2.0F * Math.PI); // 随机角度
                double distance = Math.sqrt(Math.random()) * radius; // 随机距离
                double extraX = caster.getX() + distance * Math.cos(angle); // 计算粒子偏移X
                double extraY = caster.getY() + 0.3; // 粒子Y坐标
                double extraZ = caster.getZ() + distance * Math.sin(angle); // 粒子偏移Z
                world.addParticle((ParticleOptions) ModParticle.PHANTOM_WING_FLAME.get(), extraX, extraY, extraZ, 0.0, world.random.nextGaussian() * 0.04, 0.0); // 添加粒子
            }
        }
    }
}