package com.inolia_zaicek.more_tetra_tools.Effect;

import com.inolia_zaicek.more_tetra_tools.Util.MTTEffectHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.Optional;
import java.util.UUID;

import static com.inolia_zaicek.more_tetra_tools.Effect.Clent.MTTEffectGuiStats.*;

public class RadianceSword {
    private static final UUID uuid = UUID.fromString("F87BC32A-13CC-12A8-16AA-1372AE364623");
    @SubscribeEvent
    public static void hurt(LivingHurtEvent event) {
        LivingEntity mob = event.getEntity();
        var map = mob.getActiveEffectsMap();
        //光
        if (event.getSource().getEntity() instanceof LivingEntity livingEntity) {
            float effectLevel1 = (MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectLevel(livingEntity, radiance_Effect));
            float effectLevel2 = (MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectLevel(livingEntity, hikari_Effect));
            Level level = livingEntity.level();
            BlockPos pos = livingEntity.blockPosition();
            int brightness = level.getMaxLocalRawBrightness(pos);
            if (effectLevel1>0 && (brightness>=8||livingEntity.hasEffect(MobEffects.GLOWING))) {
                mob.addEffect(new MobEffectInstance(MobEffects.GLOWING, 200, 0));
                if (!mob.hasEffect(MobEffects.GLOWING)) {
                    map.put(MobEffects.GLOWING, new MobEffectInstance(MobEffects.GLOWING, 200, 0));
                }
                event.setAmount(event.getAmount()*(1+effectLevel1/100));
            }
            if (effectLevel2>0 && (brightness<=8||livingEntity.hasEffect(MobEffects.DARKNESS))) {
                Optional.of(event.getEntity())
                        .map(LivingEntity::getAttributes)
                        .filter(manager -> manager.hasAttribute(Attributes.ARMOR))
                        .map(manager -> manager.getInstance(Attributes.ARMOR))
                        .filter(instance -> instance.getModifier(uuid) == null)
                        .ifPresent(instance -> instance.addTransientModifier(
                                new AttributeModifier(uuid, "modular_radiance_sword", -(effectLevel2/100), AttributeModifier.Operation.MULTIPLY_TOTAL)));
                mob.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 200, 0));
                if (!mob.hasEffect(MobEffects.DARKNESS)) {
                    map.put(MobEffects.DARKNESS, new MobEffectInstance(MobEffects.DARKNESS, 200, 0));
                }
            }
        }else if (event.getSource().getDirectEntity() instanceof LivingEntity livingEntity) {
            float effectLevel1 = (MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectLevel(livingEntity, radiance_Effect));
            float effectLevel2 = (MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectLevel(livingEntity, hikari_Effect));
            Level level = livingEntity.level();
            BlockPos pos = livingEntity.blockPosition();
            int brightness = level.getMaxLocalRawBrightness(pos);
            if (effectLevel1>0 && (brightness>=8||livingEntity.hasEffect(MobEffects.GLOWING))) {
                event.setAmount(event.getAmount()*(1+effectLevel1/100));
                mob.addEffect(new MobEffectInstance(MobEffects.GLOWING, 200, 0));
                if (!mob.hasEffect(MobEffects.GLOWING)) {
                    map.put(MobEffects.GLOWING, new MobEffectInstance(MobEffects.GLOWING, 200, 0));
                }
            }
            if (effectLevel2>0 && (brightness<=8||livingEntity.hasEffect(MobEffects.DARKNESS))) {
                Optional.of(event.getEntity())
                        .map(LivingEntity::getAttributes)
                        .filter(manager -> manager.hasAttribute(Attributes.ARMOR))
                        .map(manager -> manager.getInstance(Attributes.ARMOR))
                        .filter(instance -> instance.getModifier(uuid) == null)
                        .ifPresent(instance -> instance.addTransientModifier(
                                new AttributeModifier(uuid, "modular_radiance_sword", -(effectLevel2/100), AttributeModifier.Operation.MULTIPLY_TOTAL)));
                mob.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 200, 0));
                if (!mob.hasEffect(MobEffects.DARKNESS)) {
                    map.put(MobEffects.DARKNESS, new MobEffectInstance(MobEffects.DARKNESS, 200, 0));
                }
            }
        }
    }
}
