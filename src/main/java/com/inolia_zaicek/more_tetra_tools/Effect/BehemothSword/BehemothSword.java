package com.inolia_zaicek.more_tetra_tools.Effect.BehemothSword;

import com.inolia_zaicek.more_tetra_tools.Damage.MTTTickZero;
import com.inolia_zaicek.more_tetra_tools.MoreTetraTools;
import com.inolia_zaicek.more_tetra_tools.Util.MTTDamageSourceHelper;
import com.inolia_zaicek.more_tetra_tools.Util.MTTEffectHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import se.mickelus.tetra.blocks.workbench.gui.WorkbenchStatsGui;
import se.mickelus.tetra.gui.stats.StatsHelper;
import se.mickelus.tetra.gui.stats.bar.GuiStatBar;
import se.mickelus.tetra.gui.stats.getter.*;
import se.mickelus.tetra.items.modular.impl.holo.gui.craft.HoloStatsGui;

import java.util.List;
import java.util.Random;

import static com.inolia_zaicek.more_tetra_tools.Effect.Clent.MTTEffectGuiStats.*;

public class BehemothSword {
    @OnlyIn(Dist.CLIENT)
    public static void init() {
        var statGetter1 = new StatGetterEffectLevel(painful_scream_Effect, 1);
        var statGetter2 = new StatGetterEffectEfficiency(painful_scream_Effect, 1);
        IStatGetter[] statGetters = {statGetter1, statGetter2};
        IStatFormat[] statFormats = {StatFormat.noDecimal, StatFormat.noDecimal};
        GuiStatBar statBar1 = new GuiStatBar(0, 0, StatsHelper.barLength,
                painful_scream_Name, 0, 10, false, false, false,
                statGetter1, LabelGetterBasic.integerLabel,
                new TooltipGetterMultiValue(painful_scream_Tooltip, statGetters, statFormats)
        );
        WorkbenchStatsGui.addBar(statBar1);
        HoloStatsGui.addBar(statBar1);
        var statGetter3 = new StatGetterEffectLevel(star_spawn_of_cthulhu_Effect, 1);
        var statGetter4 = new StatGetterEffectEfficiency(star_spawn_of_cthulhu_Effect, 1);
        IStatGetter[] statGetters1 = {statGetter3, statGetter4};
        IStatFormat[] statFormats1 = {StatFormat.noDecimal, StatFormat.noDecimal};
        GuiStatBar statBar2 = new GuiStatBar(0, 0, StatsHelper.barLength,
                star_spawn_of_cthulhu_Name, 0, 100, false, false, false,
                statGetter3, LabelGetterBasic.integerLabel,
                new TooltipGetterMultiValue(star_spawn_of_cthulhu_Tooltip, statGetters1, statFormats1)
        );
        WorkbenchStatsGui.addBar(statBar2);
        HoloStatsGui.addBar(statBar2);
        var statGetter5 = new StatGetterEffectLevel(light_of_thie_vanish_dawn_Effect, 1);
        var statGetter6 = new StatGetterEffectEfficiency(light_of_thie_vanish_dawn_Effect, 1);
        IStatGetter[] statGetters2 = {statGetter5, statGetter6};
        IStatFormat[] statFormats2 = {StatFormat.noDecimal, StatFormat.noDecimal};
        GuiStatBar statBar3 = new GuiStatBar(0, 0, StatsHelper.barLength,
                light_of_thie_vanish_dawn_Name, 0, 100, false, false, false,
                statGetter5, LabelGetterBasic.integerLabel,
                new TooltipGetterMultiValue(light_of_thie_vanish_dawn_Tooltip, statGetters2, statFormats2)
        );
        WorkbenchStatsGui.addBar(statBar3);
        HoloStatsGui.addBar(statBar3);
        var statGetter7 = new StatGetterEffectLevel(flares_of_the_blazing_sun_Effect, 1);
        var statGetter8 = new StatGetterEffectEfficiency(flares_of_the_blazing_sun_Effect, 1);
        IStatGetter[] statGetters3 = {statGetter7, statGetter8};
        IStatFormat[] statFormats3 = {StatFormat.noDecimal, StatFormat.noDecimal};
        GuiStatBar statBar4 = new GuiStatBar(0, 0, StatsHelper.barLength,
                flares_of_the_blazing_sun_Name, 0, 100, false, false, false,
                statGetter7, LabelGetterBasic.integerLabel,
                new TooltipGetterMultiValue(flares_of_the_blazing_sun_Tooltip, statGetters3, statFormats3)
        );
        WorkbenchStatsGui.addBar(statBar4);
        HoloStatsGui.addBar(statBar4);
        var statGetter9 = new StatGetterEffectLevel(incinerat_break_Effect, 1);
        var statGetter10 = new StatGetterEffectEfficiency(incinerat_break_Effect, 1);
        IStatGetter[] statGetter15 = {statGetter9, statGetter10};
        IStatFormat[] statFormats15 = {StatFormat.noDecimal, StatFormat.noDecimal};
        GuiStatBar statBar5 = new GuiStatBar(0, 0, StatsHelper.barLength,
                incinerat_break_Name, 0, 100, false, false, false,
                statGetter9, LabelGetterBasic.integerLabel,
                new TooltipGetterMultiValue(incinerat_break_Tooltip, statGetter15, statFormats15)
        );
        WorkbenchStatsGui.addBar(statBar5);
        HoloStatsGui.addBar(statBar5);
        var statGetter11 = new StatGetterEffectLevel(incinerat_burst_Effect, 1);
        var statGetter12 = new StatGetterEffectEfficiency(incinerat_burst_Effect, 1);
        IStatGetter[] statGetters16 = {statGetter11, statGetter12};
        IStatFormat[] statFormats16 = {StatFormat.noDecimal, StatFormat.noDecimal};
        GuiStatBar statBar6 = new GuiStatBar(0, 0, StatsHelper.barLength,
                incinerat_burst_Name, 0, 70, false, false, false,
                statGetter11, LabelGetterBasic.integerLabel,
                new TooltipGetterMultiValue(incinerat_burst_Tooltip, statGetters16, statFormats16)
        );
        WorkbenchStatsGui.addBar(statBar6);
        HoloStatsGui.addBar(statBar6);
    }
    @SubscribeEvent
    public static void hurt(LivingHurtEvent event) {
        Random random = new Random();
        if (event.getSource().getEntity() instanceof LivingEntity livingEntity&& MTTDamageSourceHelper.isMeleeAttack(event.getSource()) ) {
            //音爆
            float effectLevel = (MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectLevel(livingEntity, painful_scream_Effect));
            float effectEfficiency = (MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectEfficiency(livingEntity, painful_scream_Effect));
            if (effectLevel > 0 &&random.nextInt(101) <= effectLevel) {
                float baseDamage = (float) livingEntity.getAttributeValue(Attributes.ATTACK_DAMAGE) * effectEfficiency / 100;
                float range = 10;
                if(baseDamage>0) {
                    // 方向：livingEntity的朝向
                    double yaw = livingEntity.getYHeadRot(); // 获取实体头部朝向角度
                    double radians = Math.toRadians(yaw);
                    double dx = -Math.sin(radians);
                    double dz = Math.cos(radians);
                    // 调用生成监守者的音波
                    spawnSonicBoom(livingEntity.level(), livingEntity, baseDamage, range, dx, dz);
                }
            }
            //法转
            var mob = event.getEntity();
            float effectLevel2 = (MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectLevel(livingEntity, star_spawn_of_cthulhu_Effect));
            float effectEfficiency2 = (MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectEfficiency(livingEntity, star_spawn_of_cthulhu_Effect));
            if (effectLevel2 > 0&&MTTDamageSourceHelper.isMeleeAttack(event.getSource())) {
                float number = (float) effectLevel2 / 100;
                float damage = event.getAmount();
                event.setAmount(Math.max(0, damage * (1 - number)));
                mob.invulnerableTime = 0;
                if (livingEntity instanceof Player player1) {
                    mob.setLastHurtByPlayer(player1);
                }
                var DamageType = MTTTickZero.hasSource(livingEntity.level(), DamageTypes.MAGIC, livingEntity);
                mob.hurt(DamageType, damage * number);
            }
        }
    }
    // 定义生成监守者音爆的方法
    private static void spawnSonicBoom(Level level, LivingEntity originEntity, float damage, float range, double dirX, double dirZ) {
        // 播放音效（可选）
        level.playSound(null, originEntity.getX(), originEntity.getY(), originEntity.getZ(), SoundEvents.WARDEN_SONIC_BOOM, SoundSource.PLAYERS, 1.0f, 1.0f);

        // 计算起点位置（略微向前偏移）
        Vec3 startPos = originEntity.position().add(0, originEntity.getBbHeight() / 2.0, 0);

        // 控制音波长度
        int steps = (int) range; // 根据范围决定步数

        for (int i = 1; i <= steps; i++) {
            // 计算当前位置
            double offsetX = dirX * i;
            double offsetZ = dirZ * i;
            double spawnX = startPos.x + offsetX;
            double spawnY = startPos.y;
            double spawnZ = startPos.z + offsetZ;

            Vec3 particlePos = new Vec3(spawnX, spawnY, spawnZ);

            // 发送粒子效果
            ((ServerLevel) level).sendParticles(ParticleTypes.SONIC_BOOM, particlePos.x, particlePos.y, particlePos.z, 1, 0, 0, 0, 0);

            // 造型范围内的实体伤害
            double radius = 1.5; // 伤害范围（可以调整）
            List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class,
                    new AABB(new BlockPos((int) spawnX, (int) spawnY, (int) spawnZ)).inflate(radius),
                    it -> it != originEntity && !(it instanceof net.minecraft.world.entity.animal.Wolf));

            for (LivingEntity target : entities) {
                target.hurt(level.damageSources().sonicBoom(originEntity), damage);
            }
        }
    }
}
