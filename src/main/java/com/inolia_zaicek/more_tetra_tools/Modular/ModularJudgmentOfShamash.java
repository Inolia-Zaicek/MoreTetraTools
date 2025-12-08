package com.inolia_zaicek.more_tetra_tools.Modular; // 定义该类所属的包，表示它是“More Mod Tetra”模组中“Modular Curios”部分的一部分。

import com.google.common.collect.Multimap;
import com.inolia_zaicek.more_tetra_tools.Damage.MTTTickZero;
import com.inolia_zaicek.more_tetra_tools.MoreTetraTools;
import com.inolia_zaicek.more_tetra_tools.Register.MTTEffectsRegister;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
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

import static com.inolia_zaicek.more_tetra_tools.Effect.Clent.MTTEffectGuiStats.divineAvatarEffect;
import static com.inolia_zaicek.more_tetra_tools.Effect.Clent.MTTEffectGuiStats.shamash_assault_Effect;

@SuppressWarnings({"all", "removal"})
public class ModularJudgmentOfShamash extends ItemModularHandheld { // 声明一个名为ModularNecklace的公共类，它继承自ModularItem并实现ICurio接口。
    //部件类型/槽位——[slot]
    public final static String modular_judgment_of_shamash_blade = "modular_judgment_of_shamash/blade";
    public final static String modular_judgment_of_shamash_handle = "modular_judgment_of_shamash/handle";
    public final static String modular_judgment_of_shamash_guard = "modular_judgment_of_shamash/guard";
    public final static String modular_judgment_of_shamash_counterweight = "modular_judgment_of_shamash/counterweight";
    public final static String modular_judgment_of_shamash_socket = "modular_judgment_of_shamash/socket";
    // --- 物品标识符 ---
    /*** 模块化项链在模组内的唯一标识符。* 这个标识符用于在NBT数据、配方和其他模组交互中引用该物品。*/
    public final static String identifier = "modular_judgment_of_shamash";
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
    /*** 使用ObjectHolder注解，使得Forge在加载时能够自动获取已注册的“tetra:modular_judgment_of_shamash”物品实例。
     * 这允许其他模组或代码直接通过`ModularNecklace.instance`来访问这个物品，而无需手动注册和查找。
     * `registryName = "item"` 指定了要查找的注册表类型是物品。
     * `value = "tetra:modular_judgment_of_shamash"` 指定了该物品在物品注册表中的名称。
     * 注意：此字段在编译时和运行时可能存在依赖关系，确保“tetra:modular_judgment_of_shamash”已正确注册。*/
    //modular_judgment_of_shamash算是类似“单头工具”、“双头工具”的物品必备ID
    @ObjectHolder(
            registryName = "item",
            value = "tetra:modular_judgment_of_shamash"
    )
    public static ModularLaPlumaScythe instance;

    /*** ModularNecklace 类的构造函数。* 这里初始化了该模块化物品的基本属性。*/
    public ModularJudgmentOfShamash() {
        // 调用父类（ModularItem）的构造函数，并设置物品的基本属性：// new Item.Properties(): 创建物品属性对象。// .stacksTo(1): 设置该物品堆叠上限为1，表示项链是独立的、不可堆叠的物品。// .fireResistant(): 使该物品具有防火属性，在火焰中不会被烧毁。
        super(new Properties().stacksTo(1).fireResistant());
        //可否打磨
        canHone = true;
        //设置主要部件有什么
        majorModuleKeys = new String[]{modular_judgment_of_shamash_blade,  modular_judgment_of_shamash_handle};
        //设置次要部件有什么
        minorModuleKeys = new String[]{modular_judgment_of_shamash_guard, modular_judgment_of_shamash_socket,modular_judgment_of_shamash_counterweight};
        // 定义该项链所必需的模块（Required Modules）。游戏会确保这些模块至少存在一个，否则物品可能无法正常工作或显示。
        requiredModules = new String[]{modular_judgment_of_shamash_blade, modular_judgment_of_shamash_handle};
        //可修复
        SchematicRegistry.instance.registerSchematic(new RepairSchematic(this, identifier));
        //连携
        MoreTetraTools.items.add(this);
    }
    //连携
    @Override
    public void commonInit(PacketHandler packetHandler) {
        DataManager.instance.synergyData.onReload(() -> synergies =
                DataManager.instance.synergyData.getOrdered("modular_judgment_of_shamash/"));
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
            //攻击
            if (player.getMainHandItem().getItem() instanceof IModularItem item
                    &&!player.getCooldowns().isOnCooldown(player.getMainHandItem().getItem())) {
                //获取词条等级
                float effectLevel = (float) item.getEffectLevel(player.getMainHandItem(), shamash_assault_Effect);
                if (effectLevel > 0) {
                    double range = (double) 2.5F;
                    Vec3 srcVec = player.getEyePosition();
                    Vec3 lookVec = player.getViewVector(1.0F);
                    Vec3 destVec = srcVec.add(lookVec.x() * range, lookVec.y() * range, lookVec.z() * range);
                    float var9 = 1.0F;
                    List<Entity> possibleList = level.getEntities(player, player.getBoundingBox().expandTowards(lookVec.x() * range, lookVec.y() * range, lookVec.z() * range).inflate((double) var9, (double) var9, (double) var9));
                    boolean flag = false;
                    //Cataclysm.PROXY.playWorldSound(player, (byte) 1);
                    for (Entity entity : possibleList) {
                        if (entity instanceof LivingEntity playerEntity) {
                            float borderSize = 0.5F;
                            AABB collisionBB = entity.getBoundingBox().inflate((double) borderSize, (double) borderSize, (double) borderSize);
                            Optional<Vec3> interceptPos = collisionBB.clip(srcVec, destVec);
                            if (collisionBB.contains(srcVec)) {
                                flag = true;
                            } else if (interceptPos.isPresent()) {
                                flag = true;
                            }

                            if (flag) {
                                var DamageType = MTTTickZero.hasSource(player.level(), MTTTickZero.TickFireDamage,player);
                                float finish = (float) player.getAttributeValue(Attributes.ATTACK_DAMAGE) * effectLevel/100;
                                entity.invulnerableTime=0;
                                entity.hurt(DamageType, finish);
                                if(entity instanceof LivingEntity mob){
                                    mob.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 40, 1));
                                    var map = mob.getActiveEffectsMap();
                                    map.put(MobEffects.MOVEMENT_SLOWDOWN,
                                            new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 40, 1));
                                    mob.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 40, 1));
                                    map.put(MobEffects.WEAKNESS,
                                            new MobEffectInstance(MobEffects.WEAKNESS, 40, 1));
                                }
                                entity.invulnerableTime=0;
                                player.swing(InteractionHand.MAIN_HAND);
                                player.startUsingItem(InteractionHand.MAIN_HAND);
                                var DamageType1 = MTTTickZero.source(player.level(), DamageTypes.MAGIC);
                                if(player.getHealth()>1) {
                                    player.hurt(DamageType, Math.max(1,player.getMaxHealth() * 0.13f)  );
                                }else{
                                player.hurt(DamageType,0);
                                }
                                player.getCooldowns().addCooldown(this, 20 * 5);//设置冷却时间
                                level.playSound(
                                        null, // 玩家参数为 null 表示音效来自世界，不绑定到特定玩家
                                        player.getX(),
                                        player.getY(),
                                        player.getZ(),
                                        SoundEvents.BLAZE_SHOOT, // 箭矢射出的音效
                                        SoundSource.PLAYERS, // 音效来源类别，这里是玩家动作
                                        0.6F, // 音量 (1.0f 是标准音量)
                                        1.0F
                                );
                            }
                        }
                    }
                }
            }
        } else if (!level.isClientSide() && pUsedHand == InteractionHand.OFF_HAND) {
            //攻击
            if (player.getOffhandItem().getItem() instanceof IModularItem item
                    &&!player.getCooldowns().isOnCooldown(player.getOffhandItem().getItem())) {
                float effectLevel = (float) item.getEffectLevel(player.getOffhandItem(), shamash_assault_Effect);
                if (effectLevel > 0) {
                    double range = (double) 2.5F;
                    Vec3 srcVec = player.getEyePosition();
                    Vec3 lookVec = player.getViewVector(1.0F);
                    Vec3 destVec = srcVec.add(lookVec.x() * range, lookVec.y() * range, lookVec.z() * range);
                    float var9 = 1.0F;
                    List<Entity> possibleList = level.getEntities(player, player.getBoundingBox().expandTowards(lookVec.x() * range, lookVec.y() * range, lookVec.z() * range).inflate((double) var9, (double) var9, (double) var9));
                    boolean flag = false;
                    //Cataclysm.PROXY.playWorldSound(player, (byte) 1);
                    for (Entity entity : possibleList) {
                        if (entity instanceof LivingEntity playerEntity) {
                            float borderSize = 0.5F;
                            AABB collisionBB = entity.getBoundingBox().inflate((double) borderSize, (double) borderSize, (double) borderSize);
                            Optional<Vec3> interceptPos = collisionBB.clip(srcVec, destVec);
                            if (collisionBB.contains(srcVec)) {
                                flag = true;
                            } else if (interceptPos.isPresent()) {
                                flag = true;
                            }

                            if (flag) {
                                var DamageType = MTTTickZero.hasSource(player.level(), MTTTickZero.TickFireDamage,player);
                                float finish = (float) player.getAttributeValue(Attributes.ATTACK_DAMAGE) * effectLevel/100;
                                entity.invulnerableTime=0;
                                entity.hurt(DamageType, finish);
                                if(entity instanceof LivingEntity mob){
                                    mob.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 40, 1));
                                    var map = mob.getActiveEffectsMap();
                                    map.put(MobEffects.MOVEMENT_SLOWDOWN,
                                            new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 40, 1));
                                    mob.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 40, 1));
                                    map.put(MobEffects.WEAKNESS,
                                            new MobEffectInstance(MobEffects.WEAKNESS, 40, 1));
                                }
                                entity.invulnerableTime=0;
                                player.swing(InteractionHand.OFF_HAND);
                                player.startUsingItem(InteractionHand.OFF_HAND);
                                var DamageType1 = MTTTickZero.source(player.level(), DamageTypes.MAGIC);
                                if(player.getHealth()>1) {
                                    player.hurt(DamageType, Math.max(1,player.getMaxHealth() * 0.13f)  );
                                }else {
                                    player.hurt(DamageType, 0);
                                }
                                player.getCooldowns().addCooldown(this, 20 * 5);//设置冷却时间
                                level.playSound(
                                        null, // 玩家参数为 null 表示音效来自世界，不绑定到特定玩家
                                        player.getX(),
                                        player.getY(),
                                        player.getZ(),
                                        SoundEvents.BLAZE_SHOOT, // 箭矢射出的音效
                                        SoundSource.PLAYERS, // 音效来源类别，这里是玩家动作
                                        0.6F, // 音量 (1.0f 是标准音量)
                                        1.0F
                                );
                            }
                        }
                    }
                }
            }
        }
        return super.use(level, player, pUsedHand);
    }
}