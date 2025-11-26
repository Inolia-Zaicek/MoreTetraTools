package com.inolia_zaicek.more_tetra_tools.entity;

import com.inolia_zaicek.more_tetra_tools.MoreTetraTools;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

// 例如：com.inolia_zaicek.zeroing_story.entity.MyMusketArrowEntity.java
public class MTTMusketArrowEntity extends Arrow {
    //新建nbt
    public static ResourceLocation musketnbt = MoreTetraTools.getResource("musket");
    // 定义一个 DataKey 用于在 ModDataNBT 中存储原始工具的 NBT
    public static final String TAG_TOOL_STACK_NBT_KEY = "zeroing_story:tool_stack_nbt"; // 建议加上 Mod ID 作为前缀以避免冲突

    // 需要一个公共的无参构造函数用于注册EntityType的Factory
    public MTTMusketArrowEntity(EntityType<? extends Arrow> arrow, Level level) {
        super(arrow, level);
    }

    public MTTMusketArrowEntity(Level pLevel, LivingEntity pShooter) {
        super(pLevel, pShooter);
        // 可以在这里设置一些默认的箭矢属性，或者在发射时设置
    }
    //     * 当箭矢击中实体时调用。
    @Override
    protected void onHitEntity(EntityHitResult result) {
        // 先调用父类方法处理基础伤害、击退等
        super.onHitEntity(result);
        if(result.getEntity() instanceof LivingEntity livingEntity){
            livingEntity.invulnerableTime = 0;
        }
        if (!this.level().isClientSide) { // 确保只在服务器端移除实体
            this.discard();
        }
    }
    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result); // 调用父类方法，使箭矢能够插入方块，并可能播放音效

        // 对于燧发枪的抛射物，通常不希望它像普通箭矢一样插入方块并停留很长时间
        // 而是希望它命中方块后立即消失。

        // 关键：命中方块后立即消失
        if (!this.level().isClientSide) { // 确保只在服务器端移除实体
            this.discard();
        }
    }
    // 覆盖此方法以返回你的自定义EntityType
    @Override
    public EntityType<?> getType() {
        return MTTModEntities.MY_MUSKET_ARROW.get(); // 假设你注册了YourModEntities类
    }

    // 其他箭矢相关的逻辑，例如伤害、命中等
}
