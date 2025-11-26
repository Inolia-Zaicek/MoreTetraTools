package com.inolia_zaicek.more_tetra_tools.network.Packet;


import com.inolia_zaicek.more_tetra_tools.entity.MTTMusketArrowEntity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class MTTMusketPacket {
    private double baseDamage;//基础伤害
    private int knockback;//基础击退距离
    private int fireSeconds; // 箭矢着火的秒数 (0 表示不着火)
    private byte pierceLevel;//
    private float arrowSpeed;
    private float arrowInaccuracy;
    // 【新增构造函数】用于在客户端创建包时传递箭矢属性数据
    public MTTMusketPacket(double baseDamage, int knockback, int fireSeconds, byte pierceLevel, float arrowSpeed, float arrowInaccuracy) {
        this.baseDamage = baseDamage;
        this.knockback = knockback;
        this.fireSeconds = fireSeconds;
        this.pierceLevel = pierceLevel;
        this.arrowSpeed = arrowSpeed;
        this.arrowInaccuracy = arrowInaccuracy;
    }
    // 【Forge SimpleChannel 需要】必须有一个无参构造函数，供解码时使用
    public MTTMusketPacket() {
        // 初始化默认值，或者留空，由 decode 方法填充
    }
    public static void encode(MTTMusketPacket packet, FriendlyByteBuf buf) {
        // 【重要】写入数据的顺序必须与 decode 读取数据的顺序一致！
        buf.writeDouble(packet.baseDamage);
        buf.writeInt(packet.knockback);
        buf.writeInt(packet.fireSeconds);
        buf.writeByte(packet.pierceLevel);
        buf.writeFloat(packet.arrowSpeed);
        buf.writeFloat(packet.arrowInaccuracy);
        // 如果将来添加更多字段，在这里和 decode 中都要同步添加
    }

    // 【Forge SimpleChannel 需要】从 FriendlyByteBuf 读取数据创建包 (在服务器端执行)
    public static MTTMusketPacket decode(FriendlyByteBuf buf) {
        // 创建一个新的 MusketPacket 实例
        MTTMusketPacket packet = new MTTMusketPacket();

        // 【重要】读取数据的顺序必须与 encode 写入数据的顺序一致！
        packet.baseDamage = buf.readDouble();
        packet.knockback = buf.readInt();
        packet.fireSeconds = buf.readInt();
        packet.pierceLevel = buf.readByte();
        packet.arrowSpeed = buf.readFloat();
        packet.arrowInaccuracy = buf.readFloat();
        // 读取完所有数据后，返回填充好的包对象
        return packet;

        // 注意：你之前的 Botania 检测不应该放在这里。Decode 方法的职责就是纯粹地从字节流中重建包对象。
        // 任何逻辑判断（比如是否加载了某个mod）应该放在处理方法 handle 中。
        // if(ModList.get().isLoaded("botania")) {
        //     return new MusketPacket(); // 这段逻辑是错误的，无法正确解码数据
        // }
        // return null; // 返回 null 通常不是 SimpleChannel 期望的，可能会导致问题
    }
    //新建nbt
    public static boolean handle(MTTMusketPacket msg, Supplier<NetworkEvent.Context> ctxSupplier) {
        //获取上下文
        var context = ctxSupplier.get();
        context.enqueueWork(() -> {
            //要做什么
            var player = context.getSender();
            Level level = player.level();
            Item item =player.getMainHandItem().getItem();
            ItemStack mainHandItem = player.getMainHandItem();
            // 1. 创建箭矢实体
            Arrow arrow = new MTTMusketArrowEntity(level, player);
            // 2. 获取玩家的朝向向量 (使用服务器端玩家的朝向)
            Vec3 lookVec = player.getLookAngle();
            arrow.shoot(lookVec.x, lookVec.y, lookVec.z, msg.arrowSpeed, msg.arrowInaccuracy);
            // 计算箭矢的基础伤害，并加上燧发枪的PROJECTILE_DAMAGE统计数据（该数据已受修改器影响）
            float baseArrowDamage = (float) msg.baseDamage;
            // 应用燧发枪的PROJECTILE_DAMAGE统计数据（再次经过条件统计修改器钩子处理），最终设定箭矢伤害
            arrow.setBaseDamage(baseArrowDamage/3.5);
            // *** 修改箭矢的击退效果 ***
            arrow.setKnockback(msg.knockback);
            // *** 修改箭矢的穿透等级 ***【实际上这玩意好像没用】
            if (msg.pierceLevel > 0) {
                arrow.setPierceLevel(msg.pierceLevel); // 注意是 byte 类型
            }
            // *** 让箭矢着火 ***
            if (msg.fireSeconds > 0) {
                arrow.setSecondsOnFire(msg.fireSeconds);
            }
            //设置箭矢为暴击箭矢（但是会有拖尾特效）
            //arrow.setCritArrow(true);

            //箭矢不受重力影响
            arrow.setNoGravity(true);
            // 5. 将配置好的箭矢实体添加到世界中 (服务器世界)
            level.addFreshEntity(arrow);
            // 6. 播放射箭音效 (在服务器端播放，会自动同步给附近的客户端)
            level.playSound(
                    null, // 玩家参数为 null 表示音效来自世界，不绑定到特定玩家
                    player.getX(),
                    player.getY(),
                    player.getZ(),
                    SoundEvents.ARROW_SHOOT, // 箭矢射出的音效
                    SoundSource.PLAYERS, // 音效来源类别，这里是玩家动作
                    1.0F, // 音量 (1.0f 是标准音量)
                    1.0F / (level.getRandom().nextFloat() * 0.4F + 1.2F) + msg.arrowSpeed * 0.5F // 音高，使用包里的速度
            );
            // 奖励玩家使用物品的统计数据
            player.awardStat(Stats.ITEM_USED.get(item));
        });
        //标记包已处理完成
        context.setPacketHandled(true);
        return true;
    }

}