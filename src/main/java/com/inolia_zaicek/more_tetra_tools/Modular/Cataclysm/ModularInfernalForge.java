package com.inolia_zaicek.more_tetra_tools.Modular.Cataclysm; // 定义该类所属的包，表示它是“More Mod Tetra”模组中“Modular Curios”部分的一部分。

import com.github.L_Ender.cataclysm.config.CMConfig;
import com.github.L_Ender.cataclysm.entity.effect.ScreenShake_Entity;
import com.github.L_Ender.cataclysm.init.ModSounds;
import com.google.common.collect.Multimap;
import com.inolia_zaicek.more_tetra_tools.Modular.ModularLaPlumaScythe;
import com.inolia_zaicek.more_tetra_tools.MoreTetraTools;
import com.inolia_zaicek.more_tetra_tools.Util.MTTEffectHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ObjectHolder;
import se.mickelus.mutil.network.PacketHandler;
import se.mickelus.tetra.TetraToolActions;
import se.mickelus.tetra.blocks.workbench.BasicWorkbenchBlock;
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
public class ModularInfernalForge extends ItemModularHandheld { // 声明一个名为ModularNecklace的公共类，它继承自ModularItem并实现ICurio接口。
    //部件类型/槽位——[slot]
    public final static String modular_infernal_forge_hammer = "modular_infernal_forge/hammer";
    public final static String modular_infernal_forge_handle = "modular_infernal_forge/handle";
    public final static String modular_infernal_forge_pickaxe = "modular_infernal_forge/pickaxe";
    public final static String modular_infernal_forge_counterweight = "modular_infernal_forge/counterweight";
    public final static String modular_infernal_forge_socket = "modular_infernal_forge/socket";
    // --- 物品标识符 ---
    /*** 模块化项链在模组内的唯一标识符。* 这个标识符用于在NBT数据、配方和其他模组交互中引用该物品。*/
    public final static String identifier = "modular_infernal_forge";
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
    /*** 使用ObjectHolder注解，使得Forge在加载时能够自动获取已注册的“tetra:modular_infernal_forge”物品实例。
     * 这允许其他模组或代码直接通过`ModularNecklace.instance`来访问这个物品，而无需手动注册和查找。
     * `registryName = "item"` 指定了要查找的注册表类型是物品。
     * `value = "tetra:modular_infernal_forge"` 指定了该物品在物品注册表中的名称。
     * 注意：此字段在编译时和运行时可能存在依赖关系，确保“tetra:modular_infernal_forge”已正确注册。*/
    //modular_infernal_forge算是类似“单头工具”、“双头工具”的物品必备ID
    @ObjectHolder(
            registryName = "item",
            value = "tetra:modular_infernal_forge"
    )
    public static ModularLaPlumaScythe instance;

    /*** ModularNecklace 类的构造函数。* 这里初始化了该模块化物品的基本属性。*/
    public ModularInfernalForge() {
        // 调用父类（ModularItem）的构造函数，并设置物品的基本属性：// new Item.Properties(): 创建物品属性对象。// .stacksTo(1): 设置该物品堆叠上限为1，表示项链是独立的、不可堆叠的物品。// .fireResistant(): 使该物品具有防火属性，在火焰中不会被烧毁。
        super(new Properties().stacksTo(1).fireResistant());
        //可否打磨
        canHone = true;
        //设置主要部件有什么
        majorModuleKeys = new String[]{modular_infernal_forge_hammer,  modular_infernal_forge_handle,modular_infernal_forge_pickaxe,};
        //设置次要部件有什么
        minorModuleKeys = new String[]{ modular_infernal_forge_socket,modular_infernal_forge_counterweight};
        // 定义该项链所必需的模块（Required Modules）。游戏会确保这些模块至少存在一个，否则物品可能无法正常工作或显示。
        requiredModules = new String[]{modular_infernal_forge_hammer, modular_infernal_forge_handle,modular_infernal_forge_pickaxe,modular_infernal_forge_counterweight};
        //可修复
        SchematicRegistry.instance.registerSchematic(new RepairSchematic(this, identifier));
        //连携
        MoreTetraTools.items.add(this);
    }
    //连携
    @Override
    public void commonInit(PacketHandler packetHandler) {
        DataManager.instance.synergyData.onReload(() -> synergies =
                DataManager.instance.synergyData.getOrdered("modular_infernal_forge/"));
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

    @Override
    public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context) {
        Player player = context.getPlayer();
        Level world = context.getLevel();
        BlockPos pos = context.getClickedPos();
        InteractionHand hand = context.getHand();
        return player != null && !player.isCrouching() && world.getBlockState(pos).getBlock().equals(Blocks.CRAFTING_TABLE) && this.getToolLevel(player.getItemInHand(hand), TetraToolActions.hammer) > 0 ? BasicWorkbenchBlock.upgradeWorkbench(player, world, pos, hand, context.getClickedFace()) : super.onItemUseFirst(stack, context);
    }
    /// 锻锤
    @Override
    public boolean hurtEnemy(ItemStack heldItemStack, LivingEntity target, LivingEntity attacker) {
        if (!target.level().isClientSide) {
            target.playSound((SoundEvent) ModSounds.HAMMERTIME.get(), 0.5F, 0.5F);
            target.knockback((double)1.0F, attacker.getX() - target.getX(), attacker.getZ() - target.getZ());
        }

        return true;
    }
    @Override
    public InteractionResult useOn(UseOnContext context) {
        // 当玩家使用该物品在方块上时触发
        ItemStack stack = context.getItemInHand(); // 获取玩家手中的物品
        Player player = context.getPlayer(); // 获取玩家对象
        if (player.getMainHandItem() == stack) {
            // 如果拿在主手上，触发震地效果
            this.EarthQuake(context); // 执行震地攻击方法
            int cooldown = Math.max( 1,(int) ((int) 20*MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectEfficiency(player, netherite_base_Effect)) );
            player.getCooldowns().addCooldown(this, cooldown); // 设置技能冷却
            return InteractionResult.SUCCESS; // 操作成功
        } else {
            return super.useOn(context); // 其他情况用父类处理
        }
    }
    //获取工具等级：getToolLevel(player.getItemInHand(hand), TetraToolActions.hammer)
    private void EarthQuake(UseOnContext context) {
        Player player = context.getPlayer(); // 获取使用者
        Level world = context.getLevel(); // 获取当前世界
        boolean berserk = player.getMaxHealth() * 1.0F / 2.0F >= player.getHealth(); // 判断血量是否在半血以下（狂暴状态）
        int hammerLevel = (int)( getToolLevel(context.getItemInHand(), TetraToolActions.hammer) / 2 );//锤子等级/2
        float atk = (float)player.getAttributeValue(Attributes.ATTACK_DAMAGE);
        float attackDamage = atk*(MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectLevel(player, earthquake_strike_Effect))/100;
        double radius = (double) (4.0F + hammerLevel); // 地震影响半径
        ScreenShake_Entity.ScreenShake(world, player.position(), 30.0F, 0.1F, 0, 30); // 震动屏幕效果
        world.playSound((Player)null, player.getX(), player.getY(), player.getZ(), SoundEvents.GENERIC_EXPLODE, SoundSource.PLAYERS, 1.5F, 1.0F / (player.getRandom().nextFloat() * 0.4F + 0.8F)); // 播放爆炸声

        for(Entity entity : world.getEntities(player, player.getBoundingBox().inflate(radius, radius, radius))) {
            // 遍历影响范围内的实体（包括玩家附近的实体）
            if (entity instanceof LivingEntity) {
                // 对生命体实体造成伤害
                entity.hurt(world.damageSources().mobAttack(player),
                        attackDamage
                );
                // 伤害基于玩家攻击力
                entity.setDeltaMovement(entity.getDeltaMovement().multiply((double)0.0F, (double)2.0F, (double)0.0F)); // 垂直方向强化击飞效果
                if (berserk) {
                    entity.setSecondsOnFire(5); // 狂暴状态下点燃目标5秒
                }
            }
        }

        if (world.isClientSide) {
            // 客户端：生成粒子特效
            BlockState block = world.getBlockState(player.blockPosition().below()); // 获取玩家脚下方块状态
            double NumberofParticles = radius * (double)4.0F; // 粒子数量计算（防止过多）
            for(double i = (double)0.0F; i < (double)80.0F; ++i) {
                // 循环创建粒子效果
                double d0 = player.getX() + radius * (double) Mth.sin((float)(i / NumberofParticles * (double)360.0F)); // 计算粒子X位置沿圆周
                double d1 = player.getY() + 0.15; // 粒子Y位置稍微偏上
                double d2 = player.getZ() + radius * (double)Mth.cos((float)(i / NumberofParticles * (double)360.0F)); // 粒子Z位置沿圆周
                double d3 = world.getRandom().nextGaussian() * 0.2; // 粒子运动喷溅X分量
                double d4 = world.getRandom().nextGaussian() * 0.2; // 粒子运动喷溅Y分量
                double d5 = world.getRandom().nextGaussian() * 0.2; // 粒子运动喷溅Z分量
                world.addParticle(new BlockParticleOption(ParticleTypes.BLOCK, block), d0, d1, d2, d3, d4, d5); // 添加块粒子
                if (berserk) {
                    world.addParticle(ParticleTypes.FLAME, d0, d1, d2, d3, d4, d5); // 狂暴状态添加火焰粒子
                }
            }
        }
    }

    @Override
    public boolean canDisableShield(ItemStack stack, ItemStack shield, LivingEntity entity, LivingEntity attacker) {
        // 该物品可以破坏盾牌
        return true;
    }
}