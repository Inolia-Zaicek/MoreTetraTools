package com.inolia_zaicek.more_tetra_tools.Modular.Cataclysm; // 定义该类所属的包，表示它是“More Mod Tetra”模组中“Modular Curios”部分的一部分。

import com.github.L_Ender.cataclysm.capabilities.RenderRushCapability;
import com.github.L_Ender.cataclysm.config.CMConfig;
import com.github.L_Ender.cataclysm.entity.projectile.Phantom_Halberd_Entity;
import com.github.L_Ender.cataclysm.init.ModCapabilities;
import com.github.L_Ender.cataclysm.init.ModParticle;
import com.google.common.collect.Multimap;
import com.inolia_zaicek.more_tetra_tools.Modular.ModularLaPlumaScythe;
import com.inolia_zaicek.more_tetra_tools.MoreTetraTools;
import com.inolia_zaicek.more_tetra_tools.Util.MTTEffectHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
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
import static com.inolia_zaicek.more_tetra_tools.Effect.Clent.MTTEffectGuiStats.incinerat_break_Effect;

@SuppressWarnings({"all", "removal"})
public class ModularSoulRender extends ItemModularHandheld { // 声明一个名为ModularNecklace的公共类，它继承自ModularItem并实现ICurio接口。
    //部件类型/槽位——[slot]
    public final static String modular_soul_render_blade = "modular_soul_render/blade";
    public final static String modular_soul_render_handle = "modular_soul_render/handle";
    public final static String modular_soul_render_head = "modular_soul_render/head";
    public final static String modular_soul_render_counterweight = "modular_soul_render/counterweight";
    public final static String modular_soul_render_socket = "modular_soul_render/socket";
    // --- 物品标识符 ---
    /*** 模块化项链在模组内的唯一标识符。* 这个标识符用于在NBT数据、配方和其他模组交互中引用该物品。*/
    public final static String identifier = "modular_soul_render";
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
    /*** 使用ObjectHolder注解，使得Forge在加载时能够自动获取已注册的“tetra:modular_soul_render”物品实例。
     * 这允许其他模组或代码直接通过`ModularNecklace.instance`来访问这个物品，而无需手动注册和查找。
     * `registryName = "item"` 指定了要查找的注册表类型是物品。
     * `value = "tetra:modular_soul_render"` 指定了该物品在物品注册表中的名称。
     * 注意：此字段在编译时和运行时可能存在依赖关系，确保“tetra:modular_soul_render”已正确注册。*/
    //modular_soul_render算是类似“单头工具”、“双头工具”的物品必备ID
    @ObjectHolder(
            registryName = "item",
            value = "tetra:modular_soul_render"
    )
    public static ModularLaPlumaScythe instance;

    /*** ModularNecklace 类的构造函数。* 这里初始化了该模块化物品的基本属性。*/
    public ModularSoulRender() {
        // 调用父类（ModularItem）的构造函数，并设置物品的基本属性：// new Item.Properties(): 创建物品属性对象。// .stacksTo(1): 设置该物品堆叠上限为1，表示项链是独立的、不可堆叠的物品。// .fireResistant(): 使该物品具有防火属性，在火焰中不会被烧毁。
        super(new Properties().stacksTo(1).fireResistant());
        //可否打磨
        canHone = true;
        //设置主要部件有什么
        majorModuleKeys = new String[]{modular_soul_render_blade,  modular_soul_render_handle};
        //设置次要部件有什么
        minorModuleKeys = new String[]{modular_soul_render_head,modular_soul_render_socket,modular_soul_render_counterweight};
        // 定义该项链所必需的模块（Required Modules）。游戏会确保这些模块至少存在一个，否则物品可能无法正常工作或显示。
        requiredModules = new String[]{modular_soul_render_blade, modular_soul_render_handle,modular_soul_render_head};
        //可修复
        SchematicRegistry.instance.registerSchematic(new RepairSchematic(this, identifier));
        //连携
        MoreTetraTools.items.add(this);
    }
    //连携
    @Override
    public void commonInit(PacketHandler packetHandler) {
        DataManager.instance.synergyData.onReload(() -> synergies =
                DataManager.instance.synergyData.getOrdered("modular_soul_render/"));
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
    /// 戟
// 释放使用（攻击或蓄力）方法，参数为物品、场景、实体和剩余时间
    public void releaseUsing(ItemStack stack, Level level, LivingEntity livingEntity, int timeLeft) {
        boolean hasSucceeded = false; // 标记是否成功触发冲刺/生成戟阵
        if (livingEntity instanceof Player player) { // 如果实体是玩家
            int i = this.getUseDuration(stack) - timeLeft; // 计算已使用的时间
            if (livingEntity.isShiftKeyDown()) {
                /// 密度与范围的加成
                int effectLevel = (MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectLevel(player, cursium_field_Effect));
                //生成戟阵
                this.StrikeWindmillHalberd(
                        level,        // 场景对象，用于粒子和实体生成
                        player,       // 执行该方法的玩家实体
                        7+effectLevel,            // 提升戟阵的密度（单位范围之内的数量，不影响杀伤范围，越高戟排列越直（像是放射状x《《相当于提升数量》》
                        5+effectLevel,            // 提升范围与数量，但密度不变（提升这个会向外额外生成多次戟阵《《提升这个相当于扩大范围》》
                        1.0F,        // initialRadius：初始半径（距离中心的起点）越大，矛距离自身越远《《也不建议改动
                        1.0F,        // 内侧矛阵轮与外侧矛阵轮之间的间隔【(--(--o】和【(-(-o】【这种《《不建议改小，会太密集
                        0.2,         // curveFactor：弧度曲线偏移量，影响弧线弯曲程度
                        1            // delay：粒子出现的延迟（单位时间或帧）
                );
                if (!level.isClientSide) { // 服务器端设置冷却
                    int cooldown = Math.max( 1,(int) ((int) 20*MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectEfficiency(livingEntity, cursium_field_Effect)) );
                    player.getCooldowns().addCooldown(this, cooldown); // 设置冷却时间，避免连续触发
                }
            } else { // 冲刺
                int t = Mth.clamp(i, 0, 60); // 蓄力时间限制60
                if (t > 0) { // 如果蓄力时间大于0
                    float f = 0.1F * t; // 基于时间的移动速度因子
                    // 增加玩家的速度（冲刺距离）向前
                    Vec3 vec3 = player.getDeltaMovement().add(
                            player.getViewVector(1.0F).normalize().multiply(f, f * 0.15F, f));
                    // 设置实体的运动状态，向前推进并略微向上
                    livingEntity.setDeltaMovement(vec3.add(0.0, livingEntity.onGround() ? 0.2 : 0.0, 0.0));
                    // 获取冲刺能力 Cap
                    RenderRushCapability.IRenderRushCapability ChargeCapability =
                            (RenderRushCapability.IRenderRushCapability)ModCapabilities.getCapability(livingEntity, ModCapabilities.RENDER_RUSH_CAPABILITY);
                    if (ChargeCapability != null) {
                        ChargeCapability.setRush(true); // 设置冲刺状态
                        ChargeCapability.setTimer(t / 2); // 设置冲刺时间（减半）
                        float effectLevel = (MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectLevel(player, cursium_assault_Effect));
                        /// 冲刺伤害
                        ChargeCapability.setdamage(
                                (float)player.getAttributeValue(Attributes.ATTACK_DAMAGE)*effectLevel/100
                        ); // 设置伤害值
                        hasSucceeded = true; // 表示成功触发
                    }
                    if (!level.isClientSide && hasSucceeded) { // 服务器端添加冷却
                        int cooldown = Math.max( 1,(int) ((int) 20*MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectEfficiency(livingEntity, cursium_field_Effect)) );
                        player.getCooldowns().addCooldown(this, cooldown); // 设置冷却时间，避免连续触发
                    }
                }
            }
        }
    }

    // 生成戟阵的核心方法
    private void StrikeWindmillHalberd(Level level, LivingEntity player, int numberOfBranches, int particlesPerBranch,
                                       double initialRadius, double radiusIncrement, double curveFactor, int delay) {
        float angleIncrement = (float)((Math.PI * 2D) / numberOfBranches); // 每枝的角度间隔

        for (int branch = 0; branch < numberOfBranches; ++branch) {
            float baseAngle = angleIncrement * branch; // 当前枝的基础角度

            for (int i = 0; i < particlesPerBranch; ++i) {
                double currentRadius = initialRadius + i * radiusIncrement; // 当前半径
                float currentAngle = (float)(baseAngle + (i * angleIncrement) / initialRadius + i * curveFactor); // 当前角度（弧度）
                // 计算粒子位置偏移
                double xOffset = currentRadius * Math.cos(currentAngle);
                double zOffset = currentRadius * Math.sin(currentAngle);
                // 粒子生成位置（以玩家为中心，偏移一定距离）
                double spawnX = player.getX() + xOffset;
                double spawnY = player.getY() + 0.3;
                double spawnZ = player.getZ() + zOffset;
                int d3 = delay * (i + 1); // 粒子出现延迟（递增）
                // 添加随机振动效果
                double deltaX = level.getRandom().nextGaussian() * 0.007;
                double deltaY = level.getRandom().nextGaussian() * 0.007;
                double deltaZ = level.getRandom().nextGaussian() * 0.007;
                // 如果是客户端，绘制粒子
                if (level.isClientSide) {
                    level.addParticle(ModParticle.PHANTOM_WING_FLAME.get(), spawnX, spawnY, spawnZ, deltaX, deltaY, deltaZ);
                }
                // 根据位置生成戟（伤害在下面控制
                this.spawnHalberd(spawnX, spawnZ, player.getY() - 5F, player.getY() + 3F, currentAngle, d3, level, player);
            }
        }
    }

    // 在地面附近生成戟实体的方法
    private void spawnHalberd(double x, double z, double minY, double maxY, float rotation, int delay, Level world, LivingEntity player) {
        BlockPos blockpos = BlockPos.containing(x, maxY, z); // 以X,Z为中心，Y起始位置
        boolean foundGround = false; // 标记是否找到适合放置的地面
        double shapeMaxY = 0.0; // 用于存储碰撞体最大Y

        do {
            BlockPos blockposBelow = blockpos.below(); // 上移一格
            BlockState blockstate = world.getBlockState(blockposBelow);
            if (blockstate.isFaceSturdy(world, blockposBelow, Direction.UP)) { // 判断方块是否坚实
                if (!world.isEmptyBlock(blockpos)) { // 如果当前位置非空
                    BlockState blockstate1 = world.getBlockState(blockpos);
                    VoxelShape voxelshape = blockstate1.getCollisionShape(world, blockpos);
                    if (!voxelshape.isEmpty()) { // 如果有碰撞体，则记录最大Y
                        shapeMaxY = voxelshape.max(Direction.Axis.Y);
                    }
                }
                foundGround = true; // 找到合适地面
                break;
            }
            blockpos = blockpos.below(); // 继续向下寻找
        } while (blockpos.getY() >= Mth.floor(minY) - 1);

        if (foundGround) { // 如果找到合适地面
            float effectEfficiency = (MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectEfficiency(player, cursium_assault_Effect));
            float damage = (float) (player.getAttributeValue(Attributes.ATTACK_DAMAGE)*effectEfficiency/100);
            // 生成戟实体，传入位置、角度、延迟、伤害、所属实体
            world.addFreshEntity(new Phantom_Halberd_Entity(
                    world, x, (double)blockpos.getY() + shapeMaxY, z, rotation, delay, player,
                    /// 戟阵伤害（基础12
                    (float)damage));
        }
    }

    // 计算蓄力时间对应的攻击强度（0到1之间）
    public static float getPowerForTime(int i) {
        float f = (float)i / getMaxLoadTime(); // 正比于蓄力时间比例
        f = (f * f + f * 2.0F) / 3.0F; // 平滑调整增长曲线
        if (f > 1.0F) { // 限制最大值为1
            f = 1.0F;
        }
        return f;
    }
    private static int getMaxLoadTime() {
        return 20;
    }

    public int getUseDuration(ItemStack stack) {
        return 72000;
    }

    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BLOCK;
    }
}