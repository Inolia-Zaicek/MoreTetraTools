package com.inolia_zaicek.more_tetra_tools.Effect;

import com.inolia_zaicek.more_tetra_tools.Register.MTTEffectsRegister;
import com.inolia_zaicek.more_tetra_tools.Util.MTTEffectHelper;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import se.mickelus.tetra.items.modular.IModularItem;

import static com.inolia_zaicek.more_tetra_tools.Effect.Clent.MTTEffectGuiStats.grievousWoundsEffect;
import static com.inolia_zaicek.more_tetra_tools.Effect.Clent.MTTEffectGuiStats.yukimau_Effect;

public class GrievousWounds {
    @SubscribeEvent
    public static void hurt(LivingHurtEvent event) {
        if (event.getSource().getEntity() instanceof LivingEntity livingEntity) {
            var mob = event.getEntity();
            var map = mob.getActiveEffectsMap();
            int effectLevel = MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectLevel(livingEntity, grievousWoundsEffect);
            if (effectLevel > 0) {
                mob.addEffect(new MobEffectInstance(MTTEffectsRegister.GrievousWounds.get(), 200, (int) effectLevel));
                map.put(MTTEffectsRegister.GrievousWounds.get(),
                        new MobEffectInstance(MTTEffectsRegister.GrievousWounds.get(), 200, (int) effectLevel));
            }
        } else if (event.getSource().getDirectEntity() instanceof LivingEntity livingEntity) {
            var mob = event.getEntity();
            var map = mob.getActiveEffectsMap();
            int effectLevel = MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectLevel(livingEntity, grievousWoundsEffect);
            if (effectLevel > 0) {
                mob.addEffect(new MobEffectInstance(MTTEffectsRegister.GrievousWounds.get(), 200, (int) effectLevel));
                map.put(MTTEffectsRegister.GrievousWounds.get(),
                        new MobEffectInstance(MTTEffectsRegister.GrievousWounds.get(), 200, (int) effectLevel));
            }
        }
    }
}
