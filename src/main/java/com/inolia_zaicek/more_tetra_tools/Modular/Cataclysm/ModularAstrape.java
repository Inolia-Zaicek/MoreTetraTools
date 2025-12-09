package com.inolia_zaicek.more_tetra_tools.Modular.Cataclysm; // 定义该类所属的包，表示它是“More Mod Tetra”模组中“Modular Curios”部分的一部分。

import com.github.L_Ender.cataclysm.config.CMConfig;
import com.github.L_Ender.cataclysm.entity.projectile.Lightning_Spear_Entity;
import com.github.L_Ender.cataclysm.init.ModSounds;
import com.google.common.collect.Multimap;
import com.inolia_zaicek.more_tetra_tools.Modular.ModularLaPlumaScythe;
import com.inolia_zaicek.more_tetra_tools.MoreTetraTools;
import com.inolia_zaicek.more_tetra_tools.Util.MTTEffectHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
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
public class ModularAstrape extends ItemModularHandheld { // 声明一个名为ModularNecklace的公共类，它继承自ModularItem并实现ICurio接口。
    //部件类型/槽位——[slot]
    public final static String modular_astrape_blade = "modular_astrape/blade";
    public final static String modular_astrape_handle = "modular_astrape/handle";
    public final static String modular_astrape_guard = "modular_astrape/guard";
    public final static String modular_astrape_counterweight = "modular_astrape/counterweight";
    public final static String modular_astrape_socket = "modular_astrape/socket";
    // --- 物品标识符 ---
    /*** 模块化项链在模组内的唯一标识符。* 这个标识符用于在NBT数据、配方和其他模组交互中引用该物品。*/
    public final static String identifier = "modular_astrape";
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
    /*** 使用ObjectHolder注解，使得Forge在加载时能够自动获取已注册的“tetra:modular_astrape”物品实例。
     * 这允许其他模组或代码直接通过`ModularNecklace.instance`来访问这个物品，而无需手动注册和查找。
     * `registryName = "item"` 指定了要查找的注册表类型是物品。
     * `value = "tetra:modular_astrape"` 指定了该物品在物品注册表中的名称。
     * 注意：此字段在编译时和运行时可能存在依赖关系，确保“tetra:modular_astrape”已正确注册。*/
    //modular_astrape算是类似“单头工具”、“双头工具”的物品必备ID
    @ObjectHolder(
            registryName = "item",
            value = "tetra:modular_astrape"
    )
    public static ModularLaPlumaScythe instance;

    /*** ModularNecklace 类的构造函数。* 这里初始化了该模块化物品的基本属性。*/
    public ModularAstrape() {
        // 调用父类（ModularItem）的构造函数，并设置物品的基本属性：// new Item.Properties(): 创建物品属性对象。// .stacksTo(1): 设置该物品堆叠上限为1，表示项链是独立的、不可堆叠的物品。// .fireResistant(): 使该物品具有防火属性，在火焰中不会被烧毁。
        super(new Properties().stacksTo(1).fireResistant());
        //可否打磨
        canHone = true;
        //设置主要部件有什么
        majorModuleKeys = new String[]{modular_astrape_blade,  modular_astrape_handle};
        //设置次要部件有什么
        minorModuleKeys = new String[]{modular_astrape_guard, modular_astrape_socket,modular_astrape_counterweight};
        // 定义该项链所必需的模块（Required Modules）。游戏会确保这些模块至少存在一个，否则物品可能无法正常工作或显示。
        requiredModules = new String[]{modular_astrape_blade, modular_astrape_handle};
        //可修复
        SchematicRegistry.instance.registerSchematic(new RepairSchematic(this, identifier));
        //连携
        MoreTetraTools.items.add(this);
    }
    //连携
    @Override
    public void commonInit(PacketHandler packetHandler) {
        DataManager.instance.synergyData.onReload(() -> synergies =
                DataManager.instance.synergyData.getOrdered("modular_astrape/"));
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
    /// 本体
    public UseAnim getUseAnimation(ItemStack p_43417_) {
        return UseAnim.SPEAR;
    }

    public int getUseDuration(ItemStack pStack) {
        return 72000;
    }
    // 玩家释放使用技能的主方法
    public void releaseUsing(ItemStack stack, Level level, LivingEntity livingEntity, int time) {
        if (livingEntity instanceof Player player) { // 如果实体是玩家
            int i = this.getUseDuration(stack) - time; // 计算已蓄力时间
            float f = getPowerForTime(i); // 根据蓄力时间计算攻击强度（0到1）
            if (!(f < 0.5F)) { // 蓄力时间到达阈值（超过0.5威力）
                level.playSound(null, player.getX(), player.getY(), player.getZ(), ModSounds.EMP_ACTIVATED.get(), SoundSource.PLAYERS, 1.0F, 0.8F); // 播放技能激活音效
                if (!level.isClientSide) { // 服务器端执行
                    float effectLevel1 = (MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectLevel(livingEntity, thunder_astrape_Effect));
                    float effectLevel2 = (MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectLevel(livingEntity, thunder_field_Effect));
                    float effectEfficiency2 = (MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectEfficiency(livingEntity, thunder_field_Effect));
                    float atk = (float) livingEntity.getAttributeValue(Attributes.ATTACK_DAMAGE);
                    // 计算玩家观看方向（Y轴旋转）
                    float d7 = livingEntity.getYRot(); // 玩家Y轴旋转角度（视角）
                    float d = livingEntity.getXRot(); // 玩家X轴旋转角度（上下角度）
                    // 计算前方向向量（以Y轴为水平面）
                    float d1 = -Mth.sin(d7 * ((float)Math.PI / 180F)) * Mth.cos(d * ((float)Math.PI / 180F));
                    float d2 = -Mth.sin(d * ((float)Math.PI / 180F));
                    float d3 = Mth.cos(d7 * ((float)Math.PI / 180F)) * Mth.cos(d * ((float)Math.PI / 180F));
                    // 计算旋转角度
                    double theta = d7 * Math.PI / 180D;
                    theta += 1; // 旋转角度增加，使投射方向变化
                    // 计算投射方向向量（以Y轴旋转）
                    double vecX = Math.cos(theta);
                    double vecZ = Math.sin(theta);
                    // 计算发射位置（偏移玩家位置）
                    double x = livingEntity.getX() + vecX;
                    double y = livingEntity.getY() + livingEntity.getBbHeight() / 2.0; // 玩家身高中点
                    double Z = livingEntity.getZ() + vecZ;
                    Vec3 vec3 = new Vec3(d1, d2, d3).normalize(); // 计算发射方向单位向量
                    // 计算发射方向的Yaw和Pitch
                    float yRot = (float)(Math.atan2(vec3.z, vec3.x) * 180D / Math.PI) + 90F; // 横向旋转
                    float xRot = (float)(- (Math.atan2(vec3.y, Math.sqrt(vec3.x * vec3.x + vec3.z * vec3.z)) * 180D / Math.PI)); // 纵向旋转
                    // 创建雷电投射实体（雷电炸弹）
                    Lightning_Spear_Entity lightning = new Lightning_Spear_Entity(player, vec3.normalize(), level,
                            /// 矛伤【11
                            effectLevel1*atk/100
                    ); // 伤害由配置控制
                    /// 加速度
                    lightning.accelerationPower = 0.15F; // 加速度/漂浮速度参数
                    lightning.setYRot(yRot); // 设置横向旋转角
                    lightning.setXRot(xRot); // 设置纵向旋转角
                    lightning.setPosRaw(x, y, Z); // 设置起始位置
                    /// 雷电范围伤害【2
                    lightning.setAreaDamage(
                            effectLevel2*atk/100
                    );
                    lightning.setAreaRadius(1.0F+effectEfficiency2); // 雷电范围半径（控制“面积大小”）
                    boolean flag = level.addFreshEntity(lightning); // 添加到世界
                    if (flag) { // 生成成功
                        int cooldown = Math.max( 1,(int) ((int) 20* MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectEfficiency(livingEntity, thunder_astrape_Effect)) );
                        player.getCooldowns().addCooldown(this, cooldown); // 设置冷却时间，避免连续触发
                    }
                }
            }
        }
    }

    // 根据蓄力时间计算攻击强度（0-1）
    public static float getPowerForTime(int p_40662_) {
        float f = (float)p_40662_ / 20.0F; // 以20刻为满蓄力
        f = (f * f + f * 2.0F) / 3.0F; // 增强蓄力成长曲线
        if (f > 1.0F) {
            f = 1.0F; // 限制最大值为1
        }
        return f; // 返回蓄力百分比（影响伤害）
    }

    // 玩家使用武器时触发，开始蓄力
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand); // 获取持有的物品
        player.startUsingItem(hand); // 开始蓄力
        return InteractionResultHolder.consume(itemstack); // 返回“消耗”状态
    }

}