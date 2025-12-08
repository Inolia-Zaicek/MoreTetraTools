package com.inolia_zaicek.more_tetra_tools.Modular; // 定义该类所属的包，表示它是“More Mod Tetra”模组中“Modular Curios”部分的一部分。

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import com.inolia_zaicek.more_tetra_tools.MoreTetraTools;
import com.inolia_zaicek.more_tetra_tools.Register.MTTEffectsRegister;
import com.inolia_zaicek.more_tetra_tools.Util.MTTUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
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
import se.mickelus.tetra.properties.AttributeHelper;

import javax.annotation.Nullable;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.inolia_zaicek.more_tetra_tools.Effect.Clent.MTTEffectGuiStats.*;


@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE,modid = MoreTetraTools.MODID)
@SuppressWarnings({"all", "removal"})
public class ModularTravellerStick extends ItemModularHandheld { // 声明一个名为ModularNecklace的公共类，它继承自ModularItem并实现ICurio接口。
    //部件类型/槽位——[slot]
    public final static String modular_traveller_stick_head = "modular_traveller_stick/head";
    public final static String modular_traveller_stick_handle = "modular_traveller_stick/handle";
    public final static String modular_traveller_stick_core = "modular_traveller_stick/core";
    // --- 物品标识符 ---
    /*** 模块化项链在模组内的唯一标识符。* 这个标识符用于在NBT数据、配方和其他模组交互中引用该物品。*/
    public final static String identifier = "modular_traveller_stick";
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
    /*** 使用ObjectHolder注解，使得Forge在加载时能够自动获取已注册的“tetra:modular_traveller_stick”物品实例。
     * 这允许其他模组或代码直接通过`ModularNecklace.instance`来访问这个物品，而无需手动注册和查找。
     * `registryName = "item"` 指定了要查找的注册表类型是物品。
     * `value = "tetra:modular_traveller_stick"` 指定了该物品在物品注册表中的名称。
     * 注意：此字段在编译时和运行时可能存在依赖关系，确保“tetra:modular_traveller_stick”已正确注册。*/
    //modular_traveller_stick算是类似“单头工具”、“双头工具”的物品必备ID
    @ObjectHolder(
            registryName = "item",
            value = "tetra:modular_traveller_stick"
    )
    public static ModularLaPlumaScythe instance;

    /*** ModularNecklace 类的构造函数。* 这里初始化了该模块化物品的基本属性。*/
    public ModularTravellerStick() {
        // 调用父类（ModularItem）的构造函数，并设置物品的基本属性：// new Item.Properties(): 创建物品属性对象。// .stacksTo(1): 设置该物品堆叠上限为1，表示项链是独立的、不可堆叠的物品。// .fireResistant(): 使该物品具有防火属性，在火焰中不会被烧毁。
        super(new Properties().stacksTo(1).fireResistant());
        //可否打磨
        canHone = true;
        //设置主要部件有什么
        majorModuleKeys = new String[]{modular_traveller_stick_head,  modular_traveller_stick_handle};
        //设置次要部件有什么
        minorModuleKeys = new String[]{modular_traveller_stick_core,};
        // 定义该项链所必需的模块（Required Modules）。游戏会确保这些模块至少存在一个，否则物品可能无法正常工作或显示。
        requiredModules = new String[]{modular_traveller_stick_head, modular_traveller_stick_handle};
        //可修复
        SchematicRegistry.instance.registerSchematic(new RepairSchematic(this, identifier));
        //连携
        MoreTetraTools.items.add(this);
    }
    //连携
    @Override
    public void commonInit(PacketHandler packetHandler) {
        DataManager.instance.synergyData.onReload(() -> synergies =
                DataManager.instance.synergyData.getOrdered("modular_traveller_stick/"));
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
    @OnlyIn(Dist.CLIENT) // 标记此方法仅在客户端执行。
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
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack itemStack) {
        if (this.isBroken(itemStack)) {
            return AttributeHelper.emptyMap;
        } else if (slot == EquipmentSlot.MAINHAND) {
            return this.getAttributeModifiersCached(itemStack);
        } else {
            return slot == EquipmentSlot.OFFHAND ? (Multimap)this.getAttributeModifiersCached(itemStack).entries().stream().filter((entry) -> !((Attribute)entry.getKey()).equals(Attributes.ATTACK_DAMAGE) && !((Attribute)entry.getKey()).equals(Attributes.ATTACK_DAMAGE)).collect(Multimaps.toMultimap(Map.Entry::getKey, Map.Entry::getValue, ArrayListMultimap::create)) : AttributeHelper.emptyMap;
        }
    }

    private static final ResourceLocation X = new ResourceLocation(MoreTetraTools.MODID, "waypoint_x");
    // 定义了用于存储绑定位置 Y 坐标的 NBT 标签的 ResourceLocation。
    private static final ResourceLocation Y = new ResourceLocation(MoreTetraTools.MODID, "waypoint_y");
    // 定义了用于存储绑定位置 Z 坐标的 NBT 标签的 ResourceLocation。
    private static final ResourceLocation Z = new ResourceLocation(MoreTetraTools.MODID, "waypoint_z");
    // 定义了用于存储绑定维度（Dimension）的 NBT 标签的 ResourceLocation。
    private static final ResourceLocation WORLD = new ResourceLocation(MoreTetraTools.MODID, "waypoint_dimension");
    //存储生物信息
    public static final String KEY_ENTITY = "entity";
    @Override//右键方块
    public net.minecraft.world.InteractionResult useOn(net.minecraft.world.item.context.UseOnContext context) {
        // 仅在服务器端执行此逻辑，以防止作弊和网络同步问题。
        if (!context.getLevel().isClientSide()) {
            Player player = context.getPlayer();
            ItemStack heldStack = player.getItemInHand(context.getHand()); // 获取玩家手中正在使用的物品
            BlockPos pos = context.getClickedPos();
            // 检查玩家是否确实持有的是 ModularTravellerStick，并且未使用冷却。
            if (heldStack.getItem() instanceof IModularItem item && player.isShiftKeyDown()) {
                //NBT
                CompoundTag persistentData = heldStack.getOrCreateTag();
                //词条判断
                int teleportLevel = item.getEffectLevel(heldStack, teleportationEffect);
                int waypointLevel = item.getEffectLevel(heldStack, waypointEffect);
                int captureLevel = item.getEffectLevel(heldStack, captureEffect);
                //末影珍珠传送
                if (teleportLevel > 0) {
                    // 计算传送坐标（方块正上方）
                    double x = pos.getX() + 0.5;
                    double y = pos.getY() + 1.0;
                    double z = pos.getZ() + 0.5;
                    // 执行传送并重置摔落距离
                    player.teleportTo(x, y, z);
                    player.resetFallDistance();
                    // 播放传送音效
                    player.level().playSound(null, player.xo, player.yo, player.zo, SoundEvents.CHORUS_FRUIT_TELEPORT, SoundSource.PLAYERS, 1.0F, 1.0F);
                    player.getCooldowns().addCooldown(this, 20 * 1);//设置冷却时间
                    return net.minecraft.world.InteractionResult.SUCCESS;
                }
                //锚点记录
                if (waypointLevel > 0&&player.isShiftKeyDown() && player.hasEffect(MTTEffectsRegister.ReadyToTransfer.get()) ) {
                    //记录坐标
                    persistentData.putInt(X.toString(), pos.getX()); // 使用 ResourceLocation 的 toString() 方法作为 NBT 键
                    persistentData.putInt(Y.toString(), pos.getY()); // 使用 ResourceLocation 的 toString() 方法作为 NBT 键
                    persistentData.putInt(Z.toString(), pos.getZ()); // 使用 ResourceLocation 的 toString() 方法作为 NBT 键
                    persistentData.putString(WORLD.toString(), context.getLevel().dimension().location().getPath()); // 使用 ResourceLocation 的 toString() 方法作为 NBT 键
                    player.sendSystemMessage(Component.translatable("tooltip.more_tetra_tools.set_waypoint",
                                    pos.getX(), // 第一个参数，对应翻译键中的第一个 %d
                                    pos.getY(), // 第二个参数，对应翻译键中的第二个 %d
                                    pos.getZ(), // 第三个参数，对应翻译键中的第三个 %d
                                    context.getLevel().dimension().location().getPath() // 第四个参数，对应翻译键中的 %s
                            ).withStyle(ChatFormatting.LIGHT_PURPLE)
                    );
                    player.removeEffect(MTTEffectsRegister.ReadyToTransfer.get());
                    player.getCooldowns().addCooldown(this, 20 * 1);//设置冷却时间
                }
                //捕捉——释放
                if(captureLevel>0){
                    CompoundTag tag = context.getItemInHand().getOrCreateTag(); // 获取玩家手中物品的 NBT 标签。
                    CompoundTag entityTag = tag.getCompound(KEY_ENTITY); // 获取 NBT 标签中存储的实体信息。
                    // 检查 entityTag 是否不为空，即物品是否存储了生物。
                    if(!entityTag.isEmpty()) {
                        Level level = context.getLevel(); // 获取玩家所在的世界。
                        // 使用 EntityType.create() 方法，根据存储的 NBT 数据重建生物实体。
                        Optional<Entity> entityOpt = EntityType.create(entityTag, level);
                        Direction dir = context.getClickedFace(); // 获取玩家点击的方块的哪个面。
                        // 计算生物放置的位置。
                        // 尝试将生物放置在被点击方块的相邻位置，并且居中。
                        double x = pos.getX() + dir.getStepX() + 0.5;
                        double y = pos.getY() + dir.getStepY();
                        double z = pos.getZ() + dir.getStepZ() + 0.5;
                        // 如果 entityOpt.isPresent() (即成功创建了实体):
                        entityOpt.ifPresent(entity -> {
                            entity.setPos(x, y, z); // 设置生物的位置。
                            level.addFreshEntity(entity); // 将生物添加到游戏中。
                            level.gameEvent(player, GameEvent.ENTITY_PLACE, entity.position()); // 触发一个游戏事件，表示玩家放置了一个实体。
                        });

                        tag.remove(KEY_ENTITY); // 从物品的 NBT 标签中移除存储的生物信息。
                        return InteractionResult.SUCCESS; // 返回 SUCCESS 表示本次交互成功。
                    }
                    // 如果物品中没有存储生物，则返回 PASS，让 Minecraft 处理默认的右键点击方块行为。
                    return InteractionResult.PASS;
                }
            }
        }
        return net.minecraft.world.InteractionResult.PASS;
    }
    // 当玩家与实体互动时触发的方法。
    @SubscribeEvent
    public static boolean event(PlayerInteractEvent.EntityInteractSpecific event) {
        Player player = event.getEntity();
        Entity entity = event.getTarget();
        ItemStack mainHandStack = player.getItemInHand(InteractionHand.MAIN_HAND);
        ItemStack offHandStack = player.getItemInHand(InteractionHand.OFF_HAND);
        if(entity instanceof LivingEntity livingEntity && !(entity instanceof Enemy) ) {
            //主手——nbt空，词条等级>0
            if(mainHandStack.getOrCreateTag().getCompound(KEY_ENTITY).isEmpty()
                    && mainHandStack.getItem() instanceof IModularItem item && item.getEffectLevel(mainHandStack, captureEffect)>0) {
                Level level = player.level(); // 获取玩家所在的世界。
                level.gameEvent(player, GameEvent.ENTITY_INTERACT, entity.position()); // 触发一个游戏事件，表示玩家与实体交互。
                capture(livingEntity, mainHandStack); // 调用 capture 方法，将生物捕获到物品中。
                player.getCooldowns().addCooldown(mainHandStack.getItem(), 20 * 1);//设置冷却时间
                // 返回 true 表示这个方法处理了事件，防止默认的右键点击行为。
                return true;
            }
            //副手——nbt空，词条等级>0
            else if(offHandStack.getOrCreateTag().getCompound(KEY_ENTITY).isEmpty()
                    && offHandStack.getItem() instanceof IModularItem item && item.getEffectLevel(offHandStack, captureEffect)>0) {
                Level level = player.level(); // 获取玩家所在的世界。
                level.gameEvent(player, GameEvent.ENTITY_INTERACT, entity.position()); // 触发一个游戏事件，表示玩家与实体交互。
                capture(livingEntity, offHandStack); // 调用 capture 方法，将生物捕获到物品中。
                player.getCooldowns().addCooldown(offHandStack.getItem(), 20 * 1);//设置冷却时间
                // 返回 true 表示这个方法处理了事件，防止默认的右键点击行为。
                return true;
            }
        }
        return false;
    }
    // 将生物捕获到物品中。
    public static void capture(LivingEntity livingEntity, ItemStack stack) {
        CompoundTag serializedEntity = livingEntity.serializeNBT(); // 将生物实体序列化为 NBT 数据。
        CompoundTag itemTag = stack.getOrCreateTag(); // 获取物品的 NBT 标签，如果不存在则创建。
        itemTag.put(KEY_ENTITY, serializedEntity); // 将序列化后的生物 NBT 数据 put 到物品的 NBT 标签中，使用 KEY_ENTITY 作为键。
        stack.setTag(itemTag); // 将更新后的 NBT 标签设置回物品。
        livingEntity.discard(); // 将生物实体从游戏中移除（销毁）。
    }
    //物品右键使用
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand pUsedHand) {
        if (pUsedHand == InteractionHand.MAIN_HAND) {
            var mainHandItem = player.getMainHandItem();
            if (mainHandItem.getItem() instanceof IModularItem item) {
                //获取词条等级
                int waypointLevel = item.getEffectLevel(mainHandItem, waypointEffect);
                int gohomeLevel = item.getEffectLevel(mainHandItem, gohomeEffect);
                int recallLevel = item.getEffectLevel(mainHandItem, recallEffect);
                if (!level.isClientSide() && waypointLevel > 0) {
                    //没有准备buff
                    if(!player.hasEffect(MTTEffectsRegister.ReadyToTransfer.get()) ) {
                        player.addEffect(new MobEffectInstance(MTTEffectsRegister.ReadyToTransfer.get(), 20 * 5, 0));
                        player.getCooldowns().addCooldown(this, 20 * 1);//设置冷却时间
                    }else{
                        //有buff
                        CompoundTag tag = mainHandItem.getOrCreateTag(); // 获取物品的NBT标签
                        // 检查是否已绑定（即 NBT 中是否包含锚点坐标和维度）
                        boolean hasWaypoint = tag.contains(X.toString()) && tag.contains(Y.toString()) &&
                                tag.contains(Z.toString()) && tag.contains(WORLD.toString());
                        if (hasWaypoint&&player instanceof ServerPlayer serverPlayer&&!player.isShiftKeyDown() ) {
                            // 从 NBT 读取坐标和维度
                            int savedX = tag.getInt(X.toString());
                            int savedY = tag.getInt(Y.toString());
                            int savedZ = tag.getInt(Z.toString());
                            String savedDimensionPath = tag.getString(WORLD.toString());
                            // 将字符串维度路径转换为 Dimension.ResourceKey
                            ResourceLocation dimensionResourceLocation = new ResourceLocation(savedDimensionPath);
                            ServerLevel targetLevel = null;
                            if (level.getServer() != null) { // 确保 server 不为 null
                                targetLevel = level.getServer().getLevel(net.minecraft.resources.ResourceKey.create(Registries.DIMENSION, dimensionResourceLocation));
                            }
                            if (targetLevel != null) {
                                // 调整传送的目标 Y 坐标，使其位于方块中心上方
                                double teleportY = savedY + 1;
                                // 使用 teleportTo 方法，该方法允许在不同维度传送，并处理了其他细节
                                serverPlayer.teleportTo(targetLevel, savedX + 0.5, teleportY, savedZ + 0.5, player.getYRot(), player.getXRot());
                                player.level().playSound(null, player.xo, player.yo, player.zo, SoundEvents.CHORUS_FRUIT_TELEPORT, SoundSource.PLAYERS, 1.0F, 1.0F);
                                player.removeEffect(MTTEffectsRegister.ReadyToTransfer.get());
                                player.getCooldowns().addCooldown(this, 20 * 1);//设置冷却时间
                            }
                        }else {
                            player.sendSystemMessage(Component.translatable("tooltip.more_tetra_tools.waypoint_not_set").withStyle(ChatFormatting.RED));
                        }
                    }
                }
                if (!level.isClientSide() && gohomeLevel > 0) {
                    //没有准备buff
                    if(!player.hasEffect(MTTEffectsRegister.ReadyToTransfer.get()) ) {
                        player.addEffect(new MobEffectInstance(MTTEffectsRegister.ReadyToTransfer.get(), 20 * 5, 0));
                        player.getCooldowns().addCooldown(this, 20 * 1);//设置冷却时间
                    }else {
                        // 检查当前玩家对象是否是 ServerPlayer 类型
                        if (player instanceof ServerPlayer serverPlayer) {
                            // 获取玩家当前设置的复活点坐标 (BlockPos)【只有设置了才行
                            BlockPos respawnPos = serverPlayer.getRespawnPosition();
                            // 获取玩家当前复活点所在的维度 (ResourceKey<Level>)
                            ResourceKey<Level> respawnDim = serverPlayer.getRespawnDimension();
                            //
                            if (respawnPos != null && respawnDim != null) {
                                // 通过 MinecraftServer 实例获取目标维度的 ServerLevel 对象
                                ServerLevel targetLevel = serverPlayer.server.getLevel(respawnDim);
                                // 检查是否成功获取到目标维度的 ServerLevel 对象
                                if (targetLevel != null) {
                                    // 计算目标传送 X 坐标，加上 0.5 使其位于方块中心
                                    double targetX = respawnPos.getX() + 0.5;
                                    // 计算目标传送 Y 坐标，加上 1，让玩家传送到床的上方，避免卡住
                                    double targetY = respawnPos.getY() + 1 ;
                                    // 计算目标传送 Z 坐标，加上 0.5 使其位于方块中心
                                    double targetZ = respawnPos.getZ() + 0.5;
                                    // 获取玩家当前的 Y 轴旋转角度（朝向）
                                    float currentYaw = serverPlayer.getYRot();
                                    // 获取玩家当前的 X 轴旋转角度（俯仰角）
                                    float currentPitch = serverPlayer.getXRot();
                                    // 执行玩家的传送操作，传送到计算好的目标维度和坐标，并保持原有朝向
                                    serverPlayer.teleportTo(targetLevel, targetX, targetY, targetZ, currentYaw, currentPitch);
                                    // 在玩家原位置播放传送音效，用于给玩家视觉和听觉反馈
                                    player.level().playSound(null, player.xo, player.yo, player.zo, SoundEvents.CHORUS_FRUIT_TELEPORT, SoundSource.PLAYERS, 1.0F, 1.0F);
                                    // 移除玩家身上名为 "ReadyToTransfer" 的效果（假设是自定义效果）
                                    player.removeEffect(MTTEffectsRegister.ReadyToTransfer.get());
                                    // 给玩家设置一个冷却时间，以防止连续传送，20 刻度为 1 秒
                                    player.getCooldowns().addCooldown(this, 20 * 1);//设置冷却时间
                                }
                            }else{
                                // 如果玩家没有设置有效的复活点（respawnPos 或 respawnDim 为 null）
                                // 则传送到世界默认出生点
                                ServerLevel targetLevel = serverPlayer.server.getLevel(Level.OVERWORLD); // 获取主世界 ServerLevel
                                // 获取主世界的默认出生点坐标 (BlockPos)
                                BlockPos worldSpawnPos = targetLevel.getSharedSpawnPos();
                                // 设置传送坐标为世界默认出生点，并略微向上偏移
                                double targetX = worldSpawnPos.getX() + 0.5;
                                double targetY = worldSpawnPos.getY() + 1; // 向上偏移以避免卡住
                                double targetZ = worldSpawnPos.getZ() + 0.5;
                                // 获取玩家当前的 Y 轴旋转角度（朝向）
                                float currentYaw = serverPlayer.getYRot();
                                // 获取玩家当前的 X 轴旋转角度（俯仰角）
                                float currentPitch = serverPlayer.getXRot();
                                // 执行玩家的传送操作，传送到计算好的目标维度和坐标，并保持原有朝向
                                serverPlayer.teleportTo(targetLevel, targetX, targetY, targetZ, currentYaw, currentPitch);
                                // 在玩家原位置播放传送音效，用于给玩家视觉和听觉反馈
                                player.level().playSound(null, player.xo, player.yo, player.zo, SoundEvents.CHORUS_FRUIT_TELEPORT, SoundSource.PLAYERS, 1.0F, 1.0F);
                                // 移除玩家身上名为 "ReadyToTransfer" 的效果（假设是自定义效果）
                                player.removeEffect(MTTEffectsRegister.ReadyToTransfer.get());
                                // 给玩家设置一个冷却时间，以防止连续传送，20 刻度为 1 秒
                                player.getCooldowns().addCooldown(this, 20 * 1);//设置冷却时间
                            }
                        }
                    }
                }
                if(!level.isClientSide() && recallLevel>0){
                    var mobList = MTTUtil.mobList(51, player);
                    BlockPos playerOnPos = player.getOnPos();
                    double targetX = playerOnPos.getX() + 0.5;
                    double targetY = playerOnPos.getY() + 1; // 向上偏移以避免卡住
                    double targetZ = playerOnPos.getZ() + 0.5;
                    for (Mob mobs : mobList) {
                        //有主人的随从
                        if ( (mobs instanceof OwnableEntity ownableEntity && ownableEntity.getOwnerUUID() != null) ) {
                            mobs.teleportTo(targetX, targetY, targetZ);
                        }
                    }
                    player.getCooldowns().addCooldown(this, 20 * 1);//设置冷却时间
                }
                int boostLevel = item.getEffectLevel(mainHandItem, boost_Effect);
                if(boostLevel>0){
                    int currentDamage = mainHandItem.getDamageValue();
                    if(currentDamage<=mainHandItem.getMaxDamage()){
                        if(level.isClientSide()){
                        // 玩家当前观察的方向向量
                        Vec3 lookVec = player.getLookAngle();
                        // 玩家当前位置的移动速度向量
                        Vec3 moveVec = player.getDeltaMovement();
                        // 垂直向上的偏移量，用于增加一定的跳跃感
                        Vec3 yBonus = new Vec3(0f, 0.5f, 0f);
                        // 乘以2的正向
                        player.setDeltaMovement(moveVec.add(lookVec.scale(2.0)).add(yBonus));
                        }
                        player.hasImpulse = true;
                        // 清除玩家掉落距离（避免飞行中的伤害）
                        player.fallDistance = 0;
                        mainHandItem.setDamageValue(Math.min(mainHandItem.getMaxDamage(), currentDamage+1));
                    }
                }
            }
        }
        else if (pUsedHand == InteractionHand.OFF_HAND) {
            if (player.getOffhandItem().getItem() instanceof IModularItem item) {
                //获取词条等级
                int waypointLevel = item.getEffectLevel(player.getOffhandItem(), waypointEffect);
                int gohomeLevel = item.getEffectLevel(player.getOffhandItem(), gohomeEffect);
                if (!level.isClientSide() && waypointLevel > 0) {
                    //没有准备buff
                    if(!player.hasEffect(MTTEffectsRegister.ReadyToTransfer.get()) ) {
                        player.addEffect(new MobEffectInstance(MTTEffectsRegister.ReadyToTransfer.get(), 20 * 5, 0));
                        player.getCooldowns().addCooldown(this, 20 * 1);//设置冷却时间
                    }else{
                        //有buff
                        CompoundTag tag = player.getOffhandItem().getOrCreateTag(); // 获取物品的NBT标签
                        // 检查是否已绑定（即 NBT 中是否包含锚点坐标和维度）
                        boolean hasWaypoint = tag.contains(X.toString()) && tag.contains(Y.toString()) &&
                                tag.contains(Z.toString()) && tag.contains(WORLD.toString());
                        if (hasWaypoint&&player instanceof ServerPlayer serverPlayer&&!player.isShiftKeyDown() ) {
                            // 从 NBT 读取坐标和维度
                            int savedX = tag.getInt(X.toString());
                            int savedY = tag.getInt(Y.toString());
                            int savedZ = tag.getInt(Z.toString());
                            String savedDimensionPath = tag.getString(WORLD.toString());
                            // 将字符串维度路径转换为 Dimension.ResourceKey
                            ResourceLocation dimensionResourceLocation = new ResourceLocation(savedDimensionPath);
                            ServerLevel targetLevel = null;
                            if (level.getServer() != null) { // 确保 server 不为 null
                                targetLevel = level.getServer().getLevel(net.minecraft.resources.ResourceKey.create(Registries.DIMENSION, dimensionResourceLocation));
                            }
                            if (targetLevel != null) {
                                // 调整传送的目标 Y 坐标，使其位于方块中心上方
                                double teleportY = savedY + 1;
                                // 使用 teleportTo 方法，该方法允许在不同维度传送，并处理了其他细节
                                serverPlayer.teleportTo(targetLevel, savedX + 0.5, teleportY, savedZ + 0.5, player.getYRot(), player.getXRot());
                                player.level().playSound(null, player.xo, player.yo, player.zo, SoundEvents.CHORUS_FRUIT_TELEPORT, SoundSource.PLAYERS, 1.0F, 1.0F);
                                player.removeEffect(MTTEffectsRegister.ReadyToTransfer.get());
                                player.getCooldowns().addCooldown(this, 20 * 1);//设置冷却时间
                            }
                        }else {
                            player.sendSystemMessage(Component.translatable("tooltip.more_tetra_tools.waypoint_not_set").withStyle(ChatFormatting.RED));
                        }
                    }
                }
                if (!level.isClientSide() && gohomeLevel > 0) {
                    //没有准备buff
                    if(!player.hasEffect(MTTEffectsRegister.ReadyToTransfer.get()) ) {
                        player.addEffect(new MobEffectInstance(MTTEffectsRegister.ReadyToTransfer.get(), 20 * 5, 0));
                        player.getCooldowns().addCooldown(this, 20 * 1);//设置冷却时间
                    }else {
                        // 检查当前玩家对象是否是 ServerPlayer 类型
                        if (player instanceof ServerPlayer serverPlayer) {
                            // 获取玩家当前设置的复活点坐标 (BlockPos)【只有设置了才行
                            BlockPos respawnPos = serverPlayer.getRespawnPosition();
                            // 获取玩家当前复活点所在的维度 (ResourceKey<Level>)
                            ResourceKey<Level> respawnDim = serverPlayer.getRespawnDimension();
                            //
                            if (respawnPos != null && respawnDim != null) {
                                // 通过 MinecraftServer 实例获取目标维度的 ServerLevel 对象
                                ServerLevel targetLevel = serverPlayer.server.getLevel(respawnDim);
                                // 检查是否成功获取到目标维度的 ServerLevel 对象
                                if (targetLevel != null) {
                                    // 计算目标传送 X 坐标，加上 0.5 使其位于方块中心
                                    double targetX = respawnPos.getX() + 0.5;
                                    // 计算目标传送 Y 坐标，加上 1，让玩家传送到床的上方，避免卡住
                                    double targetY = respawnPos.getY() + 1 ;
                                    // 计算目标传送 Z 坐标，加上 0.5 使其位于方块中心
                                    double targetZ = respawnPos.getZ() + 0.5;
                                    // 获取玩家当前的 Y 轴旋转角度（朝向）
                                    float currentYaw = serverPlayer.getYRot();
                                    // 获取玩家当前的 X 轴旋转角度（俯仰角）
                                    float currentPitch = serverPlayer.getXRot();
                                    // 执行玩家的传送操作，传送到计算好的目标维度和坐标，并保持原有朝向
                                    serverPlayer.teleportTo(targetLevel, targetX, targetY, targetZ, currentYaw, currentPitch);
                                    // 在玩家原位置播放传送音效，用于给玩家视觉和听觉反馈
                                    player.level().playSound(null, player.xo, player.yo, player.zo, SoundEvents.CHORUS_FRUIT_TELEPORT, SoundSource.PLAYERS, 1.0F, 1.0F);
                                    // 移除玩家身上名为 "ReadyToTransfer" 的效果（假设是自定义效果）
                                    player.removeEffect(MTTEffectsRegister.ReadyToTransfer.get());
                                    // 给玩家设置一个冷却时间，以防止连续传送，20 刻度为 1 秒
                                    player.getCooldowns().addCooldown(this, 20 * 1);//设置冷却时间
                                }
                            }else{
                                // 如果玩家没有设置有效的复活点（respawnPos 或 respawnDim 为 null）
                                // 则传送到世界默认出生点
                                ServerLevel targetLevel = serverPlayer.server.getLevel(Level.OVERWORLD); // 获取主世界 ServerLevel
                                // 获取主世界的默认出生点坐标 (BlockPos)
                                BlockPos worldSpawnPos = targetLevel.getSharedSpawnPos();
                                // 设置传送坐标为世界默认出生点，并略微向上偏移
                                double targetX = worldSpawnPos.getX() + 0.5;
                                double targetY = worldSpawnPos.getY() + 1; // 向上偏移以避免卡住
                                double targetZ = worldSpawnPos.getZ() + 0.5;
                                // 获取玩家当前的 Y 轴旋转角度（朝向）
                                float currentYaw = serverPlayer.getYRot();
                                // 获取玩家当前的 X 轴旋转角度（俯仰角）
                                float currentPitch = serverPlayer.getXRot();
                                // 执行玩家的传送操作，传送到计算好的目标维度和坐标，并保持原有朝向
                                serverPlayer.teleportTo(targetLevel, targetX, targetY, targetZ, currentYaw, currentPitch);
                                // 在玩家原位置播放传送音效，用于给玩家视觉和听觉反馈
                                player.level().playSound(null, player.xo, player.yo, player.zo, SoundEvents.CHORUS_FRUIT_TELEPORT, SoundSource.PLAYERS, 1.0F, 1.0F);
                                // 移除玩家身上名为 "ReadyToTransfer" 的效果（假设是自定义效果）
                                player.removeEffect(MTTEffectsRegister.ReadyToTransfer.get());
                                // 给玩家设置一个冷却时间，以防止连续传送，20 刻度为 1 秒
                                player.getCooldowns().addCooldown(this, 20 * 1);//设置冷却时间
                            }
                        }
                    }
                }
                int boostLevel = item.getEffectLevel(player.getOffhandItem(), boost_Effect);
                if(boostLevel>0){
                    int currentDamage = player.getOffhandItem().getDamageValue();
                    if(currentDamage<=player.getOffhandItem().getMaxDamage()){
                        if(level.isClientSide()) {
                            // 玩家当前观察的方向向量
                            Vec3 lookVec = player.getLookAngle();
                            // 玩家当前位置的移动速度向量
                            Vec3 moveVec = player.getDeltaMovement();
                            // 垂直向上的偏移量，用于增加一定的跳跃感
                            Vec3 yBonus = new Vec3(0f, 0.5f, 0f);
                            // 乘以2的正向
                            player.setDeltaMovement(moveVec.add(lookVec.scale(2.0)).add(yBonus));
                        }
                        player.hasImpulse = true;
                        // 清除玩家掉落距离（避免飞行中的伤害）
                        player.fallDistance = 0;
                        player.getOffhandItem().setDamageValue(Math.min(player.getOffhandItem().getMaxDamage(), currentDamage+1));
                    }
                }
            }
        }
        return super.use(level, player, pUsedHand);
    }

    @SubscribeEvent
    public static void tooltip(ItemTooltipEvent event) {
        ItemStack itemStack = event.getItemStack();
        if (itemStack.getItem() instanceof IModularItem item) {
            float waypointLevel = item.getEffectLevel(itemStack, waypointEffect);
            float captureLevel = item.getEffectLevel(itemStack, captureEffect);
            if (waypointLevel > 0) {
                CompoundTag tag = itemStack.getOrCreateTag(); // 获取物品的NBT标签
                // 检查是否已绑定（即 NBT 中是否包含锚点坐标）
                // 假设所有坐标和维度都必须存在才算“已绑定”
                boolean hasWaypoint = tag.contains(X.toString()) && tag.contains(Y.toString()) && tag.contains(Z.toString()) && tag.contains(WORLD.toString());
                if (hasWaypoint) {
                    // 已绑定：显示锚点坐标和维度
                    int x = tag.getInt(X.toString());
                    int y = tag.getInt(Y.toString());
                    int z = tag.getInt(Z.toString());
                    String dimensionPath = tag.getString(WORLD.toString());
                    event.getToolTip().add(Component.translatable("tooltip.more_tetra_tools.set_waypoint", x, y, z, dimensionPath).withStyle(ChatFormatting.LIGHT_PURPLE) // 使用浅紫色
                    );
                } else {
                    event.getToolTip().add(
                            Component.translatable("tooltip.more_tetra_tools.waypoint_not_set").withStyle(ChatFormatting.GRAY) // 使用灰色
                    );
                }
            }
            if(captureLevel>0){
                if(event.getEntity().level() != null && itemStack.hasTag() && itemStack.getTag().contains(KEY_ENTITY)) {
                    CompoundTag entityTag = itemStack.getTag().getCompound(KEY_ENTITY); // 获取存储的实体 NBT。
                    Optional<Entity> entityOpt = EntityType.create(entityTag, event.getEntity().level()); // 尝试创建实体对象。
                    entityOpt.ifPresent(entity -> // 如果创建成功，则显示生物的显示名称。
                    event.getToolTip().add(
                            Component.translatable("tooltip.more_tetra_tools.has_entity", entity.getDisplayName() ).withStyle(ChatFormatting.GOLD))
                    ); // 使用灰色
                }else{
                    event.getToolTip().add(
                            Component.translatable("tooltip.more_tetra_tools.no_entity").withStyle(ChatFormatting.GRAY) // 使用灰色
                    );
                }
            }
        }
    }
}