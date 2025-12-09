package com.inolia_zaicek.more_tetra_tools.Effect.BehemothSword;

import com.inolia_zaicek.more_tetra_tools.Damage.MTTTickZero;
import com.inolia_zaicek.more_tetra_tools.MoreTetraTools;
import com.inolia_zaicek.more_tetra_tools.Register.MTTEffectsRegister;
import com.inolia_zaicek.more_tetra_tools.Util.MTTDamageSourceHelper;
import com.inolia_zaicek.more_tetra_tools.Util.MTTEffectHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
import java.util.Random;

import static com.inolia_zaicek.more_tetra_tools.Effect.Clent.MTTEffectGuiStats.*;
import static com.inolia_zaicek.more_tetra_tools.Effect.Clent.MTTEffectGuiStats.chokuhi_kyoshin_joseishu_Effect;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE,modid = MoreTetraTools.MODID)
public class StarSpawnOfCthulhu {
    private static final ResourceLocation star_spawn_of_cthulhu_tick = new ResourceLocation(MoreTetraTools.MODID, "star_spawn_of_cthulhu");
    @SubscribeEvent
    public static void hurt(LivingHurtEvent event) {
        if(event.getEntity()!=null){
            LivingEntity livingEntity = event.getEntity();
            ItemStack mainHandItem = livingEntity.getMainHandItem();
            float effectEfficiency2 = (MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectEfficiency(livingEntity, star_spawn_of_cthulhu_Effect));
            //获取工具nbt数值
            CompoundTag persistentData = mainHandItem.getOrCreateTag();
            //不存在就是0
            int distortionValue =persistentData.getInt(String.valueOf(star_spawn_of_cthulhu_tick));
            if (effectEfficiency2 > 0) {
                persistentData.putInt(String.valueOf(star_spawn_of_cthulhu_tick), Math.max(0,distortionValue-5) );
            }
        }
        //扭曲
        if (event.getSource().getEntity() instanceof LivingEntity livingEntity) {
            ItemStack mainHandItem = livingEntity.getMainHandItem();
            float effectEfficiency2 = (MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectEfficiency(livingEntity, star_spawn_of_cthulhu_Effect));
            //获取工具nbt数值
            CompoundTag persistentData = mainHandItem.getOrCreateTag();
            //不存在就是0
            int distortionValue =persistentData.getInt(String.valueOf(star_spawn_of_cthulhu_tick));
            if (effectEfficiency2 > 0) {
                float finish = (distortionValue/0.8F)/200+0.5F;
                float number = effectEfficiency2/100;
                event.setAmount(event.getAmount()*finish*number);
            }
        }else if (event.getSource().getDirectEntity() instanceof LivingEntity livingEntity) {
            ItemStack mainHandItem = livingEntity.getMainHandItem();
            float effectEfficiency2 = (MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectEfficiency(livingEntity, star_spawn_of_cthulhu_Effect));
            //获取工具nbt数值
            CompoundTag persistentData = mainHandItem.getOrCreateTag();
            //不存在就是0
            int distortionValue =persistentData.getInt(String.valueOf(star_spawn_of_cthulhu_tick));
            if (effectEfficiency2 > 0) {
                float finish = (distortionValue/0.8F)/200+0.5F;
                float number = effectEfficiency2/100;
                event.setAmount(event.getAmount()*finish*number);
            }
        }
    }
    @SubscribeEvent
    public static void tick(LivingEvent.LivingTickEvent event) {
        LivingEntity player = event.getEntity();
        ItemStack mainHandItem = player.getMainHandItem();
        float effectEfficiency2 = (MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectEfficiency(player, star_spawn_of_cthulhu_Effect));
        if (effectEfficiency2 > 0 && player.level().getGameTime() % 200 == 0) {
            //获取工具nbt数值
            CompoundTag persistentData = mainHandItem.getOrCreateTag();
            //不存在就是0
            int distortionValue = persistentData.getInt(String.valueOf(star_spawn_of_cthulhu_tick));
            persistentData.putInt(String.valueOf(star_spawn_of_cthulhu_tick), Math.min(100, distortionValue + 1));
        }
    }
    @SubscribeEvent
    public static void entityKilled(LivingDeathEvent event) {
        if (event.getSource().getEntity() instanceof LivingEntity livingEntity) {
            float effectEfficiency2 = (MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectEfficiency(livingEntity, star_spawn_of_cthulhu_Effect));
            if (effectEfficiency2 > 0) {
                ItemStack mainHandItem = livingEntity.getMainHandItem();
                CompoundTag persistentData = mainHandItem.getOrCreateTag();
                int distortionValue = persistentData.getInt(String.valueOf(star_spawn_of_cthulhu_tick));
                persistentData.putInt(String.valueOf(star_spawn_of_cthulhu_tick), Math.min(100, distortionValue + 3));
            }
        }else if (event.getSource().getDirectEntity() instanceof LivingEntity livingEntity) {
            float effectEfficiency2 = (MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectEfficiency(livingEntity, star_spawn_of_cthulhu_Effect));
            if (effectEfficiency2 > 0) {
                ItemStack mainHandItem = livingEntity.getMainHandItem();
                CompoundTag persistentData = mainHandItem.getOrCreateTag();
                int distortionValue = persistentData.getInt(String.valueOf(star_spawn_of_cthulhu_tick));
                persistentData.putInt(String.valueOf(star_spawn_of_cthulhu_tick), Math.min(100, distortionValue + 3));
            }
        }
    }
}
