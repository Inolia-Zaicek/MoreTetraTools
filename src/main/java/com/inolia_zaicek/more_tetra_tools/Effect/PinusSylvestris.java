package com.inolia_zaicek.more_tetra_tools.Effect;

import com.inolia_zaicek.more_tetra_tools.Register.MTTEffectsRegister;
import com.inolia_zaicek.more_tetra_tools.Util.MTTEffectHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import se.mickelus.tetra.items.modular.IModularItem;

import java.util.Random;

import static com.inolia_zaicek.more_tetra_tools.Effect.Clent.MTTEffectGuiStats.*;

public class PinusSylvestris {
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void hurt(LivingHurtEvent event) {
        //攻击
        if (event.getSource().getEntity() instanceof LivingEntity player) {
            float effectLevel = (MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectLevel(player, vanguardSwordsmanshipEffect));
                if (effectLevel > 0&&player.hasEffect(MTTEffectsRegister.VanguardSwordsmanship.get())) {
                    float number = 1+effectLevel / 100;
                    event.setAmount(event.getAmount()*number);
                    player.removeEffect(MTTEffectsRegister.VanguardSwordsmanship.get());
            }
        }else if (event.getSource().getDirectEntity() instanceof LivingEntity player) {
            float effectLevel = (MTTEffectHelper.getInstance().getMainMaxOffHandHalfEffectLevel(player, vanguardSwordsmanshipEffect));
            if (effectLevel > 0&&player.hasEffect(MTTEffectsRegister.VanguardSwordsmanship.get())) {
                float number = 1+effectLevel / 100;
                event.setAmount(event.getAmount()*number);
                player.removeEffect(MTTEffectsRegister.VanguardSwordsmanship.get());
            }
        }

    }
}