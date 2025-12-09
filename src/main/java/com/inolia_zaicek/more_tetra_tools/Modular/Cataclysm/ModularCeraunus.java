package com.inolia_zaicek.more_tetra_tools.Modular.Cataclysm; // 定义该类所属的包，表示它是“More Mod Tetra”模组中“Modular Curios”部分的一部分。

import com.github.L_Ender.cataclysm.config.CMConfig;
import com.github.L_Ender.cataclysm.entity.effect.Wave_Entity;
import com.github.L_Ender.cataclysm.entity.projectile.Player_Ceraunus_Entity;
import com.github.L_Ender.cataclysm.init.ModSounds;
import com.google.common.collect.Multimap;
import com.inolia_zaicek.more_tetra_tools.Modular.ModularLaPlumaScythe;
import com.inolia_zaicek.more_tetra_tools.MoreTetraTools;
import com.inolia_zaicek.more_tetra_tools.Util.MTTEffectHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ObjectHolder;
import org.jetbrains.annotations.Nullable;
import se.mickelus.mutil.network.PacketHandler;
import se.mickelus.tetra.data.DataManager;
import se.mickelus.tetra.gui.GuiModuleOffsets;
import se.mickelus.tetra.items.modular.ItemModularHandheld;
import se.mickelus.tetra.module.ItemModule;
import se.mickelus.tetra.module.ItemUpgradeRegistry;
import se.mickelus.tetra.module.SchematicRegistry;
import se.mickelus.tetra.module.schematic.RepairSchematic;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.github.L_Ender.cataclysm.items.Ceraunus.getThrownUuid;
import static com.inolia_zaicek.more_tetra_tools.Effect.Clent.MTTEffectGuiStats.*;

@SuppressWarnings({"all", "removal"})
public class ModularCeraunus extends ItemModularHandheld { // 声明一个名为ModularNecklace的公共类，它继承自ModularItem并实现ICurio接口。
    //部件类型/槽位——[slot]
    public final static String modular_ceraunus_blade = "modular_ceraunus/blade";
    public final static String modular_ceraunus_handle = "modular_ceraunus/handle";
    public final static String modular_ceraunus_body = "modular_ceraunus/body";
    public final static String modular_ceraunus_counterweight = "modular_ceraunus/counterweight";
    public final static String modular_ceraunus_socket = "modular_ceraunus/socket";
    // --- 物品标识符 ---
    /*** 模块化项链在模组内的唯一标识符。* 这个标识符用于在NBT数据、配方和其他模组交互中引用该物品。*/
    public final static String identifier = "modular_ceraunus";
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
    /*** 使用ObjectHolder注解，使得Forge在加载时能够自动获取已注册的“tetra:modular_ceraunus”物品实例。
     * 这允许其他模组或代码直接通过`ModularNecklace.instance`来访问这个物品，而无需手动注册和查找。
     * `registryName = "item"` 指定了要查找的注册表类型是物品。
     * `value = "tetra:modular_ceraunus"` 指定了该物品在物品注册表中的名称。
     * 注意：此字段在编译时和运行时可能存在依赖关系，确保“tetra:modular_ceraunus”已正确注册。*/
    //modular_ceraunus算是类似“单头工具”、“双头工具”的物品必备ID
    @ObjectHolder(
            registryName = "item",
            value = "tetra:modular_ceraunus"
    )
    public static ModularLaPlumaScythe instance;

    /*** ModularNecklace 类的构造函数。* 这里初始化了该模块化物品的基本属性。*/
    public ModularCeraunus() {
        // 调用父类（ModularItem）的构造函数，并设置物品的基本属性：// new Item.Properties(): 创建物品属性对象。// .stacksTo(1): 设置该物品堆叠上限为1，表示项链是独立的、不可堆叠的物品。// .fireResistant(): 使该物品具有防火属性，在火焰中不会被烧毁。
        super(new Properties().stacksTo(1).fireResistant());
        //可否打磨
        canHone = true;
        //设置主要部件有什么
        majorModuleKeys = new String[]{modular_ceraunus_blade,  modular_ceraunus_handle};
        //设置次要部件有什么
        minorModuleKeys = new String[]{modular_ceraunus_body, modular_ceraunus_socket,modular_ceraunus_counterweight};
        // 定义该项链所必需的模块（Required Modules）。游戏会确保这些模块至少存在一个，否则物品可能无法正常工作或显示。
        requiredModules = new String[]{modular_ceraunus_blade, modular_ceraunus_handle};
        //可修复
        SchematicRegistry.instance.registerSchematic(new RepairSchematic(this, identifier));
        //连携
        MoreTetraTools.items.add(this);
    }
    //连携
    @Override
    public void commonInit(PacketHandler packetHandler) {
        DataManager.instance.synergyData.onReload(() -> synergies =
                DataManager.instance.synergyData.getOrdered("modular_ceraunus/"));
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
    /// 锚本体代码
    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity holder, int slot, boolean isSelected) {
        // 每帧更新，非客户端侧且已投掷实体存在但未找到实体则移除“thrown_anchor”标签
        if (!level.isClientSide() && getThrownUuid(stack) != null && this.getThrownEntity(level, stack) == null) {
            stack.getTag().remove("thrown_anchor");
        }
    }
    @Override
    public UseAnim getUseAnimation(ItemStack p_43417_) {
        return UseAnim.SPEAR; // 使用投掷动画
    }
    @Override
    public int getUseDuration(ItemStack p_43419_) {
        return 72000; // 蓄力最大时间（持续时间极长，实际按用完或放弃）
    }
    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity livingEntity, int time) {
        if (livingEntity instanceof Player player) {
            int i = this.getUseDuration(stack) - time; // 计算蓄力时间（实际放开时用）
            /// 词条部分
            //锚
            int effectLevel1 = (MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectLevel(livingEntity, break_waves_Effect));
            float effectEfficiency1 = (MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectEfficiency(livingEntity, break_waves_Effect));
            //浪
            int effectLevel2 = (MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectLevel(livingEntity, tempestuous_waves_Effect));
            float effectEfficiency2 = (MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectEfficiency(livingEntity, tempestuous_waves_Effect));
            /// 蓄力时间计算最高蓄力系数，1.0指最高蓄力达到1强度
            float f = getPowerForTime(i,1+effectEfficiency1/100);
            //至少有50%蓄力进度
            if (!((double)f < (double)0.5F) && !level.isClientSide) {
                // 蓄力时间达标（伤害系数>=0.5）且不在客户端
                float yawRadians = (float)Math.toRadians((double)(90.0F + player.getYRot())); // 计算面向角的弧度（面向方向 + 90度）
                double vecX = Math.cos((double)yawRadians); // 面向方向的X分量
                double vecZ = Math.sin((double)yawRadians); // 面向方向的Z分量
                double vec = (double)2.0F; // 产生点（锚点）距离玩家位置的偏移距离（锚投掷距离）
                double spawnX = livingEntity.getX() + vecX * vec; // 计算锚点X坐标
                double spawnY = livingEntity.getY(); // 锚点Y坐标（与玩家同高）
                double spawnZ = livingEntity.getZ() + vecZ * vec; // 计算锚点Z坐标
                float atk = (float)player.getAttributeValue(Attributes.ATTACK_DAMAGE);//获取攻击力
                /// 计算
                int wave = 0;
                float ceraunusDamage = f* atk*effectLevel1/100;//锚伤（*蓄力进度
                float waveDamage = f* atk*effectLevel2/100;//浪伤（*蓄力进度
                if(atk>16){
                    //4的数量
                    wave+=(int) ( (atk-16.0F)/4.0F );
                }
                /// 攻击力＞16时，每有4点攻击力，水浪数量额外+1
                int numberOfWaves = 4+wave; // 水浪的波次数量
                float angleStep = 25.0F; // 每个水浪的角度间隔（度）
                double firstAngleOffset = (double)(numberOfWaves - 1) / (double)2.0F * (double)angleStep; // 初始角偏移，用于对称分布

                if (livingEntity.isShiftKeyDown()) {
                    int cooldown = Math.max( 1,(int) (20*effectEfficiency2) );
                    // 玩家按下Shift键：执行“水浪攻击”
                    player.getCooldowns().addCooldown(this, CMConfig.CeraunusCooldown); // 设置技能冷却时间
                    level.playSound((Player)null, player.getX(), player.getY(), player.getZ(), (SoundEvent) ModSounds.HEAVY_SMASH.get(), SoundSource.PLAYERS, 0.6F, 1.0F); // 播放攻击声音

                    for(int k = 0; k < numberOfWaves; ++k) {
                        // 根据波次数生成多个水浪
                        double angle = (double)player.getYRot() - firstAngleOffset + (double)((float)k * angleStep); // 当前水浪角度
                        double rad = Math.toRadians(angle); // 角度转弧度
                        double dx = -Math.sin(rad); // 水浪偏移方向X
                        double dz = Math.cos(rad); // 水浪偏移方向Z
                        // 创建水浪实体
                        Wave_Entity WaveEntity = new Wave_Entity(level, livingEntity,
                                60,//水浪存活秒数（？
                                waveDamage//水浪伤害
                        );
                        WaveEntity.setPos(spawnX, spawnY, spawnZ); // 设置水浪起点
                        WaveEntity.setState(1); // 设置水浪状态
                        WaveEntity.setYRot(-((float)(Mth.atan2(dx, dz) * (180D / Math.PI)))); // 设置水浪旋转角（用于方向）
                        livingEntity.level().addFreshEntity(WaveEntity); // 添加水浪实体到世界
                    }
                } else if (getThrownUuid(stack) == null) {

                    // 没有投掷实体，执行“船锚投掷”
                    Player_Ceraunus_Entity launchedBlock = new Player_Ceraunus_Entity(level, player); // 创建锚实体
                    launchedBlock.setBaseDamage(
                            // 设置锚的伤害（由玩家攻击力决定）
                            (double)( ceraunusDamage ));
                    launchedBlock.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 2.5F, 1.0F); // 将锚投出，速度为2.5F
                    if (level.addFreshEntity(launchedBlock)) {
                        this.setThrownEntity(stack, launchedBlock); // 记录投掷实体的UUID
                    }
                }
            }
        }
    }

    // 根据蓄力时间返回伤害倍率（“水浪/船锚”攻击伤害系数）
    public static float getPowerForTime(int time,float maxTime) {
        float f = (float)time / 20.0F; // 计算时间比例（蓄力时间/20秒）
        /// 例如：蓄力了6s：6*6+6*2/3=36+12)/3=48/3=16
        f = (f * f + f * 2.0F) / 3.0F; // 通过曲线增加伤害（平方调整）
        if (f > maxTime) {
            f = maxTime; // 上限设置为1（最大伤害系数）
        }
        return f; // 返回最终伤害系数
    }

    // 获取已投掷实体的UUID
    public static @Nullable UUID getThrownUuid(ItemStack stack) {
        return stack.hasTag() && stack.getTag().hasUUID("thrown_anchor") ? stack.getTag().getUUID("thrown_anchor") : null; // 读取标签中的UUID
    }

    // 通过UUID获取已投掷实体对象
    private @Nullable Player_Ceraunus_Entity getThrownEntity(Level level, ItemStack stack) {
        if (level instanceof ServerLevel server) {
            UUID id = getThrownUuid(stack); // 获取UUID
            if (id != null) {
                Entity e = server.getEntity(id); // 从世界中获取实体
                if (e instanceof Player_Ceraunus_Entity) {
                    Player_Ceraunus_Entity ceraunus_entity = (Player_Ceraunus_Entity)e; // 类型转换
                    return ceraunus_entity;
                }
            }
        }
        return null; // 未找到投掷实体
    }

    // 设置投掷实体的UUID标签
    private void setThrownEntity(ItemStack stack, Player_Ceraunus_Entity cube) {
        stack.getOrCreateTag().putUUID("thrown_anchor", cube.getUUID()); // 存储UUID到标签
    }

    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand); // 获取玩家手中的物品
        player.startUsingItem(hand); // 开始使用（蓄力）
        return InteractionResultHolder.consume(itemstack); // 表示正在使用
    }
}